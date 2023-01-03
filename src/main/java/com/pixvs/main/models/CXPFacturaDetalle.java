package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 08/09/2020.
 */
@Entity
@Table(name = "CXPFacturasDetalles")
public class CXPFacturaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPFD_CXPFacturadetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPFD_CXPF_CXPFacturaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPF_CXPFacturaId")
    private CXPFactura cxpFactura;

    @Column(name = "CXPFD_CXPF_CXPFacturaId", nullable = false, insertable = false, updatable = false)
    private Integer cxpFacturaId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPFD_OCR_OrdenCompraReciboId", nullable = true, insertable = false, updatable = false, referencedColumnName = "OCR_OrdenCompraReciboId")
    private OrdenCompraRecibo recibo;

    @Column(name = "CXPFD_OCR_OrdenCompraReciboId", nullable = true, insertable = true, updatable = true)
    private Integer reciboId;

    @Column(name = "CXPFD_NumeroLinea", nullable = false, insertable = true, updatable = true)
    private Integer numeroLinea;

    @Column(name = "CXPFD_Descripcion", length = 255, nullable = true, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "CXPFD_Cantidad", nullable = true, insertable = true, updatable = true)
    private BigDecimal cantidad;

    @Column(name = "CXPFD_PrecioUnitario", nullable = false, insertable = true, updatable = true)
    private BigDecimal precioUnitario;

    @Column(name = "CXPFD_IVA", nullable = true, insertable = true, updatable = true)
    private BigDecimal iva;

    @Column(name = "CXPFD_IVAExento", nullable = true, insertable = true, updatable = true)
    private Boolean ivaExento;

    @Column(name = "CXPFD_IEPS", nullable = true, insertable = true, updatable = true)
    private BigDecimal ieps;

    @Column(name = "CXPFD_IEPSCuotaFija", nullable = true, insertable = true, updatable = true)
    private BigDecimal iepsCuotaFija;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPFD_CMM_TipoRegistroId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoRegistro;

    @Column(name = "CXPFD_CMM_TipoRegistroId", nullable = true, insertable = true, updatable = true)
    private Integer tipoRegistroId;

    @Column(name = "CXPFD_Descuento", nullable = false, insertable = true, updatable = true)
    private BigDecimal descuento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPFD_CMM_TipoRetencionId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoRetencion;

    @Column(name = "CXPFD_CMM_TipoRetencionId", nullable = true, insertable = true, updatable = true)
    private Integer tipoRetencionId;

    @Column(name = "CXPFD_ART_ArticuloId", nullable = true, insertable = true, updatable = true)
    private Integer articuloId;

    @Column(name = "CXPFD_UM_UnidadMedidaId", nullable = true, insertable = true, updatable = true)
    private Integer unidadMedidaId;

    @Transient
    private Integer ordenCompraDetalleId;
    @Transient
    private BigDecimal cantidadRelacionar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CXPFactura getCxpFactura() {
        return cxpFactura;
    }

    public void setCxpFactura(CXPFactura cxpFactura) {
        this.cxpFactura = cxpFactura;
    }

    public Integer getCxpFacturaId() {
        return cxpFacturaId;
    }

    public void setCxpFacturaId(Integer cxpFacturaId) {
        this.cxpFacturaId = cxpFacturaId;
    }

    public OrdenCompraRecibo getRecibo() {
        return recibo;
    }

    public void setRecibo(OrdenCompraRecibo recibo) {
        this.recibo = recibo;
    }

    public Integer getReciboId() {
        return reciboId;
    }

    public void setReciboId(Integer reciboId) {
        this.reciboId = reciboId;
    }

    public Integer getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(Integer numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Boolean getIvaExento() {
        return ivaExento;
    }

    public void setIvaExento(Boolean ivaExento) {
        this.ivaExento = ivaExento;
    }

    public BigDecimal getIeps() {
        return ieps;
    }

    public void setIeps(BigDecimal ieps) {
        this.ieps = ieps;
    }

    public BigDecimal getIepsCuotaFija() {
        return iepsCuotaFija;
    }

    public void setIepsCuotaFija(BigDecimal iepsCuotaFija) {
        this.iepsCuotaFija = iepsCuotaFija;
    }

    public ControlMaestroMultiple getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(ControlMaestroMultiple tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Integer getTipoRegistroId() {
        return tipoRegistroId;
    }

    public void setTipoRegistroId(Integer tipoRegistroId) {
        this.tipoRegistroId = tipoRegistroId;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public ControlMaestroMultiple getTipoRetencion() {
        return tipoRetencion;
    }

    public void setTipoRetencion(ControlMaestroMultiple tipoRetencion) {
        this.tipoRetencion = tipoRetencion;
    }

    public Integer getTipoRetencionId() {
        return tipoRetencionId;
    }

    public void setTipoRetencionId(Integer tipoRetencionId) {
        this.tipoRetencionId = tipoRetencionId;
    }

    public BigDecimal getCantidadRelacionar() {
        return cantidadRelacionar;
    }

    public void setCantidadRelacionar(BigDecimal cantidadRelacionar) {
        this.cantidadRelacionar = cantidadRelacionar;
    }

    public Integer getOrdenCompraDetalleId() {
        return ordenCompraDetalleId;
    }

    public void setOrdenCompraDetalleId(Integer ordenCompraDetalleId) {
        this.ordenCompraDetalleId = ordenCompraDetalleId;
    }

    public Integer getArticuloId() { return articuloId; }
    public void setArticuloId(Integer articuloId) { this.articuloId = articuloId; }

    public Integer getUnidadMedidaId() { return unidadMedidaId; }
    public void setUnidadMedidaId(Integer unidadMedidaId) { this.unidadMedidaId = unidadMedidaId; }
}
