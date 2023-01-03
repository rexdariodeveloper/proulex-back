package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.Prenomina;
import com.pixvs.main.models.PrenominaMovimiento;
import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.main.models.ProgramaGrupoListadoClase;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Prenomina.PrenominaListadoPagarProjection;
import com.pixvs.main.models.projections.Prenomina.PrenominaQuincenaFechasProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.DateUtil;
import net.minidev.json.JSONObject;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.poi.ss.util.CellUtil.createCell;

/**
 * Created by Angel Daniel Hernández Silva on 14/08/2021.
 */
@RestController
@RequestMapping("/api/v1/prenomina")
public class PrenominaController {

    @Autowired
    private PrenominaDao prenominaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ProgramaGrupoListadoClaseDao programaGrupoListadoClaseDao;
    @Autowired
    private PrenominaMovimientoDao prenominaMovimientoDao;
    @Autowired
    private ProgramaGrupoDao programaGrupoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPrenomina(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatter = formmat1.format(ldt);

        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<PrenominaQuincenaFechasProjection> fechasQuincena = prenominaDao.getFechaQuincena(formatter);
        HashMap<String, Object> data = new HashMap<>();
        if(sucursales.size() == 1){
            data.put("datos", prenominaDao.findAllPrenominaSucursal(dateFormat.format(fechasQuincena.get(0).getFechaInicio()),dateFormat.format(fechasQuincena.get(0).getFechaFin()),sucursales.get(0).getId()));
        }else {
            data.put("datos", new ArrayList<>());
        }
        data.put("sucursales", sucursales);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/quincenas/{sucursalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getQuincenas(@PathVariable Integer sucursalId, ServletRequest req) throws SQLException {

        Date fechaHoy = new Date();
        Date fechaInicioPrenomina = prenominaDao.getFechaInicialPrenomina();
        List<PrenominaQuincenaFechasProjection> fechasNoPagadas = prenominaDao.getFechasQuincenaConPrenominaPorSucursal(fechaInicioPrenomina,fechaHoy,sucursalId);
        HashMap<String, Object> data = new HashMap<>();
        data.put("quincenas",fechasNoPagadas);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPrenominaFiltro(@RequestBody JSONObject json,ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String,Object> sucursales = (HashMap<String,Object>)json.get("sucursales");
        String sucursaBuscar = sucursales.get("id").toString();
        HashMap<String,String> quincena = (HashMap<String,String>)json.get("quincena");
        if(quincena == null){
            return new JsonResponse(null,"Cargando quincenas",JsonResponse.STATUS_ERROR_NULL);
        }
        String[] fechas = quincena.get("nombre").split("[/]", 0);
        String fechaInicio = fechas[0];
        String fechaFin = fechas[1];
        return new JsonResponse(prenominaDao.findAllPrenominaSucursal(fechaInicio,fechaFin,Integer.valueOf(sucursaBuscar)), null, JsonResponse.STATUS_OK);
    }

    /*@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody List<PrenominaListadoPagarProjection> prenomina, ServletRequest req) throws Exception {

        return new JsonResponse(null,"",JsonResponse.STATUS_OK);
    }*/
    @RequestMapping(value = "/template/generarNomina", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public void templateDownload(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws IOException, ParseException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        String fechaInicio = json.getAsString("fechaInicio");
        String fechaFin = json.getAsString("fechaFin");
        HashMap<String,Object> sucursales = (HashMap<String,Object>)json.get("sucursales");
        String sucursaBuscar = sucursales.get("id").toString();
        List<PrenominaListadoPagarProjection> datos = prenominaDao.findAllPrenominaSucursalOrderByGrupoFechaInicioAndCodigoGrupoAndEmpleado(fechaInicio,fechaFin,Integer.valueOf(sucursaBuscar));
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);
        XSSFSheet sheet = workbook.createSheet("PRENOMINA");
        String[] ht = {"CODIGO_EMPLEADO","SEDE", "FECHA DE INICIO", "FECHA DE TÉRMINO","CURSO","NOMBRE","CODIGOPER","CATEGORIA","DESCRIPCIÓN","HORAS","SUELDO POR HORA","PERCEPCIÓN","DEDUCCIÓN","TIPO","MODALIDAD","FECHA CLASE SUPLIDA"};
        createRow(sheet, 0, ht, header);
        for(Integer i=0;i<datos.size();i++){
            PrenominaListadoPagarProjection dato = datos.get(i);
            XSSFRow row = sheet.createRow(i+1);
            Integer tipoMovimientoId = null;

            if(dato.getTipoMovimientoId() != null){
                tipoMovimientoId = dato.getTipoMovimientoId();
            }

            createCell(row,0 , dato.getCodigoEmpleado(), detail);
            createCell(row,1 , dato.getSucursal(), detail);
            createCell(row,2 , dato.getGrupoFechaInicio() == null ? null : DateUtil.getFecha(dato.getGrupoFechaInicio(),"dd/MM/yyyy"), detail);
            createCell(row,3 , dato.getGrupoFechaFin() == null ? null : DateUtil.getFecha(dato.getGrupoFechaFin(),"dd/MM/yyyy"), detail);
            createCell(row,4 , dato.getCodigoGrupo() == null ? "" : dato.getCodigoGrupo(), detail);
            createCell(row,5 , dato.getEmpleado(), detail);
            createCell(row,6 , dato.getTabulador(), detail);
            createCell(row,7 , dato.getCategoria(), detail);
            createCell(row,8 , dato.getNombreGrupo(), detail);
            createCell(row,9 , dato.getHorasPagadas().toString(), detail);
            createCell(row,10 , dato.getSueldoPorHora(), detail);
            createCell(row,11 , dato.getPercepcion() == null ? "" : dato.getPercepcion(), detail);
            createCell(row,12 , dato.getDeduccion() == null ? "" : dato.getDeduccion(), detail);
            createCell(row,13 , dato.getIdioma(), detail);
            createCell(row,14 , dato.getModalidad() == null ? "" : dato.getModalidad(), detail);
            createCell(row,15 , dato.getFechaDiaSuplida() == null ? "" : DateUtil.getFecha(dato.getFechaDiaSuplida()), detail);

            BigDecimal horasPagadas = dato.getHorasPagadas();

            Prenomina prenomina = new Prenomina();
            prenomina.setActivo(true);
            prenomina.setEmpleadoDeduccionPercepcionId(dato.getDeduccionPercepcionId());
            prenomina.setFechaInicioQuincena(new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio));
            prenomina.setFechaFinQuincena(new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin));
            prenomina.setCreadoPorId(idUsuario);
            prenomina.setProgramaGrupoId(dato.getIdGrupo());
            prenomina.setEmpleadoId(Integer.valueOf(dato.getIdEmpleado()));
            prenomina.setFechaDiaFestivo(dato.getFechaDiaFestivo());
            prenomina.setFechaDiaSuplida(dato.getFechaDiaSuplida());
            prenomina.setEsApoyoTransporte(false);
            if(dato.getIdSuplencia() != null){
                ProgramaGrupoListadoClase suplencia = programaGrupoListadoClaseDao.findById(dato.getIdSuplencia());
                if(horasPagadas.compareTo(BigDecimal.ZERO) >= 0){
                    suplencia.setFechaPago(new Timestamp(System.currentTimeMillis()));
                }else{
                    suplencia.setFechaDeduccion(new Timestamp(System.currentTimeMillis()));
                }
                programaGrupoListadoClaseDao.save(suplencia);
                ProgramaGrupo grupo = programaGrupoDao.findById(suplencia.getGrupoId());
                grupo.setModificadoPorId(idUsuario);
                programaGrupoDao.save(grupo);
            }else if(tipoMovimientoId != null){
                if(tipoMovimientoId.intValue() == ControlesMaestrosMultiples.CMM_PRENOM_TipoMovimiento.PAGO_A_PROFESOR_TITULAR){
                    Integer empleadoId = dato.getIdEmpleado();
                    Integer grupoId = dato.getIdGrupo();
                    Date fechaInicioPeriodo = dato.getFechaInicioPeriodo();
                    Date fechaFinPeriodo = dato.getFechaFinPeriodo();

                    List<Integer> clasesSuplidasIds = programaGrupoListadoClaseDao.findAllByProfesorTitularIdAndGrupoIdAndPeriodoAndFechaDeduccionIsNull(empleadoId,grupoId,fechaInicioPeriodo,fechaFinPeriodo);
                    if(clasesSuplidasIds.size() > 0){
                        List<ProgramaGrupoListadoClase> clasesSuplidas = programaGrupoListadoClaseDao.findAllByIdIn(clasesSuplidasIds);
                        for(ProgramaGrupoListadoClase claseSuplida : clasesSuplidas){
                            claseSuplida.setFechaDeduccion(new Timestamp(System.currentTimeMillis()));
                            programaGrupoListadoClaseDao.save(claseSuplida);
                            ProgramaGrupo grupo = programaGrupoDao.findById(claseSuplida.getGrupoId());
                            grupo.setModificadoPorId(idUsuario);
                            programaGrupoDao.save(grupo);
                        }
                    }
                }else if(tipoMovimientoId.intValue() == ControlesMaestrosMultiples.CMM_PRENOM_TipoMovimiento.DEDUCCION_POR_CANCELACION_DE_CLASE){
                    prenomina.setFechaClaseCancelada(dato.getFechaInicioPeriodo());
                }else if(tipoMovimientoId.intValue() == ControlesMaestrosMultiples.CMM_PRENOM_TipoMovimiento.APOYO_PARA_TRANSPORTE){
                    prenomina.setEsApoyoTransporte(true);
                }
            }
            prenomina.setSueldoPorHora(dato.getSueldoPorHoraDecimal());
            prenominaDao.save(prenomina);

            PrenominaMovimiento prenominaMovimiento = new PrenominaMovimiento();
            prenominaMovimiento.setEmpleadoId(dato.getIdEmpleado());
            prenominaMovimiento.setFechaInicioPeriodo(dato.getFechaInicioPeriodo());
            prenominaMovimiento.setFechaFinPeriodo(dato.getFechaFinPeriodo());
            prenominaMovimiento.setFechaInicioQuincena(new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio));
            prenominaMovimiento.setFechaFinQuincena(new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin));
            if(horasPagadas.compareTo(BigDecimal.ZERO) >= 0){
                prenominaMovimiento.setHorasPercepcion(horasPagadas);
            }else{
                prenominaMovimiento.setHorasDeduccion(horasPagadas.negate());
            }
            prenominaMovimiento.setSueldoPorHora(dato.getSueldoPorHoraDecimal());
            prenominaMovimiento.setTipoMovimientoId(dato.getTipoMovimientoId());
            if(dato.getMovimientoReferenciaId() != null){
                prenominaMovimiento.setMovimientoReferenciaId(dato.getMovimientoReferenciaId());
            }
            prenominaMovimiento.setReferenciaProcesoTabla(dato.getReferenciaProcesoTabla());
            prenominaMovimiento.setReferenciaProcesoId(dato.getReferenciaProcesoId());
            prenominaMovimientoDao.save(prenominaMovimiento);
        }
        sheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        for (Integer i = 0; i < ht.length; i++){ sheet.autoSizeColumn(i); }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Prenomina.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
    }

    @RequestMapping(value = "/template/preReporte/generarNomina", method = RequestMethod.POST)
    @Transactional(rollbackFor = Exception.class)
    public void templatePreReporteDownload(@RequestBody JSONObject json,HttpServletResponse response,ServletRequest req) throws IOException, ParseException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        String fechaInicio = json.getAsString("fechaInicio");
        String fechaFin = json.getAsString("fechaFin");
        HashMap<String,Object> sucursales = (HashMap<String,Object>)json.get("sucursales");
        String sucursaBuscar = sucursales.get("id").toString();
        List<PrenominaListadoPagarProjection> datos = prenominaDao.findAllPrenominaSucursalOrderByGrupoFechaInicioAndCodigoGrupoAndEmpleado(fechaInicio,fechaFin,Integer.valueOf(sucursaBuscar));
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle header = createStyle(workbook, IndexedColors.SEA_GREEN.getIndex(), IndexedColors.WHITE.getIndex(), true, false);
        CellStyle detail = createStyle(workbook, null, null, false, false);
        XSSFSheet sheet = workbook.createSheet("PRENOMINA");
        String[] ht = {"CODIGO_EMPLEADO","SEDE", "FECHA DE INICIO", "FECHA DE TÉRMINO","CURSO","NOMBRE","CODIGOPER","CATEGORIA","DESCRIPCIÓN","HORAS","SUELDO POR HORA","PERCEPCIÓN","DEDUCCIÓN","TIPO", "MODALIDAD","FECHA CLASE SUPLIDA"};
        createRow(sheet, 0, ht, header);
        for(Integer i=0;i<datos.size();i++){
            PrenominaListadoPagarProjection dato = datos.get(i);
            XSSFRow row = sheet.createRow(i+1);
            createCell(row,0 , dato.getCodigoEmpleado(), detail);
            createCell(row,1 , dato.getSucursal(), detail);
            createCell(row,2 , dato.getGrupoFechaInicio() == null ? null : DateUtil.getFecha(dato.getGrupoFechaInicio(),"dd/MM/yyyy"), detail);
            createCell(row,3 , dato.getGrupoFechaFin() == null ? null : DateUtil.getFecha(dato.getGrupoFechaFin(),"dd/MM/yyyy"), detail);
            createCell(row,4 , dato.getCodigoGrupo() == null ? "" : dato.getCodigoGrupo(), detail);
            createCell(row,5 , dato.getEmpleado(), detail);
            createCell(row,6 , dato.getTabulador(), detail);
            createCell(row,7 , dato.getCategoria(), detail);
            createCell(row,8 , dato.getNombreGrupo(), detail);
            createCell(row,9 , dato.getHorasPagadas().toString(), detail);
            createCell(row,10 , dato.getSueldoPorHora(), detail);
            createCell(row,11 , dato.getPercepcion() == null ? "" : dato.getPercepcion(), detail);
            createCell(row,12 , dato.getDeduccion() == null ? "" : dato.getDeduccion(), detail);
            createCell(row,13 , dato.getIdioma(), detail);
            createCell(row,14 , dato.getModalidad() == null ? "" : dato.getModalidad(), detail);
            createCell(row,15 , dato.getFechaDiaSuplida() == null ? "" : DateUtil.getFecha(dato.getFechaDiaSuplida()), detail);
        }
        sheet.addIgnoredErrors(new CellRangeAddress(0,9999,0,9999), IgnoredErrorType.NUMBER_STORED_AS_TEXT );
        for (Integer i = 0; i < ht.length; i++){ sheet.autoSizeColumn(i); }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Prenomina.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        IOUtils.copy(stream, response.getOutputStream());
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

}
