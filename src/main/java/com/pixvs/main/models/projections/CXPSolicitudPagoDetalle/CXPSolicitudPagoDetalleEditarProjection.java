package com.pixvs.main.models.projections.CXPSolicitudPagoDetalle;

import com.pixvs.main.models.CXPSolicitudPagoDetalle;
import com.pixvs.main.models.projections.CXPFactura.CXPSolicitudFacturaEditarProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CXPSolicitudPagoDetalle.class})
public interface CXPSolicitudPagoDetalleEditarProjection {

    Integer getId();
    CXPSolicitudFacturaEditarProjection getCxpFactura();
    Integer getCxpFacturaId();

}
