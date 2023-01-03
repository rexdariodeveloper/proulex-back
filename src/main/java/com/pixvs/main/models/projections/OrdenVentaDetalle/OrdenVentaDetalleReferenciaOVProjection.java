package com.pixvs.main.models.projections.OrdenVentaDetalle;

import com.pixvs.main.models.OrdenVentaDetalle;
import com.pixvs.main.models.projections.OrdenVenta.OrdenVentaSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {OrdenVentaDetalle.class})
public interface OrdenVentaDetalleReferenciaOVProjection {

    Integer getId();
    OrdenVentaSimpleProjection getOrdenVenta();
    Integer getOrdenVentaId();
}
