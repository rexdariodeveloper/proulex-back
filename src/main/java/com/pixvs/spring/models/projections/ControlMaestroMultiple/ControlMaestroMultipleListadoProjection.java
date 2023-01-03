package com.pixvs.spring.models.projections.ControlMaestroMultiple;

import com.pixvs.spring.models.ControlMaestroMultiple;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ControlMaestroMultiple.class})
public interface ControlMaestroMultipleListadoProjection {
    Integer getId();

    String getControl();

    String getValor();

    Boolean getActivo();
}
