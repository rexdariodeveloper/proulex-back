package com.pixvs.main.models.projections.PAActividadEvaluacion;

import com.pixvs.main.models.PAActividadEvaluacion;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {PAActividadEvaluacion.class})
public interface PAActividadEvaluacionCapturaProjection {

    Integer getGrupoId();
    Integer getDetalleId();
    String getNivel();
    String getGrupo();
    String getHorario();
    String getActividad();
    BigDecimal getPuntos();
}
