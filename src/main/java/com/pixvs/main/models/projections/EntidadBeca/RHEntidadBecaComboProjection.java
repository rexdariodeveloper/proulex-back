package com.pixvs.main.models.projections.EntidadBeca;

import com.pixvs.main.models.Entidad;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Entidad.class})
public interface RHEntidadBecaComboProjection {

    Integer getId();

    String getEntidad();
}