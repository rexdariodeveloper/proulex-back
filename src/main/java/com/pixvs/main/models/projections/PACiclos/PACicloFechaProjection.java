package com.pixvs.main.models.projections.PACiclos;

import com.pixvs.main.models.PACiclo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {PACiclo.class})
public interface PACicloFechaProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    Date getFechaInicio();
    Date getFechaFin();

}
