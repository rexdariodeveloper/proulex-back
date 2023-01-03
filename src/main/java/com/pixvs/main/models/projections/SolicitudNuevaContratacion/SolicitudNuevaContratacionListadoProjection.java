package com.pixvs.main.models.projections.SolicitudNuevaContratacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.SolicitudNuevaContratacion;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Rene Carrillo on 21/04/2022.
 */
@Projection(types = {SolicitudNuevaContratacion.class})
public interface SolicitudNuevaContratacionListadoProjection {
    Integer getId();

    String getCodigo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    String getUsuario();

    Integer getCantidadEmpleado();

    String getTipoContrato();

    String getEstatus();
}
