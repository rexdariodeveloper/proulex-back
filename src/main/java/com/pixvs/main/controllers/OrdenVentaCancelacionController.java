package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.*;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.AlumnoAsistencia.AlumnoAsistenciaEditarProjection;
import com.pixvs.main.models.projections.AlumnoExamenCalificacion.AlumnoExamenCalificacionProjection;
import com.pixvs.main.models.projections.OrdenVenta.OrdenVentaCancelacionProjection;
import com.pixvs.main.models.projections.OrdenVenta.OrdenVentaHistorialPVResumenProjection;
import com.pixvs.main.models.projections.OrdenVentaCancelacion.OrdenVentaCancelacionEditarProjection;
import com.pixvs.main.models.projections.OrdenVentaCancelacionDetalle.OrdenVentaCancelacionDetalleAfectarInventarioProjection;
import com.pixvs.main.models.projections.OrdenVentaDetalle.OrdenVentaDetalleHistorialPVResumenProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.services.ProcesadorInventariosService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ArchivoController;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import net.sf.jasperreports.engine.JRException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 25/03/2022.
 */
@RestController
@RequestMapping("/api/v1/orden-venta-cancelacion")
public class OrdenVentaCancelacionController {

    // Daos
    @Autowired
    private OrdenVentaCancelacionDao ordenVentaCancelacionDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private OrdenVentaDao ordenVentaDao;
    @Autowired
    private OrdenVentaDetalleDao ordenVentaDetalleDao;
    @Autowired
    private InscripcionDao inscripcionDao;
    @Autowired
    private InscripcionSinGrupoDao inscripcionSinGrupoDao;
    @Autowired
    private AlumnoGrupoDao alumnoGrupoDao;
    @Autowired
    private AlumnoAsistenciaDao alumnoAsistenciaDao;
    @Autowired
    private AlumnoExamenCalificacionDao alumnoExamenCalificacionDao;
    @Autowired
    private BecaUDGDao becaUDGDao;
    @Autowired
    private OrdenVentaCancelacionDetalleDao ordenVentaCancelacionDetalleDao;
    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    // Services
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private ProcesadorInventariosService procesadorInventariosService;

    // Controllers
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ArchivoController archivoController;

    // Misc
    @Autowired
    private HashId hashId;
    @Autowired
    private Environment environment;

    //*********************//
    //***** Consultas *****//
    //*********************//

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCancelaciones() throws Exception {
        return new JsonResponse(ordenVentaCancelacionDao.findProjectedListadoAllBy(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoOrdenesCompraById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        OrdenVentaCancelacionEditarProjection ordenVentaCancelacion = null;
        OrdenVentaHistorialPVResumenProjection ordenVenta = null;
        List<OrdenVentaDetalleHistorialPVResumenProjection> ovDetalles = new ArrayList<>();
        List<SucursalComboProjection> sucursales = new ArrayList<>();
        List<ControlMaestroMultipleComboSimpleProjection> tiposMovimiento = new ArrayList<>();
        List<ControlMaestroMultipleComboSimpleProjection> motivosDevolucion = new ArrayList<>();
        List<ControlMaestroMultipleComboSimpleProjection> motivosCancelacion = new ArrayList<>();

        if (id != null) {
            ordenVentaCancelacion = ordenVentaCancelacionDao.findProjectedEditarById(id);

            Integer ordenVentaId = ordenVentaCancelacionDao.findOrdenVentaIdById(id);
            Integer sucursalId = ordenVentaCancelacionDao.findSucursalIdById(id);
            sucursales.add(sucursalDao.findProjectedComboById(sucursalId));
            tiposMovimiento.add(ordenVentaCancelacion.getTipoMovimiento());

            if (ordenVentaCancelacion.getMotivoDevolucion() != null) {
                motivosDevolucion.add(ordenVentaCancelacion.getMotivoDevolucion());
            }

            if (ordenVentaCancelacion.getMotivoCancelacion() != null) {
                motivosCancelacion.add(ordenVentaCancelacion.getMotivoCancelacion());
            }

            ordenVenta = ordenVentaDao.findProjectedHistorialPVResumenById(ordenVentaId);
            ovDetalles = ordenVentaDetalleDao.findProjectedHistorialPVResumenByOrdenVentaId(ordenVenta.getId());
        } else {
            sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId);
            tiposMovimiento = controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByValor("CMM_OVC_TipoMovimiento");
            motivosDevolucion = controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByValor(CMM_OVC_MotivoDevolucion.NOMBRE);
            motivosCancelacion = controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByValor(CMM_OVC_MotivoCancelacion.NOMBRE);
        }

        List<ControlMaestroMultipleComboSimpleProjection> tiposDocumentos = controlMaestroMultipleDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByValor(CMM_OVC_TiposDocumento.NOMBRE);

        HashMap<String, Object> json = new HashMap<>();

        json.put("ordenVentaCancelacion", ordenVentaCancelacion);
        json.put("sucursales", sucursales);
        json.put("tiposMovimiento", tiposMovimiento);
        json.put("motivosDevolucion", motivosDevolucion);
        json.put("motivosCancelacion", motivosCancelacion);
        json.put("tiposDocumentos", tiposDocumentos);
        json.put("ordenVenta", ordenVenta);
        json.put("ovDetalles", ovDetalles);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/buscar/nota-venta/{sucursalId}/{codigoOV}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getNotaVenta(@PathVariable Integer sucursalId, @PathVariable String codigoOV, ServletRequest req) throws Exception {
        OrdenVentaCancelacionProjection ov = ordenVentaDao.findOrdenVentaCancelacionProjectedByCodigo(codigoOV);

        if (ov == null) {
            return new JsonResponse(null, "Nota de venta no encontrada.", JsonResponse.STATUS_ERROR_NULL);
        } else if (!ov.getSucursalId().equals(sucursalId)) {
            return new JsonResponse(null, "La nota de venta no se encuentra en la sede seleccionada.", JsonResponse.STATUS_ERROR_NULL);
        } else if (ov.getEstatusId().equals(CMM_OV_Estatus.FACTURADA)) {
            return new JsonResponse(null, "La nota de venta ya fue facturada.", JsonResponse.STATUS_ERROR_NULL);
        } else if (!ov.getEstatusId().equals(CMM_OV_Estatus.PAGADA)) {
            return new JsonResponse(null, "La nota de venta no se ha pagado.", JsonResponse.STATUS_ERROR_NULL);
        }

        HashMap<String, Object> responseBody = new HashMap<>();

        responseBody.put("ordenVenta", ordenVentaDao.findProjectedHistorialPVResumenById(ov.getId()));
        responseBody.put("detalles", ordenVentaDetalleDao.findProjectedHistorialPVResumenByOrdenVentaId(ov.getId()));
        responseBody.put("detallesCanceladosIds", ordenVentaDetalleDao.getIdAllByOrdenVentaIdAndCancelado(ov.getId()));
        responseBody.put("codigoCorteCaja", ov.getSucursalCorteCaja() != null ? ov.getSucursalCorteCaja().getCodigo() : "");

        return new JsonResponse(responseBody, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody OrdenVentaCancelacion ovCancelacion, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        boolean permisoCancelarInscripcionBeca = rolMenuPermisoDao.isPermisoByUsuarioIdAndPermisoId(usuarioId, MenuPrincipalPermisos.CANCELAR_INSCRIPCION_BECA_DESPUES_PERIODO);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // Validamos que la OV esté pagada
        OrdenVenta ov = ordenVentaDao.findByDetalleId(ovCancelacion.getDetalles().get(0).getOrdenVentaDetalleId());

        if (ov.getEstatusId().intValue() != CMM_OV_Estatus.PAGADA) {
            throw new AdvertenciaException("La nota de venta no está pagada.");
        }

        // Validamos que no haya detalles ya cancelados
        for (OrdenVentaCancelacionDetalle detalle : ovCancelacion.getDetalles()) {
            Boolean detalleCancelado = ordenVentaDetalleDao.isOrdenVentaDetalleById(detalle.getOrdenVentaDetalleId());

            if (detalleCancelado != null && detalleCancelado) {
                throw new AdvertenciaException("Algunos detalles seleccionados ya fueron cancelados.");
            }
        }

        // Actualizamos inscripciones a canceladas
        for (OrdenVentaCancelacionDetalle detalle : ovCancelacion.getDetalles()) {
            // Buscamos inscripciones con grupo
            // En teoría solo puede haber 1 inscripcion que coincida, pero por la naturaleza de la consulta se pueden regresar mas inscripciones con otros parámetros
            List<Inscripcion> inscripciones = inscripcionDao.findAllByOrdenVentaDetalleIdAndEstatusIdIn(detalle.getOrdenVentaDetalleId(), Arrays.asList(CMM_INS_Estatus.PAGADA, CMM_INS_Estatus.BAJA));

            for (Inscripcion inscripcion : inscripciones) {
                inscripcion.setEstatusId(CMM_INS_Estatus.CANCELADA);
                inscripcion.setModificadoPorId(usuarioId);

                inscripcionDao.save(inscripcion);

                //Actualizamos las alertas multisede
                inscripcionDao.actualizarAlertasMultisede(inscripcion.getId());

                //Buscamos la relación alumno-grupo
                AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findAllByInscripcionId(inscripcion.getId());

                //Buscar las calificaciones y asistencias
                if (alumnoGrupo != null) {
                    List<AlumnoAsistenciaEditarProjection> asistencias = alumnoAsistenciaDao.findAllByGrupoIdAndAlumnoId(alumnoGrupo.getGrupoId(), alumnoGrupo.getAlumnoId());

                    for (AlumnoAsistenciaEditarProjection asistencia : asistencias) {
                        alumnoAsistenciaDao.delete(alumnoAsistenciaDao.findById(asistencia.getId()));
                    }

                    List<AlumnoExamenCalificacionProjection> calificaciones = alumnoExamenCalificacionDao.findAllByGrupoIdAndAlumnoId(alumnoGrupo.getGrupoId(), alumnoGrupo.getAlumnoId());

                    for (AlumnoExamenCalificacionProjection calificacion : calificaciones) {
                        alumnoExamenCalificacionDao.delete(alumnoExamenCalificacionDao.findById(calificacion.getId()));
                    }

                    alumnoGrupoDao.delete(alumnoGrupo);

                    //Si tiene una beca asociada, reactivarla
                    if (inscripcion.getBecaUDGId() != null) {
                        //Validamos permiso de inscripciones con beca
                        if (!permisoCancelarInscripcionBeca &&
                                formatter.parse(formatter.format(new Timestamp(System.currentTimeMillis()))).after(alumnoGrupo.getGrupo().getFechaFinInscripcionesBecas())) {
                            throw new AdvertenciaException("La inscripción no puede ser canelada fuera del periodo.");
                        }

                        BecaUDG becaUDG = becaUDGDao.findById(inscripcion.getBecaUDGId());
                        becaUDG.setEstatusId(CMM_BECU_Estatus.PENDIENTE_POR_APLICAR);

                        becaUDGDao.save(becaUDG);
                        becaUDGDao.limpiarDatosBecaRH(becaUDG.getId());
                    }
                }
            }

            // Buscamos inscripciones sin grupo
            // En teoría solo puede haber 1 inscripcion que coincida, pero por la naturaleza de la consulta se pueden regresar mas inscripciones con otros parámetros
            List<InscripcionSinGrupo> inscripcionesSinGrupo = inscripcionSinGrupoDao.findAllByOrdenVentaDetalleIdAndEstatusIdIn(detalle.getOrdenVentaDetalleId(), Arrays.asList(CMM_INSSG_Estatus.PAGADA));

            for (InscripcionSinGrupo inscripcion : inscripcionesSinGrupo) {
                inscripcion.setEstatusId(CMM_INSSG_Estatus.CANCELADA);
                inscripcion.setModificadoPorId(usuarioId);

                inscripcionSinGrupoDao.save(inscripcion);

                //Si tiene una beca asociada, reactivarla
                if (inscripcion.getBecaUDGId() != null) {
                    //Validamos permiso de inscripciones con beca
                    if (!permisoCancelarInscripcionBeca) {
                        throw new AdvertenciaException("La inscripción no puede ser canelada fuera del periodo.");
                    }

                    BecaUDG becaUDG = becaUDGDao.findById(inscripcion.getBecaUDGId());
                    becaUDG.setEstatusId(CMM_BECU_Estatus.PENDIENTE_POR_APLICAR);

                    becaUDGDao.save(becaUDG);
                    becaUDGDao.limpiarDatosBecaRH(becaUDG.getId());
                }
            }
        }

        String prefijoAutonumerico = "";
        ovCancelacion.setTipoMovimientoId(ovCancelacion.getTipoMovimiento().getId());

        if (ovCancelacion.getTipoMovimientoId() == CMM_OVC_TipoMovimiento.DEVOLUCION) {
            prefijoAutonumerico = "ND";
            ovCancelacion.setFechaDevolucion(new Date());
            ovCancelacion.setMotivoDevolucionId(ovCancelacion.getMotivoDevolucion().getId());
            ovCancelacion.setFechaCancelacion(null);
            ovCancelacion.setMotivoCancelacionId(null);
        } else if (ovCancelacion.getTipoMovimientoId() == CMM_OVC_TipoMovimiento.CANCELACION) {
            prefijoAutonumerico = "NC";
            ovCancelacion.setFechaDevolucion(null);
            ovCancelacion.setMotivoDevolucionId(null);
            ovCancelacion.setFechaCancelacion(new Date());
            ovCancelacion.setMotivoCancelacionId(ovCancelacion.getMotivoCancelacion().getId());
        }

        if (ovCancelacion.getId() == null) {
            ovCancelacion.setCreadoPorId(usuarioId);
            ovCancelacion.setEstatusId(CMM_OVC_Estatus.APROBADA);
            ovCancelacion.setTipoCancelacionId(CMM_OVC_TiposCancelacion._100);
            ovCancelacion.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo(prefijoAutonumerico));
        } else {
            OrdenVentaCancelacion objetoActual = ordenVentaCancelacionDao.findById(ovCancelacion.getId().intValue());

            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), ovCancelacion.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }

            ovCancelacion.setModificadoPorId(usuarioId);
            ovCancelacion.setEstatusId(objetoActual.getEstatusId());
        }

        ovCancelacion = ordenVentaCancelacionDao.save(ovCancelacion);

        //Afectamos el Inventario
        List<OrdenVentaCancelacionDetalleAfectarInventarioProjection> detallesAfectar = ordenVentaCancelacionDetalleDao.findProjecctedAfectarInventarioByCancelacionId(ovCancelacion.getId());

        if (detallesAfectar != null && !detallesAfectar.isEmpty()) {
            actualizarInventario(detallesAfectar, ovCancelacion.getTipoMovimientoId());
        }

        return new JsonResponse(ovCancelacion.getId(), null, JsonResponse.STATUS_OK);
    }

    @ResponseBody
    @RequestMapping(value = "/descargar/documentos", method = RequestMethod.POST)
    public ResponseEntity<Resource> descargarDocumentos(@RequestBody HashMap<String, Object> body, Boolean publico) throws Exception {
        String ordenVentaCancelacionIdStr = (String) body.get("ordenVentaCancelacionId");
        Integer ordenVentaCancelacionId = hashId.decode(ordenVentaCancelacionIdStr);

        OrdenVentaCancelacion ordenVentaCancelacion = ordenVentaCancelacionDao.findById(ordenVentaCancelacionId);

        List<String> documentosHashIds = new ArrayList<>();

        for (OrdenVentaCancelacionArchivo documento : ordenVentaCancelacion.getArchivos()) {
            documentosHashIds.add(hashId.encode(documento.getArchivoId()));
        }

        HashMap<String, Object> requestBodyArchivos = new HashMap<>();
        requestBodyArchivos.put("idsArchivos", documentosHashIds);
        requestBodyArchivos.put("nombreZip", "documentos.zip");

        return archivoController.downloadFiles(requestBodyArchivos, null);
    }

    @RequestMapping(value = "/imprimir/formato", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public void imprimirFormato(@RequestBody HashMap<String, Object> filtros, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        String reportPath = "/modulos/ordenes-venta/Cancelacion.jasper";
        Integer ordenVentaCancelacionId = (Integer) filtros.get("ordenVentaCancelacionId");


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("id", ordenVentaCancelacionId);

        InputStream reporte = reporteService.generarJasperReport(reportPath, parameters, ReporteServiceImpl.output.PDF, true);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=solicitud_de_cancelacion.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value = "/actualizar/inventario", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse actualizarInventario(ServletRequest req) throws Exception {
        return new JsonResponse(null, actualizarInventario(null, CMM_OVC_TipoMovimiento.CANCELACION) + " movimientos creados", JsonResponse.STATUS_OK);
    }

    private int actualizarInventario(List<OrdenVentaCancelacionDetalleAfectarInventarioProjection> detallesAfectar, int tipoMovimientoId) throws Exception {
        if (detallesAfectar == null) {
            detallesAfectar = ordenVentaCancelacionDetalleDao.findProjecctedAfectarInventarioAllByPendientes();
        }

        int conteoRegistros = 0;

        for (OrdenVentaCancelacionDetalleAfectarInventarioProjection detalleAfectar : detallesAfectar) {
            List<Almacen> almacenes = almacenDao.findBySucursalIdAndActivoTrue(detalleAfectar.getSucursalId());

            if (almacenes.size() != 1) {
                continue;
            }

            Localidad localidad = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacenes.get(0).getId());

            procesadorInventariosService.procesaMovimiento(
                    detalleAfectar.getArticuloId(),
                    localidad.getId(),
                    detalleAfectar.getCantidad(),
                    (tipoMovimientoId == CMM_OVC_TipoMovimiento.DEVOLUCION ? "Devolución" : "Cancelación") + " de nota de venta " + detalleAfectar.getCodigoOV(),
                    detalleAfectar.getCodigoOV(),
                    detalleAfectar.getId(),
                    detalleAfectar.getPrecio(),
                    CMM_IM_TipoMovimiento.CANCELACION_DE_NOTA_DE_VENTA,
                    detalleAfectar.getUsuarioId()
            );

            conteoRegistros++;
        }

        return conteoRegistros;
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {
        String query = "SELECT * from [VW_OrdenesVentaCancelaciones_ExcelListado]";
        String[] columnas = new String[]{"Tipo movimiento", "Folio", "Nota de venta", "Sede", "Fecha movimiento", "Importe a reembolsar", "Usuario", "Estatus"};
        String[] totales = new String[]{ "Importe a reembolsar" };
        String[] columnasMoneda = new String[]{ "Importe a reembolsar" };

        excelController.downloadXlsx(response, "cancelacion_nota_venta", query, columnas, totales, columnasMoneda, null, "Cancelaciones");
    }
}