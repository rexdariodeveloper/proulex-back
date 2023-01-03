package com.pixvs.main.models.projections.CXPPagoDetalle;

import com.pixvs.main.models.CXPPagoDetalle;
import com.pixvs.main.models.projections.CXPPago.CXPPagoProgramacionPagoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 17/09/2020.
 */
@Projection(types = {CXPPagoDetalle.class})
public interface CXPPagoDetalleProgramacionPagoProjection {

    Integer getId();
    BigDecimal getMontoAplicado();
    CXPPagoProgramacionPagoProjection getCxpPago();

}
