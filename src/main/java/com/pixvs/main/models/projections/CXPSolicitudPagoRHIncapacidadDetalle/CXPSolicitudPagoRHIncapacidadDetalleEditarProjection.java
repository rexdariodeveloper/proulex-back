package com.pixvs.main.models.projections.CXPSolicitudPagoRHIncapacidadDetalle;

import com.pixvs.main.models.CXPSolicitudPagoRHIncapacidadDetalle;
import com.pixvs.main.models.CXPSolicitudPagoRHRetiroCajaAhorro;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXPSolicitudPagoRHIncapacidadDetalle.class})
public interface CXPSolicitudPagoRHIncapacidadDetalleEditarProjection {

    Integer getId();
    Integer getIncapacidadId();
    ControlMaestroMultipleComboProjection getTipo();
    ControlMaestroMultipleComboProjection getTipoMovimiento();
    BigDecimal getSalarioDiario();
    Integer getPorcentaje();
    Integer getDias();
}
