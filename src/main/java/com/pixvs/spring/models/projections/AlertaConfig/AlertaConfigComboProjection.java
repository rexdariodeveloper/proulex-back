package com.pixvs.spring.models.projections.AlertaConfig;

import com.pixvs.spring.models.AlertaConfig;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlertaConfig.class})
public interface AlertaConfigComboProjection {

    Integer getId();
    String getNombre();
    Integer getNodoId();
    String getTablaReferencia();
    Boolean getAplicaSucursales();

}
