package com.pixvs.main.models.projections.CXPSolicitudPagoRHIncapacidad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.CXPSolicitudPagoRH;
import com.pixvs.main.models.CXPSolicitudPagoRHIncapacidad;
import com.pixvs.main.models.projections.CXPSolicitudPagoRHIncapacidadDetalle.CXPSolicitudPagoRHIncapacidadDetalleEditarProjection;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Projection(types = {CXPSolicitudPagoRHIncapacidad.class})
public interface CXPSolicitudPagoRHIncapacidadEditarProjection {

    Integer getId();

    Integer getCpxSolicitudPagoRhId();

    String getFolioIncapacidad();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaFin();

    List<CXPSolicitudPagoRHIncapacidadDetalleEditarProjection> getDetalles();
}
