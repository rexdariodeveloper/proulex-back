package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.InventarioFisico;
import com.pixvs.main.models.InventarioFisicoDetalle;
import com.pixvs.main.models.InventarioMovimiento;
import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_ALM_TipoAlmacen;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_IF_EstatusInventarioFisico;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_IM_TipoMovimiento;
import com.pixvs.main.models.projections.InventarioFisico.InventarioFisicoListadoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.AutonumericoService;
import com.pixvs.spring.services.ConcurrenciaService;
import com.pixvs.spring.util.HashId;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.pixvs.spring.util.StringCheck.isNullorEmpty;

@RestController
@RequestMapping("/api/v1/inventarios-fisicos")
public class InventarioFisicoController {

    @Autowired
    private AutonumericoService autonumericoService;

    @Autowired
    private ExcelController excelController;

    @Autowired
    private HashId hashId;

    @Autowired
    private AlmacenDao almacenDao;

    @Autowired
    private LocalidadDao localidadDao;

    @Autowired
    private ArticuloDao articuloDao;

    @Autowired
    private ArticuloFamiliaDao articuloFamiliaDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private LocalidadArticuloAcumuladoDao localidadArticuloAcumuladoDao;

    @Autowired
    private InventarioFisicoDao inventarioFisicoDao;

    @Autowired
    private InventarioFisicoDetalleDao inventarioFisicoDetalleDao;

    @Autowired
    private InventarioMovimientoController inventarioMovimientoController;

    @Autowired
    private ConcurrenciaService concurrenciaService;

    @RequestMapping(value = "/last200", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getLast200(@PathVariable Integer[] estatusId, ServletRequest req) throws Exception {
        List<InventarioFisicoListadoProjection> inventarios
                = estatusId != null
                ? inventarioFisicoDao.findFirst200ByEstatusIdInOrderByFechaDesc(estatusId)
                : inventarioFisicoDao.findFirst200ByIdIsNotNullOrderByFechaDesc();
        return new JsonResponse(inventarios, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListado() throws Exception {
        HashMap<String, Object> datos = new HashMap<>();

        datos.put("datos", getLast200(null, null).getData());
        datos.put("estatus", controlMaestroMultipleDao.findAllByControlInOrderByValor(new String[]{CMM_IF_EstatusInventarioFisico.NOMBRE}));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFiltros(@RequestBody JSONObject json, ServletRequest req) throws SQLException, ParseException {
        ArrayList<HashMap<String, Integer>> allEstatus = (ArrayList<HashMap<String, Integer>>) json.get("referencia");
        ArrayList<HashMap<String, Integer>> IFEstatus = (ArrayList<HashMap<String, Integer>>) json.get("estatus");

        ArrayList<Integer> estatus = new ArrayList<>();

        if (IFEstatus != null) {
            for (HashMap<String, Integer> status : IFEstatus) {
                estatus.add(status.get("id"));
            }
        }

        String fechaCreacionDesde = (String) json.get("fechaCreacionDesde");
        String fechaCreacionHasta = (String) json.get("fechaCreacionHasta");

        Date dateFechaCreacionDesde = isNullorEmpty(fechaCreacionDesde) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionDesde);
        Date dateFechaCreacionHasta = isNullorEmpty(fechaCreacionHasta) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(fechaCreacionHasta);

        return new JsonResponse(inventarioFisicoDao.findAllQueryProjectedBy(dateFechaCreacionDesde, dateFechaCreacionHasta, estatus.isEmpty() ? 1 : 0, estatus), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/detalle", "/detalle/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosDetalle(@PathVariable(required = false) Integer id) throws Exception {
        HashMap<String, Object> datos = new HashMap<>();

        if (id != null) {
            InventarioFisicoListadoProjection inventario = inventarioFisicoDao.findInventarioFisicoProjectedById(id);
            datos.put("inventarioFisico", inventario);
            datos.put("inventarioFisicoDetalles", inventarioFisicoDetalleDao.findAllProjectedByInventarioFisicoId(id));
            datos.put("editar", inventario.getEstatus().getId().equals(CMM_IF_EstatusInventarioFisico.EN_EDICION));
        } else {
            datos.put("almacenes", almacenDao.findAllProjectedComboResponsableByActivoTrueAndTipoAlmacenIdOrderByCodigoAlmacen(CMM_ALM_TipoAlmacen.NORMAL));
            datos.put("localidades", localidadDao.findProjectedComboAllByActivoTrueOrderByCodigoLocalidad());
            datos.put("familias", articuloFamiliaDao.findProjectedComboAllByActivoTrue());
            datos.put("articulos", articuloDao.findProjectedComboAllByActivoTrueAndInventariable(true));
            datos.put("localidaesArticulos", localidadArticuloAcumuladoDao.findLocalidadArticuloAcumuladoByIdIsNotNull());
            datos.put("editar", true);
            datos.put("fecha", new Date());
        }

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody InventarioFisico inventarioFisico, ServletRequest req) throws Exception {
        Integer usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        boolean afectar = inventarioFisico.isAfectar();
        Localidad localidad = inventarioFisico.getLocalidad();
        String razon = "Inventario Físico de: " + localidad.getCodigoLocalidad() + "/" + localidad.getNombre();
        List<InventarioFisicoDetalle> detalles = inventarioFisico.getInventarioFisicoDetalles();

        if (inventarioFisico.getId() == null) {
            InventarioFisicoListadoProjection inventarioFisicoTemp = inventarioFisicoDao.findInventarioFisicoByLocalidadIdAndEstatusId(localidad.getId(), CMM_IF_EstatusInventarioFisico.EN_EDICION);

            if (inventarioFisicoTemp != null) {
                throw new Exception("El Inventario Físico: " + inventarioFisicoTemp.getCodigo() + " está en edición.");
            }

            List<InventarioFisicoListadoProjection> inventariosNuevos = inventarioFisicoDao.findInventariosFisicosByLocalidadIdAndFechaCreacionGreaterThanEqual(localidad.getId(), new Date(inventarioFisico.getFechaCreacionTemp()));

            if (inventariosNuevos.size() > 0) {
                throw new Exception("Hay nuevos inventarios físicos para la localidad, favor de recargar.");
            }

            inventarioFisico.setCodigo(autonumericoService.getSiguienteAutonumericoByPrefijo("IF"));
            inventarioFisico.setLocalidadId(localidad.getId());
            inventarioFisico.setFechaAfectacion(null);
            inventarioFisico.setEstatusId(CMM_IF_EstatusInventarioFisico.EN_EDICION);
            inventarioFisico.setCreadoPorId(usuarioId);
        } else {
            InventarioFisico objetoActual = inventarioFisicoDao.findById(inventarioFisico.getId().intValue());
            try{
                concurrenciaService.verificarIntegridad(objetoActual.getFechaModificacion(),inventarioFisico.getFechaModificacion());
            }catch (Exception e){
                return new JsonResponse("", objetoActual.getModificadoPor().getNombreCompleto(), JsonResponse.STATUS_ERROR_CONCURRENCIA);
            }
            inventarioFisico.setModificadoPorId(usuarioId);

            if (afectar) {
                inventarioFisico.setEstatusId(CMM_IF_EstatusInventarioFisico.AJUSTADO);
                inventarioFisico.setAfectadoPorId(usuarioId);
                inventarioFisico.setFechaAfectacion(new Timestamp(new Date().getTime()));
            } else {
                inventarioFisico.setFechaAfectacion(null);
            }
        }

        inventarioFisico = inventarioFisicoDao.save(inventarioFisico);

        for (InventarioFisicoDetalle detalle : detalles) {
            detalle.setInventarioFisicoId(inventarioFisico.getId());
            detalle = inventarioFisicoDetalleDao.save(detalle);

            if (afectar && detalle.getConteo().compareTo(detalle.getExistencia()) != 0) {
                procesarMovimiento(
                        detalle.getArticuloId(),
                        localidad.getId(),
                        detalle.getConteo().subtract(detalle.getExistencia()),
                        razon,
                        inventarioFisico.getCodigo(),
                        detalle.getId(),
                        req
                );
            }
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }

    @Transactional
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteById(@PathVariable String id) throws Exception {
        return new JsonResponse(inventarioFisicoDao.actualizarEstatus(hashId.decode(id), CMM_IF_EstatusInventarioFisico.BORRADO), null, JsonResponse.STATUS_OK);
    }

    @GetMapping("/download/excel")
    public void downloadXlsxAjuste(HttpServletResponse response) throws Exception {
        String query = "SELECT * from [VW_LISTADO_INVENTARIOS_FISICOS]";
        String[] columnas = new String[]{"Código", "Almacén", "Localidad", "Creado Por", "Fecha", "Afectado Por", "Afectado", "Estatus"};

        excelController.downloadXlsx(response, "inventario-fisico", query, columnas, null, "Inventario Físico");
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
        movimiento.setTipoMovimientoId(CMM_IM_TipoMovimiento.INVENTARIO_FISICO);

        inventarioMovimientoController.procesarMovimiento(movimiento, req);
    }
}
