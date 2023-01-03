package com.pixvs.main.models.projections.InscripcionSinGrupo;

import com.pixvs.main.models.InscripcionSinGrupo;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 11/08/2021.
 */
@Projection(types = {InscripcionSinGrupo.class})
public interface InscripcionSinGrupoRelacionarProjection {

    Integer getId();
    Integer getArticuloId();

}
