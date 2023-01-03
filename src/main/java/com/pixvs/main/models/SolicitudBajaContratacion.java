package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Rene Carrillo on 21/04/2022.
 */
@Entity
@Table(name = "SolicitudesBajasContrataciones")
public class SolicitudBajaContratacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SBC_SolicitudBajaContratacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "SBC_Codigo", length = 50, nullable = false, insertable = true, updatable = false)
    private String codigo;

    // Empleado Contrato
    @Column(name = "SBC_EMPCO_EmpleadoContratoId", nullable = false, insertable = true, updatable = true)
    private Integer empleadoContratoId;

    @OneToOne
    @JoinColumn(name = "SBC_EMPCO_EmpleadoContratoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EMPCO_EmpleadoContratoId")
    private EmpleadoContrato empleadoContrato;

    // Tipo Motivo
    @Column(name = "SBC_CMM_TipoMotivoId", nullable = false, insertable = true, updatable = true)
    private Integer tipoMotivoId;

    @OneToOne
    @JoinColumn(name = "SBC_CMM_TipoMotivoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoMotivo;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "SBC_FechaSeparacion", nullable = false, insertable = true, updatable = true)
    private Date fechaSeparacion;

    @Column(name = "SBC_Comentario", length = 500, nullable = true, insertable = true, updatable = true)
    private String comentario;

    // Estatus
    @Column(name = "SBC_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "SBC_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @CreationTimestamp
    @Column(name = "SBC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "SBC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "SBC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "SBC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "SBC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "SBC_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

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

    public Integer getEmpleadoContratoId() {
        return empleadoContratoId;
    }

    public void setEmpleadoContratoId(Integer empleadoContratoId) {
        this.empleadoContratoId = empleadoContratoId;
    }

    public EmpleadoContrato getEmpleadoContrato() {
        return empleadoContrato;
    }

    public void setEmpleadoContrato(EmpleadoContrato empleadoContrato) {
        this.empleadoContrato = empleadoContrato;
    }

    public Integer getTipoMotivoId() {
        return tipoMotivoId;
    }

    public void setTipoMotivoId(Integer tipoMotivoId) {
        this.tipoMotivoId = tipoMotivoId;
    }

    public ControlMaestroMultiple getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(ControlMaestroMultiple tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    public Date getFechaSeparacion() {
        return fechaSeparacion;
    }

    public void setFechaSeparacion(Date fechaSeparacion) {
        this.fechaSeparacion = fechaSeparacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
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

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }
}
