package com.pixvs.spring.models.projections.Pais;

import com.pixvs.spring.models.Pais;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 2021/12/03.
 */
@Projection(types = {Pais.class})
public interface PaisNacionalidadProjection {

    Integer getId();

    String getNombre();

    String getNacionalidad();

}