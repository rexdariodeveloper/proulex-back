package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Rene Carrillo on 31/03/2022.
 */
@Entity
@Table(name = "EmpleadosHorarios")
public class EmpleadoHorario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPH_EmpleadoHorarioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EMPH_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false)
    private Integer empleadoId;

    @Column(name = "EMPH_Dia", nullable = false, insertable = true, updatable = true)
    private String dia;

    @Column(name = "EMPH_HoraInicio", nullable = true, insertable = true, updatable = true)
    private Time horaInicio;

    @Column(name = "EMPH_HoraFin", nullable = true, insertable = true, updatable = true)
    private Time horaFin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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
}
