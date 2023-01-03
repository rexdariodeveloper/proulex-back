package com.pixvs.main.models.projections.Programa;

import com.pixvs.main.models.Programa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 07/07/2021.
 */
@Projection(types = {Programa.class})
public interface ProgramaCardProjection {

    Integer getId();
    @Value("#{target.codigo + '-' +target.nombre }")
    String getNombre();
    String getCodigo();
    Integer getImagenId();
    Boolean getJobsSems();

}
