package com.pixvs.main.models.projections.TransferenciaDetalle;

import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;

import java.math.BigDecimal;

public interface TransferenciaDetalleListadoProjection {

    Integer getId();

    int getTransferenciaId();

    ArticuloComboProjection getArticulo();

    BigDecimal getCantidad();

    BigDecimal getCantidadTransferida();

    BigDecimal getCantidadDevuelta();

    BigDecimal getSpill();
}
