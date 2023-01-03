package com.pixvs.spring.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class ExcelFileExporter {

    public static String columnsToString(String[] alColumnas) {
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < alColumnas.length; i++) {
            texto.append(",").append(alColumnas[i]);
        }

        return texto.toString().replaceFirst(",", "");
    }

    public static String columnsToStringComillas(String[] alColumnas) {
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < alColumnas.length; i++) {
            texto.append(",").append("\"").append(alColumnas[i]).append("\"");
        }

        return texto.toString().replaceFirst(",", "");
    }

    public static Workbook listToExcelSheet(Workbook workbook,
                                            List<Object> data,
                                            String[] columnas,
                                            String[] totalesGenerales,
                                            int[] alColumnasAncho,
                                            String nombreHoja,
                                            Boolean autoSizeColumn,
                                            Boolean isMasivo,
                                            HashMap<String, Short> coloresPorValor,
                                            HashMap<String, Short> coloresPorColumna,
                                            boolean imprimirCero,
                                            boolean imprimirBorde) {
        Map<String, BigDecimal> sumaTotalesGenerales = new HashMap<>();

        if (totalesGenerales != null) {
            for (int i = 0; i < totalesGenerales.length; i++) {
                sumaTotalesGenerales.put(totalesGenerales[i], BigDecimal.ZERO);
            }
        }

        Font fontBold = workbook.createFont();
        fontBold.setBold(true);

        Sheet sheet = workbook.createSheet(nombreHoja != null ? nombreHoja : "Hoja");

        Row row = sheet.createRow(0);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        headerCellStyle.setFont(font);
        headerCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

        CellStyle cellStyleDate = workbook.createCellStyle();
        CreationHelper createHelperDate = workbook.getCreationHelper();
        cellStyleDate.setDataFormat(createHelperDate.createDataFormat().getFormat("m/d/yy"));
        cellStyleDate.setAlignment(HorizontalAlignment.CENTER);

        CellStyle cellStyleText = workbook.createCellStyle();
        cellStyleText.setAlignment(HorizontalAlignment.CENTER);

        CellStyle cellStyleAccent = workbook.createCellStyle();
        cellStyleAccent.setFont(fontBold);
        cellStyleAccent.setAlignment(HorizontalAlignment.LEFT);

        if (imprimirBorde) {
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);

            cellStyleDate.setBorderRight(BorderStyle.THIN);
            cellStyleDate.setBorderLeft(BorderStyle.THIN);
            cellStyleDate.setBorderTop(BorderStyle.THIN);
            cellStyleDate.setBorderBottom(BorderStyle.THIN);

            cellStyleText.setBorderRight(BorderStyle.THIN);
            cellStyleText.setBorderLeft(BorderStyle.THIN);
            cellStyleText.setBorderTop(BorderStyle.THIN);
            cellStyleText.setBorderBottom(BorderStyle.THIN);

            cellStyleAccent.setBorderRight(BorderStyle.THIN);
            cellStyleAccent.setBorderLeft(BorderStyle.THIN);
            cellStyleAccent.setBorderTop(BorderStyle.THIN);
            cellStyleAccent.setBorderBottom(BorderStyle.THIN);
        }

        // Creating header
        Cell cell;

        for (int i = 0; i < columnas.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(columnas[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Creating data rows for each customer
        for (int i = 0; i < data.size(); i++) {
            Row dataRow = sheet.createRow(i + 1);
            Object valor;

            for (int e = 0; e < columnas.length; e++) {
                valor = ((Object[]) data.get(i))[e];

                CellStyle cellStylePorValor = null;
                CellStyle cellStylePorColumna = null;

                if (coloresPorValor != null) {
                    try {
                        if (coloresPorValor.containsKey(valor.toString())) {
                            cellStylePorValor = workbook.createCellStyle();
                            cellStylePorValor.setFillForegroundColor(coloresPorValor.get(valor.toString()));
                            cellStylePorValor.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                            if (imprimirBorde) {
                                headerCellStyle.setBorderRight(BorderStyle.THIN);
                                headerCellStyle.setBorderLeft(BorderStyle.THIN);
                                headerCellStyle.setBorderTop(BorderStyle.THIN);
                                headerCellStyle.setBorderBottom(BorderStyle.THIN);
                            }
                        } else {
                            Integer defaultKey = null;

                            try {
                                defaultKey = Integer.parseInt(valor.toString());
                            } catch (Exception ex1) {}

                            if (defaultKey != null) {
                                cellStylePorValor = workbook.createCellStyle();
                                cellStylePorValor.setFillForegroundColor(coloresPorValor.get("-1"));
                                cellStylePorValor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            }
                        }
                    } catch (Exception ex) {}

                    if (imprimirBorde && cellStylePorValor != null) {
                        cellStylePorValor.setBorderRight(BorderStyle.THIN);
                        cellStylePorValor.setBorderLeft(BorderStyle.THIN);
                        cellStylePorValor.setBorderTop(BorderStyle.THIN);
                        cellStylePorValor.setBorderBottom(BorderStyle.THIN);
                    }
                } else if (coloresPorColumna != null) {
                    if (coloresPorColumna.containsKey(columnas[e])) {
                        cellStylePorColumna = workbook.createCellStyle();
                        cellStylePorColumna.setFillForegroundColor(coloresPorColumna.get(columnas[e]));
                        cellStylePorColumna.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    } else {
                        Integer defaultKey = null;

                        try {
                            defaultKey = Integer.parseInt(columnas[e]);
                        } catch (Exception ex) {}

                        if (defaultKey != null) {
                            cellStylePorColumna = workbook.createCellStyle();
                            cellStylePorColumna.setFillForegroundColor(coloresPorColumna.get("-1"));
                            cellStylePorColumna.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        }
                    }

                    if (imprimirBorde && cellStylePorColumna != null) {
                        cellStylePorColumna.setBorderRight(BorderStyle.THIN);
                        cellStylePorColumna.setBorderLeft(BorderStyle.THIN);
                        cellStylePorColumna.setBorderTop(BorderStyle.THIN);
                        cellStylePorColumna.setBorderBottom(BorderStyle.THIN);
                    }
                }

                if (valor != null && Integer.class.equals(valor.getClass())) {
                    int value = (Integer) valor;

                    cell = dataRow.createCell(e);
                    cell.setCellStyle(cellStylePorValor != null ? cellStylePorValor : cellStylePorColumna != null ? cellStylePorColumna : cellStyleText);

                    if (imprimirCero || value != 0) {
                        cell.setCellValue(value);
                    }

                    if (sumaTotalesGenerales.get(columnas[e]) != null) {
                        BigDecimal t = sumaTotalesGenerales.get(columnas[e]).add(BigDecimal.valueOf(value));
                        sumaTotalesGenerales.replace(columnas[e], t);
                    }
                } else if (valor != null && BigDecimal.class.equals(valor.getClass())) {
                    cell = dataRow.createCell(e);
                    cell.setCellValue(((BigDecimal) valor).doubleValue());
                    cell.setCellStyle(cellStyleText);

                    if (sumaTotalesGenerales.get(columnas[e]) != null) {
                        BigDecimal t = sumaTotalesGenerales.get(columnas[e]).add((BigDecimal) valor);
                        sumaTotalesGenerales.replace(columnas[e], t);
                    }
                } else if (valor != null && Timestamp.class.equals(valor.getClass())) {
                    cell = dataRow.createCell(e);
                    cell.setCellValue((Timestamp) valor);
                    cell.setCellStyle(cellStyleDate);
                } else if (valor != null && Date.class.equals(valor.getClass())) {
                    cell = dataRow.createCell(e);
                    cell.setCellValue((Date) valor);
                    cell.setCellStyle(cellStyleDate);
                } else if (valor != null && java.sql.Date.class.equals(valor.getClass())) {
                    cell = dataRow.createCell(e);
                    cell.setCellValue((java.sql.Date) valor);
                    cell.setCellStyle(cellStyleDate);
                } else {
                    try {
                        cell = dataRow.createCell(e);
                        cell.setCellValue(valor.toString());
                        cell.setCellStyle(cellStyleText);
                    } catch (Exception ex) {
                    }
                }

            }
        }

        if (totalesGenerales != null) {
            Row totalesRow = sheet.createRow(data.size() + 1);
            Integer firstColumn = findIndex(columnas, totalesGenerales[0]);

            if (firstColumn != -1) {
                cell = totalesRow.createCell(firstColumn - 1);
                cell.setCellValue("TOTAL");
                cell.setCellStyle(cellStyleAccent);
            }

            for (int i = 0; i < totalesGenerales.length; i++) {
                Integer index = findIndex(columnas, totalesGenerales[i]);

                if (index != -1) {
                    cell = totalesRow.createCell(index);
                    cell.setCellValue(sumaTotalesGenerales.get(totalesGenerales[i]).doubleValue());
                    cell.setCellStyle(cellStyleAccent);
                }
            }
        }

        sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, columnas.length - 1));
        sheet.createFreezePane(0, 1);

        // Making size of column auto resize to fit with data
        for (int i = 0; i < columnas.length; i++) {
            if (autoSizeColumn && !isMasivo) {
                sheet.autoSizeColumn(i);
            } else {
                sheet.setColumnWidth(i, alColumnasAncho != null ? alColumnasAncho[i] : 4000);
            }
        }

        return workbook;
    }

    public static ByteArrayInputStream listToExcelFile(List<Object> data, String[] alColumnas) {
        return listToExcelFile(data, alColumnas, null);
    }

    public static ByteArrayInputStream listToExcelFile(List<Object> data, String[] alColumnas, String nombreHoja) {
        return listToExcelFile(null, null, data, null, alColumnas, nombreHoja, null, null, null, null, null);
    }

    public static ByteArrayInputStream listToExcelFile(List<Object> data, String[] alColumnas, String[] totalesGenerales, String[] columnasMoneda, String nombreHoja) {
        return listToExcelFile(null, null, data, null, alColumnas, nombreHoja, null, null, null, totalesGenerales, columnasMoneda);
    }

    public static ByteArrayInputStream listToExcelFile(
            String[] titulo,
            HashMap<String, Object> filtros,
            List<Object> data,
            List<String[]> groupColumnas,
            String[] columnas,
            String nombreHoja,
            String groupBy,
            String groupByDisplay,
            String[] totales,
            String[] totalesGenerales,
            String[] columnasMoneda) {

        if (nombreHoja == null) {
            nombreHoja = "Customers";
        }

        try (Workbook workbook = new XSSFWorkbook()) {

            //Fonts and styles
            Font fontWhite = workbook.createFont();
            fontWhite.setColor(IndexedColors.WHITE.getIndex());

            Font fontWhiteBold = workbook.createFont();
            fontWhiteBold.setColor(IndexedColors.WHITE.getIndex());
            fontWhiteBold.setBold(true);

            Font fontBold = workbook.createFont();
            fontBold.setBold(true);

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
            titleStyle.setFont(fontBold);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setFont(fontWhite);
            headerCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle cellStyleDate = workbook.createCellStyle();
            CreationHelper createHelperDate = workbook.getCreationHelper();
            cellStyleDate.setDataFormat(createHelperDate.createDataFormat().getFormat("m/d/yy"));
            cellStyleDate.setAlignment(HorizontalAlignment.CENTER);

            CellStyle cellStyleCurrency = workbook.createCellStyle();
            CreationHelper createHelperCurrency = workbook.getCreationHelper();
            cellStyleCurrency.setDataFormat(createHelperCurrency.createDataFormat().getFormat("$#,##0.00"));
            cellStyleCurrency.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle cellStyleCurrencyAccent = workbook.createCellStyle();
            CreationHelper createHelperCurrencyAccent = workbook.getCreationHelper();
            cellStyleCurrencyAccent.setDataFormat(createHelperCurrencyAccent.createDataFormat().getFormat("$#,##0.00"));
            cellStyleCurrencyAccent.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleCurrencyAccent.setFont(fontBold);

            CellStyle cellStyleText = workbook.createCellStyle();
            cellStyleText.setAlignment(HorizontalAlignment.CENTER);

            CellStyle cellStyleAccent = workbook.createCellStyle();
            cellStyleAccent.setFont(fontBold);
            cellStyleAccent.setAlignment(HorizontalAlignment.LEFT);

            CellStyle cellStyleGroupHeader = workbook.createCellStyle();
            cellStyleGroupHeader.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellStyleGroupHeader.setFont(fontWhiteBold);
            cellStyleGroupHeader.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
            cellStyleGroupHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleGroupHeader.setAlignment(HorizontalAlignment.CENTER);
            cellStyleGroupHeader.setBorderTop(BorderStyle.MEDIUM);
            cellStyleGroupHeader.setBorderBottom(BorderStyle.MEDIUM);

            Sheet sheet = workbook.createSheet(nombreHoja);
            Integer rowCount = 0;
            Cell cell = null;

            Integer columnsSize = columnas.length;

            if (groupByDisplay != null) {
                columnsSize = columnsSize - 1;
            }

            if (titulo != null) {
                for (String texto : titulo) {
                    Row rowTitle = sheet.createRow(rowCount);
                    sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, columnsSize - 1));
                    cell = rowTitle.createCell(0);
                    cell.setCellValue(texto);
                    cell.setCellStyle(titleStyle);

                    rowCount++;
                }

                rowCount++;
            }

            if (filtros != null) {
                Iterator it = filtros.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    Row rowFiltros = sheet.createRow(rowCount);
                    cell = rowFiltros.createCell(0);
                    cell.setCellValue((String) pair.getKey());
                    cell.setCellStyle(cellStyleAccent);

                    cell = rowFiltros.createCell(1);
                    String value = (String) pair.getValue();

                    if (value != null && !value.isEmpty()) {
                        value = value.replace('|', ',');
                        Integer items = value.split(",").length / 2;
                        cell.setCellValue(items > 0 ? (items.toString() + " elementos") : value);
                    } else {
                        cell.setCellValue("Todos");
                    }

                    cell.setCellStyle(titleStyle);

                    rowCount++;
                }

                rowCount++;
            }

            if (groupColumnas != null) {
                Row groupRow = sheet.createRow(rowCount);

                for (String[] grupo : groupColumnas) {
                    Integer firstIndex = findIndex(columnas, grupo[1]);
                    Integer lastIndex = findIndex(columnas, grupo[2]);

                    for (Integer i = firstIndex; i <= lastIndex; i++) {
                        cell = groupRow.createCell(i);

                        if (i == firstIndex) {
                            cell.setCellValue(grupo[0]);
                        }

                        cellStyleGroupHeader.setBorderLeft(BorderStyle.MEDIUM);
                        cellStyleGroupHeader.setBorderRight(BorderStyle.MEDIUM);
                        cell.setCellStyle(cellStyleGroupHeader);
                    }

                    if (firstIndex < lastIndex) {
                        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, firstIndex, lastIndex));
                    }

                }

                rowCount++;
            }

            Row row = sheet.createRow(rowCount);

            // Creating header
            for (int i = 0; i < columnsSize; i++) {
                cell = row.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);
            }

            rowCount++;

            sheet.createFreezePane(0, rowCount);

            Integer groupIndex = -1;
            if (groupBy != null) {
                groupIndex = findIndex(columnas, groupBy);
            }

            Map<String, BigDecimal> sumaTotales = new HashMap<>();
            if (totales != null) {
                for (int i = 0; i < totales.length; i++) {
                    sumaTotales.put(totales[i], BigDecimal.ZERO);
                }
            }

            Map<String, BigDecimal> sumaTotalesGenerales = new HashMap<>();
            if (totalesGenerales != null) {
                for (int i = 0; i < totalesGenerales.length; i++) {
                    sumaTotalesGenerales.put(totalesGenerales[i], BigDecimal.ZERO);
                }
            }

            // Creating data rows for each customer
            for (int i = 0; i < data.size(); i++) {
                //Si es agrupado y se muestra el valor
                if (groupBy != null && groupByDisplay != null) {
                    String anterior = "";
                    String actual = ((Object[]) data.get(i))[groupIndex].toString();

                    if (i > 0) {
                        anterior = ((Object[]) data.get(i - 1))[groupIndex].toString();
                    }

                    if (!actual.equals(anterior)) {
                        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, columnsSize - 1));
                        rowCount++;
                        Row grupo = sheet.createRow(rowCount);
                        cell = grupo.createCell(0);
                        cell.setCellValue(groupByDisplay + ": " + actual);
                        cell.setCellStyle(cellStyleAccent);
                        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, columnsSize - 1));
                        rowCount++;
                    }
                }

                Row dataRow = sheet.createRow(rowCount);
                Object valor;

                for (int e = 0; e < columnsSize; e++) {
                    valor = ((Object[]) data.get(i))[e];

                    if (valor != null) {
                        cell = dataRow.createCell(e);

                        if (valor.getClass() == String.class) {
                            cell.setCellValue((String) valor);
                            cell.setCellStyle(cellStyleText);
                        } else if (valor.getClass() == BigDecimal.class) {
                            boolean isCurrency = columnasMoneda != null && ArrayUtils.contains(columnasMoneda, columnas[e]);

                            cell.setCellValue(((BigDecimal) valor).doubleValue());
                            cell.setCellStyle(isCurrency ? cellStyleCurrency : cellStyleText);

                            if (sumaTotales.get(columnas[e]) != null) {
                                BigDecimal t = sumaTotales.get(columnas[e]).add((BigDecimal) valor);
                                sumaTotales.replace(columnas[e], t);
                            }

                            if (sumaTotalesGenerales.get(columnas[e]) != null) {
                                BigDecimal t = sumaTotalesGenerales.get(columnas[e]).add((BigDecimal) valor);
                                sumaTotalesGenerales.replace(columnas[e], t);
                            }
                        } else if (valor.getClass() == Integer.class) {
                            cell.setCellValue((Integer) valor);
                            cell.setCellStyle(cellStyleText);

                            if (sumaTotales.get(columnas[e]) != null) {
                                BigDecimal t = sumaTotales.get(columnas[e]).add(BigDecimal.valueOf((Integer) valor));
                                sumaTotales.replace(columnas[e], t);
                            }

                            if (sumaTotalesGenerales.get(columnas[e]) != null) {
                                BigDecimal t = sumaTotalesGenerales.get(columnas[e]).add(BigDecimal.valueOf((Integer) valor));
                                sumaTotalesGenerales.replace(columnas[e], t);
                            }
                        } else if (valor.getClass() == java.sql.Date.class) {
                            cell.setCellValue((java.sql.Date) valor);
                            cell.setCellStyle(cellStyleDate);
                        } else if (valor.getClass() == Date.class) {
                            cell.setCellValue((Date) valor);
                            cell.setCellStyle(cellStyleDate);
                        } else if (valor.getClass() == Timestamp.class) {
                            cell.setCellValue((Timestamp) valor);
                            cell.setCellStyle(cellStyleDate);
                        }
                    }

                    if (groupByDisplay != null) {
                        CellStyle aux = cell.getCellStyle();
                        aux.setBorderRight(BorderStyle.THIN);
                        aux.setBorderLeft(BorderStyle.THIN);
                        aux.setBorderTop(BorderStyle.THIN);
                        aux.setBorderBottom(BorderStyle.THIN);

                        cell.setCellStyle(aux);
                    }
                }

                rowCount++;

                //Calculo de totales
                if (totales != null) {
                    String actual = ((Object[]) data.get(i))[groupIndex].toString();
                    String siguiente = "";

                    if (i < data.size() - 1) {
                        siguiente = ((Object[]) data.get(i + 1))[groupIndex].toString();
                    }

                    if (!actual.equals(siguiente)) {
                        Row totalesRow = sheet.createRow(rowCount);

                        for (int j = 0; j < totales.length; j++) {
                            Integer index = findIndex(columnas, totales[j]);
                            if (index != -1) {
                                cell = totalesRow.createCell(index);
                                cell.setCellValue(sumaTotales.get(totales[j]).doubleValue());
                                cell.setCellStyle(cellStyleAccent);

                                sumaTotales.replace(totales[j], BigDecimal.ZERO);
                            }
                        }

                        rowCount++;
                        rowCount++;
                    }
                }
            }

            if (totalesGenerales != null) {
                rowCount++;
                Row totalesRow = sheet.createRow(rowCount);
                Integer firstColumn = findIndex(columnas, totalesGenerales[0]);

                if (firstColumn != -1) {
                    cell = totalesRow.createCell(firstColumn - 1);
                    cell.setCellValue("TOTAL");
                    cell.setCellStyle(cellStyleAccent);
                }

                for (int i = 0; i < totalesGenerales.length; i++) {
                    Integer index = findIndex(columnas, totalesGenerales[i]);
                    boolean isCurrency = columnasMoneda != null && ArrayUtils.contains(columnasMoneda, totalesGenerales[i]);

                    if (index != -1) {
                        cell = totalesRow.createCell(index);
                        cell.setCellValue(sumaTotalesGenerales.get(totalesGenerales[i]).doubleValue());
                        cell.setCellStyle(isCurrency ? cellStyleCurrencyAccent : cellStyleAccent);
                    }
                }
            }

            // Making size of column auto resize to fit with data
            // for (int i = 0; i < columnsSize; i++) {
            // sheet.autoSizeColumn(i);
            // }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ByteArrayInputStream listToExcelSXSSF(List<Object> data, String[] alColumnas) {
        return listToExcelSXSSF(data, alColumnas, null);
    }

    public static ByteArrayInputStream listToExcelSXSSF(List<Object> data, String[] alColumnas, String nombreHoja) {
        return listToExcelSXSSF(null, null, data, null, alColumnas, nombreHoja, null, null, null, null, null);
    }

    public static ByteArrayInputStream listToExcelSXSSF(List<Object> data, String[] alColumnas, String[] totalesGenerales, String[] columnasMoneda, String nombreHoja) {
        return listToExcelSXSSF(null, null, data, null, alColumnas, nombreHoja, null, null, null, totalesGenerales, columnasMoneda);
    }

    public static ByteArrayInputStream listToExcelSXSSF(
            String[] titulo,
            HashMap<String, Object> filtros,
            List<Object> data,
            List<String[]> groupColumnas,
            String[] columnas,
            String nombreHoja,
            String groupBy,
            String groupByDisplay,
            String[] totales,
            String[] totalesGenerales,
            String[] columnasMoneda) {

        if (nombreHoja == null) {
            nombreHoja = "Customers";
        }

        int[] listaWidthColumna = new int[columnas.length];

        try (Workbook workbook = new SXSSFWorkbook()) {

            //Fonts and styles
            Font fontWhite = workbook.createFont();
            fontWhite.setColor(IndexedColors.WHITE.getIndex());

            Font fontWhiteBold = workbook.createFont();
            fontWhiteBold.setColor(IndexedColors.WHITE.getIndex());
            fontWhiteBold.setBold(true);

            Font fontBold = workbook.createFont();
            fontBold.setBold(true);

            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
            titleStyle.setFont(fontBold);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            headerCellStyle.setFont(fontWhite);
            headerCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            CellStyle cellStyleDate = workbook.createCellStyle();
            CreationHelper createHelperDate = workbook.getCreationHelper();
            cellStyleDate.setDataFormat(createHelperDate.createDataFormat().getFormat("m/d/yy"));
            cellStyleDate.setAlignment(HorizontalAlignment.CENTER);

            CellStyle cellStyleCurrency = workbook.createCellStyle();
            CreationHelper createHelperCurrency = workbook.getCreationHelper();
            cellStyleCurrency.setDataFormat(createHelperCurrency.createDataFormat().getFormat("$#,##0.00"));
            cellStyleCurrency.setAlignment(HorizontalAlignment.RIGHT);

            CellStyle cellStyleCurrencyAccent = workbook.createCellStyle();
            CreationHelper createHelperCurrencyAccent = workbook.getCreationHelper();
            cellStyleCurrencyAccent.setDataFormat(createHelperCurrencyAccent.createDataFormat().getFormat("$#,##0.00"));
            cellStyleCurrencyAccent.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleCurrencyAccent.setFont(fontBold);

            CellStyle cellStyleText = workbook.createCellStyle();
            cellStyleText.setAlignment(HorizontalAlignment.CENTER);

            CellStyle cellStyleAccent = workbook.createCellStyle();
            cellStyleAccent.setFont(fontBold);
            cellStyleAccent.setAlignment(HorizontalAlignment.LEFT);

            CellStyle cellStyleGroupHeader = workbook.createCellStyle();
            cellStyleGroupHeader.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellStyleGroupHeader.setFont(fontWhiteBold);
            cellStyleGroupHeader.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
            cellStyleGroupHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleGroupHeader.setAlignment(HorizontalAlignment.CENTER);
            cellStyleGroupHeader.setBorderTop(BorderStyle.MEDIUM);
            cellStyleGroupHeader.setBorderBottom(BorderStyle.MEDIUM);

            SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(nombreHoja);

            Integer rowCount = 0;
            Cell cell = null;

            Integer columnsSize = columnas.length;

            if (groupByDisplay != null) {
                columnsSize = columnsSize - 1;
            }

            if (titulo != null) {
                for (String texto : titulo) {
                    Row rowTitle = sheet.createRow(rowCount);
                    sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, columnsSize - 1));
                    cell = rowTitle.createCell(0);
                    cell.setCellValue(texto);
                    cell.setCellStyle(titleStyle);

                    rowCount++;
                }

                rowCount++;
            }

            if (filtros != null) {
                Iterator it = filtros.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    Row rowFiltros = sheet.createRow(rowCount);
                    cell = rowFiltros.createCell(0);
                    cell.setCellValue((String) pair.getKey());
                    cell.setCellStyle(cellStyleAccent);

                    cell = rowFiltros.createCell(1);
                    String value = (String) pair.getValue();

                    if (value != null && !value.isEmpty()) {
                        value = value.replace('|', ',');
                        Integer items = value.split(",").length / 2;
                        cell.setCellValue(items > 0 ? (items.toString() + " elementos") : value);
                    } else {
                        cell.setCellValue("Todos");
                    }

                    cell.setCellStyle(titleStyle);

                    rowCount++;
                }

                rowCount++;
            }

            if (groupColumnas != null) {
                Row groupRow = sheet.createRow(rowCount);

                for (String[] grupo : groupColumnas) {
                    Integer firstIndex = findIndex(columnas, grupo[1]);
                    Integer lastIndex = findIndex(columnas, grupo[2]);

                    for (Integer i = firstIndex; i <= lastIndex; i++) {
                        cell = groupRow.createCell(i);

                        if (i == firstIndex) {
                            cell.setCellValue(grupo[0]);
                        }

                        cellStyleGroupHeader.setBorderLeft(BorderStyle.MEDIUM);
                        cellStyleGroupHeader.setBorderRight(BorderStyle.MEDIUM);
                        cell.setCellStyle(cellStyleGroupHeader);
                    }

                    if (firstIndex < lastIndex) {
                        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, firstIndex, lastIndex));
                    }

                }

                rowCount++;
            }

            Row row = sheet.createRow(rowCount);

            // Creating header
            for (int i = 0; i < columnsSize; i++) {
                cell = row.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerCellStyle);

                // Obtener el valor cuantas letras tiene.
                int wColumna = listaWidthColumna[i];
                // Si el valor (letras) es mayor que el valor anterior o posterior
                if(columnas[i].length() > wColumna)
                    listaWidthColumna[i] = columnas[i].length();
            }

            rowCount++;

            sheet.createFreezePane(0, rowCount);

            Integer groupIndex = -1;
            if (groupBy != null) {
                groupIndex = findIndex(columnas, groupBy);
            }

            Map<String, BigDecimal> sumaTotales = new HashMap<>();
            if (totales != null) {
                for (int i = 0; i < totales.length; i++) {
                    sumaTotales.put(totales[i], BigDecimal.ZERO);
                }
            }

            Map<String, BigDecimal> sumaTotalesGenerales = new HashMap<>();
            if (totalesGenerales != null) {
                for (int i = 0; i < totalesGenerales.length; i++) {
                    sumaTotalesGenerales.put(totalesGenerales[i], BigDecimal.ZERO);
                }
            }

            // Creating data rows for each customer
            for (int i = 0; i < data.size(); i++) {
                //Si es agrupado y se muestra el valor
                if (groupBy != null && groupByDisplay != null) {
                    String anterior = "";
                    String actual = ((Object[]) data.get(i))[groupIndex].toString();

                    if (i > 0) {
                        anterior = ((Object[]) data.get(i - 1))[groupIndex].toString();
                    }

                    if (!actual.equals(anterior)) {
                        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, columnsSize - 1));
                        rowCount++;
                        Row grupo = sheet.createRow(rowCount);
                        cell = grupo.createCell(0);
                        cell.setCellValue(groupByDisplay + ": " + actual);
                        cell.setCellStyle(cellStyleAccent);
                        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 0, columnsSize - 1));
                        rowCount++;
                    }
                }

                Row dataRow = sheet.createRow(rowCount);
                Object valor;

                for (int e = 0; e < columnsSize; e++) {
                    valor = ((Object[]) data.get(i))[e];

                    // Obtener el valor cuantas letras tiene.
                    int wColumna = listaWidthColumna[e];
                    // Si el valor (letras) es mayor que el valor anterior o posterior
                    if(valor != null)
                        if(valor.toString().length() > wColumna)
                            listaWidthColumna[e] = valor.toString().length();


                    if (valor != null && Integer.class.equals(valor.getClass())) {
                        cell = dataRow.createCell(e);
                        cell.setCellValue((Integer) valor);
                        cell.setCellStyle(cellStyleText);

                        if (sumaTotales.get(columnas[e]) != null) {
                            BigDecimal t = sumaTotales.get(columnas[e]).add(BigDecimal.valueOf((Integer) valor));
                            sumaTotales.replace(columnas[e], t);
                        }

                        if (sumaTotalesGenerales.get(columnas[e]) != null) {
                            BigDecimal t = sumaTotalesGenerales.get(columnas[e]).add(BigDecimal.valueOf((Integer) valor));
                            sumaTotalesGenerales.replace(columnas[e], t);
                        }
                    } else if (valor != null && BigDecimal.class.equals(valor.getClass())) {
                        boolean isCurrency = columnasMoneda != null && ArrayUtils.contains(columnasMoneda, columnas[e]);

                        cell = dataRow.createCell(e);
                        cell.setCellValue(((BigDecimal) valor).doubleValue());
                        cell.setCellStyle(isCurrency ? cellStyleCurrency : cellStyleText);

                        if (sumaTotales.get(columnas[e]) != null) {
                            BigDecimal t = sumaTotales.get(columnas[e]).add((BigDecimal) valor);
                            sumaTotales.replace(columnas[e], t);
                        }

                        if (sumaTotalesGenerales.get(columnas[e]) != null) {
                            BigDecimal t = sumaTotalesGenerales.get(columnas[e]).add((BigDecimal) valor);
                            sumaTotalesGenerales.replace(columnas[e], t);
                        }
                    } else if (valor != null && Timestamp.class.equals(valor.getClass())) {
                        cell = dataRow.createCell(e);
                        cell.setCellValue((Timestamp) valor);
                        cell.setCellStyle(cellStyleDate);
                    } else if (valor != null && Date.class.equals(valor.getClass())) {
                        cell = dataRow.createCell(e);
                        cell.setCellValue((Date) valor);
                        cell.setCellStyle(cellStyleDate);
                    } else if (valor != null && java.sql.Date.class.equals(valor.getClass())) {
                        cell = dataRow.createCell(e);
                        cell.setCellValue((java.sql.Date) valor);
                        cell.setCellStyle(cellStyleDate);
                    } else {
                        try {
                            cell = dataRow.createCell(e);
                            cell.setCellValue(valor.toString());
                            cell.setCellStyle(cellStyleText);
                        } catch (Exception ex) {
                        }
                    }

                    if (groupByDisplay != null) {
                        CellStyle aux = cell.getCellStyle();
                        aux.setBorderRight(BorderStyle.THIN);
                        aux.setBorderLeft(BorderStyle.THIN);
                        aux.setBorderTop(BorderStyle.THIN);
                        aux.setBorderBottom(BorderStyle.THIN);

                        cell.setCellStyle(aux);
                    }
                }

                rowCount++;

                //Calculo de totales
                if (totales != null) {
                    String actual = ((Object[]) data.get(i))[groupIndex].toString();
                    String siguiente = "";

                    if (i < data.size() - 1) {
                        siguiente = ((Object[]) data.get(i + 1))[groupIndex].toString();
                    }

                    if (!actual.equals(siguiente)) {
                        Row totalesRow = sheet.createRow(rowCount);

                        for (int j = 0; j < totales.length; j++) {
                            Integer index = findIndex(columnas, totales[j]);
                            if (index != -1) {
                                cell = totalesRow.createCell(index);
                                cell.setCellValue(sumaTotales.get(totales[j]).doubleValue());
                                cell.setCellStyle(cellStyleAccent);

                                sumaTotales.replace(totales[j], BigDecimal.ZERO);
                            }
                        }

                        rowCount++;
                        rowCount++;
                    }
                }
            }

            if (totalesGenerales != null) {
                rowCount++;
                Row totalesRow = sheet.createRow(rowCount);
                Integer firstColumn = findIndex(columnas, totalesGenerales[0]);

                if (firstColumn != -1) {
                    cell = totalesRow.createCell(firstColumn - 1);
                    cell.setCellValue("TOTAL");
                    cell.setCellStyle(cellStyleAccent);
                }

                for (int i = 0; i < totalesGenerales.length; i++) {
                    Integer index = findIndex(columnas, totalesGenerales[i]);
                    boolean isCurrency = columnasMoneda != null && ArrayUtils.contains(columnasMoneda, totalesGenerales[i]);

                    if (index != -1) {
                        cell = totalesRow.createCell(index);
                        cell.setCellValue(sumaTotalesGenerales.get(totalesGenerales[i]).doubleValue());
                        cell.setCellStyle(isCurrency ? cellStyleCurrencyAccent : cellStyleAccent);
                    }
                }
            }

            // Making size of column auto resize to fit with data
            for (int i = 0; i < columnsSize; i++) {
                int width = ((int)(listaWidthColumna[i] * 1.14388)) * 256;
                sheet.setColumnWidth(i, width);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            if (workbook instanceof SXSSFWorkbook) ((SXSSFWorkbook)workbook).dispose();

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static Integer findIndex(String[] data, String target) {
        Integer index = -1;

        for (int i = 0; i < data.length; i++) {
            if (data[i].equals(target)) {
                return i;
            }
        }

        return index;
    }
}