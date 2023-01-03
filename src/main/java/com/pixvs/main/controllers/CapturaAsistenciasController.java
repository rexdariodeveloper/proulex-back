package com.pixvs.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.AlumnoAsistencia.AlumnoAsistenciaResumenProjection;
import com.pixvs.main.models.projections.AlumnoAsistencia.ReporteAsistenciasProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import static org.apache.poi.ss.util.CellUtil.createCell;

@RestController
@RequestMapping("/api/v1/captura_asistencia")
public class CapturaAsistenciasController {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolDao rolDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private AlumnoAsistenciaDao alumnoAsistenciaDao;
    @Autowired
    private LogController logController;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;
    @Autowired
    private UsuarioDatosAdicionalesDao usuarioDatosAdicionalesDao;
    @Autowired
    private AlumnoGrupoDao alumnoGrupoDao;
    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;
    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private PACicloDao paCicloDao;
    @Autowired
    private ExcelController excelController;
    @Autowired
    PAModalidadDao modalidadDao;
    @Autowired
    ProgramaGrupoController programaGrupoController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCursos(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        HashMap<String, Object> data = new HashMap<>();
        HashMap<String, Boolean> permisos = new HashMap<>();

        if (rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.VISUALIZAR_GRUPOS_SEDE_ASISTENCIAS)) {
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

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.EXPORTAR_EXCEL_ASISTENCIAS) != null) {
            permisos.put("EXPORTAR_EXCEL_ASISTENCIAS", Boolean.TRUE);
        }

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.MOSTRAR_FILTROS_CAPTURA_ASISTENCIAS) != null) {
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

        boolean tienePermiso = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.VISUALIZAR_GRUPOS_SEDE_ASISTENCIAS);

        data.put("datos", programaGrupoDao.findProjectedCapturaByFiltrosConPermisos(sedesId, fechas, modalidadesId, usuarioId, tienePermiso));

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/detalle/{grupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable("grupoId") Integer grupoId, ServletRequest req) throws SQLException, ParseException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);
        HashMap<String,Object> datos = new HashMap<>();

        Boolean permisoRol = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.CAPTURA_EXTEMPORANEA_ASISTENCIAS);
        Boolean permisoResponsable = programaGrupoDao.getUsuarioEsResponsable(usuarioId,grupoId);
        HashMap<String, Boolean> permisos = new HashMap<>();
        if (permisoRol)
            permisos.put("CAPTURA_EXTEMPORANEA_ASISTENCIAS", true);
        if (permisoResponsable)
            permisos.put("USUARIO_RESPONSABLE", true);
        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.EXPORTAR_EXCEL_ASISTENCIAS) != null)
            permisos.put("EXPORTAR_EXCEL_ASISTENCIAS", Boolean.TRUE);

        datos.put("grupo", programaGrupoDao.findProjectedCapturaEditarById(grupoId));
        datos.put("fechas", programaGrupoDao.getFechasClaseByGrupoId(grupoId));
        datos.put("alumnos", alumnoGrupoDao.findAllListadoCapturaByGrupoId(grupoId));
        datos.put("tipos", controlMaestroMultipleDao.findAllByControl("CMM_AA_TipoAsistencia"));
        datos.put("asistencias", alumnoAsistenciaDao.findAllEditarProjetedByGrupoId(grupoId));
        datos.put("historial", logController.getHistorial(grupoId, LogProceso.CAPTURA_ASISTENCIAS));
        datos.put("permisos", permisos);

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse guardar(@RequestBody JSONArray json, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<JSONObject> errors = new ArrayList<>();
        try{
            List<Object[]> results = em
                    .createNativeQuery("EXEC sp_asistencias :asistencias, :creadoPorId")
                    .setParameter("asistencias", json.toString())
                    .setParameter("creadoPorId", usuarioId)
                    .getResultList();
            results.size();

            for (Object[] result : results){
                JSONObject feedback = new JSONObject();
                feedback.appendField("student", result[0]);
                feedback.appendField("group", result[1]);
                feedback.appendField("message", result[2]);
                errors.add(feedback);
            }
        } catch (PersistenceException e){
            throw new AdvertenciaException(e.getLocalizedMessage());
        } catch (Exception e) { e.printStackTrace(); }
        return new JsonResponse(errors,null,JsonResponse.STATUS_OK);
        /*
        Usuario usuario = usuarioDao.findById(usuarioId);
        //Recuperar el estatus del grupo
        ProgramaGrupo grupo = programaGrupoDao.findById((Integer) json.get("grupoId"));
        Boolean grupoActivo = grupo.getEstatusId().equals(ControlesMaestrosMultiples.CMM_PROGRU_Estatus.ACTIVO);
        //Recuperar el permiso de CAPTURA_EXTEMPORANEA_CALIFICACIONES
        Boolean capturaExtemporanea = rolDao.tienePermiso(usuario.getRolId(), MenuPrincipalPermisos.CAPTURA_EXTEMPORANEA_ASISTENCIAS);
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
        estatusFinales.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.REPROBADO);
        Boolean editado = false;
        for (Object obj : (List<Object>) json.get("alumnos")){
            JSONObject alumno = new JSONObject((LinkedHashMap<String, Object>) obj);
            Integer alumnoId = (Integer) alumno.get("alumnoId");
            if(alumnoId != null){
                Alumno alu = alumnoDao.findById(alumnoId);
                //Buscar su registro en AlumnosGrupos
                List<Integer> estatus = new ArrayList<>();
                estatus.add(ControlesMaestrosMultiples.CMM_ALUG_Estatus.BAJA);
                List<AlumnoGrupo> relaciones = alumnoGrupoDao.findAllByAlumnoIdAndGrupoIdAndEstatusIdNotIn(alumnoId, grupo.getId(), estatus);
                AlumnoGrupo alumnoGrupo = relaciones.get(0);
                //Guardar la asistencia temporalmente
                Object o = (Object) alumno.get("asistencia");
                ObjectMapper mapper = new ObjectMapper();
                AlumnoAsistencia detalle = mapper.convertValue(o, AlumnoAsistencia.class);
                try{
                    if(alumnoGrupo == null)
                        throw new Exception("El alumno no cuenta con una relación vigente");
                    Boolean edit = actualizarAsistencia(alumnoGrupo, grupo, detalle, usuarioId);
                    editado = editado || edit;
                } catch (Exception e) {
                    JSONObject error = new JSONObject();
                    error.put("codigo", alu.getCodigo());
                    error.put("nombre", alu.getPrimerApellido()+" "+alu.getSegundoApellido()+" "+alu.getNombre());
                    error.put("error", e.getMessage());
                    errors.add(error);
                    continue;
                }
            }
        }
        if (editado){
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.MODIFICADO,
                            LogProceso.CAPTURA_ASISTENCIAS,
                            grupo.getId()
                    ),
                    usuarioId
            );
        }
        */
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean actualizarAsistencia(AlumnoGrupo alumnoGrupo, ProgramaGrupo grupo, AlumnoAsistencia asistencia, Integer usuarioId) throws  Exception {
        Boolean editado = false;
        if (asistencia.getId() == null){ asistencia.setCreadoPorId(usuarioId); }
        else { asistencia.setModificadoPorId(usuarioId); editado = true; }
        alumnoAsistenciaDao.save(asistencia);
        Integer nuevoEstatus = null;
        Integer estatusAnterior = alumnoGrupo.getEstatusId();
        //Obtener el nuevo estatus
        try{
            nuevoEstatus = programaGrupoController.evaluarAlumnoGrupo(alumnoGrupo, grupo);
        } catch (Exception e){
            alumnoGrupo.setEstatusId(estatusAnterior);
            TransactionStatus transactionStatus = TransactionAspectSupport.currentTransactionStatus();
            transactionStatus.setRollbackOnly();
            transactionStatus.flush();
            throw e;
        }
        alumnoGrupo.setEstatusId(nuevoEstatus);
        AlumnoAsistenciaResumenProjection resumen = alumnoAsistenciaDao.getProjectedResumenByAlumnoIdAndGrupoId(alumnoGrupo.getAlumnoId(), alumnoGrupo.getGrupoId());
        alumnoGrupo.setFaltas(resumen.getFaltas());
        alumnoGrupo.setAsistencias(resumen.getAsistencias());
        alumnoGrupo.setMinutosRetardo(resumen.getRetardos());
        alumnoGrupoDao.save(alumnoGrupo);
        return editado;
    }

    @GetMapping("/excel/{grupoId}")
    public void exportarDetalle(@PathVariable("grupoId") Integer grupoId, ServletRequest req, HttpServletResponse response) throws IOException, Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ProgramaGrupo grupo = programaGrupoDao.findById(grupoId);

        //String date = new SimpleDateFormat("yyyy-MM-dd").format(grupo.getFechaInicio());

        List<Object[]> results = em
                .createNativeQuery("EXEC [dbo].[sp_getReporteAsistencias] :sedeId, :plantelId, :pacId, :cicloId, :modalidadId, :fechaInicio, :codigoGrupo")
                .setParameter("sedeId", grupo.getSucursalId())
                .setParameter("plantelId", grupo.getSucursalPlantelId())
                .setParameter("pacId", grupo.getProgramacionAcademicaComercialId())
                .setParameter("cicloId", grupo.getPaCicloId())
                .setParameter("modalidadId", grupo.getPaModalidadId())
                .setParameter("fechaInicio", grupo.getFechaInicio())
                .setParameter("codigoGrupo", grupo.getCodigoGrupo())
                .getResultList();

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet(grupo.getCodigoGrupo());
        exportarGrupo(workbook,sheet,results,0);

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        for (Integer i = 0; i < 50; i++){ sheet.setColumnWidth(i,4000); }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Asistencias "+grupo.getCodigoGrupo()+".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    /* Reporte de asistencias */
    @RequestMapping(value = "/reporte", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());

        HashMap<String, Boolean> permisos = new HashMap<>();

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuarioDao.findById(usuarioId).getRolId(), MenuPrincipalPermisos.REPORTE_ASISTENCIAS_EXCEL) != null) {
            permisos.put("REPORTE_ASISTENCIAS_EXCEL", Boolean.TRUE);
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

        List<ReporteAsistenciasProjection> rpt = alumnoAsistenciaDao.getReporteAsistenciasListado(
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

        String query = "SELECT codigo, primerApellido, segundoApellido, nombre, correo, faltas, asistencias, retardos, estatus, " +
                "grado, grupo, turno, nivel, profesor, plantel, codigoGrupo, horario, escuela, carrera, cohorte, " +
                "regular, carta, codigoProulex " +
                "FROM [dbo].[VW_RPT_ASISTENCIAS_DETALLE] " +
                "WHERE sedeId IN(:sedesId)\n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                "       AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas((nombre + ' ' + primerApellido + ISNULL(' ' + segundoApellido, '')), :alumno) = 1 THEN 1 ELSE 0 END END = 1\n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))";

        String[] alColumnas = new String[]{"Codigo de alumno", "Primer apellido", "Segundo apellido", "Nombre(s)", "Correo electronico",
                "Faltas", "Asistencias", "Retardos (min)", "Estatus", "Semestre U de G", "Grupo U de G", "Turno U de G", "Nivel JOBS-SEMS",
                "Profesor", "Plantel", "Grupo JOBS-SEMS", "Horario", "Escuela", "Carrera", "Cohorte", "Alumno regular",
                "Entrego carta compromiso", "Codigo Proulex"};

        excelController.downloadXlsx(response, "Reporte asistencias", query, alColumnas, params, "Detalles");
    }

    @RequestMapping(value = "reporte/excel", method = RequestMethod.POST)
    public void exportarReporte(@RequestBody JSONObject json, ServletRequest req, HttpServletResponse response) throws Exception {
        JSONObject filtros = getFiltros(json);

        List<Object[]> results = em
                .createNativeQuery("EXEC [dbo].[sp_getReporteAsistencias] :sedeId, :plantelId, :pacId, :cicloId, :modalidadId, :fechaInicio, :codigoGrupo")
                .setParameter("sedeId", filtros.get("sedeId"))
                .setParameter("plantelId", filtros.get("plantelId"))
                .setParameter("pacId", filtros.get("pacId"))
                .setParameter("cicloId", filtros.get("cicloId"))
                .setParameter("modalidadId", filtros.get("modalidadId"))
                .setParameter("fechaInicio", filtros.get("fechaInicio"))
                .setParameter("codigoGrupo", filtros.get("codigoGrupo"))
                .getResultList();

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("Asistencias");
        exportarGrupo(workbook,sheet,results,0);

        Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
        _sh.setAccessible(true);
        XSSFSheet xssfsheet = (XSSFSheet)_sh.get(sheet);
        xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        for (Integer i = 0; i < 50; i++){ sheet.setColumnWidth(i,4000); }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Reporte asistencias.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public void exportarGrupo(SXSSFWorkbook workbook, SXSSFSheet sheet, List<Object[]> array, Integer rowCount){
        //Declare styles
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);
        CellStyle falta = createStyle(workbook, IndexedColors.RED.getIndex(), null, false, false);
        CellStyle asiste = createStyle(workbook, IndexedColors.GREEN.getIndex(), null, false, false);
        CellStyle retardo = createStyle(workbook, IndexedColors.LIGHT_ORANGE.getIndex(), null, false, false);
        CellStyle pendiente = createStyle(workbook, IndexedColors.GREY_25_PERCENT.getIndex(), null, false, false);
        List<String> lht = new ArrayList<>();
        lht.addAll(Arrays.asList(new String[]{"Código de alumno","Primer apellido","Segundo apellido","Nombre(s)", "Faltas", "Asistencias", "Estatus",
                "Semestre U de G","Grupo U de G","Turno U de G","Nivel JOBS-SEMS", "Profesor","Plantel","Grupo JOBS-SEMS","Horario",
                "Escuela","Carrera","Cohorte","Alumno regular","Entregó carta compromiso","Código Proulex"}));
        String f = String.valueOf(array.get(0)[array.get(0).length - 1]);
        Collections.addAll(lht, f.replace("[", "").replace("]", "").split(","));
        String[] ht = lht.toArray(new String[lht.size()]);
        createRow(sheet, rowCount, ht, header);
        rowCount++;
        for (Object[] item : array){
            SXSSFRow row = sheet.createRow(rowCount);

            Integer column = 0;
            for(Integer i = column; i < 22; i++){
                if (!i.equals(14)){
                    createCell(row, column, String.valueOf(item[i]), detail);
                    column++;
                }
            }
            column = 21;
            for(Integer i = 24; i < item.length - 1; i++){
                String value = String.valueOf(item[i]);
                if(value == null || value.equals("null"))
                    createCell(row,column, "", pendiente);
                else if (value.equals("2000550"))//Asistencia
                    createCell(row,column, "", asiste);
                else if (value.equals("2000551")) //Falta
                    createCell(row,column, "", falta);
                else if (value.equals("2000552"))//Falta justificada
                    createCell(row,column, "J", falta);
                else //Retardo
                    createCell(row,column, value, retardo);
                column++;
            }
            rowCount++;
        }
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

    private JSONObject getFiltros(JSONObject json){
        JSONObject filtros = new JSONObject();
        Integer sedeId = null;
        Integer pacId = null;
        Integer cicloId = null;
        Integer modalidadId = null;
        String fechaInicio = null;
        String codigo = null;
        List<Integer> plantelesIds = new ArrayList<>();
        List<Integer> escuelasIds = new ArrayList<>();

        if (json.get("sede") != null)
           sedeId = (Integer) ((HashMap<String, Object>) json.get("sede")).get("id");
        filtros.put("sedeId", sedeId);
        if (json.get("planteles") != null){
            for (HashMap<String, Object> plantel : (List<HashMap<String, Object>>) json.get("planteles")){
                plantelesIds.add((Integer) plantel.get("id"));
            }
        }
        filtros.put("planteles", plantelesIds);
        if (json.get("pa") != null)
            pacId = (Integer) ((HashMap<String, Object>) json.get("pa")).get("id");
        filtros.put("pacId", pacId);
        if (json.get("ciclo") != null)
            cicloId = (Integer) ((HashMap<String, Object>) json.get("ciclo")).get("id");
        filtros.put("cicloId", cicloId);
        if (json.get("modalidad") != null)
            modalidadId = (Integer) ((HashMap<String, Object>) json.get("modalidad")).get("id");
        filtros.put("modalidadId", modalidadId);
        if (json.get("fechaInicio") != null){
            fechaInicio = (String) ((HashMap<String, Object>) json.get("fechaInicio")).get("fecha");
        }
        filtros.put("fechaInicio" , fechaInicio);
        if (json.get("plantelesAlumnos") != null){
            for (HashMap<String, Object> escuela : (List<HashMap<String, Object>>) json.get("plantelesAlumnos")){
                escuelasIds.add((Integer) escuela.get("id"));
            }
        }
        filtros.put("escuelas", escuelasIds);
        if (json.get("codigo") != null){
            codigo = (String) json.get("codigo");
        }
        filtros.put("codigoGrupo" , codigo);

        return filtros;
    }
}

