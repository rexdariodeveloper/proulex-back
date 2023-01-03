package com.pixvs.main.models.projections.PAActividadEvaluacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PAActividadEvaluacion;
import com.pixvs.main.models.PACiclo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {PAActividadEvaluacion.class})
public interface PAActividadEvaluacionListadoProjection {

    Integer getId();
    String getCodigo();
    String getActividad();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
}
