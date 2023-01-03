package com.pixvs.main.models.projections.ProgramaIdioma;

import com.pixvs.main.models.ProgramaIdioma;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Rene Carrillo on 12/12/2022.
 */
@Projection(types = {ProgramaIdioma.class})
public interface ProgramaIdiomaComboDescuentoCertificacionProjection {
    Integer getId();

    String getIdioma();

    Integer getNumeroNiveles();
}
