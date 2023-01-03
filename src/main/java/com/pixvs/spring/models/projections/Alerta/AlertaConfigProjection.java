package com.pixvs.spring.models.projections.Alerta;

import com.pixvs.spring.models.AlertaConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "alertasConfigProjection", types = {AlertaConfig.class})
public interface AlertaConfigProjection {

    Integer getId();
    String getNombre();
    Integer getTipoMovimientoId();
    String getTablaReferencia();
    @Value("#{target.nodo.url}")
    String getUrl();
    String getUrlAlerta();
    String getUrlNotificacion();
    String getUrlDocumento();

}
