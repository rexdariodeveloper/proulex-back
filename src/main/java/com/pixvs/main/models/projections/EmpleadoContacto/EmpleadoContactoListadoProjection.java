package com.pixvs.main.models.projections.EmpleadoContacto;

import com.pixvs.main.models.EmpleadoContacto;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpleadoContacto.class})
public interface EmpleadoContactoListadoProjection {


    Integer getId();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

    String getParentesco();

    String getTelefono();

    String getMovil();

    String getCorreoElectronico();

}