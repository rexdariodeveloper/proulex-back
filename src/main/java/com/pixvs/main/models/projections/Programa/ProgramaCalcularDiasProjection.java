package com.pixvs.main.models.projections.Programa;

import com.pixvs.main.models.Programa;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 04/11/2020.
 */
@Projection(types = {Programa.class})
public interface ProgramaCalcularDiasProjection {

    Integer getId();
    String getCodigo();
    String getNombre();

}
