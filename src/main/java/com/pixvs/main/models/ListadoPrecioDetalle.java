package com.pixvs.main.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Entity
@Table(name = "ListadosPreciosDetalles")
public class ListadoPrecioDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIPRED_ListadoPrecioDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "LIPRED_LIPRE_ListadoPrecioId", nullable = true, insertable = false, updatable = false)
    private Integer listadoPrecioId;

    @Column(name = "LIPRED_ListadoPrecioPadreId", nullable = true, insertable = false, updatable = false)
    private Integer padreId;

    @Column(name = "LIPRED_Precio", nullable = false, insertable = true, updatable = true)
    private BigDecimal precio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LIPRED_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "LIPRED_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "LIPRED_ListadoPrecioPadreId", referencedColumnName = "LIPRED_ListadoPrecioDetalleId", insertable = true, updatable = true)
    private List<ListadoPrecioDetalle> hijos;

    @OneToMany( cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "LIPREDC_LIPRED_ListadoPrecioDetalleId", referencedColumnName = "LIPRED_ListadoPrecioDetalleId", nullable = false, insertable = true, updatable = true)
    private List<ListadoPrecioDetalleCurso> detallesCurso;

    @Transient
    Boolean borrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getListadoPrecioId() {
        return listadoPrecioId;
    }

    public void setListadoPrecioId(Integer listadoPrecioId) {
        this.listadoPrecioId = listadoPrecioId;
    }

    public Integer getPadreId() {
        return padreId;
    }

    public void setPadreId(Integer padreId) {
        this.padreId = padreId;
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

    public List<ListadoPrecioDetalle> getHijos() {
        return hijos;
    }

    public void setHijos(List<ListadoPrecioDetalle> hijos) {
        this.hijos = hijos;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public List<ListadoPrecioDetalleCurso> getDetallesCurso() {
        return detallesCurso;
    }

    public void setDetallesCurso(List<ListadoPrecioDetalleCurso> detallesCurso) {
        this.detallesCurso = detallesCurso;
    }
}
