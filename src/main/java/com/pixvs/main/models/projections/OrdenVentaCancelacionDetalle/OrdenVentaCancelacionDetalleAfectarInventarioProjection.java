package com.pixvs.main.models.projections.OrdenVentaCancelacionDetalle;

import com.pixvs.main.models.OrdenVentaCancelacionDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 02/09/2022.
 */
@Projection(types = {OrdenVentaCancelacionDetalle.class})
public interface OrdenVentaCancelacionDetalleAfectarInventarioProjection {

    Integer getId();

    Integer getArticuloId();

    Integer getSucursalId();

    BigDecimal getCantidad();

    String getCodigoOV();

    BigDecimal getPrecio();

    Integer getUsuarioId();

    Integer getCancelacionId();
}