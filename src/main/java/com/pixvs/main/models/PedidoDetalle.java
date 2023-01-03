package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PedidosDetalles")
public class PedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PEDD_PedidoDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PEDD_PED_PedidoId", nullable = false, insertable = false, updatable = false)
    private int pedidoId;

    @OneToOne
    @JoinColumn(name = "PEDD_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "PEDD_ART_ArticuloId", nullable = false)
    private int articuloId;

    @OneToOne
    @JoinColumn(name = "PEDD_UM_UnidadMedidaId", referencedColumnName = "UM_UnidadMedidaId", insertable = false, updatable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "PEDD_UM_UnidadMedidaId", nullable = false)
    private int unidadMedidaId;

    @Column(name = "PEDD_CantidadPedida", nullable = false)
    private BigDecimal cantidadPedida;

    @Column(name = "PEDD_CantidadSurtida", nullable = false)
    private BigDecimal cantidadSurtida;

    @Column(name = "PEDD_Existencia", nullable = false)
    private BigDecimal existencia;

    @Column(name = "PEDD_CMM_EstatusId", nullable = false)
    private int estatusId;

    @Column(name = "PEDD_ComentarioSurtir")
    private String comentarioSurtir;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getPedidoId() { return pedidoId; }
    public void setPedidoId(int pedidoId) { this.pedidoId = pedidoId; }

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

    public BigDecimal getCantidadSurtida() { return cantidadSurtida; }
    public void setCantidadSurtida(BigDecimal cantidadSurtida) { this.cantidadSurtida = cantidadSurtida; }

    public BigDecimal getExistencia() { return existencia; }
    public void setExistencia(BigDecimal existencia) { this.existencia = existencia; }

    public int getEstatusId() { return estatusId; }
    public void setEstatusId(int estatusId) { this.estatusId = estatusId; }

    public String getComentarioSurtir() {
        return comentarioSurtir;
    }

    public void setComentarioSurtir(String comentarioSurtir) {
        this.comentarioSurtir = comentarioSurtir;
    }
}
