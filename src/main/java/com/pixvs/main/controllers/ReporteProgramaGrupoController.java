package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.Alumno.AlumnoOrdenPagoProjection;
import com.pixvs.main.models.projections.AlumnoAsistencia.AlumnoAsistenciaEditarProjection;
import com.pixvs.main.models.projections.AlumnoExamenCalificacion.AlumnoExamenCalificacionProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionListadoGrupoProjection;
import com.pixvs.main.models.projections.Inscripcion.InscripcionProjection;
import com.pixvs.main.models.projections.ListadoPrecioDetalle.ListadoPrecioDetalleEditarProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloFechaProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.*;
import com.pixvs.main.models.projections.ProgramaGrupoListadoClase.ProgramaGrupoListadoClaseEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEmpleadoProjection;
import com.pixvs.main.services.ProgramasGruposAlertaService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.RolDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioEditarProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.poi.ss.util.CellUtil.createCell;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/reporte-grupos")
public class ReporteProgramaGrupoController {

    @Autowired
    private Environment environment;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private PACicloDao paCicloDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ReporteService reporteService;

    @Autowired
    PAModalidadDao modalidadDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos",new ArrayList<>());
        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        List<ProgramaGrupoReporteProjection> reporte = programaGrupoDao.getReporteGrupos(sedesId, fechas, modalidadesId);

        return new JsonResponse(reporte, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @GetMapping(value = "/listados/programaciones/{sedeId}")
    public JsonResponse getProgramacionBySede(@PathVariable("sedeId") Integer sedeId){
        HashMap<String, Object> json = new HashMap<>();
        SucursalComboProjection sucursal = sucursalDao.findProjectedComboById(sedeId);
        if(sucursal.getNombre().equals("JOBS ") || sucursal.getNombre().equals("JOBS SEMS")){
            json.put("ciclos", paCicloDao.findProjectedComboAllBySucursalId(sedeId, null));
            json.put("programaciones",new ArrayList<>());
        }else {
            json.put("ciclos",new ArrayList<>());
            json.put("programaciones", programacionAcademicaComercialDao.findProjectedComboAllBySedeIdOrPlantelId(sedeId, null));
        }

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping(value = "/listados/modalidades/{sedeId}/{paId}/{cicloId}")
    public JsonResponse getModalidadesBySedeAndCiclo(@PathVariable("sedeId") Integer sedeId, @PathVariable("paId") Integer paId, @PathVariable("cicloId") Integer cicloId) {
        HashMap<String, Object> json = new HashMap<>();
        json.put("modalidades", paModalidadDao.findProjectedComboSimpleAllBySedeIdAndPaId(sedeId, null, paId, cicloId));
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping(value = "/listados/fechas/{sedeId}/{paId}/{cicloId}/{modalidadId}")
    public JsonResponse getFechasBySedeAndCicloAndModalidad(@PathVariable("sedeId") Integer sedeId, @PathVariable("paId") Integer paId, @PathVariable("cicloId") Integer cicloId, @PathVariable("modalidadId") Integer modalidadId) {
        HashMap<String, Object> json = new HashMap<>();
        json.put("fechas", programaGrupoDao.findFechaInicioBySedeAndPAOrCicloAndModalidad(sedeId, null, paId, cicloId, modalidadId));
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping(value = "/listados/fechasFin/{sedeId}/{paId}/{cicloId}/{modalidadId}")
    public JsonResponse getFechasFinBySedeAndCicloAndModalidad(@PathVariable("sedeId") Integer sedeId, @PathVariable("paId") Integer paId, @PathVariable("cicloId") Integer cicloId, @PathVariable("modalidadId") Integer modalidadId) {
        HashMap<String, Object> json = new HashMap<>();
        json.put("fechas", programaGrupoDao.findFechaFinBySedeAndPAOrCicloAndModalidad(sedeId, null, paId, cicloId, modalidadId));
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void templateDownload(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, ParseException {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedesId);
        params.put("fechas", fechas);
        params.put("modalidadesId", modalidadesId);

        String query = "SELECT codigoGrupo, grupoNombre, plantel, fechaInicio, fechaFin, nivel, horario, cupo, " +
                "totalInscritos, profesor, tipo " +
                "FROM [dbo].[VW_RPT_ReporteGrupos] " +
                "WHERE sedeId IN(:sedesId)\n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
                "ORDER BY codigoGrupo, fechaInicio";

        String[] alColumnas = new String[]{"Codigo del grupo", "Nombre del grupo", "Plantel", "Fecha inicio", "Fecha fin", "Nivel", "Horario", "Cupo", "Alumnos inscritos", "Profesor", "Tipo de grupo"};

        excelController.downloadXlsx(response, "Reporte_grupos", query, alColumnas, params, "Grupos");
    }

    @RequestMapping(value="/exportar/resumen/pdf/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public JsonResponse exportarResumenPDF(@PathVariable("id") Integer id, HttpServletResponse response, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        UsuarioEditarProjection usuario = usuarioDao.findProjectedById(usuarioId);
        ProgramaGrupo grupo = programaGrupoDao.findById(id);

        InputStream reporte = getResumen(id, usuario.getNombreCompleto());

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+grupo.getCodigoGrupo()+".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        org.apache.tomcat.util.http.fileupload.IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
        return null;
    }

    @RequestMapping(value = "/exportar/resumen/zip", method = RequestMethod.POST)
    public void exportarResumenZIP(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws IOException, ParseException, SQLException, JRException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        UsuarioEditarProjection usuario = usuarioDao.findProjectedById(usuarioId);
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        List<ProgramaGrupoReporteProjection> grupos = programaGrupoDao.getReporteGrupos(sedesId, fechas, modalidadesId);
        HashMap<String,InputStream> files = new HashMap<>();
        for (ProgramaGrupoReporteProjection grupo : grupos){
            InputStream reporte = getResumen(grupo.getId(), usuario.getNombreCompleto());
            files.put(grupo.getCodigoGrupo(), reporte);
        }
        reporteService.downloadAsZip(response, files,"Resumen grupos");
    }

    private CellStyle createStyle(XSSFWorkbook workbook, Short background, Short fontColor, boolean bold, boolean locked){
        CellStyle style = workbook.createCellStyle();
        if (background != null) {
            style.setFillForegroundColor(background);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        XSSFFont font = workbook.createFont();
        if (fontColor != null) { font.setColor(fontColor); }
        if (bold) { font.setBold(true); }
        style.setLocked(locked);
        style.setFont(font);
        return style;
    }

    private void createRow(XSSFSheet sheet, Integer rowNumber, String[] values, CellStyle style) {
        XSSFRow row = sheet.createRow(rowNumber);
        Integer index = 0;
        for (String value : values) {
            createCell(row, index, value, style);
            index++;
        }
    }

    private InputStream getResumen(Integer grupoId, String usuario) throws SQLException, IOException, JRException {
        Map<String, Object> params = new HashMap<>();
        params.put("path", environment.getProperty("environments.pixvs.front.url"));
        params.put("grupoId", grupoId);
        params.put("usuario", usuario);

        return reporteService.generarJasperReport("/modulos/programacion-academica/Grupos.jasper", params, ReporteServiceImpl.output.PDF, true);
    }
}

