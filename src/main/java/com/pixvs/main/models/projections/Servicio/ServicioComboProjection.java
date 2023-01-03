package com.pixvs.main.models.projections.Servicio;

import com.pixvs.main.models.Servicio;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Servicio.class})
public interface ServicioComboProjection {

    Integer getId();
    String getConcepto();
    Integer getTipoServicioId();
    Boolean getRequiereXML();
    Boolean getRequierePDF();
    Integer getArticuloId();

}
