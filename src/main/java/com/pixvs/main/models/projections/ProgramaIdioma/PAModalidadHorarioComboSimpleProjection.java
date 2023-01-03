package com.pixvs.main.models.projections.ProgramaIdioma;

import com.pixvs.main.models.PAModalidadHorario;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

@Projection(types = {PAModalidadHorario.class})
public interface PAModalidadHorarioComboSimpleProjection {

    Integer getId();
    String getNombre();
    Time getHoraInicio();
    Time getHoraFin();

}
