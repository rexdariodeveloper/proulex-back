package com.pixvs.spring.models.projections.Municipio;

import com.pixvs.spring.models.Municipio;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Municipio.class})
public interface MunicipioComboProjection {

    Integer getId();
    String getNombre();

}
