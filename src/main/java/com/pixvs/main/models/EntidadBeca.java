package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EntidadesBecas")
public class EntidadBeca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENBE_EntidadBecaId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "ENBE_Codigo", nullable = false)
    private String codigo;

    @Column(name = "ENBE_Nombre", nullable = false)
    private String nombre;

    @Column(name = "ENBE_LIPRE_ListadoPrecioId")
    private Integer listadoPrecioId;

    @OneToOne
    @JoinColumn(name = "ENBE_LIPRE_ListadoPrecioId", insertable = false, updatable = false, referencedColumnName = "LIPRE_ListadoPrecioId")
    private ListadoPrecio listadoPrecio;

    @Column(name = "ENBE_Activo", nullable = false)
    private Boolean activo;

    @OneToOne
    @JoinColumn(name = "ENBE_USU_CreadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario creadoPor;

    @Column(name = "ENBE_USU_CreadoPorId", nullable = false, updatable = false)
    private Integer creadoPorId;

    @OneToOne
    @JoinColumn(name = "ENBE_USU_ModificadoPorId", insertable = false, updatable = false, referencedColumnName = "USU_UsuarioId")
    private Usuario modificadoPor;

    @Column(name = "ENBE_USU_ModificadoPorId")
    private Integer modificadoPorId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ENBE_FechaCreacion", nullable = false, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    @Column(name = "ENBE_FechaUltimaModificacion")
    private Date fechaModificacion;

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

    public Integer getListadoPrecioId() {
        return listadoPrecioId;
    }

    public void setListadoPrecioId(Integer listadoPrecioId) {
        this.listadoPrecioId = listadoPrecioId;
    }

    public ListadoPrecio getListadoPrecio() {
        return listadoPrecio;
    }

    public void setListadoPrecio(ListadoPrecio listadoPrecio) {
        this.listadoPrecio = listadoPrecio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}