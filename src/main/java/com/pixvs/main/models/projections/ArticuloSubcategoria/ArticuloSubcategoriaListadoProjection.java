package com.pixvs.main.models.projections.ArticuloSubcategoria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ArticuloSubcategoria;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 24/06/2020.
 */
@Projection(types = {ArticuloSubcategoria.class})
public interface ArticuloSubcategoriaListadoProjection {

    Integer getId();
    ArticuloCategoriaComboProjection getCategoria();
    Integer getCategoriaId();
    String getPrefijo();
    String getNombre();
    String getDescripcion();
    Integer getArchivoId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
