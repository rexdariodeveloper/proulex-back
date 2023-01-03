package com.pixvs.main.models.projections.OrdenVenta;

import com.pixvs.main.models.OrdenVenta;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 01/10/2021.
 */
@Projection(types = {OrdenVenta.class})
public interface OrdenVentaHistorialPVResumenProjection {

    Integer getId();
    String getCodigo();
    String getUsuario();
    Date getFecha();
    BigDecimal getMontoTotal();
    String getMedioPago();

}
