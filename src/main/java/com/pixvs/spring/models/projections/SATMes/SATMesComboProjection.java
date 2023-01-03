package com.pixvs.spring.models.projections.SATMes;

import com.pixvs.spring.models.SATMes;
import com.pixvs.spring.models.projections.SATPeriodicidad.SATPeriodicidadComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {SATMes.class})
public interface SATMesComboProjection {

    Integer getId();

    String getCodigo();

    String getDescripcion();

    boolean getActivo();

    SATPeriodicidadComboProjection getPeriodicidad();

    @Value("#{ target.codigo + ' - ' + target.descripcion }")
    String getValor();
}
