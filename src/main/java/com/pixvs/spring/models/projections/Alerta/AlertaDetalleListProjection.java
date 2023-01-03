package com.pixvs.spring.models.projections.Alerta;

import com.pixvs.spring.models.AlertaDetalle;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleEditarProjection;
import com.pixvs.spring.models.projections.Alerta.AlertaProjection;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;

@Projection(name = "alertaDetalleListProjection", types = {AlertaDetalle.class})
public interface AlertaDetalleListProjection {

    Integer getId();
    // UsuarioProjection getUsuario();
    Integer getAlertaId();
    Integer getEstatusDetalleId();
    ControlMaestroMultipleEditarProjection getEstatusDetalle();
    Integer getUsuarioId();
    Timestamp getFechaAtendido();
    Boolean getArchivado();
    Boolean getMostrar();
    Boolean getVisto();
    String getComentario();
    Timestamp getFechaCreacion();
    Integer getCreadoPorId();
    Timestamp getFechaUltimaModificacion();
    Integer getModificadoPorId();
    String getTimestamp();
    AlertaSimpleProjection getAlerta();
    Integer getEtapaId();
}
