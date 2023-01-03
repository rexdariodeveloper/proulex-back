package com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion;

import com.pixvs.main.models.ProgramaGrupoIncompanyCriterioEvaluacion;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramaGrupoIncompanyCriterioEvaluacion.class})
public interface ProgramaGrupoIncompanyCriterioEvaluacionListadoEditarProjection {

    @Value("#{null}")
    Integer getId();

    @Value("#{null}")
    Integer getGrupoId();

    PAActividadEvaluacionComboProjection getActividadEvaluacion();

    PAModalidadComboProjection getModalidad();

    ControlMaestroMultipleComboProjection getTestFormat();

    Date getFechaAplica();

    Integer getScore();

    Integer getTime();

    Integer getDias();

    Boolean getActivo();

    Integer getOrden();
}