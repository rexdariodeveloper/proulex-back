package com.pixvs.main.models.projections.Temporada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Temporada;
import com.pixvs.main.models.projections.TemporadaDetalle.TemporadaDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {Temporada.class})
public interface TemporadaEditarProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();
    Boolean getActivo();
    int getCreadoPorId();
    Integer getModificadoPorId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    List<TemporadaDetalleEditarProjection> getDetalles();

}