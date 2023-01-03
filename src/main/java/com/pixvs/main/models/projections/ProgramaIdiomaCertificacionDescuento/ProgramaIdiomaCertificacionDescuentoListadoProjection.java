package com.pixvs.main.models.projections.ProgramaIdiomaCertificacionDescuento;

import com.pixvs.main.models.ProgramaIdiomaCertificacionDescuento;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 29/11/2022.
 */
@Projection(types = {ProgramaIdiomaCertificacionDescuento.class})
public interface ProgramaIdiomaCertificacionDescuentoListadoProjection {
    Integer getId();

    String getCurso();

    String getCertificacion();

    Boolean getActivo();
}
