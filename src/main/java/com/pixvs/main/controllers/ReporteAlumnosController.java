package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.main.models.projections.Inscripcion.ReporteInscripcionesProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ReporteAlumnoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reporte-alumnos")
public class ReporteAlumnosController {

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;

    @Autowired
    PAModalidadDao modalidadDao;

    @Autowired
    InscripcionDao inscripcionDao;

    @Autowired
    AlumnoDao alumnoDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private PuntoVentaController puntoVentaController;

    @Autowired
    private CapturaCalificacionesController capturaCalificacionesController;

    @Autowired
    private CapturaAsistenciasController capturaAsistenciasController;

    @Autowired
    private KardexAlumnoController kardexAlumnoController;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("planteles", sucursalPlantelDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<Integer> plantelesId = Utilidades.getListItems(json.get("plantel"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> tiposInscripciones = Utilidades.getListItems(json.get("tiposInscripciones"), "id");
        String alumno = (String) json.get("alumno");

        return new JsonResponse(programaGrupoDao.getReporteAlumnos(sedesId, plantelesId, fechas, modalidadesId, !StringCheck.isNullorEmpty(alumno) ? alumno : null, tiposInscripciones), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/asistencias", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void descargarRptAsistencias(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<Integer> plantelesId = Utilidades.getListItems(json.get("plantel"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> tiposInscripciones = Utilidades.getListItems(json.get("tiposInscripciones"), "id");
        String alumno = (String) json.get("alumno");

        List<ReporteAlumnoProjection> registros = programaGrupoDao.getReporteAlumnos(sedesId, plantelesId, fechas, modalidadesId, !StringCheck.isNullorEmpty(alumno) ? alumno : null, tiposInscripciones);
        HashMap<Integer, String> grupos = new HashMap<>();

        for (ReporteAlumnoProjection registro : registros) {
            if (!grupos.containsKey(registro.getGrupoId())) {
                grupos.put(registro.getGrupoId(), registro.getGrupo());
            }
        }

        HashMap<String, InputStream> files = new HashMap<>();

        for (Map.Entry<Integer, String> grupo : grupos.entrySet()) {
            List<Object[]> resultsReporte = new ArrayList<>();
            ProgramaGrupo grupoTemp = programaGrupoDao.findById(grupo.getKey());

            List<Object[]> results = em
                    .createNativeQuery("EXEC [dbo].[sp_getReporteAsistencias] :sedeId, :plantelId, :pacId, :cicloId, :modalidadId, :fechaInicio, :codigoGrupo")
                    .setParameter("sedeId", grupoTemp.getSucursalId())
                    .setParameter("plantelId", grupoTemp.getSucursalPlantelId())
                    .setParameter("pacId", grupoTemp.getProgramacionAcademicaComercialId())
                    .setParameter("cicloId", grupoTemp.getPaCicloId())
                    .setParameter("modalidadId", grupoTemp.getPaModalidadId())
                    .setParameter("fechaInicio", grupoTemp.getFechaInicio())
                    .setParameter("codigoGrupo", grupoTemp.getCodigoGrupo())
                    .getResultList();

            for (Object[] result : results) {
                boolean incluir = false;

                for (ReporteAlumnoProjection registro : registros) {
                    if (result[14].equals(registro.getGrupo()) && result[21].equals(registro.getAlumnoCodigo())) {
                        incluir = true;
                    }
                }

                if (incluir) {
                    resultsReporte.add(result);
                }
            }

            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("Asistencias");
            capturaAsistenciasController.exportarGrupo(workbook, sheet, resultsReporte, 0);

            Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
            _sh.setAccessible(true);
            XSSFSheet xssfsheet = (XSSFSheet) _sh.get(sheet);
            xssfsheet.addIgnoredErrors(new CellRangeAddress(0, 9999, 0, 9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT);

            for (Integer i = 0; i < 50; i++) {
                sheet.setColumnWidth(i, 4000);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            files.put(grupo.getValue() + ".xlsx", new ByteArrayInputStream(outputStream.toByteArray()));
        }

        reporteService.downloadAsZip(response, files, "Reportes de asistencias");
    }

    @RequestMapping(value = "/exportar/calificaciones", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void descargarRptCalificaciones(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<Integer> plantelesId = Utilidades.getListItems(json.get("plantel"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> tiposInscripciones = Utilidades.getListItems(json.get("tiposInscripciones"), "id");
        String alumno = (String) json.get("alumno");

        List<ReporteAlumnoProjection> registros = programaGrupoDao.getReporteAlumnos(sedesId, plantelesId, fechas, modalidadesId, !StringCheck.isNullorEmpty(alumno) ? alumno : null, tiposInscripciones);
        HashMap<Integer, String> grupos = new HashMap<>();

        for (ReporteAlumnoProjection registro : registros) {
            if (!grupos.containsKey(registro.getGrupoId())) {
                grupos.put(registro.getGrupoId(), registro.getGrupo());
            }
        }

        HashMap<String, InputStream> files = new HashMap<>();

        for (Map.Entry<Integer, String> grupo : grupos.entrySet()) {
            List<Object[]> resultsReporte = new ArrayList<>();
            ProgramaGrupo grupoTemp = programaGrupoDao.findById(grupo.getKey());

            List<Object[]> results = em
                    .createNativeQuery("EXEC sp_getReporteCalificaciones :usuarioId, :programaIdiomaId, :grupoId")
                    .setParameter("usuarioId", usuarioId)
                    .setParameter("programaIdiomaId", grupoTemp.getProgramaIdiomaId())
                    .setParameter("grupoId", grupoTemp.getId())
                    .getResultList();

            for (Object[] result : results) {
                boolean incluir = false;

                for (ReporteAlumnoProjection registro : registros) {
                    if (result[16].equals(registro.getAlumnoCodigo())) {
                        incluir = true;
                    }
                }

                if (incluir) {
                    resultsReporte.add(result);
                }
            }

            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("Calificaciones");
            capturaCalificacionesController.exportarGrupo(workbook, sheet, resultsReporte, grupoTemp.getProgramaIdiomaId(),0);

            Field _sh = SXSSFSheet.class.getDeclaredField("_sh");
            _sh.setAccessible(true);
            XSSFSheet xssfsheet = (XSSFSheet) _sh.get(sheet);
            xssfsheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );

            Row r = sheet.getRow(0);

            if (r != null) {
                Integer last = new Integer(r.getLastCellNum());
                sheet.trackAllColumnsForAutoSizing();

                for (Integer i = 0; i < last; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            files.put(grupo.getValue() + ".xlsx", new ByteArrayInputStream(outputStream.toByteArray()));
        }

        reporteService.downloadAsZip(response, files, "Reportes de calificaciones");
    }

    @RequestMapping(value = "/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/imprimir/archivos", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public void imprimirArchivos(@RequestBody HashMap<String, Object> body, HttpServletResponse response) throws Exception {
        int inscripcionId = hashId.decode((String) body.get("inscripcionId"));
        int archivoId = (Integer) body.get("archivoId");

        ReporteInscripcionesProjection inscripcion = inscripcionDao.findInscripcionReporteProjectedById(inscripcionId);

        switch (archivoId) {
            case 3:
                kardexAlumnoController.descargarKardex(alumnoDao.findById(inscripcion.getAlumnoId()), false, response);
                break;

            case 4:
                JSONObject json = new JSONObject();
                json.put("alumnoId", inscripcion.getAlumnoId());
                json.put("grupoId", inscripcion.getGrupoId());

                capturaCalificacionesController.downloadBoleta(json, response);
                break;

            case 5:
                HashMap<String, Object> filtros = new HashMap<>();
                filtros.put("ordenesVentaIdsStr", String.valueOf(inscripcion.getNotaVentaId()));
                filtros.put("sucursalId", inscripcion.getSedeInscripcionId());

                puntoVentaController.descargarPdf(filtros, response);
                break;
        }
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void descargarReporte(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<Integer> plantelesId = Utilidades.getListItems(json.get("plantel"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");
        List<Integer> tiposInscripciones = Utilidades.getListItems(json.get("tiposInscripciones"), "id");
        String alumno = (String) json.get("alumno");

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedesId);
        params.put("plantelesId", plantelesId);
        params.put("fechas", fechas);
        params.put("modalidadesId", modalidadesId);
        params.put("tiposInscripciones", tiposInscripciones);
        params.put("alumno", !StringCheck.isNullorEmpty(alumno) ? alumno : null);

        String query =
                "SELECT alumnoCodigo, alumno, sedeInscripcion, notaVenta, tipoInscripcion, sedeGrupo, grupo, fechaInicio, fechaFin, estatusAlumno\n" +
                        "FROM (\n" +
                        "SELECT *, 'LOCAL' AS tipoInscripcion\n" +
                        "FROM VW_RPT_ALUMNOS\n" +
                        "WHERE sedeInscripcionId IN(:sedesId)\n" +
                        "    AND (COALESCE(:plantelesId, 0) = 0 OR plantelInscripcionId IN(:plantelesId))\n" +
                        "    AND 1 IN (:tiposInscripciones)\n" +
                        "    AND sedeInscripcionId = sedeGrupoId\n" +
                        "\n" +
                        "UNION ALL\n" +
                        "\n" +
                        "SELECT *, 'MULTISEDE EVÍA' AS tipoInscripcion\n" +
                        "FROM VW_RPT_ALUMNOS\n" +
                        "WHERE sedeInscripcionId IN(:sedesId)\n" +
                        "    AND (COALESCE(:plantelesId, 0) = 0 OR plantelInscripcionId IN(:plantelesId))\n" +
                        "    AND 2 IN (:tiposInscripciones)\n" +
                        "    AND sedeInscripcionId != sedeGrupoId\n" +
                        "\n" +
                        "UNION ALL\n" +
                        "\n" +
                        "SELECT *, 'MULTISEDE RECIBE' AS tipoInscripcion\n" +
                        "FROM VW_RPT_ALUMNOS\n" +
                        "WHERE sedeGrupoId IN(:sedesId)\n" +
                        "    AND (COALESCE(:plantelesId, 0) = 0 OR plantelGrupoId IN(:sedesId))\n" +
                        "    AND 3 IN (:tiposInscripciones)\n" +
                        "    AND sedeInscripcionId != sedeGrupoId\n" +
                        ") AS todo\n" +
                        "WHERE FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                        "    AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
                        "    AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas(alumno, :alumno) = 1 THEN 1 ELSE 0 END END = 1\n" +
                        "ORDER BY alumnoCodigo,\n" +
                        "    grupo\n" +
                        "OPTION(RECOMPILE)";

        String[] allColumnas = new String[]{
                "CÓDIGO", "ALUMNO", "SEDE INSCRIPCIÓN", "NOTA VENTA", "TIPO INSCRIPCIÓN",
                "SEDE GRUPO", "GRUPO", "FECHA INICIO", "FECHA FIN", "ESTATUS"
        };

        excelController.downloadXlsx(response, "Reporte de Alumnos", query, allColumnas, params, "Reporte");
    }
}