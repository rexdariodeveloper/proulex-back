package com.pixvs.main.models.projections.ProgramaGrupo;

import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ProgramaGrupo.class})
public interface ReporteGeneralAlumnoProjection {

    String getPlantelAlumno();

    Integer getTotalAlumnos();

    String getNivel();
}