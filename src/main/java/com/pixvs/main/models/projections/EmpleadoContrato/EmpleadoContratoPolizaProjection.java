package com.pixvs.main.models.projections.EmpleadoContrato;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.EmpleadoContrato;
import com.pixvs.main.models.projections.Empleado.EmpleadoInforAdicionalProjected;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {EmpleadoContrato.class})
public interface EmpleadoContratoPolizaProjection {

    Integer getId();

    Integer getEmpleadoId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    EmpleadoInforAdicionalProjected getEmpleado();

}
