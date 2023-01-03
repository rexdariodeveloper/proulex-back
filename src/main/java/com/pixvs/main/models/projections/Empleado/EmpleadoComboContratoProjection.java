package com.pixvs.main.models.projections.Empleado;

import com.pixvs.main.models.Empleado;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by Benjamin Osorio
 * */
@Projection(types = {Empleado.class})
public interface EmpleadoComboContratoProjection {

    Integer getId();

    @Value("#{target.codigoEmpleado + '-' +target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();

    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompletoSinCodigo();

    @Value("#{target.codigoEmpleado + ' - ' +target.primerApellido + ' ' + (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) + ' ' + target.nombre }")
    String getNombreCompletoASC();

}
