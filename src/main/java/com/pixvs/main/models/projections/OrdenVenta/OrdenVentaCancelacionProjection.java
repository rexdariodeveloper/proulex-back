package com.pixvs.main.models.projections.OrdenVenta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenVenta;
import com.pixvs.main.models.projections.SucursalCorteCaja.SucursalCorteCajaListadoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

@Projection(types = {OrdenVenta.class})
public interface OrdenVentaCancelacionProjection {

    Integer getId();

    String getCodigo();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaOV();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaRequerida();

    Integer getSucursalId();

    Integer getEstatusId();

    SucursalCorteCajaListadoProjection getSucursalCorteCaja();
}