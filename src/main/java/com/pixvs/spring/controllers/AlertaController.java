package com.pixvs.spring.controllers;

import com.pixvs.main.models.Usuario;
import com.pixvs.spring.models.*;
import com.pixvs.spring.models.projections.Alerta.AlertaDetalleListProjection;
import com.pixvs.spring.models.projections.Departamento.*;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.*;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.spring.models.projections.AlertaConfig.AlertaConfigComboProjection;
import com.pixvs.spring.models.projections.AlertaConfigEtapa.AlertaConfigEtapaComboProjection;
import com.pixvs.spring.models.projections.AlertaConfigEtapa.AlertaConfigEtapaEditarProjection;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;


@RestController
@RequestMapping("/v1/alerta")
public class AlertaController {

    @Autowired
    private Environment environment;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private DepartamentoDao departamentoDao;

    @Autowired
    private AlertaConfigDao alertaConfigDao;

    @Autowired
    private AlertaConfigEtapaDao alertaConfigEtapaDao;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private AlertaConfigEtapaAprobadorDao aprobadorDao;

    @Autowired
    private NotificacionController notificacionController;

    @Autowired
    private AlertasDao alertasDao;

    @Autowired
    private ProcesadorAlertasService alertasService;

    @Autowired
    private  AlertasDetalleDao alertasDetalleDao;

    @Transactional
    @RequestMapping(value = "/listados", method = RequestMethod.GET)
    public JsonResponse getMenu(ServletRequest req) throws  SQLException {

        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Usuario usuario = usuarioDao.findById(idUsuario);

        JSONObject json = new JSONObject();

        MenuPrincipal raiz = new MenuPrincipal();
        raiz.setId(1);
        List<DepartamentoNodoSelectedProjection> departamentosTree = departamentoDao.findProjectedNodoSelectedAllByActivoTrueAndAutorizaTrueAndDepartamentoPadreIdIsNull();

        json.put("menu", getTree(usuario.getRolId(), ControlesMaestrosMultiples.CMM_SYS_SistemaAcceso.WEB, raiz));
        json.put("tipoOrden",cmmDao.findAllByControl("CMM_ACE_TipoOrden"));
        json.put("tipoAlerta",cmmDao.findAllByControl("CMM_CALRD_TipoAlerta"));
        json.put("tipoAprobacion",cmmDao.findAllByControl("CMM_ACE_TipoAprobacion"));
        json.put("tipoCondicionAprobacion",cmmDao.findAllByControlAndActivoIsTrueOrderByValor("CMM_ACE_TipoCondicionAprobacion"));
        json.put("tipoMonto",cmmDao.findAllByControl("CMM_ACE_TipoMonto"));
        json.put("usuarios", usuarioDao.findProjectedComboAllByEstatusId(1000001));
        json.put("departamentosTree",departamentosTree);
        json.put("criteriosEconomicos",cmmDao.findAllByControl("CMM_ALE_MostrarCriteriosEconomicos"));
        json.put("usuarioNotificacion",cmmDao.findAllByControl("CMM_ALE_MostrarUsuarioNotificacion"));




        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody AlertaConfigEtapa etapa, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        AlertaConfigEtapaNotificadores notificadorCreadorRemover = null;
        for(AlertaConfigEtapaNotificadores notificador : etapa.getNotificadores()){
            if(notificador.getNotificador().getId().equals(0)){
                notificadorCreadorRemover = notificador;
                etapa.setNotificarCreador(true);
                break;
            }
        }
        if(notificadorCreadorRemover != null){
            etapa.getNotificadores().remove(notificadorCreadorRemover);
        }

        if (etapa.getId() == null) {
            etapa.setCreadoPorId(idUsuario);
        } else {
            AlertaConfigEtapa actual = alertaConfigEtapaDao.findById(etapa.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(actual.getFechaModificacion(),etapa.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", actual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            etapa.setModificadoPorId(idUsuario);

        }

        etapa = alertaConfigEtapaDao.save(etapa);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = {"/etapas/{id}","/etapas/{id}/{sucursalId}"}, method = RequestMethod.GET)
    public JsonResponse getEtapas(@PathVariable Integer id, @PathVariable(required = false) Integer sucursalId, ServletRequest req) throws  SQLException {

        List<AlertaConfigEtapaComboProjection> etapas;
        if(sucursalId != null){
            if (environment.getProperty("environments.pixvs.requiere-entidades").equals("true")) {
                etapas = alertaConfigEtapaDao.findAllProjectedByAlertaConfigId(id);
            }else{
            etapas = alertaConfigEtapaDao.findAllProjectedByAlertaConfigIdAndSucursalId(id,sucursalId);}
        }else{
            etapas = alertaConfigEtapaDao.findAllProjectedByAlertaConfigId(id);
        }

        return new JsonResponse(etapas, "Etapas", JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/etapa/{id}", method = RequestMethod.GET)
    public JsonResponse getEtapa(@PathVariable Integer id, ServletRequest req) throws  SQLException {

        AlertaConfigEtapaEditarProjection etapa = alertaConfigEtapaDao.findProjectedById(id);

        return new JsonResponse(etapa, "Etapas", JsonResponse.STATUS_OK);
    }

    private List<JSONObject> getTree(Integer rolId, Integer sistemaId, MenuPrincipal raiz){

        List<JSONObject> lista = new ArrayList<>();
        Integer raizId = -1;

        if (raiz != null) {
            raizId = raiz.getId();
        }

        //Obtener todos los nodos vinculados a la raiz
        List<MenuPrincipal> items = menuDao.findAllProjectedAllByUsuarioIdAndSistemaAccesoIdOrderByOrdenAsc(rolId, sistemaId, raizId);

        for (MenuPrincipal item : items){

            JSONObject nodo = new JSONObject();
            List<JSONObject> children = getTree(rolId,sistemaId,item);
            //Si el nodo es final y no tiene relación a Alertas se omite
            List<AlertaConfigComboProjection> configs = alertaConfigDao.findAllProjectedByNodoId(item.getId());
            if (children.size() > 0 || configs.size() > 0){

                nodo.put("id", item.getId());
                nodo.put("title", item.getTitle());
                nodo.put("padreId", item.getNodoPadreId());
                nodo.put("ocultarAcciones", true);
                nodo.put("ocultarSeleccion", true);
                nodo.put("aplicaSucursales", true);

                if(children.size() > 0) {
                    nodo.put("type", item.getType());
                    nodo.put("children", children);
                }
                else {
                    nodo.put("type", "collapsable");
                    List<JSONObject> nodosConfig = new ArrayList<>();
                    for (AlertaConfigComboProjection config : configs){
                        JSONObject nodoConfig = new JSONObject();
                        nodoConfig.put("id", config.getId());
                        nodoConfig.put("title", config.getNombre());
                        nodoConfig.put("padreId", item.getId());
                        nodoConfig.put("ocultarAcciones", true);
                        nodoConfig.put("ocultarSeleccion", true);
                        nodoConfig.put("aplicaSucursales", true);

                        if(config.getAplicaSucursales()){
                            nodoConfig.put("type", "collapsable");
                            if (environment.getProperty("environments.pixvs.requiere-sucursales").equals("true")) {
                                List<Map<String,Object>> sucursales=null;
                                List<JSONObject> sucursalesJSON = new ArrayList<>();
                                if (environment.getProperty("environments.pixvs.requiere-entidades").equals("true")) {
                                    sucursales = alertaConfigDao.getSucursalesEntidades();
                                }else {
                                    sucursales = alertaConfigDao.getSucursales();
                                }
                                for (Map<String,Object> sucursal : sucursales){
                                    JSONObject sucursalJSON = new JSONObject();
                                    sucursalJSON.put("id", (Integer)sucursal.get("id"));
                                    sucursalJSON.put("title", (String) sucursal.get("nombre"));
                                    sucursalJSON.put("type", "item");
                                    sucursalJSON.put("padreId", item.getId());
                                    sucursalJSON.put("ocultarAcciones", true);
                                    sucursalJSON.put("ocultarSeleccion", true);
                                    sucursalJSON.put("configId", config.getId());
                                    sucursalJSON.put("aplicaSucursales", true);

                                    sucursalesJSON.add(sucursalJSON);
                                }
                                nodoConfig.put("children", sucursalesJSON);
                            }
                        }else{
                                nodoConfig.put("type", "item");
                                nodoConfig.put("configId", config.getId());
                                nodoConfig.put("aplicaSucursales", false);
                        }
                        nodosConfig.add(nodoConfig);
                    }
                    nodo.put("children", nodosConfig);
                }
                lista.add(nodo);
            }
        }
        return lista;
    }

    @RequestMapping(value = "visto/{idDetalle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse actualizaVistoDetalle(@PathVariable Integer idDetalle) throws SQLException {

        alertasDao.actualizaVistoDetalle(idDetalle);

        return new JsonResponse(null, "Alerta Actualizada", "Alertas");
    }

    @RequestMapping(value = "archivado/{idDetalle}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse actualizaArchivadoDetalle(@PathVariable Integer idDetalle) throws SQLException {

        alertasDao.actualizaArchivaDetalle(idDetalle);

        return new JsonResponse(null, "Alerta Actualizada", "Alertas");
    }

    @RequestMapping(value = "/usuario/{idUsuario}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getAlertasByUsuarioId(@PathVariable Integer idUsuario) throws SQLException {

        List<AlertaDetalleListProjection> alertas = alertasDao.findAllAlertasByUsuario(idUsuario);
        List<AlertaDetalleListProjection> notificaciones = alertasDetalleDao.findTop50ByUsuarioIdAndTipoAlertaIdAndVistoOrderByFechaCreacionDesc(idUsuario, 1000171, false);
        Map<String, Object> map = new HashMap<>();
        map.put("alertas", alertas);
        map.put("notificaciones", notificaciones);

        return new JsonResponse(map, "Listado de Alertas", "Alertas");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPedidos(ServletRequest req) throws SQLException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> data = new HashMap<>();
		data.put("sedes", alertaConfigDao.getSucursales());
        data.put("tipos", cmmDao.findAllByControl("CMM_CALRD_TipoAlerta"));
        data.put("usuarios", usuarioDao.findProjectedComboAllByEstatusId(ControlesMaestrosMultiples.CMM_Estatus.ACTIVO));
        data.put("estatus", cmmDao.findAllByControl("CMM_CALE_EstatusAlerta"));
        data.put("alertas", alertasDetalleDao.findProjectedListadoAllByPendientes(idUsuario));
        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException, ParseException {
        HashMap<String, Integer> estatus = (HashMap<String, Integer>) json.get("estatus");
        HashMap<String, Integer> sede    = (HashMap<String, Integer>) json.get("sede");
        HashMap<String, Integer> tipo    = (HashMap<String, Integer>) json.get("tipo");

        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer estatusId = estatus != null ? estatus.get("id") : null;
        Integer sedeId    = sede != null ? sede.get("id") : null;
        Integer tipoId    = tipo != null ? tipo.get("id"): null;

        String fechaCreacionDesde = (String) json.get("fechaDesde");
        String fechaCreacionHasta = (String) json.get("fechaHasta");

        Date dateFechaCreacionDesde = isNullorEmpty(fechaCreacionDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionDesde);
        Date dateFechaCreacionHasta = isNullorEmpty(fechaCreacionHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionHasta);

        return new JsonResponse(alertasDetalleDao.findProjectedListadoAllByFiltros(dateFechaCreacionDesde, dateFechaCreacionHasta, estatusId, usuarioId, sedeId, tipoId), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/estatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody HashMap<String,Object> datos, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer alertaDetalleId = (Integer) datos.get("alertaDetalleId");
        Boolean autorizar = (Boolean) datos.get("autorizar");
        String comentario = (String) datos.get("comentario");
        try{
            alertasService.actualizaEstatusAlerta(alertaDetalleId,idUsuario,autorizar,comentario);
        } catch (Exception e){
            return new JsonResponse("", e.getMessage(), JsonResponse.STATUS_ERROR_PROBLEMA);
        }
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/ver", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse verAlerta(@RequestBody HashMap<String,Object> datos, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Integer alertaDetalleId = (Integer) datos.get("alertaDetalleId");
        AlertaDetalle alertaDetalle = alertasDetalleDao.findById(alertaDetalleId).get();
        alertaDetalle.setVisto(true);
        alertasDetalleDao.save(alertaDetalle);
        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

}
