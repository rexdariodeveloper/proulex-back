package com.pixvs.main.models.projections.SolicitudNuevaContratacionDetalle;

import com.pixvs.main.models.SolicitudNuevaContratacionDetalle;
import com.pixvs.main.models.projections.Empleado.EmpleadoEditarProjection;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 21/04/2022.
 */
@Projection(types = {SolicitudNuevaContratacionDetalle.class})
public interface SolicitudNuevaContratacionDetalleEditarProjection {

    Integer getId();

    Integer getSolicitudNuevaContratacionId();

    EmpleadoEditarProjection getEmpleado();

    Integer getEstatusId();
}
