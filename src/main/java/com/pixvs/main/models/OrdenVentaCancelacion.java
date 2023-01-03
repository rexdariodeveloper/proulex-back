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
 * Created by Angel Daniel Hernández Silva on 25/03/2022.
 */
@Entity
@Table(name = "OrdenesVentaCancelaciones")
public class OrdenVentaCancelacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OVC_OrdenVentaCancelacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "OVC_Codigo", length = 15, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @Column(name = "OVC_CMM_TipoMovimientoId", nullable = false)
    private int tipoMovimientoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVC_CMM_TipoMovimientoId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoMovimiento;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "OVC_FechaDevolucion")
    private Date fechaDevolucion;

    @Column(name = "OVC_CMM_MotivoDevolucionId")
    private Integer motivoDevolucionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVC_CMM_MotivoDevolucionId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple motivoDevolucion;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "OVC_FechaCancelacion")
    private Date fechaCancelacion;

    @Column(name = "OVC_CMM_MotivoCancelacionId")
    private Integer motivoCancelacionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVC_CMM_MotivoCancelacionId", insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple motivoCancelacion;

    @Column(name = "OVC_Banco", length = 255)
    private String banco;

    @Column(name = "OVC_Beneficiario", length = 255)
    private String beneficiario;

    @Column(name = "OVC_NumeroCuenta", length = 255)
    private String numeroCuenta;

    @Column(name = "OVC_CLABE", length = 255)
    private String clabe;

    @Column(name = "OVC_TelefonoContacto", length = 255)
    private String telefonoContacto;

    @Column(name = "OVC_ImporteReembolsar", nullable = false)
    private BigDecimal importeReembolsar;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVC_CMM_TipoCancelacionId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoCancelacion;

    @Column(name = "OVC_CMM_TipoCancelacionId", nullable = false, insertable = true, updatable = true)
    private Integer tipoCancelacionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVC_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "OVC_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @CreationTimestamp
    @Column(name = "OVC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "OVC_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "OVC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "OVC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "OVCD_OVC_OrdenVentaCancelacionId", referencedColumnName = "OVC_OrdenVentaCancelacionId", nullable = false, insertable = true, updatable = true)
    private List<OrdenVentaCancelacionDetalle> detalles;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "OVCA_OVC_OrdenVentaCancelacionId", referencedColumnName = "OVC_OrdenVentaCancelacionId", nullable = false, insertable = true, updatable = true)
    private List<OrdenVentaCancelacionArchivo> archivos;

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

    public int getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(int tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public ControlMaestroMultiple getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(ControlMaestroMultiple tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Integer getMotivoDevolucionId() {
        return motivoDevolucionId;
    }

    public void setMotivoDevolucionId(Integer motivoDevolucionId) {
        this.motivoDevolucionId = motivoDevolucionId;
    }

    public ControlMaestroMultiple getMotivoDevolucion() {
        return motivoDevolucion;
    }

    public void setMotivoDevolucion(ControlMaestroMultiple motivoDevolucion) {
        this.motivoDevolucion = motivoDevolucion;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public Integer getMotivoCancelacionId() {
        return motivoCancelacionId;
    }

    public void setMotivoCancelacionId(Integer motivoCancelacionId) {
        this.motivoCancelacionId = motivoCancelacionId;
    }

    public ControlMaestroMultiple getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(ControlMaestroMultiple motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public BigDecimal getImporteReembolsar() {
        return importeReembolsar;
    }

    public void setImporteReembolsar(BigDecimal importeReembolsar) {
        this.importeReembolsar = importeReembolsar;
    }

    public ControlMaestroMultiple getTipoCancelacion() {
        return tipoCancelacion;
    }

    public void setTipoCancelacion(ControlMaestroMultiple tipoCancelacion) {
        this.tipoCancelacion = tipoCancelacion;
    }

    public Integer getTipoCancelacionId() {
        return tipoCancelacionId;
    }

    public void setTipoCancelacionId(Integer tipoCancelacionId) {
        this.tipoCancelacionId = tipoCancelacionId;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public List<OrdenVentaCancelacionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenVentaCancelacionDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<OrdenVentaCancelacionArchivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<OrdenVentaCancelacionArchivo> archivos) {
        this.archivos = archivos;
    }
}