package com.pixvs.main.models.projections.AlumnoGrupo;

import com.pixvs.main.models.AlumnoGrupo;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {AlumnoGrupo.class})
public interface AlumnoGrupoCalificacionesProjection {

    Integer getId();
    Integer getAlumnoId();
    Integer getGrupoId();
    BigDecimal getCalificacionFinal();
    BigDecimal getCalificacionConvertida();
    ControlMaestroMultipleComboSimpleProjection getEstatus();
    Integer getEstatusId();
}
