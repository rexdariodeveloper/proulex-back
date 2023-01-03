package com.pixvs.main.models.projections.Cliente;

import com.pixvs.main.models.Cliente;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Cliente.class})
public interface ClienteCardProjection {

    Integer getId();
    String getNombre();

}
