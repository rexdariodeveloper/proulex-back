package com.pixvs.spring.models;

import java.util.HashMap;

public class ExcelExportadoHoja {

    private String nombreHoja;
    private String query;
    private String[] columnas;
    private String[] totalesGenerales;
    private int[] columnasAnchos;
    private boolean columnasAnchosAuto = false;
    private HashMap<String, Object> queryParameters;
    private HashMap<String, Short> coloresPorValor;
    private HashMap<String, Short> coloresPorColumna;
    private boolean imprimirCero = true;
    private boolean imprimirBorde = false;

    public String getNombreHoja() {
        return nombreHoja;
    }

    public void setNombreHoja(String nombreHoja) {
        this.nombreHoja = nombreHoja;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String[] getColumnas() {
        return columnas;
    }

    public void setColumnas(String[] columnas) {
        this.columnas = columnas;
    }

    public String[] getTotalesGenerales() {
        return totalesGenerales;
    }

    public void setTotalesGenerales(String[] totalesGenerales) {
        this.totalesGenerales = totalesGenerales;
    }

    public int[] getColumnasAnchos() {
        return columnasAnchos;
    }

    public void setColumnasAnchos(int[] columnasAnchos) {
        this.columnasAnchos = columnasAnchos;
    }

    public boolean isColumnasAnchosAuto() {
        return columnasAnchosAuto;
    }

    public void setColumnasAnchosAuto(boolean columnasAnchosAuto) {
        this.columnasAnchosAuto = columnasAnchosAuto;
    }

    public HashMap<String, Object> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(HashMap<String, Object> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public HashMap<String, Short> getColoresPorValor() {
        return coloresPorValor;
    }

    public void setColoresPorValor(HashMap<String, Short> coloresPorValor) {
        this.coloresPorValor = coloresPorValor;
    }

    public HashMap<String, Short> getColoresPorColumna() {
        return coloresPorColumna;
    }

    public void setColoresPorColumna(HashMap<String, Short> coloresPorColumna) {
        this.coloresPorColumna = coloresPorColumna;
    }

    public boolean isImprimirCero() {
        return imprimirCero;
    }

    public void setImprimirCero(boolean imprimirCero) {
        this.imprimirCero = imprimirCero;
    }

    public boolean isImprimirBorde() {
        return imprimirBorde;
    }

    public void setImprimirBorde(boolean imprimirBorde) {
        this.imprimirBorde = imprimirBorde;
    }
}