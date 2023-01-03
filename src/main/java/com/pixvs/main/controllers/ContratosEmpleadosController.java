package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboContratoProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoContratoProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoPolizaListadoProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpladoContratoListadoProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoOfficeProjection;
import com.pixvs.main.models.projections.EmpleadoContrato.EmpleadoContratoOpenOfficeProjection;
import com.pixvs.main.models.projections.EntidadContrato.EntidadContratoWordProjection;
import com.pixvs.main.services.OpenOfficeService;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.services.ReporteServiceImpl;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
//import org.jodconverter.core.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@RestController
@RequestMapping("/api/v1/contratos-empleados")
public class ContratosEmpleadosController {

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
    @Autowired
    private EntidadDao entidadDao;
    @Autowired
    private EmpleadoContratoDao empleadoContratoDao;
    @Autowired
    private EntidadContratoDao entidadContratoDao;
    @Autowired
    private OpenOfficeService openOfficeService;

    /*@Resource
    private DocumentConverter documentConverter;*/

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getprepararListado()  {
        ArrayList empleados = new ArrayList<>();
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("entidades", entidadDao.findComboAllByActivoIsTrue());
        respuesta.put("empleados", empleados);//empleadoDao.findAllEmpleadoCombo());
        return new JsonResponse(respuesta, "Filtros", JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getContratosFiltros(@RequestBody JSONObject json) throws SQLException, ParseException {
        HashMap<String,Object> ent = (HashMap<String,Object>)json.get("entidad");
        Integer idEntidad = (Integer) ent.get("id");
        //Entidad entidad = entidadDao.findEntidadById(idEntidad);

        Integer anio = (Integer)json.get("anio");
        Integer todosEmpleados = 1;
        String idsEmpleados = null;
        if(json.get("empleados") != null  && ((List) json.get("empleados")).size() > 0){
            idsEmpleados = "";
            todosEmpleados = 0;
            List<Object> empleados = ((List) json.get("empleados"));
            String[] ids = new String[empleados.size()];
            for (Integer i = 0; i < empleados.size(); i++) {
                HashMap<String, Object> temp = (HashMap<String, Object>) empleados.get(i);
                //idsEmpleados = new StringBuilder(idsEmpleados).append(temp.get("id").toString()).toString();
                ids[i] = temp.get("id").toString();
            }

            idsEmpleados = String.join(",",ids);
        }
        List<EmpladoContratoListadoProjection> contratos = empleadoContratoDao.findAllProjectedListadoContratosEntidad(idEntidad,anio, todosEmpleados, idsEmpleados );
        return new JsonResponse(contratos, "Filtros", JsonResponse.STATUS_OK);
    }

    public JsonResponse getDescargarZip(@RequestBody JSONObject json, String tipo) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);
        Integer i = 0;

        List<EmpleadoContratoOfficeProjection> contratos = getContratos(json);

        if(contratos == null){
            return new JsonResponse(null, "No se encontraron rcontratos con estos parametros", JsonResponse.STATUS_ERROR);
        }

        for (EmpleadoContratoOfficeProjection contrato : contratos) {
            i += 1;
            EmpleadoContratoProjection empleado = empleadoDao.findComboContratoById(null);//contrato.getEmpleadoId());
            InputStream documento = null;
            ZipEntry ze = null;

            //boolean usarODT = (empleado.getEntidadId() == 9 || empleado.getEntidadId() == 13 || empleado.getEntidadId() == 14 || empleado.getEntidadId() == 15);
            if(tipo.equals("PDF")){
                documento = getDocumento(contrato.getId(), "PDF");
                ze = new ZipEntry(empleado.getCodigoEmpleado() + "-" + empleado.getNombreCompleto() + ".pdf");
            }else{
                documento = getDocumento(contrato.getId(), "DOCX");
                ze = new ZipEntry(empleado.getCodigoEmpleado() + "-" + empleado.getNombreCompleto() + ".odt");
            }

            zipOut.putNextEntry(ze);
            byte[] tmp = new byte[4 * 1024];
            int size = 0;
            while ((size = documento.read(tmp)) != -1) {
                zipOut.write(tmp, 0, size);
            }
            zipOut.flush();
            documento.close();
        }
        zipOut.close();
        //System.out.println("Done... Zipped the files...");
        HashMap<String, Object> archivo = new HashMap<>();
        archivo.put("archivo", baos.toByteArray());
        archivo.put("extension", ".zip");
        archivo.put("extensionArchivo", ".zip");
        archivo.put("nombreArchivo", "contratos.zip");
        return new JsonResponse(archivo, "", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public void getPDF(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {
        Integer idContrato = (Integer) json.get("id");
        // Obtenemos el word y lo pasamos PDF
        InputStream inputStream = getDocumento(idContrato, "PDF");
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrato.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    public InputStream getDocumento(Integer idContrato, String tipo) throws Exception {
        EmpleadoContratoOfficeProjection contrato = empleadoContratoDao.findProjectedById(idContrato);
        EmpleadoContratoProjection empleado = empleadoDao.findComboContratoById(null);//contrato.getEmpleadoId());

        EntidadContratoWordProjection nombreArchivo = entidadContratoDao.findProjectedByTipoContratoId(contrato.getTipoContrato().getId());
        String archivo = null;
        String reportPath = null;
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("idContrato", idContrato);

        archivo = nombreArchivo.getDocumentoContrato().getNombreOriginal().replace("docx", "odt");
        reportPath = environment.getProperty("environments.pixvs.reportes.location")+ File.separator +"modulos"+File.separator+"rh"+File.separator+"contratos"+File.separator+archivo;
        tipo = (tipo == "DOCX" ? "ODT": tipo);

        Integer idEntidad = 10;
        EmpleadoContratoOpenOfficeProjection contratoODT = empleadoContratoDao.findComboContratoOdtById(idContrato, idEntidad);
        return openOfficeService.updateFile(reportPath, contratoODT, tipo);
    }

    @RequestMapping(value = "/download/docx/{idContrato}")
    public void getDocx(@PathVariable Integer idContrato, HttpServletResponse response) throws Exception {

        EmpleadoContratoOfficeProjection contrato = empleadoContratoDao.findProjectedById(idContrato);
        EmpleadoContratoProjection empleado = empleadoDao.findComboContratoById(null);//contrato.getEmpleadoId());

        InputStream inputStream = getDocumento(idContrato, "ODT");
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+empleado.getNombreCompleto()+".odt");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    @RequestMapping(value = "/imprimir/zip/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDescargarZip(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws Exception {
        return getDescargarZip(json, "PDF");
    }

    @RequestMapping(value = "/imprimir/zip/docx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDescargarZipDocx(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws Exception {
        return getDescargarZip(json, "DOCX");
    }

    private List<EmpleadoContratoOfficeProjection> getContratos(JSONObject json){
        HashMap<String,Object> entidad = (HashMap<String,Object>)json.get("entidad");
        Integer idEntidad = (Integer) entidad.get("id");
        Integer anio = (Integer)json.get("anio");
        Integer todosEmpleados = 1;
        String idsEmpleados = null;
        if(json.get("empleados") != null  && ((List) json.get("empleados")).size() > 0){
            idsEmpleados = "";
            todosEmpleados = 0;
            List<Object> empleados = ((List) json.get("empleados"));
            String[] ids = new String[empleados.size()];
            for (Integer i = 0; i < empleados.size(); i++) {
                HashMap<String, Object> temp = (HashMap<String, Object>) empleados.get(i);
                //idsEmpleados = new StringBuilder(idsEmpleados).append(temp.get("id").toString()).toString();
                ids[i] = temp.get("id").toString();
            }

            idsEmpleados = String.join(",",ids);
        }
        return empleadoContratoDao.findAllProjectedListadoContratos(anio, todosEmpleados, idsEmpleados);
    }



    /**
     * Funcion para pruebas, para no iniciar el front y trabajar solo con el back y postman
     * Al final, se pasa el codigo modificado a la funcion getDocument(el que se requiera), ya que es la funcion para las descargas
     * @param json parametros
     * @param response
     * @param req
     * @throws Exception
     */
    @RequestMapping(value = "/update/odt", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateOdt(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws Exception {
        String reportPath = environment.getProperty("environments.pixvs.reportes.location")+ File.separator +"modulos"+File.separator+"rh"+File.separator+"CONTRATO INDIVIDUAL DE TRABAJO-OPERADORA DE SERVICIOS INTEGRALES.odt";
        Integer idContrato = 634;
        boolean isPdf = false;
        String tipo = "DOCX";

        Integer idEntidad = 10;
        EmpleadoContratoOpenOfficeProjection contrato = empleadoContratoDao.findComboContratoOdtById(idContrato, idEntidad);
        InputStream inputStream = openOfficeService.updateFile(reportPath, contrato, tipo);
        // StringUtils.stripAccents(contrato.getNombre())
        response.setContentType("application/vnd.oasis.opendocument.text");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrato."+tipo.toLowerCase());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        //return new JsonResponse("ODT", "", JsonResponse.STATUS_OK);
    }

    /**
     * Polizas
    * */

    @RequestMapping(value = "/polizas/filtros", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getprepararListadoPoliza()  {
        HashMap<String, Object> respuesta = new HashMap<>();
        respuesta.put("entidades", entidadDao.findComboAllByActivoIsTrue());
        respuesta.put("empleados", empleadoDao.findAllEmpleadoCombo());
        return new JsonResponse(respuesta, "Filtros", JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/polizas/filtros/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPolizasFiltros(@RequestBody JSONObject json) throws SQLException, ParseException {

        List<EmpleadoPolizaListadoProjection> polizas = getListadoPolizas(json);
        if(polizas == null){
            return new JsonResponse(null, "No se encontraron rcontratos con estos parametros", JsonResponse.STATUS_ERROR);
        }
        return new JsonResponse(polizas, "Polizas", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/polizas/zip", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPolizasZip(@RequestBody JSONObject json, String tipo) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);
        Integer i = 0;
        List<EmpleadoPolizaListadoProjection> polizas = getListadoPolizas(json);

        if(polizas == null){
            return new JsonResponse(null, "No se encontraron rcontratos con estos parametros", JsonResponse.STATUS_ERROR);
        }

        for (EmpleadoPolizaListadoProjection polizaEmpleado : polizas) {
            i += 1;
            InputStream documento = null;
            documento = getDocumentoPoliza(polizaEmpleado.getId());
            ZipEntry ze = null;

            ze = new ZipEntry(i +"-" + polizaEmpleado.getNombreCompleto() + ".pdf");

            zipOut.putNextEntry(ze);
            byte[] tmp = new byte[4 * 1024];
            int size = 0;
            while ((size = documento.read(tmp)) != -1) {
                zipOut.write(tmp, 0, size);
            }
            zipOut.flush();
            documento.close();
        }
        zipOut.close();
        HashMap<String, Object> archivo = new HashMap<>();
        archivo.put("archivo", baos.toByteArray());
        archivo.put("extension", ".zip");
        archivo.put("extensionArchivo", ".zip");
        archivo.put("nombreArchivo", "contratos.zip");
        return new JsonResponse(archivo, "", JsonResponse.STATUS_OK);
    }

    public List<EmpleadoPolizaListadoProjection> getListadoPolizas(JSONObject json){
        HashMap<String,Object> entidad = (HashMap<String,Object>)json.get("entidad");;
        HashMap<String,Object> empleadoInfo = (HashMap<String,Object>)json.get("empleado");
        Integer idEmpleado = null;
        if(empleadoInfo != null && !empleadoInfo.isEmpty()){
            idEmpleado = (Integer)empleadoInfo.get("id");
        }
        Integer idEntidad = (Integer) entidad.get("id");
        return empleadoDao.findAllProjectedListadoPolizas(idEntidad, idEmpleado );
    }

    @RequestMapping(value = "/polizas/zip/pdf", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPolizasZip(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws Exception {
        return getPolizasZip(json, "PDF");
    }

    @RequestMapping(value = "/poliza/preview", method = RequestMethod.POST)
    public void getPolizaPDF(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {
        Integer idEmpleado = (Integer) json.get("id");
        InputStream inputStream = getDocumentoPoliza(idEmpleado);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=poliza.pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    public InputStream getDocumentoPoliza(Integer idEmpleado) throws Exception {
        String reportPath = "/modulos/rh/polizas-seguro-vida/rptPolizaSeguro.jasper";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ID_EMPLEADO", idEmpleado);
        parameters.put("PATH_REPORTES", environment.getProperty("environments.pixvs.reportes.location"));

        return reporteService.generarJasperReport(reportPath, parameters, ReporteServiceImpl.output.PDF, true);//reporteService.generarReporte(reportPath, parameters);
    }

    @RequestMapping(value = "/empleados/{idEntidad}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEmpleadosByEntidadId(@PathVariable Integer idEntidad) {
        List<EmpleadoComboContratoProjection> emp = empleadoDao.findAllEmpleadoComboEntidades(idEntidad, 1, null);
        Map<String, Object> empleados = new HashMap<>();
        empleados.put("empleadosEntidad", emp);
        return new JsonResponse(empleados, "Empleados de la entidad", JsonResponse.STATUS_OK);
    }

}
