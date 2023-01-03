package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.main.models.projections.ProgramaMeta.ProgramaMetaEditarProjection;
import com.pixvs.main.models.projections.ProgramaMeta.ProgramaMetaListadoProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercial.ProgramacionAcademicaComercialComboProjection;
import com.pixvs.main.models.projections.ProgramacionAcademicaComercialDetalle.ProgramacionAcademicaComercialDetalleMetaListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.DateUtil;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 10/05/2021.
 */
@RestController
@RequestMapping("/api/v1/programas-metas")
public class ProgramaMetaController {

    @Autowired
    private ProgramaMetaDao programaMetaDao;
    @Autowired
    private ProgramaMetaDetalleDao programaMetaDetalleDao;
    @Autowired
    private ProgramacionAcademicaComercialDao programacionAcademicaComercialDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private ProgramacionAcademicaComercialDetalleDao programacionAcademicaComercialDetalleDao;
    @Autowired
    private ProgramaDao programaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getProgramasMetas() throws SQLException {

        List<ProgramaMetaListadoProjection> programasMetas = programaMetaDao.findProjectedListadoAllBy();

        return new JsonResponse(programasMetas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody ProgramaMeta programaMeta, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        // Settear id de subpropiedades en cabecera
        programaMeta.setProgramacionAcademicaComercialId(programaMeta.getProgramacionAcademicaComercial().getId());

        if (programaMeta.getId() == null) {
            programaMeta.setCreadoPorId(idUsuario);
            programaMeta.setActivo(true);

            // Generar código
            ProgramacionAcademicaComercial programacionAcademicaComercial = programacionAcademicaComercialDao.findById(programaMeta.getProgramacionAcademicaComercialId());
            programaMeta.setCodigo("META-" + programacionAcademicaComercial.getCodigo());
        } else {
            ProgramaMeta objetoActual = programaMetaDao.findById(programaMeta.getId().intValue());
            try {
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), programaMeta.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            programaMeta.setActivo(objetoActual.getActivo());
            programaMeta.setModificadoPorId(idUsuario);
        }

        if(programaMeta.getDetalles() == null || programaMeta.getDetalles().size() == 0){
            ProgramacionAcademicaComercial programacionAcademicaComercial = programacionAcademicaComercialDao.findById(programaMeta.getProgramacionAcademicaComercialId());
            ProgramaMetaDetalle programaMetaDetalle = new ProgramaMetaDetalle();
            List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrueOrderByNombre();
            programaMetaDetalle.setSucursalId(sucursales.get(0).getId());
            programaMetaDetalle.setPaModalidadId(programacionAcademicaComercial.getDetalles().get(0).getPaModalidadId());
            programaMetaDetalle.setFechaInicio(programacionAcademicaComercial.getDetalles().get(0).getFechaInicio());
            programaMetaDetalle.setMeta(0);
            programaMeta.setDetalles(Arrays.asList(programaMetaDetalle));
        }

        programaMeta = programaMetaDao.save(programaMeta);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idProgramaMeta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idProgramaMeta) throws SQLException {

        ProgramaMetaEditarProjection programaMeta = programaMetaDao.findProjectedEditarById(idProgramaMeta);

        return new JsonResponse(programaMeta, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idProgramaMeta}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idProgramaMeta) throws SQLException {

        ProgramaMeta programaMeta = programaMetaDao.findById(hashId.decode(idProgramaMeta));
        programaMeta.setActivo(false);
        programaMetaDao.save(programaMeta);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProgramaMetaById(@PathVariable(required = false) Integer id) throws SQLException {

        ProgramaMetaEditarProjection programaMeta = null;
        JSONObject programaMetaDetallesJsonEditar = null;
        List<ProgramacionAcademicaComercialComboProjection> programacionAcademicaComercial = new ArrayList<>();

        // Datos solo de edición
        if (id != null) {
            programaMeta = programaMetaDao.findProjectedEditarById(id);
            programaMetaDetallesJsonEditar = (JSONObject) JSONValue.parse(programaMetaDetalleDao.findJsonStrEditarByProgramaMetaId(programaMeta.getId()));
            programacionAcademicaComercial.add(programaMeta.getProgramacionAcademicaComercial());
        }
        // Datos solo de nuevo registro
        else{
            programacionAcademicaComercial.addAll(programacionAcademicaComercialDao.findProjectedComboAllByActivoTrueAndNotProgramaMeta());
        }

        // Listados generales
        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByActivoTrueOrderByNombre();
        List<PAModalidadComboProjection> paModalidades = paModalidadDao.findProjectedComboAllByActivoTrueOrderByNombre();

        // Preparar body de la petición
        HashMap<String, Object> json = new HashMap<>();
        json.put("programaMeta", programaMeta);
        json.put("programaMetaDetallesJsonEditar", programaMetaDetallesJsonEditar);
        json.put("programacionAcademicaComercial", programacionAcademicaComercial);
        json.put("sucursales", sucursales);
        json.put("paModalidades", paModalidades);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * FROM [VW_REPORTE_EXCEL_PROGRAMAS_METAS]";
        String[] alColumnas = new String[]{"Código", "Nombre", "Ciclo", "Meta","Avance"};

        excelController.downloadXlsx(response, "metas", query, alColumnas, null,"Metas");
    }

    @GetMapping("/download/excel/{programaMetaId}")
    public void downloadXlsx(@PathVariable Integer programaMetaId, HttpServletResponse response) throws IOException {

        ProgramaMeta programaMeta = programaMetaDao.findById(programaMetaId);

        String query = "" +
                "SELECT " +
                "    PAC_Codigo AS \"CÓDIGO_PROGRAMACIÓN_COMERCIAL\", " +
                "    PAC_Nombre AS \"NOMBRE_PROGRAMACIÓN_COMERCIAL\", " +
                "    SUC_CodigoSucursal AS \"CÓDIGO_SEDE\", " +
                "    SUC_Nombre AS \"NOMBRE_SEDE\", " +
                "    PAMOD_Codigo AS \"CÓDIGO_MODALIDAD\", " +
                "    PAMOD_Nombre AS \"NOMBRE_MODALIDAD\", " +
                "    FORMAT(PMD_FechaInicio,'dd/MM/yyyy') AS \"FECHA_INICIO_CURSO\", " +
                "    COALESCE(PMD_Meta,0) AS \"META\" " +
                "FROM ProgramacionAcademicaComercial " +
                "INNER JOIN Sucursales ON SUC_Activo = 1 " +
                "LEFT JOIN ProgramasMetas ON PM_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId AND PM_ProgramaMetaId = " + programaMetaId.toString() + " " +
                "LEFT JOIN ProgramasMetasDetalles ON PMD_PM_ProgramaMetaId = PM_ProgramaMetaId AND PMD_SUC_SucursalId = SUC_SucursalId " +
                "INNER JOIN PAModalidades ON PAMOD_ModalidadId = PMD_PAMOD_ModalidadId " +
                "ORDER BY  \"NOMBRE_MODALIDAD\", PMD_FechaInicio, \"NOMBRE_SEDE\"";
        String[] alColumnas = new String[]{"CÓDIGO_PROGRAMACIÓN_COMERCIAL", "NOMBRE_PROGRAMACIÓN_COMERCIAL", "CÓDIGO_SEDE", "NOMBRE_SEDE", "CÓDIGO_MODALIDAD", "NOMBRE_MODALIDAD", "FECHA_INICIO_CURSO", "META"};

        excelController.downloadXlsx(response, programaMeta.getCodigo(), query, alColumnas, null,"Metas " + programaMeta.getCodigo());
    }

    @GetMapping("/download/plantilla/{programacionAcademicaComercialId}")
    public void downloadPlantilla(@PathVariable Integer programacionAcademicaComercialId, HttpServletResponse response) throws IOException {

        String query = "" +
                "SELECT" +
                "   \"CÓDIGO_PROGRAMACIÓN_COMERCIAL\", " +
                "   \"NOMBRE_PROGRAMACIÓN_COMERCIAL\", " +
                "   \"CÓDIGO_SEDE\", " +
                "   \"NOMBRE_SEDE\", " +
                "   \"CÓDIGO_MODALIDAD\", " +
                "   \"NOMBRE_MODALIDAD\", " +
                "   \"FECHA_INICIO_CURSO\", " +
                "   \"META\" " +
                "FROM [VW_PLANTILLA_PROGRAMAS_METAS] " +
                "WHERE programacionAcademicaComercialId = " + programacionAcademicaComercialId.toString() + " " +
                "ORDER BY \"NOMBRE_MODALIDAD\", fechaInicio, \"CÓDIGO_MODALIDAD\", \"NOMBRE_SEDE\"";
        String[] alColumnas = new String[]{"CÓDIGO_PROGRAMACIÓN_COMERCIAL", "NOMBRE_PROGRAMACIÓN_COMERCIAL", "CÓDIGO_SEDE", "NOMBRE_SEDE", "CÓDIGO_MODALIDAD", "NOMBRE_MODALIDAD", "FECHA_INICIO_CURSO", "META"};

        excelController.downloadXlsx(response, "metas_plantilla", query, alColumnas, null,"Metas plantilla");
    }

    @RequestMapping(value = "/programacion-academica-comercial-detalles/{programacionAcademicaComercialId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDetallesProgramacionAcademicaComercial(@PathVariable Integer programacionAcademicaComercialId) {
        List<ProgramacionAcademicaComercialDetalleMetaListadoProjection> detalles = programacionAcademicaComercialDetalleDao.findProjectedMetaListadoAllByProgramacionAcademicaComercialId(programacionAcademicaComercialId);
        return new JsonResponse(detalles, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/upload/xml", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse fileUploadXML(@RequestParam("file") MultipartFile file, ServletRequest req) throws IOException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        ProgramacionAcademicaComercial programacionAcademicaComercial = null;
        HashMap<String,Sucursal> sucursalesMap = new HashMap<>();
        HashMap<String,PAModalidad> paModalidadesMap = new HashMap<>();

        JSONObject programaMetaDetallesJsonEditar = new JSONObject();

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);

            if(!row.getCell(7).toString().equals("")){
                String codigoProgramacionAcademicaComercial = row.getCell(0).getStringCellValue();
                String codigoSucursal = row.getCell(2).getStringCellValue();
                String codigoModalidad = row.getCell(4).getStringCellValue();
                Date fechaInicioCurso = DateUtil.parse(row.getCell(6).getStringCellValue() + " 00:00:00.0000000","dd/MM/yyyy HH:mm:ss.SSSSSSS z");
                Integer meta = (int) row.getCell(7).getNumericCellValue();

                if(programacionAcademicaComercial == null){
                    programacionAcademicaComercial = programacionAcademicaComercialDao.findByCodigo(codigoProgramacionAcademicaComercial);
                }
                Sucursal sucursal = sucursalesMap.get(codigoSucursal);
                if(sucursal == null){
                    sucursal = sucursalDao.findByCodigoSucursal(codigoSucursal);
                    sucursalesMap.put(codigoSucursal,sucursal);
                    programaMetaDetallesJsonEditar.put(sucursal.getId().toString(),new JSONObject());
                }
                PAModalidad paModalidad = paModalidadesMap.get(codigoModalidad);
                if(paModalidad == null){
                    paModalidad = paModalidadDao.findByCodigoAndActivoTrue(codigoModalidad);
                    paModalidadesMap.put(codigoModalidad,paModalidad);
                }
                List<ProgramacionAcademicaComercialDetalle> programacionAcademicaComercialDetalles = programacionAcademicaComercialDetalleDao.findByProgramacionAcademicaComercialIdAndPaModalidadIdAndFechaInicio(programacionAcademicaComercial.getId(),paModalidad.getId(),fechaInicioCurso);

                JSONObject jsonSucursal = (JSONObject) programaMetaDetallesJsonEditar.get(sucursal.getId().toString());
                for(ProgramacionAcademicaComercialDetalle programacionAcademicaComercialDetalle : programacionAcademicaComercialDetalles){
                    if(jsonSucursal.get(programacionAcademicaComercialDetalle.getId().toString()) == null){
                        jsonSucursal.put(programacionAcademicaComercialDetalle.getId().toString(),new JSONObject());
                    }

                    JSONObject jsonPaModalidad = (JSONObject) jsonSucursal.get(programacionAcademicaComercialDetalle.getPaModalidadId().toString());
                    if(jsonPaModalidad == null){
                        jsonPaModalidad = new JSONObject();
                    }

                    jsonPaModalidad.put(DateUtil.getFecha(programacionAcademicaComercialDetalle.getFechaInicio(),"yyyy-MM-dd"),meta);
                    jsonSucursal.put(programacionAcademicaComercialDetalle.getPaModalidadId().toString(),jsonPaModalidad);
                }
                programaMetaDetallesJsonEditar.put(sucursal.getId().toString(),jsonSucursal);
            }
        }

        HashMap<String,Object> ressponseBody = new HashMap<>();
        ressponseBody.put("programacionAcademicaComercialId",programacionAcademicaComercial.getId());
        ressponseBody.put("programaMetaDetallesJsonEditar",programaMetaDetallesJsonEditar);

        return new JsonResponse(ressponseBody,null,JsonResponse.STATUS_OK);
    }

}
