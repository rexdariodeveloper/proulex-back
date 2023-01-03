package com.pixvs.main.controllers;

import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.ProgramaGrupoDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.ExcelExportadoHoja;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import nonapi.io.github.classgraph.json.JSONSerializer;
import org.apache.poi.ss.usermodel.IndexedColors;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/eficiencia-grupos")
public class ReporteEficienciaGruposController {

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private SucursalDao sucursalDao;

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
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());
        json.put("horarios", programaGrupoDao.getHorariosRptEficienciaGrupos());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws SQLException {
        List<Integer> sedesIdP = Utilidades.getListItems(json.get("sede"), "id");
        String sedesId = "";

        for (int i = 0; i < sedesIdP.size(); i++) {
            sedesId += "'" + sedesIdP.get(i) + "'" + ((i+1) < sedesIdP.size() ? ", " : "");
        }

        HashMap<String, Object> fechaP = (HashMap<String, Object>) json.get("fechas");
        String fecha = fechaP != null ? (String) fechaP.get("fecha") : null;

        HashMap<String, Object> modalidad = (HashMap<String, Object>) json.get("modalidad");
        int modalidadId = modalidad != null ? (Integer) modalidad.get("id") : null;

        String queryPorHorario = programaGrupoDao.getQueryRptEficienciaGruposPorHorario(sedesId, fecha, String.valueOf(modalidadId));
        List<String> horarios = programaGrupoDao.getHorariosRptEficienciaGrupos();
        String[] columnasPorHorario = new String[5 + horarios.size()];

        columnasPorHorario[0] = "Sede";
        columnasPorHorario[1] = "Curso";
        columnasPorHorario[2] = "Nivel";
        columnasPorHorario[3] = "Grupo";
        columnasPorHorario[4] = "Fecha";

        for (int i = 5; i < columnasPorHorario.length; i++) {
            columnasPorHorario[i] = horarios.get(i - 5);
        }

        List<Object> data = excelController.getQueryResultList(queryPorHorario, null);

        List<HashMap<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            HashMap<String, Object> dataMap = new HashMap<>();

            for (int e = 0; e < columnasPorHorario.length; e++) {
                dataMap.put(columnasPorHorario[e], ((Object[]) data.get(i))[e]);
            }

            result.add(dataMap);
        }

        return new JsonResponse(result, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/listados/getFechas", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody JSONObject json) {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        int anio = (Integer) json.get("anio");
        int modalidadId = (Integer) json.get("modalidad");

        HashMap<String, Object> res = new HashMap<>();

        res.put("fechas", programaGrupoDao.findFechasInicioBySedeAnioModalidadId(sedesId, anio, Arrays.asList(modalidadId)));

        return new JsonResponse(res, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void templateDownload(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, ParseException {
        List<Integer> sedesIdP = Utilidades.getListItems(json.get("sede"), "id");
        String sedesId = "";

        for (int i = 0; i < sedesIdP.size(); i++) {
            sedesId += "'" + sedesIdP.get(i) + "'" + ((i+1) < sedesIdP.size() ? ", " : "");
        }

        HashMap<String, Object> fechaP = (HashMap<String, Object>) json.get("fechas");
        String fecha = fechaP != null ? (String) fechaP.get("fecha") : null;

        HashMap<String, Object> modalidad = (HashMap<String, Object>) json.get("modalidad");
        int modalidadId = modalidad != null ? (Integer) modalidad.get("id") : null;

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedesIdP);
        params.put("fecha", fecha);
        params.put("modalidadId", modalidadId);

        ArrayList<ExcelExportadoHoja> allHojas = new ArrayList<>();

        String queryPorHorario = programaGrupoDao.getQueryRptEficienciaGruposPorHorario(sedesId, fecha, String.valueOf(modalidadId));
        List<String> horarios = programaGrupoDao.getHorariosRptEficienciaGrupos();
        String[] columnasPorHorario = new String[5 + horarios.size()];
        String[] totalesPorHorario = new String[horarios.size()];

        columnasPorHorario[0] = "Sede";
        columnasPorHorario[1] = "Curso";
        columnasPorHorario[2] = "Nivel";
        columnasPorHorario[3] = "Grupo";
        columnasPorHorario[4] = "Fecha";

        for (int i = 5; i < columnasPorHorario.length; i++) {
            columnasPorHorario[i] = horarios.get(i - 5);
            totalesPorHorario[i - 5] = columnasPorHorario[i];
        }

        HashMap<String, Short> coloresPorValor = new HashMap<>();

        for (int i = 0; i <= 6; i++) {
            if (i == 0) {
                coloresPorValor.put(String.valueOf(i), IndexedColors.WHITE.getIndex());
            } else if (i < 4) {
                coloresPorValor.put(String.valueOf(i), IndexedColors.RED.getIndex());
            } else if (i < 6) {
                coloresPorValor.put(String.valueOf(i), IndexedColors.LIGHT_YELLOW.getIndex());
            } else {
                coloresPorValor.put(String.valueOf(i), IndexedColors.LIGHT_GREEN.getIndex());
            }
        }

        coloresPorValor.put(String.valueOf(-1), IndexedColors.LIGHT_GREEN.getIndex());

        ExcelExportadoHoja hojaEficiencias = new ExcelExportadoHoja();
        hojaEficiencias.setNombreHoja("Eficiencias");
        hojaEficiencias.setQuery(queryPorHorario);
        hojaEficiencias.setColumnas(columnasPorHorario);
        hojaEficiencias.setTotalesGenerales(totalesPorHorario);
        hojaEficiencias.setQueryParameters(null);
        hojaEficiencias.setColumnasAnchosAuto(true);
        hojaEficiencias.setColoresPorValor(coloresPorValor);
        hojaEficiencias.setImprimirCero(false);
        hojaEficiencias.setImprimirBorde(true);
        allHojas.add(hojaEficiencias);

        String queryPorSede = programaGrupoDao.getQueryRptEficienciaGruposPorSede(sedesId, fecha, String.valueOf(modalidadId));
        int maxInscritos = programaGrupoDao.getMaxInscritosRptEficienciaGrupos(sedesIdP, fecha, modalidadId);
        String[] columnasPorSede = new String[maxInscritos + 3];
        String[] totalesPorSede = new String[maxInscritos + 2];

        columnasPorSede[0] = "Sede";
        columnasPorSede[columnasPorSede.length - 1] = "Total grupos";
        totalesPorSede[totalesPorSede.length - 1] = columnasPorSede[columnasPorSede.length - 1];

        for (int i = 0; i <= maxInscritos; i++) {
            columnasPorSede[i + 1] = String.valueOf(i);
            totalesPorSede[i] = columnasPorSede[i + 1];
        }

        HashMap<String, Short> coloresPorColumna = new HashMap<>();

        for (int i = 0; i <= 6; i++) {
            if (i < 4) {
                coloresPorColumna.put(String.valueOf(i), IndexedColors.RED.getIndex());
            } else if (i < 6) {
                coloresPorColumna.put(String.valueOf(i), IndexedColors.LIGHT_YELLOW.getIndex());
            } else {
                coloresPorColumna.put(String.valueOf(i), IndexedColors.LIGHT_GREEN.getIndex());
            }
        }

        coloresPorColumna.put(String.valueOf(-1), IndexedColors.LIGHT_GREEN.getIndex());

        ExcelExportadoHoja hojaResumen = new ExcelExportadoHoja();
        hojaResumen.setNombreHoja("Resumen");
        hojaResumen.setQuery(queryPorSede);
        hojaResumen.setColumnas(columnasPorSede);
        hojaResumen.setTotalesGenerales(totalesPorSede);
        hojaResumen.setQueryParameters(null);
        hojaResumen.setColumnasAnchosAuto(true);
        hojaResumen.setColoresPorColumna(coloresPorColumna);
        hojaResumen.setImprimirCero(false);
        hojaResumen.setImprimirBorde(true);
        allHojas.add(hojaResumen);

        excelController.downloadXlsxMultipleSheet(response, "Reporte_Eficiencia_Grupos", allHojas, false);
    }
}