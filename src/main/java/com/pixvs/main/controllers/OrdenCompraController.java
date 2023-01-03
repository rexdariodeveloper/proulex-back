package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.OrdenCompra;
import com.pixvs.main.models.OrdenCompraDetalle;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.mapeos.ArticulosFamilias;
import com.pixvs.main.models.mapeos.ArticulosTipos;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraEditarProjection;
import com.pixvs.main.models.projections.OrdenCompra.OrdenCompraListadoProjection;
import com.pixvs.main.models.projections.OrdenCompraRecibo.OrdenCompraReciboListadoProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboVistaProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.services.ProcesadorAlertasService;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.util.HashId;
import net.minidev.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
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
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;


/**
 * Created by Angel Daniel Hernández Silva on 11/08/2020.
 */
@RestController
@RequestMapping("/api/v1/ordenes-compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraDao ordenCompraDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private CXPFacturaDao facturaDao;
    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private DepartamentoDao departamentoDao;
    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;
    @Autowired
    private ArticuloDao articuloDao;
    @Autowired
    private UnidadMedidaDao unidadMedidaDao;
    @Autowired
    private OrdenCompraReciboDao ordenCompraReciboDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
	private AutonumericoService autonumericoService;
    @Autowired
    private SATService satService;

	@Autowired
	private LogController logController;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ProcesadorAlertasService alertasService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getOrdenesCompra() throws SQLException {

        List<OrdenCompraListadoProjection> ordenesCompra = ordenCompraDao.findProjectedListadoAllBy();

        return new JsonResponse(ordenesCompra, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getOrdenesCompraFiltrados(@RequestBody HashMap<String,Object> body){

        HashMap<String,Object> eventBuscadorAmazon = (HashMap<String,Object>)body.get("eventBuscadorAmazon");
        String textoBuscar = (String) eventBuscadorAmazon.get("textoBuscar");

        String codigo = null;
        String proveedor = null;
        String rfc = null;
        String fechaOC = null;
        String fechaUltimaModificacion = null;
        String estatus = null;

        switch ((Integer)eventBuscadorAmazon.get("id")){
            case BuscadorAmazonOpciones.Todo:
                codigo = textoBuscar;
                proveedor = textoBuscar;
                rfc = textoBuscar;
                fechaOC = textoBuscar;
                fechaUltimaModificacion = textoBuscar;
                estatus = textoBuscar;
                break;
            case BuscadorAmazonOpciones.Codigo:
                codigo = textoBuscar;
                break;
            case BuscadorAmazonOpciones.Proveedor:
                proveedor = textoBuscar;
                break;
            case BuscadorAmazonOpciones.RFC:
                rfc = textoBuscar;
                break;
            case BuscadorAmazonOpciones.FechaOC:
                fechaOC = textoBuscar;
                break;
            case BuscadorAmazonOpciones.FechaUltimaModificacion:
                fechaUltimaModificacion = textoBuscar;
                break;
            case BuscadorAmazonOpciones.Estatus:
                estatus = textoBuscar;
                break;
        }

        List<OrdenCompraListadoProjection> ordenesCompra = ordenCompraDao.findProjectedListadoAllByFiltrado(codigo,proveedor,rfc,fechaOC,fechaUltimaModificacion,estatus);

        return new JsonResponse(ordenesCompra,null,JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody OrdenCompra ordenCompra, ServletRequest req) throws Exception {

		int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
		boolean esNuevo = ordenCompra.getId() == null;

        if(ordenCompra.getProveedor() != null){
            ordenCompra.setProveedorId(ordenCompra.getProveedor().getId());
        }else{
            ordenCompra.setProveedorId(null);
        }

        if(ordenCompra.getRecepcionArticulosAlmacen() != null){
            ordenCompra.setRecepcionArticulosAlmacenId(ordenCompra.getRecepcionArticulosAlmacen().getId());
        }else{
            ordenCompra.setRecepcionArticulosAlmacenId(null);
        }

        if(ordenCompra.getMoneda() != null){
            ordenCompra.setMonedaId(ordenCompra.getMoneda().getId());
        }else{
            ordenCompra.setMonedaId(null);
        }

        if(ordenCompra.getDepartamento() != null){
            ordenCompra.setDepartamentoId(ordenCompra.getDepartamento().getId());
        }else{
            ordenCompra.setDepartamentoId(null);
        }

        if(ordenCompra.getDescuento() != null){
            ordenCompra.setDescuento(ordenCompra.getDescuento().divide(new BigDecimal(100)));
        }

        for(OrdenCompraDetalle detalle : ordenCompra.getDetalles()){
            if(detalle.getUnidadMedida() != null){
                detalle.setUnidadMedidaId(detalle.getUnidadMedida().getId());
            }else{
                detalle.setUnidadMedidaId(null);
            }

            if(detalle.getArticulo() != null){
                detalle.setArticuloId(detalle.getArticulo().getId());

                if(detalle.getUnidadMedida() != null){
                    Articulo articulo = articuloDao.findById(detalle.getArticulo().getId());
                    if(articulo.getUnidadMedidaConversionComprasId() != null && detalle.getUnidadMedida().getId().intValue() == articulo.getUnidadMedidaConversionComprasId().intValue()){
                        detalle.setFactorConversion(articulo.getFactorConversionCompras());
                    }else{
                        detalle.setFactorConversion(BigDecimal.ONE);
                    }
                }
            }else{
                detalle.setArticuloId(null);
            }

            if(detalle.getIva() != null){
                detalle.setIva(detalle.getIva().divide(new BigDecimal(100)));
            }

            if(detalle.getIeps() != null){
                detalle.setIeps(detalle.getIeps().divide(new BigDecimal(100)));
            }
        }

        if (ordenCompra.getId() == null) {
			Proveedor proveedor = proveedorDao.findById(ordenCompra.getProveedorId());
            if(satService.proveedorEnListaNegra(proveedor.getRfc())){
                return new JsonResponse(null, "Proveedor en lista negra", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
			}

            ordenCompra.setCreadoPorId(idUsuario);
            ordenCompra.setEstatusId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OC_EstatusOC.EN_EDICION);
            ordenCompra.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("OC"));
        } else {
            OrdenCompra objetoActual = ordenCompraDao.findById(ordenCompra.getId().intValue());
			try{
				concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),ordenCompra.getFechaModificacion());
			}catch (Exception e){
				return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
			}
            ordenCompra.setModificadoPorId(idUsuario);
			ordenCompra.setEstatusId(objetoActual.getEstatusId());
			HashMap<Integer,OrdenCompraDetalle> detallesMap = new HashMap<>();
            for(OrdenCompraDetalle detalle : objetoActual.getDetalles()){
                detallesMap.put(detalle.getId(),detalle);
            }
            for(OrdenCompraDetalle detalle : ordenCompra.getDetalles()){
                if(detalle.getId() != null){
                    detalle.setCuentaCompras(detallesMap.get(detalle.getId()).getCuentaCompras());
                    if(detalle.getCuentaCompras() != null){
                        Articulo articulo = articuloDao.findById(detalle.getArticuloId());
                        articulo.setCuentaCompras(detalle.getCuentaCompras());
                        articuloDao.save(articulo);
                    }
                }
			}
        }

		ordenCompra = ordenCompraDao.save(ordenCompra);

		if(esNuevo){
			logController.insertaLogUsuario(
					new Log(null,
							LogTipo.CREADO,
							LogProceso.ORDENES_COMPRA,
							ordenCompra.getId()
					),
					idUsuario
			);
		}else{
			logController.insertaLogUsuario(
					new Log(null,
							LogTipo.MODIFICADO,
							LogProceso.ORDENES_COMPRA,
							ordenCompra.getId()
					),
					idUsuario
			);
		}

        return new JsonResponse(ordenCompra.getId(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/enviar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse enviar(@RequestBody OrdenCompra ordenCompra, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        boolean esNuevo = ordenCompra.getId() == null;
        JsonResponse jsonResponse = guardar(ordenCompra,req);
        if(jsonResponse.getStatus() != JsonResponse.STATUS_OK){
            return jsonResponse;
        }

        ordenCompra = ordenCompraDao.findById(ordenCompra.getId());

        ordenCompra.setEstatusId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OC_EstatusOC.ABIERTA);
        if(!esNuevo){
            ordenCompra.setModificadoPorId(idUsuario);
            ordenCompra.setFechaModificacion(new Date(System.currentTimeMillis()));
        }
        ordenCompra.setAutorizadoPorId(idUsuario);

		ordenCompraDao.save(ordenCompra);

		logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.ENVIADO,
                        LogProceso.ORDENES_COMPRA,
                        ordenCompra.getId()
                ),
                idUsuario
		);

		/* aqui se inserta la alerta Sin integrar porque no encuentro de donde se obtiene la sucursal */
        if (environment.getProperty("environments.pixvs.alertas-notificaciones").equals("true")) {
            Integer sucursalId = ordenCompra.getRecepcionArticulosAlmacen().getSucursalId();
            try {
//                alertasService.crearAlerta("Órdenes de Compra", ordenCompra.getId(), ordenCompra.getCodigo(), "Órden de compra", sucursalId, idUsuario);
            } catch (Exception e) {
                return new JsonResponse(null, e.getMessage(), JsonResponse.STATUS_OK);
            }
        }
        return new JsonResponse(ordenCompra.getId(), null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = "/{idOrdenCompra}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getById(@PathVariable Integer idOrdenCompra) throws SQLException {

        OrdenCompraEditarProjection ordenCompra = ordenCompraDao.findProjectedEditarById(idOrdenCompra);

        return new JsonResponse(ordenCompra, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{idOrdenCompra}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String idOrdenCompra, ServletRequest req) throws SQLException {

		int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        OrdenCompra ordenCompra = ordenCompraDao.findById(hashId.decode(idOrdenCompra));

        ordenCompra.setEstatusId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OC_EstatusOC.BORRADA);

		ordenCompraDao.save(ordenCompra);

		logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.BORRADO,
                        LogProceso.ORDENES_COMPRA,
                        ordenCompra.getId()
                ),
                idUsuario
		);

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }


    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoOrdenesCompraById(@PathVariable(required = false) Integer id) throws SQLException {

		OrdenCompraEditarProjection ordenCompra = null;
		List<Log> historial = null;
        List<OrdenCompraReciboListadoProjection> movimientos = new ArrayList<>();
        if (id != null) {
			ordenCompra = ordenCompraDao.findProjectedEditarById(id);
			historial = logController.getHistorial(id, LogProceso.ORDENES_COMPRA);
            movimientos = ordenCompraReciboDao.findProjectedListadoAllByOrdenCompraIdOrderByIdAsc(ordenCompra.getId());
        }

        List<ProveedorComboVistaProjection> proveedores = proveedorDao.findProjectedComboVistaAllByActivoTrue();
        List<AlmacenComboProjection> almacenes = almacenDao.findProjectedComboAllByActivoTrue();
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();
        List<DepartamentoComboProjection> departamentos = departamentoDao.findProjectedComboAllByActivoTrue();
        List<ControlMaestroMultipleComboProjection> estatus = controlMaestroMultipleDao.findAllByControl(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OC_EstatusOC.NOMBRE);

        List<ArticuloComboProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloTipoIdAndTipoArticuloIdNot(ControlesMaestrosMultiples.CMM_ARTT_TipoArticulo.COMPRADO,ArticulosTipos.MISCELANEO);
        List<ArticuloComboProjection> articulosMiscelaneos = articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloTipoIdAndTipoArticuloId(ControlesMaestrosMultiples.CMM_ARTT_TipoArticulo.COMPRADO,ArticulosTipos.MISCELANEO);
        List<UnidadMedidaComboProjection> unidadesMedida = unidadMedidaDao.findProjectedComboAllByActivoTrue();

        HashMap<String, Object> json = new HashMap<>();

        json.put("ordenCompra", ordenCompra);
        json.put("proveedores", proveedores);
        json.put("almacenes", almacenes);
        json.put("monedas", monedas);
        json.put("departamentos", departamentos);
        json.put("estatus", estatus);

        json.put("articulos", articulos);
        json.put("articulosMiscelaneos", articulosMiscelaneos);
		json.put("unidadesMedida", unidadesMedida);

		json.put("historial", historial);

        json.put("movimientos", movimientos);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsx(HttpServletResponse response) throws IOException {

        String query = "SELECT * from [VW_LISTADO_ORDENES_COMPRA]";
        String[] alColumnas = new String[]{"Código", "Fecha O C", "Fecha O C", "Estatus Id"};

        excelController.downloadXlsx(response, "ordenesCompra", query, alColumnas, null);
    }

    @RequestMapping(value = "/articulos/save/misc", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarMiscelaneo(@RequestBody Articulo articuloMiscelaneo, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Articulo nuevoMiscelaneo = new Articulo();
        nuevoMiscelaneo.setTipoArticuloId(ArticulosTipos.MISCELANEO);
        nuevoMiscelaneo.setCodigoArticulo(autonumericoService.getSiguienteAutonumericoByPrefijo("MSC"));
        nuevoMiscelaneo.setNombreArticulo(articuloMiscelaneo.getNombreArticulo());
        nuevoMiscelaneo.setDescripcionCorta(articuloMiscelaneo.getNombreArticulo());
        nuevoMiscelaneo.setPermitirCambioAlmacen(false);
        nuevoMiscelaneo.setPlaneacionTemporadas(false);
        nuevoMiscelaneo.setFamiliaId(ArticulosFamilias.MISCELANEO);
        nuevoMiscelaneo.setUnidadMedidaInventarioId(articuloMiscelaneo.getUnidadMedidaInventario().getId());
        nuevoMiscelaneo.setTipoCostoId(com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ART_TipoCosto.ULTIMO);
        if(articuloMiscelaneo.getCostoUltimo() != null){
            nuevoMiscelaneo.setCostoUltimo(articuloMiscelaneo.getCostoUltimo());
            nuevoMiscelaneo.setCostoPromedio(articuloMiscelaneo.getCostoUltimo());
            nuevoMiscelaneo.setCostoEstandar(articuloMiscelaneo.getCostoUltimo());
        }else{
            nuevoMiscelaneo.setCostoUltimo(BigDecimal.ZERO);
            nuevoMiscelaneo.setCostoPromedio(BigDecimal.ZERO);
            nuevoMiscelaneo.setCostoEstandar(BigDecimal.ZERO);
        }
        nuevoMiscelaneo.setActivo(true);
        nuevoMiscelaneo.setCreadoPorId(idUsuario);

        Articulo miscelaneoGuardado = articuloDao.save(nuevoMiscelaneo);

        return new JsonResponse(miscelaneoGuardado.getId(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/articulos/{articuloId}/um", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getOrdenesCompra(@PathVariable Integer articuloId) throws SQLException {

        Articulo articulo = articuloDao.findById(articuloId);

        List<Integer> umIds = new ArrayList<>();
        umIds.add(articulo.getUnidadMedidaInventarioId());
        if(articulo.getUnidadMedidaConversionComprasId() != null){
            umIds.add(articulo.getUnidadMedidaConversionComprasId());
        }

        List<UnidadMedidaComboProjection> unidadesMedida = unidadMedidaDao.findProjectedComboAllByActivoTrueAndIdIn(umIds);

        return new JsonResponse(unidadesMedida, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/reporte", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltros(){

        List<ProveedorComboVistaProjection> proveedores = proveedorDao.findProjectedComboVistaAllBy();
        List<ArticuloComboProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloTipoIdAndTipoArticuloIdNot(ControlesMaestrosMultiples.CMM_ARTT_TipoArticulo.COMPRADO,ArticulosTipos.MISCELANEO);
        List<ControlMaestroMultipleComboProjection> estatus = controlMaestroMultipleDao.findAllByControl("CMM_OC_EstatusOC");
        List<AlmacenComboProjection> almacenes = almacenDao.findProjectedComboAllByActivoTrue();
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();

        HashMap<String,Object> data = new HashMap<>();
        data.put("proveedores",proveedores);
        data.put("articulos",articulos);
        data.put("estatus",estatus);
        data.put("almacenes",almacenes);
        data.put("monedas",monedas);

        return new JsonResponse(data,"",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/reporte/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(@RequestBody HashMap<String,Object> body) throws SQLException, ParseException {

        List<HashMap<String, Object> > proveedores    = (ArrayList)body.get("proveedores");
        String codigo                   = (String)body.get("codigo");
        List<HashMap<String, Object> > articulos      = (ArrayList)body.get("articulos");
        List<HashMap<String, Object> > almacenes      = (ArrayList)body.get("almacenes");
        String fechaDesde = (String) body.get("fechaDesde");
        String fechaHasta = (String) body.get("fechaHasta");
        List<HashMap<String, Object> > estatus        = (ArrayList)body.get("estatus");
        List<HashMap<String, Object> > monedas        = (ArrayList)body.get("monedas");

        List<Integer> proveedoresIds    = new ArrayList<>();
        List<Integer> articulosIds      = new ArrayList<>();
        List<Integer> almacenesIds      = new ArrayList<>();
        List<Integer> estatusIds        = new ArrayList<>();
        List<Integer> monedasIds        = new ArrayList<>();

        Date dateFechaDesde = isNullorEmpty(fechaDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaDesde);
        Date dateFechaHasta = isNullorEmpty(fechaHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaHasta);

        if(proveedores != null){
            for (HashMap<String, Object> proveedor : proveedores){
                proveedoresIds.add((Integer)proveedor.get("id"));
            }
        }

        if(codigo != null && codigo.equals(""))
            codigo = null;

        if(articulos != null){
            for (HashMap<String, Object> articulo : articulos){
                articulosIds.add((Integer)articulo.get("id"));
            }
        }

        if(almacenes != null){
            for (HashMap<String, Object> almacen : almacenes){
                almacenesIds.add((Integer)almacen.get("id"));
            }
        }

        if(estatus != null){
            for (HashMap<String, Object> item : estatus){
                estatusIds.add((Integer)item.get("id"));
            }
        }

        if(monedas != null){
            for (HashMap<String, Object> moneda : monedas){
                monedasIds.add((Integer)moneda.get("id"));
            }
        }

        List<Map<String, Object> > data = ordenCompraDao.getReporte(proveedoresIds.size()>0? 0 : 1, proveedoresIds,
                                                                    codigo,
                                                                    articulosIds.size()>0? 0 : 1, articulosIds,
                                                                    almacenesIds.size()>0? 0 : 1, almacenesIds,
                                                                    dateFechaDesde, dateFechaHasta,
                                                                    estatusIds.size()>0? 0: 1, estatusIds,
                                                                    monedasIds.size()>0? 0: 1, monedasIds);

        return new JsonResponse(data,"",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value="reporte/download/excel", method = RequestMethod.POST)
    public void exportPedidos(@RequestBody JSONObject body, HttpServletResponse response) throws IOException {

        List<HashMap<String, Object> > proveedores    = (ArrayList)body.get("proveedores");
        String codigo                   = (String)body.get("codigo");
        List<HashMap<String, Object> > articulos      = (ArrayList)body.get("articulos");
        List<HashMap<String, Object> > almacenes      = (ArrayList)body.get("almacenes");
        String fechaDesde = (String) body.get("fechaDesde");
        String fechaHasta = (String) body.get("fechaHasta");
        List<HashMap<String, Object> > estatus        = (ArrayList)body.get("estatus");
        List<JSONObject> monedas        = (ArrayList)body.get("monedas");

        List<String> proveedoresIds    = new ArrayList<>();
        List<String> articulosIds      = new ArrayList<>();
        List<String> almacenesIds      = new ArrayList<>();
        List<String> estatusIds        = new ArrayList<>();
        List<String> monedasIds        = new ArrayList<>();

        if(proveedores != null){
            for (HashMap<String, Object> proveedor : proveedores){
                proveedoresIds.add((String)proveedor.get("id").toString());
            }
        }

        if(codigo != null && codigo.equals(""))
            codigo = null;

        if(articulos != null){
            for (HashMap<String, Object> articulo : articulos){
                articulosIds.add((String)articulo.get("id").toString());
            }
        }

        if(almacenes != null){
            for (HashMap<String, Object> almacen : almacenes){
                almacenesIds.add((String)almacen.get("id").toString());
            }
        }

        if(estatus != null){
            for (HashMap<String, Object> item : estatus){
                estatusIds.add((String)item.get("id").toString());
            }
        }

        if(monedas != null){
            for (HashMap<String, Object> moneda : monedas){
                monedasIds.add((String)moneda.get("id").toString());
            }
        }

        String query = "SELECT " +
                "codigo \"Código de OC\", almacen \"Almacén\", fechaOC \"Fecha de OC\", fechaReq \"Fecha entrega\", " +
                "estatus \"Estatus de OC\", proveedor \"Proveedor\", art_cod \"Código de artículo\", articulo \"Artículo\", " +
                "um \"UM\", cantidad \"Cantidad\", precio \"Precio\", subtotal \"Subtotal\", iva \"IVA\", ieps \"IEPS\", " +
                "descuento \"Descuento\", total \"Total\" " +
                "FROM VW_REPORTE_ORDENES_COMPRA WHERE " +
                (proveedoresIds.isEmpty()? "" : "PRO_ProveedorId IN ("+String.join(",",proveedoresIds)+") AND ") +
                (codigo == null ? "" : "OC_Codigo = "+codigo+" AND ") +
                (articulosIds.isEmpty()? "" : "ART_ArticuloId IN ("+String.join(",",articulosIds)+") AND ") +
                (almacenesIds.isEmpty()? "" : "ALM_AlmacenId IN ("+String.join(",",almacenesIds)+") AND ") +
                (fechaDesde == null ? "" : "(OC_FechaOC >= CAST ('"+fechaDesde+"' AS DATE) ) AND ") +
                (fechaHasta == null ? "" : "(OC_FechaOC <= CAST ('"+fechaHasta+"' AS DATE) ) AND ") +
                (proveedoresIds.isEmpty()? "" : "CMM_ControlId IN ("+String.join(",",estatusIds)+") AND ") +
                (monedasIds.isEmpty()? "" : "OC_MON_MonedaId IN ("+String.join(",",monedasIds)+") AND ") +
                "1 = 1 "+
                "ORDER BY OC_Codigo";

        String[] alColumnas = new String[]{"Código de OC", "Almacén", "Fecha de OC", "Fecha entrega", "Estatus de OC", "Proveedor", "Código de artículo", "Artículo", "UM", "Cantidad", "Precio", "Subtotal", "IVA", "IEPS", "Descuento", "Total"};

        excelController.downloadXlsx(response, "ordenes", query, alColumnas, null, "Ordenes de compra");
    }

    @RequestMapping(value = "/historial", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltrosHistorial(){

        List<ProveedorComboVistaProjection> proveedores = proveedorDao.findProjectedComboVistaAllBy();
        List<ArticuloComboProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloTipoIdAndTipoArticuloIdNot(ControlesMaestrosMultiples.CMM_ARTT_TipoArticulo.COMPRADO,ArticulosTipos.MISCELANEO);
        List<ControlMaestroMultipleComboProjection> estatus = controlMaestroMultipleDao.findAllByControl("CMM_OC_EstatusOC");
        List<AlmacenComboProjection> almacenes = almacenDao.findProjectedComboAllByActivoTrue();
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();

        HashMap<String,Object> data = new HashMap<>();
        data.put("proveedores",proveedores);
        data.put("articulos",articulos);
        data.put("estatus",estatus);
        data.put("almacenes",almacenes);
        data.put("monedas",monedas);

        return new JsonResponse(data,"",JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/historial/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getHistorial(@RequestBody HashMap<String,Object> body) throws SQLException, ParseException {

        List<HashMap<String, Object> > proveedores    = (ArrayList)body.get("proveedores");
        String codigo                   = (String)body.get("codigo");
        List<HashMap<String, Object> > articulos      = (ArrayList)body.get("articulos");
        List<HashMap<String, Object> > almacenes      = (ArrayList)body.get("almacenes");
        String fechaDesde = (String) body.get("fechaDesde");
        String fechaHasta = (String) body.get("fechaHasta");
        List<HashMap<String, Object> > estatus        = (ArrayList)body.get("estatus");
        HashMap<String, Object> monedas = (HashMap<String, Object>)body.get("monedas");

        List<Integer> proveedoresIds    = new ArrayList<>();
        List<Integer> articulosIds      = new ArrayList<>();
        List<Integer> almacenesIds      = new ArrayList<>();
        List<Integer> estatusIds        = new ArrayList<>();
        List<Integer> monedasIds        = new ArrayList<>();

        Date dateFechaDesde = isNullorEmpty(fechaDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaDesde);
        Date dateFechaHasta = isNullorEmpty(fechaHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaHasta);

        if(proveedores != null){
            for (HashMap<String, Object> proveedor : proveedores){
                proveedoresIds.add((Integer)proveedor.get("id"));
            }
        }

        if(codigo != null && codigo.equals(""))
            codigo = null;

        if(articulos != null){
            for (HashMap<String, Object> articulo : articulos){
                articulosIds.add((Integer)articulo.get("id"));
            }
        }

        if(almacenes != null){
            for (HashMap<String, Object> almacen : almacenes){
                almacenesIds.add((Integer)almacen.get("id"));
            }
        }

        if(estatus != null){
            for (HashMap<String, Object> item : estatus){
                estatusIds.add((Integer)item.get("id"));
            }
        }

        if(monedas != null){
            monedasIds.add((Integer)monedas.get("id"));
        }

        List<Map<String, Object> > data = ordenCompraDao.getHistorial(proveedoresIds.size()>0? 0 : 1, proveedoresIds,
                codigo,
                articulosIds.size()>0? 0 : 1, articulosIds,
                almacenesIds.size()>0? 0 : 1, almacenesIds,
                dateFechaDesde, dateFechaHasta,
                estatusIds.size()>0? 0: 1, estatusIds,
                monedasIds.size()>0? 0: 1, monedasIds);

        if(data.size() > 0)
            return new JsonResponse(data,"",JsonResponse.STATUS_OK);
        else{
            data = new ArrayList<>();
            return new JsonResponse(data,"",JsonResponse.STATUS_OK);
        }
    }

    @RequestMapping(value="historial/download/excel", method = RequestMethod.POST)
    public void exportHistorial(@RequestBody JSONObject body, HttpServletResponse response) throws IOException {

        List<HashMap<String, Object> > proveedores    = (ArrayList)body.get("proveedores");
        String codigo                   = (String)body.get("codigo");
        List<HashMap<String, Object> > articulos      = (ArrayList)body.get("articulos");
        List<HashMap<String, Object> > almacenes      = (ArrayList)body.get("almacenes");
        String fechaDesde = (String) body.get("fechaDesde");
        String fechaHasta = (String) body.get("fechaHasta");
        List<HashMap<String, Object> > estatus        = (ArrayList)body.get("estatus");
        HashMap<String, Object> monedas = (HashMap<String, Object>)body.get("monedas");

        List<String> proveedoresIds    = new ArrayList<>();
        List<String> articulosIds      = new ArrayList<>();
        List<String> almacenesIds      = new ArrayList<>();
        List<String> estatusIds        = new ArrayList<>();
        List<String> monedasIds        = new ArrayList<>();

        if(proveedores != null){
            for (HashMap<String, Object> proveedor : proveedores){
                proveedoresIds.add((String)proveedor.get("id").toString());
            }
        }

        if(codigo != null && codigo.equals(""))
            codigo = null;

        if(articulos != null){
            for (HashMap<String, Object> articulo : articulos){
                articulosIds.add((String)articulo.get("id").toString());
            }
        }

        if(almacenes != null){
            for (HashMap<String, Object> almacen : almacenes){
                almacenesIds.add((String)almacen.get("id").toString());
            }
        }

        if(estatus != null){
            for (HashMap<String, Object> item : estatus){
                estatusIds.add((String)item.get("id").toString());
            }
        }

        if(monedas != null){
            monedasIds.add((String)monedas.get("id").toString());
        }

        String query = "SELECT " +
                "codigoProveedor CODIGO_PROVEEDOR,\n" +
                "nombreProveedor PROVEEDOR,\n" +
                "comercialProveedor NOMBRE_COMERCIAL,\n" +
                "codigoOC OC,\n" +
                "fechaOC FECHA_OC,\n" +
                "estatusOC ESTATUS_OC,\n" +
                "estatusPartida ESTATUS_PARTIDA,\n" +
                "umOC UM_OC,\n" +
                "tipoArticulo TIPO,\n" +
                "codigoArticulo CODIGO_ARTICULO,\n" +
                "nombreArticulo ARTICULO,\n" +
                "porcentajeIVA '%IVA',\n" +
                "cantidadRequerida CANTIDAD_REQUERIDA,\n" +
                "factorConversion FACTOR_CONVERSION,\n" +
                "precioOC PRECIO_OC,\n" +
                "subtotal SUBTOTAL,\n" +
                "montoIVA IVA,\n" +
                "montoIEPS IEPS,\n" +
                "porcentajeIEPS '%IEPS',\n" +
                "descuento DESCUENTO,\n" +
                "total TOTAL,\n" +
                "fechaRecibo FECHA_RECIBO,\n" +
                "costoPromedio COSTO_PROMEDIO,\n" +
                "umOCR UM,\n" +
                "cantidadRecibida CANTIDAD_RECIBIDA,\n" +
                "cantidadPendiente CANTIDAD_PENDIENTE,\n" +
                "facturaId FACTURA,\n" +
                "fechaFactura FECHA_FACTURA,\n" +
                "um2 UM_FACTURA,\n" +
                "precioFactura PRECIO_FACTURA,\n" +
                "porcentajeIVA2 '%IVA_FACTURA',\n" +
                "montoIVA2 IVA_FACTURA,\n" +
                "porcentajeIEPS2 '%IEPS_FACTURA',\n" +
                "montoIEPS2 IEPS_FACTURA,\n" +
                "total2 TOTAL_FACTURA " +
                "FROM VW_REPORTE_HISTORIAL_COMPRA WHERE " +
                (proveedoresIds.isEmpty()? "" : "PRO_ProveedorId IN ("+String.join(",",proveedoresIds)+") AND ") +
                (codigo == null ? "" : "OC_Codigo = "+codigo+" AND ") +
                (articulosIds.isEmpty()? "" : "ART_ArticuloId IN ("+String.join(",",articulosIds)+") AND ") +
                (almacenesIds.isEmpty()? "" : "ALM_AlmacenId IN ("+String.join(",",almacenesIds)+") AND ") +
                (fechaDesde == null ? "" : "(OC_FechaOC >= CAST ('"+fechaDesde+"' AS DATE) ) AND ") +
                (fechaHasta == null ? "" : "(OC_FechaOC <= CAST ('"+fechaHasta+"' AS DATE) ) AND ") +
                (estatusIds.isEmpty()? "" : "estatusId IN ("+String.join(",",estatusIds)+") AND ") +
                (monedasIds.isEmpty()? "" : "OC_MON_MonedaId IN ("+String.join(",",monedasIds)+") AND ") +
                "1 = 1 "+
                "ORDER BY OC_Codigo";

        String[] alColumnas = new String[]{"CODIGO_PROVEEDOR","PROVEEDOR","NOMBRE_COMERCIAL","OC","FECHA_OC","ESTATUS_OC","ESTATUS_PARTIDA",
                "UM_OC","TIPO","CODIGO_ARTICULO","ARTICULO","%IVA","CANTIDAD_REQUERIDA","FACTOR_CONVERSION","PRECIO_OC",
                "SUBTOTAL","IVA","IEPS","%IEPS","DESCUENTO","TOTAL","FECHA_RECIBO","COSTO_PROMEDIO","UM","CANTIDAD_RECIBIDA",
                "CANTIDAD_PENDIENTE","FACTURA","FECHA_FACTURA","UM_FACTURA","PRECIO_FACTURA","%IVA_FACTURA","IVA_FACTURA",
                "%IEPS_FACTURA","IEPS_FACTURA","TOTAL_FACTURA"};

        excelController.downloadXlsx(response, "historial", query, alColumnas, null);
    }

    @RequestMapping(value = "/estado-cuenta", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEstadoCuenta() {
        List<Integer> estatus = Arrays.asList(ControlesMaestrosMultiples.CMM_CXPF_EstatusFactura.ABIERTA);

        HashMap<String, Object> data = new HashMap<>();

        data.put("proveedores", proveedorDao.findProjectedComboVistaAllBy());
        data.put("facturas", facturaDao.findProjectedComboAllByEstatusIdIn(estatus));

        return new JsonResponse(data, "", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/estado-cuenta/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getEstadoCuenta(@RequestBody HashMap<String, Object> body) throws Exception {
        HashMap<String, Object> proveedor = (HashMap<String, Object>) body.get("proveedores");
        List<HashMap<String, Object>> facturas = (ArrayList) body.get("facturas");
        HashMap<String, Object> estatus = (HashMap<String, Object>) body.get("estatus");
        String fecha = (String) body.get("fecha");

        List<Integer> proveedoresIds = null;
        List<Integer> facturasIds = null;

        if (proveedor != null) {
            proveedoresIds = new ArrayList<>();
            proveedoresIds.add((Integer) proveedor.get("id"));
        }

        if (facturas != null) {
            facturasIds = new ArrayList<>();

            for (HashMap<String, Object> factura : facturas) {
                facturasIds.add((Integer) factura.get("id"));
            }
        }

        List<Map<String, Object>> data =
                ordenCompraDao.getEstadoCuenta(
                        proveedoresIds,
                        facturasIds,
                        !isNullorEmpty(fecha) ? new SimpleDateFormat("yyyy-MM-dd").parse(fecha) : null,
                        estatus != null ? (Integer) estatus.get("id") : 0
                );

        return new JsonResponse(data, "", JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "estado-cuenta/download/excel", method = RequestMethod.POST)
    public void exportEstadoCuenta(@RequestBody JSONObject body, HttpServletResponse response) throws Exception {
        HashMap<String, Object> proveedor = (HashMap<String, Object>) body.get("proveedores");
        List<HashMap<String, Object>> facturas = (ArrayList) body.get("facturas");
        HashMap<String, Object> estatus = (HashMap<String, Object>) body.get("estatus");
        String fecha = (String) body.get("fecha");

        List<Integer> proveedoresIds = null;
        List<Integer> facturasIds = null;

        if (proveedor != null) {
            proveedoresIds = new ArrayList<>();
            proveedoresIds.add((Integer) proveedor.get("id"));
        }

        if (facturas != null) {
            facturasIds = new ArrayList<>();

            for (HashMap<String, Object> factura : facturas) {
                facturasIds.add((Integer) factura.get("id"));
            }
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("proveedoresIds", proveedoresIds);
        params.put("facturasIds", facturasIds);
        params.put("fecha", !isNullorEmpty(fecha) ? new SimpleDateFormat("yyyy-MM-dd").parse(fecha) : null);
        params.put("estatus", estatus != null ? (Integer) estatus.get("id") : 0);

        String query =
                "SELECT factura,\n" +
                "       fecha,\n" +
                "       termino,\n" +
                "       vencimiento,\n" +
                "       monto,\n" +
                "       restante,\n" +
                "       CASE WHEN diasVencido > 0 AND restante > 0 THEN diasVencido ELSE 0 END AS dias\n" +
                "FROM\n" +
                "(\n" +
                "    SELECT facturaId,\n" +
                "           factura,\n" +
                "           fecha,\n" +
                "           termino,\n" +
                "           vencimiento,\n" +
                "           monto,\n" +
                "           pagado,\n" +
                "           restante,\n" +
                "           proveedorId,\n" +
                "           proveedorNombre,\n" +
                "           DATEDIFF(DAY, vencimiento, CAST(COALESCE(:fecha, GETDATE()) AS DATE)) diasVencido,\n" +
                "           diasCredito\n" +
                "    FROM VW_ESTADO_CUENTA_PROVEEDOR\n" +
                ") AS todo\n" +
                "WHERE(COALESCE(:proveedoresIds, 0) = 0 OR proveedorId IN(:proveedoresIds))\n" +
                "       AND (COALESCE(:facturasIds, 0) = 0 OR facturaId IN(:facturasIds))\n" +
                "       AND 1 =\n" +
                "           CASE WHEN :estatus = 0 THEN 1 ELSE\n" +
                "           CASE WHEN :estatus = 1 AND (restante <= 0 OR diasVencido <= diasCredito) THEN 1 ELSE\n" +
                "           CASE WHEN :estatus = 2 AND restante > 0 AND diasVencido > diasCredito THEN 1 ELSE\n" +
                "           0 END END END\n" +
                "ORDER BY fecha,\n" +
                "         vencimiento,\n" +
                "         factura\n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{"Factura", "Fecha", "Termino", "Vencimiento", "Monto original", "Monto actual", "Días vencido"};
        String[] columnasMoneda = new String[]{"Monto original", "Monto actual"};

        excelController.downloadXlsx(response, "estado-cuenta", query, columnas, null, columnasMoneda, params, "Reporte");
    }

    @RequestMapping(value="/pdf/{ocHashId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarPdf(@PathVariable String ocHashId, HttpServletResponse response) throws IOException, SQLException, JRException {

        OrdenCompra ordenCompra = ordenCompraDao.findById(hashId.decode(ocHashId));

        String reportPath = "/ordenesCompra/OC.jasper";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("id", ordenCompra.getId());

        InputStream reporte = reporteService.generarReporte(reportPath,parameters);

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ordenCompra.getCodigo()+".pdf");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }


}

class BuscadorAmazonOpciones {
    public static final int Todo = 1;
    public static final int Codigo = 2;
    public static final int Proveedor = 3;
    public static final int RFC = 4;
    public static final int FechaOC = 5;
    public static final int FechaUltimaModificacion = 6;
    public static final int Estatus = 7;
}