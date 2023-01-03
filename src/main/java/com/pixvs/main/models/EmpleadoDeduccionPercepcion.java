package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.Archivo;
import com.pixvs.spring.models.ControlMaestroMultiple;
import com.pixvs.spring.models.Departamento;
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
@Table(name = "EmpleadosDeduccionesPercepciones")
public class EmpleadoDeduccionPercepcion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDP_EmpleadoDeduccionPercepcionId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "EDP_Codigo", length = 15, nullable = false)
    private String codigo;

    //Empleado
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "EDP_EMP_EmpleadoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "EMP_EmpleadoId")
    private Empleado empleado;

    @Column(name = "EDP_EMP_EmpleadoId", nullable = false)
    private Integer empleadoId;

    //Sucursal
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "EDP_SUC_SucursalId", nullable = false, insertable = false, updatable = false, referencedColumnName = "SUC_SucursalId")
    private Sucursal sucursal;

    @Column(name = "EDP_SUC_SucursalId", nullable = false)
    private Integer sucursalId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "EDP_Fecha", nullable = false)
    private Date fecha;

    //Tipo de moviento (Deduccion o Percepcion)
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "EDP_CMM_TipoMovimientoId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple tipoMovimiento;

    @Column(name = "EDP_CMM_TipoMovimientoId", nullable = false)
    private Integer tipoMovimientoId;

    //Deduccion o Percepcion (Concepto)
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "EDP_DEDPER_DeduccionPercepcionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "DEDPER_DeduccionPercepcionId")
    private DeduccionPercepcion deduccionPercepcion;

    @Column(name = "EDP_DEDPER_DeduccionPercepcionId", nullable = false)
    private Integer deduccionPercepcionId;

    @Column(name = "EDP_ValorFijo", nullable = false)
    private BigDecimal valorFijo;

    @Column(name = "EDP_CantidadHoras", nullable = false)
    private Integer cantidadHoras;

    @Column(name = "EDP_Total", nullable = false)
    private BigDecimal total;

    @Column(name = "EDP_Activo", nullable = false)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "EDP_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "EDP_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "EDP_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "EDP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "EDP_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "EDP_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "EDPD_EDP_EmpleadoDeduccionPercepcionId", referencedColumnName = "EDP_EmpleadoDeduccionPercepcionId", nullable = false, insertable = true, updatable = true)
    private List<EmpleadoDeduccionPercepcionDocumento> documentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public DeduccionPercepcion getDeduccionPercepcion() {
        return deduccionPercepcion;
    }

    public void setDeduccionPercepcion(DeduccionPercepcion deduccionPercepcion) {
        this.deduccionPercepcion = deduccionPercepcion;
    }

    public Integer getDeduccionPercepcionId() {
        return deduccionPercepcionId;
    }

    public void setDeduccionPercepcionId(Integer deduccionPercepcionId) {
        this.deduccionPercepcionId = deduccionPercepcionId;
    }

    public BigDecimal getValorFijo() {
        return valorFijo;
    }

    public void setValorFijo(BigDecimal valorFijo) {
        this.valorFijo = valorFijo;
    }

    public Integer getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(Integer cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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

    public List<EmpleadoDeduccionPercepcionDocumento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<EmpleadoDeduccionPercepcionDocumento> documentos) {
        this.documentos = documentos;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Integer getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(Integer sucursalId) {
        this.sucursalId = sucursalId;
    }
}
