package com.pixvs.main.controllers;

import com.pixvs.main.dao.BecaAlumnoRHDao;
import com.pixvs.main.dao.EntidadBecaDao;
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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/becas-sindicato")
public class ReporteBecasSindicatoController {

    @Autowired
    EntidadBecaDao entidadBecaDao;

    @Autowired
    BecaAlumnoRHDao becaAlumnoRHDao;

    @Autowired
    private ExcelController excelController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws Exception {
        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("entidades", entidadBecaDao.findListadoRHEntidadBecaProjected());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws Exception {
        List<String> entidadesId = Utilidades.getListItems(json.get("entidades"), "entidad");
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");

        return new JsonResponse(becaAlumnoRHDao.findListadoBecaAlumnoRH(entidadesId, fechaInicio, fechaFin), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void templateDownload(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<String> entidadesId = Utilidades.getListItems(json.get("entidades"), "entidad");
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");

        HashMap<String, Object> params = new HashMap<>();

        params.put("entidadesId", entidadesId);
        params.put("fechaInicio", fechaInicio);
        params.put("fechaFin", fechaFin);

        String query =
                "SELECT *\n" +
                "FROM BECAS_ALUMNOS_RH\n" +
                "WHERE SUBSTRING(NOMBREDESCUENTOUDG, 0, CHARINDEX(' ', NOMBREDESCUENTOUDG)) IN (:entidadesId)\n" +
                "      AND FECHAALTABECAUDG BETWEEN :fechaInicio AND :fechaFin\n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{"ID", "CODIGOPLX", "CODIGOBECAUDG", "CODIGOEMPLEADOUDG", "PATERNO", "MATERNO",
                "NOMBRE", "DESCUENTOUDG", "SEDEUDG", "NIVELUDG", "HORARIOUDG", "FECHAALTABECAUDG", "HORAALTABECAUDG",
                "CODCURUDG", "PARENTESCOUDG", "FIRMADIGITALUDG", "ESTATUSUDG", "CODIGOESTATUSUDG", "SEDEPLX", "NIVELPLX",
                "HORARIOPLX", "GRUPOPLX", "CODCURPLX", "FECHAINIPLX", "FECHAFINPLX", "FECHAAPLICACIONPLX", "FECHAEXPIRACIONBECAUDG",
                "FOLIOSIAPPLX", "CALPLX", "FECHACALPLX", "STATUSPLX", "CODIGOESTATUSPLX", "NOMBREDESCUENTOUDG", "ASISTENCIA", "DIASEMANA"};

        excelController.downloadXlsx(response, "Reporte Becas Sindicato", query, columnas, params, "Reporte");
    }
}