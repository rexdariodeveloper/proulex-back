package com.pixvs.main.controllers;

import com.pixvs.main.dao.CXPFacturaDao;
import com.pixvs.main.dao.MonedaDao;
import com.pixvs.main.dao.ProveedorDao;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboVistaProjection;
import com.pixvs.spring.controllers.ExcelController;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

class TiposReportes {
    public static final int DETALLE = 1;
    public static final int RESUMEN = 2;
}
class TiposSaldos {
    public static final int TODOS = 1;
    public static final int VENCIDOS = 2;
    public static final int POR_VENCER = 3;
}

/**
 * Created by Angel Daniel Hernández Silva on 16/10/2020.
 */
@RestController
@RequestMapping("/api/v1/reporte-antiguedad-saldos")
public class ReporteAntiguedadSaldos {

    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private CXPFacturaDao cxpFacturaDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private Environment environment;

    @RequestMapping(value = "/filtros", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltros(){

        List<ProveedorComboVistaProjection> proveedores = proveedorDao.findProjectedComboVistaAllBy();
        List<CXPFacturaComboProjection> facturas = cxpFacturaDao.findProjectedComboAllByEstatusIdIn(Arrays.asList(
                ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.ABIERTA,
                ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO,
                ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_EN_PROCESO,
                ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PARCIAL,
                ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.PAGO_PROGRAMADO_PARCIAL
        ));
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();

        HashMap<String,Object> data = new HashMap<>();
        data.put("proveedores",proveedores);
        data.put("facturas",facturas);
        data.put("monedas",monedas);

        return new JsonResponse(data,"",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/filtros/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(@RequestBody HashMap<String,Object> body){

        List< Map<String, Object>> proveedores = (ArrayList)body.get("proveedores");
        List< Map<String, Object>> facturas    = (ArrayList)body.get("facturas");
        Map<String, Object> moneda = (Map<String, Object>) body.get("monedas");
        Integer monedaId = null;
        Integer diasAgrupados = null;
        Integer tipoReporte = null;
        Integer tipoSaldos = null;

        if(moneda != null)
            monedaId = (Integer) moneda.get("id");

        if(body.get("diasAgrupar") != null){
            diasAgrupados = Integer.parseInt((String)body.get("diasAgrupar"));
        }
        if(body.get("tipoReporte") != null){
            HashMap<String,Object> tr = (HashMap)body.get("tipoReporte");
            tipoReporte = (Integer)tr.get("id");
        }
        if(body.get("tipoSaldos") != null){
            HashMap<String,Object> ts = (HashMap)body.get("tipoSaldos");
            tipoSaldos = (Integer)ts.get("id");
        }

        String proveedoresIdsStr = null;
        if(proveedores != null && proveedores.size() > 0){
            proveedoresIdsStr = "";
            for (Map<String, Object> proveedor : proveedores){
                proveedoresIdsStr += ("|" + proveedor.get("id").toString() + "|");
            }
        }

        String facturasIdsStr = null;
        if(facturas != null && facturas.size() > 0){
            facturasIdsStr = "";
            for (Map<String, Object> factura : facturas){
                facturasIdsStr += ("|" + factura.get("id").toString() + "|");
            }
        }

        Boolean mostrarVencidos = true;
        Boolean mostrarPorVencer = true;

        switch(tipoSaldos){
            case TiposSaldos.VENCIDOS:
                mostrarPorVencer = false;
                break;
            case TiposSaldos.POR_VENCER:
                mostrarVencidos = false;
                break;
        }
        mostrarPorVencer = true;
        HashMap<String,Object> data = new HashMap<>();
        switch(tipoReporte){
            case TiposReportes.DETALLE:
                data.put("datos",proveedorDao.getReporteAntiguedadSaldosDetalle(proveedoresIdsStr,facturasIdsStr,monedaId,diasAgrupados,mostrarVencidos,mostrarPorVencer));
                return new JsonResponse(data,"",JsonResponse.STATUS_OK);
            case TiposReportes.RESUMEN:
                data.put("datos",proveedorDao.getReporteAntiguedadSaldosResumen(proveedoresIdsStr,facturasIdsStr,monedaId,diasAgrupados,mostrarVencidos,mostrarPorVencer));
                return new JsonResponse(data,"",JsonResponse.STATUS_OK);
        }

        return new JsonResponse(null,"",JsonResponse.STATUS_ERROR_NULL);
    }

    @RequestMapping(value="/download/excel", method = RequestMethod.POST)
    public void exportReporte(@RequestBody HashMap<String,Object> body, HttpServletResponse response) throws IOException {

        List< Map<String, Object>> proveedores = (ArrayList)body.get("proveedores");
        List< Map<String, Object>> facturas    = (ArrayList)body.get("facturas");
        Map<String, Object> moneda             = (Map<String, Object>) body.get("monedas");
        Integer monedaId = null;
        Integer diasAgrupados = null;
        Integer tipoReporte = null;
        Integer tipoSaldos = null;

        if(body.get("monedaId") != null){
            monedaId = Integer.parseInt((String)body.get("monedaId"));
        }
        if(body.get("diasAgrupar") != null){
            diasAgrupados = Integer.parseInt((String)body.get("diasAgrupar"));
        }
        if(body.get("tipoReporte") != null){
            HashMap<String,Object> tr = (HashMap)body.get("tipoReporte");
            tipoReporte = (Integer)tr.get("id");
        }
        if(body.get("tipoSaldos") != null){
            HashMap<String,Object> ts = (HashMap)body.get("tipoSaldos");
            tipoSaldos = (Integer)ts.get("id");
        }

        String proveedoresIdsStr = null;
        if(proveedores != null && proveedores.size() > 0){
            proveedoresIdsStr = "";
            for (Map<String, Object> proveedor : proveedores){
                proveedoresIdsStr += ("|" + proveedor.get("id").toString() + "|");
            }
        }

        String facturasIdsStr = null;
        if(facturas != null && facturas.size() > 0){
            facturasIdsStr = "";
            for (Map<String, Object> factura : facturas){
                facturasIdsStr += ("|" + factura.get("id").toString() + "|");
            }
        }

        if(moneda != null)
            monedaId = (Integer) moneda.get("id");

        Boolean mostrarVencidos = true;
        Boolean mostrarPorVencer = true;

        switch(tipoSaldos){
            case TiposSaldos.VENCIDOS:
                mostrarPorVencer = false;
                break;
            case TiposSaldos.POR_VENCER:
                mostrarVencidos = false;
                break;
        }
        HashMap<String,Object> data = new HashMap<>();
        String query = "";
        String[] alColumnas = null;
        String nombreReporte = "";
        switch(tipoReporte){
            case TiposReportes.DETALLE:
                query += "SELECT proveedorCodigo as N'Código proveedor', " +
                        "proveedorNombre as N'Nombre proveedor', " +
                        "codigoRegistro as N'Código Factura', " +
                        "fechaRegistro as N'Fecha factura', " +
                        "fechaVencimiento as N'Fecha vencimiento', " +
                        "diasVencimiento as N'# vencimiento', " +
                        "montoRegistro as N'Monto original', " +
                        "montoActual as N'Saldo', " +
                        "porVencer as N'Por vencer', " +
                        "montoP1 as N'Monto 1', " +
                        "montoP2 as N'Monto 2', " +
                        "montoP3 as N'Monto 3', " +
                        "montoP4 as N'Monto 4', " +
                        "montoProgramado as N'Monto programado', " +
                        "codigos as N'Solicitudes' FROM [dbo].[fn_reporteAntiguedadSaldosDetalle](";

                alColumnas = new String[]{"Código proveedor","Nombre proveedor","Código Factura","Fecha factura","Fecha vencimiento","# vencimiento","Monto original","Saldo","Por vencer","Monto 1","Monto 2","Monto 3","Monto 4","Monto programado","Solicitudes"};
                nombreReporte = "Antigüedad Saldos (Detalles)";
                break;
                //data.put("datos",proveedorDao.getReporteAntiguedadSaldosDetalle());
                //return new JsonResponse(data,"",JsonResponse.STATUS_OK);
            case TiposReportes.RESUMEN:
                query += "SELECT codigo as N'Código proveedor', " +
                        "nombre as N'Nombre proveedor', " +
                        "montoRegistro as N'Monto original', " +
                        "montoActual as N'Saldo', " +
                        "porVencer as N'Por vencer', " +
                        "montoP1 as N'Monto 1', " +
                        "montoP2 as N'Monto 2', " +
                        "montoP3 as N'Monto 3', " +
                        "montoP4 as N'Monto 4', " +
                        "montoProgramado as N'Monto programado' FROM [dbo].[fn_reporteAntiguedadSaldosResumen](";

                alColumnas = new String[]{"Código proveedor","Nombre proveedor","Monto original","Saldo","Por vencer","Monto 1","Monto 2","Monto 3","Monto 4","Monto programado"};
                nombreReporte = "Antigüedad Saldos (Resumen)";
                break;
                //data.put("datos",proveedorDao.getReporteAntiguedadSaldosResumen(proveedoresIdsStr,facturasIdsStr,monedaId,diasAgrupados,mostrarVencidos,mostrarPorVencer));
                //return new JsonResponse(data,"",JsonResponse.STATUS_OK);
        }

        query += (proveedoresIdsStr != null? ("'"+proveedoresIdsStr+"'") : "null")+","+
                (facturasIdsStr != null? ("'"+facturasIdsStr+"'") : "null")+","+
                (monedaId != null? monedaId : "null")+","+
                (diasAgrupados != null? diasAgrupados : "null")+","+
                (mostrarVencidos != null? (mostrarVencidos? 1 : 0) : "null")+","+
                (mostrarPorVencer != null? (mostrarPorVencer? 1 : 0) : "null")+")";

        excelController.downloadXlsx(response, nombreReporte, query, alColumnas, null,"Saldos");
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLS(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/antiguedad/resumen.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());
        parameters.put("proveedores", filtros.get("proveedores"));
        parameters.put("facturas", filtros.get("facturas"));
        parameters.put("moneda", filtros.get("moneda"));
        parameters.put("dias", filtros.get("dias"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Antiguedad (resumen).xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/antiguedad/resumen.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());
        parameters.put("proveedores", filtros.get("proveedores"));
        parameters.put("facturas", filtros.get("facturas"));
        parameters.put("moneda", filtros.get("moneda"));
        parameters.put("dias", filtros.get("dias"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Resumen.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/detalle/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdfDetalle(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/antiguedad/detalle.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", cm.getValor());
        parameters.put("proveedores", filtros.get("proveedores"));
        parameters.put("facturas", filtros.get("facturas"));
        parameters.put("moneda", filtros.get("moneda"));
        parameters.put("dias", filtros.get("dias"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Detalle.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {

        ArrayList<HashMap<String, Integer>> allProveedores = (ArrayList<HashMap<String, Integer>>) json.get("proveedores");
        String proveedores = null;
        if (allProveedores != null) {
            proveedores = "";
            for (HashMap<String, Integer> registro : allProveedores) {
                proveedores += "|" + registro.get("id") + "|";
            }
        }

        Integer proveedorId = null;

        if((String) json.get("proveedorId") != null)
            proveedorId = Integer.parseInt((String) json.get("proveedorId"));

        if(proveedorId != null)
            proveedores = "|" + proveedorId + "|";

        ArrayList<HashMap<String, Integer>> allFacturas = (ArrayList<HashMap<String, Integer>>) json.get("facturas");
        String facturas = null;
        if (allFacturas != null) {
            facturas = "";
            for (HashMap<String, Integer> registro : allFacturas) {
                facturas += "|" + registro.get("id") + "|";
            }
        }

        Integer dias = Integer.parseInt((String) json.get("diasAgrupar"));
        HashMap<String, Integer> moneda = (HashMap<String, Integer>) json.get("monedas");

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("proveedores", (proveedores != null && proveedores.length() > 0)? proveedores : null);
        filtros.put("facturas", (facturas != null && facturas.length() > 0)? facturas : null);
        filtros.put("dias", dias);
        filtros.put("moneda", moneda != null? moneda.get("id") : null);

        return filtros;
    }
}
