package com.pixvs.main.models.projections.SolicitudRenovacionContratacionDetalle;

import com.pixvs.main.models.SolicitudRenovacionContratacionDetalle;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by Rene Carrillo on 27/04/2022.
 */
@Projection(types = {SolicitudRenovacionContratacionDetalle.class})
public interface SolicitudRenovacionContratacionDetalleEditarProjection {

    Integer getId();

    Integer getSolicitudRenovacionContratacionId();

    EmpleadoEditarProjection getEmpleado();

    Integer getEstatusId();
}
