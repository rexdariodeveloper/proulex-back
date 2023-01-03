package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.javafaker.Bool;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "ProgramasGruposListadoClases")
public class ProgramaGrupoListadoClase {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRULC_ProgramaGrupoListadoClaseId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PROGRULC_PROGRU_GrupoId", nullable = true, insertable = false, updatable = false)
    private Integer grupoId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PROGRULC_Fecha", nullable = true)
    private Date fecha;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRULC_EMP_EmpleadoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "PROGRULC_EMP_EmpleadoId", nullable = true, insertable = true, updatable = true)
    private Integer empleadoId;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "PROGRULC_CMM_FormaPagoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple formaPago;

    @Column(name = "PROGRULC_CMM_FormaPagoId", nullable = true, insertable = true, updatable = true)
    private Integer formaPagoId;

    @Column(name = "PROGRULC_Comentario",length = 500,nullable = true, insertable = true, updatable = true)
    private String comentario;

    @Column(name = "PROGRULC_CategoriaProfesor", nullable = true, insertable = true, updatable = true)
    private String categoriaProfesor;

    @Column(name = "PROGRULC_SueldoProfesor", nullable = true, insertable = true, updatable = true)
    private BigDecimal sueldoProfesor;

    @CreationTimestamp
    @Column(name = "PROGRULC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @Column(name = "PROGRULC_FechaPago")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    private Date fechaPago;

    @Column(name = "PROGRULC_FechaDeduccion")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    private Date fechaDeduccion;

    @Transient
    Boolean changeDetected;

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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Boolean getChangeDetected() {
        return changeDetected;
    }

    public void setChangeDetected(Boolean changeDetected) {
        this.changeDetected = changeDetected;
    }

    public ControlMaestroMultiple getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(ControlMaestroMultiple formaPago) {
        this.formaPago = formaPago;
    }

    public Integer getFormaPagoId() {
        return formaPagoId;
    }

    public void setFormaPagoId(Integer formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCategoriaProfesor() {
        return categoriaProfesor;
    }

    public void setCategoriaProfesor(String categoriaProfesor) {
        this.categoriaProfesor = categoriaProfesor;
    }

    public BigDecimal getSueldoProfesor() {
        return sueldoProfesor;
    }

    public void setSueldoProfesor(BigDecimal sueldoProfesor) {
        this.sueldoProfesor = sueldoProfesor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaDeduccion() {
        return fechaDeduccion;
    }

    public void setFechaDeduccion(Date fechaDeduccion) {
        this.fechaDeduccion = fechaDeduccion;
    }
}
