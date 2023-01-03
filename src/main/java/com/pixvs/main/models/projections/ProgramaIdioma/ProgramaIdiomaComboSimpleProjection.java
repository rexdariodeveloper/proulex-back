package com.pixvs.main.models.projections.ProgramaIdioma;

import com.pixvs.main.models.ProgramaIdioma;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 15/08/2021.
 */
@Projection(types = {ProgramaIdioma.class})
public interface ProgramaIdiomaComboSimpleProjection {

    Integer getId();
    @Value("#{target.programa.codigo + ' ' +target.idioma.valor }")
    String getNombre();

}
