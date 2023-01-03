package com.pixvs.main.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "InventariosFisicosDetalles")
public class InventarioFisicoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IFD_InventarioFisicoDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "IFD_IF_InventarioFisicoId", nullable = false)
    private int inventarioFisicoId;

    @Column(name = "IFD_ART_ArticuloId", nullable = false)
    private int articuloId;

    @OneToOne
    @JoinColumn(name = "IFD_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "IFD_UM_UnidadMedidaId", nullable = false)
    private int unidadMedidaId;

    @OneToOne
    @JoinColumn(name = "IFD_UM_UnidadMedidaId", referencedColumnName = "UM_UnidadMedidaId", insertable = false, updatable = false)
    private UnidadMedida unidadMedida;

    @Column(name = "IFD_Conteo", nullable = false)
    private BigDecimal conteo;

    @Column(name = "IFD_Existencia", nullable = false)
    private BigDecimal existencia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getInventarioFisicoId() {
        return inventarioFisicoId;
    }

    public void setInventarioFisicoId(int inventarioFisicoId) {
        this.inventarioFisicoId = inventarioFisicoId;
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

    public BigDecimal getConteo() {
        return conteo;
    }

    public void setConteo(BigDecimal conteo) {
        this.conteo = conteo;
    }

    public BigDecimal getExistencia() {
        return existencia;
    }

    public void setExistencia(BigDecimal existencia) {
        this.existencia = existencia;
    }
}