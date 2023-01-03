package com.pixvs.spring.models.projections.DepartamentoResponsabilidadHabilidad;

import com.pixvs.spring.models.DepartamentoResponsabilidadHabilidad;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {DepartamentoResponsabilidadHabilidad.class})
public interface DepartamentoResponsabilidadHabilidadProjection {

    Integer getId();

    Integer getDepartamentoId();

    String getDescripcion();

    Boolean getEsResponsabilidad();

}
