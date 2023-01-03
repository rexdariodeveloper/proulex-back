package com.pixvs.spring.models.projections.AlertaConfigEtapa;

import com.pixvs.spring.models.AlertaConfigEtapa;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlertaConfigEtapa.class})
public interface AlertaConfigEtapaComboProjection {

    Integer getId();
    Integer getAlertaConfigId();
    Integer getOrden();
    //Integer getSucursalId();
    Boolean getNotificarCreador();
}
