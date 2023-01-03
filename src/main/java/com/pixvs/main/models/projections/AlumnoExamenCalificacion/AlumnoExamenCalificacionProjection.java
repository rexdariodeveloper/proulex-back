package com.pixvs.main.models.projections.AlumnoExamenCalificacion;

import com.pixvs.main.models.AlumnoExamenCalificacion;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {AlumnoExamenCalificacion.class})
public interface AlumnoExamenCalificacionProjection {

    Integer getId();
    Integer getAlumnoId();
    Integer getGrupoId();
    Integer getProgramaIdiomaExamenDetalleId();
    Integer getProgramaGrupoExamenDetalleId();
    BigDecimal getPuntaje();

}
