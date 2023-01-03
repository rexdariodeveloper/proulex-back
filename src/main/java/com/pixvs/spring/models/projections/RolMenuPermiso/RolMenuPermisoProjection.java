package com.pixvs.spring.models.projections.RolMenuPermiso;

import com.pixvs.spring.models.RolMenuPermiso;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {RolMenuPermiso.class})
public interface RolMenuPermisoProjection {

    Integer getId();
    String getNombrePermiso();
    Integer getRolId();
    Integer getPermisoId();

}
