package com.pixvs.main.models.projections.Articulo;

import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.projections.ArticuloComponente.ArticuloComponenteArbolProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloArbolComponentesProjection {

    Integer getId();
    String getCodigoArticulo();
    String getNombreArticulo();
    UnidadMedidaComboProjection getUnidadMedidaInventario();

    List<ArticuloComponenteArbolProjection> getComponentes();

}
