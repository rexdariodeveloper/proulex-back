package com.pixvs.main.models.projections.PAModalidadHorario;

import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAModalidadHorario;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

@Projection(types = {PAModalidadHorario.class})
public interface PAModalidadHorarioComboProjection {

    Integer getId();
    Integer getModalidadId();
    String getNombre();
    Time getHoraInicio();
    Time getHoraFin();
    String getCodigo();
}
