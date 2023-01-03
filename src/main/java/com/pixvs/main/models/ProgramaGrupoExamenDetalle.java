package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "ProgramasGruposExamenesDetalles")
public class ProgramaGrupoExamenDetalle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRUED_ProgramaIdiomaExamenDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGRUED_PROGIE_ProgramaIdiomaExamenId", nullable = true, insertable = false, updatable = false)
    private Integer programaIdiomaExamenId;
    
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRUED_PAAE_ActividadEvaluacionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PAAE_ActividadEvaluacionId")
    private PAActividadEvaluacion actividadEvaluacion;

    @Column(name = "PROGRUED_PAAE_ActividadEvaluacionId", nullable = true, insertable = true, updatable = true)
    private Integer actividadEvaluacionId;
    
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRUED_CMM_TestId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple test;

    @Column(name = "PROGRUED_CMM_TestId", nullable = true, insertable = true, updatable = true)
    private Integer testId;

    @Column(name = "PROGRUED_Time", nullable = true, insertable = true, updatable = true)
    private Integer time;

    @Column(name = "PROGRUED_Puntaje", nullable = true, insertable = true, updatable = true)
    private BigDecimal puntaje;

    @Column(name = "PROGRUED_Continuos", insertable = true, updatable = true)
    private Boolean continuos;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getProgramaIdiomaExamenId() { return programaIdiomaExamenId; }
    public void setProgramaIdiomaExamenId(Integer programaIdiomaExamenId) { this.programaIdiomaExamenId = programaIdiomaExamenId; }

    public PAActividadEvaluacion getActividadEvaluacion() { return actividadEvaluacion; }
    public void setActividadEvaluacion(PAActividadEvaluacion actividadEvaluacion) { this.actividadEvaluacion = actividadEvaluacion; }

    public Integer getActividadEvaluacionId() { return actividadEvaluacionId; }
    public void setActividadEvaluacionId(Integer actividadEvaluacionId) { this.actividadEvaluacionId = actividadEvaluacionId; }

    public ControlMaestroMultiple getTest() { return test; }
    public void setTest(ControlMaestroMultiple test) { this.test = test; }

    public Integer getTestId() { return testId; }
    public void setTestId(Integer testId) { this.testId = testId; }

    public Integer getTime() { return time; }
    public void setTime(Integer time) { this.time = time; }

    public BigDecimal getPuntaje() { return puntaje; }
    public void setPuntaje(BigDecimal puntaje) { this.puntaje = puntaje; }

    public Boolean getContinuos() { return continuos; }
    public void setContinuos(Boolean continuos) { this.continuos = continuos; }
}
