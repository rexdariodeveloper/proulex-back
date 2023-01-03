package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.Almacen;
import com.pixvs.main.models.OrdenCompra;
import com.pixvs.main.models.Requisicion;
import com.pixvs.main.models.RequisicionPartida;
import com.pixvs.main.models.mapeos.*;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.main.models.projections.Requisicion.RequisicionEditarProjection;
import com.pixvs.main.models.projections.Requisicion.RequisicionListadoProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.storage.FileSystemStorageService;
import com.pixvs.spring.util.HashId;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 21/10/2020.
 */
@RestController
@RequestMapping("/api/v1/requisiciones")
public class RequisicionController {

    @Autowired
    private RequisicionDao requisicionDao;
    @Autowired
    private DepartamentoDao departamentoDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private UnidadMedidaDao unidadMedidaDao;
    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private AlertaConfigDao alertaConfigDao;
    @Autowired
    private AlertasDetalleDao alertaDetalleDao;
    @Autowired
    private AlertasDao alertaDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private AutonumericoService autonumericoService;
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @Autowired
    private LogController logController;

    @Autowired
    private Environment environment;

    @Autowired
    private ProcesadorAlertasService alertasService;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getRequisiciones(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<RequisicionListadoProjection> requisiciones = requisicionDao.findProjectedListadoAllByUsuarioId(idUsuario);

        return new JsonResponse(requisiciones, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Requisicion requisicion, ServletRequest req, Boolean enviar) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Boolean esNuevo = requisicion.getId() == null;

        if(requisicion.getAlmacen() != null){
            requisicion.setAlmacenId(requisicion.getAlmacen().getId());
        }else{
            requisicion.setAlmacenId(null);
        }

        if(requisicion.getDepartamento() != null){
            requisicion.setDepartamentoId(requisicion.getDepartamento().getId());
        }else{
            requisicion.setDepartamentoId(null);
        }

        if (requisicion.getId() == null) {
            requisicion.setCreadoPorId(idUsuario);
            requisicion.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("REQ"));
            if(enviar != null && enviar){
                requisicion.setEstadoRequisicionId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.EN_PROCESO);
                requisicion.setEnviadaPorId(idUsuario);
            }else{
                requisicion.setEstadoRequisicionId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.GUARDADO);
            }
        } else {
            Requisicion objetoActual =requisicionDao.findById(requisicion.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),requisicion.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            if(!objetoActual.getEstadoRequisicionId().equals(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.GUARDADO)){
                return new JsonResponse(null, "No es posible realizar esta acción", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }
            requisicion.setModificadoPorId(idUsuario);
            if(enviar != null && enviar){
                requisicion.setEstadoRequisicionId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.EN_PROCESO);
                requisicion.setEnviadaPorId(idUsuario);
            }else{
                requisicion.setEstadoRequisicionId(objetoActual.getEstadoRequisicionId());
            }
        }

        for(RequisicionPartida partida : requisicion.getPartidas()){
            if(partida.getId() != null && partida.getImg64() != null && partida.getImagenArticuloId() != null){
                fileSystemStorageService.borrarArchivo(partida.getImagenArticuloId());
            }
            if (partida.getImg64() != null) {
                Archivo archivo = fileSystemStorageService.storeBase64(partida.getImg64(), idUsuario, 4, null, true, true);
                partida.setImagenArticuloId(archivo.getId());
            }
        }

        int numeroPartida = 0;
        for(RequisicionPartida partida : requisicion.getPartidas()){
            partida.setNumeroPartida(++numeroPartida);

            if(partida.getArticulo() != null){
                partida.setArticuloId(partida.getArticulo().getId());
            }else{
                partida.setArticuloId(null);
            }

            if(partida.getUnidadMedida() != null){
                partida.setUnidadMedidaId(partida.getUnidadMedida().getId());
            }else{
                partida.setUnidadMedidaId(null);
            }

            partida.setEstadoPartidaId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_REQP_EstatusRequisicionPartida.ABIERTA);

        }

        requisicion = requisicionDao.save(requisicion);

        Integer logTipo;
        if(enviar != null && enviar){
            logTipo = LogTipo.ENVIADO;
        }else if(esNuevo){
            logTipo = LogTipo.CREADO;
        }else{
            logTipo = LogTipo.MODIFICADO;
        }

        logController.insertaLogUsuario(
                new Log(null,
                        logTipo,
                        LogProceso.REQUISICIONES,
                        requisicion.getId()
                ),
                idUsuario
        );

        return new JsonResponse((esNuevo ? requisicion.getCodigo(): null), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/enviar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse enviar(@RequestBody Requisicion requisicion, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Boolean esNuevo = requisicion.getId() == null;

        guardar(requisicion,req,true);
		Requisicion requi = requisicionDao.findById(requisicion.getId());
		Almacen almacen = almacenDao.findById(requi.getAlmacenId());

		Boolean requiereAlerta = alertasService.validarAutorizacion(AlertasConfiguraciones.REQUISICIONES_VALIDACION, requi.getId(), requi.getCodigo(), "Requisición", almacen.getSucursalId(), idUsuario,almacen.getSucursal().getNombre());

		if(requiereAlerta){
			logController.insertaLogUsuario(
					new Log(null,
							LogTipo.POR_AUTORIZAR,
							LogProceso.REQUISICIONES,
							requi.getId()
					),
					idUsuario
			);
			requi.setEstadoRequisicionId(ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.POR_AUTORIZAR);
			requi = requisicionDao.save(requi);
		}
		return new JsonResponse((esNuevo ? requisicion.getCodigo(): null), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/{idRequisicion}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idRequisicion) throws SQLException {

        RequisicionEditarProjection requisicion = requisicionDao.findProjectedEditarById(idRequisicion);

        return new JsonResponse(requisicion, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{hashidRequisicion}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String hashidRequisicion, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Requisicion actualizado = requisicionDao.findById(hashId.decode(hashidRequisicion));
        actualizado.setEstadoRequisicionId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.BORRADA);
        requisicionDao.save(actualizado);

        logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.BORRADO,
                        LogProceso.REQUISICIONES,
                        actualizado.getId()
                ),
                idUsuario
        );

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoRequisicionesById(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        RequisicionEditarProjection requisicion = null;
        List<Log> historial = null;
        if (id != null) {
             requisicion = requisicionDao.findProjectedEditarById(id);
            historial = logController.getHistorial(id, LogProceso.REQUISICIONES);
        }

        List<DepartamentoComboProjection> departamentos = departamentoDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<AlmacenComboProjection> almacenes = almacenDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<ArticuloComboProjection> articulos = articuloDao.getArticulosCompradosNoInventariables();
        List<ArticuloComboProjection> articulosMiscelaneos = new ArrayList<>();
        List<UnidadMedidaComboProjection> unidadesMedida = unidadMedidaDao.findProjectedComboAllByActivoTrue();

        HashMap<String, Object> json = new HashMap<>();

        json.put("requisicion", requisicion);
        json.put("departamentos", departamentos);
        json.put("almacenes", almacenes);
        json.put("articulos", articulos);
        json.put("articulosMiscelaneos", articulosMiscelaneos);
        json.put("unidadesMedida", unidadesMedida);
        json.put("historial", historial);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_REQUISICIONES]";
        String[] alColumnas = new String[]{"Código de requisición", "Fecha de requisición", "Sede - Departamento", "Usuario solicitante", "Estado requisición"};

        excelController.downloadXlsx(response, "requisiciones", query, alColumnas, null,"Requisiciones");
    }

    @RequestMapping(value="/pdf/{requisicionHashId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@PathVariable String requisicionHashId, HttpServletResponse response) throws Exception{

        Integer requisicionId = hashId.decode(requisicionHashId);
        Requisicion requisicion = requisicionDao.findById(requisicionId);

        String reportePath = "/requisiciones/Requisicion.jasper";

        HashMap<String,Object> params = new HashMap<>();
        params.put("path",environment.getProperty("environments.pixvs.front.url"));
        params.put("id",requisicionId);

        InputStream reporte = reporteService.generarReporte(reportePath,params);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+requisicion.getCodigo()+".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());
        response.flushBuffer();
    }

    /** ALERTAS **/
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/aprobar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse aprobarAlerta(@RequestBody Integer id, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        actualizarAlerta(id,idUsuario,true, null);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/alerta/rechazar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse rechazarAlerta(@RequestBody HashMap<String,Object> body, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer id = (Integer) body.get("id");
        String comentario = (String) body.get("comentario");
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        actualizarAlerta(id,idUsuario,false, comentario);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    private void actualizarAlerta(Integer procesoId, Integer usuarioId, Boolean autorizar, String comentario) throws Exception{
        MenuPrincipal nodo = menuDao.findByUrl("/app/compras/requisiciones");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),procesoId, com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO);
        for ( AlertaDetalle detalle : alerta.getDetalles() ){
            if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                alertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, autorizar,comentario);
            }
        }
    }

}

