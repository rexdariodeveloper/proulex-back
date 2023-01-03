package com.pixvs.main.models.projections.Empleado;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.EmpleadoContacto;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Departamento.DepartamentoComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {Empleado.class})
public interface EmpleadoListadoProjection {


    Integer getId();

    String getCodigoEmpleado();

    @Value("#{(target.usuario != null ? target.usuario.nombre: target.nombre) + ' ' + (target.usuario !=null? target.usuario.primerApellido : target.primerApellido) + ' ' +(target.usuario != null? target.usuario.segundoApellido : target.segundoApellido) }")
    String getNombreCompleto();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaAlta();

    ControlMaestroMultipleComboProjection getTipoEmpleado();

    DepartamentoComboProjection getDepartamento();

    SucursalComboProjection getSucursal();

    ControlMaestroMultipleComboProjection getEstatus();

}