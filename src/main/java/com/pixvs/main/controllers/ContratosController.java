package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoContratosProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoReporteProfesorProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/contratos")
public class ContratosController {

    @Autowired
    private Environment environment;
    @Autowired
    private ProgramaDao programaDao;
    @Autowired
    private EmpleadoDao empleadoDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private HashId hashId;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ReporteService reporteService;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleados(ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<PAModalidadComboSimpleProjection> modalidades = paModalidadDao.findComboAllByActivoTrueOrderByNombre();
        List<EmpleadoComboProjection> profesores = empleadoDao.findAllByTipoEmpleadoId(ControlesMaestrosMultiples.CMM_EMP_TipoEmpleadoId.ACADEMICO);
        List<ControlMaestroMultipleComboProjection> idiomas = controlMaestroMultipleDao.findAllByControl(ControlesMaestrosMultiples.CMM_ART_Idioma.NOMBRE);
        json.put("datos",new ArrayList<>());
        json.put("sucursales",sucursales);
        json.put("modalidades",modalidades);
        json.put("profesores",profesores);
        json.put("idiomas",idiomas);
        json.put("anios", programaGrupoDao.findAniosFechaInicio());
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGrupos(@RequestBody JSONObject json) throws SQLException, ParseException {
        List<ProgramaGrupoContratosProjection> grupos = contratosGrupos(json);
        return new JsonResponse(grupos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/template/generarReporte", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public JsonResponse descargarPdf(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws IOException, ParseException, SQLException, JRException {

            //List<ProgramaGrupoContratosProjection> grupos = programaGrupoDao.getContratosGrupos(sucursalId, allProgramas, modalidadId, idsProgramas, cicloId, idiomaId, fechaInicio, allEmpleados, ids, programacionId);
        List<ProgramaGrupoContratosProjection> grupos = contratosGrupos(json);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);
        Integer i = 0;
        for (ProgramaGrupoContratosProjection dato : grupos) {
            i = i + 1;
            EmpleadoComboProjection temp = empleadoDao.findComboById(dato.getEmpleadoId());
            Integer empleadoIdEnviar = dato.getEmpleadoId();
            Integer idiomaIdEnviar = dato.getProgramaId();
            String fechaInicioDate = dato.getFechaInicio().toString();
            Integer sucursalIdEnviar = dato.getSucursalId();
            Integer modalidadIdEnviar = dato.getModalidadId();
            Integer cicloIdEnviar = dato.getCicloId();
            Integer idiomaCmmEnviar = dato.getIdiomaCmm();
            Integer plantelIdEnviar = dato.getPlantelId();
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("idSucursal", sucursalIdEnviar);
            parameters.put("idCiclo", cicloIdEnviar);
            parameters.put("fechaInicio", fechaInicioDate);
            parameters.put("idEmpleado", empleadoIdEnviar);
            parameters.put("idPlantel", plantelIdEnviar);
            parameters.put("fechaFin", dato.getFechaFin().toString());
            parameters.put("idModalidad", modalidadIdEnviar);
            parameters.put("idIdioma", idiomaIdEnviar);
            parameters.put("idCmmIdioma", idiomaCmmEnviar);
            parameters.put("tipoContrato", dato.getTipoGrupo());

            String nombre = i+"- "+dato.getNombreProfesor();
            InputStream reporte = this.getContratoJasper(dato.getTipoGrupo(), parameters);
            ZipEntry ze = new ZipEntry(nombre+".pdf");
            zipOut.putNextEntry(ze);
            byte[] tmp = new byte[4 * 1024];
            int size = 0;
            while ((size = reporte.read(tmp)) != -1) {
                zipOut.write(tmp, 0, size);
            }
            zipOut.flush();
            reporte.close();
        }
        zipOut.close();
        System.out.println("Done... Zipped the files...");
        HashMap<String, Object> archivo = new HashMap<>();
        archivo.put("archivo", baos.toByteArray());
        archivo.put("extension", ".zip");
        archivo.put("extensionArchivo", ".zip");
        archivo.put("nombreArchivo", "Contratos.zip");
        return new JsonResponse(archivo, "", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public void getPDF(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idIdioma", json.getAsNumber("programaId"));
        parameters.put("fechaInicio", json.getAsString("fechaInicio"));
        parameters.put("fechaFin", json.getAsString("fechaFin"));
        parameters.put("idEmpleado", json.getAsNumber("empleadoId"));
        parameters.put("idSucursal", json.getAsNumber("sucursalId"));
        parameters.put("idModalidad", json.getAsNumber("modalidadId"));
        parameters.put("idCiclo", json.getAsNumber("cicloId"));
        parameters.put("idCmmIdioma", json.getAsNumber("idiomaCmm"));
        parameters.put("idPlantel", json.getAsNumber("plantelId"));
        parameters.put("tipoContrato", json.getAsNumber("tipoGrupo"));

        String nombre = json.getAsString("codigoProfesor")+" - "+json.getAsString("nombreProfesor");
        InputStream reporte = this.getContratoJasper((Integer) json.getAsNumber("tipoGrupo"), parameters);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; "+nombre+".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());
        response.flushBuffer();

    }

    /*
    * Si exiten nuevos archivos o nuevos tipos de grupo, deberian especificarse en esta funcion
    * */
    public InputStream getContratoJasper(Integer tipoGrupo,  Map<String, Object> parameters) throws JRException, SQLException, IOException {
        String reportPath = "/modulos/rh/contratos/";
        if(tipoGrupo == 2000390){
            reportPath += "Virtual.jasper";
        }else{
            reportPath += "Presencial.jasper";
        }
        return reporteService.generarJasperReport(reportPath, parameters, ReporteServiceImpl.output.PDF, true);
    }


    public List<ProgramaGrupoContratosProjection> contratosGrupos(JSONObject json){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String,Object> sucursal = (HashMap<String,Object>)json.get("sede");
        //if(sucursal.get("nombre").equals("JOBS SEMS")) {
        Integer sucursalId = sucursal != null ? Integer.valueOf(sucursal.get("id").toString()) : null;
        HashMap<String, Object> modalidad = (HashMap<String, Object>) json.get("modalidades");
        Integer modalidadId = modalidad != null ? Integer.valueOf(modalidad.get("id").toString()) : null;
        HashMap<String, Object> idioma = (HashMap<String, Object>) json.get("idioma");
        Integer idiomaId = idioma != null ? Integer.valueOf(idioma.get("id").toString()) : null;
        String fechaInicio = (String) json.get("fechaInicio");

        //HashMap<String, Object> ciclo = (HashMap<String, Object>) json.get("ciclo");
        //Integer cicloId = ciclo != null ? Integer.valueOf(ciclo.get("id").toString()) : null;
        Integer anio = (Integer) json.get("anio");
        //Integer programacionId = programacion != null ? Integer.valueOf(programacion.get("id").toString()) : null;
        ArrayList<Integer> idsProgramas = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        if (json.get("profesores") != null && ((List) json.get("profesores")).size() > 0) {
            ids = new ArrayList<>();
            List<Object> profesores = ((List) json.get("profesores"));
            for (Integer i = 0; i < profesores.size(); i++) {
                HashMap<String, Object> temp = (HashMap<String, Object>) profesores.get(i);
                ids.add(Integer.valueOf(temp.get("id").toString()));
            }
        }
        Integer allEmpleados = 0;
        if (ids.size() == 0) {
            allEmpleados = 1;
        }
        if (json.get("programa") != null && ((List) json.get("programa")).size() > 0) {
            idsProgramas = new ArrayList<>();
            List<Object> programas = ((List) json.get("programa"));
            for (Integer i = 0; i < programas.size(); i++) {
                HashMap<String, Object> temp = (HashMap<String, Object>) programas.get(i);
                idsProgramas.add(Integer.valueOf(temp.get("id").toString()));
            }
        }
        Integer allProgramas = 0;
        if (idsProgramas.size() == 0) {
            allProgramas = 1;
        }
        List<ProgramaGrupoContratosProjection> grupos = programaGrupoDao.getContratosGrupos(sucursalId, allProgramas, modalidadId, idsProgramas, idiomaId, fechaInicio, allEmpleados, ids, anio);
        return grupos;
    }

}

