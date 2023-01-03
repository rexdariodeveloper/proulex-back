package com.pixvs.main.models.projections.InventarioFisicoDetalle;

import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;

import java.math.BigDecimal;

public interface InventarioFisicoDetalleListadoProjection {

    Integer getId();

    int getInventarioFisicoId();

    ArticuloComboProjection getArticulo();

    BigDecimal getConteo();

    BigDecimal getExistencia();
}
