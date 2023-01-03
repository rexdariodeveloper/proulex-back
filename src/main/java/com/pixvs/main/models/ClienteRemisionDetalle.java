package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 11/06/2021.
 */
@Entity
@Table(name = "ClientesRemisionesDetalles")
public class ClienteRemisionDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIRD_ClienteRemisionDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CLIRD_CLIR_ClienteRemisionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "CLIR_ClienteRemisionId")
    private ClienteRemision clienteRemision;

    @Column(name = "CLIRD_CLIR_ClienteRemisionId", nullable = false, insertable = false, updatable = false)
    private Integer clienteRemisionId;

    @OneToOne
    @JoinColumn(name = "CLIRD_ART_ArticuloId", nullable = false, insertable = false, updatable = false, referencedColumnName = "ART_ArticuloId")
    private Articulo articulo;

    @Column(name = "CLIRD_ART_ArticuloId", nullable = false, insertable = true, updatable = true)
    private Integer articuloId;

    @Column(name = "CLIRD_Cantidad", nullable = false, insertable = true, updatable = true)
    private BigDecimal cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClienteRemision getClienteRemision() {
        return clienteRemision;
    }

    public void setClienteRemision(ClienteRemision clienteRemision) {
        this.clienteRemision = clienteRemision;
    }

    public Integer getClienteRemisionId() {
        return clienteRemisionId;
    }

    public void setClienteRemisionId(Integer clienteRemisionId) {
        this.clienteRemisionId = clienteRemisionId;
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
