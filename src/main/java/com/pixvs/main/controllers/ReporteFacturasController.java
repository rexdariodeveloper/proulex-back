package com.pixvs.main.controllers;

import com.pixvs.main.dao.CXCFacturaDao;
import com.pixvs.main.dao.DatosFacturacionDao;
import com.pixvs.main.dao.SucursalDao;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.CMM_CXCF_EstatusFactura;
import com.pixvs.main.models.projections.CXCFactura.CXCFacturaDescargarProjection;
import com.pixvs.main.models.projections.CXCFactura.ReporteFacturasProjection;
import com.pixvs.main.services.FacturaService;
import com.pixvs.spring.config.JwtFilter;
import com.pixvs.spring.controllers.ExcelController;
import com.pixvs.spring.dao.ControlMaestroMultipleDao;
import com.pixvs.spring.models.JsonResponse;
import com.pixvs.spring.util.StringCheck;
import com.pixvs.spring.util.Utilidades;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Rene Carrillo Sánchez on 15/07/2022.
 */
@RestController
@RequestMapping("/api/v1/reporte-facturas")
public class ReporteFacturasController {

    @Autowired
    private SucursalDao sucursalDao;

    @Autowired
    private DatosFacturacionDao datosFacturacionDao;

    @Autowired
    private ControlMaestroMultipleDao controlMaestroMultipleDao;

    @Autowired
    private CXCFacturaDao cxcFacturaDao;

    @Autowired
    ExcelController excelController;

    @Autowired
    private FacturaService facturacionService;

    @Autowired
    private CXCFacturaNotaVentaController facturaNotaVentaController;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getReporte(ServletRequest req) throws Exception {
        int usuarioId = JwtFilter.getUsuarioId((HttpServletRequest) req);

        Integer[] listaCMM = {
                CMM_CXCF_EstatusFactura.ABIERTA,
                CMM_CXCF_EstatusFactura.CANCELADA,
                CMM_CXCF_EstatusFactura.PAGO_PARCIAL,
                CMM_CXCF_EstatusFactura.PAGADA
        };

        HashMap<String, Object> json = new HashMap<>();

        json.put("datos", new ArrayList<>());
        json.put("listaSede", sucursalDao.findProjectedComboAllByUsuarioPermisosId(usuarioId));
        json.put("listaReceptor", datosFacturacionDao.findDatosFacturacionRfcComboProjection());
        json.put("listaEstatus", controlMaestroMultipleDao.findAllByIdInOrderByValor(listaCMM));

        return new JsonResponse(json, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/all/filtros", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getDatosByFiltros(@RequestBody JSONObject json) throws Exception {
        List<Integer> listaSedeId = Utilidades.getListItems(json.get("listaSede"), "id");
        String fechaInicio = (String) json.get("fechaInicio");
        String fechaFin = (String) json.get("fechaFin");
        String notaVenta = (String) json.get("notaVenta");
        List<Integer> listaEstatusId = Utilidades.getListItems(json.get("listaEstatus"), "id");
        List<String> listaReceptorP = Utilidades.getListItems(json.get("listaRecepto"), "rfc");
        String listaReceptor = null;

        if (listaReceptorP != null) {
            listaReceptor = "";

            for (String rfc : listaReceptorP) {
                listaReceptor += "|" + rfc + "|";
            }
        }

        List<ReporteFacturasProjection> listaReporte;

        try {
            listaReporte = cxcFacturaDao.vw_rpt_reporteFacturas(
                    listaSedeId,
                    !StringCheck.isNullorEmpty(fechaInicio) ? fechaInicio : null,
                    !StringCheck.isNullorEmpty(fechaFin) ? fechaFin : null,
                    !StringCheck.isNullorEmpty(listaReceptor) ? listaReceptor : "",
                    !StringCheck.isNullorEmpty(notaVenta) ? notaVenta : "",
                    listaEstatusId
            );
        } catch (Exception ex) {
            return new JsonResponse(null, "Algo está mal con los datos, favor de verificar.", JsonResponse.STATUS_ERROR);
        }

        return new JsonResponse(listaReporte, null, JsonResponse.STATUS_OK);
    }

    @RequestMapping(value = "/exportar/xlsx", method = RequestMethod.POST)
    public void exportXLSX(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> listaSedeId = Utilidades.getListItems(json.get("listaSede"), "id");
        String fechaInicio = json.get("fechaInicio").toString();
        String fechaFin = json.get("fechaFin").toString();
        String notaVenta = (String) json.get("notaVenta");
        List<Integer> listaEstatusId = Utilidades.getListItems(json.get("listaEstatus"), "id");
        List<String> listaReceptorP = Utilidades.getListItems(json.get("listaRecepto"), "rfc");
        String listaReceptor = null;

        if (listaReceptorP != null) {
            listaReceptor = "";

            for (String rfc : listaReceptorP) {
                listaReceptor += "|" + rfc + "|";
            }
        }

        HashMap<String, Object> params = new HashMap<>();

        params.put("listaSedeId", listaSedeId);
        params.put("fechaInicio", !StringCheck.isNullorEmpty(fechaInicio) ? fechaInicio : null);
        params.put("fechaFin", !StringCheck.isNullorEmpty(fechaFin) ? fechaFin : null);
        params.put("listaReceptor", !StringCheck.isNullorEmpty(listaReceptor) ? listaReceptor : "");
        params.put("notaVenta", !StringCheck.isNullorEmpty(notaVenta) ? notaVenta : "");
        params.put("listaEstatusId", listaEstatusId);

        String query =
                "SELECT FechaFactura, \n" +
                "Sede, \n" +
                "TipoFactura, \n" +
                "FolioFactura, \n" +
                "Receptor, \n" +
                "NotaVenta, \n" +
                "Estatus, \n" +
                "Total \n" +
                "FROM VW_RPT_ReporteFacturas \n" +
                "WHERE (COALESCE(:listaSedeId, 0) = 0 OR SucursalId IN (:listaSedeId)) \n" +
                "       AND CONVERT(DATE, FechaFactura) BETWEEN COALESCE(:fechaInicio, '1900-01-01') AND COALESCE(:fechaFin, '2100-12-31') \n" +
                "       AND (COALESCE(:listaReceptor, '') = '' OR :listaReceptor LIKE '%|' + Receptor + '|%') \n" +
                "       AND (COALESCE(:notaVenta, '') = '' OR TodasOV LIKE '%' + :notaVenta + '%') \n " +
                "       AND (COALESCE(:listaEstatusId, 0) = 0 OR EstatusId IN (:listaEstatusId)) \n" +
                "ORDER BY Sede, FolioFactura \n" +
                "OPTION(RECOMPILE)";

        String[] columnas = new String[]{"FECHA FACTURA", "SEDE", "TIPO FACTURA", "FOLIO FACTURA", "RECEPTOR", "NOTA DE VENTA", "ESTATUS", "TOTAL"};
        String[] totales = new String[]{"TOTAL"};
        String[] columnasMoneda = new String[]{"TOTAL"};

        excelController.downloadXlsx(response, "Reporte de Facturas", query, columnas, totales, columnasMoneda, params, "Reporte");
    }

    @RequestMapping(value = "/exportar/facturas", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public JsonResponse exportarFacturas(@RequestBody JSONObject json, HttpServletResponse response, ServletRequest req) throws Exception {
        List<Integer> listaSedeId = Utilidades.getListItems(json.get("listaSede"), "id");
        String fechaInicio = json.get("fechaInicio").toString();
        String fechaFin = json.get("fechaFin").toString();
        String notaVenta = (String) json.get("notaVenta");
        List<Integer> listaEstatusId = Utilidades.getListItems(json.get("listaEstatus"), "id");
        List<String> listaReceptorP = Utilidades.getListItems(json.get("listaRecepto"), "rfc");
        String listaReceptor = null;

        if (listaReceptorP != null) {
            listaReceptor = "";

            for (String rfc : listaReceptorP) {
                listaReceptor += "|" + rfc + "|";
            }
        }

        List<ReporteFacturasProjection> listaReporte;

        try {
            listaReporte = cxcFacturaDao.vw_rpt_reporteFacturas(
                    listaSedeId,
                    !StringCheck.isNullorEmpty(fechaInicio) ? fechaInicio : null,
                    !StringCheck.isNullorEmpty(fechaFin) ? fechaFin : null,
                    !StringCheck.isNullorEmpty(listaReceptor) ? listaReceptor : "",
                    !StringCheck.isNullorEmpty(notaVenta) ? notaVenta : "",
                    listaEstatusId
            );
        } catch (Exception ex) {
            return new JsonResponse(null, "Algo está mal con los datos, favor de verificar.", JsonResponse.STATUS_ERROR);
        }

        if (!listaReporte.isEmpty()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(baos);

            for (ReporteFacturasProjection factura : listaReporte) {
                try {
                    String nombreCarpeta = factura.getReceptor() + "/";

                    // Factura XML
                    InputStream reporte = obtenerFactura(factura.getId(), "xml");
                    ZipEntry ze = new ZipEntry(nombreCarpeta + factura.getFolioFactura() + ".xml");
                    zipOut.putNextEntry(ze);

                    byte[] tmp = new byte[4 * 1024];
                    int size = 0;

                    while ((size = reporte.read(tmp)) != -1) {
                        zipOut.write(tmp, 0, size);
                    }

                    zipOut.flush();
                    reporte.close();

                    // Factura PDF
                    reporte = obtenerFactura(factura.getId(), "pdf");
                    ze = new ZipEntry(nombreCarpeta + factura.getFolioFactura() + ".pdf");
                    zipOut.putNextEntry(ze);
                    tmp = new byte[4 * 1024];
                    size = 0;

                    while ((size = reporte.read(tmp)) != -1) {
                        zipOut.write(tmp, 0, size);
                    }

                    zipOut.flush();
                    reporte.close();
                } catch (Exception e) {
                    System.out.println("Error: no se puede descargar las facturas");
                }
            }

            zipOut.close();

            HashMap<String, Object> archivo = new HashMap<>();
            archivo.put("archivo", baos.toByteArray());
            archivo.put("extension", ".zip");
            archivo.put("extensionArchivo", ".zip");
            archivo.put("nombreArchivo", "Facturas.zip");

            return new JsonResponse(archivo, "", JsonResponse.STATUS_OK);
        } else {
            return new JsonResponse("", "No existen registros.", JsonResponse.STATUS_ERROR);
        }
    }

    private InputStream obtenerFactura(int facturaId, String extension) throws Exception {
        CXCFacturaDescargarProjection factura = cxcFacturaDao.findCXCFacturaDescargarProjectionById(facturaId);

        String xmlString = facturacionService.getHeaderUTF() + "\n" + factura.getXml();
        String reporte = "FacturaCFDI" + (factura.getVersion().equals("4.0") ? "" : "_33");

        return extension.equalsIgnoreCase("xml") ? new ByteArrayInputStream(xmlString.getBytes()) : facturacionService.formatoPDF("/modulos/finanzas/" + reporte + ".jasper", xmlString, null);
    }
}