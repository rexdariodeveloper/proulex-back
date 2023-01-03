package com.pixvs.spring.models.projections.MenuListadoGeneral;

import com.pixvs.spring.models.MenuListadoGeneral;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by Angel Daniel Hernández Silva on 11/06/2020.
 */
@Projection(types = {MenuListadoGeneral.class})
public interface MenuListadoGeneralComboProjection {


    Integer getId();

    Integer getNodoPadreId();

    String getTitulo();

    String getTituloEN();

    String getIcono();

    Integer getOrden();

    String getTipoNodoId();

}