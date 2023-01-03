package com.pixvs.spring.services;

import com.pixvs.spring.util.StringCheck;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private Environment environment;

    public enum output {
        PDF,
        XLSX,
        WORD
    }

    @Override
    public InputStream generarJasperReport(String rutaReporte, Map<String, Object> params, output tipo, boolean dataSourceConnection) throws JRException, SQLException, IOException {

        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(environment.getProperty("environments.pixvs.reportes.location") + rutaReporte);

        Connection conn = null;
        JasperPrint jasperPrint = null;
        try {
            conn = dataSourceConnection ? dataSource.getConnection() : null;
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        } finally {
            if (conn != null)
                conn.close();
        }

        byte[] content = null;

        switch (tipo) {
            case PDF:
                content = JasperExportManager.exportReportToPdf(jasperPrint);
                break;
            case XLSX:
                break;
            case WORD:
                try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                    Exporter exporter = new JRDocxExporter();
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
                    exporter.exportReport();
                    content = byteArrayOutputStream.toByteArray();
                }
                break;
        }

        return new ByteArrayInputStream(content);
    }

    @Override
    public InputStream generarReporte(String reporte, Map<String, Object> params) throws JRException, SQLException {
        //Adquiere y construye el reporte base
        //InputStream jasperStream = this.getClass().getResourceAsStream(reporte);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile("./src/main/java/com/pixvs/main/jasper" + reporte);

        Connection conn = null;
        JasperPrint jasperPrint = null;
        try {
            conn = dataSource.getConnection();
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        } finally {
            if (conn != null)
                conn.close();
        }

        //Crea el reporte dado el tipo seleccionado
        byte[] content = null;
        content = JasperExportManager.exportReportToPdf(jasperPrint);
        //JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\reporte.pdf");

        return new ByteArrayInputStream(content);
    }

    @Override
    public InputStream generarReporte(String reporte, Map<String, Object> params, String type) throws JRException, SQLException, IOException {

        //Adquiere y construye el reporte base
        InputStream jasperStream = this.getClass().getResourceAsStream(reporte);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        Connection conn = null;
        JasperPrint jasperPrint = null;
        try {
            conn = dataSource.getConnection();
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        } finally {
            if (conn != null)
                conn.close();
        }

        byte[] content = null;

        if (type.equals("XLSX")) {
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setIgnoreGraphics(false);
            configuration.setAutoFitPageHeight(true);
            configuration.setFontSizeFixEnabled(true);
            configuration.setRemoveEmptySpaceBetweenColumns(true);
            configuration.setRemoveEmptySpaceBetweenRows(true);
            configuration.setDetectCellType(true);
            configuration.setShowGridLines(false);
            configuration.setShrinkToFit(true);

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                Exporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
                exporter.setConfiguration(configuration);
                exporter.exportReport();
                content = byteArrayOutputStream.toByteArray();
            }
        } else if (type.equals("DOCX")) {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                Exporter exporter = new JRDocxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
                exporter.exportReport();
                content = byteArrayOutputStream.toByteArray();
            }
        }

        return new ByteArrayInputStream(content);
    }

    @Override
    public void downloadAsZip(HttpServletResponse response, HashMap<String, InputStream> files, String fileName) throws IOException {
        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", fileName));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());

        for (String key : files.keySet()) {
            ZipEntry ze = new ZipEntry(key + (StringCheck.isNullorEmpty(FilenameUtils.getExtension(key)) ? ".pdf" : ""));
            zipOutputStream.putNextEntry(ze);
            byte[] buffer = new byte[4 * 1024];
            int size = 0;

            while ((size = files.get(key).read(buffer)) != -1) {
                zipOutputStream.write(buffer, 0, size);
            }

            zipOutputStream.flush();
            zipOutputStream.closeEntry();
        }

        zipOutputStream.finish();
    }

    /**
     * Genera el jasperPrint desde la carpeta de reportes
     * */
    @Override
    public JasperPrint generarReporteJasperPrint(String reporte, Map<String, Object> params, boolean dataSourceConnection) throws JRException, SQLException{
        JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(environment.getProperty("environments.pixvs.reportes.location") + reporte);

        Connection conn = null;
        JasperPrint jasperPrint = null;
        try {
            conn = dataSourceConnection ? dataSource.getConnection() : null;
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        } finally {
            if (conn != null)
                conn.close();
        }
        return jasperPrint;
    }

    /**
     * Genera el jasperPrint desde los resources
     * */
    @Override
    public JasperPrint generarReporteJasperPrint(String reporte, Map<String, Object> params) throws JRException, SQLException {
        //Adquiere y construye el reporte base
        InputStream jasperStream = this.getClass().getResourceAsStream(reporte);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

        Connection conn = null;
        JasperPrint jasperPrint = null;
        try {
            conn = dataSource.getConnection();
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        } finally {
            if (conn != null)
                conn.close();
        }
        return jasperPrint;
    }

    @Override
    public InputStream unirArchivos(List<JasperPrint> archivos,  ReporteServiceImpl.output type) throws JRException, IOException {
        byte[] content = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(type.equals(output.PDF)){
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(SimpleExporterInput.getInstance(archivos));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setCreatingBatchModeBookmarks(true);
            exporter.setConfiguration(configuration);
            exporter.exportReport();

            content = byteArrayOutputStream.toByteArray();
        }else if(type.equals(output.XLSX)){//xlsx
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, archivos);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
            exporter.exportReport();
            content = byteArrayOutputStream.toByteArray();
        }
        return new ByteArrayInputStream(content);
    }

}
