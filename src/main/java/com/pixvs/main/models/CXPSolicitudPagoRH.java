package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "CXPSolicitudesPagosRH")
public class CXPSolicitudPagoRH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CPXSPRH_CXPSolicitudPagoRhId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "CPXSPRH_Codigo", nullable = false)
    private String codigo;

    @OneToOne
    @JoinColumn(name = "CPXSPRH_CXPF_CXPFacturaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CXPF_CXPFacturaId")
    private CXPFactura factura;

    @Column(name = "CPXSPRH_CXPF_CXPFacturaId", nullable = false, insertable = true, updatable = true)
    private Integer facturaId;

    @OneToOne
    @JoinColumn(name = "CPXSPRH_SUC_SucursalId", nullable = false, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "CPXSPRH_SUC_SucursalId", nullable = false)
    private Integer sucursalId;

    @OneToOne
    @JoinColumn(name = "CPXSPRH_EMP_EmpleadoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "CPXSPRH_EMP_EmpleadoId", nullable = false)
    private Integer empleadoId;

    @OneToOne
    @JoinColumn(name = "CPXSPRH_CMM_TipoPagoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoPago;

    @Column(name = "CPXSPRH_CMM_TipoPagoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoPagoId;

    @Column(name = "CPXSPRH_Monto", nullable = true, insertable = true, updatable = true)
    private BigDecimal monto;

    @Column(name = "CXPSPRH_Comentarios", nullable = true, insertable = true, updatable = true)
    private String comentarios;

    @OneToOne
    @JoinColumn(name = "CPXSPRH_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "CPXSPRH_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @CreationTimestamp
    @Column(name = "CPXSPRH_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "CPXSPRH_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "CPXSPRH_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "CPXSPRH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "CPXSPRH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "CPXSPRH_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "CPXSPRHBD_CPXSPRH_CXPSolicitudPagoRhId", referencedColumnName = "CPXSPRH_CXPSolicitudPagoRhId", nullable = false, insertable = true, updatable = true)
    private List<CXPSolicitudPagoRHBecarioDocumento> becarioDocumentos;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "CPXSPRHI_CPXSPRH_CXPSolicitudPagoRhId", nullable = false, insertable = true, updatable = true, referencedColumnName = "CPXSPRH_CXPSolicitudPagoRhId")
    private List<CXPSolicitudPagoRHIncapacidad> incapacidad;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "CPXSPRHPA_CPXSPRH_CXPSolicitudPagoRhId", referencedColumnName = "CPXSPRH_CXPSolicitudPagoRhId",nullable = false, insertable = true, updatable = true)
    private List<CXPSolicitudPagoRHPensionAlimenticia> pensionAlimenticia;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name="CPXSPRHRCA_CPXSPRH_CXPSolicitudPagoRhId", referencedColumnName = "CPXSPRH_CXPSolicitudPagoRhId",nullable = false, insertable = true, updatable = true)
    private List<CXPSolicitudPagoRHRetiroCajaAhorro> cajaAhorro;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public ControlMaestroMultiple getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(ControlMaestroMultiple tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Integer getTipoPagoId() {
        return tipoPagoId;
    }

    public void setTipoPagoId(Integer tipoPagoId) {
        this.tipoPagoId = tipoPagoId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
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

    public List<CXPSolicitudPagoRHIncapacidad> getIncapacidad() {
        return incapacidad;
    }

    public void setIncapacidad(List<CXPSolicitudPagoRHIncapacidad> incapacidad) {
        this.incapacidad = incapacidad;
    }

    public List<CXPSolicitudPagoRHPensionAlimenticia> getPensionAlimenticia() {
        return pensionAlimenticia;
    }

    public void setPensionAlimenticia(List<CXPSolicitudPagoRHPensionAlimenticia> pensionAlimenticia) {
        this.pensionAlimenticia = pensionAlimenticia;
    }

    public List<CXPSolicitudPagoRHRetiroCajaAhorro> getCajaAhorro() {
        return cajaAhorro;
    }

    public void setCajaAhorro(List<CXPSolicitudPagoRHRetiroCajaAhorro> cajaAhorro) {
        this.cajaAhorro = cajaAhorro;
    }

    public List<CXPSolicitudPagoRHBecarioDocumento> getBecarioDocumentos() {
        return becarioDocumentos;
    }

    public void setBecarioDocumentos(List<CXPSolicitudPagoRHBecarioDocumento> becarioDocumentos) {
        this.becarioDocumentos = becarioDocumentos;
    }

    public CXPFactura getFactura() {
        return factura;
    }

    public void setFactura(CXPFactura factura) {
        this.factura = factura;
    }

    public Integer getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Integer facturaId) {
        this.facturaId = facturaId;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
