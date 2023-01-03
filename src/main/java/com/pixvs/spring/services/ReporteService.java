package com.pixvs.spring.services;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReporteService {

    InputStream generarJasperReport(String reporte, Map<String, Object> params, ReporteServiceImpl.output tipo, boolean dataSourceConnection) throws JRException, SQLException, IOException;

    InputStream generarReporte(String reporte, Map<String, Object> params) throws JRException, SQLException;

    InputStream generarReporte(String reporte, Map<String, Object> params, String type) throws JRException, SQLException, IOException;

    JasperPrint generarReporteJasperPrint(String reporte, Map<String, Object> params, boolean dataSourceConnection) throws JRException, SQLException;

    JasperPrint generarReporteJasperPrint(String reporte, Map<String, Object> params) throws JRException, SQLException;

    InputStream unirArchivos(List<JasperPrint> archivos, ReporteServiceImpl.output type) throws JRException, IOException;

    void downloadAsZip(HttpServletResponse response, HashMap<String, InputStream> files, String fileName) throws IOException;
}
