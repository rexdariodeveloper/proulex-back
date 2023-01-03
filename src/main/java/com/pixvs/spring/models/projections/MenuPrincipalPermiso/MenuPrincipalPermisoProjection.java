package com.pixvs.spring.models.projections.MenuPrincipalPermiso;

import com.pixvs.spring.models.MenuPrincipalPermiso;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {MenuPrincipalPermiso.class})
public interface MenuPrincipalPermisoProjection {

    Integer getId();
    Integer getNodoId();
    String getNombre();
    Boolean getActivo();

}
