package com.pixvs.main.models.projections.ProgramaGrupoIncompanyClaseReprogramada;

import com.pixvs.main.models.ProgramaGrupoIncompanyClaseCancelada;
import com.pixvs.main.models.ProgramaGrupoIncompanyClaseReprogramada;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Time;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramaGrupoIncompanyClaseReprogramada.class})
public interface ProgramaGrupoIncompanyClaseReprogramadaEditarProjection {


    Integer getId();

    Integer getGrupoId();

    Date getFechaReprogramar();

    Date getFechaNueva();

    Time getHoraInicio();

    Time getHoraFin();

    String getMotivo();

}