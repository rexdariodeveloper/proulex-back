package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Entity
@Table(name = "ListadosPrecios")
public class ListadoPrecio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIPRE_ListadoPrecioId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "LIPRE_Codigo", nullable = false, insertable = true, updatable = true)
    private String codigo;

    @Column(name = "LIPRE_Nombre", nullable = false, insertable = true, updatable = true)
    private String nombre;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "LIPRE_FechaInicio", nullable = true)
    private Date fechaInicio;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    @Column(name = "LIPRE_FechaFin", nullable = true)
    private Date fechaFin;

    @Column(name = "LIPRE_Indeterminado", nullable = true, insertable = true, updatable = true)
    private Boolean indeterminado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIPRE_MON_MonedaId", nullable = false, insertable = false, updatable = false, referencedColumnName = "MON_MonedaId")
    private Moneda moneda;

    @Column(name = "LIPRE_MON_MonedaId", nullable = false, insertable = true, updatable = true)
    private Integer monedaId;

    @Column(name = "LIPRE_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "LIPRE_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "LIPRE_USU_CreadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "LIPRE_USU_CreadoPorId", nullable = true, insertable = true, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "LIPRE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "LIPRE_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "LIPRE_FechaUltimaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "LIPRED_LIPRE_ListadoPrecioId", referencedColumnName = "LIPRE_ListadoPrecioId", nullable = false, insertable = true, updatable = true)
    private List<ListadoPrecioDetalle> detalles;

    @Transient
    private String fecha;

    @Transient
    private String asignado;

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

    public Boolean getIndeterminado() {
        return indeterminado;
    }

    public void setIndeterminado(Boolean indeterminado) {
        this.indeterminado = indeterminado;
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

    public List<ListadoPrecioDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ListadoPrecioDetalle> detalles) {
        this.detalles = detalles;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsignado() {
        return asignado;
    }

    public void setAsignado(String asignado) {
        this.asignado = asignado;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }

    public Integer getMonedaId() {
        return monedaId;
    }

    public void setMonedaId(Integer monedaId) {
        this.monedaId = monedaId;
    }
}
