package com.pixvs.main.models.projections.OrdenVentaCancelacionDetalle;

import com.pixvs.main.models.OrdenVentaCancelacionDetalle;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 04/04/2022.
 */
@Projection(types = {OrdenVentaCancelacionDetalle.class})
public interface OrdenVentaCancelacionDetalleEditarProjection {

    Integer getId();
    Integer getOrdenVentaDetalleId();
    Boolean getRegresoLibro();

}
