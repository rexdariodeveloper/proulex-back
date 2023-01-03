package com.pixvs.main.controllers;

import com.pixvs.log.Log;
import com.pixvs.log.LogController;
import com.pixvs.main.dao.*;
import com.pixvs.main.models.InventarioMovimiento;
import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.Transferencia;
import com.pixvs.main.models.TransferenciaDetalle;
import com.pixvs.main.models.mapeos.ArticulosSubtipos;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_IM_TipoMovimiento;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_TRA_EstatusTransferencia;
import com.pixvs.main.models.projections.TransferenciaDetalle.TransferenciaMovimientoListadoProjection;
import com.pixvs.spring.models.mapeos.LogProceso;
import com.pixvs.spring.models.mapeos.LogTipo;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Transferencia.TransferenciaListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

@RestController
@RequestMapping("/api/v1/transferencias")
public class TransferenciaController {

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private LocalidadArticuloDao localidadArticuloDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private LocalidadArticuloAcumuladoDao localidadArticuloAcumuladoDao;

    @Autowired
    private TransferenciaDao transferenciaDao;

    @Autowired
    private TransferenciaDetalleDao transferenciaDetalleDao;

    @Autowired
    private InventarioMovimientoController inventarioMovimientoController;

    @Autowired
    private LogController logController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/last200", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getLast200(@PathVariable Integer[] estatusId, ServletRequest req) throws Exception {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        List<Integer> ids = new ArrayList<>();

        List<TransferenciaListadoProjection> transferencias
                = estatusId != null
                ? transferenciaDao.findAllTransferenciasByPermisoAndEstatus(idUsuario, estatusId)
                : transferenciaDao.findAllTransferenciasByPermiso(idUsuario);
        List<TransferenciaListadoProjection> filtradas = new ArrayList<>();

        for (TransferenciaListadoProjection transferencia : transferencias){
            if (!ids.contains(transferencia.getId())){
                filtradas.add(transferencia);
                ids.add(transferencia.getId());
            }
        }
        return new JsonResponse(filtradas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListado(ServletRequest req) throws Exception {
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", getLast200(null, req).getData());
        datos.put("estatus", controlMaestroMultipleDao.findAllByControlInOrderByValor(new String[]{CMM_TRA_EstatusTransferencia.NOMBRE}));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/recibir/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListadoRecibir(ServletRequest req) throws Exception {
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> datos = new HashMap<>();

        Integer[] estatus = new Integer[]{
                CMM_TRA_EstatusTransferencia.EN_TRANSITO,
                CMM_TRA_EstatusTransferencia.TRANSFERIDO_PARCIALMENTE
        };

        List<TransferenciaListadoProjection> transferencias =  transferenciaDao.findAllTransferenciasByPermisoDestinoAndEstatus(idUsuario, estatus);
        List<Integer> ids = new ArrayList<>();
        List<TransferenciaListadoProjection> filtradas = new ArrayList<>();

        for (TransferenciaListadoProjection transferencia : transferencias) {
            if (!ids.contains(transferencia.getId())){
                filtradas.add(transferencia);
                ids.add(transferencia.getId());
            }
        }

        datos.put("datos", filtradas);
        datos.put("estatus", controlMaestroMultipleDao.findAllByIdInOrderByValor(estatus));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        int idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);
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

        List<TransferenciaListadoProjection> transferencias = transferenciaDao.findAllQueryProjectedBy(dateFechaCreacionDesde, dateFechaCreacionHasta, estatus.isEmpty() ? 1 : 0, estatus, idUsuario);

        List<Integer> ids = new ArrayList<>();
        List<TransferenciaListadoProjection> filtradas = new ArrayList<>();

        for (TransferenciaListadoProjection transferencia : transferencias){
            if (!ids.contains(transferencia.getId())){
                filtradas.add(transferencia);
                ids.add(transferencia.getId());
            }
        }

        return new JsonResponse(filtradas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/recibir/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltrosRecibir(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        return getFiltros(json, req);
    }

    @RequestMapping(value = {"/detalle", "/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosDetalle(@PathVariable(required = false) Integer id, ServletRequest req) throws Exception {
        HashMap<String, Object> datos = new HashMap<>();
        Integer idUsuario = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<TransferenciaMovimientoListadoProjection> movimientos = new ArrayList<>();

        if (id != null) {
            datos.put("transferencia", transferenciaDao.findTransferenciaProjectedById(id));
            datos.put("transferenciaDetalles", transferenciaDetalleDao.findAllByTransferenciaId(id));
            datos.put("historial", logController.getHistorial(id, LogProceso.TRANSFERENCIAS));
            movimientos = transferenciaDetalleDao.findMovimientosById(id);
        } else {
            datos.put("almacenes", almacenDao.findAllProjectedComboByActivoTrueAndTipoAlmacenIdAndPermisoOrderByCodigoAlmacen(CMM_ALM_TipoAlmacen.NORMAL, idUsuario));
            datos.put("almacenes_todos", almacenDao.findAllProjectedComboByActivoTrueAndTipoAlmacenIdOrderByCodigoAlmacen(CMM_ALM_TipoAlmacen.NORMAL));
            datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueOrderByCodigoLocalidad());
            datos.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndInventariableAndArticuloSubtipoIdNotIn(true, Arrays.asList(ArticulosSubtipos.PAQUETE_DE_LIBROS)));
        }

        datos.put("localidaesArticulos", localidadArticuloAcumuladoDao.findLocalidadArticuloAcumuladoByIdIsNotNull());
        datos.put("movimientos", movimientos);
        datos.put("localidaedesArticulosActivos", localidadArticuloDao.findAllProjectedBy() );

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/recibir/detalle", "/recibir/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosRecibirDetalle(@PathVariable(required = false) Integer id) throws Exception {
        HashMap<String, Object> datos = new HashMap<>();

        if (id != null) {
            TransferenciaListadoProjection transferencia = transferenciaDao.findTransferenciaProjectedById(id);

            Integer[] estatus = new Integer[]{
                    CMM_TRA_EstatusTransferencia.EN_TRANSITO,
                    CMM_TRA_EstatusTransferencia.TRANSFERIDO_PARCIALMENTE
            };

            datos.put("transferencia", transferencia);
            datos.put("transferenciaDetalles", transferenciaDetalleDao.findAllByTransferenciaIdAndEstatusIdIn(id, estatus));
            datos.put("mostrarRechazar", transferencia.getEstatus().getId().equals(CMM_TRA_EstatusTransferencia.EN_TRANSITO));
            datos.put("historial", logController.getHistorial(id, LogProceso.TRANSFERENCIAS));
            datos.put("almacenes", almacenDao.findAllProjectedComboByActivoTrueAndTipoAlmacenIdOrderByCodigoAlmacen(CMM_ALM_TipoAlmacen.NORMAL));
            datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueOrderByCodigoLocalidad());
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardarTransferencia(@RequestBody Transferencia transferencia, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        List<TransferenciaDetalle> transferenciaDetalles = transferencia.getTransferenciaDetalles();

        Localidad localidadOrigen = transferencia.getLocalidadOrigen();
        Localidad localidadDestino = transferencia.getLocalidadDestino();
        AlmacenComboProjection almacenTransito = almacenDao.findProjectedComboByActivoTrueAndTipoAlmacenId(CMM_ALM_TipoAlmacen.TRANSITO);
        Localidad localidadTransito = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacenTransito.getId());

        String razon = "Transferencia de: " + localidadOrigen.getCodigoLocalidad() + "/" + localidadOrigen.getNombre()
                + " a: " + localidadDestino.getCodigoLocalidad() + "/" + localidadDestino.getNombre();

        if(transferencia.getId() != null){
            Transferencia objetoActual = transferenciaDao.findById(transferencia.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),transferencia.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
        }

        if (transferencia.getId() == null) {
            Integer[] estatus = new Integer[]{
                    CMM_TRA_EstatusTransferencia.TRANSFERIDO,
                    CMM_TRA_EstatusTransferencia.RECHAZADO
            };

            TransferenciaListadoProjection transferenciaTemp = transferenciaDao.findTransferenciaByLocalidadOrigenIdAndLocalidadDestinoIdAndEstatusIdNotIn(localidadOrigen.getId(), localidadDestino.getId(), estatus);

            if (transferenciaTemp != null) {
                return new JsonResponse(null, "La Transferencia: " + transferenciaTemp.getCodigo() + " está pendiente de recibir.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }

            transferencia.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("TRA"));
            transferencia.setLocalidadOrigenId(localidadOrigen.getId());
            transferencia.setLocalidadDestinoId(localidadDestino.getId());
            transferencia.setEstatusId(CMM_TRA_EstatusTransferencia.EN_TRANSITO);
            transferencia.setCreadoPorId(usuarioId);
            transferencia = transferenciaDao.save(transferencia);

            for (TransferenciaDetalle detalle : transferenciaDetalles) {
                detalle.setTransferenciaId(transferencia.getId());
                detalle.setEstatusId(CMM_TRA_EstatusTransferencia.EN_TRANSITO);
                detalle = transferenciaDetalleDao.save(detalle);

                procesarMovimiento(
                        detalle.getArticuloId(),
                        localidadOrigen.getId(),
                        detalle.getCantidad().negate(),
                        razon,
                        transferencia.getCodigo(),
                        detalle.getId(),
                        req
                );

                procesarMovimiento(
                        detalle.getArticuloId(),
                        localidadTransito.getId(),
                        detalle.getCantidad(),
                        razon,
                        transferencia.getCodigo(),
                        detalle.getId(),
                        req
                );
            }

            logController.insertaLogUsuario(
                    new Log(null,
                            LogTipo.EN_TRANSITO,
                            LogProceso.TRANSFERENCIAS,
                            transferencia.getId()
                    ),
                    usuarioId
            );
        } else {
            int nTransferidos = 0;

            for (TransferenciaDetalle detalle : transferenciaDetalles) {
                BigDecimal sumaCantidades = detalle.getCantidadTransferida().add(detalle.getCantidadDevuelta()).add(detalle.getSpill());

                if (sumaCantidades.compareTo(BigDecimal.ZERO) != 0) {
                    if (detalle.getCantidadTransferida().compareTo(BigDecimal.ZERO) != 0) {
                        procesarMovimiento(
                                detalle.getArticuloId(),
                                localidadTransito.getId(),
                                detalle.getCantidadTransferida().negate(),
                                razon,
                                transferencia.getCodigo(),
                                detalle.getId(),
                                req
                        );

                        procesarMovimiento(
                                detalle.getArticuloId(),
                                localidadDestino.getId(),
                                detalle.getCantidadTransferida(),
                                razon,
                                transferencia.getCodigo(),
                                detalle.getId(),
                                req
                        );
                    }

                    if (detalle.getCantidadDevuelta().compareTo(BigDecimal.ZERO) != 0) {
                        procesarMovimiento(
                                detalle.getArticuloId(),
                                localidadTransito.getId(),
                                detalle.getCantidadDevuelta().negate(),
                                "Devolución " + razon,
                                transferencia.getCodigo(),
                                detalle.getId(),
                                req
                        );

                        procesarMovimiento(
                                detalle.getArticuloId(),
                                localidadOrigen.getId(),
                                detalle.getCantidadDevuelta(),
                                "Devolución " + razon,
                                transferencia.getCodigo(),
                                detalle.getId(),
                                req
                        );
                    }

                    if (detalle.getSpill().compareTo(BigDecimal.ZERO) != 0) {
                        procesarMovimiento(
                                detalle.getArticuloId(),
                                localidadTransito.getId(),
                                detalle.getSpill().negate(),
                                "Spill " + razon,
                                transferencia.getCodigo(),
                                detalle.getId(),
                                req
                        );
                    }

                    TransferenciaDetalle detalleTmp = transferenciaDetalleDao.findTransferenciaDetalleById(detalle.getId());
                    detalleTmp.setCantidadTransferida(detalleTmp.getCantidadTransferida().add(detalle.getCantidadTransferida()));
                    detalleTmp.setCantidadDevuelta(detalleTmp.getCantidadDevuelta().add(detalle.getCantidadDevuelta()));
                    detalleTmp.setSpill(detalleTmp.getSpill().add(detalle.getSpill()));

                    sumaCantidades = detalleTmp.getCantidadTransferida().add(detalleTmp.getCantidadDevuelta()).add(detalleTmp.getSpill());

                    if (detalleTmp.getCantidad().equals(sumaCantidades)) {
                        detalleTmp.setEstatusId(CMM_TRA_EstatusTransferencia.TRANSFERIDO);
                        nTransferidos++;
                    } else {
                        detalleTmp.setEstatusId(CMM_TRA_EstatusTransferencia.TRANSFERIDO_PARCIALMENTE);
                    }

                    transferenciaDetalleDao.save(detalleTmp);
                }
            }

            transferencia.setEstatusId(transferenciaDetalles.size() == nTransferidos ? CMM_TRA_EstatusTransferencia.TRANSFERIDO : CMM_TRA_EstatusTransferencia.TRANSFERIDO_PARCIALMENTE);
            transferencia.setModificadoPorId(usuarioId);
            transferenciaDao.save(transferencia);

            logController.insertaLogUsuario(
                    new Log(null,
                            transferenciaDetalles.size() == nTransferidos
                                    ? LogTipo.TRANSFERIDO
                                    : LogTipo.TRANSFERENCIA_PARCIAL,
                            LogProceso.TRANSFERENCIAS,
                            transferencia.getId()
                    ),
                    usuarioId
            );
        }

        return new JsonResponse(transferencia.getCodigo(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/rechazar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse rechazarTransferencia(@RequestBody HashMap<String, Object> datos, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer transferenciaId = (Integer) datos.get("id");
        String comentario = (String) datos.get("comentario");

        Transferencia transferencia = transferenciaDao.findTransferenciaById(transferenciaId);
        transferencia.setComentario((transferencia.getComentario() != null? transferencia.getComentario() : "")+" | "+comentario);
        List<TransferenciaDetalle> detalles = transferenciaDetalleDao.findAllTransferenciaDetalleByTransferenciaId(transferenciaId);

        Localidad localidadOrigen = transferencia.getLocalidadOrigen();
        Localidad localidadDestino = transferencia.getLocalidadDestino();
        AlmacenComboProjection almacenTransito = almacenDao.findProjectedComboByActivoTrueAndTipoAlmacenId(CMM_ALM_TipoAlmacen.TRANSITO);
        Localidad localidadTransito = localidadDao.findByAlmacenIdAndLocalidadGeneralTrue(almacenTransito.getId());

        String razon = "Rechazar Transferencia de: " + localidadOrigen.getCodigoLocalidad() + "/" + localidadOrigen.getNombre()
                + " a: " + localidadDestino.getCodigoLocalidad() + "/" + localidadDestino.getNombre();

        for (TransferenciaDetalle detalle : detalles) {
            detalle.setCantidadDevuelta(detalle.getCantidad());
            detalle.setEstatusId(CMM_TRA_EstatusTransferencia.RECHAZADO);
            transferenciaDetalleDao.save(detalle);

            procesarMovimiento(
                    detalle.getArticuloId(),
                    localidadTransito.getId(),
                    detalle.getCantidad().negate(),
                    razon,
                    transferencia.getCodigo(),
                    detalle.getId(),
                    req
            );

            procesarMovimiento(
                    detalle.getArticuloId(),
                    transferencia.getLocalidadOrigen().getId(),
                    detalle.getCantidad(),
                    razon,
                    transferencia.getCodigo(),
                    detalle.getId(),
                    req
            );
        }

        transferencia.setEstatusId(CMM_TRA_EstatusTransferencia.RECHAZADO);
        transferencia.setModificadoPorId(usuarioId);
        transferenciaDao.save(transferencia);

        logController.insertaLogUsuario(
                new Log(null,
                        LogTipo.RECHAZADO,
                        LogProceso.TRANSFERENCIAS,
                        transferencia.getId()
                ),
                usuarioId
        );

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsxAjuste(HttpServletResponse response) throws Exception {
        String query = "SELECT * from [VW_LISTADO_TRANSFERENCIAS]";
        String[] columnas = new String[]{"Código", "Fecha", "Almacén Origen", "Localidad Origen", "Almacén Destino", "Localidad Destino", "Comentario", "Estatus"};

        excelController.downloadXlsx(response, "transferencias", query, columnas, null, "Transferencias");
    }

    @GetMapping("/recibir/download/excel")
    public void downloadXlsxRecibirTransferencias(HttpServletResponse response) throws Exception {
        String query = "SELECT * from [VW_LISTADO_TRANSFERENCIAS] WHERE EstatusId IN ("+CMM_TRA_EstatusTransferencia.EN_TRANSITO+","+CMM_TRA_EstatusTransferencia.TRANSFERIDO_PARCIALMENTE+")";
        String[] columnas = new String[]{"Código", "Fecha", "Almacén Origen", "Localidad Origen", "Almacén Destino", "Localidad Destino", "Comentario", "Estatus"};

        excelController.downloadXlsx(response, "transferencias-recibir", query, columnas, null, "Transferencias");
    }

    @Transactional(rollbackFor = Exception.class)
    public void procesarMovimiento(int articuloId, int localidadId, BigDecimal cantidad, String razon, String referencia, int referenciaId, ServletRequest req) throws Exception {
        InventarioMovimiento movimiento = new InventarioMovimiento();
        movimiento.setArticuloId(articuloId);
        movimiento.setLocalidadId(localidadId);
        movimiento.setCantidad(cantidad);
        movimiento.setRazon(razon);
        movimiento.setReferencia(referencia);
        movimiento.setReferenciaMovimientoId(referenciaId);
        movimiento.setTipoMovimientoId(CMM_IM_TipoMovimiento.TRANSFERENCIA);

        inventarioMovimientoController.procesarMovimiento(movimiento, req);
    }
}
