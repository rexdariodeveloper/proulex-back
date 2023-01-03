package com.pixvs.main.models.projections.Articulo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.Articulo;
import com.pixvs.main.models.ArticuloTipo;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.UnidadMedida.UnidadMedidaComboProjection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by Angel Daniel Hernández Silva on 06/07/2020.
 */
@Projection(types = {Articulo.class})
public interface ArticuloListadoProjection {


    Integer getId();

    String getCodigoArticulo();

    String getNombreArticulo();

    String getCodigoAlterno();

    String getNombreAlterno();

    String getDescripcionCorta();

    UnidadMedidaComboProjection getUnidadMedidaInventario();

    ArticuloFamiliaComboProjection getFamilia();

    ArticuloTipo getTipoArticulo();

    Integer getImagenId();

    Boolean getActivo();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaCreacion();

}