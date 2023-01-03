package com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle;

import com.pixvs.main.models.ProgramaIdiomaExamenModalidad;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboSimpleProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenModalidad.ProgramaIdiomaExamenModalidadEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenUnidad.ProgramaIdiomaExamenUnidadEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaExamenModalidad.class})
public interface ProgramaIdiomaExamenDetalleEditarProjection {


    Integer getId();

    Integer getProgramaIdiomaExamenId();

    PAActividadEvaluacionComboProjection getActividadEvaluacion();

    ControlMaestroMultipleComboProjection getTest();

    Integer getTime();

    BigDecimal getPuntaje();

    Boolean getContinuos();

    Boolean getActivo();

    List<ProgramaIdiomaExamenModalidadEditarProjection> getModalidades();

    List<ProgramaIdiomaExamenUnidadEditarProjection> getUnidades();
}