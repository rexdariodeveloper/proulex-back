package com.pixvs.main.models.projections.ListadoPrecio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ListadoPrecio;
import com.pixvs.main.models.Programa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ListadoPrecio.class})
public interface ListadoPrecioListadoProjection {


    Integer getId();

    String getCodigo();

    String getNombre();

    String getFecha();

    String getAsignado();

    Boolean getActivo();

}