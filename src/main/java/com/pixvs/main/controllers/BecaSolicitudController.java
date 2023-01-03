package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.AlertasConfiguraciones;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Alumno.AlumnoComboProjection;
import com.pixvs.main.models.projections.BecaSolicitud.BecaSolicitudEditarProjection;
import com.pixvs.main.models.projections.BecaSolicitud.BecaSolicitudListadoProjection;
import com.pixvs.main.models.projections.BecaUDG.BecaUDGEditarProjection;
import com.pixvs.main.models.projections.EntidadBeca.EntidadBecaComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaComboSimpleProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.main.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.util.DateUtil;
import com.pixvs.spring.util.HashId;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.poi.ss.util.CellUtil.createCell;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@RestController
@RequestMapping("/api/v1/becas-solicitudes")
public class BecaSolicitudController {

    @Autowired
    private BecaSolicitudDao becaSolicitudDao;
    @Autowired
    private BecaUDGDao becaUDGDao;
    @Autowired
    private AlertaConfigDao alertaConfigDao;
    @Autowired
    private AlertasDao alertaDao;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private ProcesadorAlertasService alertasService;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private ProgramaIdiomaDao programaIdiomaDao;
    @Autowired
    private AlumnoDao alumnoDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private PAModalidadDao paModalidadDao;
    @Autowired
    private EntidadBecaDao entidadBecaDao;
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private LogController logController;
    @Autowired
    private ExcelController excelController;

    @Autowired
    private Environment environment;
    @Autowired
    MenuDao menuDao;
    // Otros
    @Autowired
    private HashId hashId;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFacturas() throws SQLException {
        List<BecaSolicitudListadoProjection> solicitudes = becaSolicitudDao.findAllBy();
        return new JsonResponse(solicitudes, null, JsonResponse.STATUS_OK);
    }

    /*@RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFacturas(@RequestBody JSONObject datos, ServletRequest req) throws SQLException, ParseException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> filtros = getFiltros(datos);

        List<CXPFacturaProgramacionPagoBeneficiarioProjection> cxpFacturas = cxpFacturaDao.findProjectedProgramacionPagoAllByFiltros(((ArrayList<Integer>) filtros.get("proveedores")) != null? 0 : 1,(ArrayList<Integer>) filtros.get("proveedores"),(String) filtros.get("documento"));

        HashMap<String, Object> json = new HashMap<>();

        json.put("cxpFacturas",cxpFacturas);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }*/
    @RequestMapping(value = {"/getById/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEditarProjectionById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        BecaSolicitudEditarProjection solicitud = becaSolicitudDao.findProjectionById(id);
        HashMap<String, Object> json = new HashMap<>();
        json.put("solicitud",solicitud);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/alumno/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAlumnoById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        AlumnoComboProjection alumno = alumnoDao.findProjectedComboById(id);
        return new JsonResponse(alumno, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoProveedoresById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();
        BecaUDGEditarProjection beca = null;
        if (id != null) {
                beca = becaUDGDao.findEditarById(id);
                if(beca != null) {
                    AlumnoComboProjection alumno = alumnoDao.findComboByCodigo(beca.getCodigoAlumno());
                    json.put("alumnos", new ArrayList<>().add(alumno));
                }
        }

        List<ProgramaIdiomaComboProjection> cursos = programaIdiomaDao.findAllByActivoIsTrue();
        //List<PAMO> monedas = monedaDao.findProjectedComboAllByActivoTrueOrderByNombre();
        //List<AlumnoComboProjection> alumnos = alumnoDao.findProjectedComboAllBy();
        List<EntidadBecaComboProjection> entidadesBecas = entidadBecaDao.findProjectedComboAllByActivoTrueOrderByNombre();

        json.put("beca", beca);
        json.put("cursos", cursos);
        //json.put("alumnos", alumnos);
        json.put("entidadesBecas", entidadesBecas);
        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody BecaUDG beca, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        if(beca.getId() == null){
            BecaSolicitud solicitud = new BecaSolicitud();
            solicitud.setCreadoPorId(idUsuario);
            solicitud.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            solicitud.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("SB"));
            solicitud.setBecas(new ArrayList<>());
            Alumno alumno = alumnoDao.findById(beca.getAlumnoTemp().getId());
            beca.setCodigoBeca(autonumericoService.getSiguienteAutonumericoByPrefijo("PB"));
            beca.setCodigoAlumno(alumno.getCodigo());
            beca.setFechaAlta(new Timestamp(System.currentTimeMillis()));
            beca.setNombre(alumno.getNombre());
            beca.setPrimerApellido(alumno.getPrimerApellido());
            beca.setSegundoApellido(alumno.getSegundoApellido());
            beca.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR);
            beca.setCodigoEmpleado("");
            beca.setDescuento(beca.getDescuento().divide(BigDecimal.valueOf(100)));
            beca.setTipoId(ControlesMaestrosMultiples.CMM_BECU_Tipo.PROULEX);
            if(beca.getProgramaIdioma() != null){
                beca.setProgramaIdiomaId(beca.getProgramaIdioma().getId());
                beca.setProgramaIdioma(null);
            }
            if(beca.getPaModalidad() != null){
                beca.setPaModalidadId(beca.getPaModalidad().getId());
                beca.setPaModalidad(null);
            }
            if(beca.getEntidadBeca() != null){
                beca.setEntidadBecaId(beca.getEntidadBeca().getId());
                beca.setEntidadBeca(null);
            }
            solicitud.getBecas().add(beca);
            solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_BECS_Estatus.APROBADA);
            solicitud = becaSolicitudDao.save(solicitud);
            Integer logTipo = LogTipo.ACEPTADA;
            Boolean requiereAlerta = alertasService.validarAutorizacion(AlertasConfiguraciones.BECAS_SOLICITUD, solicitud.getId(), solicitud.getCodigo(), "Solicitud de beca", null, idUsuario, "Solicitudes de Becas");
            if(requiereAlerta){
                logTipo = LogTipo.POR_AUTORIZAR;
                solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_BECS_Estatus.PENDIENTE);
                solicitud.getBecas().get(0).setEstatusId(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
                solicitud = becaSolicitudDao.save(solicitud);
            }

            logController.insertaLogUsuario(
                    new Log(null,
                            logTipo,
                            LogProceso.SOLICITUDES_BECAS,
                            solicitud.getId()
                    ),
                    idUsuario
            );
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/cancelar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse cancelar(@RequestBody HashMap<String,Object> jsonBody, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer solicitudId = (Integer)jsonBody.get("facturaId");
        BecaSolicitud solicitud = becaSolicitudDao.findById(solicitudId);
        solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_BECS_Estatus.CANCELADA);
        logController.insertaLogUsuario(
                new Log(solicitud.getCodigo(),
                        com.pixvs.main.models.mapeos.LogTipo.SOLICITUD_CANCELADA,
                        LogProceso.SOLICITUDES_BECAS,
                        solicitudId
                ),
                idUsuario
        );
        becaSolicitudDao.save(solicitud);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    /*@RequestMapping(value="/pdf/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@PathVariable Integer id, HttpServletResponse response) throws IOException, SQLException, JRException {

        CXPSolicitudPago solicitudPago = cxpSolicitudPagoDao.findById(id);

        String reportPath = "/cuentasPorPagar/Programacion_pago_proveedores.jasper";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("frontUrl", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("id", solicitudPago.getId());
        parameters.put("mostrarPagado", false);

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+solicitudPago.getCodigoSolicitud()+".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }*/

    /** ALERTAS **/
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/aprobar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse aprobarAlerta(@RequestBody HashMap<String,Object> body, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer id = (Integer)body.get("solicitudId");
        HashMap<String,Boolean> facturasAprobarIdsMap = (HashMap<String, Boolean>)body.get("becasAprobarIdsMap");
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Timestamp fechaModificacion = new Timestamp(System.currentTimeMillis());

        MenuPrincipal nodo = menuDao.findByUrl("/app/programacion-academica/becas-solicitudes");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),id, ControlesMaestrosMultiples.CMM_BECS_Estatus.PENDIENTE);
        Boolean alertaEnProceso = false;
        if(alerta != null){
            for ( AlertaDetalle detalle : alerta.getDetalles() ){
                if ( detalle.getUsuarioId().equals(idUsuario) && detalle.getEstatusDetalleId().equals(ControlesMaestrosMultiples.CMM_BECS_Estatus.PENDIENTE) ){
                    alertaEnProceso = true;
                    break;
                }
            }
        }
        /*if(!alertaEnProceso){
            return new JsonResponse(null,"Esta alerta ya fue procesada. Favor de actualizar antes de continuar.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }*/

        /*BecaSolicitud objetoActual = becaSolicitudDao.findById(id);
        try {
            concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), fechaModificacion);
        } catch (Exception e) {
            return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }*/

        BecaSolicitud solicitud = becaSolicitudDao.findById(id);
        for(BecaUDG detalle : solicitud.getBecas()){

            if(facturasAprobarIdsMap.get(detalle.getId().toString()) == null || !facturasAprobarIdsMap.get(detalle.getId().toString())){
                detalle.setEstatusId(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.RECHAZADA);
                logController.insertaLogUsuario(
                        new Log(detalle.getCodigoBeca(),
                                com.pixvs.main.models.mapeos.LogTipo.SOLICITUD_CANCELADA,
                                LogProceso.SOLICITUDES_BECAS,
                                solicitud.getId()
                        ),
                        idUsuario
                );
            }else{
                detalle.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR);
                logController.insertaLogUsuario(
                        new Log(detalle.getCodigoBeca(),
                                com.pixvs.main.models.mapeos.LogTipo.SOLICITUD_APROBADA,
                                LogProceso.SOLICITUDES_BECAS,
                                solicitud.getId()
                        ),
                        idUsuario
                );
            }
        }
        solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_BECS_Estatus.APROBADA);
        becaSolicitudDao.save(solicitud);

        logController.insertaLogUsuario(
                new Log(null,
                        com.pixvs.main.models.mapeos.LogTipo.SOLICITUD_APROBADA,
                        LogProceso.SOLICITUDES_BECAS,
                        solicitud.getId()
                ),
                idUsuario
        );

        actualizarAlerta(id,idUsuario,true, null);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/rechazar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse rechazarAlerta(@RequestBody HashMap<String,Object> body, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer id = (Integer) body.get("id");
        String comentario = (String) body.get("comentario");
        Timestamp fechaModificacion = null;
        if(body.get("fechaModificacion") != null){
            fechaModificacion = DateUtil.parseAsTimestamp((String)body.get("fechaModificacion"),"yyyy-MM-dd HH:mm:ss.SSS z");
        }

        MenuPrincipal nodo = menuDao.findByUrl("/app/programacion-academica/becas-solicitudes");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),id, ControlesMaestrosMultiples.CMM_BECS_Estatus.PENDIENTE);
        Boolean alertaEnProceso = false;
        if(alerta != null){
            for ( AlertaDetalle detalle : alerta.getDetalles() ){
                if ( detalle.getUsuarioId().equals(idUsuario) && detalle.getEstatusDetalleId().equals(ControlesMaestrosMultiples.CMM_BECS_Estatus.PENDIENTE) ){
                    alertaEnProceso = true;
                    break;
                }
            }
        }
        /*if(!alertaEnProceso){
            return new JsonResponse(null,"Esta alerta ya fue procesada. Favor de actualizar antes de continuar.",JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }*/

        /*BecaSolicitud objetoActual = becaSolicitudDao.findById(id);
        try {
            concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(), fechaModificacion);
        } catch (Exception e) {
            return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }*/

        actualizarAlerta(id,idUsuario,false, comentario);
        // Obtenemos las cxpFactura
        BecaSolicitud solicitud = becaSolicitudDao.findById(id);
        for(BecaUDG detalle : solicitud.getBecas()){
            detalle.setEstatusId(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.RECHAZADA);
            logController.insertaLogUsuario(
                    new Log(detalle.getCodigoBeca(),
                            LogTipo.PAGO_RECHAZADO,
                            LogProceso.SOLICITUDES_BECAS,
                            detalle.getId()
                    ),
                    idUsuario
            );

        }
        logController.insertaLogUsuario(
                new Log(null,
                        com.pixvs.main.models.mapeos.LogTipo.SOLICITUD_CANCELADA,
                        LogProceso.SOLICITUDES_BECAS,
                        solicitud.getId()
                ),
                idUsuario
        );
        solicitud.setEstatusId(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.RECHAZADA);
        becaSolicitudDao.save(solicitud);

        return new JsonResponse(id, "null", JsonResponse.STATUS_OK);
    }

    private void actualizarAlerta(Integer procesoId, Integer usuarioId, Boolean autorizar, String comentario) throws Exception{
        MenuPrincipal nodo = menuDao.findByUrl("/app/programacion-academica/becas-solicitudes");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),procesoId, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        for ( AlertaDetalle detalle : alerta.getDetalles() ){
            if ( detalle.getUsuarioId() != null && detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId() != null && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                alertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, autorizar,comentario);
                break;
            }
        }
    }

    @GetMapping("/download/template")
    public void templateDownload(ServletRequest req, HttpServletResponse response) throws IOException {
        Usuario usuario = usuarioDao.findById(JwtFilter.getUsuarioId((HttpServletRequest) req));
        //Create worbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        //Create styles
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        //Create main sheet
        XSSFSheet sheet = workbook.createSheet("Solicitudes de becas");
        //Modifing ht structure to generate grouped cells
        LinkedHashMap<String, String[]> ht = new LinkedHashMap<>();
        ht.put("", new String[] {"ENTIDAD BECA"});
        ht.put("DATOS", new String[] {"CÓDIGO ALUMNO PLIC","PRIMER APELLIDO","SEGUNDO APELLIDO","NOMBRE",
                "CURSO","MODALIDAD","NIVEL","DESCUENTO %","COMENTARIOS"});
        //Iterate ht structure to create merged regions and recover column names
        XSSFRow h0 = sheet.createRow(0);
        Integer currentColumn = 0;
        ArrayList<String> names = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : ht.entrySet()){
            createCell(h0, currentColumn, entry.getKey(), header);
            if (entry.getValue().length > 1){
                sheet.addMergedRegion(new CellRangeAddress(0, 0, currentColumn, (currentColumn + entry.getValue().length) - 1));
                currentColumn += entry.getValue().length;
            } else
                currentColumn++;
            names.addAll(Arrays.asList(entry.getValue()));
        }
        String[] htNames = names.toArray(new String[0]);
        createRow(sheet, 1, htNames, header);
        sheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        //Create hidden sheet for lists
        XSSFSheet hidden = workbook.createSheet("hidden");
        List<List<String>> listados = new ArrayList<>();
        List<ProgramaIdiomaComboSimpleProjection> cursos = programaIdiomaDao.findProjectionAllByActivoIsTrue();
        listados.add(cursos.stream().map( item -> item.getNombre()).collect(Collectors.toList()));
        List<EntidadBecaComboProjection> entidades = entidadBecaDao.findProjectedComboAllByActivoTrueOrderByNombre();
        listados.add(entidades.stream().map( item -> item.getNombre()).collect(Collectors.toList()));
        //listados.add(CMMtoList("CMM_BEC_EntidadBeca", false));
        List<PAModalidadComboSimpleProjection> modalidades = paModalidadDao.findComboAllByActivoTrueOrderByNombre();
        listados.add(modalidades.stream().map( item -> item.getNombre()).collect(Collectors.toList()));
        //Get max length of lists
        Integer max = Collections.max(listados.stream().map(item -> item.size()).collect(Collectors.toList()));
        //Fill hidden sheet with lists
        for (Integer j = 0; j < max; j++){
            XSSFRow row = hidden.createRow(j);
            for (Integer i = 0; i < listados.size(); i++){
                if ( j < listados.get(i).size()) { createCell(row, i, listados.get(i).get(j)); }
            }
        }
        //Create an array to associate target column with list index
        Map<String, String> associate = new HashMap<>();
        associate.put("F", "A");
        associate.put("A", "B");
        associate.put("G", "C");
        //Add Datavalidations to simulate dropdowns
        for (Map.Entry<String,String> entry : associate.entrySet()){
            int target    = CellReference.convertColStringToIndex(entry.getKey());
            int reference = CellReference.convertColStringToIndex(entry.getValue());
            if(listados.get(reference).size() > 0){
                XSSFDataValidation dropdown = createDropdown(workbook,new Integer[] {reference,listados.get(reference).size()},target);
                sheet.addValidationData(dropdown);
            }
        }
        //Enable autosize columns
        for (Integer i = 0; i < htNames.length; i++){ sheet.autoSizeColumn(i); }
        //Hide list sheet
        workbook.setSheetHidden(1, true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        String fecha = new SimpleDateFormat("yyyy-MM-dd HHmm").format(new Date());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Solicitud_Becas "+fecha+".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @RequestMapping(value = "upload/template", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse templateUpload(@RequestParam("file") MultipartFile file, ServletRequest req, HttpServletResponse response) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, List<ControlMaestroMultipleComboProjection>> listados = new HashMap<>();
        List<ProgramaIdiomaComboSimpleProjection> cursos = programaIdiomaDao.findProjectionAllByActivoIsTrue();
        List<PAModalidadComboSimpleProjection> modalidades = paModalidadDao.findComboAllByActivoTrueOrderByNombre();
        List<EntidadBecaComboProjection> entidades = entidadBecaDao.findProjectedComboAllByActivoTrueOrderByNombre();
        //listados.put("entidadBeca"  ,controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor("CMM_BEC_EntidadBeca"));
        List<String> errors = new ArrayList<>();
        InputStream stream = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator rows = sheet.iterator();
        rows.next();
        Row header = (Row) rows.next();
        BecaSolicitud solicitud = new BecaSolicitud();
        solicitud.setCreadoPorId(usuarioId);
        solicitud.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
        solicitud.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("SB"));
        solicitud.setBecas(new ArrayList<>());
        solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_BECS_Estatus.APROBADA);

        Integer index = 2;
        Integer procesados = 0;

        while (rows.hasNext()){
            Row row = (Row) rows.next();
            index ++;
            //Validate required columns
            Integer[] requiredColumns = {0, 1, 2, 3, 5, 6, 7, 8, 9};
            Boolean valid = true;
            try{
                for (Integer i : requiredColumns){ getCellValue(row,i,true); }
            } catch (Exception e){
                //if first value is not present, only ignore row
                if(!e.getMessage().equals("A"))
                    throw new Exception(e.getMessage()+" es un valor requerido.");
                //errors.add("Required value: "+e.getMessage()+index.toString());
                valid = false;
            }
            //if all required values are present, fill the objects
            if(valid){
                BecaUDG beca = new BecaUDG();
                String cursoStr = getCellValue(row,5, true);
                String modalidadStr = getCellValue(row,6, true);
                ProgramaIdiomaComboSimpleProjection tipoBeca = cursos.stream().filter(item -> cursoStr.toUpperCase().equals(item.getNombre().toUpperCase())).findAny().orElse(null);
                EntidadBecaComboProjection entidadBeca = entidades.stream().filter(item -> cursoStr.toUpperCase().equals(item.getNombre().toUpperCase())).findAny().orElse(null);
                //beca.setEntidadId(getCMMIdByString(listados.get("entidadBeca"), getCellValue(row, 0, true), false));
                beca.setTipoId(ControlesMaestrosMultiples.CMM_BECU_Tipo.PROULEX);
                beca.setEntidadBecaId(entidadBeca.getId());
                beca.setProgramaIdiomaId(tipoBeca.getId());
                beca.setCodigoAlumno(getCellValue(row,1, true));
                beca.setPrimerApellido(getCellValue(row,2, true));
                beca.setSegundoApellido(getCellValue(row,3, true));
                beca.setNombre(getCellValue(row,4, true));
                PAModalidadComboSimpleProjection modalidad = modalidades.stream().filter(item -> modalidadStr.toUpperCase().equals(item.getNombre().toUpperCase())).findAny().orElse(null);
                beca.setPaModalidadId(modalidad.getId());
                beca.setNivel(Integer.valueOf(getCellValue(row,7, true)));
                beca.setDescuento(new BigDecimal(getCellValue(row,8, true)).divide(BigDecimal.valueOf(100)));
                beca.setComentarios(getCellValue(row,9, true));
                beca.setCodigoBeca(autonumericoService.getSiguienteAutonumericoByPrefijo("PB"));
                beca.setCodigoEmpleado("");
                beca.setFechaAlta(new Timestamp(System.currentTimeMillis()));
                //Aqui poner las alertas
                /////////////////////////////////////////////
                beca.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.APLICADA);
                solicitud.getBecas().add(beca);
                procesados ++;
            }
        }
        if(solicitud.getBecas().size() > 0){
            solicitud = becaSolicitudDao.save(solicitud);
            Integer logTipo = LogTipo.ACEPTADA;
            Boolean requiereAlerta = alertasService.validarAutorizacion(AlertasConfiguraciones.BECAS_SOLICITUD, solicitud.getId(), solicitud.getCodigo(), "Solicitud de beca", null, usuarioId, "Solicitudes de Becas");
            if(requiereAlerta){
                logTipo = LogTipo.POR_AUTORIZAR;
                solicitud.setEstatusId(ControlesMaestrosMultiples.CMM_BECS_Estatus.PENDIENTE);
                for(BecaUDG beca : solicitud.getBecas()){
                    beca.setEstatusId(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
                }
                solicitud = becaSolicitudDao.save(solicitud);
            }

            logController.insertaLogUsuario(
                    new Log(null,
                            logTipo,
                            LogProceso.SOLICITUDES_BECAS,
                            solicitud.getId()
                    ),
                    usuarioId
            );
        }else{
            return new JsonResponse(null, "El archivo se encuentra vacío", JsonResponse.STATUS_ERROR);
        }

        return new JsonResponse(errors, procesados.toString()+" registros procesados!", JsonResponse.STATUS_OK);
    }

    private List<String> CMMtoList(String control, Boolean referencia){
        if (referencia){
            return controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(control).stream()
                    .map(item -> item.getReferencia()).collect(Collectors.toList());
        } else {
            return controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(control).stream()
                    .map(item -> item.getValor()).collect(Collectors.toList());
        }

    }

    private Integer getCMMIdByString(List<ControlMaestroMultipleComboProjection> listado, String valor, Boolean referencia){
        ControlMaestroMultipleComboProjection control = null;
        if (referencia)
            control = listado.stream().filter(item -> valor.toUpperCase().equals(item.getReferencia().toUpperCase())).findAny().orElse(null);
        else
            control = listado.stream().filter(item -> valor.toUpperCase().equals(item.getValor().toUpperCase())).findAny().orElse(null);
        return control != null? control.getId() : null;
    }

    private CellStyle createStyle(XSSFWorkbook workbook, Short background, Short fontColor, boolean bold, boolean locked){
        CellStyle style = workbook.createCellStyle();
        if (background != null) {
            style.setFillForegroundColor(background);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        XSSFFont font = workbook.createFont();
        if (fontColor != null) { font.setColor(fontColor); }
        if (bold) { font.setBold(true); }
        style.setLocked(locked);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private void createRow(XSSFSheet sheet, Integer rowNumber, String[] values, CellStyle style) {
        XSSFRow row = sheet.createRow(rowNumber);
        Integer index = 0;
        for (String value : values) {
            createCell(row, index, value, style);
            index++;
        }
    }

    private XSSFDataValidation createDropdown(XSSFWorkbook workbook, Integer[] reference, Integer target){
        String name = "list"+target;
        Name namedCell = workbook.createName();
        String refName = CellReference.convertNumToColString(reference[0]);
        namedCell.setNameName(name);
        namedCell.setRefersToFormula("hidden!$"+refName+"$1:$"+refName+"$" + reference[1]);
        XSSFDataValidationHelper helper = new XSSFDataValidationHelper(workbook.getSheet("hidden"));
        XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) helper.createFormulaListConstraint(name);
        CellRangeAddressList addressList = new CellRangeAddressList(2, 20000, target, target);
        XSSFDataValidation validation = (XSSFDataValidation) helper.createValidation(constraint,addressList);

        return validation;
    }

    private String getCellValue(Row row, Integer index, Boolean required) throws Exception {
        try{
            Cell cell = row.getCell(index, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (required && cell == null)
                throw new Exception(CellReference.convertNumToColString(index));
            else if (cell == null)
                return null;
            DataFormatter formatter = new DataFormatter();
            String response = formatter.formatCellValue(cell);
            if (response.trim().length() == 0)
                throw new Exception(CellReference.convertNumToColString(index));
            if (response.toUpperCase().equals("N/A"))
                throw new Exception(CellReference.convertNumToColString(index));
            return response;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {
        String query = "SELECT solicitudBeca,entidad,codigoBeca,codigoAlumno,CONCAT(primerApellido,' ',segundoApellido,' ',nombre) as nombre,curso,modalidad,nivel,CONCAT(descuento,'%') as descuento,estatus FROM [dbo].[VW_LISTADO_SOLICITUDES_BECAS] ORDER BY solicitudBeca";
        String[] alColumnas = new String[]{"Solicitud Beca", "Entidad", "Codigo Beca", "Codigo Alumno", "Nombre","Curso","Modalidad","Nivel","Descuento","Estatus"};

        excelController.downloadXlsx(response, "Becas", query, alColumnas, null, "Becas");
    }

    //@Scheduled(cron = "0 0 3 * * *")
    @RequestMapping(value = "/revocar", method = RequestMethod.GET)
    public void revocarBecas() {
        List<BecaUDG> becas = becaUDGDao.findAllByFechaExpiracionIsLessThanAndEstatusId(new Date(), ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR);
        for (BecaUDG beca : becas){
            beca.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.EXPIRADA);
        }
        becaUDGDao.saveAll(becas);
    }

    @Transactional
    @RequestMapping(value = "/delete/{becaId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String becaId) throws SQLException {
        BecaUDG becaUDG = becaUDGDao.findById(hashId.decode(becaId));

        if(becaUDG.getEstatusId().compareTo(ControlesMaestrosMultiples.CMM_BECU_Estatus.PENDIENTE_POR_APLICAR) != 0){
            return new JsonResponse(null,"La beca ya fue cancelada, por favor recargar la pagina.",JsonResponse.STATUS_ERROR);
        }

        becaUDG.setEstatusId(ControlesMaestrosMultiples.CMM_BECU_Estatus.CANCELADA);

        try{
            becaUDG = becaUDGDao.save(becaUDG);
        }catch (Exception ex){
            return new JsonResponse(null,"Algo mal la beca, por favor intente más tarde",JsonResponse.STATUS_ERROR);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }
}
