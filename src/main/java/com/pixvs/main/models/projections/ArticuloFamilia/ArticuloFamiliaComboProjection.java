package com.pixvs.main.models.projections.ArticuloFamilia;

import com.pixvs.main.models.ArticuloFamilia;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 24/06/2020.
 */
@Projection(types = {ArticuloFamilia.class})
public interface ArticuloFamiliaComboProjection {

    Integer getId();
    String getNombre();

}
