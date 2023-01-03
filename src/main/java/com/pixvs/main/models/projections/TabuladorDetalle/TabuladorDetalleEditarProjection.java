package com.pixvs.main.models.projections.TabuladorDetalle;

import com.pixvs.main.models.TabuladorDetalle;
import com.pixvs.main.models.TemporadaDetalle;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorCategoriaListadoProjection;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {TabuladorDetalle.class})
public interface TabuladorDetalleEditarProjection {

    Integer getId();
    Integer getTabuladorId();
    PAProfesorComboProjection getProfesorCategoria();
    BigDecimal getSueldo();
    Boolean getActivo();
}