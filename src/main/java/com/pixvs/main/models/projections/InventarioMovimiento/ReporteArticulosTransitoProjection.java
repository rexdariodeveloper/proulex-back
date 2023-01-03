package com.pixvs.main.models.projections.InventarioMovimiento;

import java.math.BigDecimal;

public interface ReporteArticulosTransitoProjection {

    String getReferencia();
    String getRecibe();
    String getCodigoArticulo();
    String getNombreArticulo();
    String getUnidad();

    BigDecimal getCantidad();
    BigDecimal getCosto();
    BigDecimal getTotal();
    Integer getLocalidadId();
}
