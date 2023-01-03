package com.pixvs.main.models.projections.Localidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Localidad;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Projection(types = {Localidad.class})
public interface LocalidadEditarProjection {

    Integer getId();

    String getCodigoLocalidad();

    String getNombre();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
