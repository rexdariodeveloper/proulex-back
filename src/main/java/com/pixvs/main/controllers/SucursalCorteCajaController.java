package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;

import com.pixvs.main.models.OrdenVenta;
import com.pixvs.main.models.SucursalCorteCaja;
import com.pixvs.main.models.Usuario;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.SucursalCorteCaja.SucursalCorteCajaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cortes")
public class SucursalCorteCajaController {

    // Daos
    @Autowired
    private SucursalCorteCajaDao sucursalCorteCajaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    // Controllers
    @Autowired
    private ExcelController excelController;

    //Services
    @Autowired
    private ReporteService reporteService;

    // Otros
    @Autowired
    private HashId hashId;
    @Autowired
    private Environment environment;
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Autowired
    private OrdenVentaDao ordenVentaDao;
    @Autowired
    private ControlMaestroDao controlMaestroDao;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCortes(ServletRequest req) throws SQLException {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(usuarioId);
        HashMap<String, Object> datos = new HashMap<>();
        HashMap<String, Boolean> listaPermiso = new HashMap<>();
        List<Integer> sedesIds = sucursalDao.findIdsByUsuarioPermisosId(usuarioId);
        sedesIds = sedesIds.size() > 0 ? sedesIds : null;
        datos.put("datos", sucursalCorteCajaDao.findProjectedListadoAllBySedeIdIn(sedesIds));
        datos.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        datos.put("usuarios", usuarioDao.findProjectedComboAllByEstatusId(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));

        if (rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), MenuPrincipalPermisos.EXPORTAR_EXCEL_CORTE_CAJA) != null)
            listaPermiso.put("EXPORTAR_EXCEL_CORTE_CAJA", Boolean.TRUE);
        datos.put("listaPermiso", listaPermiso);

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCortes(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        List<SucursalCorteCajaListadoProjection> cortes = getListaCorteFiltros(json, req);
        return new JsonResponse(cortes, null, JsonResponse.STATUS_OK);
    }

    public List<SucursalCorteCajaListadoProjection> getListaCorteFiltros(JSONObject json, ServletRequest req) throws SQLException, ParseException{
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<Integer> sedesIds = new ArrayList<>();

        HashMap<String, Object> sede = (HashMap<String, Object>) json.get("sede");
        Integer sedeId = sede != null ? (Integer) sede.get("id") : null;

        if (sedeId == null) { sedesIds = sucursalDao.findIdsByUsuarioPermisosId(usuarioId);} else { sedesIds.add(sedeId); }

        String fechaInicio = (String) json.get("fechaInicio");
        Date fechaInicioDate = fechaInicio != null ? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(fechaInicio) : null;
        fechaInicio = fechaInicio != null ? new SimpleDateFormat("yyyy-MM-dd").format(fechaInicioDate) : null;

        String fechaFin = (String) json.get("fechaFin");
        Date fechaFinDate = fechaFin != null ? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(fechaFin) : null;
        fechaFin = fechaFin != null ? new SimpleDateFormat("yyyy-MM-dd").format(fechaFinDate) : null;

        HashMap<String, Object> usuario = (HashMap<String, Object>) json.get("usuario");
        Integer creadorId = usuario != null ? (Integer) usuario.get("id") : null;

        String codigoOV = (String) json.get("codigoOV");
        Integer corteId = null;
        if (codigoOV != null && !codigoOV.trim().isEmpty()){
            OrdenVenta ov = ordenVentaDao.findByCodigo(codigoOV);
            if (ov != null)
                corteId = ov.getSucursalCorteCajaId();
        }

        return sucursalCorteCajaDao.findProjectedListadoAllByFiltros(sedesIds, fechaInicio, fechaFin, creadorId, corteId);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoCorteById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="/pdf", method = RequestMethod.POST)
    public void descargarPdf(@RequestBody JSONObject json,HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {
        InputStream reporte = getCorteById((Integer) json.get("id"), ReporteServiceImpl.output.PDF);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=corte.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLSX(@RequestBody JSONObject json,HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {

        String reportPath = "/corte/corte.jasper";
        Integer corteId = (Integer) json.get("id");
        if (corteId == null){
            SucursalCorteCaja corteCaja =  sucursalCorteCajaDao.findFirstByUsuarioAbreIdOrderByIdDesc((Integer) json.get("usuarioId"));
            corteId = corteCaja.getId();
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("id", corteId);

        InputStream reporte = reporteService.generarReporte(reportPath,parameters,"XLSX");

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=corte.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value="/resumen/xls", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarXLSXMultiple(@RequestBody JSONObject json,HttpServletResponse response) throws IOException, SQLException, JRException, ParseException {

        Integer corteId = (Integer) json.get("id");
        if (corteId == null){
            SucursalCorteCaja corteCaja =  sucursalCorteCajaDao.findFirstByUsuarioAbreIdOrderByIdDesc((Integer) json.get("usuarioId"));
            corteId = corteCaja.getId();
        }

        InputStream reporte = getCorteById((Integer) corteId, ReporteServiceImpl.output.XLSX);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=corte.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_REPORTE_EXCEL_ALUMNOS]";
        String[] alColumnas = new String[]{"Código alumno", "Nombre", "Apellidos", "Edad", "Correo electrónico", "Teléfono", "Sede", "Estatus"};

        excelController.downloadXlsx(response, "alumnos", query, alColumnas, null,"Alumnos");
    }

    public InputStream getCorteById(Integer idCorte, ReporteServiceImpl.output tipoReporte) throws JRException, SQLException, IOException {
        String urlBase = File.separator+"modulos"+File.separator+"ventas"+File.separator+"corte"+File.separator;
        ControlMaestro cm = controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA");
        String cortePath = urlBase+"corte.jasper";
        String resumenPath = urlBase+"resumen.jasper";
        String descuentosPath = urlBase+"descuentos.jasper";
        String librosPath = urlBase+"libros.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("id", idCorte);
        parameters.put("empresa", cm.getValor());

        List<JasperPrint> archivos = new ArrayList<>();
        archivos.add(reporteService.generarReporteJasperPrint(cortePath, parameters, true));
        archivos.add(reporteService.generarReporteJasperPrint(resumenPath, parameters, true));
        archivos.add(reporteService.generarReporteJasperPrint(descuentosPath, parameters, true));
        archivos.add(reporteService.generarReporteJasperPrint(librosPath, parameters, true));
        // Se puede especificar que reportes se uniran al PDF
        /*if(tipoReporte.equals(ReporteServiceImpl.output.PDF)){
            return reporteService.unirArchivos(archivos, tipoReporte);
        }if(tipoReporte.equals(ReporteServiceImpl.output.XLSX)){
            return reporteService.unirArchivos(archivos, tipoReporte);
        }*/
        return reporteService.unirArchivos(archivos, tipoReporte);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<SucursalCorteCajaListadoProjection> listaCorte = getListaCorteFiltros(json, req);
        List<Object> data = new ArrayList<>();
        // Moneda
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);

        listaCorte.forEach((corte) -> {
            List<Object> _corte = new ArrayList<>() ;
            _corte.add(corte.getCodigo());
            _corte.add(corte.getSede());
            _corte.add(corte.getPlantel());
            _corte.add(corte.getUsuario());
            _corte.add(corte.getFechaInicio());
            _corte.add(corte.getFechaFin());

            String moneyString = numberFormat.format(corte.getTotal() == null ? 0 : corte.getTotal());
            _corte.add(moneyString);

            _corte.add(corte.getEstatus());

            data.add(_corte.toArray());
        });

        String[] allColumnas = new String[]{
                "CÓDIGO", "SEDE", "PLANTEL", "USUARIO", "FECHA INICIO", "FECHA FIN", "VENTA TOTAL", "ESTATUS"
        };

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

        excelController.downloadXlsxSXSSF(response, "Cortes caja " + LocalDateTime.now().format(dateTimeFormatter), data, allColumnas, "Reporte");
    }
}
