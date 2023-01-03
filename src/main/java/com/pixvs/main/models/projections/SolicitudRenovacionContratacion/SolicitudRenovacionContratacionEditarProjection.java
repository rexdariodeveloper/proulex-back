package com.pixvs.main.models.projections.SolicitudRenovacionContratacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.SolicitudRenovacionContratacion;
import com.pixvs.main.models.projections.SolicitudRenovacionContratacionDetalle.SolicitudRenovacionContratacionDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

/**
 * Created by Rene Carrillo on 27/04/2022.
 */
@Projection(types = {SolicitudRenovacionContratacion.class})
public interface SolicitudRenovacionContratacionEditarProjection {

    Integer getId();

    String getCodigo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    Integer getEstatusId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    List<SolicitudRenovacionContratacionDetalleEditarProjection> getListaSolicitudRenovacionContratacionDetalle();
}
