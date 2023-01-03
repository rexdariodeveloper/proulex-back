package com.pixvs.spring.models.projections.MenuListadoGeneralDetalle;

import com.pixvs.spring.models.MenuListadoGeneralDetalle;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {MenuListadoGeneralDetalle.class})
public interface MenuListadoGeneralDetalleEditarProjection {

    Integer getId();
    String getJsonConfig();
    String getJsonListado();
    String getCampoModelo();

}
