package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Rene Carrillo on 21/04/2022.
 */
@Entity
@Table(name = "SolicitudesRenovacionesContrataciones")

//@NamedStoredProcedureQuery(name = "sp_getRenovacionesConFiltros", procedureName = "sp_getRenovacionesConFiltros", parameters = {@StoredProcedureParameter(mode = ParameterMode.IN,name = "fechaInicio",type=String.class), @StoredProcedureParameter(mode = ParameterMode.IN,name = "fechaFin",type=String.class)})

public class SolicitudRenovacionContratacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SRC_SolicitudRenovacionContratacionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "SRC_Codigo", length = 50, nullable = false, insertable = true, updatable = false)
    private String codigo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "SRC_FechaInicio", nullable = false)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "SRC_FechaFin", nullable = false)
    private Date fechaFin;

    // Estatus
    @Column(name = "SRC_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "SRC_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @CreationTimestamp
    @Column(name = "SRC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "SRC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "SRC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "SRC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "SRC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "SRC_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "SRCD_SRC_SolicitudRenovacionContratacionId", nullable = false, insertable = true, updatable = true, referencedColumnName = "SRC_SolicitudRenovacionContratacionId")
    private List<SolicitudRenovacionContratacionDetalle> listaSolicitudRenovacionContratacionDetalle;

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
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

    public List<SolicitudRenovacionContratacionDetalle> getListaSolicitudRenovacionContratacionDetalle() {
        return listaSolicitudRenovacionContratacionDetalle;
    }

    public void setListaSolicitudRenovacionContratacionDetalle(List<SolicitudRenovacionContratacionDetalle> listaSolicitudRenovacionContratacionDetalle) {
        this.listaSolicitudRenovacionContratacionDetalle = listaSolicitudRenovacionContratacionDetalle;
    }
}
