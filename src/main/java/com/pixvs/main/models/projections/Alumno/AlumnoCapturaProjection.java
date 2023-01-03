package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Alumno.class})
public interface AlumnoCapturaProjection {

    Integer getAlumnoId();
    Integer getGrupoId();
    String getCodigo();
    String getPrimerApellido();
    String getSegundoApellido();
    String getNombre();
    String getCorreo();
    String getTelefono();
    String getSemestre();
    String getGrupo();
    String getTurno();
    String getInstitucion();
    String getCarrera();
    String getCodigoProulex();
}
