package com.pixvs.main.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class DatosFactura {

    private String folio;
    private String serie;
    private BigDecimal total;
    private Date fecha;
    private UUID uuid;
    private String monedaCodigo;
    private BigDecimal subtotal;
    private BigDecimal montoIva;
    private BigDecimal montoIeps;
    private BigDecimal montoDescuento;
    private BigDecimal montoRetenciones;

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMonedaCodigo() {
        return monedaCodigo;
    }

    public void setMonedaCodigo(String monedaCodigo) {
        this.monedaCodigo = monedaCodigo;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getMontoIva() {
        return montoIva;
    }

    public void setMontoIva(BigDecimal montoIva) {
        this.montoIva = montoIva;
    }

    public BigDecimal getMontoIeps() {
        return montoIeps;
    }

    public void setMontoIeps(BigDecimal montoIeps) {
        this.montoIeps = montoIeps;
    }

    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(BigDecimal montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public BigDecimal getMontoRetenciones() {
        return montoRetenciones;
    }

    public void setMontoRetenciones(BigDecimal montoRetenciones) {
        this.montoRetenciones = montoRetenciones;
    }
}
