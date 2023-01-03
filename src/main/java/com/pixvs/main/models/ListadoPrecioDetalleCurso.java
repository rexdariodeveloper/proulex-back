package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 14/09/2021.
 */
@Entity
@Table(name = "ListadosPreciosDetallesCursos")
public class ListadoPrecioDetalleCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIPREDC_ListadoPrecioDetalleCursoId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIPREDC_LIPRED_ListadoPrecioDetalleId", nullable = false, insertable = false, updatable = false, referencedColumnName = "LIPRED_ListadoPrecioDetalleId")
    private ListadoPrecioDetalle listadoPrecioDetalle;

    @Column(name = "LIPREDC_LIPRED_ListadoPrecioDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer listadoPrecioDetalleId;

    @Column(name = "LIPREDC_Precio", nullable = false, insertable = true, updatable = true)
    private BigDecimal precio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIPREDC_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "LIPREDC_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ListadoPrecioDetalle getListadoPrecioDetalle() {
        return listadoPrecioDetalle;
    }

    public void setListadoPrecioDetalle(ListadoPrecioDetalle listadoPrecioDetalle) {
        this.listadoPrecioDetalle = listadoPrecioDetalle;
    }

    public Integer getListadoPrecioDetalleId() {
        return listadoPrecioDetalleId;
    }

    public void setListadoPrecioDetalleId(Integer listadoPrecioDetalleId) {
        this.listadoPrecioDetalleId = listadoPrecioDetalleId;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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
}
