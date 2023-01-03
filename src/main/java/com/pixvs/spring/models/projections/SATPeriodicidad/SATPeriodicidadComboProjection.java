package com.pixvs.spring.models.projections.SATPeriodicidad;

import com.pixvs.spring.models.SATPeriodicidad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SATPeriodicidad.class})
public interface SATPeriodicidadComboProjection {

    Integer getId();

    String getCodigo();

    String getDescripcion();

    boolean getActivo();

    @Value("#{ target.codigo + ' - ' + target.descripcion }")
    String getValor();
}
