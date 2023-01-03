package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Entity
@Table(name = "CXPPagos")
public class CXPPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPP_CXPPagoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CXPP_PRO_ProveedorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PRO_ProveedorId")
    private Proveedor proveedor;

    @Column(name = "CXPP_PRO_ProveedorId", nullable = true, insertable = true, updatable = false)
    private Integer proveedorId;

    @Column(name = "CXPP_RemitirA", length = 250, nullable = true, insertable = true, updatable = true)
    private String remitirA;

    @OneToOne
    @JoinColumn(name = "CXPP_BAC_CuentaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "BAC_CuentaId")
    private CuentaBancaria cuentaBancaria;

    @Column(name = "CXPP_BAC_CuentaId", nullable = true, insertable = true, updatable = false)
    private Integer cuentaBancariaId;

    @OneToOne
    @JoinColumn(name = "CXPP_FP_FormaPagoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "FP_FormaPagoId")
    private FormaPago formaPago;

    @Column(name = "CXPP_FP_FormaPagoId", nullable = true, insertable = true, updatable = true)
    private Integer formaPagoId;

    @Column(name = "CXPP_FechaPago", nullable = true, insertable = true, updatable = true)
    private Date fechaPago;

    @Column(name = "CXPP_IdentificacionPago", length = 100, nullable = true, insertable = true, updatable = true)
    private String identificacionPago;

    @Column(name = "CXPP_MontoPago", nullable = true, insertable = true, updatable = true)
    private BigDecimal montoPago;

    @OneToOne
    @JoinColumn(name = "CXPP_MON_MonedaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "CXPP_MON_MonedaId", nullable = true, insertable = true, updatable = true)
    private Integer monedaId;

    @Column(name = "CXPP_ParidadOficial", nullable = true, insertable = true, updatable = true)
    private BigDecimal paridadOficial;

    @Column(name = "CXPP_ParidadUsuario", nullable = true, insertable = true, updatable = true)
    private BigDecimal paridadUsuario;

    @OneToOne
    @JoinColumn(name = "CXPP_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CXPP_CMM_EstatusId", nullable = true, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "CXPP_ARC_ComprobanteId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo comprobante;

    @Column(name = "CXPP_ARC_ComprobanteId", nullable = true, insertable = true, updatable = true)
    private Integer comprobanteId;

    @CreationTimestamp
    @Column(name = "CXPP_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "CXPP_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CXPP_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "CXPP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CXPP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPP_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinColumn(name="CXPPD_CXPP_CXPPagoId", referencedColumnName = "CXPP_CXPPagoId", nullable = false, insertable = true, updatable = true)
    private List<CXPPagoDetalle> detalles;

    @Transient
    private Integer solicitudId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Integer proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getRemitirA() {
        return remitirA;
    }

    public void setRemitirA(String remitirA) {
        this.remitirA = remitirA;
    }

    public CuentaBancaria getCuentaBancaria() { return cuentaBancaria; }

    public void setCuentaBancaria(CuentaBancaria cuentaBancaria) { this.cuentaBancaria = cuentaBancaria; }

    public Integer getCuentaBancariaId() { return cuentaBancariaId; }

    public void setCuentaBancariaId(Integer cuentaBancariaId) { this.cuentaBancariaId = cuentaBancariaId; }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Integer getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(Integer formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getIdentificacionPago() {
        return identificacionPago;
    }

    public void setIdentificacionPago(String identificacionPago) {
        this.identificacionPago = identificacionPago;
    }

    public BigDecimal getMontoPago() {
        return montoPago;
    }

    public void setMontoPago(BigDecimal montoPago) {
        this.montoPago = montoPago;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }

    public BigDecimal getParidadOficial() {
        return paridadOficial;
    }

    public void setParidadOficial(BigDecimal paridadOficial) {
        this.paridadOficial = paridadOficial;
    }

    public BigDecimal getParidadUsuario() {
        return paridadUsuario;
    }

    public void setParidadUsuario(BigDecimal paridadUsuario) {
        this.paridadUsuario = paridadUsuario;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
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

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public List<CXPPagoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CXPPagoDetalle> detalles) {
        this.detalles = detalles;
    }

    public Integer getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Integer solicitudId) {
        this.solicitudId = solicitudId;
    }

    public Archivo getComprobante() {
        return comprobante;
    }

    public void setComprobante(Archivo comprobante) {
        this.comprobante = comprobante;
    }

    public Integer getComprobanteId() {
        return comprobanteId;
    }

    public void setComprobanteId(Integer comprobanteId) {
        this.comprobanteId = comprobanteId;
    }
}
