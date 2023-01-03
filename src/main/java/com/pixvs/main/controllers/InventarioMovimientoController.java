package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.InventarioMovimiento;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_AI_MotivoAjuste;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_IM_TipoMovimiento;
import com.pixvs.main.models.mapeos.ArticulosTipos;
import com.pixvs.main.services.ProcesadorInventariosService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.ControlesMaestrosMultiples.CMM_Estatus;
import com.pixvs.spring.services.AutonumericoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

@RestController
@RequestMapping("/api/v1/inventario-movimiento")
public class InventarioMovimientoController {

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private InventarioMovimientoDao inventarioMovimientoDao;

    @Autowired
    private ProcesadorInventariosService procesadorInventariosService;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private LocalidadArticuloAcumuladoDao localidadArticuloAcumuladoDao;

    @RequestMapping(value = "/last200/{idTipo}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getInventarios(@PathVariable Integer idTipo, ServletRequest req) throws Exception {
        return getInventarios(idTipo,req,false);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse procesarMovimiento(@RequestBody InventarioMovimiento movimiento, ServletRequest req) throws Exception {
        procesadorInventariosService.procesaMovimiento(
                movimiento.getArticulo() != null ? movimiento.getArticulo().getId() : movimiento.getArticuloId(),
                movimiento.getLocalidad() != null ? movimiento.getLocalidad().getId() : movimiento.getLocalidadId(),
                movimiento.getCantidad(),
                movimiento.getRazon(),
                movimiento.getReferencia(),
                movimiento.getReferenciaMovimientoId(),
                movimiento.getPrecioUnitario(),
                movimiento.getTipoMovimientoId(),
                JwtFilter.getUsuarioId((HttpServletRequest) req)
        );

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/ajuste-inventario/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoAjustes(ServletRequest req) throws Exception {
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", getInventarios(CMM_IM_TipoMovimiento.AJUSTE_INVENTARIO, req,true).getData());

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/ajuste-inventario/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getUsuariosFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        String referencia = (String) json.get("referencia");
        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");
        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        Date dateFechaCreacionDesde = isNullorEmpty(fechaCreacionDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionDesde);
        Date dateFechaCreacionHasta = isNullorEmpty(fechaCreacionHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionHasta);

        return new JsonResponse(inventarioMovimientoDao.findAllQueryProjectedBy(dateFechaCreacionDesde, dateFechaCreacionHasta, isNullorEmpty(referencia) ? 1 : 0, referencia), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/ajuste-inventario/detalle", "/ajuste-inventario/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosAjusteDetalle(@PathVariable(required = false) Integer id, ServletRequest req) throws Exception {
		int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> datos = new HashMap<>();

        if (id != null) {
            datos.put("movimiento", inventarioMovimientoDao.findInventarioMovimientoById(id));
        } else {
            datos.put("almacenes", almacenDao.findAllProjectedComboByActivoTrueAndTipoAlmacenIdAndPermisoOrderByCodigoAlmacen(CMM_ALM_TipoAlmacen.NORMAL,idUsuario));
            datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueOrderByCodigoLocalidad());
            datos.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndInventariable(true));
            datos.put("localidaesArticulos", localidadArticuloAcumuladoDao.findLocalidadArticuloAcumuladoByIdIsNotNull());
            datos.put("motivos", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(CMM_AI_MotivoAjuste.NOMBRE));
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/ajuste-inventario/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarAjusteInventario(@RequestBody InventarioMovimiento movimiento, ServletRequest req) throws Exception {
        movimiento.setReferencia(autonumericoService.getSiguienteAutonumericoByPrefijo("AI"));
        procesarMovimiento(movimiento, req);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/ajuste-inventario/download/excel")
    public void downloadXlsxAjuste(HttpServletResponse response) throws Exception {
        String query = "SELECT * from [VW_LISTADO_AJUSTE_INVENTARIO]";
        String[] columnas = new String[]{"Referencia", "Fecha", "Almacén", "Localidad", "Artículo", "UM", "Motivo de ajuste", "Cantidad", "Costo"};

        excelController.downloadXlsx(response, "ajuste-inventario", query, columnas, null,"Ajustes de inventario");
    }

    @RequestMapping(value = "/kardex-articulos/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getKardex(ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", new ArrayList<>());
        datos.put("almacenes", almacenDao.findProjectedComboAllByUsuarioPermisosId(idUsuario));
        datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueAndPermiso(idUsuario));
        datos.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndInventariable(true));
        datos.put("usuarios", usuarioDao.findProjectedComboAllByEstatusId(CMM_Estatus.ACTIVO));
        datos.put("tiposMovimiento", controlMaestroMultipleDao.findAllByControlAndActivoIsTrueOrderByValor(CMM_IM_TipoMovimiento.NOMBRE));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/kardex-articulos/download/excel", method = RequestMethod.POST)
    public void downloadXlsxKardex(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {
        HashMap<String, Object> filtros = getFiltrosKardex(json);

        Object articuloId = filtros.get("articuloId");
        Object fechaCreacionDesde = filtros.get("fechaCreacionDesde");
        Object fechaCreacionHasta = filtros.get("fechaCreacionHasta");
        Object almacenId = filtros.get("almacenId");
        Object localidades = filtros.get("localidades");
        Object referencia = filtros.get("referencia");
        Object tiposMovimiento = filtros.get("tiposMovimiento");
        Object usuarioId = filtros.get("usuarioId");

        String consulta = "SELECT Fecha, Articulo, AlmacenLocalidad, TipoMovimiento, Referencia, Razon, Usuario, " +
                "ExistenciaAnterior, Entrada, Salida, Total, Costo FROM fn_getKardexArticulo(" +
                (articuloId != null ? "'" + articuloId + "'" : "NULL") + ", " +
                (fechaCreacionDesde != null ? "'" + fechaCreacionDesde + "'" : "NULL") + ", " +
                (fechaCreacionHasta != null ? "'" + fechaCreacionHasta + "'" : "NULL") + ", " +
                (almacenId != null ? "'" + almacenId + "'" : "NULL") + ", " +
                (localidades != null ? "'" + localidades + "'" : "NULL") + ", " +
                (referencia != null ? "'" + referencia + "'" : "NULL") + ", " +
                (tiposMovimiento != null ? "'" + tiposMovimiento + "'" : "NULL") + ", " +
                (usuarioId != null ? "'" + usuarioId + "'" : "NULL") + "" +
                ") ORDER BY Fecha ASC";

        String[] columnas = new String[]{
                "Fecha",
                "Artículo",
                "Almacén / Localidad",
                "Tipo Movimiento",
                "Referencia",
                "Razon",
                "Usuario",
                "Existencia Anterior",
                "Entrada",
                "Salida",
                "Total",
                "Costo"
        };

        excelController.downloadXlsx(response, "kardex-articulo", consulta, columnas, null);
    }

    public HashMap<String, Object> getFiltrosKardex(JSONObject json) throws SQLException, ParseException {
        Integer articuloId = json.get("articulo") != null ? ((HashMap<String, Integer>) json.get("articulo")).get("id") : null;

        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");

        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        Integer almacenId = json.get("almacen") != null ? ((HashMap<String, Integer>) json.get("almacen")).get("id") : null;

        ArrayList<HashMap<String, Integer>> allLocalidades = (ArrayList<HashMap<String, Integer>>) json.get("localidades");
        String localidades = null;
        if (allLocalidades != null) {
            localidades = "";
            for (HashMap<String, Integer> registro : allLocalidades) {
                localidades += "|" + registro.get("id") + "|";
            }
        }

        String referencia = (String) json.get("referencia");

        ArrayList<HashMap<String, Integer>> allTiposMovimiento = (ArrayList<HashMap<String, Integer>>) json.get("tiposMovimiento");
        String tiposMovimiento = null;
        if (allTiposMovimiento != null) {
            tiposMovimiento = "";
            for (HashMap<String, Integer> registro : allTiposMovimiento) {
                tiposMovimiento += "|" + registro.get("id") + "|";
            }
        }

        Integer usuarioId = json.get("usuario") != null ? ((HashMap<String, Integer>) json.get("usuario")).get("id") : null;

        HashMap<String, Object> filtros = new HashMap<>();
        filtros.put("articuloId", articuloId);
        filtros.put("fechaCreacionDesde", fechaCreacionDesde);
        filtros.put("fechaCreacionHasta", fechaCreacionHasta);
        filtros.put("almacenId", almacenId);
        filtros.put("localidades", (localidades != null && localidades.length() > 0)? localidades : null);
        filtros.put("referencia", referencia);
        filtros.put("tiposMovimiento", (tiposMovimiento != null && tiposMovimiento.length() > 0)? tiposMovimiento : null);
        filtros.put("usuarioId", usuarioId);

        return filtros;
    }
	
	private JsonResponse getInventarios(Integer idTipo, ServletRequest req, Boolean soloPermisosUsuario) throws Exception {
        if(soloPermisosUsuario != null && soloPermisosUsuario){
            int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
            List<Integer> localidadesIdsPermisos = localidadDao.findIdAllByActivoTrueAndPermiso(idUsuario);
            return new JsonResponse(inventarioMovimientoDao.findFirst200ByTipoMovimientoIdAndLocalidadIdInOrderByFechaCreacionDesc(idTipo,localidadesIdsPermisos), null, JsonResponse.STATUS_OK);
        }else{
            return new JsonResponse(inventarioMovimientoDao.findFirst200ByTipoMovimientoIdOrderByFechaCreacionDesc(idTipo), null, JsonResponse.STATUS_OK);
        }
    }
}
