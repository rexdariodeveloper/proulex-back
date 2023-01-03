package com.pixvs.main.models.projections.Temporada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Temporada;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {Temporada.class})
public interface TemporadaListadoProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();
    Boolean getActivo();

}