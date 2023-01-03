package com.pixvs.main.models.projections.ClienteRemisionDetalle;

import com.pixvs.main.models.ClienteRemisionDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 14/06/2021.
 */
@Projection(types = {ClienteRemisionDetalle.class})
public interface ClienteRemisionDetalleEditarProjection {

    Integer getId();
    ArticuloComboProjection getArticulo();
    BigDecimal getCantidad();

}
