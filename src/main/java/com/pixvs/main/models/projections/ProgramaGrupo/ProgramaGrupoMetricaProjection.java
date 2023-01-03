package com.pixvs.main.models.projections.ProgramaGrupo;

import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoMetricaProjection {

    Integer getId();
    String getNombre();
    BigDecimal getPorcentaje();
}
