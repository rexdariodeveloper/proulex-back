package com.pixvs.spring.models.projections.Estado;

import com.pixvs.spring.models.Estado;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Estado.class})
public interface EstadoComboProjection {

    Integer getId();
    String getNombre();

}
