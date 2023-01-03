package com.pixvs.spring.models.projections.Alerta;

import com.pixvs.spring.models.AlertaDetalle;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;

@Projection(name = "alertaDetalleProjection", types = {AlertaDetalle.class})
public interface AlertaDetalleProjection {

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
    Integer getEtapaId();
}
