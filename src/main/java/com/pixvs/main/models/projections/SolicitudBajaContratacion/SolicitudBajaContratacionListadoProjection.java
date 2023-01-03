package com.pixvs.main.models.projections.SolicitudBajaContratacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.SolicitudBajaContratacion;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Rene Carrillo on 31/08/2022.
 */
@Projection(types = {SolicitudBajaContratacion.class})
public interface SolicitudBajaContratacionListadoProjection {
    Integer getId();

    String getCodigo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    String getUsuario();

    String getEmpleado();

    String getTipoContrato();

    String getEstatus();
}
