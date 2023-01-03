package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PedidosRecibosDetallesLocalidades")
public class PedidoReciboDetalleLocalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRDL_PedidoReciboDetalleLocalidadId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "PRDL_PRD_PedidoReciboDetalleId", referencedColumnName = "PRD_PedidoReciboDetalleId", insertable = false, updatable = false)
    private PedidoReciboDetalle pedidoReciboDetalle;

    @Column(name = "PRDL_PRD_PedidoReciboDetalleId", nullable = false, insertable = false, updatable = false)
    private int pedidoReciboDetalleId;

    @OneToOne
    @JoinColumn(name = "PRDL_LOC_LocalidadId", referencedColumnName = "LOC_LocalidadId", insertable = false, updatable = false)
    private Localidad localidad;

    @Column(name = "PRDL_LOC_LocalidadId", nullable = false)
    private int localidadId;

    @Column(name = "PRDL_Cantidad", nullable = false)
    private BigDecimal cantidad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PedidoReciboDetalle getPedidoReciboDetalle() {
        return pedidoReciboDetalle;
    }

    public void setPedidoReciboDetalle(PedidoReciboDetalle pedidoReciboDetalle) {
        this.pedidoReciboDetalle = pedidoReciboDetalle;
    }

    public int getPedidoReciboDetalleId() {
        return pedidoReciboDetalleId;
    }

    public void setPedidoReciboDetalleId(int pedidoReciboDetalleId) {
        this.pedidoReciboDetalleId = pedidoReciboDetalleId;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public int getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(int localidadId) {
        this.localidadId = localidadId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
}
