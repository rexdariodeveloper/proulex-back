package com.pixvs.main.models.projections.Empleado;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Empleado.class})
public interface EmpleadoComboProjection {


    Integer getId();
    @Value("#{target.codigoEmpleado + '-' +target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();
    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompletoSinCodigo();
    @Value("#{target.codigoEmpleado + ' - ' +target.primerApellido + ' ' + (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) + ' ' + target.nombre }")
    String getNombreCompletoASC();
    SucursalComboProjection getSucursal();
}