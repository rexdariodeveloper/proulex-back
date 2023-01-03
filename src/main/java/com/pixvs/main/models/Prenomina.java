package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "Prenominas")
public class Prenomina {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRENO_PrenominaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PRENO_PROGRU_GrupoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PROGRU_GrupoId")
    private ProgramaGrupo programaGrupo;

    @Column(name = "PRENO_PROGRU_GrupoId", nullable = true, insertable = true, updatable = true)
    private Integer programaGrupoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PRENO_EDP_EmpleadoDeduccionPercepcionId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EDP_EmpleadoDeduccionPercepcionId")
    private EmpleadoDeduccionPercepcion empleadoDeduccionPercepcion;

    @Column(name = "PRENO_EDP_EmpleadoDeduccionPercepcionId", nullable = true, insertable = true, updatable = true)
    private Integer empleadoDeduccionPercepcionId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PRENO_FechaInicioQuincena", nullable = false)
    private Date fechaInicioQuincena;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PRENO_FechaFinQuincena", nullable = false)
    private Date fechaFinQuincena;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PRENO_EMP_EmpleadoId", nullable = true, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "PRENO_EMP_EmpleadoId", nullable = true, insertable = true, updatable = true)
    private Integer empleadoId;

    @Column(name = "PRENO_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PRENO_DiasFestivo", nullable = true)
    private Date fechaDiaFestivo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PRENO_FechaClaseSuplida", nullable = true)
    private Date fechaDiaSuplida;

    @Column(name = "PRENO_SueldoPorHora", nullable = true, insertable = true, updatable = false)
    private BigDecimal sueldoPorHora;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PRENO_FechaClaseCancelada", nullable = true)
    private Date fechaClaseCancelada;

    @Column(name = "PRENO_EsApoyoTransporte", nullable = true)
    private Boolean esApoyoTransporte;

    @CreationTimestamp
    @Column(name = "PRENO_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PRENO_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PRENO_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PRENO_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PRENO_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PRENO_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProgramaGrupo getProgramaGrupo() {
        return programaGrupo;
    }

    public void setProgramaGrupo(ProgramaGrupo programaGrupo) {
        this.programaGrupo = programaGrupo;
    }

    public Integer getProgramaGrupoId() {
        return programaGrupoId;
    }

    public void setProgramaGrupoId(Integer programaGrupoId) {
        this.programaGrupoId = programaGrupoId;
    }

    public EmpleadoDeduccionPercepcion getEmpleadoDeduccionPercepcion() {
        return empleadoDeduccionPercepcion;
    }

    public void setEmpleadoDeduccionPercepcion(EmpleadoDeduccionPercepcion empleadoDeduccionPercepcion) {
        this.empleadoDeduccionPercepcion = empleadoDeduccionPercepcion;
    }

    public Integer getEmpleadoDeduccionPercepcionId() {
        return empleadoDeduccionPercepcionId;
    }

    public void setEmpleadoDeduccionPercepcionId(Integer empleadoDeduccionPercepcionId) {
        this.empleadoDeduccionPercepcionId = empleadoDeduccionPercepcionId;
    }

    public Date getFechaInicioQuincena() {
        return fechaInicioQuincena;
    }

    public void setFechaInicioQuincena(Date fechaInicioQuincena) {
        this.fechaInicioQuincena = fechaInicioQuincena;
    }

    public Date getFechaFinQuincena() {
        return fechaFinQuincena;
    }

    public void setFechaFinQuincena(Date fechaFinQuincena) {
        this.fechaFinQuincena = fechaFinQuincena;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
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

    public Date getFechaDiaFestivo() {
        return fechaDiaFestivo;
    }

    public void setFechaDiaFestivo(Date fechaDiaFestivo) {
        this.fechaDiaFestivo = fechaDiaFestivo;
    }

    public Date getFechaDiaSuplida() {
        return fechaDiaSuplida;
    }

    public void setFechaDiaSuplida(Date fechaDiaSuplida) {
        this.fechaDiaSuplida = fechaDiaSuplida;
    }

    public BigDecimal getSueldoPorHora() {
        return sueldoPorHora;
    }

    public void setSueldoPorHora(BigDecimal sueldoPorHora) {
        this.sueldoPorHora = sueldoPorHora;
    }

    public Date getFechaClaseCancelada() {
        return fechaClaseCancelada;
    }

    public void setFechaClaseCancelada(Date fechaClaseCancelada) {
        this.fechaClaseCancelada = fechaClaseCancelada;
    }

    public Boolean getEsApoyoTransporte() {
        return esApoyoTransporte;
    }

    public void setEsApoyoTransporte(Boolean esApoyoTransporte) {
        this.esApoyoTransporte = esApoyoTransporte;
    }
}
