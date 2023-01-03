package com.pixvs.main.models.projections.OrdenVenta;

import com.pixvs.main.models.OrdenVenta;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 30/09/2021.
 */
@Projection(types = {OrdenVenta.class})
public interface OrdenVentaHistorialPVProjection {

    Integer getId();
    Date getFecha();
    String getCodigo();
    BigDecimal getMonto();
    Integer getEstatusId();
    String getEstatus();

}
