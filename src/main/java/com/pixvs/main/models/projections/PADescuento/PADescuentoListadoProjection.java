package com.pixvs.main.models.projections.PADescuento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.PADescuento;
import com.pixvs.main.models.projections.PADescuentoDetalle.PADescuentoDetalleEditarProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PADescuento.class})
public interface PADescuentoListadoProjection {


    Integer getId();

    String getCodigo();

    String getConcepto();

    @Value("#{target.fechaInicio == null ? target.porcentajeDescuento : (target.porcentajeDescuento + '%') }")
    String getPorcentajeDescuento();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    Boolean getActivo();
}