package com.pixvs.main.models.projections.ArticuloSubcategoria;

import com.pixvs.main.models.ArticuloSubcategoria;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 09/07/2021.
 */
@Projection(types = {ArticuloSubcategoria.class})
public interface ArticuloSubcategoriaCardProjection {

    Integer getId();
    String getNombre();
    Integer getImagenId();

}
