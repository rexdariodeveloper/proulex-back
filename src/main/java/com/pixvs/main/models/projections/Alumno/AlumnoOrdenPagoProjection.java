package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Alumno.class})
public interface AlumnoOrdenPagoProjection {

    String getCodigoAlumno();
    String getCodigoUDG();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    String getGenero();
    String getTelefono();
    String getFechaNacimiento();
    String getCorreoElectronico();
    String getCodigoGrupo();
    String getFechaInicio();
    String getFechaFin();
    String getNivel();
    String getHorario();

}
