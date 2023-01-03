package com.pixvs.main.models.projections.PrecioIncompany;

import com.pixvs.main.models.PrecioIncompany;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Rene Carrillo on 05/10/2020.
 */
@Projection(types = {PrecioIncompany.class})
public interface PrecioIncompanyComboZonaProjection {
    Integer getId();
    String getNombre();
    BigDecimal getPrecioVenta();
    BigDecimal getPorcentajeTransporte();
}
