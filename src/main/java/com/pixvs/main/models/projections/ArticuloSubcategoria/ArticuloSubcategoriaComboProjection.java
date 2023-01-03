package com.pixvs.main.models.projections.ArticuloSubcategoria;

import com.pixvs.main.models.ArticuloSubcategoria;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 08/07/2020.
 */
@Projection(types = {ArticuloSubcategoria.class})
public interface ArticuloSubcategoriaComboProjection {

    Integer getId();
    String getNombre();
    ArticuloCategoriaComboProjection getCategoria();

}
