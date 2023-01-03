package com.pixvs.main.controllers;

import com.pixvs.main.dao.LocalidadDao;
import com.pixvs.main.dao.OrdenCompraDao;
import com.pixvs.main.dao.OrdenCompraReciboDao;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraDevolverProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraListadoProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraRecibirProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.AutonumericoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 20/08/2020.
 */
@RestController
@RequestMapping("/api/v1/devolucion-ordenes-compra")
public class DevolucionOrdenCompraController {

    @Autowired
    private OrdenCompraReciboDao ordenCompraReciboDao;
    @Autowired
    private OrdenCompraDao ordenCompraDao;
    @Autowired
    private LocalidadDao localidadDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private ExcelController excelController;
    @Autowired
    private InventarioMovimientoController inventarioMovimientoController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getOrdenesCompra(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);
        List<Integer> almacenesIds = new ArrayList<>();

        for(Almacen almacen : usuario.getAlmacenes()){
            almacenesIds.add(almacen.getId());
        }

        List<Integer> estatusIds = new ArrayList<>();
        estatusIds.add(ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBIDA);
        estatusIds.add(ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBO_PARCIAL);
        List<OrdenCompraListadoProjection> ordenesCompra = ordenCompraDao.findProjectedListadoAllByEstatusIdInAndRecepcionArticulosAlmacenIdIn(estatusIds,almacenesIds);

        return new JsonResponse(ordenesCompra, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody OrdenCompra ordenCompra, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        OrdenCompra ordenCompraOriginal = ordenCompraDao.findById(ordenCompra.getId());
        String razonMovimiento = "Devolución de " + ordenCompraOriginal.getCodigo();
        String codigoRecibo = autonumericoService.getSiguienteAutonumericoByPrefijo("OCR");

        HashMap<Integer,OrdenCompraDetalle> detallesMap = new HashMap<>();
        for(OrdenCompraDetalle detalle : ordenCompraOriginal.getDetalles()){
            detallesMap.put(detalle.getId(),detalle);
        }

        for(OrdenCompraDetalle detalle : ordenCompra.getDetalles()){
            for(OrdenCompraRecibo recibo : detalle.getRecibos()){
                if(recibo.getCantidadDevolver().compareTo(BigDecimal.ZERO) > 0){
                    OrdenCompraRecibo nuevaDevolucion = new OrdenCompraRecibo();
                    nuevaDevolucion.setOrdenCompraId(ordenCompra.getId());
                    nuevaDevolucion.setCodigoRecibo(codigoRecibo);
                    nuevaDevolucion.setOrdenCompraDetalleId(detalle.getId());
                    nuevaDevolucion.setReciboReferenciaId(recibo.getId());
                    nuevaDevolucion.setFechaRequerida(ordenCompraOriginal.getFechaRequerida());
                    nuevaDevolucion.setFechaRecibo(new Date(System.currentTimeMillis()));
                    nuevaDevolucion.setCantidadRecibo(recibo.getCantidadDevolver().negate());
                    nuevaDevolucion.setLocalidadId(recibo.getLocalidadId());
                    nuevaDevolucion.setCreadoPorId(idUsuario);

                    ordenCompraReciboDao.save(nuevaDevolucion);

                    OrdenCompraDetalle detalleOriginal = detallesMap.get(detalle.getId());
                    InventarioMovimiento movimiento = new InventarioMovimiento();
                    movimiento.setArticuloId(detalleOriginal.getArticuloId());
                    movimiento.setLocalidadId(recibo.getLocalidadId());
                    movimiento.setCantidad(recibo.getCantidadDevolver().negate());
                    movimiento.setRazon(razonMovimiento);
                    movimiento.setReferencia(ordenCompraOriginal.getCodigo());
                    movimiento.setReferenciaMovimientoId(detalle.getId());
                    movimiento.setTipoMovimientoId(ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.DEVOLUCION_OC);
                    movimiento.setPrecioUnitario(detalleOriginal.getPrecio());

                    inventarioMovimientoController.procesarMovimiento(movimiento,req);
                }
            }
        }

        BigDecimal cantidadRecibida = ordenCompraDao.getCantidadRecibida(ordenCompra.getId());

        if(cantidadRecibida.compareTo(BigDecimal.ZERO) == 0){
            ordenCompraOriginal.setEstatusId(ControlesMaestrosMultiples.CMM_OC_EstatusOC.ABIERTA);
        }else{
            ordenCompraOriginal.setEstatusId(ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBO_PARCIAL);
        }
        ordenCompraOriginal.setModificadoPorId(idUsuario);

        ordenCompraDao.save(ordenCompraOriginal);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoOrdenesCompraById(@PathVariable(required = false) Integer id) throws SQLException {


        OrdenCompraDevolverProjection ordenCompra = null;
        if (id != null) {
            ordenCompra = ordenCompraDao.findProjectedDevolverById(id);
        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("ordenCompra", ordenCompra);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ORDENES_COMPRA]";
        String[] alColumnas = new String[]{"Código", "Fecha O C", "Fecha O C", "Estatus Id"};

        excelController.downloadXlsx(response, "ordenesCompra", query, alColumnas, null);
    }

}
