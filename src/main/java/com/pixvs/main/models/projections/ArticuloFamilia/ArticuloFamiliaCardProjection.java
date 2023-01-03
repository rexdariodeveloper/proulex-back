package com.pixvs.main.models.projections.ArticuloFamilia;

import com.pixvs.main.models.ArticuloFamilia;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 09/07/2021.
 */
@Projection(types = {ArticuloFamilia.class})
public interface ArticuloFamiliaCardProjection {

    Integer getId();
    String getNombre();
    Integer getImagenId();

}
