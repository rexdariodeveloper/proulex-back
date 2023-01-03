package com.pixvs.spring.models.projections.MenuListadoGeneral;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.spring.models.MenuListadoGeneral;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 11/06/2020.
 */
@Projection(types = {MenuListadoGeneral.class})
public interface MenuListadoGeneralEditarProjection {


    Integer getId();

    Integer getNodoPadreId();

    String getTitulo();

    String getTituloEN();

    Boolean getActivo();

    String getIcono();

    Integer getOrden();

    String getTipoNodoId();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}