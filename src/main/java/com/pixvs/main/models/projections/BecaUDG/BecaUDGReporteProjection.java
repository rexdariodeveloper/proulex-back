package com.pixvs.main.models.projections.BecaUDG;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.BecaUDG;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
@Projection(types = {BecaUDG.class})
public interface BecaUDGReporteProjection {

    Integer getId();

    String getCodigoPlic();

    String getCodigoBecaUdg();

    String getNotaVenta();

    String getDescuentoUDG();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaAlta();

    String getEstatusBeca();

    String getDescuentoCliente();

    String getTotalCliente();

    String getCliente();
}