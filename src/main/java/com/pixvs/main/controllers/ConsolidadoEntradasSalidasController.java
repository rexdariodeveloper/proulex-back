package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlmacenDao;
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

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

@RestController
@RequestMapping("/api/v1/consolidado-entradas-salidas")
public class ConsolidadoEntradasSalidasController {

    @Autowired
    private AlmacenDao almacenDao;

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
        datos.put("almacenes", almacenDao.findProjectedComboAllByUsuarioPermisosId(idUsuario));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {
        HashMap<String, Integer> allArticulos = (HashMap<String, Integer>) json.get("tipoReporte");
        Integer tipo = allArticulos.get("id");

        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");
        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        ArrayList<HashMap<String, Integer>> allAlmacenes = (ArrayList<HashMap<String, Integer>>) json.get("almacenes");
        String almacenes = null;
        if (allAlmacenes != null) {
            almacenes = "";
            for (HashMap<String, Integer> registro : allAlmacenes) {
                almacenes += "|" + registro.get("id") + "|";
            }
        }

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("fechaCreacionDesde", fechaCreacionDesde);
        filtros.put("fechaCreacionHasta", fechaCreacionHasta);
        filtros.put("almacenes", (almacenes != null && almacenes.length() > 0)? almacenes : null);
        filtros.put("tipo", tipo == 1? 1 : 0);

        return filtros;
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/entradas/entradas.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());
        parameters.put("fechaInicio", filtros.get("fechaCreacionDesde"));
        parameters.put("fechaFin", filtros.get("fechaCreacionHasta"));
        parameters.put("almacenes",filtros.get("almacenes"));
        parameters.put("tipo",filtros.get("tipo"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Consolidado-entradas-salidas.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLS(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/entradas/entradas.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());
        parameters.put("fechaInicio", filtros.get("fechaCreacionDesde"));
        parameters.put("fechaFin", filtros.get("fechaCreacionHasta"));
        parameters.put("almacenes",filtros.get("almacenes"));
        parameters.put("tipo",filtros.get("tipo"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Consolidado-entradas-salidas.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }
}
