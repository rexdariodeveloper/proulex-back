package com.pixvs.spring.models.projections.ControlMaestroMultiple;

import com.pixvs.spring.models.ControlMaestroMultiple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ControlMaestroMultiple.class})
public interface ControlMaestroMultipleComboSimpleProjection {

    Integer getId();

    String getReferencia();

    @Value("#{((target.referencia == null || target.referencia.trim().length() == 0) ? '' : (target.referencia + ' - ')) + target.valor}")
    String getValor();
}
