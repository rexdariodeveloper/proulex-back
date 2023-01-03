package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.AlertasConfiguraciones;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.LocalidadArticulo.LocalidadArticuloProjection;
import com.pixvs.main.models.projections.PedidoReciboDetalle.PedidoMovimientoListadoProjection;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen;
import com.pixvs.main.models.mapeos.ArticulosSubtipos;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Localidad.LocalidadComboProjection;
import com.pixvs.main.models.projections.Pedido.PedidoEditarProjection;
import com.pixvs.main.models.projections.Pedido.PedidoListadoProjection;
import com.pixvs.main.models.projections.Pedido.PedidoListadoRecibirProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_PED_EstatusPedido;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_CALE_EstatusAlerta;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_Estatus;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

/*
    Controller for the management of Pedidos.
    Methods Summary:
    ---------------------------------------------------------------------------------------------------------------------
    | Method            | Route             | Description                                                               |
    |-------------------------------------------------------------------------------------------------------------------|
    | getPedidos        | /all              | Returns a list of Pedidos                                                 |
    | getFiltros        | /all/filtros      | Returns a list of Pedidos filtered by date range and status               |
    | getPedido         | /detail[/{id}]    | Returns an instance of Pedido by the given id and a list of auxiliar data,|
    |                   |                   |   if id is omitted just returns a list of auxiliar data                   |
    | savePedido        | /save             | Creates or update an instance of Pedido                                   |
    | deletePedido      | /delete/{id}      | Deletes ("logical") an instance of Pedido by the given id                 |
    | exportPedidos     | /download/excel   | Builds an excel report of all Pedidos stored                              |
    | nextEstado        |                   | Navigates to next state if its possible, and update it on the current     |
    |                   |                   |   Pedido instance                                                         |
    | getArticulos      | /products/{id}    | Returns a list of Articulos filtered by Localidad which id is equal than  |
    |                   |                   |   given                                                                   |
    ---------------------------------------------------------------------------------------------------------------------
    Notes:
    1) Auxiliar data for Pedidos are:
        localidades (Localidad) - Filtered by Localidad which current user has access
        CEDIS       (Localidad) - Filtered by Localidad which are setted as CEDIS
*/

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private PedidoDao pedidoDao;

    @Autowired
    private  PedidoReciboDao reciboDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private LocalidadArticuloDao localidadArticuloDao;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private InventarioMovimientoController inventarioMovimientoController;

    @Autowired
    private LogController logController;

    @Autowired
    private ProcesadorAlertasService alertasService;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private AlertaConfigDao alertaConfigDao;

    @Autowired
    private AlertasDetalleDao alertaDetalleDao;

    @Autowired
    private AlertasDao alertaDao;


    @Autowired
    private AlertaConfigEtapaDao configEtapaDao;

    @Autowired
    private Environment environment;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPedidos(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<PedidoListadoProjection> pedidos = pedidoDao.findAllPedidosByPermiso(idUsuario);

        HashMap<String, Object> data = new HashMap<>();
        data.put("datos", pedidos);
        data.put("estatus", cmmDao.findAllByControl("CMM_PED_EstatusPedido"));

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        ArrayList<HashMap<String, Integer>> allEstatus = (ArrayList<HashMap<String, Integer>>) json.get("estatus");

        ArrayList<Integer> estatus = new ArrayList<>();

        if (allEstatus != null) {
            for (HashMap<String, Integer> status : allEstatus) {
                estatus.add(status.get("id"));
            }
        }

        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");
        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        Date dateFechaCreacionDesde = isNullorEmpty(fechaCreacionDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionDesde);
        Date dateFechaCreacionHasta = isNullorEmpty(fechaCreacionHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionHasta);

        return new JsonResponse(pedidoDao.findAllQueryProjectedBy(dateFechaCreacionDesde, dateFechaCreacionHasta, estatus.isEmpty() ? 1 : 0, estatus), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/detail", "/detail/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPedido(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<LocalidadComboProjection> localidades_activas = localidadDao.findProjectedComboAllByActivoTrueAndTipoAlmacenIdAndPermiso(CMM_ALM_TipoAlmacen.NORMAL,idUsuario);
        List<LocalidadComboProjection> localidades_todas   = localidadDao.findProjectedComboAllByActivoTrueAndTipoAlmacenIdOrderByCodigoLocalidad(CMM_ALM_TipoAlmacen.NORMAL);

        List<PedidoMovimientoListadoProjection> movimientos = new ArrayList<>();

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("localidades", localidades_activas);
        datos.put("localidades_todas", localidades_todas);

        if (id != null) {
            PedidoEditarProjection pedido = pedidoDao.findPedidoById(id);
            datos.put("pedido", pedido);
            datos.put("movimientos", pedidoDao.getMovimientos(0,pedido.getCodigo()));
            datos.put("historial", logController.getHistorial(id, LogProceso.PEDIDOS));
            movimientos = reciboDao.findMoviminetosById(id);
        }

        List<Integer> subtipos = Arrays.asList(ArticulosSubtipos.LIBRO,ArticulosSubtipos.PAQUETE_DE_LIBROS);
        datos.put("articulosLocGeneral", articuloDao.getArticulosInventariablesLocalidadGeneralCedisByInventariableAndSubtipo(true, subtipos));

        datos.put("movimientos", movimientos);

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/products/{id}/{tipo}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getArticulos(@PathVariable Integer id, @PathVariable String tipo) throws SQLException {

        HashMap<String, Object> datos = new HashMap<>();
        List<Integer> subtipos = Arrays.asList(ArticulosSubtipos.LIBRO,ArticulosSubtipos.PAQUETE_DE_LIBROS);
        List<ArticuloComboProjection> articulos = null;
        datos.put("temporada", pedidoDao.getArticulosTemporada());
        if(tipo.equals("almacen")) {

            articulos = articuloDao.getArticulosInventariablesByAlmacenAndSubtipo(id, subtipos, true);

			datos.put("existencia", pedidoDao.getArticulosExistenciaAlmacen(id));
			datos.put("existenciaPaquetes", (JSONObject)JSONValue.parse(pedidoDao.getArticulosPaquetesExistenciaAlmacenAsJson(id)));
		}
		else {
            articulos = articuloDao.findProjectedComboAllByActivoTrueAndArticuloSubtipoIdInAndInventariable(subtipos, true);
			datos.put("existencia", pedidoDao.getArticulosExistencia(id));
			datos.put("existenciaPaquetes", (JSONObject)JSONValue.parse(pedidoDao.getArticulosPaquetesExistenciaAsJson(id)));
		}
        datos.put("articulos", articulos);
        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse savePedido(@RequestBody Pedido pedido, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (pedido.getId() == null) {
            pedido.setCreadoPorId(idUsuario);
            try {
                //pedido.setEstatusId(CMM_PED_EstatusPedido.GUARDADO);
                String response = this.nextEstado(pedido,null);
                if(response != null)
                    throw new Exception(response);
            } catch (Exception e){
                return new JsonResponse("", e.getMessage(), JsonResponse.STATUS_ERROR_PROBLEMA);
            }
            pedido.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PDD"));
            pedido.setLocalidadOrigenId(pedido.getLocalidadOrigen().getId());
            pedido.setLocalidadCEDISId(pedido.getLocalidadCEDIS().getId());
            Almacen almacenDestino =  pedido.getLocalidadCEDIS().getAlmacen();
            Integer noExisten = 0;
            for (PedidoDetalle detalle: pedido.getDetalles()){
                LocalidadArticuloProjection la = localidadArticuloDao.findLocalidadArticuloByalmacenIdAnd(almacenDestino.getId(), detalle.getArticuloId());
                if(la == null){
                    noExisten += 1;
                }
            }
            if(noExisten > 0){
                return new JsonResponse("", noExisten+" artículo(s) no estan activo(s) en el destino.", JsonResponse.STATUS_ERROR_PROBLEMA);
            }
        } else {
            Pedido actual = pedidoDao.findById(pedido.getId().intValue()).get();
            /*try {
                concurrenciaService.verificarIntegridad(actual.getFechaModificacion(), pedido.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", actual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }*/
            pedido.setCodigo(actual.getCodigo());
            pedido.setLocalidadOrigenId(pedido.getLocalidadOrigen().getId());
            pedido.setLocalidadCEDISId(pedido.getLocalidadCEDIS().getId());
            pedido.setModificadoPorId(idUsuario);
            pedido.setCreadoPorId(actual.getCreadoPorId());
            pedido.setEstatusId(actual.getEstatusId());
        }

        for(PedidoDetalle detalle : pedido.getDetalles()){
            if(detalle.getEstatusId() == 0)
                detalle.setEstatusId(CMM_Estatus.ACTIVO);
        }

        pedido = pedidoDao.save(pedido);
        createLogItem(pedido,idUsuario);

        return new JsonResponse(pedido.getCodigo(), null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse sendPedido(@RequestBody Pedido pedido, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        if (pedido.getId() == null) {
            pedido.setCreadoPorId(idUsuario);
            pedido.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PDD"));
            pedido.setLocalidadOrigenId(pedido.getLocalidadOrigen().getId());
            pedido.setLocalidadCEDISId(pedido.getLocalidadCEDIS().getId());

            Almacen almacenDestino =  pedido.getLocalidadCEDIS().getAlmacen();
            Integer noExisten = 0;
            for (PedidoDetalle detalle: pedido.getDetalles()){
                LocalidadArticuloProjection la = localidadArticuloDao.findLocalidadArticuloByalmacenIdAnd(almacenDestino.getId(), detalle.getArticuloId());
                if(la == null){
                    noExisten += 1;
                }
            }
            if(noExisten > 0){
                return new JsonResponse("", noExisten+" artículo(s) no estan activo(s) en el destino.", JsonResponse.STATUS_ERROR_PROBLEMA);
            }
        } else {
            Pedido actual = pedidoDao.findById(pedido.getId().intValue()).get();
            /*try {
                concurrenciaService.verificarIntegridad(actual.getFechaModificacion(), pedido.getFechaModificacion());
            } catch (Exception e) {
                return new JsonResponse("", actual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }*/
            pedido.setCodigo(actual.getCodigo());
            pedido.setLocalidadOrigenId(pedido.getLocalidadOrigen().getId());
            pedido.setLocalidadCEDISId(pedido.getLocalidadCEDIS().getId());
            pedido.setModificadoPorId(idUsuario);
            pedido.setCreadoPorId(actual.getCreadoPorId());
            pedido.setEstatusId(actual.getEstatusId());
        }

        pedido.setEstatusId(CMM_PED_EstatusPedido.GUARDADO);
        pedido = pedidoDao.save(pedido);

        //try {
            pedido.setEstatusId(CMM_PED_EstatusPedido.GUARDADO);
            String response = this.nextEstado(pedido,null);
            if(response != null)
                throw new Exception(response);
       /* } catch (Exception e){
            return new JsonResponse("", e.getMessage(), JsonResponse.STATUS_ERROR_PROBLEMA);
        }*/

        for(PedidoDetalle detalle : pedido.getDetalles()){
            if(detalle.getEstatusId() == 0)
                detalle.setEstatusId(CMM_Estatus.ACTIVO);
        }

        pedido = pedidoDao.save(pedido);
        createLogItem(pedido,idUsuario);

        return new JsonResponse(pedido.getCodigo(), null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deletePedido(@PathVariable String id, ServletRequest req) throws SQLException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        int actualizado = pedidoDao.actualizarEstatus(hashId.decode(id), CMM_PED_EstatusPedido.BORRADO);

        Pedido pedido = pedidoDao.findById(hashId.decode(id)).get();

        createLogItem(pedido,idUsuario);

        return new JsonResponse(actualizado, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void exportPedidos(HttpServletResponse response, ServletRequest req) throws IOException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        String query = "SELECT * from [VW_LISTADO_PEDIDOS] WHERE usuarioId = " + idUsuario + " ORDER BY \"Fecha\" DESC";
        String[] alColumnas = new String[]{"Código", "Fecha", "Origen", "CEDIS", "Estatus"};

        excelController.downloadXlsx(response, "pedidos", query, alColumnas, null, "Pedidos");
    }

    /** SURTIR PEDIDOS **/

    @RequestMapping(value = "/supply", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPedidosPorSurtir(ServletRequest req) throws SQLException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<Integer> status = new ArrayList<>();
        status.add(CMM_PED_EstatusPedido.POR_SURTIR);
        status.add(CMM_PED_EstatusPedido.SURTIDO_PARCIAL);

        List<PedidoListadoProjection> pedidos = pedidoDao.getPedidosPorSurtir(status,idUsuario);

        List<PedidoListadoProjection> filtrados = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for( PedidoListadoProjection pedido : pedidos){
            if(!ids.contains(pedido.getCodigo())){
                filtrados.add(pedido);
                ids.add(pedido.getCodigo());
            }
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("datos", filtrados);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/supply/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPorSurtirFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");
        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        Date dateFechaCreacionDesde = isNullorEmpty(fechaCreacionDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionDesde);
        Date dateFechaCreacionHasta = isNullorEmpty(fechaCreacionHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionHasta);

        List<Integer> status = new ArrayList<>();
        status.add(CMM_PED_EstatusPedido.POR_SURTIR);
        status.add(CMM_PED_EstatusPedido.SURTIDO_PARCIAL);

        return new JsonResponse(pedidoDao.findAllQueryEstatusBy(dateFechaCreacionDesde, dateFechaCreacionHasta, status), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/supply/detail", "/supply/detail/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDetallesSurtir(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<LocalidadComboProjection> localidades_activas = localidadDao.findProjectedComboAllByActivoTrueOrderByCodigoLocalidad();
        List<LocalidadComboProjection> localidades_todas   = localidadDao.findProjectedComboAllByActivoTrueOrderByCodigoLocalidad();

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("localidades", localidades_activas);
        datos.put("localidades_todas", localidades_todas);

        if (id != null) {
            PedidoEditarProjection pedido = pedidoDao.findPedidoById(id);
            datos.put("pedido", pedido);
            datos.put("movimientos", pedidoDao.getMovimientos(0,pedido.getCodigo()));
            datos.put("historial", logController.getHistorial(id, LogProceso.PEDIDOS));
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/supply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse saveSurtir(@RequestBody List<JSONObject> movimientos, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        AlmacenComboProjection almacenTransito = almacenDao.findProjectedComboByActivoTrueAndTipoAlmacenId(ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen.TRANSITO);
        Localidad localidadTransito = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacenTransito.getId());

        Integer actualId = (Integer) movimientos.get(0).get("pedidoId");
        String fechaModificacionStr = (String)movimientos.get(0).get("fechaModificacion");
        Date fechaModificacion = null;
        Pedido pedido = pedidoDao.findById(actualId).get();
        if(fechaModificacionStr != null){
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSS");
            fechaModificacion = new Date(dateFormatter.parse(fechaModificacionStr).getTime());
        }

        try{
            concurrenciaService.verificarIntegridad(pedido.getFechaModificacion(),fechaModificacion);
        }catch (Exception e){
            return new JsonResponse("", pedido.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
        }

        for( JSONObject movimiento : movimientos){

            Integer id         = (Integer) movimiento.get("id");
            Integer articuloId = (Integer) movimiento.get("articuloId");
            Integer pedidoId   = (Integer) movimiento.get("pedidoId");
            String  codigo     = (String)  movimiento.get("codigo");
            Integer origenId   = (Integer) movimiento.get("origenId");
            Integer destinoId  = (Integer) movimiento.get("destinoId");
            String  comentario = (String)  movimiento.get("comentario");
            BigDecimal  surtir = BigDecimal.valueOf(movimiento.getAsNumber("surtir").doubleValue());

            for(PedidoDetalle detalle : pedido.getDetalles()){
                if(detalle.getArticuloId() == articuloId){
                    BigDecimal cantidadSurtida = detalle.getCantidadSurtida();
                    detalle.setCantidadSurtida( cantidadSurtida.add(surtir) );
                    String comentarioSurtir = "";
                    if(detalle.getComentarioSurtir() != null){
                        comentarioSurtir = detalle.getComentarioSurtir();
                    }
                    if(comentario != null){
                        comentarioSurtir = comentarioSurtir + Character.toString((char) 10) + comentario;
                    }
					detalle.setComentarioSurtir(comentarioSurtir);
                }
            }

            if(comentario == null || comentario.equals("")){
                Localidad origen = localidadDao.findById(origenId);
                Localidad destino = localidadDao.findById(destinoId);

                comentario = "Pedido de: "+destino.getAlmacen().getCodigoAlmacen() +" a "+origen.getAlmacen().getCodigoAlmacen();
            }

            // Se crea una salida del almacén CEDIS
            procesarMovimiento( articuloId,      // articulo
                                origenId,        // localidad origen
                                surtir.negate(), // cantidad
                                comentario,      // razón
                                codigo,          // referencia
                                id,              // referenciaId
                                req,             // request
                                ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.SURTIR_PEDIDO
            );

            // Se crea una entrada al almacén Tránsito
            procesarMovimiento( articuloId,                 // articulo
                                localidadTransito.getId(),  // localidad transito
                                surtir,                     // cantidad
                                comentario,                 // razón
                                codigo,                     // referencia
                                id,                         // referenciaId
                                req,                        // request
                                ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.SURTIR_PEDIDO
            );

        }

        try {
            String response = this.nextEstado(pedido,null,true);
            if(response != null && !response.equals(""))
                throw new Exception(response);
        } catch (Exception e){
            return new JsonResponse("", e.getMessage(), JsonResponse.STATUS_ERROR_PROBLEMA);
        }

        pedido.setModificadoPorId(idUsuario);
        pedidoDao.save(pedido);

        createLogItem(pedido,idUsuario);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    /** RECIBIR PEDIDOS **/
    @RequestMapping(value = "/recieve", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPedidosPorRecibir(ServletRequest req) throws SQLException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<Integer> status = new ArrayList<>();
        status.add(CMM_PED_EstatusPedido.SURTIDO_PARCIAL);
        status.add(CMM_PED_EstatusPedido.SURTIDO);

        List<PedidoListadoRecibirProjection> pedidos = pedidoDao.getPedidosPorRecibir(status, idUsuario);

        HashMap<String, Object> data = new HashMap<>();
        data.put("datos", pedidos);

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/recieve/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPorRecibirFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");
        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        Date dateFechaCreacionDesde = isNullorEmpty(fechaCreacionDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionDesde);
        Date dateFechaCreacionHasta = isNullorEmpty(fechaCreacionHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionHasta);

        List<Integer> status = new ArrayList<>();
        status.add(CMM_PED_EstatusPedido.SURTIDO_PARCIAL);
        status.add(CMM_PED_EstatusPedido.SURTIDO);

        return new JsonResponse(pedidoDao.findAllQueryEstatusBy(dateFechaCreacionDesde, dateFechaCreacionHasta, status), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/recieve/detail", "/recieve/detail/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPorRecibirDetalles(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<LocalidadComboProjection> localidades_todas   = localidadDao.findProjectedComboAllByActivoTrueOrderByCodigoLocalidad();

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("localidades_todas", localidades_todas);

        if (id != null) {
            PedidoEditarProjection pedido = pedidoDao.findPedidoById(id);
            List<LocalidadComboProjection> localidadesAlmacenOrigen = localidadDao.findProjectedComboAllByActivoTrueAndAlmacenId(pedido.getLocalidadOrigen().getAlmacen().getId());

            datos.put("pedido", pedido);
            datos.put("localidadesAlmacenOrigen", localidadesAlmacenOrigen);
            datos.put("existenciaArticulosMapLocalidades", (JSONObject)JSONValue.parse(pedidoDao.getArticulosExistenciasJsonMapLocalidadesByAlmacenId(pedido.getLocalidadOrigen().getAlmacen().getId())));
            datos.put("movimientos", pedidoDao.getMovimientosTodos(0,pedido.getCodigo()));
            datos.put("historial", logController.getHistorial(id, LogProceso.PEDIDOS));
            datos.put("recibos", reciboDao.findAllProjectedByPedidoId(id));
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/recieve", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse saveRecibir(@RequestBody PedidoRecibo recibo, ServletRequest req) throws Exception {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        AlmacenComboProjection almacenTransito = almacenDao.findProjectedComboByActivoTrueAndTipoAlmacenId(ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen.TRANSITO);
        Localidad localidadTransito = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacenTransito.getId());

        Integer actualId = recibo.getPedidoId();
        Pedido pedido = pedidoDao.findById(actualId).get();

        if(recibo.getId() != null){
            recibo.setModificadoPorId(idUsuario);
        }else {
            recibo.setCreadoPorId(idUsuario);
            recibo.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("PRD"));
        }
        Boolean recibirArticulos = false;
        for(PedidoReciboDetalle detalle : recibo.getDetalles()){
            detalle.setEstatusId(CMM_Estatus.ACTIVO);

            String comentario = "pedido : "+pedido.getCodigo();

            // [ Articulos recibidos ]
            for(PedidoReciboDetalleLocalidad detalleLocalidad : detalle.getLocalidades()){
				if(detalleLocalidad.getCantidad().compareTo(new BigDecimal(0)) == 1) {
					String com = (detalle.getComentario() == null || detalle.getComentario().equals("")) ? ("Recibo "+comentario) : detalle.getComentario();
					// Salida: Almacén en tránsito
					procesarMovimiento(detalle.getArticuloId(), localidadTransito.getId(), detalleLocalidad.getCantidad().negate(), com, pedido.getCodigo(), pedido.getId(), req, ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.RECIBIR_PEDIDO);
					// Entrada: Almacen recepción
					procesarMovimiento(detalle.getArticuloId(), detalleLocalidad.getLocalidadId(), detalleLocalidad.getCantidad(), com, pedido.getCodigo(), pedido.getId(), req, ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.RECIBIR_PEDIDO);
                    recibirArticulos = true;
				}
			}

            // [ Articulos devueltos]
            if(detalle.getCantidadDevuelta().compareTo(new BigDecimal(0)) == 1){
                String com = (detalle.getComentario() == null || detalle.getComentario().equals("")) ? ("Devolución "+comentario) : detalle.getComentario();
                // Salida: Almacén en tránsito
                procesarMovimiento( detalle.getArticuloId(), localidadTransito.getId(), detalle.getCantidadDevuelta().negate(), com, pedido.getCodigo(), pedido.getId(), req, ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.DEVOLUCION_PEDIDO);
                // Entrada: Almacen CEDIS
                procesarMovimiento( detalle.getArticuloId(), pedido.getLocalidadCEDISId(), detalle.getCantidadDevuelta(), com, pedido.getCodigo(), pedido.getId(), req, ControlesMaestrosMultiples.CMM_IM_TipoMovimiento.DEVOLUCION_PEDIDO);
            }

            // [ Articulos de spill]
            if(detalle.getSpill().compareTo(new BigDecimal(0)) == 1){
                String com = (detalle.getComentario() == null || detalle.getComentario().equals("")) ? ("Spill "+comentario) : detalle.getComentario();
                // Salida: Almacén en tránsito
                procesarMovimiento( detalle.getArticuloId(), localidadTransito.getId(), detalle.getSpill().negate(), com, pedido.getCodigo(), pedido.getId(), req, ControlesMaestrosMultiples.CMM_IM_TipoSpill.SPILL);
                // Entrada: Almacen CEDIS
                //procesarMovimiento( detalle.getArticuloId(), pedido.getLocalidadCEDISId(), detalle.getSpill(), com, pedido.getCodigo(), pedido.getId(), req, ControlesMaestrosMultiples.CMM_IM_TipoSpill.SPILL);
            }
        }

        recibo = reciboDao.save(recibo);

        try {
            String response = this.nextEstado(pedido,null);
            if(response != null && !response.equals(""))
                throw new Exception(response);
        } catch (Exception e){
            return new JsonResponse("", e.getMessage(), JsonResponse.STATUS_ERROR_PROBLEMA);
        }

        if(pedido.getEstatusId() == CMM_PED_EstatusPedido.COMPLETO){
            this.createLogItem(pedido, idUsuario);
        }else if(recibirArticulos &&  pedido.getEstatusId() == CMM_PED_EstatusPedido.SURTIDO_PARCIAL){
            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.RECIBIDO_PARCIAL,
                            LogProceso.PEDIDOS,
                            pedido.getId()
                    ),
                    idUsuario
            );
            // this.createLogItem(pedido, idUsuario);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    /** REPORTE PEDIDOS **/
    @RequestMapping(value = "/report/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> data = new HashMap<>();
        data.put("localidades", localidadDao.findProjectedComboAllByActivoTrueAndPermiso(idUsuario));
//        data.put("almacenes", almacenDao.findProjectedComboAllByActivoTrueAndPermiso(idUsuario));
        data.put("almacenes", new ArrayList<>());
        data.put("estatus", cmmDao.findAllByControl("CMM_PED_EstatusPedido"));
        data.put("datos", new ArrayList<>());

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/report/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporteFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        String fechaDesde = (String) json.get("fechaDesde");
        String fechaHasta = (String) json.get("fechaHasta");

        Date dateFechaDesde = isNullorEmpty(fechaDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaDesde);
        Date dateFechaHasta = isNullorEmpty(fechaHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaHasta);

        String codigo = (String) json.get("codigo");
        if(codigo != null && codigo.trim().equals("")){
            codigo = null;
        }

        ArrayList<HashMap<String, Integer>> allAlmacenes = (ArrayList<HashMap<String, Integer>>) json.get("almacenes");

        ArrayList<HashMap<String, Integer>> allLocalidades = (ArrayList<HashMap<String, Integer>>) json.get("localidades");

        ArrayList<HashMap<String, Integer>> allEstatus     = (ArrayList<HashMap<String, Integer>>) json.get("estatus");

        ArrayList<Integer> estatus = new ArrayList<>();

        if (allEstatus != null) {
            for (HashMap<String, Integer> status : allEstatus) {
                estatus.add(status.get("id"));
            }
        }

        ArrayList<Integer> almacenes = new ArrayList<>();
        if (allAlmacenes != null) {
            for (HashMap<String, Integer> almacen : allAlmacenes) {
                almacenes.add(almacen.get("id"));
            }
        }

        ArrayList<Integer> localidades = new ArrayList<>();
        if (allLocalidades != null) {
            for (HashMap<String, Integer> localidad : allLocalidades) {
                localidades.add(localidad.get("id"));
            }
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("datos", pedidoDao.findAllQueryReporteBy(dateFechaDesde, dateFechaHasta, codigo, estatus.isEmpty() ? 1 : 0, estatus, almacenes.isEmpty() ? 1 : 0, almacenes, localidades.isEmpty() ? 1 : 0, localidades));

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="report/download/excel", method = RequestMethod.POST)
    public void exportPedidos(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {

        String fechaDesde = (String) json.get("fechaDesde");
        String fechaHasta = (String) json.get("fechaHasta");
        String codigo = (String) json.get("codigo");
        ArrayList<HashMap<String, Integer>> allAlmacenes = (ArrayList<HashMap<String, Integer>>) json.get("almacenes");
        ArrayList<HashMap<String, Integer>> allLocalidades = (ArrayList<HashMap<String, Integer>>) json.get("localidades");
        ArrayList<HashMap<String, Integer>> allEstatus     = (ArrayList<HashMap<String, Integer>>) json.get("estatus");

        HashMap<String, Object> cabecera = new HashMap<>();

        Date fechaDe = new SimpleDateFormat("yyyy-MM-dd").parse((String) json.get("fechaDesde"));
        Date fechaA = new SimpleDateFormat("yyyy-MM-dd").parse((String) json.get("fechaHasta"));

        cabecera.put("Fecha reporte", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        if(codigo == null){
            cabecera.put("Fecha Desde", new SimpleDateFormat("dd/MM/yyyy").format(fechaDe));
            cabecera.put("Fecha Hasta", new SimpleDateFormat("dd/MM/yyyy").format(fechaA));
        }else{
            cabecera.put("Código", new SimpleDateFormat("dd/MM/yyyy").format(fechaA));
        }
        // cabecera.put("Almacenes", json.get("almacenes"));

        // cabecera.put("Artículos", json.get("articulos"));

        ArrayList<String> estatus = new ArrayList<>();
        if (allEstatus != null) {
            for (HashMap<String, Integer> status : allEstatus) {
                estatus.add(status.get("id").toString());
            }
            cabecera.put("Estatus", String.join("|",estatus));
        }


        ArrayList<String> almacenes = new ArrayList<>();
        if (allAlmacenes != null) {
            for (HashMap<String, Integer> almacen : allAlmacenes) {
                almacenes.add(almacen.get("id").toString());
            }
            cabecera.put("Almacenes", String.join("|",almacenes));
        }

        String.join(",",almacenes);

        ArrayList<String> localidades = new ArrayList<>();
        if (allLocalidades != null) {
            for (HashMap<String, Integer> localidad : allLocalidades) {
                localidades.add(localidad.get("id").toString());
            }
            cabecera.put("Localidades", String.join("|",localidades));
        }

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);
        RolMenuPermiso permiso = rolMenuPermisoDao.findByRolIdAndPermisoId(usuario.getRolId(), 2);

        String query = "SELECT " +
                "pedido \"Pedido\", " +
                "fechaPedido \"Fecha Pedido\", " +
                "origen \"Almacén - Localidad\", " +
                "codigoArticulo \"Código artículo\", " +
                "isbn \"ISBN\", " +
                "articulo \"Artículo\", " +
                "um \"UM\", " +
                "cantidadPedido \"Cantidad pedido\", " +
                "cantidadSurtida \"Cantidad surtida\", " +
                "fechaSurtido \"Fecha surtido\", " +
                "transito \"Cantidad transito\", " +
                "cantidadRecibida \"Cantidad recibida\", " +
                "fechaRecibo \"Fecha recibo\", " +
                "cantidadPendiente \"Cantidad pendiente\" ";
        if(permiso != null) {
            query += ", costo \"Costo\", " +
                    "total \"Total\" ";
        }
            query +=
            "FROM VW_REPORTE_PEDIDOS WHERE " +
                (fechaDesde == null ? "" : "(fechaPedido >= CAST ('"+fechaDesde+"' AS DATE) ) AND ") +
                (fechaHasta == null ? "" : "(fechaPedido <= CAST ('"+fechaHasta+"' AS DATE) ) AND ") +
                (codigo == null ? "" : "pedido = '"+codigo+"' AND ") +
                (estatus.isEmpty()? "" : "estatusId IN ("+String.join(",",estatus)+") AND ") +
                (almacenes.isEmpty()? "" : "almacenId IN ("+String.join(",",almacenes)+") AND ") +
                (localidades.isEmpty()? "" : "origenId IN ("+String.join(",",localidades)+") AND ") +
                "1 = 1 "+
                "ORDER BY pedido";

        // String[] alColumnas = new String[]{"Pedido","Fecha Pedido","Almacén - Localidad","Código artículo","Artículo","UM","Cantidad pedido","Cantidad surtida","Fecha surtido","Cantidad recibida","Fecha recibo","Cantidad pendiente","Costo","Total"};
        String[] alColumnas = null;
        if(permiso != null) {
            alColumnas = new String[]{"Pedido","Fecha Pedido","Almacén - Localidad","Código artículo", "ISBN","Artículo","UM","Cantidad pedido","Cantidad surtida","Fecha surtido","Cantidad transito","Cantidad recibida","Fecha recibo","Cantidad pendiente","Costo","Total"};
        }else{
            alColumnas = new String[]{"Pedido","Fecha Pedido","Almacén - Localidad","Código artículo", "ISBN","Artículo","UM","Cantidad pedido","Cantidad surtida","Fecha surtido","Cantidad transito","Cantidad recibida","Fecha recibo","Cantidad pendiente"};
        }

        // excelController.downloadXlsx(response, "pedidos", query, alColumnas, null);
        String[] titulo = new String[]{"pedidos"};
        excelController.downloadDetailedXlsx(response,
                "Pedidos",
                query,
                titulo,
                cabecera,
                null,
                alColumnas,
                null,
                "Pedidos",
                null,
                null,
                null);
    }

    /** CAMBIAR ESTATUS **/
    @RequestMapping(value = "/status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse changeStatus(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer pedidoId = (Integer) json.get("id");

        Pedido pedido = pedidoDao.findById(pedidoId).get();

        String response = "";
        try {
            response = this.nextEstado(pedido,CMM_PED_EstatusPedido.CERRADO);
            if(response != null)
                throw new Exception(response);
        } catch (Exception e){
            return new JsonResponse(null, response, JsonResponse.STATUS_ERROR_PROBLEMA);
        }

        pedido.setModificadoPorId(idUsuario);

        pedido = pedidoDao.save(pedido);
        createLogItem(pedido,idUsuario);

        return new JsonResponse(pedido.getCodigo(), null, JsonResponse.STATUS_OK);
    }

    /** NAVEGAR ESTADOS **/
    private String nextEstado(Pedido actual, Integer estado)throws Exception {
        return nextEstado(actual,estado,false);
    }

    private String nextEstado(Pedido actual, Integer estado, boolean esSurtir)throws Exception {
        if((Integer)actual.getEstatusId() == 0)
            actual.setEstatusId(CMM_PED_EstatusPedido.GUARDADO);
        else{
            Pedido pedido = pedidoDao.findById(actual.getId()).get();
            Integer sucursalId = pedido.getLocalidadOrigen().getAlmacen().getSucursal().getId();
            String sucursalNombre = pedido.getLocalidadOrigen().getAlmacen().getSucursal().getNombre();
            boolean requiereAutorizacion;
            switch(actual.getEstatusId()){
                case CMM_PED_EstatusPedido.GUARDADO:
                    requiereAutorizacion = alertasService.validarAutorizacion(AlertasConfiguraciones.PEDIDOS_VALIDACION, pedido.getId(), pedido.getCodigo(), "Pedidos", sucursalId, pedido.getCreadoPorId(),sucursalNombre);

                    if(requiereAutorizacion) {
                        actual.setEstatusId(CMM_PED_EstatusPedido.POR_AUTORIZAR);
                    }else{
                        actual.setEstatusId(CMM_PED_EstatusPedido.POR_SURTIR);
                    }
                    break;
                case CMM_PED_EstatusPedido.POR_SURTIR:
                case CMM_PED_EstatusPedido.SURTIDO_PARCIAL:
                    requiereAutorizacion = false;
                    if(esSurtir){
                        requiereAutorizacion = alertasService.validarAutorizacion(AlertasConfiguraciones.SURTIR_PEDIDO_VALIDACION, pedido.getId(), pedido.getCodigo(), "Pedidos", sucursalId, pedido.getCreadoPorId(),sucursalNombre);
                    }
                    if(requiereAutorizacion) {
                        actual.setEstatusId(CMM_PED_EstatusPedido.POR_AUTORIZAR);
                        break;
                    }
                    if(estado != null && estado == CMM_PED_EstatusPedido.CERRADO){
                        Boolean puedeCerrar = pedidoDao.canClosePedido(actual.getCodigo());
                        if(puedeCerrar)
                            actual.setEstatusId(CMM_PED_EstatusPedido.CERRADO);
                        else
                            return "No permite cerrar. Artículos pendientes de recibir";
                        break;
                    }else {
                        Boolean esParcial = false;
                        for(PedidoDetalle pedidoDetalle : pedido.getDetalles()){
                            if(pedidoDetalle.getEstatusId() != CMM_Estatus.BORRADO && pedidoDetalle.getCantidadPedida().subtract(pedidoDetalle.getCantidadSurtida()).compareTo(BigDecimal.ZERO) != 0){
                                esParcial = true;
                                break;
                            }
                        }
                        if(esParcial)
                            actual.setEstatusId(CMM_PED_EstatusPedido.SURTIDO_PARCIAL);
                        else
                            actual.setEstatusId(CMM_PED_EstatusPedido.SURTIDO);
                        break;
                    }
                case CMM_PED_EstatusPedido.POR_AUTORIZAR:
                    String alertaEstatus = "AUTORIZADO";
                    if(alertaEstatus.equals("AUTORIZADO"))
                        actual.setEstatusId(CMM_PED_EstatusPedido.POR_SURTIR);
                    else if(alertaEstatus.equals("EN_REVISION"))
                        actual.setEstatusId(CMM_PED_EstatusPedido.EN_REVISION);
                    else if(alertaEstatus.equals("RECHAZADO"))
                        actual.setEstatusId(CMM_PED_EstatusPedido.RECHAZADO);
                    break;
                case CMM_PED_EstatusPedido.EN_REVISION:
                    actual.setEstatusId(CMM_PED_EstatusPedido.POR_AUTORIZAR);
                    break;
                case CMM_PED_EstatusPedido.SURTIDO:
                    actual.setEstatusId(CMM_PED_EstatusPedido.COMPLETO);
                    break;
                case CMM_PED_EstatusPedido.RECHAZADO:
                case CMM_PED_EstatusPedido.CANCELADO:
                case CMM_PED_EstatusPedido.CERRADO:
                case CMM_PED_EstatusPedido.BORRADO:
                    return "FINAL STATE REACHED";
                default:
                    return "INVALID STATE TRANSITION";
            }
        }
        return null;
    }

    private void createLogItem(Pedido pedido, Integer usuarioId){

        Integer logTipo = null;
        switch(pedido.getEstatusId()){
            case CMM_PED_EstatusPedido.GUARDADO:
                    logTipo = LogTipo.PED_GUARDADO;
                break;
            case CMM_PED_EstatusPedido.POR_SURTIR:
                    logTipo = LogTipo.PED_POR_SURTIR;
                break;
            case CMM_PED_EstatusPedido.SURTIDO_PARCIAL:
                    logTipo = LogTipo.PED_SURTIR_PARCIAL;
                break;
            case CMM_PED_EstatusPedido.SURTIDO:
                    logTipo = LogTipo.PED_SURTIR;
                break;
            case CMM_PED_EstatusPedido.CERRADO:
                    logTipo = LogTipo.PED_CERRADO;
                break;
            case CMM_PED_EstatusPedido.BORRADO:
                    logTipo = LogTipo.PED_BORRADO;
                break;
            case CMM_PED_EstatusPedido.CANCELADO:
                logTipo = LogTipo.PED_CANCELADO;
                break;
            case CMM_PED_EstatusPedido.POR_AUTORIZAR:
                logTipo = LogTipo.POR_AUTORIZAR;
                break;
            case CMM_PED_EstatusPedido.COMPLETO:
                logTipo = LogTipo.RECIBIDO;
                break;
        }

        if(logTipo != null){
            logController.insertaLogUsuario(
                    new Log(null,
                            logTipo,
                            LogProceso.PEDIDOS,
                            pedido.getId()
                    ),
                    usuarioId
            );
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void procesarMovimiento(int articuloId, int localidadId, BigDecimal cantidad, String razon, String referencia, int referenciaId, ServletRequest req, Integer tipo) throws Exception {
        InventarioMovimiento movimiento = new InventarioMovimiento();
        movimiento.setArticuloId(articuloId);
        movimiento.setLocalidadId(localidadId);
        movimiento.setCantidad(cantidad);
        movimiento.setRazon(razon);
        movimiento.setReferencia(referencia);
        movimiento.setReferenciaMovimientoId(referenciaId);
        movimiento.setTipoMovimientoId(tipo);

        inventarioMovimientoController.procesarMovimiento(movimiento, req);
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

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/supply/alerta/aprobar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse aprobarAlertaSurtir(@RequestBody Integer id, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        actualizarAlertaSurtir(id,idUsuario,true, null);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/supply/alerta/rechazar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse rechazarAlertaSurtir(@RequestBody HashMap<String,Object> body, ServletRequest req) throws SQLException, ParseException, Exception {
        Integer id = (Integer) body.get("id");
        String comentario = (String) body.get("comentario");
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        actualizarAlertaSurtir(id,idUsuario,false, comentario);
        return new JsonResponse(id, null, JsonResponse.STATUS_OK);
    }

    private void actualizarAlerta(Integer procesoId, Integer usuarioId, Boolean autorizar, String comentario) throws Exception{
        MenuPrincipal nodo = menuDao.findByUrl("/app/inventario/pedidos");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),procesoId,CMM_CALE_EstatusAlerta.EN_PROCESO);
        for ( AlertaDetalle detalle : alerta.getDetalles() ){
            if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                alertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, autorizar,comentario);
            }
        }
    }

    private void actualizarAlertaSurtir(Integer procesoId, Integer usuarioId, Boolean autorizar, String comentario) throws Exception{
        MenuPrincipal nodo = menuDao.findByUrl("/app/inventario/surtir");
        AlertaConfigComboProjection config =  alertaConfigDao.findProjectedByNodoId(nodo.getId());
        Alerta alerta = alertaDao.findByAlertaCIdAndReferenciaProcesoIdAndEstatusAlertaId(config.getId(),procesoId,CMM_CALE_EstatusAlerta.EN_PROCESO);
        for ( AlertaDetalle detalle : alerta.getDetalles() ){
            if ( detalle.getUsuarioId().equals(usuarioId) && detalle.getEstatusDetalleId().equals(CMM_CALE_EstatusAlerta.EN_PROCESO) ){
                alertasService.actualizaEstatusAlerta(detalle.getId(), usuarioId, autorizar,comentario);
            }
        }
    }
}

