package com.pixvs.main.models.projections.Articulo;

import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaListadoProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboSimpleProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 14/09/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloListadoPrecioMaterialProjection {

    Integer getId();
    String getCodigoArticulo();
    String getNombreArticulo();
    UnidadMedidaListadoProjection getUnidadMedidaInventario();
    BigDecimal getIva();
    Boolean getIvaExento();
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();
    BigDecimal getPrecio();
    Boolean getEsLibro();
    Boolean getEsCertificacion();

}
