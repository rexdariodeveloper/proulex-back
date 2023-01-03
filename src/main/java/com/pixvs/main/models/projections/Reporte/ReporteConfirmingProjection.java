package com.pixvs.main.models.projections.Reporte;

import java.math.BigDecimal;

public interface ReporteConfirmingProjection {
    String getNombreSucursal();
    String getNombreProveedor();
    String getCodigo();
    String getFechaRegistro();
    String getFolio();
    String getFechaFactura();
    Integer getDiasCredito();
    String getUltimaFechaPago();
    BigDecimal getSubtotal();
    BigDecimal getDescuento();
    BigDecimal getIva();
    BigDecimal getIeps();
    BigDecimal getRetenciones();
    BigDecimal getTotal();
}
