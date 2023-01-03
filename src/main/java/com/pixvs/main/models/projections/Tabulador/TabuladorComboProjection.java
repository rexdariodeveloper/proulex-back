package com.pixvs.main.models.projections.Tabulador;

import com.pixvs.main.models.Tabulador;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleComboProjection;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(types = {Tabulador.class})
public interface TabuladorComboProjection {

    Integer getId();
    String getCodigo();
    //List<TabuladorDetalleComboProjection> getDetalles();
}