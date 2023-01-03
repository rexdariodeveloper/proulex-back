package com.pixvs.spring.models;

import javax.persistence.*;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Entity
@Table(name = "Paises")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAI_PaisId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PAI_Nombre", nullable = false, insertable = true, updatable = true)
    private String nombre;

    @Column(name = "PAI_Clave", nullable = true, insertable = true, updatable = true)
    private String clave;

    @Column(name = "PAI_Nacionalidad", nullable = true, insertable = true, updatable = true)
    private String nacionalidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
