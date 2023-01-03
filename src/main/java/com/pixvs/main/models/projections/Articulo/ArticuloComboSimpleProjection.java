package com.pixvs.main.models.projections.Articulo;

import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaListadoProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloComboSimpleProjection {

    Integer getId();

    String getCodigoArticulo();

    String getNombreArticulo();

    Integer getIdiomaId();
}