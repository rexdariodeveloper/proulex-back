package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Rene Carrillo on 13/09/2022.
 */

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoReaperturaGrupoProjection {

    Integer getId();

    String getSede();

    String getPrograma();

    String getIdioma();

    String getNivel();

    String getHorario();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFinInscripcion();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFinInscripcionBeca();
}
