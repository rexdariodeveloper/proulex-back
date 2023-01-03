package com.pixvs.main.models.projections.Articulo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 15/06/2021.
 */
@Projection(types = {Articulo.class})
public interface ArticuloComboListaPreciosProjection {

    Integer getId();
    String getCodigoArticulo();
    String getNombreArticulo();
    UnidadMedidaComboProjection getUnidadMedidaInventario();
    BigDecimal getIva();
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();
    BigDecimal getPrecioVenta();

}
