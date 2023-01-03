package com.pixvs.main.models.projections.Articulo;

import com.pixvs.main.models.Articulo;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 28/10/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloUltimasComprasProjection {

    Integer getId();
    Date getFecha();
    String getCodigoOC();
    BigDecimal getPrecio();
    Integer getProveedorId();

}
