package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PedidosRecibosDetalles")
public class PedidoReciboDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRD_PedidoReciboDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "PRD_PR_PedidoReciboId", referencedColumnName = "PR_PedidoReciboId", insertable = false, updatable = false)
    private PedidoRecibo pedidoRecibo;

    @OneToOne
    @JoinColumn(name = "PRD_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "PRD_ART_ArticuloId", nullable = false)
    private int articuloId;

    @OneToOne
    @JoinColumn(name = "PRD_UM_UnidadMedidaId", referencedColumnName = "UM_UnidadMedidaId", insertable = false, updatable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "PRD_UM_UnidadMedidaId", nullable = false)
    private int unidadMedidaId;

    @Column(name = "PRD_CantidadPedida", nullable = false)
    private BigDecimal cantidadPedida;

    @Column(name = "PRD_CantidadDevuelta", nullable = false)
    private BigDecimal cantidadDevuelta;

    @Column(name = "PRD_CantidadSpill", nullable = false)
    private BigDecimal spill;

    @Column(name = "PRD_Comentario")
    private String comentario;

    @Column(name = "PRD_CMM_EstatusId", nullable = false)
    private int estatusId;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "PRDL_PRD_PedidoReciboDetalleId", referencedColumnName = "PRD_PedidoReciboDetalleId", nullable = false)
    private List<PedidoReciboDetalleLocalidad> localidades = new ArrayList<>();

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public PedidoRecibo getPedidoRecibo() { return pedidoRecibo; }
    public void setPedidoRecibo(PedidoRecibo pedidoRecibo) { this.pedidoRecibo = pedidoRecibo; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public int getArticuloId() { return articuloId; }
    public void setArticuloId(int articuloId) { this.articuloId = articuloId; }

    public UnidadMedida getUnidadMedida() { return unidadMedida; }
    public void setUnidadMedida(UnidadMedida unidadMedida) { this.unidadMedida = unidadMedida; }

    public int getUnidadMedidaId() { return unidadMedidaId; }
    public void setUnidadMedidaId(int unidadMedidaId) { this.unidadMedidaId = unidadMedidaId; }

    public BigDecimal getCantidadPedida() { return cantidadPedida; }
    public void setCantidadPedida(BigDecimal cantidadPedida) { this.cantidadPedida = cantidadPedida; }

    public BigDecimal getCantidadDevuelta() { return cantidadDevuelta; }
    public void setCantidadDevuelta(BigDecimal cantidadDevuelta) { this.cantidadDevuelta = cantidadDevuelta; }

    public BigDecimal getSpill() { return spill; }
    public void setSpill(BigDecimal spill) { this.spill = spill; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getEstatusId() { return estatusId; }
    public void setEstatusId(int estatusId) { this.estatusId = estatusId; }

    public List<PedidoReciboDetalleLocalidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<PedidoReciboDetalleLocalidad> localidades) {
        this.localidades = localidades;
    }
}
