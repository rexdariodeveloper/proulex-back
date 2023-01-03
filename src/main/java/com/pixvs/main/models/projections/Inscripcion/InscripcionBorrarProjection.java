package com.pixvs.main.models.projections.Inscripcion;

import com.pixvs.main.models.projections.Alumno.AlumnoListadoGrupoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;

public interface InscripcionBorrarProjection {

    Integer getId();
    String getCodigoOv();
    String getCodigoAlumno();
}
