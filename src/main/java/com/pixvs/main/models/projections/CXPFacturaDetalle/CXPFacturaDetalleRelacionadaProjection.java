package com.pixvs.main.models.projections.CXPFacturaDetalle;

import com.pixvs.main.models.CXPFacturaDetalle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 11/09/2020.
 */
@Projection(types = {CXPFacturaDetalle.class})
public interface CXPFacturaDetalleRelacionadaProjection {

    Integer getId();
    BigDecimal getCantidad();
    @Value("#{target.cxpFactura.estatusId}")
    Integer getEstatusCXPFacturaId();

}
