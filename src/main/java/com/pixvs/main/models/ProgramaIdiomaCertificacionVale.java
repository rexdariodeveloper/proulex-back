package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Rene Carrillo on 06/12/2022.
 */
@Entity
@Table(name = "ProgramasIdiomasCertificacionesVales")
public class ProgramaIdiomaCertificacionVale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PICV_ProgramaIdiomaCertificacionValeId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PICV_ALUG_AlumnoGrupoId", nullable = false, insertable = true, updatable = true)
    private Integer alumnoGrupoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PICV_ALUG_AlumnoGrupoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ALUG_AlumnoGrupoId")
    private AlumnoGrupo alumnoGrupo;

    @Column(name = "PICV_PICD_ProgramaIdiomaCertificacionDescuentoId", nullable = false, insertable = true, updatable = true)
    private Integer programaIdiomaCertificacionDescuentoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PICV_PICD_ProgramaIdiomaCertificacionDescuentoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PICD_ProgramaIdiomaCertificacionDescuentoId")
    private ProgramaIdiomaCertificacionDescuento programaIdiomaCertificacionDescuento;

    @Column(name = "PICV_PorcentajeDescuento", nullable = false, insertable = true, updatable = true)
    private BigDecimal porcentajeDescuento;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PICV_FechaVigenciaInicio",  nullable = false, insertable = true, updatable = true)
    private Date fechaVigenciaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PICV_FechaVigenciaFin",  nullable = false, insertable = true, updatable = true)
    private Date fechaVigenciaFin;

    @Column(name = "PICV_Costo", nullable = false, insertable = true, updatable = true)
    private BigDecimal costo;

    @Column(name = "PICV_CostoFinal", nullable = false, insertable = true, updatable = true)
    private BigDecimal costoFinal;

    @Column(name = "PICV_CMM_EstatusId", nullable = false, insertable = true, updatable = true)
    private Integer estatusId;

    @OneToOne
    @JoinColumn(name = "PICV_CMM_EstatusId", nullable = true, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @CreationTimestamp
    @Column(name = "PICV_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "PICV_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PICV_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PICV_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @OneToOne
    @JoinColumn(name = "PICV_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PICV_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlumnoGrupoId() {
        return alumnoGrupoId;
    }

    public void setAlumnoGrupoId(Integer alumnoGrupoId) {
        this.alumnoGrupoId = alumnoGrupoId;
    }

    public AlumnoGrupo getAlumnoGrupo() {
        return alumnoGrupo;
    }

    public void setAlumnoGrupo(AlumnoGrupo alumnoGrupo) {
        this.alumnoGrupo = alumnoGrupo;
    }

    public Integer getProgramaIdiomaCertificacionDescuentoId() {
        return programaIdiomaCertificacionDescuentoId;
    }

    public void setProgramaIdiomaCertificacionDescuentoId(Integer programaIdiomaCertificacionDescuentoId) {
        this.programaIdiomaCertificacionDescuentoId = programaIdiomaCertificacionDescuentoId;
    }

    public ProgramaIdiomaCertificacionDescuento getProgramaIdiomaCertificacionDescuento() {
        return programaIdiomaCertificacionDescuento;
    }

    public void setProgramaIdiomaCertificacionDescuento(ProgramaIdiomaCertificacionDescuento programaIdiomaCertificacionDescuento) {
        this.programaIdiomaCertificacionDescuento = programaIdiomaCertificacionDescuento;
    }

    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Date getFechaVigenciaInicio() {
        return fechaVigenciaInicio;
    }

    public void setFechaVigenciaInicio(Date fechaVigenciaInicio) {
        this.fechaVigenciaInicio = fechaVigenciaInicio;
    }

    public Date getFechaVigenciaFin() {
        return fechaVigenciaFin;
    }

    public void setFechaVigenciaFin(Date fechaVigenciaFin) {
        this.fechaVigenciaFin = fechaVigenciaFin;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getCostoFinal() {
        return costoFinal;
    }

    public void setCostoFinal(BigDecimal costoFinal) {
        this.costoFinal = costoFinal;
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
