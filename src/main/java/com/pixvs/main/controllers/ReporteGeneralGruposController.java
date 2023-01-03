package com.pixvs.main.controllers;

import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.ProgramaGrupoDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.dao.SucursalPlantelDao;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.ExcelExportadoHoja;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte-general-grupos")
public class ReporteGeneralGruposController {

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;

    @Autowired
    private ExcelController excelController;

    @Autowired
    PAModalidadDao modalidadDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
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
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = sede != null ? (Integer) sede.get("id") : null;
        List<Integer> plantelesId = Utilidades.getListItems(json.get("plantel"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        return new JsonResponse(programaGrupoDao.getReporteGeneralGrupos(sedeId, plantelesId, fechas, modalidadesId), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) {
        int anio = (Integer) json.get("anio");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioByAnioAndModalidadId(anio, modalidadesId));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void templateDownload(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, ParseException {
        HashMap<String, Object> sede = (HashMap<String,Object>) json.get("sede");
        Integer sedeId = sede != null ? (Integer) sede.get("id") : null;
        List<Integer> plantelesId = Utilidades.getListItems(json.get("plantel"), "id");
        List<String> fechas = Utilidades.getListItems(json.get("fechas"), "fecha");
        List<Integer> modalidadesId = Utilidades.getListItems(json.get("modalidad"), "id");

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedeId);
        params.put("plantelesId", plantelesId);
        params.put("fechas", fechas);
        params.put("modalidadesId", modalidadesId);

        String query = "SELECT SUM(totalAlumnos) AS totalAlumnos,\n" +
                "       SUM(totalGrupos) AS totalGrupos,\n" +
                "       plantelGrupo,\n" +
                "       modalidad,\n" +
                "       horario,\n" +
                "       nivel\n" +
                "FROM\n" +
                "(\n" +
                "    SELECT DISTINCT\n" +
                "           COUNT(alumnoId) OVER(PARTITION BY grupoId, plantelGrupo, modalidad, horario, nivel) AS totalAlumnos,\n" +
                "           COUNT(grupoId) OVER(PARTITION BY alumnoId, plantelGrupo, modalidad, horario, nivel) AS totalGrupos,\n" +
                "           plantelGrupo,\n" +
                "           modalidad,\n" +
                "           horario,\n" +
                "           nivel,\n" +
                "           grupoId\n" +
                "    FROM VW_RPT_ReporteGeneralGruposAlumnos\n" +
                "    WHERE sedeGrupoId = :sedesId\n" +
                "       AND (COALESCE(:plantelesId, 0) = 0 OR plantelGrupoId IN (:plantelesId)) \n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas) \n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId)) \n" +
                ") AS todo\n" +
                "GROUP BY nivel,\n" +
                "         horario,\n" +
                "         modalidad,\n" +
                "         plantelGrupo\n" +
                "ORDER BY nivel,\n" +
                "         horario,\n" +
                "         modalidad,\n" +
                "         plantelGrupo\n" +
                "OPTION(RECOMPILE)";

        String[] allColumnas = new String[]{"TOTAL DE ALUMNOS", "TOTAL DE GRUPOS", "PLANTEL DEL GRUPO", "MODALIDAD", "HORARIO", "NIVEL"};

        ArrayList<ExcelExportadoHoja> allHojas = new ArrayList<>();

        ExcelExportadoHoja hojaGeneral = new ExcelExportadoHoja();
        hojaGeneral.setNombreHoja("Reporte General");
        hojaGeneral.setQuery(query);
        hojaGeneral.setColumnas(allColumnas);
        hojaGeneral.setQueryParameters(params);
        hojaGeneral.setColumnasAnchosAuto(true);
        allHojas.add(hojaGeneral);

        query = "SELECT SUM(totalAlumnos) AS totalAlumnos, SUM(totalGrupos) AS totalGrupos, horario FROM (\n" +
                "SELECT SUM(totalAlumnos) AS totalAlumnos,\n" +
                "       SUM(totalGrupos) AS totalGrupos,\n" +
                "       plantelGrupo,\n" +
                "       modalidad,\n" +
                "       horario,\n" +
                "       nivel\n" +
                "FROM\n" +
                "(\n" +
                "    SELECT DISTINCT\n" +
                "           COUNT(alumnoId) OVER(PARTITION BY grupoId, plantelGrupo, modalidad, horario, nivel) AS totalAlumnos,\n" +
                "           COUNT(grupoId) OVER(PARTITION BY alumnoId, plantelGrupo, modalidad, horario, nivel) AS totalGrupos,\n" +
                "           plantelGrupo,\n" +
                "           modalidad,\n" +
                "           horario,\n" +
                "           nivel\n" +
                "    FROM VW_RPT_ReporteGeneralGruposAlumnos\n" +
                "    WHERE sedeGrupoId = :sedesId\n" +
                "       AND (COALESCE(:plantelesId, 0) = 0 OR plantelGrupoId IN (:plantelesId)) \n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas) \n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId)) \n" +
                ") AS todo\n" +
                "GROUP BY nivel,\n" +
                "         horario,\n" +
                "         modalidad,\n" +
                "         plantelGrupo\n" +
                ") AS agrupado\n" +
                "GROUP BY horario\n" +
                "ORDER BY horario\n" +
                "OPTION(RECOMPILE)";

        allColumnas = new String[]{"TOTAL DE ALUMNOS", "TOTAL DE GRUPOS", "HORARIO"};

        ExcelExportadoHoja hojaHorario = new ExcelExportadoHoja();
        hojaHorario.setNombreHoja("Total por Horario");
        hojaHorario.setQuery(query);
        hojaHorario.setColumnas(allColumnas);
        hojaHorario.setQueryParameters(params);
        hojaHorario.setColumnasAnchosAuto(true);
        allHojas.add(hojaHorario);

        query = "SELECT SUM(totalAlumnos) AS totalAlumnos, SUM(totalGrupos) AS totalGrupos, nivel FROM (\n" +
                "SELECT SUM(totalAlumnos) AS totalAlumnos,\n" +
                "       SUM(totalGrupos) AS totalGrupos,\n" +
                "       plantelGrupo,\n" +
                "       modalidad,\n" +
                "       horario,\n" +
                "       nivel\n" +
                "FROM\n" +
                "(\n" +
                "    SELECT DISTINCT\n" +
                "           COUNT(alumnoId) OVER(PARTITION BY grupoId, plantelGrupo, modalidad, horario, nivel) AS totalAlumnos,\n" +
                "           COUNT(grupoId) OVER(PARTITION BY alumnoId, plantelGrupo, modalidad, horario, nivel) AS totalGrupos,\n" +
                "           plantelGrupo,\n" +
                "           modalidad,\n" +
                "           horario,\n" +
                "           nivel\n" +
                "    FROM VW_RPT_ReporteGeneralGruposAlumnos\n" +
                "    WHERE sedeGrupoId = :sedesId\n" +
                "       AND (COALESCE(:plantelesId, 0) = 0 OR plantelGrupoId IN (:plantelesId)) \n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas) \n" +
                "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId)) \n" +
                ") AS todo\n" +
                "GROUP BY nivel,\n" +
                "         horario,\n" +
                "         modalidad,\n" +
                "         plantelGrupo\n" +
                ") AS agrupado\n" +
                "GROUP BY nivel\n" +
                "ORDER BY nivel\n" +
                "OPTION(RECOMPILE)";

        allColumnas = new String[]{"TOTAL DE ALUMNOS", "TOTAL DE GRUPOS", "NIVEL"};

        ExcelExportadoHoja hojaNivel = new ExcelExportadoHoja();
        hojaNivel.setNombreHoja("Total por Nivel");
        hojaNivel.setQuery(query);
        hojaNivel.setColumnas(allColumnas);
        hojaNivel.setQueryParameters(params);
        hojaNivel.setColumnasAnchosAuto(true);
        allHojas.add(hojaNivel);

        excelController.downloadXlsxMultipleSheet(response, "Reporte_General_Grupos", allHojas, false);
    }
}