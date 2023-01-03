package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaBeneficiarioProjection;
import com.pixvs.main.models.projections.FormaPago.FormaPagoComboProjection;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.main.models.mapeos.Monedas;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaPagoProveedoresProjection;
import com.pixvs.main.models.projections.CXPSolicitudPago.CXPSolicitudPagoListadoProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoDetalle.CXPSolicitudPagoDetalleListadoProjection;
import com.pixvs.main.models.projections.CuentaBancaria.CuentaBancariaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.util.HashId;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

/**
 * Created by Angel Daniel Hernández Silva on 18/09/2020.
 */
@RestController
@RequestMapping("/api/v1/pago-proveedores")
public class PagoProveedoresController {

    @Autowired
    private CXPSolicitudPagoDao cxpSolicitudPagoDao;
    @Autowired
    private CXPFacturaDao cxpFacturaDao;
    @Autowired
    private CXPPagoDao cxpPagoDao;
    @Autowired
    private CXPSolicitudPagoServicioDao cxpSolicitudPagoServicioDao;
    @Autowired
    private CuentaBancariaDao cuentaBancariaDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private Environment environment;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private LogController logController;
    @Autowired
    private FormaPagoDao formaPagoDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private OrdenCompraDao ordencompraDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getSolicitudes() throws SQLException {

        HashMap<String, Object> json = new HashMap<>();

        List<CXPSolicitudPagoListadoProjection> solicitudes = cxpSolicitudPagoDao.findProjectedListadoAllByEstatusIdIn(Arrays.asList(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.ACEPTADA,ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.POR_AUTORIZAR));
        List<FormaPagoComboProjection> formasPago = formaPagoDao.findProjectedComboAllByActivoTrue();
        //List<ControlMaestroMultipleComboProjection> formasPago = controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(ControlesMaestrosMultiples.CMM_CXPP_FormaPago.NOMBRE);
        List<CuentaBancariaListadoProjection> cuentas = cuentaBancariaDao.findProjectedListadoAllByActivoTrue();

        List<Integer> cxpFacturasIds = new ArrayList<>();
        for(CXPSolicitudPagoListadoProjection solicitud : solicitudes){
            for(CXPSolicitudPagoDetalleListadoProjection detalle : solicitud.getDetalles()){
                cxpFacturasIds.add(detalle.getCxpFacturaId());
            }
        }
        List<CXPFacturaPagoProveedoresProjection> cxpFacturas = cxpFacturaDao.findProjectedPagoProveedoresAllByIdIn(cxpFacturasIds);
        List<CXPFacturaBeneficiarioProjection> beneficiarios = cxpFacturaDao.getBeneficiarios(cxpFacturasIds);

        json.put("solicitudes",solicitudes);
        json.put("formasPago",formasPago);
        json.put("cxpFacturas",cxpFacturas);
        json.put("cuentas",cuentas);
        json.put("beneficiarios",beneficiarios);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);

    }

    @RequestMapping(value = "/cancelar", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse rechazarSolicitud(@RequestBody HashMap<String,Object> body, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer solicitudId = (Integer)body.get("solicitudId");

        CXPSolicitudPago solicitudRechazar = cxpSolicitudPagoDao.findById(solicitudId);

        for(CXPSolicitudPagoDetalle detalle : solicitudRechazar.getDetalles()){
            if(detalle.getEstatusId().intValue() == ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.PAGADA){
                return new JsonResponse(null,"Acción no disponible. Ya se pagaron algunas facturas.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
        }

        for(CXPSolicitudPagoDetalle detalle : solicitudRechazar.getDetalles()){
            CXPFactura factura = detalle.getCxpFactura();
            factura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO);
            factura.setModificadoPorId(idUsuario);
            cxpFacturaDao.save(factura);

            detalle.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.CANCELADA);

            // Obtenemos  de la factura
            List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( factura.getId() );
            for( Integer oc: ordenesCompra ){
                logController.insertaLogUsuario(
                        new Log(null,
                                LogTipo.PROGRAMACION_CANCELADO,
                                LogProceso.ORDENES_COMPRA,
                                oc
                        ),
                        idUsuario
                );
            }
        }

        solicitudRechazar.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.CANCELADA);
        solicitudRechazar.setModificadoPorId(idUsuario);

        cxpSolicitudPagoDao.save(solicitudRechazar);

        logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.CANCELADA,
                        LogProceso.CXP_SOLICITUDES_PAGOS,
                        solicitudRechazar.getId()
                ),
                idUsuario
        );

        return new JsonResponse(null,"","Solicitud rechazada",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/pagar", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarPago(@RequestBody CXPPago cxpPago, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<Integer> detallesSolicitudActualizarFacturasIds = new ArrayList<>();

        cxpPago.setCuentaBancariaId(cxpPago.getCuentaBancaria().getId());

        if(cxpPago.getFormaPago() != null && cxpPago.getFormaPago().getId() != null){
            cxpPago.setFormaPagoId(cxpPago.getFormaPago().getId());
        }

        for(CXPPagoDetalle detalle : cxpPago.getDetalles()){

            CXPFactura cxpFactura = cxpFacturaDao.findById(detalle.getCxpFacturaId());
            BigDecimal montoPagado = cxpFacturaDao.getMontoPagado(detalle.getCxpFacturaId());
            if(montoPagado == null){
                montoPagado = BigDecimal.ZERO;
            }
            Integer logTipo;
            if(cxpFactura.getMontoRegistro().subtract(montoPagado).subtract(detalle.getMontoAplicado()).compareTo(BigDecimal.ZERO) > 0){
                cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO);
                logTipo = LogTipo.PAGO_PARCIAL;
            }else{
                cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGADA);
                logTipo = LogTipo.PAGADO;

                /** Actualizar Solicitud de pago (CXPSolicitudedsPagosServicios) **/
                List<CXPSolicitudPagoServicio> cxpSolicitudPagoServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(cxpFactura.getId());
                List<Integer> cxpSolicitudPagoServiciosActualizadosIds = new ArrayList<>();
                for(CXPSolicitudPagoServicio cxpSolicitudPagoServicio : cxpSolicitudPagoServicios){
                    if(!cxpSolicitudPagoServiciosActualizadosIds.contains(cxpSolicitudPagoServicio.getId())){
                        cxpSolicitudPagoServiciosActualizadosIds.add(cxpSolicitudPagoServicio.getId());
                        cxpSolicitudPagoServicio.setEstatusId(ControlesMaestrosMultiples.CMM_CXPSPS_EstadoSolicitudPago.PAGADA);
                        cxpSolicitudPagoServicioDao.save(cxpSolicitudPagoServicio);
                    }
                }
            }
            cxpFactura.setModificadoPorId(idUsuario);
            cxpFacturaDao.save(cxpFactura);

            detallesSolicitudActualizarFacturasIds.add(detalle.getCxpFacturaId());

            List<CXPSolicitudPagoServicio> solicitudesServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(detalle.getCxpFacturaId());
            for(CXPSolicitudPagoServicio solicitud : solicitudesServicios){
                logController.insertaLogUsuario(
                        new Log(null,
                                logTipo ,
                                LogProceso.SOLICITUD_PAGO,
                                solicitud.getId()
                        ),
                        idUsuario
                );
            }

            if(logTipo == LogTipo.PAGADO){
                // Obtenemos  de la factura
                List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( cxpFactura.getId() );
                for( Integer oc: ordenesCompra ){

                    Integer registrosNoCompletos = ordencompraDao.getNoPagadas(oc);

                    if(registrosNoCompletos == null || registrosNoCompletos == 0){

                        OrdenCompra ordenCompra = ordencompraDao.findById(oc);
                        ordenCompra.setEstatusId(ControlesMaestrosMultiples.CMM_OC_EstatusOC.PAGADA);
                        ordencompraDao.save(ordenCompra);

                        logController.insertaLogUsuario(
                                new Log(null,
                                        LogTipo.PAGADO,
                                        LogProceso.ORDENES_COMPRA,
                                        oc
                                ),
                                idUsuario
                        );
                    }

                }
            }

        }

        cxpPago.setMonedaId(Monedas.PESO); // TODO: Cambiar cuando se implemente el pago con diferentes tipos de moneda
        cxpPago.setParidadOficial(BigDecimal.ONE); // TODO: Cambiar cuando se implemente el pago con diferentes tipos de moneda
        cxpPago.setParidadUsuario(BigDecimal.ONE); // TODO: Cambiar cuando se implemente el pago con diferentes tipos de moneda
        cxpPago.setEstatusId(ControlesMaestrosMultiples.CMM_CXPP_EstatusPago.PAGADO);

        CXPSolicitudPago solicitud = cxpSolicitudPagoDao.findById(cxpPago.getSolicitudId());

        int contDetallesFinalizados = 0;
        for(CXPSolicitudPagoDetalle detalleSolicitud: solicitud.getDetalles()){
            if(detallesSolicitudActualizarFacturasIds.contains(detalleSolicitud.getCxpFacturaId())){
                detalleSolicitud.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.PAGADA);
                detalleSolicitud.setModificadoPorId(idUsuario);
                contDetallesFinalizados++;
            }else if(!detalleSolicitud.getEstatusId().equals(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.ACEPTADA)){
                contDetallesFinalizados++;
            }
        }
        if(contDetallesFinalizados == solicitud.getDetalles().size()){
            solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.PAGADA);
            solicitud.setModificadoPorId(idUsuario);

            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.PAGADO,
                            LogProceso.CXP_SOLICITUDES_PAGOS,
                            solicitud.getId()
                    ),
                    idUsuario
            );
        }else{
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.PAGO_PARCIAL,
                            LogProceso.CXP_SOLICITUDES_PAGOS,
                            solicitud.getId()
                    ),
                    idUsuario
            );
        }
        cxpSolicitudPagoDao.save(solicitud);
        cxpPagoDao.save(cxpPago);

        return new JsonResponse(null,null,JsonResponse.STATUS_OK);
    }

    /** REPORTE PAGO A PROVEEDORES **/
    @RequestMapping(value = "/report/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> data = new HashMap<>();

        data.put("sedes", sucursalDao.findProjectedComboAllByActivoTrue());
        data.put("proveedores", proveedorDao.findProjectedComboVistaAllByActivoTrue());
        data.put("monedas", monedaDao.findProjectedComboAllByActivoTrue());
        data.put("formasPago", formaPagoDao.findProjectedComboAllByActivoTrue());
        data.put("cuentas", cuentaBancariaDao.findProjectedListadoAllByActivoTrue());

        data.put("datos", new ArrayList<>());

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/report/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporteFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException, Exception {

        HashMap<String, Object> filtros = getFiltros(json);
        HashMap<String, Object> datos = new HashMap<>();

        String p0 = (String) filtros.get("fechaInicio");
        String p1 = (String) filtros.get("fechaFin");
        String p2 = (String) filtros.get("sedes");
        String p3 = (String) filtros.get("proveedores");
        String p4 = (String) filtros.get("numeroDocumento");
        Integer p5 = (Integer) filtros.get("moneda");
        String p6 = (String) filtros.get("formaPago");
        Integer p7 = (Integer) filtros.get("cuenta");

        List< Map<String,Object> > report = cxpPagoDao.findAllQueryReporteBy(p0,p1,p2,p3,p4,p5,p6,p7);
        datos.put("datos", report );

        return new JsonResponse(report, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="report/download/excel", method = RequestMethod.POST)
    public void exportPedidos(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, ParseException, Exception {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/pagoProveedores/pagoProveedores.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());

        parameters.put("fechaInicio", filtros.get("fechaInicio"));
        parameters.put("fechaFin", filtros.get("fechaFin"));
        parameters.put("sedes", filtros.get("sedes"));
        parameters.put("proveedores", filtros.get("proveedores"));
        parameters.put("numeroDocumento", filtros.get("numeroDocumento"));
        parameters.put("moneda", filtros.get("moneda"));
        parameters.put("formaPago", filtros.get("formaPago"));
        parameters.put("cuenta", filtros.get("cuenta"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PagoProveedores.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/pdf/{id}", method = RequestMethod.GET)
    public void descargarPdf(@PathVariable Integer id, HttpServletResponse response) throws IOException, SQLException, JRException {

        CXPSolicitudPago solicitudPago = cxpSolicitudPagoDao.findById(id);

        String reportPath = "/cuentasPorPagar/Programacion_pago_proveedores.jasper";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("frontUrl", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("id", solicitudPago.getId());
        parameters.put("mostrarPagado", false);

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+solicitudPago.getCodigoSolicitud()+".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value = "/historial/{solicitudId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getHistorial(@PathVariable Integer solicitudId) throws SQLException {

        HashMap<String, Object> json = new HashMap<>();

        json.put("historial",logController.getHistorial(solicitudId, LogProceso.CXP_SOLICITUDES_PAGOS));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);

    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws Exception {
        HashMap<String, Object> filtros = new HashMap<>();

        String fecha0 = (String) json.get("fechaInicio");
        filtros.put("fechaInicio", isNullorEmpty(fecha0)? null : fecha0);

        String fecha1 = (String) json.get("fechaFin");
        filtros.put("fechaFin", isNullorEmpty(fecha1)? null : fecha1);

        String sedes = array2string((ArrayList<HashMap<String, Integer>>) json.get("sedes"));
        filtros.put("sedes", isNullorEmpty(sedes)? null : sedes);

        String proveedores = array2string((ArrayList<HashMap<String, Integer>>) json.get("proveedores"));
        filtros.put("proveedores", isNullorEmpty(proveedores)? null : proveedores);

        String numeroDocumento = json.getAsString("numeroDocumento");
        filtros.put("numeroDocumento", isNullorEmpty(numeroDocumento)? null : numeroDocumento);

        HashMap<String, Object> moneda = (HashMap<String, Object>) json.get("moneda");
        filtros.put("moneda", moneda != null? (Integer) moneda.get("id") : null);

        String formaPago = array2string((ArrayList<HashMap<String, Integer>>) json.get("formaPago"));
        filtros.put("formaPago", isNullorEmpty(formaPago)? null : formaPago);

        HashMap<String, Object> cuenta = (HashMap<String, Object>) json.get("cuenta");
        filtros.put("cuenta", cuenta != null? (Integer) cuenta.get("id") : null);

        return filtros;
    }

    private String array2string(ArrayList<HashMap <String, Integer> > array){
        if(array != null) {
            String string = "";
            for (HashMap<String, Integer> reg : array) {
                string += "|" + reg.get("id") + "|";
            }
            return string;
        }
        return null;
    }
}
