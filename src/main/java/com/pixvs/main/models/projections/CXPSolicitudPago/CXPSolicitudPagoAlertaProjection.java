package com.pixvs.main.models.projections.CXPSolicitudPago;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPSolicitudPago;
import com.pixvs.main.models.projections.CXPSolicitudPagoDetalle.CXPSolicitudPagoDetalleAlertaProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(types = {CXPSolicitudPago.class})
public interface CXPSolicitudPagoAlertaProjection {

    Integer getId();
    String getCodigoSolicitud();

    UsuarioComboProjection getCreadoPor();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

    List<CXPSolicitudPagoDetalleAlertaProjection> getDetalles();

}
