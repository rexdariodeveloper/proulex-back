package com.pixvs.main.models.projections.Dashboard;

import java.math.BigDecimal;

public interface DashboardDescuentoProjection {

    Integer getCantidad();
    String getDescripcion();
    BigDecimal getMonto();

}
