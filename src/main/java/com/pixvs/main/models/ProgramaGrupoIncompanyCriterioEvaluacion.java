package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyCriteriosEvaluacion")
public class ProgramaGrupoIncompanyCriterioEvaluacion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCCE_ProgramaIncompanyHorarioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCCE_PROGRU_GrupoId", nullable = true, insertable = false, updatable = false)
    private Integer grupoId;

    //Test
    @OneToOne()
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "PGINCCE_PAAE_ActividadEvaluacionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAAE_ActividadEvaluacionId")
    private PAActividadEvaluacion actividadEvaluacion;

    @Column(name = "PGINCCE_PAAE_ActividadEvaluacionId", nullable = true, insertable = true, updatable = false)
    private Integer actividadEvaluacionId;

    //Modalidad
    @OneToOne()
    @JoinColumn(name = "PGINCCE_PAMOD_ModalidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAMOD_ModalidadId")
    private PAModalidad modalidad;

    @Column(name = "PGINCCE_PAMOD_ModalidadId", nullable = true, insertable = true, updatable = true)
    private Integer modalidadId;

    //Idioma
    @OneToOne()
    @JoinColumn(name = "PGINCCE_CMM_TestFormatId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple testFormat;

    @Column(name = "PGINCCE_CMM_TestFormatId", nullable = false, insertable = true, updatable = true)
    private Integer testFormatId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PGINCCE_FechaAplica", nullable = true, insertable = true, updatable = true)
    private Date fechaAplica;

    @Column(name = "PGINCCE_Score", nullable = true, insertable = true, updatable = true)
    private Integer score;

    @Column(name = "PGINCCE_Time", nullable = true, insertable = true, updatable = true)
    private Integer time;

    @Column(name = "PGINCCE_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "PGINCCE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PGINCCE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PGINCCE_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Transient
    private Integer dias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public PAActividadEvaluacion getActividadEvaluacion() {
        return actividadEvaluacion;
    }

    public void setActividadEvaluacion(PAActividadEvaluacion actividadEvaluacion) {
        this.actividadEvaluacion = actividadEvaluacion;
    }

    public Integer getActividadEvaluacionId() {
        return actividadEvaluacionId;
    }

    public void setActividadEvaluacionId(Integer actividadEvaluacionId) {
        this.actividadEvaluacionId = actividadEvaluacionId;
    }

    public PAModalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(PAModalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Integer modalidadId) {
        this.modalidadId = modalidadId;
    }

    public ControlMaestroMultiple getTestFormat() {
        return testFormat;
    }

    public void setTestFormat(ControlMaestroMultiple testFormat) {
        this.testFormat = testFormat;
    }

    public Integer getTestFormatId() {
        return testFormatId;
    }

    public void setTestFormatId(Integer testFormatId) {
        this.testFormatId = testFormatId;
    }

    public Date getFechaAplica() {
        return fechaAplica;
    }

    public void setFechaAplica(Date fechaAplica) {
        this.fechaAplica = fechaAplica;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }
}
