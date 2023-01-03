package com.pixvs.main.models.projections.MontosCalculados;

import java.math.BigDecimal;

public interface MontosCalculadosProjection {

    BigDecimal getSubtotal();
    BigDecimal getIva();
    BigDecimal getIeps();
    BigDecimal getDescuento();
    BigDecimal getTotal();

}
