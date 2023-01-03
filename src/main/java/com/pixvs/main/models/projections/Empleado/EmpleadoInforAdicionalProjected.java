package com.pixvs.main.models.projections.Empleado;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.projections.Entidad.EntidadComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Empleado.class})
public interface EmpleadoInforAdicionalProjected {
    Integer getId();
    String getCodigoEmpleado();
    @Value("#{target.codigoEmpleado + '-' +target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreCompleto();
    @Value("#{target.nombre + ' ' + target.primerApellido +   (target.segundoApellido == null ? '' : ' ' + target.segundoApellido) }")
    String getNombreApellidos();
    SucursalComboProjection getSucursal();
    EntidadComboProjection getEntidad();
    Date getFechaAlta();
}
