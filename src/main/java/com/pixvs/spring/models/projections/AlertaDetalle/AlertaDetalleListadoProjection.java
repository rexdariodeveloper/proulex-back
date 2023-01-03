package com.pixvs.spring.models.projections.AlertaDetalle;

import com.pixvs.spring.models.AlertaDetalle;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {AlertaDetalle.class})
public interface AlertaDetalleListadoProjection {

    Integer getId();
    Integer getProcesoId();
    Date getFecha();
    String getCodigo();
    String getSede();
    Integer getSedeId();
    String getTipo();
    Integer getTipoId();
    String getInicadaPor();
    Integer getUsuarioId();
    String getEstatus();
    Integer getEstatusId();
    String getTabla();
    Boolean getVisto();
    String getUrl();
    String getUrlAlerta();
    String getUrlNotificacion();
    String getUrlDocumento();

}
