package com.pixvs.main.models.projections.Entidad;

import com.pixvs.main.models.Entidad;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Entidad.class})
public interface EntidadWordContratoProjection {
    Integer getId();

    String getCodigo();

    String getNombre();

    String getDirector();

    String getEntidadIndependiente();

    Boolean getActivo();
}
