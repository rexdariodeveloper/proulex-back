package com.pixvs.main.models.projections.EntidadBeca;

import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.ListadoPrecio.ListadoPrecioComboProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Entidad.class})
public interface EntidadBecaListadoProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    ListadoPrecioComboProjection getListadoPrecio();

    Boolean getActivo();
}