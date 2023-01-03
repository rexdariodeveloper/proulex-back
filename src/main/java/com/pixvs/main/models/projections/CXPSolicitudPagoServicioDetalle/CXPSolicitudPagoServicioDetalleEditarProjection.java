package com.pixvs.main.models.projections.CXPSolicitudPagoServicioDetalle;

import com.pixvs.main.models.CXPSolicitudPagoServicioDetalle;
import com.pixvs.main.models.projections.CXPFactura.CXPFacturaVerSolicitudPagoProjection;
import com.pixvs.main.models.projections.CXPFactura.CXPSolicitudFacturaEditarProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {CXPSolicitudPagoServicioDetalle.class})
public interface CXPSolicitudPagoServicioDetalleEditarProjection {

    Integer getId();
    CXPFacturaVerSolicitudPagoProjection getCxpFactura();
    Integer getCxpFacturaId();
    Integer getEstatusId();

}
