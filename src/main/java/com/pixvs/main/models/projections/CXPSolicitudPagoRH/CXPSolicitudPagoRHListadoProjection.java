package com.pixvs.main.models.projections.CXPSolicitudPagoRH;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPSolicitudPago;
import com.pixvs.main.models.CXPSolicitudPagoRH;
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
@Projection(types = {CXPSolicitudPagoRH.class})
public interface CXPSolicitudPagoRHListadoProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();
    String getNombre();
    String getTipoPago();
    BigDecimal getMonto();
    String getUsuarioCreador();
    String getEstatus();
    Integer getSedeId();
}
