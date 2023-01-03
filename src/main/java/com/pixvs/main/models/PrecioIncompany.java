package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PreciosIncompany")
public class PrecioIncompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREINC_PrecioIncompanyId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PREINC_Codigo", nullable = false, insertable = true, updatable = true)
    private String codigo;

    @Column(name = "PREINC_Nombre", nullable = false, insertable = true, updatable = true)
    private String nombre;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PREINC_FechaInicio")
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "PREINC_FechaFin")
    private Date fechaFin;

    //Estatus
    @OneToOne
    @JoinColumn(name = "PREINC_CMM_EstatusId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CMM_ControlId")
    private ControlMaestroMultiple estatus;

    @Column(name = "PREINC_CMM_EstatusId", nullable = false, insertable = true, updatable = false)
    private Integer estatusId;

    @Column(name = "PREINC_Indeterminado", nullable = true, insertable = true, updatable = true)
    private Boolean indeterminado;

    @CreationTimestamp
    @Column(name = "PREINC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "PREINC_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "PREINC_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "PREINC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "PREINC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "PREINC_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaUltimaModificacion;

    @OneToMany( cascade = {CascadeType.ALL})
    @Where(clause = "PREINCD_Activo = 1")
    @JoinColumn(name = "PREINCD_PREINC_PrecioIncompanyId", referencedColumnName = "PREINC_PrecioIncompanyId", nullable = false, insertable = true, updatable = true)
    private List<PrecioIncompanyDetalle> detalles;

    @OneToMany( cascade = {CascadeType.ALL})
    @JoinColumn(name = "PREINCS_PREINC_PrecioIncompanyId", referencedColumnName = "PREINC_PrecioIncompanyId", nullable = false, insertable = true, updatable = true)
    private List<PrecioIncompanySucursal> sucursales;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public ControlMaestroMultiple getEstatus() {
        return estatus;
    }

    public void setEstatus(ControlMaestroMultiple estatus) {
        this.estatus = estatus;
    }

    public Integer getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Integer estatusId) {
        this.estatusId = estatusId;
    }

    public Boolean getIndeterminado() {
        return indeterminado;
    }

    public void setIndeterminado(Boolean indeterminado) {
        this.indeterminado = indeterminado;
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

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public List<PrecioIncompanyDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PrecioIncompanyDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<PrecioIncompanySucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<PrecioIncompanySucursal> sucursales) {
        this.sucursales = sucursales;
    }
}
