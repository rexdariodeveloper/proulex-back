package com.pixvs.spring.models.projections.ControlMaestroMultiple;

import com.pixvs.spring.models.ControlMaestroMultiple;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "controlMaestroMultipleEditarProjection", types = {ControlMaestroMultiple.class})
public interface ControlMaestroMultipleEditarProjection {
    Integer getId();

    String getControl();

    String getValor();

    String getReferencia();

    Boolean getSistema();

    Boolean getActivo();
}
