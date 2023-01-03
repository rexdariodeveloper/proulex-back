package com.pixvs.main.controllers;

import com.pixvs.main.dao.CXCFacturaDao;
import com.pixvs.main.dao.DatosFacturacionDao;
import com.pixvs.main.dao.OrdenVentaDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.models.DatosFacturacion;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CXCF_EstatusFactura;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_OV_Estatus;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_RFC_TipoPersona;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaDescargarProjection;
import com.pixvs.main.models.projections.CXCFactura.ListadoAutofacturacionFacturasProjection;
import com.pixvs.main.models.projections.CXCFactura.NotaVentaCXCFacturaEditarProjection;
import com.pixvs.main.models.projections.DatosFacturacion.AutofacturaListadoDatosFacturacionProjection;
import com.pixvs.main.models.projections.DatosFacturacion.DatosFacturacionEditarProjection;
import com.pixvs.main.models.projections.OrdenVenta.FacturacionNotaVentaProjection;
import com.pixvs.main.services.FacturaService;
import com.pixvs.main.services.SATService;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.dao.EstadoDao;
import com.pixvs.spring.dao.SATRegimenFiscalDao;
import com.pixvs.spring.dao.SATUsoCFDIDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.EmailService;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/autofactura")
public class AutoFacturaController {

    @Autowired
    private Environment environment;

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
    private SATUsoCFDIDao usoCFDIDao;

    @Autowired
    private SATRegimenFiscalDao regimenFiscalDao;

    @Autowired
    private EstadoDao estadoDao;

    @Autowired
    private SATService satService;

    @Autowired
    private FacturaService facturacionService;

    @Autowired
    private CXCFacturaNotaVentaController facturaNotaVentaController;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = {"/listados"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(ServletRequest req) throws Exception {
        HashMap<String, Object> json = new HashMap<>();

        json.put("listSedes", sucursalDao.findProjectedComboAllByActivoTrueOrderByNombre());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/datos/{ovId}", "/datos/{ovId}/{datosFacturacionId}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDomicilio(@PathVariable(required = false) Integer ovId, @PathVariable(required = false) Integer datosFacturacionId, ServletRequest req) throws Exception {
        FacturacionNotaVentaProjection ordenVenta = ordenVentaDao.getAutofacturaOV(ovId);
        String validarOV = validarOV(ordenVenta);

        if (validarOV != null) {
            return new JsonResponse(null, validarOV, JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("ordenVenta", ordenVenta);
        json.put("tiposPersona", cmmDao.findAllByControlAndActivoIsTrueOrderByValor(CMM_RFC_TipoPersona.NOMBRE));
        json.put("listRegimenFiscal", regimenFiscalDao.findAllComboProjectedByActivoTrue());
        json.put("estados", estadoDao.findProjectedComboAllByPaisId(1));

        if (datosFacturacionId != null) {
            DatosFacturacionEditarProjection datosFacturacion = datosFacturacionDao.findDatosFacturacionEditarProjectedById(datosFacturacionId);

            if (datosFacturacion == null) {
                return new JsonResponse(null, "No existen los datos para la factura.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
            }

            json.put("datosFacturacion", datosFacturacion);
        }

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/datos/save"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse guardarDatosFacturacion(@RequestBody DatosFacturacion datosFacturacion, ServletRequest req) throws Exception {
        String validarRFC = validarRFC(datosFacturacion.getRfc());

        if (validarRFC != null) {
            return new JsonResponse(false, validarRFC, JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        datosFacturacion.setTipoPersonaId(datosFacturacion.getTipoPersona().getId());
        datosFacturacion.setPaisId(datosFacturacion.getPais() != null ? datosFacturacion.getPais().getId() : 1);
        datosFacturacion.setEstadoId(datosFacturacion.getEstado() != null ? datosFacturacion.getEstado().getId() : null);

        if (datosFacturacion.getMunicipio() != null) {
            datosFacturacion.setMunicipioId(datosFacturacion.getMunicipio().getId());
            datosFacturacion.setCiudad(null);
        } else {
            datosFacturacion.setMunicipioId(null);
        }

        datosFacturacion.setRegimenFiscalId(datosFacturacion.getRegimenFiscal() != null ? datosFacturacion.getRegimenFiscal().getId() : null);

        return new JsonResponse(datosFacturacionDao.save(datosFacturacion).getId(), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/datosNotaVenta/{sedeId}/{folio}/{webId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosNotaVenta(@PathVariable int sedeId, @PathVariable String folio, @PathVariable String webId) throws Exception {
        FacturacionNotaVentaProjection ordenVenta = ordenVentaDao.getAutofacturaOV(sedeId, folio.trim().toUpperCase(), hashId.decode(webId.trim().toUpperCase()));
        String validarOV = validarOV(ordenVenta);

        if (validarOV != null) {
            return new JsonResponse(null, validarOV, JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        return new JsonResponse(hashId.encode(ordenVenta.getId()), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/direccionesRFC/{rfc}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDireccionesRFC(@PathVariable String rfc) throws Exception {
        String validarRFC = validarRFC(rfc);

        if (validarRFC != null) {
            return new JsonResponse(null, validarRFC, JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        List<AutofacturaListadoDatosFacturacionProjection> listadoDirecciones = datosFacturacionDao.findAutoFacturaDatosFacturacionByRFC(rfc);

        if (listadoDirecciones.isEmpty()) {
            return new JsonResponse(null, "El RFC no está registrado. Favor de llenar sus datos fiscales.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        }

        return new JsonResponse(listadoDirecciones, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/facturasRFC/{rfc}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getFacturasRFC(@PathVariable String rfc) throws Exception {
        String validarRFC = validarRFC(rfc);

        if (validarRFC != null) {
            return new JsonResponse(null, validarRFC, JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        List<ListadoAutofacturacionFacturasProjection> listadoFacturas = cxcFacturaDao.findListadoAutofacturasByRFC(rfc);

        if (listadoFacturas.isEmpty()) {
            return new JsonResponse(null, "El RFC no cuenta con facturas registradas.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        }

        return new JsonResponse(listadoFacturas, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/usoCFDI/{ovId}/{datosFacturacionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getUsoCFDI(@PathVariable Integer ovId, @PathVariable Integer datosFacturacionId, ServletRequest req) throws Exception {
        FacturacionNotaVentaProjection ordenVenta = ordenVentaDao.getAutofacturaOV(ovId);
        String validarOV = validarOV(ordenVenta);

        if (validarOV != null) {
            return new JsonResponse(null, validarOV, JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        DatosFacturacionEditarProjection datosFacturacion = datosFacturacionDao.findDatosFacturacionEditarProjectedById(datosFacturacionId);

        if (datosFacturacion == null) {
            return new JsonResponse(null, "No existen los datos para la factura.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        }

        String validarRFC = validarRFC(datosFacturacion.getRfc());

        if (validarRFC != null) {
            return new JsonResponse(false, validarRFC, JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        HashMap<String, Object> json = new HashMap<>();

        json.put("ordenVenta", ordenVenta);
        json.put("datosFacturacion", datosFacturacion);
        json.put("listUsoCFDI", usoCFDIDao.findAllComboProjectedByRegimenFiscalId(datosFacturacion.getRegimenFiscalId()));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/validarFactura/{facturaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosNotaVenta(@PathVariable int facturaId) throws Exception {
        CXCFacturaDescargarProjection factura = cxcFacturaDao.findCXCFacturaDescargarProjectionById(facturaId);

        if (factura == null) {
            return new JsonResponse(null, "La Factura no existe.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        }

        if (factura.getEstatusId() == CMM_CXCF_EstatusFactura.CANCELADA) {
            return new JsonResponse(null, "La Factura está cancelada.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        return new JsonResponse(true, null, JsonResponse.STATUS_OK);
    }

    @ResponseBody
    @RequestMapping(value = "/archivos/{facturaId}/{extension}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarArchivos(@PathVariable String facturaId, @PathVariable String extension, HttpServletResponse response) throws Exception {
        try {
            facturaNotaVentaController.descargarArchivos(facturaId, extension, response);
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody Map<String, Object> json, ServletRequest req) throws Exception {
        return facturaNotaVentaController.guardar(json, null);
    }

    @RequestMapping(value = "/enviarEmail/{facturaId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse enviarEmail(@PathVariable String facturaId) throws Exception {
        NotaVentaCXCFacturaEditarProjection factura = cxcFacturaDao.findNotaVentaCXCFacturaEditarProjectionById(hashId.decode(facturaId));

        if (factura == null) {
            return new JsonResponse(null, "La Factura no existe.", JsonResponse.STATUS_OK_REGISTRO_NO_ENCONTRADO);
        }

        if (factura.getEstatusId() == CMM_CXCF_EstatusFactura.CANCELADA) {
            return new JsonResponse(null, "La Factura está cancelada.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        if (StringCheck.isNullorEmpty(factura.getDatosFacturacion().getCorreoElectronico())) {
            return new JsonResponse(null, "No se configuró el correo para enviar la Factura.", JsonResponse.STATUS_OK_ACCION_NO_DISPONIBLE);
        }

        try {
            String filename = (!StringCheck.isNullorEmpty(factura.getSerie()) ? factura.getSerie() + " " : "") + factura.getFolio();
            String xmlString = facturacionService.getHeaderUTF() + "\n" + factura.getXml();
            String reporte = "FacturaCFDI" + (factura.getVersion().equals("4.0") ? "" : "_33");

            HashMap<String, InputStream> files = new HashMap<>();
            files.put(filename + ".xml", new ByteArrayInputStream(xmlString.getBytes()));
            files.put(filename + ".pdf", facturacionService.formatoPDF("/modulos/finanzas/" + reporte + ".jasper", xmlString, null));

            emailService.sendMessageWithAttachment(
                    factura.getDatosFacturacion().getCorreoElectronico(),
                    environment.getProperty("environments.pixvs.empresa") + " - Factura " + filename,
                    "",
                    files
            );
        } catch (Exception ex) {
            throw new AdvertenciaException("Error al intentar enviar el email.");
        }

        return new JsonResponse(true, null, JsonResponse.STATUS_OK);
    }

    private String validarRFC(String rfc) throws Exception {
        if (satService.proveedorEnListaNegra(rfc)) {
            return "El RFC se encuentra en la lista negra del SAT.";
        }

        return null;
    }

    private String validarOV(FacturacionNotaVentaProjection ordenVenta) {
        if (ordenVenta == null) {
            return "La Nota de Venta no existe.";
        } else if (ordenVenta.getEstatusId().compareTo(CMM_OV_Estatus.PAGADA) != 0) {
            return "La Nota de Venta ya está " + ordenVenta.getEstatus() + ".";
        } else if (ordenVenta.getTotal().compareTo(BigDecimal.ZERO) == 0) {
            return "No es posible facturar una Nota de Venta con monto 0.";
        } else if (!ordenVentaDao.validarAutofacturaDiasFacturarOV(ordenVenta.getId())) {
            return "La Nota de Venta está fuera del periódo permitido para facturar.";
        }

        return null;
    }
}