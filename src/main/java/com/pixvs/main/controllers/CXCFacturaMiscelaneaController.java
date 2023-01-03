package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.CXCFacturaDetalle;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaMiscelaneaEditarProjection;
import com.pixvs.main.services.FacturaService;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.SATUsoCFDIDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cxcfacturas-miscelanea")
public class CXCFacturaMiscelaneaController {

    @Autowired
    private CXCFacturaDao cxcFacturaDao;

    @Autowired
    private DatosFacturacionDao datosFacturacionDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private SATService satService;

    @Autowired
    private FacturaService facturacionService;

    @Autowired
    private SATUsoCFDIDao usoCFDIDao;

    @Autowired
    private UnidadMedidaDao unidadMedidaDao;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private FormaPagoDao formaPagoDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCXCFacturas() throws Exception {
        return new JsonResponse(cxcFacturaDao.findListadoFacturasMiscelaneaProjected(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();

        if (id != null) {
            CXCFacturaMiscelaneaEditarProjection cxcFactura = cxcFacturaDao.findCXCFacturaMiscelaneaEditarProjectionById(id);

            if (cxcFactura == null) {
                return new JsonResponse(null, "La Factura no existe.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
            }

            json.put("factura", cxcFactura);
        }

        json.put("listSedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("listRFCCliente", datosFacturacionDao.findDatosFacturacionComboProjection());
        json.put("listFormaPago", formaPagoDao.findProjectedComboAllByActivoTrue());
        json.put("listMetodoPago", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_CXC_MetodoPago"));
        json.put("listMoneda", monedaDao.findProjectedComboAllByActivoTrue());
        json.put("listUsoCFDI", usoCFDIDao.findAllComboProjectedByActivoTrueOrderByCodigo());
        json.put("listUnidadMedida", unidadMedidaDao.findProjectedComboAllByActivoTrue());
        json.put("listObjetoImpuesto", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_ObjetoImp"));
        json.put("listTipoRelacion", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_TipoRelacion"));
        json.put("listMotivosCancelacion", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_MotivoCancelacion"));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody CXCFactura facturaTemp, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Map<String, Integer> facturasRelacionadas = null;

        if (facturaTemp.getFacturasRelacionadas() != null) {
            facturasRelacionadas = new HashMap<>();

            for (CXCFactura facturaRelacionada : facturaTemp.getFacturasRelacionadas()) {
                facturasRelacionadas.put(facturaRelacionada.getId().toString(), facturaRelacionada.getTipoRelacionId());
            }
        }

        try {
            CXCFactura factura = cxcFacturaDao.findById(facturacionService.spInsertarFacturaMiscelanea(
                    facturaTemp.getFormaPagoId(),
                    facturaTemp.getDiasCredito(),
                    facturaTemp.getMonedaId(),
                    facturaTemp.getMetodoPagoId(),
                    facturaTemp.getReceptorUsoCFDIId(),
                    facturaTemp.getDatosFacturacionId(),
                    facturaTemp.getSucursalId(),
                    usuarioId,
                    facturasRelacionadas
            ));

            if (facturacionService.esVersion4()) {
                for (CXCFacturaDetalle detalle : facturaTemp.getDetalles()) {
                    if (detalle.getObjetoImpuesto() == null) {
                        return new JsonResponse(false, "No es posible insertar la factura. No se ha configurado el Objeto de Impuesto para el artículo [" + detalle.getDescripcion() + "].", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
                    }
                }
            }

            factura.setDetalles(facturaTemp.getDetalles());

            factura = cxcFacturaDao.save(factura);

            int facturaId = factura.getId();
            String xmlTimbrado = facturacionService.facturar(facturaId);

            return new JsonResponse(cxcFacturaDao.findCXCFacturaMiscelaneaEditarProjectionById(facturaId), null, JsonResponse.STATUS_OK);
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @RequestMapping(value = "/validarRFC/{rfc}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse validarRFC(@PathVariable String rfc) throws Exception {
        if (satService.proveedorEnListaNegra(rfc)) {
            return new JsonResponse(false, "El RFC del Cliente se encuentra en la lista negra del SAT.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        return new JsonResponse(true, null, JsonResponse.STATUS_OK);
    }
}