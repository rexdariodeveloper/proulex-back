package com.pixvs.main.models.projections.PedidoReciboDetalleLocalidad;

import com.pixvs.main.models.PedidoReciboDetalleLocalidad;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {PedidoReciboDetalleLocalidad.class})
public interface PedidoReciboDetalleLocalidadListadoProjection {

    int getLocalidadId();
    BigDecimal getCantidad();

}