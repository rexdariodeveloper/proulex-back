package com.pixvs.spring.models.projections.AlertaDetalle;

import com.pixvs.spring.models.AlertaConfigEtapaNotificadores;
import com.pixvs.spring.models.AlertaDetalle;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AlertaDetalle.class})
public interface AlertaDetalleProjection {

    Integer getId();
    Integer getEtapa();
    Integer getAlertaId();
    Integer getEstatusDetalleId();
    Integer getUsuarioId();

}
