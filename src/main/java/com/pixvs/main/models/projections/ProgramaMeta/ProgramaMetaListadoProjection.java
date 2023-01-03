package com.pixvs.main.models.projections.ProgramaMeta;

import com.pixvs.main.models.ProgramaMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 08/05/2021.
 */
@Projection(types = {ProgramaMeta.class})
public interface ProgramaMetaListadoProjection {

    Integer getId();
    String getCodigo();
    String getNombre();
    String getCiclo();
    Integer getMeta();
    Integer getInscripciones();

}
