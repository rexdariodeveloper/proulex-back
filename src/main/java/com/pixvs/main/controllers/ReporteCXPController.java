package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.CXPPago;
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
import org.apache.commons.lang3.ArrayUtils;
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
@RequestMapping("/api/v1/cxp")
public class ReporteCXPController {

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private ProveedorDao proveedorDao;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private Environment environment;

    @Autowired
    private CXPPagoDao cxpPagoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", new ArrayList<>());
        datos.put("sedes", sucursalDao.findProjectedComboAllByActivoTrue());
        datos.put("proveedores", proveedorDao.findProjectedComboVistaAllByActivoTrue());
        datos.put("monedas", monedaDao.findProjectedComboAllByActivoTrue());

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        return new JsonResponse(
                cxpPagoDao.getReporteCXP(
                        (String)  filtros.get("fechaFin"),
                        (String)  filtros.get("sedes"),
                        (String)  filtros.get("proveedores"),
                        (String)  filtros.get("documento"),
                        (Integer) filtros.get("moneda")
                ), null, JsonResponse.STATUS_OK);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {
        Object fechaInicio = json.get("fechaInicio");
        Object fechaFin    = json.get("fechaFin");

        ArrayList<HashMap<String, Object>> allSedes = (ArrayList<HashMap<String, Object>>) json.get("sedes");
        String sedes = arrayToSring(allSedes);

        ArrayList<HashMap<String, Object>> allProveedores = (ArrayList<HashMap<String, Object>>) json.get("proveedores");
        String proveedores = arrayToSring(allProveedores);

        String documento = (String) json.get("numeroDocumento");
        HashMap<String, Object> object = (HashMap<String, Object>) json.get("moneda");
        Integer moneda = object != null? (Integer) object.get("id") : null;

        HashMap<String, Object> filtros = new HashMap<>();
        //filtros.put("fechaInicio",fechaInicio);
        filtros.put("fechaFin", fechaFin);
        filtros.put("sedes", sedes);
        filtros.put("proveedores", proveedores);
        filtros.put("documento", documento);
        filtros.put("moneda", moneda);

        filtros.put("filtroSedes", arrayToFiltro(allSedes, "nombre"));
        filtros.put("filtroProveedores", arrayToFiltro(allProveedores, "nombre"));
        filtros.put("filtroMoneda", object != null? (String) object.get("nombre") : "Todas");

        return filtros;
    }

    private String arrayToSring(ArrayList<HashMap<String, Object>> data){
        return arrayToString(data,"id", "|");
    }

    private String arrayToString(ArrayList<HashMap<String, Object>> data, String property, String glue){
        String joined = null;
        if (data == null) return joined;
        joined = "";
        for (HashMap<String, Object> reg : data) {
            joined += glue + reg.get(property).toString() + glue;
        }
        return joined;
    }

    private String arrayToFiltro(ArrayList<HashMap<String, Object>> data, String property){
        if (data == null || data.size() == 0) return "Todos";
        if(data.size() == 1) return data.get(0).get(property).toString();
        if(data.size() > 1) return data.size() + " seleccionados";
        return null;
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/cxp/cxp.jasper";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("fechaInicio", filtros.get("fechaInicio"));
        parameters.put("fechaFin", filtros.get("fechaFin"));
        parameters.put("sedes",filtros.get("sedes"));
        parameters.put("proveedores",filtros.get("proveedores"));
        parameters.put("documento",filtros.get("documento"));
        parameters.put("moneda",filtros.get("moneda"));

        parameters.put("filtroSedes",filtros.get("filtroSedes"));
        parameters.put("filtroProveedores",filtros.get("filtroProveedores"));
        parameters.put("filtroMoneda",filtros.get("filtroMoneda"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CXP.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLS(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/cxp/cxp.jasper";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("fechaInicio", filtros.get("fechaInicio"));
        parameters.put("fechaFin", filtros.get("fechaFin"));
        parameters.put("sedes",filtros.get("sedes"));
        parameters.put("proveedores",filtros.get("proveedores"));
        parameters.put("documento",filtros.get("documento"));
        parameters.put("moneda",filtros.get("moneda"));

        parameters.put("filtroSedes",filtros.get("filtroSedes"));
        parameters.put("filtroProveedores",filtros.get("filtroProveedores"));
        parameters.put("filtroMoneda",filtros.get("filtroMoneda"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CXP.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }
}
