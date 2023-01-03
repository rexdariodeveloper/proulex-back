package com.pixvs.main.models.projections.EmpleadoContrato;

import com.pixvs.main.models.EmpleadoContrato;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 29/08/2022.
 */
@Projection(types = {EmpleadoContrato.class})
public interface EmpleadoContratoComboEmpleadoProjection {

    @Value("#{ target.empleado.id }")
    Integer getId();

    @Value("#{ target.empleado.codigoEmpleado + ' - ' + target.empleado.nombre + ' ' + target.empleado.primerApellido }")
    String getNombreCompleto();
}
