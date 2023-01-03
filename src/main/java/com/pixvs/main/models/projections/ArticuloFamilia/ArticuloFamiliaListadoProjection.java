package com.pixvs.main.models.projections.ArticuloFamilia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ArticuloFamilia;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 24/06/2020.
 */
@Projection(types = {ArticuloFamilia.class})
public interface ArticuloFamiliaListadoProjection {

    Integer getId();
    String getNombre();
    String getPrefijo();
    String getDescripcion();
    Integer getArchivoId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
