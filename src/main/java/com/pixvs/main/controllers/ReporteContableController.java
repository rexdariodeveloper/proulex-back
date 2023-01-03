package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_IM_TipoMovimiento;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_Estatus;
import com.pixvs.spring.services.ReporteService;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

@RestController
@RequestMapping("/api/v1/contable_inventarios")
public class ReporteContableController {


    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private ControlMaestroDao controlMaestroDao;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private Environment environment;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getKardex(ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", new ArrayList<>());
        datos.put("sedes", sucursalDao.findProjectedComboAllByActivoTrue());

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getKardex(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {

        String fechaIncio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        Integer sedeId = (Integer)((HashMap<String, Integer>) json.get("sede")).get("id");

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("fechaInicio", fechaIncio);
        filtros.put("fechaFin", fechaFin);
        filtros.put("sede", sedeId);

        return filtros;
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/contable/contable.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("fechaInicio", filtros.get("fechaInicio"));
        parameters.put("fechaFin", filtros.get("fechaFin"));
        parameters.put("empresa", cm.getValor());
        parameters.put("sede",filtros.get("sede"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contable.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLS(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/contable/contable.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("fechaInicio", filtros.get("fechaInicio"));
        parameters.put("fechaFin", filtros.get("fechaFin"));
        parameters.put("empresa", cm.getValor());
        parameters.put("sede",filtros.get("sede"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contable.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }
}
