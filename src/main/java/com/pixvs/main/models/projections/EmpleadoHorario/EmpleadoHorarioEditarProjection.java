package com.pixvs.main.models.projections.EmpleadoHorario;

import com.pixvs.main.models.EmpleadoHorario;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;

/**
 * Created by Rene Carrillo on 31/03/2022.
 */
@Projection(types = {EmpleadoHorario.class})
public interface EmpleadoHorarioEditarProjection {
    Integer getId();

    Integer getEmpleadoId();

    String getDia();

    Time getHoraInicio();

    Time getHoraFin();
}
