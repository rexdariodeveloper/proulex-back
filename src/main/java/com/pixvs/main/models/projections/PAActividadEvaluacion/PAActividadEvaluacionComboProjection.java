package com.pixvs.main.models.projections.PAActividadEvaluacion;

import com.pixvs.main.models.PAActividadEvaluacion;
import com.pixvs.main.models.PACiclo;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {PAActividadEvaluacion.class})
public interface PAActividadEvaluacionComboProjection {

    Integer getId();
    String getCodigo();
    String getActividad();

}
