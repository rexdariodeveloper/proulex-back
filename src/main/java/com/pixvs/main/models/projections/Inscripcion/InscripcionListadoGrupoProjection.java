package com.pixvs.main.models.projections.Inscripcion;

import com.pixvs.main.models.Inscripcion;
import com.pixvs.main.models.projections.Alumno.AlumnoListadoGrupoProjection;
import com.pixvs.main.models.projections.Alumno.AlumnoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

public interface InscripcionListadoGrupoProjection {

    Integer getId();
    AlumnoListadoGrupoProjection getAlumno();
    Integer getGrupoId();
    ControlMaestroMultipleComboProjection getEstatus();
    String getCodigo();
    Integer getTurnoId();
    String getCarreraTexto();
    Integer getGradoId();
}
