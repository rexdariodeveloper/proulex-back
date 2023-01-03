package com.pixvs.main.models.projections.CXCFacturaDetalle;

import com.pixvs.main.models.CXCFacturaDetalleImpuesto;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXCFacturaDetalleImpuesto.class})
public interface CFDIFacturaDetalleImpuestoProjection {

    Integer getId();

    int getFacturaDetalleId();

    String getClave();

    String getTipoFactor();

    BigDecimal getBase();

    BigDecimal getTasaOCuota();

    BigDecimal getImporte();
}