package com.pixvs.main.controllers;

import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.ProgramaDao;
import com.pixvs.main.dao.ProgramaGrupoDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/reportes-pcp")
public class ReportesPCPController {

    @Autowired
    private Environment environment;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ProgramaDao programaDao;

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    PAModalidadDao modalidadDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());
        json.put("programas", programaDao.findAllByPcpTrueAndActivoTrueOrderByCodigo());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> programasId = Utilidades.getListItems(json.get("programas"), "id");

        return new JsonResponse(programaGrupoDao.getReporteGrupos(sedesId, fechas, modalidadesId, programasId), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechas(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioPCPByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void templateDownload(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> programasId = Utilidades.getListItems(json.get("programas"), "id");

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedesId);
        params.put("fechas", fechas);
        params.put("modalidadesId", modalidadesId);
        params.put("programasId", programasId);

        String query =
                "SELECT codigoGrupo,\n" +
                "       grupoNombre,\n" +
                "       fechaInicio,\n" +
                "       fechaFin,\n" +
                "       nivel,\n" +
                "       horario,\n" +
                "       cupo,\n" +
                "       totalInscritos,\n" +
                "       profesor\n" +
                "FROM VW_RPT_ReporteGruposPCP\n" +
                "WHERE sedeId IN(:sedesId)\n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
                "       AND (COALESCE(:programasId, 0) = 0 OR programaId IN (:programasId))\n" +
                "       AND pcp = 1\n" +
                "ORDER BY codigoGrupo, fechaInicio\n" +
                "OPTION(RECOMPILE)";

        String[] alColumnas = new String[]{"Código del grupo", "Nombre del grupo", "Fecha inicio", "Fecha fin", "Nivel", "Horario", "Cupo", "Alumnos inscritos", "Profesor"};

        excelController.downloadXlsx(response, "Reporte_grupos", query, alColumnas, params, "Grupos");
    }

    @RequestMapping(value = "/descargar/evidencia-fotografica", method = RequestMethod.POST)
    public void descargarEvidenciasFotograficas(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> programasId = Utilidades.getListItems(json.get("programas"), "id");

        descargarEvidenciaFotografica(programaGrupoDao.getIdsGruposPCP(sedesId, fechas, modalidadesId, programasId), response);
    }

    @RequestMapping(value = "/imprimir/archivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public void imprimirArchivos(@RequestBody HashMap<String, Object> body, HttpServletResponse response) throws Exception {
        int grupoId = hashId.decode((String) body.get("grupoId"));
        int archivoId = (Integer) body.get("archivoId");

        switch (archivoId) {
            case 1:
                descargarEvidenciaFotografica(Arrays.asList(grupoId), response);
                break;
            case 2: // Reporte de Asistencias
                descargarAsistencia(Arrays.asList(grupoId), response);
                break;
            case 3: // Reporte de Asistencias
                descargarCalificacion(Arrays.asList(grupoId), response);
                break;
        }
    }

    private void descargarEvidenciaFotografica(List<Integer> gruposId, HttpServletResponse response) throws Exception {
        String reportesLocation = environment.getProperty("environments.pixvs.reportes.location") + File.separator + "assets" + File.separator;

        if (gruposId.isEmpty()) {
            gruposId = Arrays.asList(-1);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("path", environment.getProperty("spring.storage.location"));
        params.put("Ids", gruposId);
        params.put("URL_LOGO", reportesLocation + "logo.png");

        getReporte("/modulos/programacion-academica/EvidenciaFotografica.jasper", "EvidenciaFotografica", params, response);
    }

    private void descargarAsistencia(List<Integer> gruposId, HttpServletResponse response) throws Exception {
        String reportesLocation = environment.getProperty("environments.pixvs.reportes.location") + File.separator + "assets" + File.separator;

        if (gruposId.isEmpty()) {
            gruposId = Arrays.asList(-1);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("Ids", gruposId);
        params.put("URL_LOGO", reportesLocation + "logo.png");

        getReporte("/modulos/control-escolar/Asistencias.jasper", "Asistencias", params, response);
    }

    @RequestMapping(value = "/descargar/asistencias", method = RequestMethod.POST)
    public void descargarAsistencias(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> programasId = Utilidades.getListItems(json.get("programas"), "id");

        descargarAsistencia(programaGrupoDao.getIdsGruposPCPAsistencias(sedesId, fechas, modalidadesId, programasId), response);
    }

    private void descargarCalificacion(List<Integer> gruposId, HttpServletResponse response) throws Exception {
        String reportesLocation = environment.getProperty("environments.pixvs.reportes.location") + File.separator + "assets" + File.separator;

        if (gruposId.isEmpty()) {
            gruposId = Arrays.asList(-1);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("Ids", gruposId);
        params.put("URL_LOGO", reportesLocation + "logo.png");

        getReporte("/modulos/control-escolar/Calificaciones.jasper", "Calificaciones", params, response);
    }

    @RequestMapping(value = "/descargar/calificaciones", method = RequestMethod.POST)
    public void descargarCalificaciones(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> programasId = Utilidades.getListItems(json.get("programas"), "id");

        descargarCalificacion(programaGrupoDao.getIdsGruposPCPAsistencias(sedesId, fechas, modalidadesId, programasId), response);
    }

    private void getReporte(String pathReporte, String nombreReporte, Map<String, Object> params, HttpServletResponse response) throws Exception {
        InputStream reporte = reporteService.generarJasperReport(pathReporte, params, ReporteServiceImpl.output.PDF, true);

        response.setContentType("application/pdf");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        org.apache.commons.io.IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();

        reporte.close();
    }
}