package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Rene Carrillo on 25/11/2022.
 */
@Entity
@Table(name = "ProgramasIdiomasCertificacionesDescuentos")
public class ProgramaIdiomaCertificacionDescuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PICD_ProgramaIdiomaCertificacionDescuentoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PICD_PROGIC_ProgramaIdiomaCertificacionId", length = 50, nullable = false, insertable = true, updatable = true)
    private Integer programaIdiomaCertificacionId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PICD_PROGIC_ProgramaIdiomaCertificacionId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGIC_ProgramaIdiomaCertificacionId")
    private ProgramaIdiomaCertificacion programaIdiomaCertificacion;

    // Estatus
    @Column(name = "PICD_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "PICD_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @CreationTimestamp
    @Column(name = "PICD_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "PICD_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PICD_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PICD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "PICD_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PICD_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PICDD_PICD_ProgramaIdiomaCertificacionDescuentoId", nullable = false, insertable = true, updatable = true, referencedColumnName = "PICD_ProgramaIdiomaCertificacionDescuentoId")
    private List<ProgramaIdiomaCertificacionDescuentoDetalle> listaDescuento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaIdiomaCertificacionId() {
        return programaIdiomaCertificacionId;
    }

    public void setProgramaIdiomaCertificacionId(Integer programaIdiomaCertificacionId) {
        this.programaIdiomaCertificacionId = programaIdiomaCertificacionId;
    }

    public ProgramaIdiomaCertificacion getProgramaIdiomaCertificacion() {
        return programaIdiomaCertificacion;
    }

    public void setProgramaIdiomaCertificacion(ProgramaIdiomaCertificacion programaIdiomaCertificacion) {
        this.programaIdiomaCertificacion = programaIdiomaCertificacion;
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

    public List<ProgramaIdiomaCertificacionDescuentoDetalle> getListaDescuento() {
        return listaDescuento;
    }

    public void setListaDescuento(List<ProgramaIdiomaCertificacionDescuentoDetalle> listaDescuento) {
        this.listaDescuento = listaDescuento;
    }
}
