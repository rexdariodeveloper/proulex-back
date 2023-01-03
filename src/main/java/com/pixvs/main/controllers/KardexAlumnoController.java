package com.pixvs.main.controllers;

import com.pixvs.main.dao.AlumnoDao;
import com.pixvs.main.models.Alumno;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Angel Daniel Hernández Silva on 11/10/2021.
 */
@RestController
@RequestMapping("/api/v1/kardex-alumno")
public class KardexAlumnoController {

    // Daos
    @Autowired
    private AlumnoDao alumnoDao;

    // Misc
    @Autowired
    private Environment environment;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private HashId hashId;

    @Autowired
    private PuntoVentaController puntoVentaController;

    @Autowired
    private CapturaCalificacionesController capturaCalificacionesController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getKardex(ServletRequest req) throws Exception {

        HashMap<String, Object> datos = new HashMap<>();

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public JsonResponse descargarPdf(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {
        Alumno alumno = alumnoDao.findByCodigo((String) json.get("codigoAlumno"));

        if (alumno == null) {
            return new JsonResponse(null, "No se encontró el alumno", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        } else {
            descargarKardex(alumno, true, response);
        }

        return null;
    }

    public void descargarKardex(Alumno alumno, boolean subreportes, HttpServletResponse response) throws Exception {
        String rutaImagen = environment.getProperty("environments.pixvs.reportes.location") + "/assets/profile.jpg";

        if (alumno.getFotoId() != null) {
            rutaImagen = "http://localhost:" + environment.getProperty("server.port") + "/v1/archivo/" + hashId.encode(alumno.getFotoId());
        }

        Map<String, Object> params = new HashMap<>();
        params.put("RutaImagen", rutaImagen);
        params.put("AlumnoId", alumno.getId());
        params.put("Subreportes", subreportes);

        InputStream reporte = reporteService.generarJasperReport("/modulos/control-escolar/KardexAlumno.jasper", params, ReporteServiceImpl.output.PDF, true);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=kardex.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }

    @RequestMapping(value = "/imprimir/notaVenta/{ordenVentaId}/{sucursalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getImprimirNotaVenta(@PathVariable int ordenVentaId, @PathVariable int sucursalId, HttpServletResponse response) throws SQLException, JRException, ParseException, IOException {
        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("ordenesVentaIdsStr", String.valueOf(ordenVentaId));
        filtros.put("sucursalId", sucursalId);

        puntoVentaController.descargarPdf(filtros, response);
    }

    @RequestMapping(value = "/imprimir/boleta/{alumnoId}/{grupoId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getImprimirBoleta(@PathVariable int alumnoId, @PathVariable int grupoId, HttpServletResponse response) throws SQLException, JRException, ParseException, IOException {
        JSONObject json = new JSONObject();
        json.put("alumnoId", alumnoId);
        json.put("grupoId", grupoId);

        capturaCalificacionesController.downloadBoleta(json, response);
    }
}
