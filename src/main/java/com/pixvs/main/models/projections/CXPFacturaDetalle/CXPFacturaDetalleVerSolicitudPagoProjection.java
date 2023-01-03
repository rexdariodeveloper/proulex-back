package com.pixvs.main.models.projections.CXPFacturaDetalle;

import com.pixvs.main.models.CXPFacturaDetalle;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 23/12/2020.
 */
@Projection(types = {CXPFacturaDetalle.class})
public interface CXPFacturaDetalleVerSolicitudPagoProjection {

    Integer getId();
    Integer getArticuloId();

}
