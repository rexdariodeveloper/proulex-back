package com.pixvs.main.models.projections.ListadoPrecioDetalle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ListadoPrecioDetalle;
import com.pixvs.main.models.Programa;
import com.pixvs.main.models.projections.Articulo.ArticuloComboSimpleProjection;
import com.pixvs.main.models.projections.Articulo.ArticuloListadoPrecioProjection;
import com.pixvs.main.models.projections.ProgramaIdioma.ProgramaIdiomaEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ListadoPrecioDetalle.class})
public interface ListadoPrecioDetalleEditarProjection {


    Integer getId();

    Integer getListadoPrecioId();

    Integer getPadreId();

    BigDecimal getPrecio();

    ArticuloListadoPrecioProjection getArticulo();

    List<ListadoPrecioDetalleEditarProjection> getHijos();
}