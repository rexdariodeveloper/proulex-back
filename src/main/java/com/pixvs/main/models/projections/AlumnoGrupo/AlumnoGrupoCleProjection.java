package com.pixvs.main.models.projections.AlumnoGrupo;

import com.pixvs.main.models.AlumnoGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {AlumnoGrupo.class})
public interface AlumnoGrupoCleProjection {

    Integer getAlumnoGrupoId();
    Integer getAlumnoGrupoEstatusId();
    Integer getProgramaGrupoEstudianteCleId();
    Integer getAlumnoId();
    Date getFechaUltimaActualizacionCle();
    Integer getGrupoId();
    Integer getUsuarioCleId();
    Integer getGrupoProfesorId();
    Integer getGrupoEstudiantesId();


    String getAlumnoNombre();
    String getAlumnoApellidos();
    String getAlumnoCorreoElectronico();

    String getAlumnoCodigo();

    Date getAlumnoFechaNacimiento();


}
