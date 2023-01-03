package com.pixvs.main.models.projections.ProgramaIdiomaNivel;

import com.pixvs.main.models.ProgramaIdiomaExamenModalidad;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamen.ProgramaIdiomaExamenEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaIdiomaExamenModalidad.class})
public interface ProgramaIdiomaNivelEditarProjection {


    Integer getId();

    Integer getProgramaIdiomaId();

    PAModalidadComboProjection getModalidad();

    Integer getModalidadId();

    Integer getNivelInicial();

    Integer getNivelFinal();

    Boolean getActivo();

    List<ProgramaIdiomaExamenEditarProjection> getExamenes();
}