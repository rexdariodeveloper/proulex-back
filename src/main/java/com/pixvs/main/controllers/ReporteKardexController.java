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
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/kardex")
public class ReporteKardexController {


    @Autowired
    private ExcelController excelController;

    @Autowired
    private InventarioMovimientoDao inventarioMovimientoDao;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

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
        datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueAndPermiso(idUsuario));
        datos.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndInventariable(true));
        datos.put("usuarios", usuarioDao.findProjectedComboAllByEstatusId(CMM_Estatus.ACTIVO));
        datos.put("tiposMovimiento", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(CMM_IM_TipoMovimiento.NOMBRE));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getKardex(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        return new JsonResponse(
                inventarioMovimientoDao.getKardexArticulo(
                        filtros.get("articulos"),
                        filtros.get("fechaCreacionDesde"),
                        filtros.get("fechaCreacionHasta"),
                        filtros.get("localidades"),
                        filtros.get("referencia")
                ), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/kardex-articulos/download/excel", method = RequestMethod.POST)
    public void downloadXlsxKardex(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {
        HashMap<String, Object> filtros = getFiltros(json);

        Object articuloId = filtros.get("articuloId");
        Object fechaCreacionDesde = filtros.get("fechaCreacionDesde");
        Object fechaCreacionHasta = filtros.get("fechaCreacionHasta");
        Object almacenId = filtros.get("almacenId");
        Object localidades = filtros.get("localidades");
        Object referencia = filtros.get("referencia");
        Object tiposMovimiento = filtros.get("tiposMovimiento");
        Object usuarioId = filtros.get("usuarioId");

        String consulta = "SELECT Fecha, Articulo, AlmacenLocalidad, TipoMovimiento, Referencia, Razon, Usuario, " +
                "ExistenciaAnterior, Entrada, Salida, Total, Costo FROM fn_getKardexArticulo(" +
                (articuloId != null ? "'" + articuloId + "'" : "NULL") + ", " +
                (fechaCreacionDesde != null ? "'" + fechaCreacionDesde + "'" : "NULL") + ", " +
                (fechaCreacionHasta != null ? "'" + fechaCreacionHasta + "'" : "NULL") + ", " +
                (almacenId != null ? "'" + almacenId + "'" : "NULL") + ", " +
                (localidades != null ? "'" + localidades + "'" : "NULL") + ", " +
                (referencia != null ? "'" + referencia + "'" : "NULL") + ", " +
                (tiposMovimiento != null ? "'" + tiposMovimiento + "'" : "NULL") + ", " +
                (usuarioId != null ? "'" + usuarioId + "'" : "NULL") + "" +
                ") ORDER BY Fecha ASC";

        String[] columnas = new String[]{
                "Fecha",
                "Artículo",
                "Almacén / Localidad",
                "Tipo Movimiento",
                "Referencia",
                "Razon",
                "Usuario",
                "Existencia Anterior",
                "Entrada",
                "Salida",
                "Total",
                "Costo"
        };

        excelController.downloadXlsx(response, "kardex-articulo", consulta, columnas, null);
    }

    private HashMap<String, Object> getFiltros(JSONObject json) throws SQLException, ParseException {
        ArrayList<HashMap<String, Integer>> allArticulos = (ArrayList<HashMap<String, Integer>>) json.get("articulo");
        String articulos = null;
        if (allArticulos != null) {
            articulos = "";
            for (HashMap<String, Integer> registro : allArticulos) {
                articulos += "|" + registro.get("id") + "|";
            }
        }

        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");

        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        ArrayList<HashMap<String, Integer>> allLocalidades = (ArrayList<HashMap<String, Integer>>) json.get("localidades");
        String localidades = null;
        if (allLocalidades != null) {
            localidades = "";
            for (HashMap<String, Integer> registro : allLocalidades) {
                localidades += "|" + registro.get("id") + "|";
            }
        }

        String referencia = (String) json.get("referencia");

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("articulos", (articulos != null && articulos.length() > 0)? articulos : null);
        filtros.put("fechaCreacionDesde", fechaCreacionDesde);
        filtros.put("fechaCreacionHasta", fechaCreacionHasta);
        filtros.put("localidades", (localidades != null && localidades.length() > 0)? localidades : null);
        filtros.put("referencia", (isNullorEmpty(referencia)? null : referencia));

        return filtros;
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/kardex/kardex.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("fechaInicio", filtros.get("fechaCreacionDesde"));
        parameters.put("fechaFin", filtros.get("fechaCreacionHasta"));
        parameters.put("empresa", cm.getValor());
        parameters.put("articulos",filtros.get("articulos"));
        parameters.put("localidades",filtros.get("localidades"));
        parameters.put("referencia",filtros.get("referencia"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=kardex.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLS(@RequestBody JSONObject json, HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        HashMap<String, Object> filtros = getFiltros(json);

        String reportPath = "/kardex/kardex.jasper";

        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("fechaInicio", filtros.get("fechaCreacionDesde"));
        parameters.put("fechaFin", filtros.get("fechaCreacionHasta"));
        parameters.put("empresa", cm.getValor());
        parameters.put("articulos",filtros.get("articulos"));
        parameters.put("localidades",filtros.get("localidades"));
        parameters.put("referencia",filtros.get("referencia"));

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Kardex.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }
}
