package com.pixvs.main.models.projections.OrdenCompraRecibo;

import com.pixvs.main.models.OrdenCompraRecibo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 27/08/2020.
 */
@Projection(types = {OrdenCompraRecibo.class})
public interface OrdenCompraReciboDevolucionProjection {

    Integer getId();
    BigDecimal getCantidadRecibo();

}
