package com.pixvs.main.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.AlumnoGrupo;
import com.pixvs.main.models.ProgramaIdiomaCertificacionVale;
import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.mapeos.ControlesMaestros;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale.ProgramaIdiomaCertificacionValeGeneradoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaCertificacionVale.ProgramaIdiomaCertificacionValeListadoProjection;
import com.pixvs.main.models.projections.SolicitudNuevaContratacion.SolicitudNuevaContratacionListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Rene Carrillo on 05/12/2022.
 */
@RestController
@RequestMapping("/api/v1/vales-certificaciones")
public class ValeCertificacionController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private PAModalidadDao modalidadDao;

    @Autowired
    private ProgramaIdiomaCertificacionValeDao programaIdiomaCertificacionValeDao;

    @Autowired
    private Environment environment;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private AlumnoGrupoDao alumnoGrupoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);

        List<SucursalComboProjection> listaSede = sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId);

        HashMap<String, Object> json = new HashMap<>();

        json.put("listaSede", listaSede);
        json.put("listaAnio", programaGrupoDao.findAniosFechaInicio());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        HashMap<String, Object> sede = json.get("sede") != null ? (HashMap<String,Object>) json.get("sede") : new HashMap<>();

        Integer sedeId = sede.containsKey("id") ? (Integer) sede.get("id") : null;
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCurso"), "id");
        List<Integer> listaModalidadId = Utilidades.getListItems(json.get("listaModalidad"), "id");
        List<String> listaFecha = Utilidades.getListItems(json.get("listaFecha"), "fecha");
        String alumno = (String) json.get("alumno");

        List<ProgramaIdiomaCertificacionValeListadoProjection> listadoValeCertificacion = new ArrayList<>();

        try{
            listadoValeCertificacion = programaIdiomaCertificacionValeDao.vw_lto_listadoValeCertificacion(
                    sedeId,
                    listaCursoId,
                    listaModalidadId,
                    listaFecha,
                    !StringCheck.isNullorEmpty(alumno) ? alumno : ""
            );
        }catch (Exception ex){
            return new JsonResponse("", "Algo está mal con los datos, por favor verificar los datos.", JsonResponse.STATUS_ERROR);
        }


        return new JsonResponse(listadoValeCertificacion, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getListaModalidad", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListaModalidadWithCurso(@RequestBody JSONObject json) {
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCurso"), "id");

        List<Integer> listaModalidadId = programaGrupoDao.buscaModalidadConCurso(listaCursoId);

        HashMap<String, Object> _json = new HashMap<>();

        _json.put("listaModalidad", modalidadDao.findComboAllByActivoTrueAndIdInOrderByNombre(listaModalidadId));

        return new JsonResponse(_json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getListaFecha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListaFechaByCicloPA(@RequestBody JSONObject json) {
        HashMap<String, Object> sede = json.get("sede") != null ? (HashMap<String,Object>) json.get("sede") : new HashMap<>();
        Integer sedeId = sede.containsKey("id") ? (Integer) sede.get("id") : null;
        List<Integer> listaCursoId = Utilidades.getListItems(json.get("listaCursoId"), "id");
        int anio = (Integer) json.get("anio");
        List<Integer> listaModalidadId = Utilidades.getListItems(json.get("listaModalidad"), "id");

        HashMap<String, Object> _json = new HashMap<>();

        _json.put("listaFecha", programaGrupoDao.findFechasInicioBySedeCursoIdAnioModalidadId(new ArrayList<>(sedeId), listaCursoId, anio, listaModalidadId));

        return new JsonResponse(_json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getValeCertificacionGenerarTodos", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getValeCertificacionGenerarTodos(@RequestBody List<Integer> listaAlumnoGrupoId, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        for (Integer alumnoGrupoId : listaAlumnoGrupoId){
            ProgramaIdiomaCertificacionValeGeneradoProjection valeGenerado = programaIdiomaCertificacionValeDao.vw_alumnosGruposValesCertificaciones(alumnoGrupoId);

            ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = new ProgramaIdiomaCertificacionVale();

            programaIdiomaCertificacionVale.setAlumnoGrupoId(alumnoGrupoId);
            programaIdiomaCertificacionVale.setProgramaIdiomaCertificacionDescuentoId(valeGenerado.getProgramaIdiomaCertificacionDescuentoId());
            programaIdiomaCertificacionVale.setPorcentajeDescuento(valeGenerado.getPorcentajeDescuento());
            programaIdiomaCertificacionVale.setFechaVigenciaInicio(valeGenerado.getFechaFin());
            programaIdiomaCertificacionVale.setFechaVigenciaFin(valeGenerado.getVigencia());
            programaIdiomaCertificacionVale.setCosto(valeGenerado.getCostoUltimo());
            programaIdiomaCertificacionVale.setCostoFinal(valeGenerado.getCostoFinal());
            programaIdiomaCertificacionVale.setEstatusId(ControlesMaestrosMultiples.CMM_VC_Estatus.GENERADO);
            programaIdiomaCertificacionVale.setCreadoPorId(usuarioId);

            programaIdiomaCertificacionValeDao.save(programaIdiomaCertificacionVale);
        }

        List<ProgramaIdiomaCertificacionValeListadoProjection> listaValeGenerado = programaIdiomaCertificacionValeDao.buscaTodosIistadoValeCertificacionConAlumnoGrupoId(listaAlumnoGrupoId);

        return new JsonResponse(listaValeGenerado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getValeCertificacionGenerar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getValeCertificacionGenerar(@RequestBody Integer alumnoGrupoId, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ProgramaIdiomaCertificacionValeGeneradoProjection valeGenerado = programaIdiomaCertificacionValeDao.vw_alumnosGruposValesCertificaciones(alumnoGrupoId);

        ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = new ProgramaIdiomaCertificacionVale();

        programaIdiomaCertificacionVale.setAlumnoGrupoId(alumnoGrupoId);
        programaIdiomaCertificacionVale.setProgramaIdiomaCertificacionDescuentoId(valeGenerado.getProgramaIdiomaCertificacionDescuentoId());
        programaIdiomaCertificacionVale.setPorcentajeDescuento(valeGenerado.getPorcentajeDescuento());
        programaIdiomaCertificacionVale.setFechaVigenciaInicio(valeGenerado.getFechaFin());
        programaIdiomaCertificacionVale.setFechaVigenciaFin(valeGenerado.getVigencia());
        programaIdiomaCertificacionVale.setCosto(valeGenerado.getCostoUltimo());
        programaIdiomaCertificacionVale.setCostoFinal(valeGenerado.getCostoFinal());
        programaIdiomaCertificacionVale.setEstatusId(ControlesMaestrosMultiples.CMM_VC_Estatus.GENERADO);
        programaIdiomaCertificacionVale.setCreadoPorId(usuarioId);

        programaIdiomaCertificacionValeDao.save(programaIdiomaCertificacionVale);

        ProgramaIdiomaCertificacionValeListadoProjection _valeGenerado = programaIdiomaCertificacionValeDao.buscaIistadoValeCertificacionConAlumnoGrupoId(alumnoGrupoId);

        return new JsonResponse(_valeGenerado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getValeCertificacionEnviar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getValeCertificacionEnviar(@RequestBody Integer alumnoGrupoId, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ProgramaIdiomaCertificacionValeGeneradoProjection valeGenerado = programaIdiomaCertificacionValeDao.vw_alumnosGruposValesCertificaciones(alumnoGrupoId);

        ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = new ProgramaIdiomaCertificacionVale();

        programaIdiomaCertificacionVale.setAlumnoGrupoId(alumnoGrupoId);
        programaIdiomaCertificacionVale.setProgramaIdiomaCertificacionDescuentoId(valeGenerado.getProgramaIdiomaCertificacionDescuentoId());
        programaIdiomaCertificacionVale.setPorcentajeDescuento(valeGenerado.getPorcentajeDescuento());
        programaIdiomaCertificacionVale.setFechaVigenciaInicio(valeGenerado.getFechaFin());
        programaIdiomaCertificacionVale.setFechaVigenciaFin(valeGenerado.getVigencia());
        programaIdiomaCertificacionVale.setCosto(valeGenerado.getCostoUltimo());
        programaIdiomaCertificacionVale.setCostoFinal(valeGenerado.getCostoFinal());
        programaIdiomaCertificacionVale.setEstatusId(ControlesMaestrosMultiples.CMM_VC_Estatus.GENERADO);
        programaIdiomaCertificacionVale.setCreadoPorId(usuarioId);

        programaIdiomaCertificacionValeDao.save(programaIdiomaCertificacionVale);

        ProgramaIdiomaCertificacionValeListadoProjection _valeGenerado = programaIdiomaCertificacionValeDao.buscaIistadoValeCertificacionConAlumnoGrupoId(alumnoGrupoId);

        return new JsonResponse(_valeGenerado, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="/getValeCertificacionImprimir", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getValeCertificacionImprimir(@RequestBody Integer alumnoGrupoId, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        InputStream reporte = getImprimirValeCertificacion(alumnoGrupoId);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vale-certificacion.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(reporte, response.getOutputStream());
        response.flushBuffer();
    }

    @RequestMapping(value = "/getValeCertificacionBorrar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getValeCertificacionBorrar(@RequestBody Integer alumnoGrupoId, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ProgramaIdiomaCertificacionValeGeneradoProjection valeGenerado = programaIdiomaCertificacionValeDao.vw_alumnosGruposValesCertificaciones(alumnoGrupoId);

        ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = new ProgramaIdiomaCertificacionVale();

        programaIdiomaCertificacionVale.setAlumnoGrupoId(alumnoGrupoId);
        programaIdiomaCertificacionVale.setProgramaIdiomaCertificacionDescuentoId(valeGenerado.getProgramaIdiomaCertificacionDescuentoId());
        programaIdiomaCertificacionVale.setPorcentajeDescuento(valeGenerado.getPorcentajeDescuento());
        programaIdiomaCertificacionVale.setFechaVigenciaInicio(valeGenerado.getFechaFin());
        programaIdiomaCertificacionVale.setFechaVigenciaFin(valeGenerado.getVigencia());
        programaIdiomaCertificacionVale.setCosto(valeGenerado.getCostoUltimo());
        programaIdiomaCertificacionVale.setCostoFinal(valeGenerado.getCostoFinal());
        programaIdiomaCertificacionVale.setEstatusId(ControlesMaestrosMultiples.CMM_VC_Estatus.BORRADO);
        programaIdiomaCertificacionVale.setCreadoPorId(usuarioId);

        programaIdiomaCertificacionValeDao.save(programaIdiomaCertificacionVale);

        ProgramaIdiomaCertificacionValeListadoProjection actualizarVale = programaIdiomaCertificacionValeDao.buscaIistadoValeCertificacionConAlumnoGrupoId(alumnoGrupoId);

        return new JsonResponse(actualizarVale, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getValeCertificacionCancelar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getValeCertificacionCancelar(@RequestBody Integer alumnoGrupoId, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        ProgramaIdiomaCertificacionVale programaIdiomaCertificacionVale = programaIdiomaCertificacionValeDao.findByAlumnoGrupoId(alumnoGrupoId);
        programaIdiomaCertificacionVale.setEstatusId(ControlesMaestrosMultiples.CMM_VC_Estatus.CANCELADO);
        programaIdiomaCertificacionVale.setModificadoPorId(usuarioId);
        programaIdiomaCertificacionVale.setFechaUltimaModificacion(new Date());

        programaIdiomaCertificacionValeDao.save(programaIdiomaCertificacionVale);

        ProgramaIdiomaCertificacionValeListadoProjection actualizarVale = programaIdiomaCertificacionValeDao.buscaIistadoValeCertificacionConAlumnoGrupoId(alumnoGrupoId);

        return new JsonResponse(actualizarVale, null, JsonResponse.STATUS_OK);
    }

    private InputStream getImprimirValeCertificacion(Integer alumnoGrupoId) throws SQLException, JRException, IOException {

        String reportPath = "/modulos/control-escolar/ValeCertificacion.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("FrontUrl", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("AlumnoGrupoId", alumnoGrupoId);

        return reporteService.generarJasperReport(reportPath, parameters, ReporteServiceImpl.output.PDF, true);
    }

    @RequestMapping(value="/getCorreoAlumno/{alumnoGrupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCorreoAlumno(@PathVariable Integer alumnoGrupoId, ServletRequest req) throws SQLException {
        AlumnoGrupo alumnoGrupo = alumnoGrupoDao.findById(alumnoGrupoId);
        return new JsonResponse(alumnoGrupo.getAlumno().getCorreoElectronico(), null, JsonResponse.STATUS_OK);
    }
}

