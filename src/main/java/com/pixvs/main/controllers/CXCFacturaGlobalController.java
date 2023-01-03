package com.pixvs.main.controllers;

import com.pixvs.main.dao.CXCFacturaDao;
import com.pixvs.main.dao.OrdenVentaDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.dao.SucursalPlantelDao;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OV_Estatus;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.CXCFactura.NotaVentaCXCFacturaEditarProjection;
import com.pixvs.main.models.projections.OrdenVenta.FacturacionGlobalNotaVentaProjection;
import com.pixvs.main.services.FacturaService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.dao.SATMesDao;
import com.pixvs.spring.dao.SATPeriodicidadDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.handler.exceptions.ErrorConDatosException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cxcfacturas-global")
public class CXCFacturaGlobalController {

    @Autowired
    private CXCFacturaDao cxcFacturaDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private SucursalPlantelDao sucursalPlantelDao;

    @Autowired
    private SATPeriodicidadDao periodicidadDao;

    @Autowired
    private SATMesDao mesDao;

    @Autowired
    private OrdenVentaDao ordenVentaDao;

    @Autowired
    private FacturaService facturacionService;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @Autowired
    ExcelController excelController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse getCXCFacturas() throws Exception {
        return new JsonResponse(cxcFacturaDao.findListadoFacturasGlobalesProjected(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        boolean esVersion4;
        HashMap<String, Object> json = new HashMap<>();

        if (id != null) {
            NotaVentaCXCFacturaEditarProjection cxcFactura = cxcFacturaDao.findNotaVentaCXCFacturaEditarProjectionById(id);

            if (cxcFactura == null) {
                return new JsonResponse(null, "La Factura no existe.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }

            json.put("factura", cxcFactura);
            json.put("notasVenta", ordenVentaDao.findFacturacionGlobalNotaVentaProjectedByFacturaId(id));
            json.put("impuestos", ordenVentaDao.findImpuestosFacturacionGlobalNotaVentaProjectedByFacturaId(id));

            esVersion4 = cxcFactura.getVersion().equals("4.0");
        } else {
            boolean permisoTerceros = rolMenuPermisoDao.isPermisoByUsuarioIdAndPermisoId(usuarioId, MenuPrincipalPermisos.FACTURA_GLOBAL_TERCEROS);

            json.put("listSedes", !permisoTerceros ? sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId) : sucursalDao.findProjectedComboAllByActivoTrueOrderByNombre());
            json.put("listPlanteles", !permisoTerceros ? sucursalPlantelDao.findProjectedComboAllByUsuarioPermisosId(usuarioId) : sucursalPlantelDao.findProjectedComboAllByActivoTrueOrderByNombre());
            json.put("listTipoRelacion", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_TipoRelacion"));

            esVersion4 = facturacionService.esVersion4();
        }

        if (esVersion4) {
            json.put("listPeriodicidades", periodicidadDao.findAllComboProjectedByActivoTrueOrderByCodigo());
            json.put("listMeses", mesDao.findAllComboProjectedByActivoTrueOrderByCodigo());
        }

        json.put("esVersion4", esVersion4);
        json.put("listMotivosCancelacion", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_MotivoCancelacion"));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Map<String, Object> json, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        int sucursalId = (int) json.get("sucursalId");
        Integer periodicidadId = (Integer) json.get("periodicidadId");
        Integer mesId = (Integer) json.get("mesId");
        Integer anio = (Integer) json.get("anio");
        List<Integer> ordenesVentaId = (List<Integer>) json.get("ordenesVentaId");
        Boolean isPrevisualizar = (Boolean) json.get("previsualizar");
        isPrevisualizar = isPrevisualizar != null && isPrevisualizar;
        Map<String, Integer> facturasRelacionadas = null;

        if (json.get("facturasRelacionadas") != null) {
            facturasRelacionadas = (Map<String, Integer>) json.get("facturasRelacionadas");
        }

        String stringOrdenesVentaId = "";

        for (int ordenVentaId : ordenesVentaId) {
            stringOrdenesVentaId += "|" + ordenVentaId + "|";
        }

        try {
            int facturaId = facturacionService.spInsertarFacturaGlobalNotaVenta(sucursalId, periodicidadId, mesId, anio, stringOrdenesVentaId, usuarioId, facturasRelacionadas);
            String xmlTimbrado = facturacionService.facturar(facturaId, !isPrevisualizar);
            NotaVentaCXCFacturaEditarProjection factura = cxcFacturaDao.findNotaVentaCXCFacturaEditarProjectionById(facturaId);

            if (isPrevisualizar) {
                throw new ErrorConDatosException(factura.getXml());
            }

            return new JsonResponse(factura, null, JsonResponse.STATUS_OK);
        } catch (ErrorConDatosException ex) {
            throw new ErrorConDatosException(ex);
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @RequestMapping(value = "/getUsuarios", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFechasByCicloPA(@RequestBody Map<String, Object> json, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        boolean permisoTerceros = rolMenuPermisoDao.isPermisoByUsuarioIdAndPermisoId(usuarioId, MenuPrincipalPermisos.FACTURA_GLOBAL_TERCEROS);
        Integer sedeId = (Integer) json.get("sedeId");
        List<Integer> plantelesId = Utilidades.getListItems(json.get("planteles"), "id");
        List<UsuarioComboProjection> usuarios =
                sedeId != null ? sucursalDao.findUsuariosPorSedeId(sedeId, permisoTerceros ? null : usuarioId)
                        : plantelesId != null ? sucursalPlantelDao.findUsuariosPorPlantelId(plantelesId, permisoTerceros ? null : usuarioId)
                        : null;

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("usuarios", usuarios);

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/notaVenta", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosNotaVenta(@RequestBody Map<String, Object> json, ServletRequest req) {
        Integer sedeId = Utilidades.getItem(json.get("sedeId"), "id");
        List<Integer> plantelId = Utilidades.getListItems(json.get("planteles"), "id");
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        List<Integer> usuariosId = Utilidades.getListItems(json.get("usuarios"), "id");

        List<FacturacionGlobalNotaVentaProjection> ordenesVenta =
                ordenVentaDao.findDatosFacturacionGlobalNotaVentaProjected(
                        Arrays.asList(sedeId),
                        plantelId,
                        fechaInicio,
                        fechaFin,
                        CMM_OV_Estatus.PAGADA,
                        usuariosId
                );

        if (ordenesVenta == null || ordenesVenta.isEmpty()) {
            return new JsonResponse(null, "No existen Notas de Venta por factura en el rago de fechas seleccionado.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        }

        HashMap<String, Object> datos = new HashMap<>();
        datos.put("notasVenta", ordenesVenta);
        datos.put("impuestos", ordenVentaDao.findImpuestosFacturacionGlobalNotaVentaProjected(Arrays.asList(sedeId), plantelId, fechaInicio, fechaFin, CMM_OV_Estatus.PAGADA, usuariosId));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/xlsx/notasVenta", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        Integer sedeId = Utilidades.getItem(json.get("sedeId"), "id");
        List<Integer> plantelId = Utilidades.getListItems(json.get("planteles"), "id");
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        List<Integer> usuariosId = Utilidades.getListItems(json.get("usuarios"), "id");

        HashMap<String, Object> params = new HashMap<>();

        params.put("sucursalId", Arrays.asList(sedeId));
        params.put("plantelId", plantelId);
        params.put("fechaInicio", fechaInicio);
        params.put("fechaFin", fechaFin);
        params.put("estatusId", CMM_OV_Estatus.PAGADA);
        params.put("usuarioId", usuariosId);

        String query =
                "SELECT Cantidad,\n" +
                "     Unidad,\n" +
                "     ClaveProdServ,\n" +
                "     NoIdentificacion,\n" +
                "     Descripcion,\n" +
                "     ValorUnitario,\n" +
                "     Subtotal,\n" +
                "     Descuento,\n" +
                "     Impuestos,\n" +
                "     Total\n" +
                "FROM Sucursales\n" +
                "     CROSS APPLY fn_getDatosFacturacionGlobalOV(SUC_SucursalId, :fechaInicio, :fechaFin) AS ov\n" +
                "WHERE SUC_SucursalId IN(:sucursalId)\n" +
                "      AND (COALESCE(:plantelId, 0) = 0 OR ISNULL(PlantelId, -1) IN (:plantelId))\n" +
                "      AND EstatusId = :estatusId\n" +
                "      AND (COALESCE(:usuarioId, 0) = 0 OR UsuarioId IN (:usuarioId))\n" +
                "ORDER BY NoIdentificacion\n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{
                "CANTIDAD", "UNIDAD", "CLAVE PRODUCTO", "NO. IDENTIFICACIÓN", "DESCRIPCIÓN", "VALOR UNITARIO",
                "SUBTOTAL", "DESCUENTO", "IMPUESTOS", "TOTAL",
        };

        String[] totales = new String[]{ "SUBTOTAL", "DESCUENTO", "IMPUESTOS", "TOTAL" };
        String[] columnasMoneda = new String[]{ "SUBTOTAL", "DESCUENTO", "IMPUESTOS", "TOTAL" };

        excelController.downloadXlsx(response, "Notas de Venta", query, columnas, totales, columnasMoneda, params, "Reporte");
    }
}