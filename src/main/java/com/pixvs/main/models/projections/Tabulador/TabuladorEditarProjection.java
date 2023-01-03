package com.pixvs.main.models.projections.Tabulador;

import com.pixvs.main.models.Tabulador;
import com.pixvs.main.models.TabuladorDetalle;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import com.pixvs.main.models.projections.TabuladorCurso.TabuladorCursoEditarProjection;
import com.pixvs.main.models.projections.TabuladorDetalle.TabuladorDetalleEditarProjection;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;
import java.util.List;

@Projection(types = {Tabulador.class})
public interface TabuladorEditarProjection {

    Integer getId();
    String getCodigo();
    String getDescripcion();
    Boolean getPagoDiasFestivos();
    Boolean getActivo();
    List<TabuladorDetalleEditarProjection> getDetalles();
    List<TabuladorCursoEditarProjection> getCursos();
}