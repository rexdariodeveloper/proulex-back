package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.AlertasConfiguraciones;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaBeneficiarioProjection;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaPagoProveedoresProjection;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaProgramacionPagoBeneficiarioProjection;
import com.pixvs.main.models.projections.CXPSolicitudPago.CXPSolicitudPagoAlertaProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoDetalle.CXPSolicitudPagoDetalleAlertaProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHEditarProjection;
import com.pixvs.spring.dao.AlertaConfigDao;
import com.pixvs.spring.dao.AlertasDao;
import com.pixvs.spring.dao.MenuDao;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaProgramacionPagoProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorProgramacionPagoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.util.DateUtil;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.compress.utils.IOUtils;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@RestController
@RequestMapping("/api/v1/programacion-pagos")
public class ProgramacionPagoController {

    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private CXPFacturaDao cxpFacturaDao;
    @Autowired
    private CXPSolicitudPagoDao cxpSolicitudPagoDao;
    @Autowired
    private CXPSolicitudPagoServicioDao cxpSolicitudPagoServicioDao;
    @Autowired
    private CXPSolicitudPagoRHDao cxpSolicitudPagoRHDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AlertaConfigDao alertaConfigDao;
    @Autowired
    private AlertasDao alertaDao;
    @Autowired
    private OrdenCompraDao ordencompraDao;

    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private ProcesadorAlertasService alertasService;
    @Autowired
    private ReporteService reporteService;

    @Autowired
    private LogController logController;

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFacturas() throws SQLException {

        HashMap<String, Object> json = new HashMap<>();

        List<ProveedorProgramacionPagoProjection> proveedores = proveedorDao.findProjectedProgramacionPagoAllBy();
        List<CXPFacturaProgramacionPagoBeneficiarioProjection> cxpFacturas = cxpFacturaDao.findProjectedProgramacionPagoBeneficiarioAllBy();

        json.put("proveedores",proveedores);
        json.put("cxpFacturas",cxpFacturas);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);

    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFacturas(@RequestBody JSONObject datos, ServletRequest req) throws SQLException, ParseException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(datos);

        List<CXPFacturaProgramacionPagoBeneficiarioProjection> cxpFacturas = cxpFacturaDao.findProjectedProgramacionPagoAllByFiltros(((ArrayList<Integer>) filtros.get("proveedores")) != null? 0 : 1,(ArrayList<Integer>) filtros.get("proveedores"),(String) filtros.get("documento"));

        HashMap<String, Object> json = new HashMap<>();

        json.put("cxpFacturas",cxpFacturas);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/programar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse programar(@RequestBody List<CXPFactura> facturasProgramarBody, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<Integer> facturasProgramarIds = new ArrayList<>();
        HashMap<Integer,CXPFactura> facturasProgramarMap = new HashMap<>();
        for(CXPFactura factura : facturasProgramarBody){
            facturasProgramarIds.add(factura.getId());
            facturasProgramarMap.put(factura.getId(),factura);
        }

        List<CXPFactura> facturasProgramar = cxpFacturaDao.findByIdIn(facturasProgramarIds);

        for(CXPFactura factura : facturasProgramar){
            try{
                concurrenciaService.verificarIntegridad(factura.getFechaModificacion(),facturasProgramarMap.get(factura.getId()).getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", factura.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            //Validamos el estatus de la factura
            if(factura.getEstatusId() == ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA ){
                return new JsonResponse("", ("Una o mas facturas han sido canceladas por: "+factura.getModificadoPor().getNombreCompleto()), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
        }

        List<CXPSolicitudPagoDetalle> cxpSolicitudPagoDetalles = new ArrayList<>();

        BigDecimal montoSolicitud = BigDecimal.ZERO;

        String codigo = autonumericoService.getSiguienteAutonumericoByPrefijo("CXPS");

        for(CXPFactura factura : facturasProgramar){
            montoSolicitud = montoSolicitud.add(facturasProgramarMap.get(factura.getId()).getMontoRegistro());

            factura.setModificadoPorId(idUsuario);
            BigDecimal montoProgramadoFactura = cxpFacturaDao.getMontoProgramado(factura.getId());
            if(montoProgramadoFactura == null){
                montoProgramadoFactura = BigDecimal.ZERO;
            }
            BigDecimal montoPagadoFactura = cxpFacturaDao.getMontoPagado(factura.getId());
            if(montoPagadoFactura == null){
                montoPagadoFactura = BigDecimal.ZERO;
            }
            if(factura.getMontoRegistro().subtract(montoPagadoFactura).subtract(montoProgramadoFactura).equals(BigDecimal.ZERO)){
                factura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO);
            }else{
                factura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_PARCIAL);
            }

            cxpFacturaDao.save(factura);

            CXPSolicitudPagoDetalle cxpSolicitudPagoDetalle = new CXPSolicitudPagoDetalle();
            cxpSolicitudPagoDetalle.setCxpFacturaId(factura.getId());
            cxpSolicitudPagoDetalle.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.ACEPTADA);
            cxpSolicitudPagoDetalles.add(cxpSolicitudPagoDetalle);
            cxpSolicitudPagoDetalle.setMontoProgramado(facturasProgramarMap.get(factura.getId()).getMontoRegistro());

            List<CXPSolicitudPagoServicio> solicitudesServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(factura.getId());
            for(CXPSolicitudPagoServicio solicitud : solicitudesServicios){

            }

            // Obtenemos  de la factura
            List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( factura.getId() );
            for( Integer oc: ordenesCompra ){
                Integer registrosNoCompletos = ordencompraDao.getNoCompletados(oc, 0);

                if(registrosNoCompletos == null || registrosNoCompletos == 0){
                    OrdenCompra ordenCompra = ordencompraDao.findById(oc);
                    ordenCompra.setEstatusId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OC_EstatusOC.PROGRAMADA);
                    ordencompraDao.save(ordenCompra);
                }

                logController.insertaLogUsuario(
                        new Log(codigo,
                                LogTipo.PAGO_PROGRAMADO,
                                LogProceso.ORDENES_COMPRA,
                                oc
                        ),
                        idUsuario
                );
            }
        }

        CXPSolicitudPago cxpSolicitudPago = new CXPSolicitudPago();
        cxpSolicitudPago.setCodigoSolicitud(codigo);
        cxpSolicitudPago.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.ACEPTADA);
        cxpSolicitudPago.setCreadoPorId(idUsuario);
        cxpSolicitudPago.setDetalles(cxpSolicitudPagoDetalles);

        cxpSolicitudPago = cxpSolicitudPagoDao.save(cxpSolicitudPago);
        Boolean requiereAlerta = alertasService.validarAutorizacion(AlertasConfiguraciones.PAGOS_VALIDACION, cxpSolicitudPago.getId(), cxpSolicitudPago.getCodigoSolicitud(), "Programación de pago", cxpSolicitudPago.getSucursalId(), idUsuario,NumberFormat.getCurrencyInstance(Locale.US).format(montoSolicitud));
        if(requiereAlerta){
            cxpSolicitudPago.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.POR_AUTORIZAR);
            cxpSolicitudPago.setModificadoPorId(idUsuario);
            cxpSolicitudPago = cxpSolicitudPagoDao.save(cxpSolicitudPago);


        }else{
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.ACEPTADA,
                            LogProceso.CXP_SOLICITUDES_PAGOS,
                            cxpSolicitudPago.getId()
                    ),
                    idUsuario
            );
        }

        return new JsonResponse(cxpSolicitudPago.getId(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/cancelar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse cancelar(@RequestBody HashMap<String,Object> jsonBody, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer facturaId = (Integer)jsonBody.get("facturaId");
        String fechaCancelacionString = (String)jsonBody.get("fechaCancelacion");
        List<HashMap<String,Object>> solicitudesPagoServicios = (List<HashMap<String,Object>>) jsonBody.get("solicitudesPagoServicios");
        Date fechaCancelacion = DateUtil.parse(fechaCancelacionString);

        for(HashMap<String,Object> solicitud : solicitudesPagoServicios){
            CXPSolicitudPagoServicio objetoActual = cxpSolicitudPagoServicioDao.findById(((Integer)solicitud.get("id")).intValue());
            try{
                Date fechaUltimaModificacion = null;
                if(solicitud.get("fechaModificacion") != null){
                    fechaUltimaModificacion = DateUtil.parse((String)solicitud.get("fechaModificacion"));
                }
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),fechaUltimaModificacion);
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
        }

        Integer totalCXPSolicitudesPagoDetalles = cxpSolicitudPagoDao.getCountByFacturaId(facturaId);
        if(totalCXPSolicitudesPagoDetalles != null && totalCXPSolicitudesPagoDetalles.intValue() > 0){
            return new JsonResponse("", "No es posible cancelar la factura. Existen solicitudes de pago relacionadas a la factura.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        CXPFactura cxpFactura = cxpFacturaDao.findById(facturaId);

        if(cxpFactura.getEstatusId() == ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA ){
            return new JsonResponse("", "Esta factura ya ha sido cancelada, favor de recargar para ver los cambios.", JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }


        for(HashMap<String,Object> solicitud : solicitudesPagoServicios){
            Integer idSolicitud = ((Integer)solicitud.get("id")).intValue();
            CXPSolicitudPagoServicio cxpSolicitudPagoServicio = cxpSolicitudPagoServicioDao.findById(idSolicitud);
            cxpSolicitudPagoServicio.setEstatusId(ControlesMaestrosMultiples.CMM_CXPSPS_EstadoSolicitudPago.CANCELADA);
            cxpSolicitudPagoServicio.setModificadoPorId(idUsuario);
            cxpSolicitudPagoServicioDao.save(cxpSolicitudPagoServicio);
        }

        cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA);
        cxpFactura.setModificadoPorId(idUsuario);
        cxpFactura.setFechaCancelacion(fechaCancelacion);
        cxpFactura.setMotivoCancelacion((String)jsonBody.get("motivoCancelacion"));

        cxpFacturaDao.save(cxpFactura);

        // Obtenemos  de la factura
        List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( cxpFactura.getId() );
        for( Integer oc: ordenesCompra ){
            OrdenCompra ordenCompra = ordencompraDao.findById(oc);
            ordenCompra.setEstatusId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OC_EstatusOC.RECIBIDA);
            ordencompraDao.save(ordenCompra);

            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.PROGRAMACION_CANCELADO,
                            LogProceso.ORDENES_COMPRA,
                            oc
                    ),
                    idUsuario
            );
        }

        List<CXPSolicitudPagoServicio> solicitudesServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(facturaId);
        for(CXPSolicitudPagoServicio solicitud : solicitudesServicios){
            solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_CXPSPS_EstadoSolicitudPago.CANCELADA);
            solicitud.setModificadoPorId(idUsuario);

            cxpSolicitudPagoServicioDao.save(solicitud);

            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.CANCELADA,
                            LogProceso.SOLICITUD_PAGO,
                            solicitud.getId()
                    ),
                    idUsuario
            );

        }
        Integer cxpSolicitudPagoRHId = cxpSolicitudPagoRHDao.getIdByFactura(facturaId);
        if(cxpSolicitudPagoRHId != null){
            cxpSolicitudPagoRHDao.actualizarActivo(cxpSolicitudPagoRHId,ControlesMaestrosMultiples.CMM_CPXSPRH_EstatusId.CANCELADA);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="/pdf/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
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

    @RequestMapping(value = "/listados/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoCXPSolicitudPagoById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        id = 3378;
        CXPSolicitudPagoAlertaProjection cxpSolicitudPago = null;
        List<Log> historial = null;
        JSONObject foliosMap = null;
        List<CXPFacturaPagoProveedoresProjection> cxpFacturasDocumentos = new ArrayList<>();
        HashMap<String, Object> json = new HashMap<>();

        if (id != null) {
            cxpSolicitudPago = cxpSolicitudPagoDao.findProjectedAlertaById(id);
            historial = logController.getHistorial(id, LogProceso.CXP_SOLICITUDES_PAGOS);

            List<Integer> cxpFacturasIds = new ArrayList<>();
            for(CXPSolicitudPagoDetalleAlertaProjection detalle : cxpSolicitudPago.getDetalles()){
                cxpFacturasIds.add(detalle.getCxpFactura().getId());
            }
            foliosMap = (JSONObject)JSONValue.parse(cxpFacturaDao.getFoliosMapJson(cxpFacturasIds));
            cxpFacturasDocumentos = cxpFacturaDao.findProjectedPagoProveedoresAllByIdIn(cxpFacturasIds);
            List<CXPFacturaBeneficiarioProjection> beneficiarios = cxpFacturaDao.getBeneficiarios(cxpFacturasIds);
            json.put("beneficiarios", beneficiarios);
        }

        MenuPrincipal nodo = menuDao.findByUrl("/app/compras/programacion-pagos");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),cxpSolicitudPago.getId(), com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);

        Boolean alertaEnProceso = false;
        if(alerta != null){
            for ( AlertaDetalle detalle : alerta.getDetalles() ){
                if ( detalle.getUsuarioId().equals(idUsuario) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                    alertaEnProceso = true;
                    break;
                }
            }
        }

        String leyendaAlerta = "";
        if(alertaEnProceso){
            List<Alerta> alertas = alertaDao.findByAlertaCIdAndReferenciaProcesoIdOrderByFechaInicioDesc(config.getId(),cxpSolicitudPago.getId());
            if(alertas.size() > 0){
                Alerta ultimaAlerta = alertas.get(0);
                if(ultimaAlerta.getEstatusAlertaId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.AUTORIZADA)){
                    leyendaAlerta = "Autorizada";
                }else if(ultimaAlerta.getEstatusAlertaId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.RECHAZADA)){
                    leyendaAlerta = "Rechazada";
                }else if(ultimaAlerta.getEstatusAlertaId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_REVISION)){
                    leyendaAlerta = "En revisión";
                }
            }
        }

        json.put("cxpSolicitudPago", cxpSolicitudPago);
        json.put("historial", historial);
        json.put("foliosMap", foliosMap);
        json.put("cxpFacturasDocumentos", cxpFacturasDocumentos);
        json.put("alertaEnProceso", alertaEnProceso);
        json.put("leyendaAlerta", leyendaAlerta);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    /** ALERTAS **/
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/aprobar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse aprobarAlerta(@RequestBody HashMap<String,Object> body, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer id = (Integer)body.get("cxpSolicitudPagoId");
        HashMap<String,Boolean> facturasAprobarIdsMap = (HashMap<String, Boolean>)body.get("facturasAprobarIdsMap");
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Timestamp fechaModificacion = null;
        if(body.get("fechaModificacion") != null){
            fechaModificacion = DateUtil.parseAsTimestamp((String)body.get("fechaModificacion"),"yyyy-MM-dd HH:mm:ss.SSS z");
        }

        MenuPrincipal nodo = menuDao.findByUrl("/app/compras/programacion-pagos");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),id, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        Boolean alertaEnProceso = false;
        if(alerta != null){
            for ( AlertaDetalle detalle : alerta.getDetalles() ){
                if ( detalle.getUsuarioId().equals(idUsuario) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                    alertaEnProceso = true;
                    break;
                }
            }
        }
        if(!alertaEnProceso){
            return new JsonResponse(null,"Esta alerta ya fue procesada. Favor de actualizar antes de continuar.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        CXPSolicitudPago objetoActual = cxpSolicitudPagoDao.findById(id);
        try {
            concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), fechaModificacion);
        } catch (Exception e) {
            return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }

        CXPSolicitudPago solicitudPago = cxpSolicitudPagoDao.findById(id);
        for(CXPSolicitudPagoDetalle detalle : solicitudPago.getDetalles()){

            if(facturasAprobarIdsMap.get(detalle.getCxpFacturaId().toString()) == null || !facturasAprobarIdsMap.get(detalle.getCxpFacturaId().toString())){
                detalle.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.CANCELADA);
                CXPFactura cxpFactura = cxpFacturaDao.findById(detalle.getCxpFacturaId());
                cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO);
                cxpFacturaDao.save(cxpFactura);

                // Obtenemos las Solicitudes de pago
                List<CXPSolicitudPagoServicio> solicitudesServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(detalle.getCxpFacturaId());
                for(CXPSolicitudPagoServicio solicitud : solicitudesServicios){
                    logController.insertaLogUsuario(
                            new Log(solicitudPago.getCodigoSolicitud(),
                                    LogTipo.PAGO_RECHAZADO,
                                    LogProceso.SOLICITUD_PAGO,
                                    solicitud.getId()
                            ),
                            idUsuario
                    );
                }

                // Obtenemos  de la factura
                List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( cxpFactura.getId() );
                for( Integer oc: ordenesCompra ){
                    logController.insertaLogUsuario(
                            new Log(solicitudPago.getCodigoSolicitud(),
                                    LogTipo.PAGO_RECHAZADO,
                                    LogProceso.ORDENES_COMPRA,
                                    oc
                            ),
                            idUsuario
                    );
                }
            }else{
                // Obtenemos las Solicitudes de pago
                List<CXPSolicitudPagoServicio> solicitudesServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(detalle.getCxpFacturaId());
                for(CXPSolicitudPagoServicio solicitud : solicitudesServicios){
                    logController.insertaLogUsuario(
                            new Log(solicitudPago.getCodigoSolicitud(),
                                    LogTipo.PAGO_ACEPTADO,
                                    LogProceso.SOLICITUD_PAGO,
                                    solicitud.getId()
                            ),
                            idUsuario
                    );
                }


                CXPFactura cxpFactura = cxpFacturaDao.findById(detalle.getCxpFacturaId());
                // Obtenemos  de la factura
                List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( cxpFactura.getId() );
                for( Integer oc: ordenesCompra ){
                    logController.insertaLogUsuario(
                            new Log(null,
                                    LogTipo.PAGO_ACEPTADO,
                                    LogProceso.ORDENES_COMPRA,
                                    oc
                            ),
                            idUsuario
                    );
                }
            }
        }
        cxpSolicitudPagoDao.save(solicitudPago);

        logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.AUTORIZADO,
                        LogProceso.CXP_SOLICITUDES_PAGOS,
                        solicitudPago.getId()
                ),
                idUsuario
        );

        actualizarAlerta(id,idUsuario,true, null);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/rechazar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse rechazarAlerta(@RequestBody HashMap<String,Object> body, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer id = (Integer) body.get("id");
        String comentario = (String) body.get("comentario");
        Timestamp fechaModificacion = null;
        if(body.get("fechaModificacion") != null){
            fechaModificacion = DateUtil.parseAsTimestamp((String)body.get("fechaModificacion"),"yyyy-MM-dd HH:mm:ss.SSS z");
        }

        MenuPrincipal nodo = menuDao.findByUrl("/app/compras/programacion-pagos");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),id, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        Boolean alertaEnProceso = false;
        if(alerta != null){
            for ( AlertaDetalle detalle : alerta.getDetalles() ){
                if ( detalle.getUsuarioId().equals(idUsuario) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                    alertaEnProceso = true;
                    break;
                }
            }
        }
        if(!alertaEnProceso){
            return new JsonResponse(null,"Esta alerta ya fue procesada. Favor de actualizar antes de continuar.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        CXPSolicitudPago objetoActual = cxpSolicitudPagoDao.findById(id);
        try {
            concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), fechaModificacion);
        } catch (Exception e) {
            return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }

        actualizarAlerta(id,idUsuario,false, comentario);
        // Obtenemos las cxpFactura
        CXPSolicitudPago solicitudPago = cxpSolicitudPagoDao.findById(id);
        Boolean cambioEstatusCXPFactura = false;
        for(CXPSolicitudPagoDetalle detalle : solicitudPago.getDetalles()){
            CXPFactura cxpFactura = cxpFacturaDao.findById(detalle.getCxpFacturaId());

            // Obtenemos las Solicitudes de pago
            List<CXPSolicitudPagoServicio> solicitudesServicios = cxpSolicitudPagoServicioDao.findByCxpFacturaId(detalle.getCxpFacturaId());
            for(CXPSolicitudPagoServicio solicitud : solicitudesServicios){

                logController.insertaLogUsuario(
                        new Log(solicitudPago.getCodigoSolicitud(),
                                LogTipo.PAGO_RECHAZADO,
                                LogProceso.SOLICITUD_PAGO,
                                solicitud.getId()
                        ),
                        idUsuario
                );
            }

            // Obtenemos  las facturas
            List<Integer> ordenesCompra = cxpFacturaDao.getOrdenesCompa( cxpFactura.getId() );
            for( Integer oc: ordenesCompra ){
                logController.insertaLogUsuario(
                        new Log(null,
                                LogTipo.PAGO_RECHAZADO,
                                LogProceso.ORDENES_COMPRA,
                                oc
                        ),
                        idUsuario
                );
            }

            BigDecimal montoProgramadoFactura = cxpFacturaDao.getMontoProgramado(cxpFactura.getId());
            if(montoProgramadoFactura == null){
                montoProgramadoFactura = BigDecimal.ZERO;
            }
            BigDecimal montoPagadoFactura = cxpFacturaDao.getMontoPagado(cxpFactura.getId());
            if(montoPagadoFactura == null){
                montoPagadoFactura = BigDecimal.ZERO;
            }

            if(montoPagadoFactura.compareTo(BigDecimal.ZERO) == 1){
                cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_PARCIAL);
            }else if(montoPagadoFactura.compareTo(BigDecimal.ZERO) == 0 && cxpFactura.getMontoRegistro().compareTo(montoProgramadoFactura) == 1){
                cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_PARCIAL);
            }else{
                cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO);
                // CXPSolicitudPagoDetalle cxpSolicitudPagoDetalle.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.ACEPTADA);
                detalle.setEstatusId(ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.RECHAZADA);
                cambioEstatusCXPFactura = true;
            }
            cxpFactura = cxpFacturaDao.save(cxpFactura);



        }
        if(cambioEstatusCXPFactura)
            solicitudPago = cxpSolicitudPagoDao.save(solicitudPago);

        logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.RECHAZADO,
                        LogProceso.CXP_SOLICITUDES_PAGOS,
                        solicitudPago.getId()
                ),
                idUsuario
        );

        return new JsonResponse(id, "null", JsonResponse.STATUS_OK);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {

        ArrayList<HashMap<String, Integer>> allProveedores = (ArrayList<HashMap<String, Integer>>) json.get("proveedores");

        ArrayList<Integer> proveedores = null;
        if (allProveedores != null) {
            proveedores = new ArrayList<>();
            for (HashMap<String, Integer> registro : allProveedores) {
                proveedores.add((Integer) registro.get("id"));
            }
        }

        String documento = json.get("documento") != null ? (String) json.get("documento") : null;
        String fecha = json.get("fechaFactura") != null ? (String) json.get("fechaFactura") : null;
        Date dateFecha = isNullorEmpty(fecha) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

        documento = isNullorEmpty(documento) ? null : documento;

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("proveedores", proveedores);
        filtros.put("documento", documento);
        filtros.put("fecha", dateFecha);

        return filtros;
    }

    private void actualizarAlerta(Integer procesoId, Integer usuarioId, Boolean autorizar, String comentario) throws Exception{
        MenuPrincipal nodo = menuDao.findByUrl("/app/compras/programacion-pagos");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),procesoId, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        for ( AlertaDetalle detalle : alerta.getDetalles() ){
            if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                alertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, autorizar,comentario);
                break;
            }
        }
    }
}
