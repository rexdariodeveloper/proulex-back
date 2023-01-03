package com.pixvs.main.models.projections.OrdenVenta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.OrdenVenta;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 01/10/2021.
 */
@Projection(types = {OrdenVenta.class})
public interface OrdenVentaSimpleProjection {

    Integer getId();
    String getCodigo();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaOV();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaRequerida();

}
