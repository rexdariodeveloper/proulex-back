package com.pixvs.main.controllers;

import com.pixvs.main.dao.InscripcionDao;
import com.pixvs.main.dao.PAModalidadDao;
import com.pixvs.main.dao.ProgramaGrupoDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.models.projections.Inscripcion.ReporteAvanceInscripcionesProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/avance-inscripciones")
public class ReporteAvanceInscripcionesController {

    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    PAModalidadDao modalidadDao;

    @Autowired
    InscripcionDao inscripcionDao;

    @Autowired
    private ExcelController excelController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        json.put("modalidades", modalidadDao.findComboAllByActivoTrueOrderByNombre());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws Exception {
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        HashMap<String, Object> fechaP = (HashMap<String, Object>) json.get("fechas");
        String fecha = fechaP != null ? (String) fechaP.get("fecha") : null;
        HashMap<String, Object> modalidad = (HashMap<String, Object>) json.get("modalidad");
        int modalidadId = modalidad != null ? (Integer) modalidad.get("id") : null;

        return new JsonResponse(inscripcionDao.fn_reporteAvanceInscripciones(sedesId, fecha, modalidadId), null, JsonResponse.STATUS_OK);
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
        List<Integer> sedesId = Utilidades.getListItems(json.get("sede"), "id");
        HashMap<String, Object> fechaP = (HashMap<String, Object>) json.get("fechas");
        String fecha = fechaP != null ? (String) fechaP.get("fecha") : null;
        HashMap<String, Object> modalidad = (HashMap<String, Object>) json.get("modalidad");
        int modalidadId = modalidad != null ? (Integer) modalidad.get("id") : null;
        String nombreModalidad = modalidadDao.findComboSimpleProjectedById(modalidadId).getNombre().toUpperCase();

        HashMap<String, Object> params = new HashMap<>();

        params.put("sedesId", sedesId);
        params.put("fecha", fecha);
        params.put("modalidadId", modalidadId);

        String query =
                "SELECT sede,\n" +
                "       inscripciones,\n" +
                "       ingresos,\n" +
                "       meta,\n" +
                "       avance\n" +
                "FROM VW_RptAvanceInscripciones\n" +
                "WHERE (COALESCE(:sedesId, 0) = 0 OR sedeId IN(:sedesId))\n" +
                "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') = :fecha\n" +
                "       AND modalidadId = :modalidadId\n" +
                "ORDER BY sede,\n" +
                "         avance\n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{"SEDE", nombreModalidad, "INGRESOS", "META", "% AVANCE"};
        String[] totales = new String[]{nombreModalidad, "INGRESOS", "META"};
        String[] columnasMoneda = new String[]{"INGRESOS"};

        excelController.downloadXlsx(response, "Reporte Avance de Inscripciones", query, columnas, totales, columnasMoneda, params, "Reporte");
    }
}