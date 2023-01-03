package com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaIdiomaExamenDetalle;
import com.pixvs.main.models.ProgramaIdiomaExamenModalidad;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenModalidad.ProgramaIdiomaExamenModalidadEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenUnidad.ProgramaIdiomaExamenUnidadEditarProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaExamenDetalle.class})
public interface ProgramaIdiomaExamenDetalleListadoProjection {


    Integer getId();

    String getActividad();

    String getTestFormat();

    BigDecimal getScore();

    Integer getDias();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    Integer getTime();
}