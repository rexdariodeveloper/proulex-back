package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 25/11/2021.
 */
@Entity
@Table(name = "ProgramasGruposProfesores")
public class ProgramaGrupoProfesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRUP_ProgramaGrupoProfesorId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROGRUP_PROGRU_GrupoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "PROGRU_GrupoId")
    private ProgramaGrupo grupo;

    @Column(name = "PROGRUP_PROGRU_GrupoId", nullable = false, insertable = true, updatable = false)
    private Integer grupoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROGRUP_EMP_EmpleadoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "PROGRUP_EMP_EmpleadoId", nullable = false, insertable = true, updatable = false)
    private Integer empleadoId;

    @Column(name = "PROGRUP_FechaInicio", nullable = false, insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fechaInicio;

    @Column(name = "PROGRUP_Motivo", length = 255, nullable = true, insertable = true, updatable = false)
    private String motivo;

    @Column(name = "PROGRUP_Sueldo", insertable = true, updatable = true)
    private BigDecimal sueldo;

    @CreationTimestamp
    @Column(name = "PROGRUP_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "PROGRUP_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProgramaGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(ProgramaGrupo grupo) {
        this.grupo = grupo;
    }

    public Integer getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Integer grupoId) {
        this.grupoId = grupoId;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
