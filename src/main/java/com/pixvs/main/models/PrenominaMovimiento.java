package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 07/12/2021.
 */
@Entity
@Table(name = "PrenominaMovimientos")
public class PrenominaMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRENOM_PrenominaMovimientoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRENOM_EMP_EmpleadoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "PRENOM_EMP_EmpleadoId", nullable = false, insertable = true, updatable = false)
    private Integer empleadoId;

    @Column(name = "PRENOM_FechaInicioPeriodo", nullable = false, insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fechaInicioPeriodo;

    @Column(name = "PRENOM_FechaFinPeriodo", nullable = false, insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fechaFinPeriodo;

    @Column(name = "PRENOM_FechaInicioQuincena", nullable = false, insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fechaInicioQuincena;

    @Column(name = "PRENOM_FechaFinQuincena", nullable = false, insertable = true, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    private Date fechaFinQuincena;

    @Column(name = "PRENOM_HorasDeduccion", nullable = true, insertable = true, updatable = false)
    private BigDecimal horasDeduccion;

    @Column(name = "PRENOM_HorasPercepcion", nullable = true, insertable = true, updatable = false)
    private BigDecimal horasPercepcion;

    @Column(name = "PRENOM_SueldoPorHora", nullable = false, insertable = true, updatable = false)
    private BigDecimal sueldoPorHora;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRENOM_CMM_TipoMovimientoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoMovimiento;

    @Column(name = "PRENOM_CMM_TipoMovimientoId", nullable = false, insertable = true, updatable = false)
    private Integer tipoMovimientoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRENOM_PRENOM_MovimientoReferenciaId", nullable = true, insertable = false, updatable = false, referencedColumnName = "PRENOM_PrenominaMovimientoId")
    private PrenominaMovimiento movimientoReferencia;

    @Column(name = "PRENOM_PRENOM_MovimientoReferenciaId", nullable = true, insertable = true, updatable = false)
    private Integer movimientoReferenciaId;

    @CreationTimestamp
    @Column(name = "PRENOM_FechaCorte", nullable = false, insertable = true, updatable = false)
    private Date fechaCorte;

    @Column(name = "PRENOM_ReferenciaProcesoTabla", nullable = true, insertable = true, updatable = false)
    private String referenciaProcesoTabla;

    @Column(name = "PRENOM_ReferenciaProcesoId", nullable = true, insertable = true, updatable = false)
    private Integer referenciaProcesoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getFechaInicioPeriodo() {
        return fechaInicioPeriodo;
    }

    public void setFechaInicioPeriodo(Date fechaInicioPeriodo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
    }

    public Date getFechaFinPeriodo() {
        return fechaFinPeriodo;
    }

    public void setFechaFinPeriodo(Date fechaFinPeriodo) {
        this.fechaFinPeriodo = fechaFinPeriodo;
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

    public BigDecimal getHorasDeduccion() {
        return horasDeduccion;
    }

    public void setHorasDeduccion(BigDecimal horasDeduccion) {
        this.horasDeduccion = horasDeduccion;
    }

    public BigDecimal getHorasPercepcion() {
        return horasPercepcion;
    }

    public void setHorasPercepcion(BigDecimal horasPercepcion) {
        this.horasPercepcion = horasPercepcion;
    }

    public BigDecimal getSueldoPorHora() {
        return sueldoPorHora;
    }

    public void setSueldoPorHora(BigDecimal sueldoPorHora) {
        this.sueldoPorHora = sueldoPorHora;
    }

    public ControlMaestroMultiple getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(ControlMaestroMultiple tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getTipoMovimientoId() {
        return tipoMovimientoId;
    }

    public void setTipoMovimientoId(Integer tipoMovimientoId) {
        this.tipoMovimientoId = tipoMovimientoId;
    }

    public PrenominaMovimiento getMovimientoReferencia() {
        return movimientoReferencia;
    }

    public void setMovimientoReferencia(PrenominaMovimiento movimientoReferencia) {
        this.movimientoReferencia = movimientoReferencia;
    }

    public Integer getMovimientoReferenciaId() {
        return movimientoReferenciaId;
    }

    public void setMovimientoReferenciaId(Integer movimientoReferenciaId) {
        this.movimientoReferenciaId = movimientoReferenciaId;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public String getReferenciaProcesoTabla() {
        return referenciaProcesoTabla;
    }

    public void setReferenciaProcesoTabla(String referenciaProcesoTabla) {
        this.referenciaProcesoTabla = referenciaProcesoTabla;
    }

    public Integer getReferenciaProcesoId() {
        return referenciaProcesoId;
    }

    public void setReferenciaProcesoId(Integer referenciaProcesoId) {
        this.referenciaProcesoId = referenciaProcesoId;
    }
}
