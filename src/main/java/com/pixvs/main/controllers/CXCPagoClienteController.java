package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.CXCFactura;
import com.pixvs.main.models.CXCPagoDetalle;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaDescargarProjection;
import com.pixvs.main.services.ComplementoPagoService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cxcpago-clientes")
public class CXCPagoClienteController {

    @Autowired
    private HashId hashId;

    @Autowired
    private CXCFacturaDao cxcFacturaDao;

    @Autowired
    private ComplementoPagoService complementoPagoService;

    @Autowired
    private CXCPagoDao cxcPagoDao;

    @Autowired
    private ClienteCuentaBancariaDao clienteCuentaBancariaDao;

    @Autowired
    private CuentaBancariaDao cuentaBancariaDao;

    @Autowired
    private FormaPagoDao formaPagoDao;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private MonedaParidadDao monedaParidadDao;

    @Autowired
    public Environment environment;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getCXCFacturas() throws Exception {
        HashMap<String, Object> json = new HashMap<>();

        json.put("saldos", cxcPagoDao.findListadoSaldosProjected());
        json.put("facturas", cxcPagoDao.findListadoFacturasProjected());

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = {"/listados/", "/listados/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getListados(@PathVariable(required = false) Integer id, ServletRequest req) throws SQLException {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> json = new HashMap<>();

        json.put("saldo", cxcPagoDao.findSaldoProjectedByAlumnoClienteId(id));
        json.put("facturas", cxcPagoDao.findListadoFacturasProjectedByAlumnoClienteId(id));
        json.put("listCuentasBancarias", cuentaBancariaDao.findProjectedComboAllByActivoTrue());
        json.put("listClienteCuentasBancarias", clienteCuentaBancariaDao.findProjectedComboAllByClienteIdAndActivoTrue(id - 2000000));
        json.put("listFormaPago", formaPagoDao.findProjectedComboAllByActivoTrue());
        json.put("listMoneda", monedaDao.findProjectedComboAllByActivoTrue());
        json.put("listMonedasParidad", monedaParidadDao.findAllMonedaParidadComboProjected());
        json.put("fechaSistema", new Date(System.currentTimeMillis()));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(rollbackFor = Exception.class)
    public JsonResponse guardar(@RequestBody List<CXCFactura> facturasTemp, ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        try {
            List<Integer> facturasAllIds = new ArrayList<>();
            List<CXCFactura> facturasGuardar = new ArrayList<>();

            String version = environment.getProperty("environments.pixvs.facturacion-version");

            for (CXCFactura facturaTemp : facturasTemp) {
                String stringDoctosRelacionadosIds = "";

                for (CXCPagoDetalle pagoDetalle : facturaTemp.getPago().getDetalles()) {
                    stringDoctosRelacionadosIds += "|" + pagoDetalle.getDoctoRelacionadoId() + "|";
                }

                CXCFactura factura = cxcFacturaDao.findById(complementoPagoService.spInsertarComplementoPago(
                        facturaTemp.getDatosFacturacionId(),
                        facturaTemp.getSucursalId(),
                        stringDoctosRelacionadosIds,
                        facturaTemp.getFechaCreacion(),
                        usuarioId
                ));

                facturaTemp.getPago().setFacturaId(factura.getId());
                facturaTemp.getPago().setVersion(version.equals("4.0") ? "2.0" : "1.0");
                factura.setPago(facturaTemp.getPago());
                factura.setTimbrar(facturaTemp.isTimbrar());

                facturasAllIds.add(factura.getId());
                facturasGuardar.add(factura);
            }

            cxcFacturaDao.saveAll(facturasGuardar);

            for (CXCFactura factura : facturasGuardar) {
                complementoPagoService.spActualizarEstatusPagoFactura(factura.getId(), usuarioId);
                complementoPagoService.facturar(factura.getId(), factura.isTimbrar());
            }

            return new JsonResponse(cxcFacturaDao.findAllCXCFacturaPagoProjectionByIdIn(facturasAllIds), null, JsonResponse.STATUS_OK);
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/archivos/{facturaId}/{extension}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarArchivos(@PathVariable String facturaId, @PathVariable String extension, HttpServletResponse response) throws Exception {
        try {
            CXCFacturaDescargarProjection factura = cxcFacturaDao.findCXCFacturaDescargarProjectionById(hashId.decode(facturaId));

            String xmlString = complementoPagoService.getHeaderUTF() + "\n" + factura.getXml();
            String reporte = "ComplementoPagoCFDI";

            InputStream inputStream = extension.equalsIgnoreCase("xml") ? new ByteArrayInputStream(xmlString.getBytes()) : complementoPagoService.formatoPDF("/modulos/finanzas/" + reporte + ".jasper", xmlString, null);

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + (!StringCheck.isNullorEmpty(factura.getSerie()) ? factura.getSerie() + " " : "") + factura.getFolio() + "." + extension + "\"");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }
}