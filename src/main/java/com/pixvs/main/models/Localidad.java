package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Entity
@Table(name = "Localidades")
public class Localidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOC_LocalidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "LOC_CodigoLocalidad", nullable = false, insertable = true, updatable = true)
    private String codigoLocalidad;

    @Column(name = "LOC_Nombre", nullable = false, insertable = true, updatable = true)
    private String nombre;

    @OneToOne
    @JoinColumn(name = "LOC_ALM_AlmacenId", nullable = true, insertable = false, updatable = false, referencedColumnName = "ALM_AlmacenId")
    private Almacen almacen;

    @Column(name = "LOC_ALM_AlmacenId", nullable = true, insertable = false, updatable = false)
    private Integer almacenId;

    @Column(name = "LOC_LocalidadGeneral", nullable = false, insertable = true, updatable = false)
    private Boolean localidadGeneral;

    @Column(name = "LOC_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "LOC_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @OneToOne
    @JoinColumn(name = "LOC_USU_CreadoPorId", nullable = false, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "LOC_USU_CreadoPorId", nullable = false, insertable = true, updatable = false)
    private Integer creadoPorId;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "LOC_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @OneToOne
    @JoinColumn(name = "LOC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "LOC_USU_ModificadoPorId", nullable = true, insertable = false, updatable = true)
    private Integer modificadoPorId;

    @ManyToMany
    @JoinTable(name = "LocalidadesArticulos", joinColumns = {@JoinColumn(name = "LOCA_LOC_LocalidadId")}, inverseJoinColumns = {@JoinColumn(name = "LOCA_ART_ArticuloId")})
    private Set<Articulo> articulos = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoLocalidad() {
        return codigoLocalidad;
    }

    public void setCodigoLocalidad(String codigoLocalidad) {
        this.codigoLocalidad = codigoLocalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Integer getAlmacenId() {
        return almacenId;
    }

    public void setAlmacenId(Integer almacenId) {
        this.almacenId = almacenId;
    }

    public Boolean getLocalidadGeneral() {
        return localidadGeneral;
    }

    public void setLocalidadGeneral(Boolean localidadGeneral) {
        this.localidadGeneral = localidadGeneral;
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

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public Set<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }

}
