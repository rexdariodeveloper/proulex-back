package com.pixvs.spring.controllers;

import com.pixvs.spring.models.ExcelExportadoHoja;
import com.pixvs.spring.util.ExcelFileExporter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ExcelController {

    @Autowired
    @Qualifier("entityManagerFactory")
    private EntityManager em;

    public void downloadXlsx(HttpServletResponse response, String nombreReporte, String query, String[] alColumnas, HashMap<String, Object> hmQueryParameters) throws IOException {
        downloadXlsx(response, nombreReporte, query, alColumnas, hmQueryParameters, null);
    }

    public void downloadXlsxMultipleSheet(HttpServletResponse response, String nombreExcel, ArrayList<ExcelExportadoHoja> alHojas, boolean isMasivo) throws IOException {
        Workbook workbook = isMasivo ? new SXSSFWorkbook() : new XSSFWorkbook();

        for (ExcelExportadoHoja hoja : alHojas) {
            workbook = ExcelFileExporter.listToExcelSheet(
                    workbook,
                    getQueryResultList(hoja.getQuery(), hoja.getQueryParameters()),
                    hoja.getColumnas(),
                    hoja.getTotalesGenerales(),
                    hoja.getColumnasAnchos(),
                    hoja.getNombreHoja(),
                    hoja.isColumnasAnchosAuto(),
                    isMasivo,
                    hoja.getColoresPorValor(),
                    hoja.getColoresPorColumna(),
                    hoja.isImprimirCero(),
                    hoja.isImprimirBorde()
            );
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreExcel + ".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        IOUtils.copy(stream, response.getOutputStream());
    }

    public void downloadXlsx(HttpServletResponse response, String nombreReporte, String query, String[] alColumnas, HashMap<String, Object> hmQueryParameters, String nombreHoja) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        ByteArrayInputStream stream = ExcelFileExporter.listToExcelFile(getQueryResultList(query, hmQueryParameters), alColumnas, nombreHoja);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public void downloadXlsx(HttpServletResponse response, String nombreReporte, String query, String[] alColumnas, String[] totalesGenerales, String[] columnasMoneda, HashMap<String, Object> hmQueryParameters, String nombreHoja) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        ByteArrayInputStream stream = ExcelFileExporter.listToExcelFile(getQueryResultList(query, hmQueryParameters), alColumnas, totalesGenerales, columnasMoneda, nombreHoja);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public void downloadDetailedXlsx(
            HttpServletResponse response,
            String nombreReporte,
            String query,
            String[] titulo,
            HashMap<String, Object> filtros,
            List<String[]> groupColumnas,
            String[] columnas,
            HashMap<String, Object> hmQueryParameters,
            String nombreHoja,
            String groupBy,
            String groupByDisplay,
            String[] totales) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        ByteArrayInputStream stream = ExcelFileExporter.listToExcelFile(titulo, filtros, getQueryResultList(query, hmQueryParameters), groupColumnas, columnas, nombreHoja, groupBy, groupByDisplay, totales, null, null);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public void downloadDetailedWithTotalesXlsx(
            HttpServletResponse response,
            String nombreReporte,
            String query,
            String[] titulo,
            HashMap<String, Object> filtros,
            List<String[]> groupColumnas,
            String[] columnas,
            HashMap<String, Object> hmQueryParameters,
            String nombreHoja,
            String groupBy,
            String groupByDisplay,
            String[] totales,
            String[] totalesGenerales) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        ByteArrayInputStream stream = ExcelFileExporter.listToExcelFile(titulo, filtros, getQueryResultList(query, hmQueryParameters), groupColumnas, columnas, nombreHoja, groupBy, groupByDisplay, totales, totalesGenerales, null);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public ByteArrayInputStream createXlsx(String nombreReporte, String query, String[] alColumnas, HashMap<String, Object> hmQueryParameters) {
        return ExcelFileExporter.listToExcelFile(getQueryResultList(query, hmQueryParameters), alColumnas);
    }

    public void downloadXlsxSXSSF(HttpServletResponse response, String nombreReporte, String query, String[] alColumnas, HashMap<String, Object> hmQueryParameters, String nombreHoja) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        ByteArrayInputStream stream = ExcelFileExporter.listToExcelSXSSF(getQueryResultList(query, hmQueryParameters), alColumnas, nombreHoja);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public void downloadXlsxSXSSF(HttpServletResponse response, String nombreReporte, List<Object> data, String[] alColumnas, String nombreHoja) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreReporte + ".xlsx");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        ByteArrayInputStream stream = ExcelFileExporter.listToExcelSXSSF(data, alColumnas, nombreHoja);
        IOUtils.copy(stream, response.getOutputStream());
    }

    public List getQueryResultList(String query, HashMap<String, Object> hmQueryParameters) {
        Query q = em.createNativeQuery(query);

        if (hmQueryParameters != null) {
            hmQueryParameters.forEach((k, v) -> {
                q.setParameter(k, v);
            });
        }

        return q.getResultList();
    }
}