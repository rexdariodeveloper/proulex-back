package com.pixvs.main.models.projections.Inscripcion;

import com.pixvs.main.models.projections.Alumno.AlumnoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;

public interface InscripcionProjection {

    Integer getId();
    String getCodigo();
    Integer getOrdenVentaDetalleId();
    AlumnoProjection getAlumno();
    Integer getGrupoId();
    ControlMaestroMultipleComboProjection getEstatus();
    Integer getEstatusId();
}
