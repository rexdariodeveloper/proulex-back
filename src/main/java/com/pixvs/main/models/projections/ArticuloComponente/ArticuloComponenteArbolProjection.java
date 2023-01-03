package com.pixvs.main.models.projections.ArticuloComponente;

import com.pixvs.main.models.ArticuloComponente;
import com.pixvs.main.models.projections.Articulo.ArticuloArbolComponentesProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 22/12/2020.
 */
@Projection(types = {ArticuloComponente.class})
public interface ArticuloComponenteArbolProjection {

    Integer getId();
    BigDecimal getCantidad();
    ArticuloArbolComponentesProjection getComponente();

}
