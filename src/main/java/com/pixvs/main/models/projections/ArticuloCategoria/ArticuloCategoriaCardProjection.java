package com.pixvs.main.models.projections.ArticuloCategoria;

import com.pixvs.main.models.ArticuloCategoria;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 09/07/2021.
 */
@Projection(types = {ArticuloCategoria.class})
public interface ArticuloCategoriaCardProjection {

    Integer getId();
    String getNombre();
    Integer getImagenId();

}
