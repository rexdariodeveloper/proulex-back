package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ArticulosSubtipos;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboListaPreciosProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.ClienteRemision.ClienteRemisionEditarProjection;
import com.pixvs.main.models.projections.ClienteRemision.ClienteRemisionListadoProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 31/05/2021.
 */
@RestController
@RequestMapping("/api/v1/remisiones")
public class ClienteRemisionController {

    // Daos
    @Autowired
    private ClienteRemisionDao clienteRemisionDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private LocalidadDao localidadDao;
    @Autowired
    private LocalidadArticuloAcumuladoDao localidadArticuloAcumuladoDao;

    // Services
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;
    @Autowired
    private AutonumericoService autonumericoService;

    // Controllers
    @Autowired
    private ExcelController excelController;
    @Autowired
    private InventarioMovimientoController inventarioMovimientoController;

    // Otros
    @Autowired
    private HashId hashId;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getRemisiones() throws SQLException {

        List<ClienteRemisionListadoProjection> remisiones = clienteRemisionDao.findProjectedListadoAllBy();

        return new JsonResponse(remisiones, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ClienteRemision clienteRemision, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (clienteRemision.getId() == null) {
            clienteRemision.setCreadoPorId(idUsuario);
            clienteRemision.setEstatusId(ControlesMaestrosMultiples.CMM_CLIR_EstatusRemision.ENVIADA);
            clienteRemision.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("RM"));
        } else {
            ClienteRemision objetoActual = clienteRemisionDao.findById(clienteRemision.getId().intValue());
            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), clienteRemision.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            clienteRemision.setModificadoPorId(idUsuario);
        }

        // Settear id de subpropiedades en cabecera
        clienteRemision.setClienteId(clienteRemision.getCliente().getId());
        clienteRemision.setMonedaId(clienteRemision.getMoneda().getId());
        clienteRemision.setAlmacenOrigenId(clienteRemision.getAlmacenOrigen().getId());
        clienteRemision.setAlmacenDestinoId(clienteRemision.getAlmacenDestino().getId());

        // Settear id de subpropiedades en detalles
        for(ClienteRemisionDetalle detalle : clienteRemision.getDetalles()) {
            detalle.setArticuloId(detalle.getArticulo().getId());
        }

        clienteRemision = clienteRemisionDao.save(clienteRemision);


        // Movimientos de salida y entrada
        Almacen almacenOrigen = almacenDao.findById(clienteRemision.getAlmacenOrigenId());
        Localidad localidadOrigen = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(clienteRemision.getAlmacenOrigenId());
        Almacen almacenDestino = almacenDao.findById(clienteRemision.getAlmacenDestinoId());
        Localidad localidadDestino = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(clienteRemision.getAlmacenDestinoId());
        for(ClienteRemisionDetalle detalle : clienteRemision.getDetalles()){
            // Salida de origen
            InventarioMovimiento movimientoSalida = new InventarioMovimiento();
            movimientoSalida.setArticuloId(detalle.getArticuloId());
            movimientoSalida.setLocalidadId(localidadOrigen.getId());
            movimientoSalida.setCantidad(detalle.getCantidad().negate());
            movimientoSalida.setRazon("Remisión de: " + almacenOrigen.getCodigoAlmacen() + " a " + almacenDestino.getCodigoAlmacen());
            movimientoSalida.setReferencia(clienteRemision.getCodigo());
            movimientoSalida.setReferenciaMovimientoId(detalle.getId());
            movimientoSalida.setTipoMovimientoId(ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.REMISION);

            inventarioMovimientoController.procesarMovimiento(movimientoSalida, req);

            // Entrada a destino
            InventarioMovimiento movimientoEntrada = new InventarioMovimiento();
            movimientoEntrada.setArticuloId(detalle.getArticuloId());
            movimientoEntrada.setLocalidadId(localidadDestino.getId());
            movimientoEntrada.setCantidad(detalle.getCantidad());
            movimientoEntrada.setRazon("Remisión de: " + almacenOrigen.getCodigoAlmacen() + " a " + almacenDestino.getCodigoAlmacen());
            movimientoEntrada.setReferencia(clienteRemision.getCodigo());
            movimientoEntrada.setReferenciaMovimientoId(detalle.getId());
            movimientoEntrada.setTipoMovimientoId(ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.REMISION);

            inventarioMovimientoController.procesarMovimiento(movimientoEntrada, req);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idClienteRemision}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idClienteRemision) throws SQLException {

        ClienteRemision clienteRemision = clienteRemisionDao.findById(hashId.decode(idClienteRemision));
        clienteRemision.setEstatusId(ControlesMaestrosMultiples.CMM_CLIR_EstatusRemision.CANCELADA);
        clienteRemisionDao.save(clienteRemision);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoRemisionById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ClienteRemisionEditarProjection clienteRemision = null;
        List<ClienteComboProjection> clientes;
        List<MonedaComboProjection> monedas;
        List<AlmacenComboProjection> almacenesOrigen;
        List<AlmacenComboProjection> almacenesDestino = new ArrayList<>();
        List<ArticuloComboListaPreciosProjection> articulos = new ArrayList<>();

        // Datos solo de edición
        if (id != null) {
            clienteRemision = clienteRemisionDao.findProjectedEditarById(id);
            Cliente cliente = clienteDao.findClienteById(clienteRemision.getCliente().getId());
            clientes = Arrays.asList(clienteRemision.getCliente());
            monedas = Arrays.asList(clienteRemision.getMoneda());
            almacenesOrigen = Arrays.asList(clienteRemision.getAlmacenOrigen());
            almacenesDestino = Arrays.asList(clienteRemision.getAlmacenDestino());
            articulos = articuloDao.findProjectedComboListaPreciosAllByListadoPrecioId(cliente.getListadoPrecioId());
        }else{
            clientes = clienteDao.findProjectedComboAllByConsignadosAndListaPrecios();
            monedas = monedaDao.findProjectedComboAllByActivoTrue();
            almacenesOrigen = almacenDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        }

        // Preparar body de la petición
        HashMap<String, Object> json = new HashMap<>();
        json.put("clienteRemision", clienteRemision);
        json.put("clientes", clientes);
        json.put("monedas", monedas);
        json.put("almacenesOrigen", almacenesOrigen);
        json.put("almacenesDestino", almacenesDestino);
        json.put("articulos", articulos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_REPORTE_EXCEL_REMISIONES]";
        String[] alColumnas = new String[]{"Código", "Cliente", "RFC", "Fecha", "Origen", "Destino", "Monto", "Estatus"};

        excelController.downloadXlsx(response, "remisiones", query, alColumnas, null,"Remisiones");
    }

    @RequestMapping(value = "/datos/cliente/{clienteId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosCliente(@PathVariable Integer clienteId) {

        Cliente cliente = clienteDao.findClienteById(clienteId);
        List<AlmacenComboProjection> almacenesCliente = almacenDao.findProjectedComboAllByClienteIdAndActivoTrue(clienteId);
        List<ArticuloComboListaPreciosProjection> articulos = articuloDao.findProjectedComboListaPreciosAllByListadoPrecioId(cliente.getListadoPrecioId());

        HashMap<String, Object> json = new HashMap<>();
        json.put("almacenesCliente", almacenesCliente);
        json.put("articulos", articulos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/existencia/articulo/{articuloId}/almacen/{almacenId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getExistenciasArticulo(@PathVariable Integer articuloId, @PathVariable Integer almacenId) {

        Localidad localidad = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacenId);
        LocalidadArticuloAcumulado localidadArticuloAcumulado = localidadArticuloAcumuladoDao.findByArticuloIdAndLocalidadId(articuloId,localidad.getId());

        BigDecimal existencia = BigDecimal.ZERO;
        if(localidadArticuloAcumulado != null){
            existencia = localidadArticuloAcumulado.getCantidad();
        }

        return new JsonResponse(existencia, null, JsonResponse.STATUS_OK);
    }

}
