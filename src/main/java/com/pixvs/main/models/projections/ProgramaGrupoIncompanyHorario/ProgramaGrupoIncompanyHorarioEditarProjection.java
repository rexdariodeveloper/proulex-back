package com.pixvs.main.models.projections.ProgramaGrupoIncompanyHorario;

import com.pixvs.main.models.ProgramaGrupoIncompanyClaseCancelada;
import com.pixvs.main.models.ProgramaGrupoIncompanyHorario;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.PAModalidad.PAModalidadComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramaGrupoIncompanyHorario.class})
public interface ProgramaGrupoIncompanyHorarioEditarProjection {


    Integer getId();

    Integer getGrupoId();

    String getDia();

    Time getHoraInicio();

    Time getHoraFin();
}