package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "DocumentosConfiguracionRH")
public class DocumentosConfiguracionRH {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DCRH_DocumentosConfiguracionRHId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    // Tipo de Proceso RH
    @Column(name = "DCRH_CMM_TipoProcesoRHId", nullable = false, insertable = true, updatable = false)
    private Integer tipoProcesoRHId;

    @OneToOne
    @JoinColumn(name = "DCRH_CMM_TipoProcesoRHId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoProcesoRH;

    // Tipo de Documento
    @Column(name = "DCRH_CMM_TipoDocumentoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoDocumentoId;

    @OneToOne
    @JoinColumn(name = "DCRH_CMM_TipoDocumentoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoDocumento;

    // Tipo de Contrato
    @Column(name = "DCRH_CMM_TipoContratoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoContratoId;

    @OneToOne
    @JoinColumn(name = "DCRH_CMM_TipoContratoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoContrato;

    // Tipo de Opcion
    @Column(name = "DCRH_CMM_TipoOpcionId", nullable = false, insertable = true, updatable = true)
    private Integer tipoOpcionId;

    @OneToOne
    @JoinColumn(name = "DCRH_CMM_TipoOpcionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoOpcion;

    // Tipo de Vigencia
    @Column(name = "DCRH_CMM_TipoVigenciaId", nullable = true, insertable = true, updatable = true)
    private Integer tipoVigenciaId;

    @OneToOne
    @JoinColumn(name = "DCRH_CMM_TipoVigenciaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoVigencia;

    // Tipo de Tiempo
    @Column(name = "DCRH_CMM_TipoTiempoId", nullable = true, insertable = true, updatable = true)
    private Integer tipoTiempoId;

    @OneToOne
    @JoinColumn(name = "DCRH_CMM_TipoTiempoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoTiempo;

    @Column(name = "DCRH_VigenciaCantidad", nullable = true, insertable = true, updatable = true)
    private BigDecimal vigenciaCantidad;

    @CreationTimestamp
    @Column(name = "DCRH_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "DCRH_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "DCRH_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "DCRH_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

    @Column(name = "DCRH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "DCRH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTipoProcesoRHId() {
        return tipoProcesoRHId;
    }

    public void setTipoProcesoRHId(Integer tipoProcesoRHId) {
        this.tipoProcesoRHId = tipoProcesoRHId;
    }

    public ControlMaestroMultiple getTipoProcesoRH() {
        return tipoProcesoRH;
    }

    public void setTipoProcesoRH(ControlMaestroMultiple tipoProcesoRH) {
        this.tipoProcesoRH = tipoProcesoRH;
    }

    public Integer getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public void setTipoDocumentoId(Integer tipoDocumentoId) {
        this.tipoDocumentoId = tipoDocumentoId;
    }

    public ControlMaestroMultiple getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(ControlMaestroMultiple tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Integer getTipoContratoId() {
        return tipoContratoId;
    }

    public void setTipoContratoId(Integer tipoContratoId) {
        this.tipoContratoId = tipoContratoId;
    }

    public ControlMaestroMultiple getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(ControlMaestroMultiple tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public Integer getTipoOpcionId() {
        return tipoOpcionId;
    }

    public void setTipoOpcionId(Integer tipoOpcionId) {
        this.tipoOpcionId = tipoOpcionId;
    }

    public ControlMaestroMultiple getTipoOpcion() {
        return tipoOpcion;
    }

    public void setTipoOpcion(ControlMaestroMultiple tipoOpcion) {
        this.tipoOpcion = tipoOpcion;
    }

    public Integer getTipoVigenciaId() {
        return tipoVigenciaId;
    }

    public void setTipoVigenciaId(Integer tipoVigenciaId) {
        this.tipoVigenciaId = tipoVigenciaId;
    }

    public ControlMaestroMultiple getTipoVigencia() {
        return tipoVigencia;
    }

    public void setTipoVigencia(ControlMaestroMultiple tipoVigencia) {
        this.tipoVigencia = tipoVigencia;
    }

    public Integer getTipoTiempoId() {
        return tipoTiempoId;
    }

    public void setTipoTiempoId(Integer tipoTiempoId) {
        this.tipoTiempoId = tipoTiempoId;
    }

    public ControlMaestroMultiple getTipoTiempo() {
        return tipoTiempo;
    }

    public void setTipoTiempo(ControlMaestroMultiple tipoTiempo) {
        this.tipoTiempo = tipoTiempo;
    }

    public BigDecimal getVigenciaCantidad() {
        return vigenciaCantidad;
    }

    public void setVigenciaCantidad(BigDecimal vigenciaCantidad) {
        this.vigenciaCantidad = vigenciaCantidad;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Integer getModificadoPorId() {
        return modificadoPorId;
    }

    public void setModificadoPorId(Integer modificadoPorId) {
        this.modificadoPorId = modificadoPorId;
    }

    public Usuario getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(Usuario modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
}
