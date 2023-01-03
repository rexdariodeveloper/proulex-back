package com.pixvs.main.models.projections.LocalidadArticuloAcumulado;

import com.pixvs.main.models.LocalidadArticuloAcumulado;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {LocalidadArticuloAcumulado.class})
public interface LocalidadArticuloAcumuladoComboProjection {

    Integer getId();

    Integer getLocalidadId();

    Integer getArticuloId();

    BigDecimal getCantidad();
}
