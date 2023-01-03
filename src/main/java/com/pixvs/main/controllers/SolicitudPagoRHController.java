package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.AlertasConfiguraciones;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.mapeos.Monedas;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHEditarProjection;
import com.pixvs.main.models.projections.CXPSolicitudPagoRH.CXPSolicitudPagoRHListadoProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorEditarProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.services.ArchivoXMLService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.storage.StorageService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/solicitud-pago-rh")
public class SolicitudPagoRHController {

    @Autowired
    private OrdenCompraDao ordenCompraDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ServicioDao servicioDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private AlertasDetalleDao alertaDetalleDao;
    @Autowired
    private AlertasDao alertaDao;
    @Autowired
    private CXPFacturaDao cxpFacturaDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private DepartamentoMainDao departamentoDao;
    @Autowired
    private UsuarioDatosAdicionalesDao usuarioDatosAdicionalesDao;

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
    private LogController logController;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private ProcesadorAlertasService alertasService;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AlertaConfigDao alertaConfigDao;
    @Autowired
    private CXPSolicitudPagoRHDao cxpSolicitudPagoRHDao;
    @Autowired
    private CXPSolicitudPagoRHIncapacidadDao cxpSolicitudPagoRHIncapacidadDao;
    @Autowired
    private CXPSolicitudPagoRHRetiroCajaAhorroDao cxpSolicitudPagoRHRetiroCajaAhorroDao;
    @Autowired
    private AlertaConfigEtapaDao alertaConfigEtapaDao;
    @Autowired
    private AlertaConfigEtapaDao configEtapaDao;
    @Autowired
    private Environment environment;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getSolicitudesPago(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);

        List<CXPSolicitudPagoRHListadoProjection> solicitudes = new ArrayList<>();

        if(rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.VISUALIZAR_TODOS_LOS_REGISTROS_SolicitudesPago)){
            solicitudes = cxpSolicitudPagoRHDao.getListadoSolicitudes();
        }else{
            List<Integer> sucursalesPermisoUsuarioIds = sucursalDao.findIdsByUsuarioPermisosId(idUsuario);

            List<Integer> departamentosPermisoIds = departamentoDao.fn_getJerarquiaDepartamentosUsuario(idUsuario);
            List<Integer> usuariosIds = usuarioDatosAdicionalesDao.getUsuariosIdsByDepartamentoIdIn(departamentosPermisoIds);

            if(sucursalesPermisoUsuarioIds.size() > 0){
                solicitudes = cxpSolicitudPagoRHDao.getListadoSolicitudes(sucursalesPermisoUsuarioIds,usuariosIds);
            }
        }

        return new JsonResponse(solicitudes, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody CXPSolicitudPagoRH solicitud, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        solicitud.setCreadoPorId(idUsuario);
        solicitud.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("SPRH"));

        /*if(solicitud.getFactura().getUuid() != null){
            Boolean existeUUID = cxpFacturaDao.existsByUuidAndEstatusIdNotIn(solicitud.getFactura().getUuid(),Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.BORRADO,ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.CANCELADA));
            if(existeUUID){
                return new JsonResponse(null, "Ya existe una factura relacionada con el mismo UUID", JsonResponse.STATUS_ERROR_REGISTRO_DUPLICADO);
            }
        }*/
        CXPFactura factura = new CXPFactura();
        factura.setUuid(UUID.randomUUID().toString());
        factura.setCodigoRegistro("");
        factura.setTipoRegistroId(ControlesMaestrosMultiples.CMM_CXPF_TipoRegistro.FACTURA_CXP);
        factura.setFechaRegistro(new Date(System.currentTimeMillis()));
        factura.setFechaReciboRegistro(new Date(System.currentTimeMillis()));
        factura.setEstatusId(2000116);
        factura.setDiasCredito(0);
        factura.setComentarios("Pago RH");
        factura.setMonedaId(Monedas.PESO);
        factura.setParidadOficial(BigDecimal.valueOf(1));

        CXPFacturaDetalle facturaDetalle = new CXPFacturaDetalle();
        facturaDetalle.setReciboId(null);
        facturaDetalle.setNumeroLinea(1);
        facturaDetalle.setDescripcion("Pago solicitud RH");
        facturaDetalle.setCantidad(BigDecimal.valueOf(1));
        facturaDetalle.setTipoRegistroId(ControlesMaestrosMultiples.CMM_CXPF_TipoRegistro.FACTURA_CXP);
        facturaDetalle.setDescuento(BigDecimal.valueOf(0));
        factura.setDetalles(new ArrayList<>());
        factura.getDetalles().add(facturaDetalle);

        if(solicitud.getSucursal()!=null){
            solicitud.setSucursalId(solicitud.getSucursal().getId());
        }
        if(solicitud.getEmpleado()!=null){
            solicitud.setEmpleadoId(solicitud.getEmpleado().getId());
        }
        if(solicitud.getTipoPago()!=null){
            solicitud.setTipoPagoId(solicitud.getTipoPago().getId());
        }

        String tipoPago="";
        switch (solicitud.getTipoPago().getId()){
            case 2000356:
                tipoPago="Retiro de caja de ahorro";

                if(solicitud.getCajaAhorro().get(0).getTipoRetiro()!=null){
                    solicitud.getCajaAhorro().get(0).setTipoRetiroId(solicitud.getCajaAhorro().get(0).getTipoRetiro().getId());
                }
                factura.setMontoRegistro(solicitud.getCajaAhorro().get(0).getCantidadRetirar());
                facturaDetalle.setPrecioUnitario(solicitud.getCajaAhorro().get(0).getCantidadRetirar());
                //solicitud.setCajaAhorro(temp);
                break;
            case 2000357:
                tipoPago="Pensión alimenticia";
                factura.setMontoRegistro(solicitud.getMonto());
                facturaDetalle.setPrecioUnitario(solicitud.getMonto());
                break;
            case 2000358:
                tipoPago="Incapacidad de personal";
                BigDecimal totalIncapacidad = BigDecimal.ZERO;
                BigDecimal deducciones = BigDecimal.ZERO;
                for(Integer i=0;i<solicitud.getIncapacidad().get(0).getDetalles().size();i++){
                    if(solicitud.getIncapacidad().get(0).getDetalles().get(i).getTipo()!=null){
                        solicitud.getIncapacidad().get(0).getDetalles().get(i).setTipoId(solicitud.getIncapacidad().get(0).getDetalles().get(i).getTipo().getId());
                        BigDecimal salarioDiario = solicitud.getIncapacidad().get(0).getDetalles().get(i).getSalarioDiario();
                        BigDecimal porcentaje = BigDecimal.valueOf(solicitud.getIncapacidad().get(0).getDetalles().get(i).getPorcentaje()).divide(BigDecimal.valueOf(100));
                        Integer dias = solicitud.getIncapacidad().get(0).getDetalles().get(i).getDias();
                        totalIncapacidad = totalIncapacidad.add(salarioDiario.multiply(porcentaje).multiply(BigDecimal.valueOf(dias)));
                    }
                    if(solicitud.getIncapacidad().get(0).getDetalles().get(i).getTipoMovimiento()!=null){
                        solicitud.getIncapacidad().get(0).getDetalles().get(i).setTipoMovimientoId(solicitud.getIncapacidad().get(0).getDetalles().get(i).getTipoMovimiento().getId());
                        solicitud.getIncapacidad().get(0).getDetalles().get(i).setTipoMovimiento(null);
                        deducciones = deducciones.add(solicitud.getIncapacidad().get(0).getDetalles().get(i).getSalarioDiario());
                    }
                }
                factura.setMontoRegistro(totalIncapacidad.subtract(deducciones));
                facturaDetalle.setPrecioUnitario(totalIncapacidad.subtract(deducciones));
                break;
            case 2000359:
                tipoPago="Pago a becario";
                factura.setMontoRegistro(solicitud.getMonto());
                facturaDetalle.setPrecioUnitario(solicitud.getMonto());
                break;
            default:
                break;
        }

        ProveedorEditarProjection proveedor = proveedorDao.findByNombre(tipoPago);

        factura.setCreadoPorId(idUsuario);
        factura.setProveedorId(proveedor.getId());
        factura = cxpFacturaDao.save(factura);
        solicitud.setFacturaId(factura.getId());

        Integer sucursalId = solicitud.getSucursalId();
        Sucursal sucursal = sucursalDao.findById(sucursalId);
        solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_CPXSPRH_EstatusId.ACEPTADA);
        solicitud = cxpSolicitudPagoRHDao.save(solicitud);
        Boolean requiereAlerta = alertasService.validarAutorizacion(AlertasConfiguraciones.SOLICITUDES_PAGO_RH_VALIDACION, solicitud.getId(), solicitud.getCodigo(), "Solicitud de pago rh", sucursalId, idUsuario, sucursal.getNombre());
        Integer logTipo = LogTipo.ACEPTADA;
        if(requiereAlerta){
            logTipo = LogTipo.POR_AUTORIZAR;
            solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_CPXSPRH_EstatusId.POR_AUTORIZAR);
            solicitud = cxpSolicitudPagoRHDao.save(solicitud);
        }



        return new JsonResponse(solicitud.getCodigo(), null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idSolicitud}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idSolicitud) throws SQLException {
        CXPSolicitudPagoRHEditarProjection buscarYaProgramados = cxpSolicitudPagoRHDao.buscarPagosYaProgramados(hashId.decode(idSolicitud));
        if(buscarYaProgramados != null){
            return new JsonResponse(null,"No se permite borrar. Solicitud en proceso de pago",JsonResponse.STATUS_ERROR_PROBLEMA);
        }
        else {
            int actualizado = cxpSolicitudPagoRHDao.actualizarActivo(hashId.decode(idSolicitud), ControlesMaestrosMultiples.CMM_CXPS_EstadoSolicitudPago.CANCELADA);
            return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
        }
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idOrdenCompra) throws SQLException {

        CXPSolicitudPagoRHEditarProjection cxpSolicitudPagoRH = cxpSolicitudPagoRHDao.findEditarProjectionById(idOrdenCompra);

        return new JsonResponse(cxpSolicitudPagoRH, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();

        //List<ProveedorComboVistaProjection> proveedores = proveedorDao.findProjectedComboAllBy();
        List<ControlMaestroMultipleComboProjection> tipoSolicitudes = controlMaestroMultipleDao.findAllByControl("CMM_CPXSPRH_TipoPagoId");
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<ControlMaestroMultipleComboProjection> tipoRetiros = controlMaestroMultipleDao.findAllByControl("CMM_CPXSPRHID_TipoRetiroId");
        List<EmpleadoComboProjection> empleados = empleadoDao.findAllByEstatusIdNotIn(Arrays.asList(ControlesMaestrosMultiples.CMM_EMP_Estatus.BORRADO));
        List<ControlMaestroMultipleComboProjection> incapacidadMovimientos = controlMaestroMultipleDao.findAllByControl("CMM_CPXSPRHID_TipoMovimientoId");
        List<ControlMaestroMultipleComboProjection> incapacidadTipo = controlMaestroMultipleDao.findAllByControl("CMM_CPXSPRHID_TipoId");

        if(id != null){
            json.put("solicitud",cxpSolicitudPagoRHDao.findEditarProjectionById(id));
            //json.put("historial", logController.getHistorial(id, LogProceso.SOLICITUD_PAGO));
            //json.put("proveedores", proveedorDao.findProjectedComboVistaAllBy());
        }

        json.put("sucursales", sucursales);
        json.put("tipoSolicitudes", tipoSolicitudes);
        json.put("tipoRetiros", tipoRetiros);
        json.put("empleados", empleados);
        json.put("incapacidadMovimientos", incapacidadMovimientos);
        json.put("incapacidadTipo", incapacidadTipo);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT codigo, fechaCreacion, nombre, tipoPago, monto, estatus, usuarioCreador, sede from [VW_LISTADO_SOLICITUDES_PAGO_RH]";
        String[] alColumnas = new String[]{"Código", "Fecha de creación", "Nombre", "Tipo de pago", "Monto", "Estatus", "Usuario creador","Sede"};

        excelController.downloadXlsx(response, "solicitudes-pago-rh", query, alColumnas, null, "Solicitudes RH");
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
        MenuPrincipal nodo = menuDao.findByUrl("/app/compras/solicitud-pago-rh");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),procesoId, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        for ( AlertaDetalle detalle : alerta.getDetalles() ){
            if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                alertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, autorizar,comentario);
            }
        }
    }

}


