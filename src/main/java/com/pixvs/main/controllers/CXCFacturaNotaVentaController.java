package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.CXCPagoDetalle;
import com.pixvs.main.models.OrdenVenta;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.CXCFactura.CFDIFacturaProjection;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaDescargarProjection;
import com.pixvs.main.models.projections.CXCFactura.NotaVentaCXCFacturaEditarProjection;
import com.pixvs.main.models.projections.OrdenVenta.FacturacionNotaVentaProjection;
import com.pixvs.main.services.FacturaService;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.SATUsoCFDIDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.handler.exceptions.ErrorConDatosException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/cxcfacturas-nota-venta")
public class CXCFacturaNotaVentaController {

    @Autowired
    private HashId hashId;

    @Autowired
    private CXCFacturaDao cxcFacturaDao;

    @Autowired
    private ControlMaestroMultipleDao cmmDao;

    @Autowired
    private DatosFacturacionDao datosFacturacionDao;

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private OrdenVentaDao ordenVentaDao;

    @Autowired
    private OrdenVentaDetalleDao ordenVentaDetalleDao;

    @Autowired
    private SATService satService;

    @Autowired
    private FacturaService facturacionService;

    @Autowired
    private SATUsoCFDIDao usoCFDIDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCXCFacturas() throws Exception {
        return new JsonResponse(cxcFacturaDao.findListadoFacturasNotaVentaProjected(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();

        if (id != null) {
            NotaVentaCXCFacturaEditarProjection cxcFactura = cxcFacturaDao.findNotaVentaCXCFacturaEditarProjectionById(id);

            if (cxcFactura == null) {
                return new JsonResponse(null, "La Factura no existe.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
            }

            json.put("factura", cxcFactura);
            json.put("detalles", ordenVentaDao.findAllFacturacionOrdenVentaProjectedByFacturaId(cxcFactura.getId()));
        }

        json.put("listRFCCliente", datosFacturacionDao.findDatosFacturacionComboProjection());
        json.put("listUsoCFDI", usoCFDIDao.findAllComboProjectedByActivoTrueOrderByCodigo());
        json.put("listSedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("listTipoRelacion", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_TipoRelacion"));
        json.put("listMotivosCancelacion", cmmDao.findAllComboSimpleProjectionByControlAndActivoIsTrueOrderByReferencia("CMM_SAT_MotivoCancelacion"));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Map<String, Object> json, ServletRequest req) throws Exception {
        int usuarioId = req != null ? JwtFilter.getUsuarioId((HttpServletRequest) req) : -1;

        Integer datosFacturacionId = (Integer) json.get("datosFacturacionId");
        int usoCFDIId = (int) json.get("usoCFDIId");
        int sucursalId = (int) json.get("sucursalId");
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
            int facturaId = facturacionService.spInsertarFacturaNotaVenta(datosFacturacionId, usoCFDIId, sucursalId, stringOrdenesVentaId, usuarioId, facturasRelacionadas);
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

    @RequestMapping(value = "/validarRFC/{rfc}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse validarRFC(@PathVariable String rfc) throws Exception {
        if (satService.proveedorEnListaNegra(rfc)) {
            return new JsonResponse(false, "El RFC del Cliente se encuentra en la lista negra del SAT.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        return new JsonResponse(true, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datos/notaVenta/{sedeId}/{notaVenta}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosNotaVenta(@PathVariable int sedeId, @PathVariable String notaVenta) {
        FacturacionNotaVentaProjection ordenVenta = ordenVentaDao.findFacturacionNotaVentaProjectedBySucursalIdAndCodigo(sedeId, notaVenta.trim());

        if (ordenVenta == null) {
            return new JsonResponse(null, "La Nota de Venta no existe.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        } else if (ordenVenta.getEstatusId().compareTo(ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA) != 0) {
            return new JsonResponse(null, "La Nota de Venta tiene estatus [" + ordenVenta.getEstatus() + "].", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        } else if (ordenVenta.getTotal().compareTo(BigDecimal.ZERO) == 0) {
            return new JsonResponse(null, "No es posible agregar una Nota de Venta con Total en 0.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        HashMap<String, Object> json = new HashMap<>();
        json.put("notaVenta", ordenVenta);
        json.put("ordenVenta", ordenVentaDao.findProjectedHistorialPVResumenById(ordenVenta.getId()));
        json.put("detalles", ordenVentaDetalleDao.findProjectedHistorialPVResumenByOrdenVentaId(ordenVenta.getId()));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/datos/factura-relacion/{sedeId}/{factura}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosFacturaRelacion(@PathVariable int sedeId, @PathVariable String factura) {
        CFDIFacturaProjection cxcFactura = cxcFacturaDao.findCFDIFacturaProjectedBySucursalIdAndFolio(sedeId, factura.trim());

        if (cxcFactura == null) {
            return new JsonResponse(null, "La Factura no existe.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        } else if (cxcFactura.getEstatusId() == ControlesMaestrosMultiples.CMM_CXCF_EstatusFactura.CANCELADA) {
            return new JsonResponse(null, "La Factura está cancelada.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        } else if (cxcFactura.getFacturaRelacionadaId() != null) {
            return new JsonResponse(null, "La Factura ya está relacionada a otro CFDI.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        HashMap<String, Object> json = new HashMap<>();
        json.put("factura", cxcFactura);

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @ResponseBody
    @RequestMapping(value = "/archivos/{facturaId}/{extension}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarArchivos(@PathVariable String facturaId, @PathVariable String extension, HttpServletResponse response) throws Exception {
        try {
            CXCFacturaDescargarProjection factura = cxcFacturaDao.findCXCFacturaDescargarProjectionById(hashId.decode(facturaId));

            String xmlString = facturacionService.getHeaderUTF() + "\n" + factura.getXml();
            String reporte = "FacturaCFDI" + (factura.getVersion().equals("4.0") ? "" : "_33");

            InputStream inputStream = extension.equalsIgnoreCase("xml") ? new ByteArrayInputStream(xmlString.getBytes()) : facturacionService.formatoPDF("/modulos/finanzas/" + reporte + ".jasper", xmlString, null);

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (!StringCheck.isNullorEmpty(factura.getSerie()) ? factura.getSerie() + " " : "") + factura.getFolio() + "." + extension + "\"");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/previsualizar", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getFacturaTemp(@RequestBody HashMap<String, Object> body, HttpServletResponse response) throws Exception {
        try {
            String xmlString = (String) body.get("xmlString");

            xmlString = facturacionService.getHeaderUTF() + "\n" + xmlString;
            String reporte = "FacturaCFDI" + (facturacionService.esVersion4() ? "" : "_33");

            InputStream inputStream = facturacionService.formatoPDF("/modulos/finanzas/" + reporte + ".jasper", xmlString, null);

            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "Factura.pdf" + "\"");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @RequestMapping(value = {"/cancelar/{facturaId}/{motivoCancelacionId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse cancelarFactura(@PathVariable String facturaId, @PathVariable int motivoCancelacionId, ServletRequest req) {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        Date fecha = new Date(System.currentTimeMillis());
        int intFacturaId = hashId.decode(facturaId);

        CXCFactura cxcFactura = cxcFacturaDao.findById(intFacturaId);

        if (cxcFactura.getTipoRegistroId() == ControlesMaestrosMultiples.CMM_CXCF_TipoRegistro.FACTURA_MISCELANEA
                && cxcFactura.getEstatusId() != ControlesMaestrosMultiples.CMM_CXCF_EstatusFactura.ABIERTA) {
            List<String> listadoPagos = new ArrayList<>();

            for (CXCPagoDetalle pagoDetalle : cxcFactura.getPagos()) {
                CXCFactura facturaPago = pagoDetalle.getPago().getFactura();
                String folioPago = (facturaPago.getSerie() != null ? facturaPago.getSerie() + " " : "") + facturaPago.getFolio();

                if (!listadoPagos.contains(folioPago)) {
                    listadoPagos.add(folioPago);
                }
            }
            return new JsonResponse(null, "No es posible cancelar. La Factura tiene un pago relacionado [" + String.join(", ", listadoPagos) + "]", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        } else if (cxcFactura.getEstatusId() == ControlesMaestrosMultiples.CMM_CXCF_EstatusFactura.CANCELADA) {
            return new JsonResponse(null, "No es posible cancelar. La Factura ya ha sido cancelada.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        cxcFactura.setEstatusId(ControlesMaestrosMultiples.CMM_CXCF_EstatusFactura.CANCELADA);
        cxcFactura.setFechaCancelacion(fecha);
        cxcFactura.setMotivoCancelacionId(motivoCancelacionId);
        cxcFactura.setModificadoPorId(usuarioId);
        cxcFactura.setFechaModificacion(fecha);

        cxcFacturaDao.save(cxcFactura);

        List<OrdenVenta> ordenesVenta = ordenVentaDao.findAllByFacturaId(intFacturaId);

        if (ordenesVenta != null && !ordenesVenta.isEmpty()) {
            for (OrdenVenta ordenVenta : ordenesVenta) {
                ordenVenta.setEstatusId(ControlesMaestrosMultiples.CMM_OV_Estatus.PAGADA);
                ordenVenta.setModificadoPorId(usuarioId);
                ordenVenta.setFechaModificacion(fecha);
            }

            ordenVentaDao.saveAll(ordenesVenta);
        }

        return new JsonResponse(null, null, JsonResponse.STATUS_OK);
    }
}