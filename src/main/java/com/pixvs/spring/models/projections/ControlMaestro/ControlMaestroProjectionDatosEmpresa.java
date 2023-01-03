package com.pixvs.spring.models.projections.ControlMaestro;

import com.pixvs.spring.models.ControlMaestro;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ControlMaestro.class})
public interface ControlMaestroProjectionDatosEmpresa {

    String getValor();
    String getOrden();

}
