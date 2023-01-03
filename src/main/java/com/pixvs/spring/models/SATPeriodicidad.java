package com.pixvs.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "SATPeriodicidad")
public class SATPeriodicidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PER_PeriodicidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PER_Codigo", nullable = false)
    private String codigo;

    @Column(name = "PER_Descripcion", nullable = false)
    private String descripcion;

    @Column(name = "PER_Activo", nullable = false)
    private boolean activo;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
