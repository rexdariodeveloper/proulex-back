package com.pixvs.main.models.projections.PAProfesorCategoria;

import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.PAProfesorCategoria;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {PAProfesorCategoria.class})
public interface PAProfesorComboProjection {

    Integer getId();
    String getCategoria();
}
