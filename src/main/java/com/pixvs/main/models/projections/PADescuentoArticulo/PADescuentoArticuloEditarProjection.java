package com.pixvs.main.models.projections.PADescuentoArticulo;

import com.pixvs.main.models.PADescuentoArticulo;
import com.pixvs.main.models.PADescuentoDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.Sucursal.SucursalComboProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {PADescuentoArticulo.class})
public interface PADescuentoArticuloEditarProjection {


    Integer getId();

    Integer getDescuentoId();

    ArticuloComboProjection getArticulo();

    Boolean getActivo();
}