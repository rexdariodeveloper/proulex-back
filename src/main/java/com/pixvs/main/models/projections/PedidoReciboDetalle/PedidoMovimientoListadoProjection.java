package com.pixvs.main.models.projections.PedidoReciboDetalle;

import com.pixvs.main.models.PedidoDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {PedidoDetalle.class})
public interface PedidoMovimientoListadoProjection {

    Integer getId();

    Date getFecha();

    String getCodigoArticulo();

    String getNombreArticulo();

    String getUM();

    BigDecimal getCantidadPedida();

    BigDecimal getCantidadRecibida();

    BigDecimal getCantidadAjuste();

    String getAlmacen();

    String getUsuario();
}
