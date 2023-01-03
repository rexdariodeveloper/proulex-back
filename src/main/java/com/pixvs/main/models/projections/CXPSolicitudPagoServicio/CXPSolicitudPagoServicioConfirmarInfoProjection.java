package com.pixvs.main.models.projections.CXPSolicitudPagoServicio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPSolicitudPagoServicio;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {CXPSolicitudPagoServicio.class})
public interface CXPSolicitudPagoServicioConfirmarInfoProjection {

    Integer getId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
