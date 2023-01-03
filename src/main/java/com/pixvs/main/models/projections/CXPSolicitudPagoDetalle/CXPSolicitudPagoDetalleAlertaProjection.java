package com.pixvs.main.models.projections.CXPSolicitudPagoDetalle;

import com.pixvs.main.models.CXPSolicitudPagoDetalle;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaAlertaCXPSPProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXPSolicitudPagoDetalle.class})
public interface CXPSolicitudPagoDetalleAlertaProjection {

    Integer getId();
    CXPFacturaAlertaCXPSPProjection getCxpFactura();
    Integer getEstatusId();
    BigDecimal getMontoProgramado();

}
