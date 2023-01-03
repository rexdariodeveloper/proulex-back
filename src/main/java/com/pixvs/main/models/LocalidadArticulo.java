package com.pixvs.main.models;

import javax.persistence.*;

@Entity
@Table(name = "LocalidadesArticulos ")
@IdClass(LocalidadArticuloCompositeId.class)
public class LocalidadArticulo {

    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "LOCA_LOC_LocalidadId", insertable = true, updatable = false, nullable = false)
    private Integer localidadId;

    @Id
    @Column(name = "LOCA_ART_ArticuloId", insertable = true, updatable = false, nullable = false)
    private Integer articuloId;

    @OneToOne
    @JoinColumn(name = "LOCA_LOC_LocalidadId", nullable = false, insertable = false, updatable = false, referencedColumnName = "LOC_LocalidadId")
    private Localidad localidad;

    public Integer getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(Integer localidadId) {
        this.localidadId = localidadId;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public Localidad getLocalidad() { return localidad; }

    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
}
