package com.pixvs.main.models.projections.Entidad;

import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Entidad.class})
public interface EntidadComboProjection {


    Integer getId();

    String getCodigo();

    @Value("#{target.codigo + ' ' +target.nombre}")
    String getNombre();

}