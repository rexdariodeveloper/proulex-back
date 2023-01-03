package com.pixvs.spring.models.projections.AlertaConfigEtapa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.AlertaConfigEtapa;
import com.pixvs.spring.models.projections.AlertaConfigEtapaAprobadores.AlertaConfigEtapaAprobadoresProjection;
import com.pixvs.spring.models.projections.AlertaConfigEtapaNotificadores.AlertaConfigEtapaNotificadoresProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import javax.persistence.OrderBy;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Projection(types = {AlertaConfigEtapa.class})
public interface AlertaConfigEtapaEditarProjection {

    Integer getId();
    Integer getAlertaConfigId();
    Integer getOrden();
    ControlMaestroMultipleComboProjection getTipoAprobacion();
    Integer getTipoAprobacionId();
    ControlMaestroMultipleComboProjection getTipoOrden();
    Integer getTipoOrdenId();
    ControlMaestroMultipleComboProjection getCondicionAprobacion();
    Integer getCondicionAprobacionId();
    Boolean getCriteriosEconomicos();
    BigDecimal getMontoMinimo();
    BigDecimal getMontoMaximo();
    ControlMaestroMultipleComboProjection getTipoMonto();
    Integer getTipoMontoId();
    Boolean getAutorizacionDirecta();
    ControlMaestroMultipleComboProjection getEstatusReferencia();
    Integer getEstatusReferenciaId();
    ControlMaestroMultipleComboProjection getTipoAlerta();
    Integer getTipoAlertaId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    UsuarioComboProjection getCreadoPor();
    Integer getCreadoPorId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();
    UsuarioComboProjection getModificadoPor();
    Integer getModificadoPorId();
    List<AlertaConfigEtapaAprobadoresProjection> getDetalles();
    List<AlertaConfigEtapaNotificadoresProjection> getNotificadores();
    //Integer getSucursalId();
    Boolean getNotificarCreador();
    Boolean getMostrarUsuario();
    Boolean getNotificacionCorreo();
}
