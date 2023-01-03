package com.pixvs.main.models.projections.Empleado;

import org.springframework.beans.factory.annotation.Value;

public interface EmpleadoContratoInfoProjection {

    Integer getId();

    @Value("#{target.codigoEmpleado + '-' +target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

}
