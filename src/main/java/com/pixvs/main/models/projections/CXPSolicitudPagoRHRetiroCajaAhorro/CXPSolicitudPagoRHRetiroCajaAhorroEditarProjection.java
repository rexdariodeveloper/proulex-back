package com.pixvs.main.models.projections.CXPSolicitudPagoRHRetiroCajaAhorro;

import com.pixvs.main.models.CXPSolicitudPagoRH;
import com.pixvs.main.models.CXPSolicitudPagoRHRetiroCajaAhorro;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {CXPSolicitudPagoRHRetiroCajaAhorro.class})
public interface CXPSolicitudPagoRHRetiroCajaAhorroEditarProjection {

    Integer getId();
    Integer getTipoRetiroId();
    ControlMaestroMultipleComboProjection getTipoRetiro();
    BigDecimal getAhorroAcumulado();
    BigDecimal getCantidadRetirar();
    String getMotivoRetiro();
}
