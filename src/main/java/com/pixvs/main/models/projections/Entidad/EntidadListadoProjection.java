package com.pixvs.main.models.projections.Entidad;

import com.pixvs.main.models.Entidad;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.EntidadContrato.EntidadContratoEditarProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Entidad.class})
public interface EntidadListadoProjection {

    Integer getId();

    String getCodigo();

    String getNombre();

    String getDirector();

    String getEntidadIndependiente();

    Boolean getActivo();
}