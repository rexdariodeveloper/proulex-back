package com.pixvs.main.models.projections.TabuladorDetalle;

import com.pixvs.main.models.TabuladorDetalle;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {TabuladorDetalle.class})
public interface TabuladorDetalleComboProjection {

    Integer getId();
    Integer getTabuladorId();
    PAProfesorComboProjection getProfesorCategoria();
    BigDecimal getSueldo();
}