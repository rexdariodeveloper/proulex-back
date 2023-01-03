package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.LocalidadDao;
import com.pixvs.main.dao.OrdenCompraDao;
import com.pixvs.main.dao.OrdenCompraReciboDao;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraListadoProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraRecibirProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestros;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
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
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 20/08/2020.
 */
@RestController
@RequestMapping("/api/v1/recibo-ordenes-compra")
public class ReciboOrdenCompraController {

    @Autowired
    private OrdenCompraReciboDao ordenCompraReciboDao;
    @Autowired
    private OrdenCompraDao ordenCompraDao;
    @Autowired
    private LocalidadDao localidadDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private ExcelController excelController;
    @Autowired
    private InventarioMovimientoController inventarioMovimientoController;
    @Autowired
    private LogController logController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getOrdenesCompra(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);
        List<Integer> almacenesIds = new ArrayList<>();

        for(Almacen almacen : usuario.getAlmacenes()){
            almacenesIds.add(almacen.getId());
        }

        List<Integer> estatusIds = new ArrayList<>();
        estatusIds.add(ControlesMaestrosMultiples.CMM_OC_EstatusOC.ABIERTA);
        estatusIds.add(ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBO_PARCIAL);
        List<OrdenCompraListadoProjection> ordenesCompra = ordenCompraDao.findProjectedListadoAllByEstatusIdInAndRecepcionArticulosAlmacenIdIn(estatusIds,almacenesIds);

        return new JsonResponse(ordenesCompra, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody OrdenCompra ordenCompra, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ControlMaestro cmEvidenciaObligatoria = controlMaestroDao.findCMByNombre(ControlesMaestros.CM_RECIBOS_EVIDENCIA_OBLIGATORIA);
        ControlMaestro cmFacturaObligatoria = controlMaestroDao.findCMByNombre(ControlesMaestros.CM_RECIBOS_FACTURA_OBLIGATORIA);
        Boolean evidenciaObligatoria = cmEvidenciaObligatoria.getValorAsBoolean();
        Boolean facturaObligatoria = cmFacturaObligatoria.getValorAsBoolean();
        String codigoRecibo = autonumericoService.getSiguienteAutonumericoByPrefijo("OCR");

        OrdenCompra ordenCompraOriginal = ordenCompraDao.findById(ordenCompra.getId());

        try{
            concurrenciaService.verificarIntegridad(ordenCompraOriginal.getFechaModificacion(),ordenCompra.getFechaModificacion());
        }catch (Exception e){
            return new JsonResponse("", ordenCompraOriginal.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }

        String razonMovimiento = "Recibo de " + ordenCompraOriginal.getCodigo();

        List<OrdenCompraRecibo> recibosGuardar = new ArrayList<>();

        HashMap<Integer,OrdenCompraDetalle> detallesMap = new HashMap<>();
        for(OrdenCompraDetalle detalle : ordenCompraOriginal.getDetalles()){
            detallesMap.put(detalle.getId(),detalle);
        }

        for(OrdenCompraDetalle detalle : ordenCompra.getDetalles()){
            if(detalle.getCantidadRecibida().compareTo(BigDecimal.ZERO) > 0){
                if(evidenciaObligatoria && ordenCompra.getEvidenciaReciboIds().size() == 0){
                    return new JsonResponse(null, "Debes adjuntar evidencia", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }
                if(facturaObligatoria && (ordenCompra.getFacturaPDFReciboId() == null || ordenCompra.getFacturaXMLReciboId() == null)){
                    return new JsonResponse(null, "Debes adjuntar una factura (PDF y XML)", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                }

                OrdenCompraRecibo nuevoRecibo = new OrdenCompraRecibo();
                nuevoRecibo.setOrdenCompraId(ordenCompra.getId());
                nuevoRecibo.setOrdenCompraDetalleId(detalle.getId());
                nuevoRecibo.setCodigoRecibo(codigoRecibo);
                nuevoRecibo.setFechaRequerida(ordenCompraOriginal.getFechaRequerida());
                nuevoRecibo.setFechaRecibo(new Date(System.currentTimeMillis()));
                nuevoRecibo.setCantidadRecibo(detalle.getCantidadRecibida());
                nuevoRecibo.setLocalidadId(ordenCompra.getLocalidadRecibir().getId());
                nuevoRecibo.setCreadoPorId(idUsuario);

                if (ordenCompra.getFacturaPDFReciboId() != null) {
                    nuevoRecibo.setFacturaPDFId(ordenCompra.getFacturaPDFReciboId());
                }

                if (ordenCompra.getFacturaXMLReciboId() != null) {
                    nuevoRecibo.setFacturaXMLId(ordenCompra.getFacturaXMLReciboId());
                }

                for(Integer archivoId : ordenCompra.getEvidenciaReciboIds()){
                    Archivo archivo = new Archivo();
                    archivo.setId(archivoId);
                    nuevoRecibo.getEvidencia().add(archivo);
                }

                recibosGuardar.add(nuevoRecibo);

                OrdenCompraDetalle detalleOriginal = detallesMap.get(detalle.getId());
                InventarioMovimiento movimiento = new InventarioMovimiento();
                movimiento.setArticuloId(detalleOriginal.getArticuloId());
                movimiento.setLocalidadId(ordenCompra.getLocalidadRecibir().getId());
                movimiento.setCantidad(detalle.getCantidadRecibida());
                movimiento.setRazon(razonMovimiento);
                movimiento.setReferencia(ordenCompraOriginal.getCodigo());
                movimiento.setReferenciaMovimientoId(detalle.getId());
                movimiento.setTipoMovimientoId(ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.RECIBO_OC);
                movimiento.setPrecioUnitario(detalleOriginal.getPrecio());

                inventarioMovimientoController.procesarMovimiento(movimiento,req);
            }
        }

        for(OrdenCompraRecibo recibo : recibosGuardar){
            ordenCompraReciboDao.save(recibo);
        }

        BigDecimal cantidad = ordenCompraDao.getCantidad(ordenCompra.getId());
        BigDecimal cantidadRecibida = ordenCompraDao.getCantidadRecibida(ordenCompra.getId());

        if(cantidad.compareTo(cantidadRecibida) > 0){
            ordenCompraOriginal.setEstatusId(ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBO_PARCIAL);

        }else{
            ordenCompraOriginal.setEstatusId(ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBIDA);

        }
        ordenCompraOriginal.setModificadoPorId(idUsuario);

        ordenCompraDao.save(ordenCompraOriginal);
//        inventarioMovimientoController.procesarMovimiento()

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoOrdenesCompraById(@PathVariable(required = false) Integer id) throws SQLException {


        OrdenCompraRecibirProjection ordenCompra = null;
        OrdenCompra ordenCompraModelo = null;
        if (id != null) {
            ordenCompra = ordenCompraDao.findProjectedRecibirById(id);
            ordenCompraModelo = ordenCompraDao.findById(id);
        }

        List<LocalidadComboProjection> localidades = localidadDao.findProjectedComboAllByActivoTrueAndAlmacenId(ordenCompraModelo.getRecepcionArticulosAlmacenId());
        List<ControlMaestroMultipleComboProjection> tiposArchivos = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_OCR_TipoArchivo.NOMBRE);

        HashMap<String, Object> json = new HashMap<>();

        json.put("ordenCompra", ordenCompra);

        json.put("localidades", localidades);
        json.put("tiposArchivos", tiposArchivos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ORDENES_COMPRA]";
        String[] alColumnas = new String[]{"Código", "Fecha O C", "Fecha O C", "Estatus Id"};

        excelController.downloadXlsx(response, "ordenesCompra", query, alColumnas, null);
    }

}
