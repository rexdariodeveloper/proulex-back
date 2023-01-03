package com.pixvs.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.AlumnoAsistencia.ReporteAsistenciasProjection;
import com.pixvs.main.models.projections.AlumnoAsistencia.ReporteCalificacionesProjection;
import com.pixvs.main.models.projections.AlumnoGrupo.AlumnoGrupoBoletaProjection;
import com.pixvs.main.models.projections.AlumnoGrupo.AlumnoGrupoCalificacionesProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.*;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import static org.apache.poi.ss.util.CellUtil.createCell;

@RestController
@RequestMapping("/api/v1/captura_calificaciones")
public class CapturaCalificacionesController {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private InscripcionDao inscripcionDao;
    @Autowired
    private AlumnoExamenCalificacionDao alumnoExamenCalificacionDao;
    @Autowired
    private LogController logController;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private PAActividadEvaluacionDao paActividadEvaluacionDao;
    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;
    @Autowired
    private UsuarioDatosAdicionalesDao usuarioDatosAdicionalesDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;
    @Autowired
    private AlumnoGrupoDao alumnoGrupoDao;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private Environment environment;
    @Autowired
    private HashId hashId;
    @Autowired
    private ProgramaGrupoController programaGrupoController;
    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;
    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private PACicloDao paCicloDao;
    @Autowired
    private AlumnoAsistenciaDao alumnoAsistenciaDao;
    @Autowired
    private ExcelController excelController;

    @Autowired
    SucursalDao sucursalDao;

    @Autowired
    PAModalidadDao modalidadDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCursos(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Boolean> permisos = new HashMap<>();

        if (rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.VISUALIZAR_GRUPOS_SEDE_CALIFICACIONES)) {
            List<Integer> sedes = new ArrayList<>();

            for (Almacen almacen : usuario.getAlmacenes()) {
                if (!sedes.contains(almacen.getSucursalId())) {
                    sedes.add(almacen.getSucursalId());
                }
            }

            List<Integer> gruposIds = new ArrayList<>();

            if (usuarioDatosAdicionalesDao.getUsuarioEsResponsableCentrosUniversitarios(usuarioId)) {
                ControlMaestroMultiple cmmSucursalJOBS = controlMaestroMultipleDao.findCMMById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSId.CMM_SUC_SucursalJOBSId);
                Integer sucursalJOBSId = Integer.parseInt(cmmSucursalJOBS.getValor());

                for (Integer sedeId : sedes) {
                    if (sedeId.intValue() == sucursalJOBSId.intValue()) {
                        gruposIds.addAll(programaGrupoDao.findIdAllByCentroUniversitarioResponsableId(usuarioId));
                        sedes.remove(sedeId);

                        break;
                    }
                }
            }

            if (usuarioDatosAdicionalesDao.getUsuarioEsResponsablePreparatorias(usuarioId)) {
                ControlMaestroMultiple cmmSucursalJOBSSEMS = controlMaestroMultipleDao.findCMMById(ControlesMaestrosMultiples.CMM_SUC_SucursalJOBSSEMSId.CMM_SUC_SucursalJOBSSEMSId);
                Integer sucursalJOBSSEMSId = Integer.parseInt(cmmSucursalJOBSSEMS.getValor());

                for (Integer sedeId : sedes) {
                    if (sedeId.intValue() == sucursalJOBSSEMSId.intValue()) {
                        gruposIds.addAll(programaGrupoDao.findIdAllByPreparatoriaResponsableId(usuarioId));
                        sedes.remove(sedeId);

                        break;
                    }
                }
            }

            data.put("datos", programaGrupoDao.findProjectedCapturaBySedeInOrIdIn(sedes, gruposIds));
        } else {
            Empleado empleado = empleadoDao.findByUsuarioId(usuarioId);

            if (empleado != null) {
                List<Integer> gruposIds = new ArrayList<>();

                if (empleado.getTipoEmpleadoId() == ControlesMaestrosMultiples.CMM_EMP_TipoEmpleadoId.ACADEMICO) {
                    List<Integer> gruposIdsNot = new ArrayList<>();

                    if (usuarioDatosAdicionalesDao.getUsuarioEsResponsableCentrosUniversitarios(usuarioId)) {
                        gruposIds.addAll(programaGrupoDao.findIdAllByCentroUniversitarioResponsableIdAndIdNotIn(usuarioId, gruposIdsNot));
                    }

                    if (usuarioDatosAdicionalesDao.getUsuarioEsResponsablePreparatorias(usuarioId)) {
                        gruposIds.addAll(programaGrupoDao.findIdAllByPreparatoriaResponsableIdAndIdNotIn(usuarioId, gruposIdsNot));
                    }
                }

                data.put("datos", programaGrupoDao.findProjectedCapturaByFiltros(empleado.getId(), new ArrayList<>(), gruposIds));
            }
        }

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.MOSTRAR_FILTROS_CAPTURA_CALIFICACIONES) != null) {
            permisos.put("MOSTRAR_FILTROS", Boolean.TRUE);

            data.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
            data.put("anios", programaGrupoDao.findAniosFechaInicio());
            data.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());
        }

        data.put("permisos", permisos);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> data = new HashMap<>();

        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        boolean tienePermiso = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.VISUALIZAR_GRUPOS_SEDE_CALIFICACIONES);

        data.put("datos", programaGrupoDao.findProjectedCapturaByFiltrosConPermisos(sedesId, fechas, modalidadesId, usuarioId, tienePermiso));

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/detalle/{grupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable("grupoId") Integer grupoId, ServletRequest req) throws SQLException, ParseException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);
        HashMap<String,Object> datos = new HashMap<>();

        Boolean permisoRol = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.CAPTURA_EXTEMPORANEA_CALIFICACIONES);
        Boolean permisoResponsable = programaGrupoDao.getUsuarioEsResponsable(usuarioId,grupoId);
        Boolean permisoCerrar = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.CERRAR_GRUPO);
        Boolean permisoBoletas = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.DESCARGA_MASIVA_BOLETAS);
        HashMap<String, Boolean> permisos = new HashMap<>();
        if (permisoRol)
            permisos.put("CAPTURA_EXTEMPORANEA_CALIFICACIONES", true);
        if (permisoResponsable)
            permisos.put("USUARIO_RESPONSABLE", true);
        if (permisoCerrar)
            permisos.put("CERRAR_GRUPO", true);
        if (permisoBoletas)
            permisos.put("DESCARGAR_BOLETAS", true);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.EXPORTAR_EXCEL_CALIFICACIONES) != null) {
            permisos.put("EXPORTAR_EXCEL_CALIFICACIONES", Boolean.TRUE);
        }

        ProgramaGrupoEditarProjection grupo = programaGrupoDao.findProjectedEditarById(grupoId);

        Integer programaId = grupo.getProgramaIdioma().getProgramaId();
        Integer modalidadId = grupo.getPaModalidad().getId();
        Integer idiomaId = grupo.getProgramaIdioma().getIdiomaId();

        datos.put("grupo", programaGrupoDao.findProjectedCapturaEditarById(grupoId));
        datos.put("alumnos", alumnoGrupoDao.findAllListadoCapturaByGrupoId(grupoId));
        datos.put("metricas", programaGrupoDao.getGrupoMetricas(programaId, grupoId, modalidadId, idiomaId));
        datos.put("actividades", programaGrupoDao.getGrupoActividades(programaId, grupoId, modalidadId, idiomaId));
        datos.put("calificaciones", alumnoExamenCalificacionDao.findAllProjectedByGrupoId(grupoId));
        datos.put("historial", logController.getHistorial(grupoId, LogProceso.CAPTURA_CALIFICACIONES));
        datos.put("permisos", permisos);
        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse guardar(@RequestBody JSONObject json, ServletRequest req) {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);
        //Recuperar el estatus del grupo
        ProgramaGrupo grupo = programaGrupoDao.findById((Integer) json.get("grupoId"));
        Boolean grupoActivo = grupo.getEstatusId().equals(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
        //Recuperar el permiso de CAPTURA_EXTEMPORANEA_CALIFICACIONES
        Boolean capturaExtemporanea = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.CAPTURA_EXTEMPORANEA_CALIFICACIONES);
        //Si el grupo está cancelado, regresar un error
        if (grupo.getEstatusId().equals(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.CANCELADO))
            return new JsonResponse(null, "El grupo ha sido cancelado.", JsonResponse.STATUS_ERROR);
        //Si el grupo está finalizado y no tiene permiso, regresar un error
        if (grupo.getEstatusId().equals(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.FINALIZADO) && !capturaExtemporanea)
            return new JsonResponse(null, "No se permite editar calificaciones en un grupo finalizado.", JsonResponse.STATUS_ERROR);
        //Si tiene permiso o el grupo está activo...
        List<Integer> estatusFinales = new ArrayList<>();
        estatusFinales.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.SIN_DERECHO);
        estatusFinales.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
        estatusFinales.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.DESERTOR);
        Boolean editado = false;
        List<JSONObject> errors = new ArrayList<>();
        CICLOX:
        for (Object obj : (List<Object>) json.get("alumnos")){
            JSONObject alumno = new JSONObject((LinkedHashMap<String, Object>) obj);
            //Buscar todas las inscripciones vigentes para el alumno, programa e idioma
            Integer alumnoId = (Integer) alumno.get("alumnoId");
            Alumno alu = alumnoDao.findById(alumnoId);
            String calificacion = alumno.getAsString("calificacion");
            BigDecimal cFinal = new BigDecimal(calificacion == null ? "0.00" : calificacion);
            AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findByAlumnoIdAndGrupoIdAndEstatusIdNot(alumnoId, grupo.getId(), ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
            BigDecimal cAnterior = alumnoGrupo.getCalificacionFinal();
            Integer eAnterior = alumnoGrupo.getEstatusId();
            alumnoGrupo.setCalificacionFinal(cFinal);
            try{
                Integer nuevoEstatus = programaGrupoController.evaluarAlumnoGrupo(alumnoGrupo, grupo);
                alumnoGrupo.setEstatusId(nuevoEstatus);
                //Verificar que no se encuentre en un estatus final
                if (estatusFinales.contains(nuevoEstatus))
                    throw new Exception("El alumno no es candidato a calificacion.");
            } catch (Exception e){
                JSONObject error = new JSONObject();
                error.put("codigo", alu.getCodigo());
                error.put("nombre", alu.getPrimerApellido()+" "+alu.getSegundoApellido()+" "+alu.getNombre());
                error.put("error", e.getMessage());
                errors.add(error);
                alumnoGrupo.setCalificacionFinal(cAnterior);
                alumnoGrupo.setEstatusId(eAnterior);
                continue CICLOX;
            }
            //Si el grupo está activo o no disparó errores, permitir guardar
            List<AlumnoExamenCalificacion> detalles = new ArrayList<>();
            for (Object o : (List<Object>) alumno.get("detalles")){
                ObjectMapper mapper = new ObjectMapper();
                AlumnoExamenCalificacion detalle = mapper.convertValue(o, AlumnoExamenCalificacion.class);
                if (detalle.getId() == null)
                    detalle.setCreadoPorId(usuarioId);
                else {
                    detalle.setModificadoPorId(usuarioId);
                    editado = true;
                }
                detalles.add(detalle);
                //alumnoExamenCalificacionDao.save(detalle);
            }
            alumnoExamenCalificacionDao.saveAll(detalles);
            alumnoGrupo.setCalificacionFinal(cFinal);
            alumnoGrupo.setCalificacionConvertida(cFinal);
            alumnoGrupoDao.save(alumnoGrupo);
        }

        if(editado){
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.MODIFICADO,
                            LogProceso.CAPTURA_CALIFICACIONES,
                            grupo.getId()
                    ),
                    usuarioId
            );
        }
        return new JsonResponse(errors, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="/boleta", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadBoleta(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        InputStream reporte = getBoleta((Integer) json.get("alumnoId"), (Integer) json.get("grupoId"));
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=boleta.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(reporte, response.getOutputStream());
    }

    @RequestMapping(value="/boletas/{grupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadBoletasGrupo(HttpServletResponse response, @PathVariable("grupoId") Integer grupoId) throws IOException, SQLException, JRException {
        List<Integer> estatus = new ArrayList<>();
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.ACTIVO);
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REGISTRADO);
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.EN_RIESGO);
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.APROBADO);
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REPROBADO);
        estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.SIN_DERECHO);
        List<AlumnoGrupoCalificacionesProjection> boletas = alumnoGrupoDao.findAllByGrupoIdAndEstatusIdIn(grupoId, estatus);
        HashMap<String,InputStream> files = new HashMap<>();
        for (AlumnoGrupoCalificacionesProjection boleta : boletas){
            Alumno alumno = alumnoDao.findById(boleta.getAlumnoId());
            InputStream reporte = getBoleta(boleta.getAlumnoId(), boleta.getGrupoId());
            files.put(alumno.getCodigo()+" "+alumno.getPrimerApellido()+" "+alumno.getSegundoApellido()+" "+alumno.getNombre(), reporte);
        }
        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);
        reporteService.downloadAsZip(response, files,grupo.getCodigoGrupo());
    }

    @GetMapping("/excel_listado")
    public void exportarListado(ServletRequest req, HttpServletResponse response) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<ProgramaIdiomaListadoProjection> cursos = programaIdiomaDao.findListadoOrderByCodigo();

        SXSSFWorkbook workbook = new SXSSFWorkbook();

        for (ProgramaIdiomaListadoProjection curso : cursos){
            //Instant startTimeCursos = Instant.now();
            List<Object[]> results = new ArrayList<>();
            try{
                results = em
                        .createNativeQuery("EXEC sp_getReporteCalificaciones :usuarioId, :programaIdiomaId, :grupoId")
                        .setParameter("usuarioId", usuarioId)
                        .setParameter("programaIdiomaId", curso.getId())
                        .setParameter("grupoId", null)
                        .getResultList();
            } catch (Exception e){
                continue;
            }

            SXSSFSheet sheet = workbook.createSheet(curso.getCodigo());
            exportarGrupo(workbook, sheet, results ,curso.getId(),0);

            Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
            _sh.setAccessible(true);
            XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
            xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );
            if(sheet.getRow(0) != null){
                for (Integer i = 0; i < sheet.getRow(0).getLastCellNum(); i++){ sheet.setColumnWidth(i,4000); }
            }
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=listado-calificaciones.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @GetMapping("/excel/{grupoId}")
    public void exportarDetalle(@PathVariable("grupoId") Integer grupoId, ServletRequest req, HttpServletResponse response) throws IOException, Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);

        List<Object[]> results = em
                .createNativeQuery("EXEC sp_getReporteCalificaciones :usuarioId, :programaIdiomaId, :grupoId")
                .setParameter("usuarioId", usuarioId)
                .setParameter("programaIdiomaId", grupo.getProgramaIdiomaId())
                .setParameter("grupoId", grupoId)
                .getResultList();

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("Calificaciones");

        exportarGrupo(workbook, sheet, results, grupo.getProgramaIdiomaId(),0);

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        Row r = sheet.getRow(0);
        if(r != null){
            Integer last = new Integer( r.getLastCellNum());
            sheet.trackAllColumnsForAutoSizing();
            for (Integer i = 0; i < last; i++){
                sheet.autoSizeColumn(i);
            }
        }


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=alumnos-calificaciones.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public Integer exportarGrupo(SXSSFWorkbook workbook, SXSSFSheet sheet, List<Object[]> array, Integer programaIdiomaId, Integer rowCount){
        List<String> actividades = em.createNativeQuery("select PAAE_Actividad FROM( " +
                "        SELECT [PAAE_Actividad], MIN([PROGRUE_ProgramaGrupoExamenId]) AS PROGRUE_ProgramaGrupoExamenId " +
                "FROM [dbo].[ProgramasGruposExamenes] " +
                "INNER JOIN [dbo].[ProgramasGrupos] ON [PROGRUE_PROGRU_GrupoId] = [PROGRU_GrupoId] " +
                "INNER JOIN [dbo].[ProgramasGruposExamenesDetalles] ON [PROGRUE_ProgramaGrupoExamenId] = [PROGRUED_PROGRUE_ProgramaGrupoExamenId] " +
                "INNER JOIN [dbo].[PAActividadesEvaluacion] on [PROGRUED_PAAE_ActividadEvaluacionId] = [PAAE_ActividadEvaluacionId] " +
                "WHERE [PROGRU_PROGI_ProgramaIdiomaId] = :programaIdiomaId " +
                "GROUP BY [PAAE_Actividad] " +
                ") Q " +
                "ORDER BY PROGRUE_ProgramaGrupoExamenId")
                .setParameter("programaIdiomaId", programaIdiomaId)
                .getResultList();
        //Declare styles
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);

        List<String> lht = new ArrayList<>();
        lht.addAll(Arrays.asList(new String[]{"Codigo de alumno","Primer apellido","Segundo apellido","Nombre(s)",
                "Semestre U de G","Grupo U de G","Turno U de G","Nivel JOBS-SEMS","Plantel","Grupo JOBS-SEMS","Horario",
                "Escuela","Carrera","Cohorte","Alumno regular","Entrego carta compromiso","Codigo Proulex", "Estatus alumno"}));
        lht.addAll(actividades);
        lht.add("Calificacion JOBS SEMS");
        lht.add("Calificacion final");
        String[] ht = lht.toArray(new String[lht.size()]);
        createRow(sheet, rowCount, ht, header);
        rowCount++;

        if(actividades.size() > 0){
            for (Object[] item : array){
                SXSSFRow row = sheet.createRow(rowCount);

                Integer column = 0;
                for(Integer i = column; i < 18; i++){
                    if (item[i] != null)
                        createCell(row, column, String.valueOf(item[i]), detail);
                    else
                        createCell(row, column, "", detail);
                    column++;
                }

                column = 18;
                BigDecimal total = BigDecimal.ZERO;
                for(String actividad : actividades){
                    if(item[column + 4] == null)
                        createCell(row,column, BigDecimal.ZERO.toString(), detail);
                    else{
                        BigDecimal value = new BigDecimal(String.valueOf(item[column + 4])).setScale(2, BigDecimal.ROUND_HALF_UP);
                        createCell(row,column, value.toString(), detail);
                        total = total.add(value);
                    }
                    column++;
                }
                createCell(row,column, String.valueOf(item[column + 4]), detail);
                column++;
                try {
                    createCell(row, column, String.valueOf(item[column + 4]), detail);
                }catch(Exception e){
                    e.printStackTrace();
                }

                //Deprecated: User requeriment
                //createCell(row,columnCount, getCalificacionConvertida(total).setScale(2, BigDecimal.ROUND_HALF_UP).toString(), detail);
                rowCount++;
            }
        }
        return rowCount + 1;
    }

    private CellStyle createStyle(SXSSFWorkbook workbook, Short background, Short fontColor, boolean bold, boolean locked){
        CellStyle style = workbook.createCellStyle();
        if (background != null) {
            style.setFillForegroundColor(background);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        Font font = workbook.createFont();
        if (fontColor != null) { font.setColor(fontColor); }
        if (bold) { font.setBold(true); }
        style.setLocked(locked);
        style.setFont(font);
        return style;
    }

    private void createRow(SXSSFSheet sheet, Integer rowNumber, String[] values, CellStyle style) {
        SXSSFRow row = sheet.createRow(rowNumber);
        Integer index = 0;
        for (String value : values) {
            createCell(row, index, value, style);
            index++;
        }
    }

    private BigDecimal getCalificacionConvertida(BigDecimal calificacion){
        if(calificacion == null)
            return new BigDecimal(10);
        if(calificacion.compareTo(new BigDecimal(55)) == 1)
            return new BigDecimal(100).subtract(new BigDecimal(2).multiply(new BigDecimal(100).subtract(calificacion))).setScale(2, BigDecimal.ROUND_HALF_UP);
        else
            return new BigDecimal(10);
    }

    private InputStream getBoleta(Integer alumnoId, Integer grupoId) throws SQLException, JRException, IOException {
        // Buscamos el alumno grupo con el ID de alumno y ID de grupo
        AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findByAlumnoIdAndGrupoIdAndEstatusIdNot(alumnoId, grupoId, ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);

        String reportPath = "/modulos/control-escolar/Boleta.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("alumnoId", alumnoId);
        parameters.put("grupoId", grupoId);
        parameters.put("hashId", this.hashId.encode(alumnoGrupo.getId()));

        return reporteService.generarJasperReport(reportPath, parameters, ReporteServiceImpl.output.PDF, true);
    }

    @RequestMapping(value="/validacion-boletas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getValidacionBoletas(@RequestBody String codigo, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {

        Integer id = hashId.decode(codigo);

        AlumnoGrupoBoletaProjection alumnoGrupoBoleta = alumnoGrupoDao.getAlumnoGrupoBoleta(id);

        HashMap<String, Object> json = new HashMap<>();

        if(alumnoGrupoBoleta != null){
            json.put("esValidar", true);
            json.put("alumnoGrupoBoleta", alumnoGrupoBoleta);
        }else{
            json.put("esValidar", false);
        }

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    /* Reporte de Calificaciones */
    @RequestMapping(value = "/reporte", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());

        HashMap<String, Boolean> permisos = new HashMap<>();

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuarioDao.findById(usuarioId).getRolId(), MenuPrincipalPermisos.REPORTE_CALIFICACIONES_EXCEL) != null) {
            permisos.put("REPORTE_CALIFICACIONES_EXCEL", Boolean.TRUE);
        }

        json.put("permisos", permisos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/planteles", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPlantelesByFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = sede != null ? (Integer) sede.get("id") : null;
        HashMap<String, Object> res = new HashMap<>();
        res.put("planteles", sucursalPlantelDao.findAllByPermisosUsuarioAndSucursalId(usuarioId, sedeId));
        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/planteles_alumno", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCMMByFiltros(@RequestBody JSONObject json) throws SQLException, ParseException {
        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = sede != null ? (Integer) sede.get("id") : null;

        List<ControlMaestroMultipleComboProjection> sedeJOBS = controlMaestroMultipleDao.findAllByControl("CMM_SUC_SucursalJOBSId");
        List<ControlMaestroMultipleComboProjection> sedeSEMS = controlMaestroMultipleDao.findAllByControl("CMM_SUC_SucursalJOBSSEMSId");

        HashMap<String, Object> res = new HashMap<>();
        if (sedeJOBS != null && sedeJOBS.size() > 0){
            if (Integer.valueOf(sedeJOBS.get(0).getValor()).equals(sedeId))
                res.put("plantelesAlumno", controlMaestroMultipleDao.findAllByControl("CMM_ALU_CentrosUniversitarios"));
        }

        if (sedeSEMS != null && sedeSEMS.size() > 0){
            if (Integer.valueOf(sedeSEMS.get(0).getValor()).equals(sedeId))
                res.put("plantelesAlumno", controlMaestroMultipleDao.findAllByControl("CMM_ALU_Preparatorias"));
        }
        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/programaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProgramacionByFiltros(@RequestBody JSONObject json){
        HashMap<String, Object> res = new HashMap<>();
        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = sede != null ? (Integer) sede.get("id") : null;
        List<HashMap<String, Object>> planteles = json.get("planteles") != null ? (List<HashMap<String,Object>>) json.get("planteles") : new ArrayList<>();
        List<Integer> plantelesIds = new ArrayList<>();
        if(planteles != null){
            for (HashMap<String, Object> plantel : planteles){
                plantelesIds.add((Integer) plantel.get("id"));
            }
        }
        res.put("programaciones", programacionAcademicaComercialDao.findProjectedComboAllBySedeIdOrPlantelIdIn(sedeId, plantelesIds));
        res.put("ciclos", paCicloDao.findProjectedComboAllBySucursalIdIn(sedeId, plantelesIds));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/modalidades", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getModalidadesByFiltros(@RequestBody JSONObject json) {
        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = sede != null ? (Integer) sede.get("id") : null;
        List<HashMap<String, Object>> planteles = json.get("planteles") != null ? (List<HashMap<String,Object>>) json.get("planteles") : new ArrayList<>();
        List<Integer> plantelesIds = new ArrayList<>();
        if(planteles != null){
            for (HashMap<String, Object> plantel : planteles){
                plantelesIds.add((Integer) plantel.get("id"));
            }
        }
        HashMap<String, Object> pa = (HashMap<String,Object>) json.get("pa");
        Integer paId = pa != null ? (Integer) pa.get("id") : null;
        HashMap<String, Object> ciclo = (HashMap<String,Object>) json.get("ciclo");
        Integer cicloId = ciclo != null ? (Integer) ciclo.get("id") : null;

        HashMap<String, Object> res = new HashMap<>();
        res.put("modalidades", paModalidadDao.findProjectedComboSimpleAllByFiltros(sedeId, plantelesIds, paId, cicloId));
        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/fechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByFiltros(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/reporte/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporteFiltros(@RequestBody JSONObject json) {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        String alumno = (String) json.get("alumno");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        List<ReporteCalificacionesProjection> rpt = alumnoAsistenciaDao.getReporteCalificacionesListado(
                sedesId,
                fechas,
                !StringCheck.isNullorEmpty(alumno) ? alumno : null,
                modalidadesId
        );

        res.put("datos", rpt);

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "reporte/xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        String alumno = (String) json.get("alumno");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedesId);
        params.put("fechas", fechas);
        params.put("alumno", !StringCheck.isNullorEmpty(alumno) ? alumno : null);
        params.put("modalidadesId", modalidadesId);

        String query = "SELECT codigoAlumno, primerApellido, segundoApellido, nombre, genero, correo, " +
                "grado, grupo, turno, nivel, plantel, codigoGrupo, horario, nombreProfesor, escuela, carrera, cohorte, " +
                "regular, carta, codigoProulex, estatus, q1, q2, q3, ca, pf, wa, fe, oo, ee, pd, pz, da, rl, calificacionConvertida, calificacionFinal " +
                "FROM [dbo].[VW_RPT_ReporteCalificacionesDetalles] \n" +
                "WHERE sedeId IN(:sedesId)\n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                "       AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas((nombre + ' ' + primerApellido + ISNULL(' ' + segundoApellido, '')), :alumno) = 1 THEN 1 ELSE 0 END END = 1 \n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))";

        String[] alColumnas = new String[]{"Codigo de alumno", "Primer apellido", "Segundo apellido", "Nombre(s)", "Genero", "Correo electronico",
                "Semestre U de G", "Grupo U de G", "Turno U de G", "Nivel", "Plantel", "Grupo", "Horario", "Profesor", "Escuela", "Carrera", "Cohorte",
                "Alumno regular", "Entrego carta compromiso", "Codigo Proulex", "Estatus", "Examen Parcial 1", "Examen Parcial 2", "Examen Parcial 3", "Comprensión auditiva", "Portafolio", "Expresión Escrita", "Examen Final", "Expresión Oral Continua", "Evaluación de Expresión Oral", "Práctica Digital", "Proyecto Final", "Practica Digital", "Reportes de lecturas", "Calificacion JOBS SEMS", "Calificacion final"};

        excelController.downloadXlsxSXSSF(response, "Reporte calificaciones", query, alColumnas, params, "Detalles");
    }
}