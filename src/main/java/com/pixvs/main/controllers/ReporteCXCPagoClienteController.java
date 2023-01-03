package com.pixvs.main.controllers;

import com.pixvs.main.dao.*;
import com.pixvs.main.models.projections.CXCPago.ReportePagosCXCPagoProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.dao.ControlMaestroDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reporte-cxcpago-clientes")
public class ReporteCXCPagoClienteController {

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private DatosFacturacionDao datosFacturacionDao;

    @Autowired
    private MonedaDao monedaDao;

    @Autowired
    private FormaPagoDao formaPagoDao;

    @Autowired
    private CuentaBancariaDao cuentaBancariaDao;

    @Autowired
    private CXCPagoDao cxcPagoDao;

    @Autowired
    private ControlMaestroDao controlMaestroDao;

    @Autowired
    private CXCPagoClienteController pagoClienteController;

    @Autowired
    private CXCFacturaNotaVentaController facturaNotaVentaController;

    @Autowired
    private HashId hashId;

    @Autowired
    private Environment environment;

    @Autowired
    private ReporteService reporteService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatos(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        HashMap<String, Object> data = new HashMap<>();

        data.put("datos", new ArrayList<>());
        data.put("sedes", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        data.put("clientes", datosFacturacionDao.findDatosFacturacionClienteComboProjection());
        data.put("monedas", monedaDao.findProjectedComboAllByActivoTrue());
        data.put("formasPago", formaPagoDao.findProjectedComboAllByActivoTrue());
        data.put("cuentas", cuentaBancariaDao.findProjectedComboAllByActivoTrue());

        return new JsonResponse(data, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws Exception {
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        List<Integer> sedeId = Utilidades.getListItems(json.get("sedes"), "id");
        String numeroDocumento = (String) json.get("numeroDocumento");
        Integer monedaId = Utilidades.getItem(json.get("moneda"), "id");
        List<Integer> formaPagoId = Utilidades.getListItems(json.get("formaPago"), "id");
        Integer cuentaId = Utilidades.getItem(json.get("cuenta"), "id");
        List<Integer> clientes = Utilidades.getListItems(json.get("clientes"), "id");
        String listaClientes = null;

        if (clientes != null) {
            listaClientes = "";

            for (Integer id : clientes) {
                listaClientes += "|" + id + "|";
            }
        }

        HashMap<String, Object> datos = new HashMap<>();

        datos.put("pagos", cxcPagoDao.findReportePagos(fechaInicio, fechaFin, sedeId, listaClientes, numeroDocumento, monedaId, formaPagoId, cuentaId));
        datos.put("facturas", cxcPagoDao.findReportePagosFacturas(fechaInicio, fechaFin, sedeId, listaClientes, numeroDocumento, monedaId, formaPagoId, cuentaId));

        return new JsonResponse(datos, null, JsonResponse.STATUS_OK);
    }

    @ResponseBody
    @RequestMapping(value = "/archivos/pago/{pagoId}/{extension}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarArchivosPago(@PathVariable String pagoId, @PathVariable String extension, HttpServletResponse response) throws Exception {
        try {
            ReportePagosCXCPagoProjection pago = cxcPagoDao.findReportePagoById(hashId.decode(pagoId));

            if (extension.equalsIgnoreCase("xml") && pago.getUuid() == null) {
                throw new AdvertenciaException("El pago no fue timbrado.");
            }

            pagoClienteController.descargarArchivos(hashId.encode(pago.getFacturaId()), extension, response);
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/archivos/pago-detalle/{pagoDetalleId}/{extension}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarArchivosPagoDetalle(@PathVariable String pagoDetalleId, @PathVariable String extension, HttpServletResponse response) throws Exception {
        try {
            facturaNotaVentaController.descargarArchivos(hashId.encode(cxcPagoDao.findReportePagosFacturaById(hashId.decode(pagoDetalleId)).getFacturaId()), extension, response);
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @RequestMapping(value = "/xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response) throws Exception {
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        List<Integer> sedeId = Utilidades.getListItems(json.get("sedes"), "id");
        String numeroDocumento = (String) json.get("numeroDocumento");
        Integer monedaId = Utilidades.getItem(json.get("moneda"), "id");
        List<Integer> formaPagoId = Utilidades.getListItems(json.get("formaPago"), "id");
        Integer cuentaId = Utilidades.getItem(json.get("cuenta"), "id");
        List<Integer> clientes = Utilidades.getListItems(json.get("clientes"), "id");
        String listaClientes = null;

        if (clientes != null) {
            listaClientes = "";

            for (Integer id : clientes) {
                listaClientes += "|" + id + "|";
            }
        }

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("path", environment.getProperty("environments.pixvs.front.url"));
        parameters.put("empresa", controlMaestroDao.findCMByNombre("CM_NOMBRE_EMPRESA").getValor());

        parameters.put("fechaInicio", fechaInicio);
        parameters.put("fechaFin", fechaFin);
        parameters.put("sedes", sedeId);
        parameters.put("clientes", listaClientes);
        parameters.put("documento", numeroDocumento);
        parameters.put("moneda", monedaId);
        parameters.put("allFormaPago", formaPagoId == null || formaPagoId.isEmpty());
        parameters.put("formaPago", formaPagoId);
        parameters.put("cuenta", cuentaId);

        InputStream reporte = reporteService.generarReporte("/pagoClientes/pagoClientes.jasper", parameters, "XLSX");

        response.setContentType(MediaType.parseMediaType("application/vnd.ms-excel").getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=PagoClientes.xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(reporte, response.getOutputStream());

        response.flushBuffer();
    }
}