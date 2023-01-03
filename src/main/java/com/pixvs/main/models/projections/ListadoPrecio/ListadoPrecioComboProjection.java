package com.pixvs.main.models.projections.ListadoPrecio;

import com.pixvs.main.models.ListadoPrecio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ListadoPrecio.class})
public interface ListadoPrecioComboProjection {

    Integer getId();

    @Value("#{target.codigo + ' - ' +target.nombre  }")
    String getNombre();
}