package com.pixvs.main.models;

import javax.persistence.*;

/**
 * Created by Angel Daniel Hernández Silva on 25/03/2022.
 */
@Entity
@Table(name = "OrdenesVentaCancelacionesDetalles")
public class OrdenVentaCancelacionDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OVCD_OrdenVentaCancelacionDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVCD_OVC_OrdenVentaCancelacionId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OVC_OrdenVentaCancelacionId")
    private OrdenVentaCancelacion ordenVentaCancelacion;

    @Column(name = "OVCD_OVC_OrdenVentaCancelacionId", nullable = false, insertable = false, updatable = false)
    private Integer ordenVentaCancelacionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OVCD_OVD_OrdenVentaDetalleId", nullable = false, insertable = false, updatable = false, referencedColumnName = "OVD_OrdenVentaDetalleId")
    private OrdenVentaDetalle ordenVentaDetalle;

    @Column(name = "OVCD_OVD_OrdenVentaDetalleId", nullable = false, insertable = true, updatable = false)
    private Integer ordenVentaDetalleId;

    @Column(name = "OVCD_RegresoLibro", nullable = true, insertable = true, updatable = false)
    private Boolean regresoLibro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrdenVentaCancelacion getOrdenVentaCancelacion() {
        return ordenVentaCancelacion;
    }

    public void setOrdenVentaCancelacion(OrdenVentaCancelacion ordenVentaCancelacion) {
        this.ordenVentaCancelacion = ordenVentaCancelacion;
    }

    public Integer getOrdenVentaCancelacionId() {
        return ordenVentaCancelacionId;
    }

    public void setOrdenVentaCancelacionId(Integer ordenVentaCancelacionId) {
        this.ordenVentaCancelacionId = ordenVentaCancelacionId;
    }

    public OrdenVentaDetalle getOrdenVentaDetalle() {
        return ordenVentaDetalle;
    }

    public void setOrdenVentaDetalle(OrdenVentaDetalle ordenVentaDetalle) {
        this.ordenVentaDetalle = ordenVentaDetalle;
    }

    public Integer getOrdenVentaDetalleId() {
        return ordenVentaDetalleId;
    }

    public void setOrdenVentaDetalleId(Integer ordenVentaDetalleId) {
        this.ordenVentaDetalleId = ordenVentaDetalleId;
    }

    public Boolean getRegresoLibro() {
        return regresoLibro;
    }

    public void setRegresoLibro(Boolean regresoLibro) {
        this.regresoLibro = regresoLibro;
    }
}
