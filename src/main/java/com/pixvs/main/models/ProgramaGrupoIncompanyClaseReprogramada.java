package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyClasesReprogramadas")
public class ProgramaGrupoIncompanyClaseReprogramada {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCCR_ProgramaIncompanyHorarioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCCR_PROGRU_GrupoId", nullable = false, insertable = false, updatable = false)
    private Integer grupoId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PGINCCR_FechaReprogramar", nullable = true, insertable = true, updatable = true)
    private Date fechaReprogramar;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PGINCCR_FechaNueva", nullable = true, insertable = true, updatable = true)
    private Date fechaNueva;

    @Column(name = "PGINCCR_HoraInicio", nullable = true, insertable = true, updatable = true)
    private Time horaInicio;

    @Column(name = "PGINCCR_HoraFin", nullable = true, insertable = true, updatable = true)
    private Time horaFin;

    @Column(name = "PGINCCR_Motivo", nullable = true, insertable = true, updatable = true)
    private String motivo;

    @Transient
    private String horaInicioString;

    @Transient
    private String horaFinString;

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

    public Date getFechaReprogramar() {
        return fechaReprogramar;
    }

    public void setFechaReprogramar(Date fechaReprogramar) {
        this.fechaReprogramar = fechaReprogramar;
    }

    public Date getFechaNueva() {
        return fechaNueva;
    }

    public void setFechaNueva(Date fechaNueva) {
        this.fechaNueva = fechaNueva;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getHoraInicioString() {
        return horaInicioString;
    }

    public void setHoraInicioString(String horaInicioString) {
        this.horaInicioString = horaInicioString;
    }

    public String getHoraFinString() {
        return horaFinString;
    }

    public void setHoraFinString(String horaFinString) {
        this.horaFinString = horaFinString;
    }
}
