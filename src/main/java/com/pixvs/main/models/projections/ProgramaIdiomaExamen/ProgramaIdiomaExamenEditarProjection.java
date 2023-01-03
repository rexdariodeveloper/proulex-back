package com.pixvs.main.models.projections.ProgramaIdiomaExamen;

import com.pixvs.main.models.ProgramaIdiomaExamenModalidad;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleEditarProjection;
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
public interface ProgramaIdiomaExamenEditarProjection {


    Integer getId();

    Integer getProgramaIdiomaNivelId();

    String getNombre();

    BigDecimal getPorcentaje();

    Boolean getActivo();

    Integer getOrden();

    List<ProgramaIdiomaExamenDetalleEditarProjection> getDetalles();
}