package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.*;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.Articulo.ArticuloUltimasComprasProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboProjection;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.spring.dao.DepartamentoDao;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import com.pixvs.main.models.projections.Moneda.MonedaComboProjection;
import com.pixvs.main.models.projections.Proveedor.ProveedorComboVistaProjection;
import com.pixvs.main.models.projections.Requisicion.RequisicionConvertirListadoProjection;
import com.pixvs.main.models.projections.RequisicionPartida.RequisicionPartidaConvertirListadoProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.UsuarioDao;
import com.pixvs.spring.models.Departamento;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Angel Daniel Hernández Silva on 26/10/2020.
 */
@RestController
@RequestMapping("/api/v1/convertir-requisicion")
public class ConvertirRequisicionController {

    @Autowired
    private RequisicionDao requisicionDao;
    @Autowired
    private RequisicionPartidaDao requisicionPartidaDao;
    @Autowired
    private SucursalDao sucursalDao;
    @Autowired
    private DepartamentoDao departamentoDao;
    @Autowired
    private ProveedorDao proveedorDao;
    @Autowired
    private AlmacenDao almacenDao;
    @Autowired
    private MonedaDao monedaDao;
    @Autowired
    private OrdenCompraController ordenCompraController;
    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;
    @Autowired
    private ArticuloCategoriaDao articuloCategoriaDao;
    @Autowired
    private ArticuloSubcategoriaDao articuloSubcategoriaDao;
    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private HashId hashId;

    @Autowired
    private ConcurrenciaService concurrenciaService;
    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private LogController logController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getRequisiciones(ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<SucursalComboProjection> sucursales = sucursalDao.findProjectedComboAllByUsuarioPermisosId(idUsuario);
        List<DepartamentoComboProjection> departamentos = departamentoDao.findProjectedComboAllByActivoTrue();
        List<ProveedorComboVistaProjection> proveedores = proveedorDao.findProjectedComboVistaAllBy();
        List<MonedaComboProjection> monedas = monedaDao.findProjectedComboAllByActivoTrue();
        MonedaComboProjection monedaPredeterminada = monedaDao.findProjectedComboByPredeterminadaTrue();
        List<RequisicionConvertirListadoProjection> requisiciones = requisicionDao.findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLike(null,null,null);

        List<ArticuloFamiliaComboProjection> articuloFamilias = articuloFamiliaDao.findProjectedComboAllByActivoTrue();
        List<ArticuloCategoriaComboProjection> articuloCategorias = articuloCategoriaDao.findProjectedComboAllByActivoTrue();
        List<ArticuloSubcategoriaComboProjection> articuloSubcategorias = articuloSubcategoriaDao.findProjectedComboAllByActivoTrue();
        List<ArticuloComboProjection> articulos = articuloDao.findProjectedComboAllByActivoTrueAndTipoArticuloNoSistema();

        HashMap<String,Object> body = new HashMap<>();
        body.put("sucursales",sucursales);
        body.put("departamentos",departamentos);
        body.put("proveedores",proveedores);
        body.put("monedas",monedas);
        body.put("monedaPredeterminada",monedaPredeterminada);
        body.put("requisicionesInicial",requisiciones);

        body.put("articuloFamilias",articuloFamilias);
        body.put("articuloCategorias",articuloCategorias);
        body.put("articuloSubcategorias",articuloSubcategorias);
        body.put("articulos",articulos);

        return new JsonResponse(body, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getRequisiciones(@RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws SQLException, ParseException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaInicio = null;
        Date fechaFin = null;
        String codigoRequisicion = null;
        List<HashMap<String,Object>> sucursales = (ArrayList)requestBody.get("sucursalId");
        List<HashMap<String,Object>> departamentos = (ArrayList)requestBody.get("departamentoId");
        List<Integer> sucursalesIds = null;
        List<Integer> departamentosIds = null;

        if(requestBody.get("fechaDesde") != null){
            fechaInicio = dateFormatter.parse((String)requestBody.get("fechaDesde"));
        }
        if(requestBody.get("fechaHasta") != null){
            fechaFin = dateFormatter.parse((String)requestBody.get("fechaHasta"));
        }
        if(requestBody.get("codigoRequisicion") != null){
            codigoRequisicion = (String)requestBody.get("codigoRequisicion");
        }
        if(sucursales != null && sucursales.size() >= 0){
            sucursalesIds = new ArrayList<>();
            for(HashMap<String,Object> sucursal : sucursales){
                sucursalesIds.add((Integer)sucursal.get("id"));
            }
        }
        if(departamentos != null && departamentos.size() >= 0){
            departamentosIds = new ArrayList<>();
            for(HashMap<String,Object> departamento : departamentos){
                departamentosIds.add((Integer)departamento.get("id"));
            }
        }

        List<RequisicionConvertirListadoProjection> requisiciones = new ArrayList<>();
        if(sucursalesIds != null && departamentosIds != null){
            requisiciones = requisicionDao.findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLikeAndSucursalesIdsAndDepartamentosIds(fechaInicio,fechaFin,codigoRequisicion,sucursalesIds,departamentosIds);
        }else if(sucursalesIds != null){
            requisiciones = requisicionDao.findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLikeAndSucursalesIds(fechaInicio,fechaFin,codigoRequisicion,sucursalesIds);
        }else if(departamentosIds != null){
            requisiciones = requisicionDao.findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLikeAndDepartamentosIds(fechaInicio,fechaFin,codigoRequisicion,departamentosIds);
        }else{
            requisiciones = requisicionDao.findProjectedConvertirListadoAllByFechaGreaterThanEqualAndFechaLessThanEqualAndCodigoLike(fechaInicio,fechaFin,codigoRequisicion);
        }

        HashMap<String,Object> body = new HashMap<>();
        body.put("requisiciones",requisiciones);

        return new JsonResponse(body, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/partidas/{requisicionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getPartidas(@PathVariable Integer requisicionId, ServletRequest req) throws SQLException {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<RequisicionPartidaConvertirListadoProjection> partidas = requisicionPartidaDao.findProjectedConvertirListadoAllByRequisicionIdAndEstadoPartidaIdIn(requisicionId,Arrays.asList(ControlesMaestrosMultiples.CMM_REQP_EstatusRequisicionPartida.ABIERTA));
        HashMap<Integer,ProveedorComboProjection> proveedoresMapArticuloId = new HashMap<>();
        for(RequisicionPartidaConvertirListadoProjection partida : partidas){
            List<ArticuloUltimasComprasProjection> ultimasCompras =  articuloDao.findProjectedUltimasComprasAllById(partida.getArticulo().getId());
            if(ultimasCompras.size() > 0){
                proveedoresMapArticuloId.put(partida.getArticulo().getId(),proveedorDao.findProjectedComboById(ultimasCompras.get(0).getProveedorId()));
            }
        }

        HashMap<String,Object> body = new HashMap<>();
        body.put("partidas",partidas);
        body.put("proveedoresMapArticuloId",proveedoresMapArticuloId);

        return new JsonResponse(body, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
    public JsonResponse guardar(@RequestBody List<OrdenCompra> ordenesCompra, ServletRequest req) throws Exception {

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);
        Set<Departamento> departamentos = usuario.getDepartamentosPermisos();
        Integer departamentoId = null;
        if(departamentos.size() == 1){
            for(Departamento departamento : departamentos){
                departamentoId = departamento.getId();
            }
        }
        List<Integer> OcIds = new ArrayList<>();

        for(OrdenCompra oc : ordenesCompra){
            oc.setDepartamentoId(departamentoId);
            JsonResponse jsonResponse = ordenCompraController.enviar(oc,req);
            if(jsonResponse.getStatus() != JsonResponse.STATUS_OK){
                throw new Exception(jsonResponse.getMessage());
            }
            OcIds.add(oc.getId());
            for(OrdenCompraDetalle detalle : oc.getDetalles()){
                Requisicion requisicion = requisicionDao.findByPartidaId(detalle.getRequisicionPartidaId());
                boolean requiConvertida = true;
                int partidasConvertidas = 0;
                for(RequisicionPartida partida : requisicion.getPartidas()){
                    if(partida.getId().intValue() == detalle.getRequisicionPartidaId().intValue()){
                        partida.setEstadoPartidaId(ControlesMaestrosMultiples.CMM_REQP_EstatusRequisicionPartida.CONVERTIDA);
                        partidasConvertidas++;
                    }
                    switch (partida.getEstadoPartidaId().intValue()){
                        case ControlesMaestrosMultiples.CMM_REQP_EstatusRequisicionPartida.ABIERTA:
                        case ControlesMaestrosMultiples.CMM_REQP_EstatusRequisicionPartida.REVISION:
                            requiConvertida = false;
                    }
                }
                if(partidasConvertidas > 1){
                    logController.insertaLogUsuario(
                            new Log("Se convirtieron " + partidasConvertidas + " partidas en la " + oc.getCodigo(),
                                    LogTipo.CONVERTIDO,
                                    LogProceso.REQUISICIONES,
                                    requisicion.getId()
                            ),
                            idUsuario
                    );
                }else if(partidasConvertidas == 1){
                    logController.insertaLogUsuario(
                            new Log("Se convirtió " + partidasConvertidas + " partida en la " + oc.getCodigo(),
                                    LogTipo.CONVERTIDO,
                                    LogProceso.REQUISICIONES,
                                    requisicion.getId()
                            ),
                            idUsuario
                    );
                }
                if(requiConvertida){
                    requisicion.setEstadoRequisicionId(ControlesMaestrosMultiples.CMM_REQ_EstatusRequisicion.CONVERTIDA);
                    logController.insertaLogUsuario(
                            new Log(null,
                                    LogTipo.CONVERTIDO,
                                    LogProceso.REQUISICIONES,
                                    requisicion.getId()
                            ),
                            idUsuario
                    );
                }
                requisicionDao.save(requisicion);
            }

            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.CREADO,
                            LogProceso.ORDENES_COMPRA,
                            oc.getId()
                    ),
                    idUsuario
            );
        }

        return new JsonResponse(OcIds,null,JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/rechazar/partida", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse rechazarPartida(@RequestBody HashMap<String,Object> requestBody, ServletRequest req) throws Exception {

        Integer partidaId = (Integer)requestBody.get("partidaId");
        String comentario = (String)requestBody.get("comentario");

        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Usuario usuario = usuarioDao.findById(idUsuario);
        RequisicionPartida partida = requisicionPartidaDao.findById(partidaId);

        partida.setEstadoPartidaId(ControlesMaestrosMultiples.CMM_REQP_EstatusRequisicionPartida.RECHAZADO);
        if(partida.getComentarios() == null){
            partida.setComentarios(" - " + comentario);
        }else{
            partida.setComentarios(partida.getComentarios() + " - " + comentario);
        }
        partida.setModificadoPorId(idUsuario);

        requisicionPartidaDao.save(partida);

        logController.insertaLogUsuario(
                new Log("Partida " + partida.getArticulo().getNombreArticulo() + " rechazada por " + usuario.getNombreCompleto(),
                        LogTipo.CONVERTIDO,
                        LogProceso.REQUISICIONES,
                        partida.getRequisicionId()
                ),
                idUsuario
        );

        return new JsonResponse(null,null,JsonResponse.STATUS_OK);
    }

}
