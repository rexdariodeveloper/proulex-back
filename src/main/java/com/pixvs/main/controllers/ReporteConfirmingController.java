package com.pixvs.main.controllers;

import com.pixvs.main.dao.OrdenCompraDao;
import com.pixvs.main.dao.ProveedorDao;
import com.pixvs.main.dao.ReportesDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.JsonResponse;
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

@RestController
@RequestMapping("/api/v1/confirming")
public class ReporteConfirmingController {

    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private ReportesDao reportesDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private Environment environment;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws Exception {
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", new ArrayList<>());
        datos.put("sucursales", sucursalDao.findProjectedComboAllByActivoTrue());
        datos.put("proveedores", proveedorDao.findProjectedComboVistaAllByActivoTrue());

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        return new JsonResponse(
                reportesDao.reporteConfirming((String) filtros.get("fechaInicio"),
                        (String) filtros.get("fechaFin"),
                        (String) filtros.get("sedes"),
                        (String) filtros.get("proveedores"))
        , null, JsonResponse.STATUS_OK);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {

        ArrayList<HashMap<String, Integer>> allSedes = (ArrayList<HashMap<String, Integer>>) json.get("sedes");
        String sedes = null;
        if (allSedes != null) {
            sedes = "";
            for (HashMap<String, Integer> registro : allSedes) {
                sedes += "|" + registro.get("id") + "|";
            }
        }

        ArrayList<HashMap<String, Integer>> allProveedores = (ArrayList<HashMap<String, Integer>>) json.get("proveedores");
        String proveedores = null;
        if (allProveedores != null) {
            proveedores = "";
            for (HashMap<String, Integer> registro : allProveedores) {
                proveedores += "|" + registro.get("id") + "|";
            }
        }

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("fechaInicio", json.get("fechaInicio"));
        filtros.put("fechaFin", json.get("fechaFin"));
        filtros.put("sedes", (sedes != null && sedes.length() > 0)? sedes : null);
        filtros.put("proveedores", (proveedores != null && proveedores.length() > 0)? proveedores : null);

        return filtros;
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, SQLException, JRException, ParseException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/confirming/confirming.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());
        parameters.put("fechaInicio", filtros.get("fechaInicio"));
        parameters.put("fechaFin", filtros.get("fechaFin"));
        parameters.put("sedes",filtros.get("sedes"));
        parameters.put("proveedores",filtros.get("proveedores"));
        parameters.put("usuario",usuarioId);

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=confirming.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLS(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, SQLException, JRException, ParseException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/confirming/confirming.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());
        parameters.put("fechaInicio", filtros.get("fechaInicio"));
        parameters.put("fechaFin", filtros.get("fechaFin"));
        parameters.put("sedes",filtros.get("sedes"));
        parameters.put("proveedores",filtros.get("proveedores"));
        parameters.put("usuario",usuarioId);

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Confirming.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }
}
