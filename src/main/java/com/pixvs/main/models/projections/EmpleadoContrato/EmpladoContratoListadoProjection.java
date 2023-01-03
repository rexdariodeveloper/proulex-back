package com.pixvs.main.models.projections.EmpleadoContrato;

import com.pixvs.main.models.EmpleadoContrato;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Benjamin Osorio Bautista on 04/12/2021.
 */
@Projection(types = {EmpleadoContrato.class})
public interface EmpladoContratoListadoProjection {

    Integer getId();

    Integer getEntidadId();

    String getEntidadNombre();

    Integer getEmpleadoId();

    String getCodigoEmpleado();

    String getNombreApellidos();

    Date getFechaAlta();

    String getTipoContrato();

    Date getFechaInicio();

    Date getFechaFin();

    String getSueldoMensual();

}
