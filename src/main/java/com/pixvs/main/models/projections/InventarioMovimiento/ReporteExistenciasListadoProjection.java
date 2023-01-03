package com.pixvs.main.models.projections.InventarioMovimiento;

import java.math.BigDecimal;

public interface ReporteExistenciasListadoProjection {

    String getCodigo();
    String getIsbn();
    String getEditorial();
    String getNombre();
    String getUM();
    String getAlmacen();
    String getLocalidad();
    String getExistencia();
    String getTransito();
    String getTotalExistencia();
    String getCosto();
    String getTotal();

}
