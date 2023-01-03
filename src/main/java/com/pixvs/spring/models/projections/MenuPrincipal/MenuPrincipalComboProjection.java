package com.pixvs.spring.models.projections.MenuPrincipal;

import com.pixvs.spring.models.MenuPrincipal;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {MenuPrincipal.class})
public interface MenuPrincipalComboProjection {

    Integer getNodoId();

    String getEtiqueta();

}
