package com.pixvs.main.models;

import com.pixvs.spring.models.ControlMaestroMultiple;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TemporadasDetalles")
public class TemporadaDetalle {

    /** Properties **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEMD_TemporadaDetalleId", nullable = false, insertable = false, updatable = false)
    private Integer id;

    @Column(name = "TEMD_TEM_TemporadaId", nullable = false, insertable = false, updatable = false)
    private int temporadaId;

    @OneToOne
    @JoinColumn(name = "TEMD_ART_ArticuloId", referencedColumnName = "ART_ArticuloId", insertable = false, updatable = false)
    private Articulo articulo;

    @Column(name = "TEMD_ART_ArticuloId", nullable = false)
    private int articuloId;

    @Column(name = "TEMD_Minimo", nullable = false)
    private BigDecimal minimo;

    @Column(name = "TEMD_Maximo", nullable = false)
    private BigDecimal maximo;

    @OneToOne
    @JoinColumn(name = "TEMD_CMM_CriterioId", referencedColumnName = "CMM_ControlId", insertable = false, updatable = false)
    private ControlMaestroMultiple criterio;

    @Column(name = "TEMD_CMM_CriterioId", nullable = false)
    private int criterioId;

    /** Methods **/

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getTemporadaId() { return temporadaId; }
    public void setTemporadaId(int temporadaId) { this.temporadaId = temporadaId; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }

    public int getArticuloId() { return articuloId; }
    public void setArticuloId(int articuloId) { this.articuloId = articuloId; }

    public BigDecimal getMinimo() { return minimo; }
    public void setMinimo(BigDecimal minimo) { this.minimo = minimo; }

    public BigDecimal getMaximo() { return maximo; }
    public void setMaximo(BigDecimal maximo) { this.maximo = maximo; }

    public ControlMaestroMultiple getCriterio() { return criterio; }
    public void setCriterio(ControlMaestroMultiple criterio) { this.criterio = criterio; }

    public int getCriterioId() { return criterioId; }
    public void setCriterioId(int criterioId) { this.criterioId = criterioId; }
}
