package com.pixvs.main.controllers;

import com.pixvs.main.dao.CXPFacturaDao;
import com.pixvs.main.dao.ProveedorDao;
import com.pixvs.main.models.mapeos.MenuPrincipalPermisos;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaReporteProjection;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ArchivoDao;
import com.pixvs.spring.dao.RolMenuPermisoDao;
import com.pixvs.spring.handler.exceptions.AdvertenciaException;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import com.pixvs.spring.services.ReporteService;
import com.pixvs.spring.storage.StorageService;
import com.pixvs.spring.util.HashId;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reporte-cxpfacturas")
public class ReporteCXPFacturasController {

    @Autowired
    private CXPFacturaDao cxpFacturaDao;

    @Autowired
    private ProveedorDao proveedorDao;

    @Autowired
    private RolMenuPermisoDao rolMenuPermisoDao;

    @Autowired
    private ArchivoDao archivoDao;

    @Autowired
    ExcelController excelController;

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private HashId hashId;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatos(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);
        HashMap<String, Object> json = new HashMap<>();

        json.put("proveedores", proveedorDao.findProjectedComboVistaAllByActivoTrue());
        json.put("permisoDescargar", rolMenuPermisoDao.isPermisoByUsuarioIdAndPermisoId(usuarioId, MenuPrincipalPermisos.RPT_FACTURAS_CXP_DESCARGA_MASIVA));
        json.put("permisoExcel", rolMenuPermisoDao.isPermisoByUsuarioIdAndPermisoId(usuarioId, MenuPrincipalPermisos.RPT_FACTURAS_CXP_EXPORTAR_EXCEL));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws Exception {
        return new JsonResponse(getReporte(json), null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        List<Integer> proveedoresId = Utilidades.getListItems(json.get("proveedores"), "id");
        String listaProveedores = null;

        if (proveedoresId != null) {
            listaProveedores = "";

            for (Integer id : proveedoresId) {
                listaProveedores += "|" + id + "|";
            }
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("fechaInicio", !StringCheck.isNullorEmpty(fechaInicio) ? fechaInicio : null);
        params.put("fechaFin", !StringCheck.isNullorEmpty(fechaFin) ? fechaFin : null);
        params.put("proveedores", listaProveedores);

        String query =
                "SELECT fecha,\n" +
                "       proveedor,\n" +
                "       folio,\n" +
                "       monto,\n" +
                "       moneda,\n" +
                "       estatus\n" +
                "FROM VW_ReporteFacturasCXP\n" +
                "WHERE fecha BETWEEN COALESCE(:fechaInicio, '1900-01-01') AND COALESCE(:fechaFin, '2100-12-31')\n" +
                "       AND (COALESCE(:proveedores, '') = '' OR :proveedores LIKE CONCAT('%|', proveedorId, '|%')) \n" +
                "ORDER BY proveedor,\n" +
                "         fecha\n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{"FECHA", "PROVEEDOR", "FOLIO", "MONTO", "MONEDA", "ESTATUS"};
        String[] totales = new String[]{"MONTO"};
        String[] columnasMoneda = new String[]{"MONTO"};

        excelController.downloadXlsx(response, "Reporte de Facturas CXP", query, columnas, totales, columnasMoneda, params, "Reporte");
    }

    @ResponseBody
    @RequestMapping(value = "/archivos/{facturaId}/{extension}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void descargarArchivos(@PathVariable String facturaId, @PathVariable String extension, HttpServletResponse response) throws Exception {
        try {
            CXPFacturaReporteProjection factura = cxpFacturaDao.findCXPFacturaReporteProjectedById(hashId.decode(facturaId));

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + factura.getNombreArchivo() + "." + extension + "\"");
            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

            IOUtils.copy(getFactura(factura, extension), response.getOutputStream());
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/descargar/facturas", method = RequestMethod.POST)
    public void descargarDocumentos(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        try {
            List<CXPFacturaReporteProjection> facturas = getReporte(json);
            HashMap<String, InputStream> archivos = new HashMap<>();

            for (CXPFacturaReporteProjection factura : facturas) {
                if (factura.getXMLId() != null) {
                    archivos.put(factura.getPathArchivo().replace("/", File.separator) + ".xml", getFactura(factura, "xml"));
                }

                if (factura.getPDFId() != null) {
                    archivos.put(factura.getPathArchivo().replace("/", File.separator) + ".pdf", getFactura(factura, "pdf"));
                }
            }

            reporteService.downloadAsZip(response, archivos, "Facturas CXP");
        } catch (Exception ex) {
            throw new AdvertenciaException(ex);
        }
    }

    private List<CXPFacturaReporteProjection> getReporte(JSONObject json) throws Exception {
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        List<Integer> proveedoresId = Utilidades.getListItems(json.get("proveedores"), "id");
        String listaProveedores = null;

        if (proveedoresId != null) {
            listaProveedores = "";

            for (Integer id : proveedoresId) {
                listaProveedores += "|" + id + "|";
            }
        }

        return cxpFacturaDao.findCXPFacturaReporteProjected(
                listaProveedores,
                !StringCheck.isNullorEmpty(fechaInicio) ? fechaInicio : null,
                !StringCheck.isNullorEmpty(fechaFin) ? fechaFin : null
        );
    }

    private InputStream getFactura(CXPFacturaReporteProjection factura, String extension) throws Exception {
        if (extension.equalsIgnoreCase("xml") && factura.getXMLId() == null) {
            throw new AdvertenciaException("La factura no tiene archivo XML.");
        }

        if (extension.equalsIgnoreCase("pdf") && factura.getPDFId() == null) {
            throw new AdvertenciaException("La factura no tiene archivo PDF.");
        }

        ArchivoProjection archivo = archivoDao.findById(extension.equalsIgnoreCase("xml") ? factura.getXMLId() : factura.getPDFId());

        return (storageService.loadAsResource(archivo.getRutaFisica(), archivo.getNombreFisico())).getInputStream();
    }
}