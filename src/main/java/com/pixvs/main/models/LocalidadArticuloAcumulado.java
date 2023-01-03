package com.pixvs.main.models;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "LocalidadesArticulosAcumulados ")
public class LocalidadArticuloAcumulado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCAA_LocalidadArticuloId", insertable = false, updatable = false, nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "LOCAA_LOC_LocalidadId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidad;

    @Column(name = "LOCAA_LOC_LocalidadId", nullable = false, insertable = true, updatable = false)
    private Integer localidadId;

    @OneToOne
    @JoinColumn(name = "LOCAA_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "LOCAA_ART_ArticuloId", nullable = false, insertable = true, updatable = false)
    private Integer articuloId;

    @Column(name = "LOCAA_Cantidad", nullable = false, insertable = true, updatable = true)
    private BigDecimal cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Integer getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(Integer localidadId) {
        this.localidadId = localidadId;
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

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
}