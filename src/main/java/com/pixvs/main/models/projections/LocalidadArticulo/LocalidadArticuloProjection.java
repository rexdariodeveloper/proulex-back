package com.pixvs.main.models.projections.LocalidadArticulo;

import com.pixvs.main.models.LocalidadArticulo;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {LocalidadArticulo.class})
public interface LocalidadArticuloProjection {

    Integer getLocalidadId();

    Integer getArticuloId();
}
