package com.pixvs.main.models.projections.AlumnoAsistencia;

import com.pixvs.main.models.AlumnoAsistencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlumnoAsistencia.class})
public interface ReporteAsistenciasProjection {

    String getCodigo();
    String getCodigoAlumno();
    String getPrimerApellido();
    String getSegundoApellido();
    String getNombre();
    String getFaltas();
    String getAsistencias();
    String getEstatus();
    String getCodigoGrupo();
    @Value("#{target.primerApellido + (target.segundoApellido != null ? (' ' + target.segundoApellido) : '') + ' ' +target.nombre + ' ' + target.primerApellido + (target.segundoApellido != null ? (' ' + target.segundoApellido) : '')}")
    String getNombreBuscar();
}
