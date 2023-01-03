package com.pixvs.main.models.projections.SolicitudNuevaContratacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.SolicitudNuevaContratacion;
import com.pixvs.main.models.projections.SolicitudNuevaContratacionDetalle.SolicitudNuevaContratacionDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Rene Carrillo on 21/04/2022.
 */
@Projection(types = {SolicitudNuevaContratacion.class})
public interface SolicitudNuevaContratacionEditarProjection {
    Integer getId();

    String getCodigo();

    Integer getEstatusId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    List<SolicitudNuevaContratacionDetalleEditarProjection> getListaSolicitudNuevaContratacionDetalle();
}
