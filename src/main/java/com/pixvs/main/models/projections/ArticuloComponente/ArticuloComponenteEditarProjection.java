package com.pixvs.main.models.projections.ArticuloComponente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ArticuloComponente;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 21/12/2020.
 */
@Projection(types = {ArticuloComponente.class})
public interface ArticuloComponenteEditarProjection {

    Integer getId();
    ArticuloComboProjection getArticulo();
    ArticuloComboProjection getComponente();
    BigDecimal getCantidad();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSS", timezone = "America/Mexico_City")
    Date getFechaModificacion();

}
