package com.pixvs.main.models.projections.SolicitudRenovacionContratacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.SolicitudRenovacionContratacion;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Rene Carrillo on 27/04/2022.
 */
@Projection(types = {SolicitudRenovacionContratacion.class})
public interface SolicitudRenovacionContratacionListadoProjection {
    Integer getId();

    String getCodigo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    String getUsuario();

    Integer getCantidadEmpleado();

    String getTipoContrato();

    String getEstatus();
}
