package com.pixvs.main.models;

import javax.persistence.*;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Entity
@Table(name = "PADescuentosArticulos")
public class PADescuentoArticulo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PADESCA_DescuentoArticuloId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PADESCA_PADESC_DescuentoId", nullable = true, insertable = false, updatable = false)
    private Integer descuentoId;

    @OneToOne
    @JoinColumn(name = "PADESCA_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "PADESCA_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    @Column(name = "PADESCA_Activo", nullable = false, insertable = true, updatable = true)
    private Boolean activo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDescuentoId() {
        return descuentoId;
    }

    public void setDescuentoId(Integer descuentoId) {
        this.descuentoId = descuentoId;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(Integer articuloId) {
        this.articuloId = articuloId;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
