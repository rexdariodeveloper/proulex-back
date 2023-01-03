package com.pixvs.main.models.projections.PAModalidad;

import com.pixvs.main.models.PAModalidad;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 07/07/2021.
 */
@Projection(types = {PAModalidad.class})
public interface PAModalidadCardProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    Integer getImagenId();

}
