package com.pixvs.main.models.projections.AlumnoGrupo;

import com.pixvs.main.models.AlumnoGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {AlumnoGrupo.class})
public interface AlumnoListadoCapturaProjection {

    Integer getAlumnoId();
    String getCodigo();
    String getCodigoUDG();
    String getPrimerApellido();
    String getSegundoApellido();
    String getNombre();
    BigDecimal getCalificacion();
    Integer getEstatusId();
    String getEstatus();
}
