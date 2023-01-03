package com.pixvs.main.models.projections.PedidoReciboDetalle;

import com.pixvs.main.models.PedidoReciboDetalle;
import com.pixvs.main.models.PedidoReciboDetalleLocalidad;
import com.pixvs.main.models.projections.PedidoReciboDetalleLocalidad.PedidoReciboDetalleLocalidadListadoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

@Projection(types = {PedidoReciboDetalle.class})
public interface PedidoReciboDetalleListadoProjection {

    int getArticuloId();
    BigDecimal getCantidadPedida();
    BigDecimal getCantidadDevuelta();
    BigDecimal getSpill();
    List<PedidoReciboDetalleLocalidadListadoProjection> getLocalidades();
}
