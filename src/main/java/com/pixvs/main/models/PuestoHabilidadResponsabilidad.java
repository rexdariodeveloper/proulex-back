package com.pixvs.main.models;

import javax.persistence.*;

@Entity
@Table(name = "PuestoHabilidadResponsabilidad")
public class PuestoHabilidadResponsabilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PUEHR_PuestoHabilidadResponsabilidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PUEHR_PUE_PuestoId", nullable = false, insertable = false, updatable = false)
    private Integer puestoId;

    @OneToOne
    @JoinColumn(name = "PUEHR_PUE_PuestoId", insertable = false, updatable = false, referencedColumnName = "PUE_PuestoId")
    private Puesto puesto;

    @Column(name = "PUEHR_Descripcion", length = 250, nullable = false, insertable = true, updatable = true)
    private String descripcion;

    @Column(name = "PUEHR_EsHabilidad", nullable = false, insertable = true, updatable = true)
    private Boolean esHabilidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(Integer puestoId) {
        this.puestoId = puestoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEsHabilidad() {
        return esHabilidad;
    }

    public void setEsHabilidad(Boolean esHabilidad) {
        this.esHabilidad = esHabilidad;
    }


}
