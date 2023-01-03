package com.pixvs.main.models.projections.AlumnoGrupo;

import com.pixvs.main.models.AlumnoGrupo;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlumnoGrupo.class})
public interface AlumnoGrupoListadoProjection {

    String getId();
    String getCodigo();
    String getNombre();
    String getGrupo();
    String getFaltas();
    String getAsistencias();
    String getEstatus();
}
