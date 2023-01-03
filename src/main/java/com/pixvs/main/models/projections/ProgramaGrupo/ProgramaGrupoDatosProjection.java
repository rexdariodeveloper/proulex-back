package com.pixvs.main.models.projections.ProgramaGrupo;

import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoDatosProjection {

    String getSucursalNombre();
    String getProgramacionCodigo();
    String getProgramaNombre();
    String getCodigoGrupo();
    String getNivel();
    String getGrupo();
    String getFechaInicio();
    String getFechaFin();
    String getModalidadNombre();
    String getProfesor();
    //String getHorario();
}
