package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.dao.DepartamentoMainDao;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.*;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaVerSolicitudPagoProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoServicio.CXPSolicitudPagoServicioEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoServicio.CXPSolicitudPagoServicioListadoProjection;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraEditarProjection;
import com.pixvs.main.models.projections.Servicio.ServicioComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.main.services.ArchivoXMLService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.storage.StorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/solicitud-pago")
public class SolicitudPagoController {

    @Autowired
    private OrdenCompraDao ordenCompraDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ServicioDao servicioDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private UnidadMedidaDao unidadMedidaDao;
    @Autowired
    private CXPSolicitudPagoServicioDao cxpSolicitudPagoServicioDao;
    @Autowired
    private CXPFacturaDao facturaDao;
    @Autowired
    private CXPFacturaDao cxpFacturaDao;
    @Autowired
    private AlertasDetalleDao alertaDetalleDao;
    @Autowired
    private AlertasDao alertaDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AlertaConfigDao alertaConfigDao;
    @Autowired
    private AlertaConfigEtapaDao alertaConfigEtapaDao;
    @Autowired
    private AlertaConfigEtapaDao configEtapaDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private DepartamentoMainDao departamentoDao;
    @Autowired
    private UsuarioDatosAdicionalesDao usuarioDatosAdicionalesDao;
    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private ArchivoXMLService archivoXMLService;
    @Autowired
    private ProcesadorAlertasService alertasService;

    @Autowired
    private LogController logController;

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getSolicitudesPago(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);

        List<CXPSolicitudPagoServicioListadoProjection> solicitudes = new ArrayList<>();

        if(rolDao.tienePermiso(usuario.getRolId(),MenuPrincipalPermisos.VISUALIZAR_TODOS_LOS_REGISTROS_SolicitudesPago)){
            solicitudes = cxpSolicitudPagoServicioDao.getListadoSolicitudes();
        }else{
            List<Integer> sucursalesPermisoUsuarioIds = sucursalDao.findIdsByUsuarioPermisosId(idUsuario);

            List<Integer> departamentosPermisoIds = departamentoDao.fn_getJerarquiaDepartamentosUsuario(idUsuario);
            List<Integer> usuariosIds = usuarioDatosAdicionalesDao.getUsuariosIdsByDepartamentoIdIn(departamentosPermisoIds);

            if(sucursalesPermisoUsuarioIds.size() > 0 && usuariosIds.size() > 0){
                solicitudes = cxpSolicitudPagoServicioDao.getListadoSolicitudes(sucursalesPermisoUsuarioIds,usuariosIds);
            }
        }

        return new JsonResponse(solicitudes, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody CXPSolicitudPagoServicio solicitud, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        boolean esNuevo = solicitud.getId() == null;

        if(solicitud.getId() == null){
            solicitud.setCreadoPorId(idUsuario);
            solicitud.setCodigoSolicitudPagoServicio(autonumericoService.getSiguienteAutonumericoByPrefijo("SP"));

            for(CXPSolicitudPagoServicioDetalle detalle : solicitud.getDetalles()){
                if(detalle.getCxpFactura().getUuid() != null){
                    Boolean existeUUID = cxpFacturaDao.existsByUuidAndEstatusIdNotIn(detalle.getCxpFactura().getUuid(),Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO,ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA));
                    if(existeUUID){
                        return new JsonResponse(null, "Ya existe una factura relacionada con el mismo UUID", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
                    }
                }
            }

            for(CXPSolicitudPagoServicioDetalle detalle : solicitud.getDetalles()){
                detalle.getCxpFactura().setCreadoPorId(idUsuario);
                for(CXPFacturaDetalle facturaDetalle : detalle.getCxpFactura().getDetalles()){
                    Articulo articulo = articuloDao.findById(facturaDetalle.getArticuloId());
                    facturaDetalle.setUnidadMedidaId(articulo.getUnidadMedidaInventarioId());
                    facturaDetalle.setCantidad(BigDecimal.ONE);
                    facturaDetalle.setPrecioUnitario(detalle.getCxpFactura().getMontoRegistro());
                }
                CXPFactura factura = facturaDao.save(detalle.getCxpFactura());
                detalle.setCxpFacturaId(factura.getId());
            }

            solicitud = cxpSolicitudPagoServicioDao.save(solicitud);
        }else{
            for(CXPSolicitudPagoServicioDetalle detalle : solicitud.getDetalles()){
                if(detalle.getCxpFactura().getUuid() != null){
                    Boolean existeUUID = cxpFacturaDao.existsByUuidAndEstatusIdNotInAndIdNot(detalle.getCxpFactura().getUuid(),Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO,ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA),detalle.getCxpFacturaId());
                    if(existeUUID){
                        return new JsonResponse(null, "Ya existe una factura relacionada con el mismo UUID", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
                    }
                }
            }

            CXPSolicitudPagoServicio solicitudBD = cxpSolicitudPagoServicioDao.findById(solicitud.getId());
            solicitudBD.getDetalles().get(0).getCxpFactura().setFacturaXMLId(solicitud.getDetalles().get(0).getCxpFactura().getFacturaXMLId());
            solicitudBD.getDetalles().get(0).getCxpFactura().setFacturaPDFId(solicitud.getDetalles().get(0).getCxpFactura().getFacturaPDFId());
            solicitudBD.getDetalles().get(0).getCxpFactura().setUuid(solicitud.getDetalles().get(0).getCxpFactura().getUuid());

            solicitud = cxpSolicitudPagoServicioDao.save(solicitudBD);
        }

        if(esNuevo){
            Integer sucursalId = solicitud.getSucursalId();
            Sucursal sucursal = sucursalDao.findById(sucursalId);
            Boolean requiereAlerta = alertasService.validarAutorizacion(AlertasConfiguraciones.SOLICITUDES_PAGO_VALIDACION, solicitud.getId(), solicitud.getCodigoSolicitudPagoServicio(), "Solicitud de pago", sucursalId, idUsuario, sucursal.getNombre());
            Integer logTipo = LogTipo.ACEPTADA;
            solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_CXPSPS_EstadoSolicitudPago.ACEPTADA);
            if(requiereAlerta){
                logTipo = LogTipo.POR_AUTORIZAR;
                solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_CXPSPS_EstadoSolicitudPago.POR_AUTORIZAR);
            }

            solicitud = cxpSolicitudPagoServicioDao.save(solicitud);


        }else{
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.MODIFICADO,
                            LogProceso.SOLICITUD_PAGO,
                            solicitud.getId()
                    ),
                    idUsuario
            );
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idOrdenCompra) throws SQLException {

        OrdenCompraEditarProjection ordenCompra = ordenCompraDao.findProjectedEditarById(idOrdenCompra);

        return new JsonResponse(ordenCompra, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();

        Boolean permiteReemplazarDocumentos = true;

        List<ControlMaestroMultipleComboProjection> tipoSolicitudes = controlMaestroMultipleDao.findAllByControl("CMM_CXPS_TipoSolicitud");
        List<ControlMaestroMultipleComboProjection> terminosPago = controlMaestroMultipleDao.findAllByControl("CMM_CXPF_TerminosPago");
        List<MonedaComboProjection> monedas;

        List<UnidadMedidaComboProjection> unidadesMedida = unidadMedidaDao.findProjectedComboAllByActivoTrue();

        if(id != null){
            CXPSolicitudPagoServicioEditarProjection solicitud = cxpSolicitudPagoServicioDao.findEditarProjectionById(id);
            json.put("solicitud",solicitud);
            CXPFacturaVerSolicitudPagoProjection cxpFactura = solicitud.getDetalles().get(0).getCxpFactura();
//            CXPFacturaVerSolicitudPagoProjection cxpFactura = cxpFacturaDao.findProjectedVerSolicitudPagoById(solicitud.getDetalles().get(0).getCxpFacturaId());
//            CXPFactura cxpFactura = cxpFacturaDao.findById(solicitud.getDetalles().get(0).getCxpFacturaId());
            json.put("servicios", servicioDao.findProjectedComboByActivoTrueAndArticuloId(cxpFactura.getDetalles().get(0).getArticuloId()));
            json.put("proveedores", Arrays.asList(cxpFactura.getProveedor()));
            json.put("sucursales", Arrays.asList(solicitud.getSucursal()));
            json.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndId(cxpFactura.getDetalles().get(0).getArticuloId()));
            json.put("historial", logController.getHistorial(id, LogProceso.SOLICITUD_PAGO));
            Integer totalCXPSolicitudesPagos =  facturaDao.getSolicitudCancelable(id);
            json.put("cancelable", ( (solicitud.getEstatusId() == ControlesMaestrosMultiples.CMM_CXPSPS_EstadoSolicitudPago.CANCELADA || totalCXPSolicitudesPagos > 0) ? false: true) );

            CXPSolicitudPagoServicio cxpSolicitudPagoServicio = cxpSolicitudPagoServicioDao.findById(id);
//            json.put("facturaPDF", archivoDao.findById(cxpSolicitudPagoServicio.getDetalles().get(0).getCxpFactura().getFacturaPDFId()));
//            json.put("facturaXML", archivoDao.findById(cxpSolicitudPagoServicio.getDetalles().get(0).getCxpFactura().getFacturaXMLId()));
            json.put("facturaPDF", solicitud.getFacturaPDF());
            json.put("facturaXML", solicitud.getFacturaXML());
            permiteReemplazarDocumentos = rolDao.tienePermiso(idUsuario, MenuPrincipalPermisos.SOLICITUDES_PAGOS_REEMPLAZAR_DOCUMENTOS);
            monedas = Arrays.asList(solicitud.getDetalles().get(0).getCxpFactura().getMoneda());
        }else{
            monedas = monedaDao.findProjectedComboAllByActivoTrue();

            json.put("proveedores", proveedorDao.findProjectedComboVistaAllByActivoTrue());
            json.put("servicios", servicioDao.findProjectedComboAllByActivoTrue());
            json.put("sucursales", sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario));
            json.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloId(ArticulosTipos.SISTEMA));
        }

        json.put("tipoSolicitudes", tipoSolicitudes);
        json.put("terminosPago", terminosPago);
        json.put("monedas", monedas);

        json.put("unidadesMedida", unidadesMedida);

        json.put("permiteReemplazarDocumentos", permiteReemplazarDocumentos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/cancelar/{id}"}, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse cancelar(@PathVariable(required = true) String id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer idSolicitud = hashId.decode(id);

        CXPSolicitudPagoServicio cxpSolicitudPagoServicio = cxpSolicitudPagoServicioDao.findById(idSolicitud);
        Integer totalCXPSolicitudesPagos =  facturaDao.getSolicitudCancelable(idSolicitud);
        if(totalCXPSolicitudesPagos > 0)
            return new JsonResponse(null, ("No es posible realizar la cancelación, existe "+ totalCXPSolicitudesPagos +" proceso(s) con esta solicitud."), JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        else{
            cxpSolicitudPagoServicio.setEstatusId(ControlesMaestrosMultiples.CMM_CXPSPS_EstadoSolicitudPago.CANCELADA);
            cxpSolicitudPagoServicio.setModificadoPorId(idUsuario);
            cxpSolicitudPagoServicioDao.save(cxpSolicitudPagoServicio);
        }
        // Obtenemos el id de la factura
        Integer facturaId = cxpSolicitudPagoServicio.getDetalles().get(0).getCxpFacturaId();
        CXPFactura cxpFactura = cxpFacturaDao.findById(facturaId);
        if(cxpFactura.getEstatusId() == ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA ){
            return new JsonResponse("", "Esta factura ya ha sido cancelada, favor de recargar para ver los cambios.", JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }else{
            cxpFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA);
            cxpFactura.setModificadoPorId(idUsuario);
            cxpFactura.setFechaCancelacion(new Date());
            cxpFacturaDao.save(cxpFactura);
        }

        logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.CANCELADA,
                        LogProceso.SOLICITUD_PAGO,
                        idSolicitud
                ),
                idUsuario
        );

        return new JsonResponse(null, "Cancelación exitosa", JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_SOLICITUD_PAGO]";
        String[] alColumnas = new String[]{"id", "codigoSolicitud", "fechaSolicitud", "fechavencimiento", "cede", "solicitud", "servicio", "usuario"};

        excelController.downloadXlsx(response, "solicitudes-pago", query, alColumnas, null);
    }

    @Transactional
    @RequestMapping(value = "/upload/xml", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse fileUploadXML(@RequestParam("file") MultipartFile file, @RequestParam("idEstructuraArchivo") Integer idEstructuraArchivo, @RequestParam("subcarpeta") String subcarpeta, @RequestParam("idTipo") Integer idTipo, @RequestParam("publico") Boolean publico, @RequestParam("activo") Boolean activo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        idTipo = idTipo > 0 ? idTipo : null;
        Archivo archivo = storageService.store(file, idUsuario, idEstructuraArchivo, subcarpeta, idTipo, publico, activo);

        JsonFacturaXML jsonFacturaXML = archivoXMLService.getDatosFacturaXML(archivo.getId());

        Boolean existeUUID = cxpFacturaDao.existsByUuidAndEstatusIdNotIn(jsonFacturaXML.getDatosFactura().getUuid().toString(),Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO,ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA));
        if(existeUUID){
            return new JsonResponse(null, "Ya existe una factura relacionada con el mismo UUID", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
        }

        return new JsonResponse(jsonFacturaXML,null,JsonResponse.STATUS_OK);
    }

    /** ALERTAS **/
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/aprobar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse aprobarAlerta(@RequestBody Integer id, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        actualizarAlerta(id,idUsuario,true, null);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/rechazar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse rechazarAlerta(@RequestBody HashMap<String,Object> body, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer id = (Integer) body.get("id");
        String comentario = (String) body.get("comentario");
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        actualizarAlerta(id,idUsuario,false, comentario);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    private void actualizarAlerta(Integer procesoId, Integer usuarioId, Boolean autorizar, String comentario) throws Exception{
        MenuPrincipal nodo = menuDao.findByUrl("/app/compras/solicitud-pago");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),procesoId, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        for ( AlertaDetalle detalle : alerta.getDetalles() ){
            if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                alertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, autorizar,comentario);
            }
        }
    }

}


