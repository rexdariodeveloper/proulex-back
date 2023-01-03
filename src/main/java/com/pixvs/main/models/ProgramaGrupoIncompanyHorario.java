package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.TransactionScoped;
import java.sql.Time;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposIncompanyHorarios")
public class ProgramaGrupoIncompanyHorario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PGINCH_ProgramaIncompanyHorarioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PGINCH_PROGRU_GrupoId", nullable = true, insertable = false, updatable = false)
    private Integer grupoId;

    @Column(name = "PGINCH_Dia", nullable = true, insertable = true, updatable = true)
    private String dia;

    @Column(name = "PGINCH_HoraInicio", nullable = true, insertable = true, updatable = true)
    private Time horaInicio;

    @Column(name = "PGINCH_HoraFin", nullable = true, insertable = true, updatable = true)
    private Time horaFin;

    @OneToOne
    @JoinColumn(name = "PGINCH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PGINCH_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PGINCH_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

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
