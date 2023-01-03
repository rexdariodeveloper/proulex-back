package com.pixvs.spring.models.projections.Pais;

import com.pixvs.spring.models.Pais;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Projection(types = {Pais.class})
public interface PaisComboProjection {

    Integer getId();
    String getNombre();

}
