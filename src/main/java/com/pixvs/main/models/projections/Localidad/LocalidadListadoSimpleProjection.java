package com.pixvs.main.models.projections.Localidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Localidad;
import com.pixvs.main.models.projections.Almacen.AlmacenComboProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
@Projection(types = {Localidad.class})
public interface LocalidadListadoSimpleProjection {

    Integer getId();

    String getCodigoLocalidad();

    String getNombre();

    Boolean getLocalidadGeneral();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    //List<ArticuloComboProjection> getArticulos();

    AlmacenComboProjection getAlmacen();
}
