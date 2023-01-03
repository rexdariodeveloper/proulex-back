package com.pixvs.main.models.projections.Workshop;

import com.pixvs.main.models.ProgramaIdioma;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ProgramaIdioma.class})
public interface WorkshopListadoProjection {
    Integer getId();
    String getTipo();
    String getNombre();
    Integer getHoras();
    Integer getModalidadesSize();
    Integer getSucursalesSize();
    Boolean getActivo();
}