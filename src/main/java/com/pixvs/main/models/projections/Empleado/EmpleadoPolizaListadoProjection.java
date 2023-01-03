package com.pixvs.main.models.projections.Empleado;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Empleado.class})
public interface EmpleadoPolizaListadoProjection {
    Integer getId();
    String getCodigoEmpleado();
    String getNombreCompleto();
    String getVigencia();
    String getEntidadNombre();

}
