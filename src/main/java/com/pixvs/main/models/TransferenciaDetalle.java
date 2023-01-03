package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TransferenciasDetalles")
public class TransferenciaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRAD_TransferenciaDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "TRAD_TRA_TransferenciaId", nullable = false, updatable = false)
    private int transferenciaId;

    @OneToOne
    @JoinColumn(name = "TRAD_TRA_TransferenciaId", referencedColumnName = "TRA_TransferenciaId", insertable = false, updatable = false)
    private Transferencia transferencia;

    @Column(name = "TRAD_ART_ArticuloId", nullable = false, updatable = false)
    private int articuloId;

    @OneToOne
    @JoinColumn(name = "TRAD_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "TRAD_UM_UnidadMedidaId", nullable = false, updatable = false)
    private int unidadMedidaId;

    @OneToOne
    @JoinColumn(name = "TRAD_UM_UnidadMedidaId", referencedColumnName = "UM_UnidadMedidaId", insertable = false, updatable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "TRAD_Cantidad", nullable = false, updatable = false)
    private BigDecimal cantidad;

    @Column(name = "TRAD_CantidadTransferida", nullable = false)
    private BigDecimal cantidadTransferida;

    @Column(name = "TRAD_CantidadDevuelta", nullable = false)
    private BigDecimal cantidadDevuelta;

    @Column(name = "TRAD_Spill", nullable = false)
    private BigDecimal spill;

    @Column(name = "TRAD_CMM_EstatusId", nullable = false)
    private int estatusId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTransferenciaId() {
        return transferenciaId;
    }

    public void setTransferenciaId(int transferenciaId) {
        this.transferenciaId = transferenciaId;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(int unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCantidadTransferida() {
        return cantidadTransferida;
    }

    public void setCantidadTransferida(BigDecimal cantidadTransferida) {
        this.cantidadTransferida = cantidadTransferida;
    }

    public BigDecimal getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(BigDecimal cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public BigDecimal getSpill() {
        return spill;
    }

    public void setSpill(BigDecimal spill) {
        this.spill = spill;
    }

    public int getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(int estatusId) {
        this.estatusId = estatusId;
    }
}
