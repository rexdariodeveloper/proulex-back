package com.pixvs.main.models.projections.Articulo;

import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaListadoProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloListadoPrecioProjection {

    Integer getId();

    String getCodigoArticulo();

    String getNombreArticulo();

	UnidadMedidaListadoProjection getUnidadMedidaInventario();

    BigDecimal getIva();
    Boolean getIvaExento();
    BigDecimal getIeps();
    BigDecimal getIepsCuotaFija();

    Integer getImagenId();

    Boolean getActivo();

    @Value("#{(target.tipoArticuloId == 5 && target.programaIdiomaId != null && target.modalidadId != null) ? true : false}")
    Boolean getEsCurso();
}