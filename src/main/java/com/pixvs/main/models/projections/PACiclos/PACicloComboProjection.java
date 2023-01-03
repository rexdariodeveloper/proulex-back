package com.pixvs.main.models.projections.PACiclos;

import com.pixvs.main.models.FormaPago;
import com.pixvs.main.models.PACiclo;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {PACiclo.class})
public interface PACicloComboProjection {

    Integer getId();
    String getCodigo();
    String getNombre();

}
