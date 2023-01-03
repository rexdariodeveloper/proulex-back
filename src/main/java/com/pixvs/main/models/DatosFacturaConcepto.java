package com.pixvs.main.models;

import java.math.BigDecimal;

public class DatosFacturaConcepto {

    private DatosFacturaImpuesto iva;
    private DatosFacturaImpuesto ieps;
    private String concepto;
    private String um;
    private String umClave;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal importe;

    public DatosFacturaImpuesto getIva() {
        return iva;
    }

    public void setIva(DatosFacturaImpuesto iva) {
        this.iva = iva;
    }

    public DatosFacturaImpuesto getIeps() {
        return ieps;
    }

    public void setIeps(DatosFacturaImpuesto ieps) {
        this.ieps = ieps;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public String getUmClave() {
        return umClave;
    }

    public void setUmClave(String umClave) {
        this.umClave = umClave;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

}
