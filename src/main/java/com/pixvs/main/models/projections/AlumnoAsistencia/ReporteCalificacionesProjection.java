package com.pixvs.main.models.projections.AlumnoAsistencia;

import com.pixvs.main.models.AlumnoAsistencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {AlumnoAsistencia.class})
public interface ReporteCalificacionesProjection {

    String getCodigo();
    String getCodigoAlumno();
    String getPrimerApellido();
    String getSegundoApellido();
    String getNombre();
    String getCodigoGrupo();
    String getEstatus();
    BigDecimal getCalificacionFinal();
    @Value("#{target.primerApellido + (target.segundoApellido != null ? (' ' + target.segundoApellido) : '') + ' ' +target.nombre + ' ' + target.primerApellido + (target.segundoApellido != null ? (' ' + target.segundoApellido) : '')}")
    String getNombreBuscar();
}
