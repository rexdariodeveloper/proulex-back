package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 08/09/2020.
 */
@Entity
@Table(name = "CXPFacturas")
public class CXPFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CXPF_CXPFacturaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CXPF_CodigoRegistro", nullable = false)
    private String codigoRegistro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_CMM_TipoRegistroId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoRegistro;

    @Column(name = "CXPF_CMM_TipoRegistroId", nullable = false)
    private Integer tipoRegistroId;

    @OneToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "CXPF_PRO_ProveedorId", insertable = false, updatable = false, referencedColumnName = "PRO_ProveedorId")
    private Proveedor proveedor;

    @Column(name = "CXPF_PRO_ProveedorId")
    private Integer proveedorId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPF_FechaRegistro", nullable = false)
    private Date fechaRegistro;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPF_FechaReciboRegistro", nullable = false)
    private Date fechaReciboRegistro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_MON_MonedaId", insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "CXPF_MON_MonedaId")
    private Integer monedaId;

    @Column(name = "CXPF_ParidadOficial")
    private BigDecimal paridadOficial;

    @Column(name = "CXPF_ParidadUsuario")
    private BigDecimal paridadUsuario;

    @Column(name = "CXPF_MontoRegistro")
    private BigDecimal montoRegistro;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPF_FechaPago")
    private Date fechaPago;

    @Column(name = "CXPF_Comentarios")
    private String comentarios;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_CMM_TipoPagoId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoPago;

    @Column(name = "CXPF_CMM_TipoPagoId")
    private Integer tipoPagoId;

    @Column(name = "CXPF_UUID")
    private String uuid;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_CMM_EstatusId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CXPF_CMM_EstatusId", nullable = false)
    private Integer estatusId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_ARC_FacturaXMLId", insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo facturaXML;

    @Column(name = "CXPF_ARC_FacturaXMLId")
    private Integer facturaXMLId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_ARC_FacturaPDFId", insertable = false, updatable = false, referencedColumnName = "ARC_ArchivoId")
    private Archivo facturaPDF;

    @Column(name = "CXPF_ARC_FacturaPDFId")
    private Integer facturaPDFId;

    @Column(name = "CXPF_DiasCredito", updatable = false)
    private Integer diasCredito;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPF_FechaCancelacion")
    private Date fechaCancelacion;

    @Column(name = "CXPF_MotivoCancelacion")
    private String motivoCancelacion;

    @CreationTimestamp
    @Column(name = "CXPF_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_USU_CreadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CXPF_USU_CreadoPorId", updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPF_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CXPF_USU_ModificadoPorId", insertable = false)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CXPF_FechaModificacion", insertable = false)
    private Date fechaModificacion;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPFD_CXPF_CXPFacturaId", referencedColumnName = "CXPF_CXPFacturaId", nullable = false)
    private List<CXPFacturaDetalle> detalles;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPPD_CXPF_CXPFacturaId", referencedColumnName = "CXPF_CXPFacturaId", nullable = false, insertable = false, updatable = false)
    private List<CXPPagoDetalle> cxpPagosDetalles;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "CXPSPSD_CXPF_CXPFacturaId", referencedColumnName = "CXPF_CXPFacturaId", nullable = false, insertable = false, updatable = false)
    private List<CXPSolicitudPagoServicioDetalle> cxpSolicitudesPagosServiciosDetalles;

    @Transient
    private String monedaCodigo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoRegistro() {
        return codigoRegistro;
    }

    public void setCodigoRegistro(String codigoRegistro) {
        this.codigoRegistro = codigoRegistro;
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

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaReciboRegistro() {
        return fechaReciboRegistro;
    }

    public void setFechaReciboRegistro(Date fechaReciboRegistro) {
        this.fechaReciboRegistro = fechaReciboRegistro;
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

    public BigDecimal getMontoRegistro() {
        return montoRegistro;
    }

    public void setMontoRegistro(BigDecimal montoRegistro) {
        this.montoRegistro = montoRegistro;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getTipoPagoId() {
        return tipoPagoId;
    }

    public void setTipoPagoId(Integer tipoPagoId) {
        this.tipoPagoId = tipoPagoId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public List<CXPFacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CXPFacturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public ControlMaestroMultiple getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(ControlMaestroMultiple tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getMonedaCodigo() {
        return monedaCodigo;
    }

    public void setMonedaCodigo(String monedaCodigo) {
        this.monedaCodigo = monedaCodigo;
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

    public List<CXPPagoDetalle> getCxpPagosDetalles() {
        return cxpPagosDetalles;
    }

    public void setCxpPagosDetalles(List<CXPPagoDetalle> cxpPagosDetalles) {
        this.cxpPagosDetalles = cxpPagosDetalles;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public List<CXPSolicitudPagoServicioDetalle> getCxpSolicitudesPagosServiciosDetalles() {
        return cxpSolicitudesPagosServiciosDetalles;
    }

    public void setCxpSolicitudesPagosServiciosDetalles(List<CXPSolicitudPagoServicioDetalle> cxpSolicitudesPagosServiciosDetalles) {
        this.cxpSolicitudesPagosServiciosDetalles = cxpSolicitudesPagosServiciosDetalles;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }
}