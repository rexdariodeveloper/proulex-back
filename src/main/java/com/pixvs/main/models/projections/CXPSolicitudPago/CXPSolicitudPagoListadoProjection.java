package com.pixvs.main.models.projections.CXPSolicitudPago;

import com.pixvs.main.models.CXPSolicitudPago;
import com.pixvs.main.models.projections.CXPSolicitudPagoDetalle.CXPSolicitudPagoDetalleListadoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 18/09/2020.
 */
@Projection(types = {CXPSolicitudPago.class})
public interface CXPSolicitudPagoListadoProjection {

    Integer getId();
    String getCodigoSolicitud();
    Date getFechaCreacion();
    @Value("0")
    BigDecimal getMontoProgramado();
    UsuarioComboProjection getCreadoPor();
    @Value("0")
    Integer getTotalFacturas();
    ControlMaestroMultipleComboProjection getEstatus();

    List<CXPSolicitudPagoDetalleListadoProjection> getDetalles();

}
