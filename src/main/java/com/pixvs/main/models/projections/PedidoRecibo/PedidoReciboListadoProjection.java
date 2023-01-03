package com.pixvs.main.models.projections.PedidoRecibo;

import com.pixvs.main.models.Temporada;
import com.pixvs.main.models.projections.PedidoReciboDetalle.PedidoReciboDetalleListadoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {Temporada.class})
public interface PedidoReciboListadoProjection {

    List<PedidoReciboDetalleListadoProjection> getDetalles();

}