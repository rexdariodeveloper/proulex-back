package com.pixvs.main.models.projections.CXCFacturaDetalle;

import com.pixvs.main.models.CXCFacturaDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

@Projection(types = {CXCFacturaDetalle.class})
public interface CFDIFacturaRelacionadaDetalleProjection {

    Integer getId();

    int getFacturaId();

    BigDecimal getCantidad();

    BigDecimal getValorUnitario();

    BigDecimal getImporte();

    BigDecimal getDescuento();

    List<CFDIFacturaDetalleImpuestoProjection> getImpuestos();
}