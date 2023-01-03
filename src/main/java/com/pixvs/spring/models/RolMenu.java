package com.pixvs.spring.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RolesMenus")
public class RolMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLMP_RolMenuId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "ROLMP_MP_NodoId", referencedColumnName = "MP_NodoId", nullable = false, insertable = false, updatable = false)
    private MenuPrincipal nodo;

    @Column(name = "ROLMP_MP_NodoId", nullable = false, insertable = true, updatable = true)
    private Integer nodoId;

    @Column(name = "ROLMP_ROL_RolId", nullable = false, insertable = true, updatable = true)
    private Integer rolId;

    @Column(name = "ROLMP_Crear", nullable = false)
    private Boolean crear;

    @Column(name = "ROLMP_Modificar", nullable = false)
    private Boolean modificar;

    @Column(name = "ROLMP_Eliminar", nullable = false)
    private Boolean eliminar;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    @Column(name = "ROLMP_FechaCreacion", nullable = false, insertable = true, updatable = false)
    private Date fechaCreacion;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    @Column(name = "ROLMP_FechaModificacion", nullable = true, insertable = false, updatable = true)
    private Date fechaModificacion;

    @Column(name = "ROLMP_USU_CreadoPorId", nullable = true, insertable = true, updatable = true)
    private Integer creadoPorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MenuPrincipal getNodo() {
        return nodo;
    }

    public void setNodo(MenuPrincipal nodo) {
        this.nodo = nodo;
    }

    public Integer getNodoId() {
        return nodoId;
    }

    public void setNodoId(Integer nodoId) {
        this.nodoId = nodoId;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public Boolean getCrear() {
        return crear;
    }

    public void setCrear(Boolean crear) {
        this.crear = crear;
    }

    public Boolean getModificar() {
        return modificar;
    }

    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
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

    public Integer getCreadoPorId() {
        return creadoPorId;
    }

    public void setCreadoPorId(Integer creadoPorId) {
        this.creadoPorId = creadoPorId;
    }
}

