package com.pixvs.main.models.projections.AlumnoAsistencia;

import com.pixvs.main.models.AlumnoAsistencia;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlumnoAsistencia.class})
public interface AlumnoAsistenciaResumenProjection {

    Integer getAlumnoId();
    Integer getGrupoId();
    Integer getAsistencias();
    Integer getFaltas();
    Integer getRetardos();
}
