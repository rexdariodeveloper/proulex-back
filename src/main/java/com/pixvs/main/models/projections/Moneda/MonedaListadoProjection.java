package com.pixvs.main.models.projections.Moneda;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Moneda;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 26/06/2020.
 */
@Projection(types = {Moneda.class})
public interface MonedaListadoProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    String getSimbolo();
    Boolean getPredeterminada();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
