package com.pixvs.main.models.projections.TemporadaDetalle;

import com.pixvs.main.models.TemporadaDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {TemporadaDetalle.class})
public interface TemporadaDetalleEditarProjection {

    Integer getId();
    int getTemporadaId();
    ArticuloComboProjection getArticulo();
    int getArticuloId();
    BigDecimal getMinimo();
    BigDecimal getMaximo();
    int getCriterioId();

}