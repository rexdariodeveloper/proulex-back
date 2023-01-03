package com.pixvs.main.models;

import com.pixvs.spring.models.Archivo;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 20/08/2020.
 */
@Entity
@Table(name = "OrdenesCompraRecibos")
public class OrdenCompraRecibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OCR_OrdenCompraReciboId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCR_OC_OrdenCompraId", nullable = true, insertable = false, updatable = false, referencedColumnName = "OC_OrdenCompraId")
    private OrdenCompra ordenCompra;

    @Column(name = "OCR_OC_OrdenCompraId", nullable = false, insertable = true, updatable = false)
    private Integer ordenCompraId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCR_OCD_OrdenCompraDetalleId", nullable = true, insertable = false, updatable = false, referencedColumnName = "OCD_OrdenCompraDetalleId")
    private OrdenCompraDetalle ordenCompraDetalle;

    @Column(name = "OCR_OCD_OrdenCompraDetalleId", nullable = false, insertable = true, updatable = false)
    private Integer ordenCompraDetalleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCR_OCR_ReciboReferenciaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "OCR_OrdenCompraReciboId")
    private OrdenCompraRecibo reciboReferencia;

    @Column(name = "OCR_OCR_ReciboReferenciaId", nullable = true, insertable = true, updatable = false)
    private Integer reciboReferenciaId;

    @Column(name = "OCR_FechaRequerida", nullable = false, insertable = true, updatable = false)
    private Date fechaRequerida;

    @Column(name = "OCR_FechaRecibo", nullable = false, insertable = true, updatable = false)
    private Date fechaRecibo;

    @Column(name = "OCR_CantidadRecibo", nullable = false, insertable = true, updatable = false)
    private BigDecimal cantidadRecibo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCR_LOC_LocalidadId", nullable = true, insertable = false, updatable = false, referencedColumnName = "LOC_LocalidadId")
    private Localidad localidad;

    @Column(name = "OCR_LOC_LocalidadId", nullable = false, insertable = true, updatable = false)
    private Integer localidadId;

    @CreationTimestamp
    @Column(name = "OCR_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCR_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "OCR_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCR_ARC_FacturaPDF", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo facturaPDF;

    @Column(name = "OCR_ARC_FacturaPDF", nullable = true, insertable = true, updatable = false)
    private Integer facturaPDFId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OCR_ARC_FacturaXML", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo facturaXML;

    @Column(name = "OCR_ARC_FacturaXML", nullable = true, insertable = true, updatable = false)
    private Integer facturaXMLId;

    @Column(name = "OCR_CodigoRecibo", nullable = false, insertable = true, updatable = false)
    private String codigoRecibo;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name="OCR_OCR_ReciboReferenciaId", referencedColumnName = "OCR_OrdenCompraReciboId", nullable = false, insertable = false, updatable = false)
    private List<OrdenCompraRecibo> devoluciones;

    @ManyToMany
    @JoinTable(name = "OrdenesCompraRecibosEvidencia", joinColumns = {@JoinColumn(name = "OCRE_OCR_OrdenCompraReciboId")}, inverseJoinColumns = {@JoinColumn(name = "OCRE_ARC_ArchivoId")})
    private Set<Archivo> evidencia = new HashSet<>();

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name="CXPFD_OCR_OrdenCompraReciboId", referencedColumnName = "OCR_OrdenCompraReciboId", nullable = false, insertable = false, updatable = false)
    private List<CXPFacturaDetalle> cxpFacturasDetalles;

    @Transient
    private BigDecimal cantidadDevolver;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Integer getOrdenCompraId() {
        return ordenCompraId;
    }

    public void setOrdenCompraId(Integer ordenCompraId) {
        this.ordenCompraId = ordenCompraId;
    }

    public OrdenCompraDetalle getOrdenCompraDetalle() {
        return ordenCompraDetalle;
    }

    public void setOrdenCompraDetalle(OrdenCompraDetalle ordenCompraDetalle) {
        this.ordenCompraDetalle = ordenCompraDetalle;
    }

    public Integer getOrdenCompraDetalleId() {
        return ordenCompraDetalleId;
    }

    public void setOrdenCompraDetalleId(Integer ordenCompraDetalleId) {
        this.ordenCompraDetalleId = ordenCompraDetalleId;
    }

    public Date getFechaRequerida() {
        return fechaRequerida;
    }

    public void setFechaRequerida(Date fechaRequerida) {
        this.fechaRequerida = fechaRequerida;
    }

    public Date getFechaRecibo() {
        return fechaRecibo;
    }

    public void setFechaRecibo(Date fechaRecibo) {
        this.fechaRecibo = fechaRecibo;
    }

    public BigDecimal getCantidadRecibo() {
        return cantidadRecibo;
    }

    public void setCantidadRecibo(BigDecimal cantidadRecibo) {
        this.cantidadRecibo = cantidadRecibo;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Integer getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(Integer localidadId) {
        this.localidadId = localidadId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public OrdenCompraRecibo getReciboReferencia() {
        return reciboReferencia;
    }

    public void setReciboReferencia(OrdenCompraRecibo reciboReferencia) {
        this.reciboReferencia = reciboReferencia;
    }

    public Integer getReciboReferenciaId() {
        return reciboReferenciaId;
    }

    public void setReciboReferenciaId(Integer reciboReferenciaId) {
        this.reciboReferenciaId = reciboReferenciaId;
    }

    public List<OrdenCompraRecibo> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<OrdenCompraRecibo> devoluciones) {
        this.devoluciones = devoluciones;
    }

    public BigDecimal getCantidadDevolver() {
        return cantidadDevolver;
    }

    public void setCantidadDevolver(BigDecimal cantidadDevolver) {
        this.cantidadDevolver = cantidadDevolver;
    }

    public Archivo getFacturaPDF() {
        return facturaPDF;
    }

    public void setFacturaPDF(Archivo facturaPDF) {
        this.facturaPDF = facturaPDF;
    }

    public Integer getFacturaPDFId() {
        return facturaPDFId;
    }

    public void setFacturaPDFId(Integer facturaPDFId) {
        this.facturaPDFId = facturaPDFId;
    }

    public Archivo getFacturaXML() {
        return facturaXML;
    }

    public void setFacturaXML(Archivo facturaXML) {
        this.facturaXML = facturaXML;
    }

    public Integer getFacturaXMLId() {
        return facturaXMLId;
    }

    public void setFacturaXMLId(Integer facturaXMLId) {
        this.facturaXMLId = facturaXMLId;
    }

    public Set<Archivo> getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(Set<Archivo> evidencia) {
        this.evidencia = evidencia;
    }

    public List<CXPFacturaDetalle> getCxpFacturasDetalles() {
        return cxpFacturasDetalles;
    }

    public void setCxpFacturasDetalles(List<CXPFacturaDetalle> cxpFacturasDetalles) {
        this.cxpFacturasDetalles = cxpFacturasDetalles;
    }

    public String getCodigoRecibo() {
        return codigoRecibo;
    }

    public void setCodigoRecibo(String codigoRecibo) {
        this.codigoRecibo = codigoRecibo;
    }
}
