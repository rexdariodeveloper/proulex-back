package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoReporteProfesorProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoReporteProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.apache.poi.ss.util.CellUtil.createCell;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/criterios-evaluacion")
public class ReporteCriteriosEvaluacionController {

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
        json.put("datos",new ArrayList<>());
        json.put("sucursales",sucursales);
        json.put("modalidades",modalidades);
        json.put("profesores",profesores);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getGrupos(@RequestBody JSONObject json) throws SQLException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String,Object> sucursal = (HashMap<String,Object>)json.get("sede");
        Integer sucursalId = sucursal != null ? Integer.valueOf(sucursal.get("id").toString()): null;
        HashMap<String,Object> modalidad = (HashMap<String,Object>)json.get("modalidades");
        Integer modalidadId = modalidad != null ? Integer.valueOf(modalidad.get("id").toString()) : null;
        HashMap<String,Object> fechas = json.get("fechaInicio") == null ? null : (HashMap<String,Object>)json.get("fechaInicio");
        String fechaInicio = fechas != null ? fechas.get("fechaInicio").toString() : "";
        String fechaFin = fechas != null ? fechas.get("fechaFin").toString() : "";
        HashMap<String,Object> ciclo = (HashMap<String,Object>)json.get("ciclo");
        Integer cicloId = ciclo != null ? Integer.valueOf(ciclo.get("id").toString()) : null;
        HashMap<String,Object> programacion = (HashMap<String,Object>)json.get("programacion");
        Integer programacionId = programacion != null ? Integer.valueOf(programacion.get("id").toString()) : null;
        ArrayList<Integer> ids = new ArrayList<>();
        if(json.get("profesores") != null && ((List)json.get("profesores")).size() > 0){
            ids = new ArrayList<>();
            List<Object> profesores = ((List)json.get("profesores"));
            for(Integer i=0;i<profesores.size();i++){
                HashMap<String,Object> temp = (HashMap<String,Object>)profesores.get(i);
                ids.add(Integer.valueOf(temp.get("id").toString()));
            }
        }
        Integer allEmpleados = 0;
        if(ids.size() == 0){
            allEmpleados = 1;
        }
        List<ProgramaGrupoReporteProfesorProjection> grupos = programaGrupoDao.getReporteGruposCriterios(sucursalId,modalidadId,cicloId,programacionId,fechaInicio,fechaFin, allEmpleados,ids);
        return new JsonResponse(grupos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/template/generarReporte", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public JsonResponse descargarPdf(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws IOException, ParseException, SQLException, JRException {
            Integer sucursalId = json.get("sucursalId") != null ? Integer.valueOf(json.get("sucursalId").toString()) : null;
            Integer modalidadId = json.get("modalidadId") != null ? Integer.valueOf(json.get("modalidadId").toString()) : null;
            HashMap<String, Object> fechas = json.get("fechaInicio") == null ? null : (HashMap<String, Object>) json.get("fechaInicio");
            String fechaInicio = fechas != null ? fechas.get("fechaInicio").toString() : null;
            String fechaFin = fechas != null ? fechas.get("fechaFin").toString() : null;
            Integer cicloId = json.get("cicloId") != null ? Integer.valueOf(json.get("cicloId").toString()) : null;
            Integer programacionId = json.get("programacionId") != null ? Integer.valueOf(json.get("programacionId").toString()) : null;
            ArrayList<Integer> ids = new ArrayList<>();
            Integer allEmpleados = 0;
            if (json.get("profesores") != null) {
                //allEmpleados = 0;
                ids = (ArrayList) json.get("profesores");
            }
            if(ids.size() == 0){
                allEmpleados = 1;
            }
            List<ProgramaGrupoReporteProfesorProjection> grupos = programaGrupoDao.getReporteGruposCriterios(sucursalId, modalidadId, cicloId, programacionId, fechaInicio, fechaFin, allEmpleados, ids);
            ArrayList<Integer> profesoresIds = new ArrayList<>();
            for(ProgramaGrupoReporteProfesorProjection profesor: grupos){
                profesoresIds.add(profesor.getEmpleadoId());
            }
            Set<Integer> listWithoutDuplicates = new LinkedHashSet<Integer>(profesoresIds);
            profesoresIds.clear();
            profesoresIds.addAll(listWithoutDuplicates);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            String rutaImagen = environment.getProperty("environments.pixvs.front.url") + "/app/main/img/logos/reportes/reportes-criterios.png";
            for (Integer profesor : profesoresIds) {
                EmpleadoComboProjection temp = empleadoDao.findComboById(profesor);
                Integer idEmpleado = profesor;
                String fechaInicioDate = fechaInicio;
                String fechaFinDate = fechaFin;
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("RutaImagen", rutaImagen);
                parameters.put("AlumnoId", idEmpleado);
                parameters.put("FechaInicio", fechaInicioDate);
                parameters.put("FechaFin", fechaFinDate);
                parameters.put("SucursalId", sucursalId);
                String reportPath = "/modulos/criterios-evaluacion/CriteriosEvaluacionV2.jasper";
                try {
                    InputStream reporte = reporteService.generarJasperReport(reportPath, parameters, ReporteServiceImpl.output.PDF, true);
                    ZipEntry ze = new ZipEntry(temp.getNombreCompleto() + ".pdf");
                    System.out.println("Zipping the file: " + temp.getNombreCompleto());
                    zipOut.putNextEntry(ze);
                    byte[] tmp = new byte[4 * 1024];
                    int size = 0;
                    while ((size = reporte.read(tmp)) != -1) {
                        zipOut.write(tmp, 0, size);
                    }
                    zipOut.flush();
                    reporte.close();
                }catch (Exception e){
                    System.out.println("Error: " + temp.getNombreCompleto());
                }
            }
            zipOut.close();
            System.out.println("Done... Zipped the files...");
            HashMap<String,Object> archivo = new HashMap<>();
            archivo.put("archivo",baos.toByteArray());
            archivo.put("extension",".zip");
            archivo.put("extensionArchivo",".zip");
            archivo.put("nombreArchivo","Asignacion_Clases.zip");
            return new JsonResponse(archivo,"",JsonResponse.STATUS_OK);
    }

}

