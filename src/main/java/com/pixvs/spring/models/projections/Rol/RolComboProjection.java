package com.pixvs.spring.models.projections.Rol;

import com.pixvs.spring.models.Rol;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Rol.class})
public interface RolComboProjection {

    Integer getId();

    String getNombre();


}
