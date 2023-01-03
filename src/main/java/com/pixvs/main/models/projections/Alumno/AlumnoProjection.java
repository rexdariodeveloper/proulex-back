package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 08/07/2021.
 */
@Projection(types = {Alumno.class})
public interface AlumnoProjection {

    Integer getId();
    String getCodigo();
    String getSegundoApellido();
    String getPrimerApellido();
    String getNombre();
    String getCodigoAlumnoUDG();
}
