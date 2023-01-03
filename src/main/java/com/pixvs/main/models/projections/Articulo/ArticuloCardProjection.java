package com.pixvs.main.models.projections.Articulo;

import com.pixvs.main.models.Articulo;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 09/07/2021.
 */
@Projection(types = {Articulo.class})
public interface ArticuloCardProjection {

    Integer getId();
    String getNombre();
    Integer getImagenId();
    Integer getArticuloSubtipoId();
    Boolean getPedirCantidadPV();

}
