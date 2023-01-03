package com.pixvs.main.models.projections.PedidoDetalle;

import com.pixvs.main.models.PedidoDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {PedidoDetalle.class})
public interface PedidoDetalleEditarProjection {

    Integer getId();
    int getPedidoId();
    ArticuloComboProjection getArticulo();
    int getArticuloId();
    int getUnidadMedidaId();
    BigDecimal getCantidadPedida();
    BigDecimal getCantidadSurtida();
    BigDecimal getExistencia();
    int getEstatusId();
    String getComentarioSurtir();

}