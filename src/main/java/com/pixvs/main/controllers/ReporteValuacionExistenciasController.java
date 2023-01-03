package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlmacenDao;
import com.pixvs.main.dao.ArticuloDao;
import com.pixvs.main.dao.InventarioMovimientoDao;
import com.pixvs.main.dao.LocalidadDao;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
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
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/valuacion")
public class ReporteValuacionExistenciasController {

    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private LocalidadDao localidadDao;
    @Autowired
    private InventarioMovimientoDao inventarioMovimientoDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;
    @Autowired
    private ExcelController excelController;
    @Autowired
    private ControlMaestroMultipleDao cmmDao;
    @Autowired
    private Environment environment;
    @Autowired
    private ReporteService reporteService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getValuacion(ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", new ArrayList<>());
        datos.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloNoSistema());
        datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueAndPermiso(idUsuario));
        datos.put("series", cmmDao.findAllByControlAndActivoIsTrueOrderByValor("CMM_ART_Clasificacion1"));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getValuacion(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        // Agrupado por serie
        if(((Integer) filtros.get("tipo")).equals(0)){
            return new JsonResponse(
                    inventarioMovimientoDao.fn_reporteValuacionSerie(
                            filtros.get("fechaInicio"),
                            filtros.get("fechaFin"),
                            filtros.get("series"),
                            filtros.get("localidades")), null, JsonResponse.STATUS_OK);
        }
        //Agrupado por artículo
        else{
            return new JsonResponse(
                    inventarioMovimientoDao.fn_reporteValuacion(
                            filtros.get("fechaInicio"),
                            filtros.get("fechaFin"),
                            filtros.get("articulos"),
                            filtros.get("localidades"),
                            idUsuario), null, JsonResponse.STATUS_OK);
        }
    }

    @RequestMapping(value = "/download/excel", method = RequestMethod.POST)
    public void downloadXlsxValuacion(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        Boolean porSerie = ((Integer) filtros.get("tipo")).equals(0);
        Object fechaInicio = filtros.get("fechaInicio");
        Object fechaFin = filtros.get("fechaFin");
        Object articulos = filtros.get("articulos");
        Object series = filtros.get("series");
        Object localidades = filtros.get("localidades");

        Date fechaInicioDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) filtros.get("fechaInicio"));
        Date fechaFinDate    = new SimpleDateFormat("yyyy-MM-dd").parse((String) filtros.get("fechaFin"));

        String fecha1 = new SimpleDateFormat("dd MMM yyyy").format(fechaInicioDate).toUpperCase();
        String fecha2 = new SimpleDateFormat("dd MMM yyyy").format(fechaFinDate   ).toUpperCase();

        HashMap<String, Object> cabecera = new HashMap<>();
        cabecera.put("Fecha reporte", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        cabecera.put("Moneda", "Pesos Mexicanos");

        ControlMaestro nombreEmpresa = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        String consulta;

        if(porSerie){
            consulta = "SELECT " +
                    "serie N'Serie', costo N'Costo', unidad N'Unidad', cantidadInicial N'Inventario inicial', " +
                    "cantidadEntrada N'Entradas', cantidadSalida N'Salidas', cantidadFinal N'Existencia', " +
                    "costoInicial N'Inventario inicial ', costoEntrada N'Entradas ', costoSalida N'Salidas ', " +
                    "costoFinal N'Costo total', almacenLocalidad N'Almacén / Localidad' " +
                    "FROM fn_reporteValuacionSerie(" +
                    (fechaInicio != null ? "'" + fechaInicio + "'" : "NULL") + ", " +
                    (fechaFin != null ? "'" + fechaFin + "'" : "NULL") + ", " +
                    (series != null ? "'" + series + "'" : "NULL") + ", " +
                    (localidades != null ? "'" + localidades + "'" : "NULL") +
                    ") ORDER BY almacenLocalidad, serie";
        }
        else{
            consulta = "SELECT " +
                    "codigo N'Código producto', nombre N'Nombre producto',  serie N'Serie', costo N'Costo', unidad N'Unidad', " +
                    "inicial N'Inventario inicial', entradas N'Entradas', salidas N'Salidas', existencia N'Existencia', " +
                    "cInicial N'Inventario inicial ', cEntradas N'Entradas ', cSalidas N'Salidas ', " +
                    "cTotal N'Costo total', almacenLocalidad N'Almacén / Localidad' " +
                    "FROM fn_reporteValuacion(" +
                    (fechaInicio != null ? "'" + fechaInicio + "'" : "NULL") + ", " +
                    (fechaFin != null ? "'" + fechaFin + "'" : "NULL") + ", " +
                    (articulos != null ? "'" + articulos + "'" : "NULL") + ", " +
                    (localidades != null ? "'" + localidades + "'" : "NULL") + ", " +
                    idUsuario +
                    ") ORDER BY almacenLocalidad, nombre";
        }

        String[] titulo;

        if(porSerie){
            titulo = new String[]{nombreEmpresa.getValor(),
                    "INVENTARIO ACTUAL DEL ALMACÉN POR SERIE",
                    "COSTO PRIMERAS ENTRADAS PRIMERAS SALIDAS (PEPS)",
                    "DEL "+fecha1+" AL "+fecha2};
        }
        else{
            titulo = new String[]{nombreEmpresa.getValor(),
                "INVENTARIO ACTUAL DEL ALMACÉN POR ARTÍCULO",
                "COSTO PRIMERAS ENTRADAS PRIMERAS SALIDAS (PEPS)",
                "DEL "+fecha1+" AL "+fecha2};
        }



        String[] columnas;
        if(porSerie)
            columnas = new String[]{ "Serie", "Costo", "Unidad", "Inventario inicial", "Entradas", "Salidas", "Existencia", "Inventario inicial ", "Entradas ", "Salidas ", "Costo total", "Almacén / Localidad" };
        else
            columnas = new String[]{ "Código producto", "Nombre producto", "Serie", "Costo", "Unidad", "Inventario inicial", "Entradas", "Salidas", "Existencia", "Inventario inicial ", "Entradas ", "Salidas ", "Costo total", "Almacén / Localidad" };

        String[] totales = new String[]{ "Inventario inicial", "Entradas", "Salidas", "Existencia", "Inventario inicial ", "Entradas ", "Salidas ", "Costo total" };

        List<String[]> groupColumnas = new ArrayList<>();
        groupColumnas.add(new String[]{"EN UNIDADES","Inventario inicial","Existencia"});
        groupColumnas.add(new String[]{"EN IMPORTES","Inventario inicial ","Costo total"});

        String nombre;
        if(porSerie)
            nombre = "Valuación (Series)";
        else
            nombre = "Valuación (Artículos)";
        excelController.downloadDetailedWithTotalesXlsx(response,
                nombre,
                consulta,
                titulo,
                cabecera,
                groupColumnas,
                columnas,
                null,
                "Valuación de existencias",
                "Almacén / Localidad",
                "Almacén / Localidad",
                totales,
                totales);
    }

    public HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {
        Integer tipo       = (Integer) ((HashMap<String, Object>) json.get("tipo")).get("id");
        Object fechaInicio = json.get("fechaInicio");
        Object fechaFin    = json.get("fechaFin");

        ArrayList<HashMap<String, Integer>> allArticulos = (ArrayList<HashMap<String, Integer>>) json.get("articulos");
        String articulos = null;
        if (allArticulos != null) {
            articulos = "";
            for (HashMap<String, Integer> registro : allArticulos) {
                articulos += "|" + registro.get("id") + "|";
            }
        }

        ArrayList<HashMap<String, Integer>> allSeries = (ArrayList<HashMap<String, Integer>>) json.get("series");
        String series = null;
        if (allSeries != null) {
            series = "";
            for (HashMap<String, Integer> registro : allSeries) {
                series += "|" + registro.get("id") + "|";
            }
        }

        ArrayList<HashMap<String, Integer>> allLocalidades = (ArrayList<HashMap<String, Integer>>) json.get("localidades");
        String localidades = null;
        if (allLocalidades != null) {
            localidades = "";
            for (HashMap<String, Integer> registro : allLocalidades) {
                localidades += "|" + registro.get("id") + "|";
            }
        }

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("tipo", tipo);
        filtros.put("fechaInicio",fechaInicio);
        filtros.put("fechaFin", fechaFin);
        filtros.put("articulos", articulos);
        filtros.put("series", series);
        filtros.put("localidades", localidades);

        return filtros;
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws IOException, SQLException, JRException, ParseException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath;
        String filename;

        Boolean porSerie = ((Integer) filtros.get("tipo")).equals(0);
        Object fechaInicio = filtros.get("fechaInicio");
        Object fechaFin = filtros.get("fechaFin");
        Object articulos = filtros.get("articulos");
        Object series = filtros.get("series");
        Object localidades = filtros.get("localidades");

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();

        if(porSerie){
            reportPath = "/valuacion/serie.jasper";
            filename = "Valuacion (serie).pdf";

            parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
            parameters.put("empresa", cm.getValor());
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);
            parameters.put("series",series);
            parameters.put("localidades",localidades);
            parameters.put("usuario",usuarioId);
        }
        else{
            reportPath = "/valuacion/articulo.jasper";
            filename = "Valuacion (articulo).pdf";

            parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
            parameters.put("empresa", cm.getValor());
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);
            parameters.put("articulos",articulos);
            parameters.put("localidades",localidades);
            parameters.put("usuario",usuarioId);
        }

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLS(@RequestBody JSONObject json, HttpServletResponse response,  ServletRequest req) throws IOException, SQLException, JRException, ParseException {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath;
        String filename;
        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");
        Map<String, Object> parameters = new HashMap<>();

        Boolean porSerie = ((Integer) filtros.get("tipo")).equals(0);
        Object fechaInicio = filtros.get("fechaInicio");
        Object fechaFin = filtros.get("fechaFin");
        Object articulos = filtros.get("articulos");
        Object series = filtros.get("series");
        Object localidades = filtros.get("localidades");

        if(porSerie){
            reportPath = "/valuacion/serie.jasper";
            filename = "Valuacion (serie).xlsx";

            parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
            parameters.put("empresa", cm.getValor());
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);
            parameters.put("series",series);
            parameters.put("localidades",localidades);
            parameters.put("usuario",idUsuario);
        } else {
            reportPath = "/valuacion/articulo.jasper";
            filename = "Valuacion (articulo).xlsx";

            parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
            parameters.put("empresa", cm.getValor());
            parameters.put("fechaInicio", fechaInicio);
            parameters.put("fechaFin", fechaFin);
            parameters.put("articulos",articulos);
            parameters.put("localidades",localidades);
            parameters.put("usuario",idUsuario);
        }

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }
}
