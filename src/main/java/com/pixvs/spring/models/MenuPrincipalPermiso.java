package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "MenuPrincipalPermisos")
public class MenuPrincipalPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MPP_MenuPrincipalPermisoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "MPP_MP_NodoId", nullable = false)
    private Integer nodoId;

    @Column(name = "MPP_Nombre", nullable = false)
    private String nombre;

    @Column(name = "MPP_Activo", nullable = false)
    private Boolean activo;

    /** Setters & Getters **/
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getNodoId() { return nodoId; }
    public void setNodoId(Integer nodoId) { this.nodoId = nodoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
