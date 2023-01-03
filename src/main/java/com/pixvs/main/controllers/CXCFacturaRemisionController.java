package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaEditarProjection;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaListadoProjection;
import com.pixvs.main.models.projections.Cliente.ClienteComboProjection;
import com.pixvs.main.models.projections.Cliente.ClienteDatosFacturarProjection;
import com.pixvs.main.models.projections.ClienteRemision.ClienteRemisionFacturarProjection;
import com.pixvs.main.models.projections.ClienteRemisionDetalle.ClienteRemisionDetalleFacturarProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
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
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 16/06/2021.
 */
@RestController
@RequestMapping("/api/v1/cxcfacturas-remisiones")
public class CXCFacturaRemisionController {

    // Daos
    @Autowired
    private CXCFacturaDao cxcFacturaDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private MonedaParidadDao monedaParidadDao;
    @Autowired
    private ClienteRemisionDetalleDao clienteRemisionDetalleDao;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ClienteRemisionDao clienteRemisionDao;

    // Services
    @Autowired
    private ConcurrenciaService concurrenciaService;
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
    public JsonResponse getCXCFacturas() throws SQLException {

        List<CXCFacturaListadoProjection> cxcFacturas = cxcFacturaDao.findProjectedListadoAllBy();

        return new JsonResponse(cxcFacturas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody CXCFactura cxcFactura, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if(cxcFactura.getId() != null){
            return new JsonResponse(null, "No es posible actualizar el registro", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        cxcFactura.setFolio(autonumericoService.getSiguienteAutonumericoByPrefijo("CXCF"));

        //cxcFactura.setClienteId(cxcFactura.getCliente().getId());
        cxcFactura.setMonedaId(cxcFactura.getMoneda().getId());
        //cxcFactura.setTipoPagoId(cxcFactura.getTipoPago().getId());
        cxcFactura.setMetodoPagoId(cxcFactura.getMetodoPago().getId());
        //cxcFactura.setUsoCFDIId(cxcFactura.getUsoCFDI().getId());

        Moneda moneda = monedaDao.findById(cxcFactura.getMonedaId());
        MonedaParidad monedaParidad = monedaParidadDao.findByMonedaIdAndFecha(moneda.getId(),cxcFactura.getFecha());
        if(monedaParidad == null){
            //cxcFactura.setParidadOficial(BigDecimal.ONE);
        }else{
            //cxcFactura.setParidadOficial(monedaParidad.getTipoCambioOficial());
        }
        //cxcFactura.setParidadUsuario(BigDecimal.ONE);

        cxcFactura.setTipoRegistroId(ControlesMaestrosMultiples.CMM_CXCF_TipoRegistro.FACTURA_CXC);

        int numeroLinea = 0;
        List<Integer> remisionesActualizarIds = new ArrayList<>();
        HashMap<Integer,Boolean> remisionesActualizarIdsMap = new HashMap<>();
        for(CXCFacturaDetalle cxcFacturaDetalle : cxcFactura.getDetalles()){
//            if(cxcFacturaDetalle.getClienteRemisionDetalleId() != null){
//                ClienteRemisionDetalleFacturarProjection clienteRemisionDetalle = clienteRemisionDetalleDao.findProjectedFacturarById(cxcFacturaDetalle.getClienteRemisionDetalleId());
//                Boolean utilizarDetalleActual = true;
//                numeroLinea++;
//
//                cxcFacturaDetalle.setNumeroLinea(numeroLinea);
//                if(cxcFacturaDetalle.getIva() != null){
//                    cxcFacturaDetalle.setIva(cxcFacturaDetalle.getIva().divide(new BigDecimal(100)));
//                }
//                if(cxcFacturaDetalle.getIeps() != null){
//                    cxcFacturaDetalle.setIeps(cxcFacturaDetalle.getIeps().divide(new BigDecimal(100)));
//                }
//                if(cxcFacturaDetalle.getDescuento() == null){
//                    cxcFacturaDetalle.setDescuento(BigDecimal.ZERO);
//                }
//
//                ClienteRemision clienteRemision = clienteRemisionDao.findById(clienteRemisionDetalle.getClienteRemisionId());
//                if(cxcFactura.getFechaEmbarque() == null || cxcFactura.getFechaEmbarque().compareTo(clienteRemision.getFecha()) > 0){
//                    cxcFactura.setFechaEmbarque(clienteRemision.getFecha());
//                }
//                if(remisionesActualizarIdsMap.get(clienteRemision.getId()) == null || !remisionesActualizarIdsMap.get(clienteRemision.getId())){
//                    remisionesActualizarIds.add(clienteRemision.getId());
//                    remisionesActualizarIdsMap.put(clienteRemision.getId(),true);
//                }
//            }else{
//                numeroLinea++;
//                cxcFacturaDetalle.setNumeroLinea(numeroLinea);
//            }
        }

        cxcFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXCF_EstatusFactura.ABIERTA);
        cxcFacturaDao.save(cxcFactura);

        for(Integer clienteRemisionId : remisionesActualizarIds){
            ClienteRemision clienteRemision = clienteRemisionDao.findById(clienteRemisionId);
            BigDecimal cantidadPendienteFacturar = clienteRemisionDao.getCantidadPendienteFacturar(clienteRemisionId);
            if(cantidadPendienteFacturar.compareTo(BigDecimal.ZERO) == 0){
                clienteRemision.setEstatusId(ControlesMaestrosMultiples.CMM_CLIR_EstatusRemision.FACTURADA);
            }else{
                clienteRemision.setEstatusId(ControlesMaestrosMultiples.CMM_CLIR_EstatusRemision.FACTURADA_PARCIAL);
            }
            clienteRemision.setModificadoPorId(idUsuario);
            clienteRemisionDao.save(clienteRemision);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);

    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoRemisionById(@PathVariable(required = false) Integer id) throws SQLException {

        CXCFacturaEditarProjection cxcFacturaEditarProjection = null;
        List<ClienteComboProjection> clientes;
        List<MonedaComboProjection> monedas;
        List<ControlMaestroMultipleComboProjection> tiposPago;
        List<ControlMaestroMultipleComboProjection> metodosPago;
        List<ControlMaestroMultipleComboProjection> usosCFDI;
        List<ClienteRemisionFacturarProjection> clientesRemisiones = new ArrayList<>();
        List<ClienteRemisionDetalleFacturarProjection> clientesRemisionesDetalles = new ArrayList<>();

        // Datos solo de edición
        if (id != null) {
            cxcFacturaEditarProjection = cxcFacturaDao.findProjectedEditarById(id);
            clientes = Arrays.asList(cxcFacturaEditarProjection.getCliente());
            monedas = Arrays.asList(cxcFacturaEditarProjection.getMoneda());
            tiposPago = Arrays.asList(cxcFacturaEditarProjection.getTipoPago());
            metodosPago = Arrays.asList(cxcFacturaEditarProjection.getMetodoPago());
            usosCFDI = Arrays.asList(cxcFacturaEditarProjection.getUsoCFDI());

            CXCFactura cxcFactura = cxcFacturaDao.findById(id);
            HashMap<Integer,Boolean> remisionesAgregadasIdsMap = new HashMap<>();
            for(CXCFacturaDetalle detalle : cxcFactura.getDetalles()){
//                ClienteRemisionDetalleFacturarProjection clienteRemisionDetalle = clienteRemisionDetalleDao.findProjectedFacturarById(detalle.getClienteRemisionDetalleId());
//                clientesRemisionesDetalles.add(clienteRemisionDetalle);
//                if(remisionesAgregadasIdsMap.get(clienteRemisionDetalle.getClienteRemisionId()) == null || !remisionesAgregadasIdsMap.get(clienteRemisionDetalle.getClienteRemisionId())){
//                    ClienteRemisionFacturarProjection clienteRemision = clienteRemisionDao.findProjectedFacturarById(clienteRemisionDetalle.getClienteRemisionId());
//                    clientesRemisiones.add(clienteRemision);
//                    remisionesAgregadasIdsMap.put(clienteRemisionDetalle.getClienteRemisionId(),true);
//                }
            }
        }else{
            clientes = clienteDao.findProjectedComboAllByListadoPrecioIdNotNullAndActivoTrue();
            monedas = monedaDao.findProjectedComboAllByActivoTrue();
            tiposPago = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_CCXP_TipoPago.NOMBRE);
            metodosPago = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_CXC_MetodoPago.NOMBRE);
            usosCFDI = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_CXC_UsoCFDI.NOMBRE);
        }

        // Preparar body de la petición
        HashMap<String, Object> json = new HashMap<>();
        json.put("cxcFacturaEditarProjection", cxcFacturaEditarProjection);
        json.put("clientes", clientes);
        json.put("monedas", monedas);
        json.put("tiposPago", tiposPago);
        json.put("metodosPago", metodosPago);
        json.put("usosCFDI", usosCFDI);
        json.put("clientesRemisiones", clientesRemisiones);
        json.put("clientesRemisionesDetalles", clientesRemisionesDetalles);

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

        ClienteDatosFacturarProjection cliente = clienteDao.findProjectedDatosFacturarById(clienteId);
        List<ClienteRemisionFacturarProjection> clientesRemisiones = clienteRemisionDao.findProjectedFacturarAllByClienteId(clienteId);

        HashMap<String, Object> json = new HashMap<>();
        json.put("cliente",cliente);
        json.put("clientesRemisiones",clientesRemisiones);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/clientes-remisiones-detalles/{clienteRemisionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getClientesRemisionesDetalles(@PathVariable Integer clienteRemisionId) {

        List<ClienteRemisionDetalleFacturarProjection> clientesRemisionesDetalles = clienteRemisionDetalleDao.findProjectedFacturarAllByClienteRemisionId(clienteRemisionId);

        HashMap<String, Object> json = new HashMap<>();
        json.put("clientesRemisionesDetalles",clientesRemisionesDetalles);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/tipo-cambio/{monedaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTipoCambio(@PathVariable Integer monedaId) {

        MonedaParidad monedaParidad = monedaParidadDao.findByMonedaIdAndFecha(monedaId,new Date());
        BigDecimal tipoCambio = BigDecimal.ONE;
        if(monedaParidad != null){
            tipoCambio = monedaParidad.getTipoCambioOficial();
        }

        HashMap<String, Object> json = new HashMap<>();
        json.put("tipoCambio",tipoCambio);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

}
