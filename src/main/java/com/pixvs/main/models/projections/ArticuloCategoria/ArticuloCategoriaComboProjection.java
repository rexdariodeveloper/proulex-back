package com.pixvs.main.models.projections.ArticuloCategoria;

import com.pixvs.main.models.ArticuloCategoria;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 24/06/2020.
 */
@Projection(types = {ArticuloCategoria.class})
public interface ArticuloCategoriaComboProjection {

    Integer getId();
    String getNombre();
    ArticuloFamiliaComboProjection getFamilia();

}
