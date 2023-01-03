package com.pixvs.main.models.projections.Moneda;

import com.pixvs.main.models.MonedaParidad;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {MonedaParidad.class})
public interface MonedaParidadComboProjection {

    Integer getMonedaId();

    BigDecimal getTipoCambioOficial();
}