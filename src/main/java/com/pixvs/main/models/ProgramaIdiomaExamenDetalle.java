package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasIdiomasExamenesDetalles")
public class ProgramaIdiomaExamenDetalle {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGIED_ProgramaIdiomaExamenDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGIED_PROGIE_ProgramaIdiomaExamenId", nullable = true, insertable = false, updatable = false)
    private Integer programaIdiomaExamenId;

    //Actividad Evaluación
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGIED_PAAE_ActividadEvaluacionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAAE_ActividadEvaluacionId")
    private PAActividadEvaluacion actividadEvaluacion;

    @Column(name = "PROGIED_PAAE_ActividadEvaluacionId", nullable = true, insertable = true, updatable = true)
    private Integer actividadEvaluacionId;

    //Test
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGIED_CMM_TestId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple test;

    @Column(name = "PROGIED_CMM_TestId", nullable = true, insertable = true, updatable = true)
    private Integer testId;

    @Column(name = "PROGIED_Time", nullable = true, insertable = true, updatable = true)
    private Integer time;

    @Column(name = "PROGIED_Puntaje", nullable = true, insertable = true, updatable = true)
    private BigDecimal puntaje;

    @Column(name = "PROGIED_Continuos", insertable = true, updatable = true)
    private Boolean continuos;

    @Column(name = "PROGIED_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @OneToMany( cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId", referencedColumnName = "PROGIED_ProgramaIdiomaExamenDetalleId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaExamenModalidad> modalidades;

    @OneToMany( cascade = {CascadeType.ALL},fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "PROGIEU_PROGIED_ProgramaIdiomaExamenDetalleId", referencedColumnName = "PROGIED_ProgramaIdiomaExamenDetalleId", nullable = false, insertable = true, updatable = true)
    private List<ProgramaIdiomaExamenUnidad> unidades;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProgramaIdiomaExamenId() {
        return programaIdiomaExamenId;
    }

    public void setProgramaIdiomaExamenId(Integer programaIdiomaExamenId) {
        this.programaIdiomaExamenId = programaIdiomaExamenId;
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

    public ControlMaestroMultiple getTest() {
        return test;
    }

    public void setTest(ControlMaestroMultiple test) {
        this.test = test;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BigDecimal getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<ProgramaIdiomaExamenModalidad> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<ProgramaIdiomaExamenModalidad> modalidades) {
        this.modalidades = modalidades;
    }

    public List<ProgramaIdiomaExamenUnidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<ProgramaIdiomaExamenUnidad> unidades) {
        this.unidades = unidades;
    }

    public Boolean getContinuos() {
        return continuos;
    }

    public void setContinuos(Boolean continuos) {
        this.continuos = continuos;
    }
}
