package com.pixvs.main.models.projections.Empleado;

import com.pixvs.main.models.Empleado;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 30/08/2022.
 */
@Projection(types = {Empleado.class})
public interface EmpleadoBajaProjection {

    Integer getId();

    String getCodigoEmpleado();

    String getNombre();

    String getPrimerApellido();

    String getSegundoApellido();
}
