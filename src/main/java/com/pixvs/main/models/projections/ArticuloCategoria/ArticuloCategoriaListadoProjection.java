package com.pixvs.main.models.projections.ArticuloCategoria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ArticuloCategoria;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 24/06/2020.
 */
@Projection(types = {ArticuloCategoria.class})
public interface ArticuloCategoriaListadoProjection {

    Integer getId();
    ArticuloFamiliaComboProjection getFamilia();
    Integer getFamiliaId();
    String getNombre();
    String getPrefijo();
    String getDescripcion();
    Integer getArchivoId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
