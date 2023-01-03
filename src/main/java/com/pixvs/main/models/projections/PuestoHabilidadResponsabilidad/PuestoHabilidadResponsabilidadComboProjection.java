package com.pixvs.main.models.projections.PuestoHabilidadResponsabilidad;

import com.pixvs.main.models.PuestoHabilidadResponsabilidad;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {PuestoHabilidadResponsabilidad.class})
public interface PuestoHabilidadResponsabilidadComboProjection {

    Integer getId();

    Integer getPuestoId();

    String getDescripcion();

    Boolean getEsHabilidad();

}
