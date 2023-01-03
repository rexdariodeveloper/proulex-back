package com.pixvs.spring.models.projections.ArchivoEstructuraCarpeta;

import com.pixvs.spring.models.ArchivoEstructuraCarpeta;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ArchivoEstructuraCarpeta.class})
public interface ArchivoEstructuraCarpetaProjection {

    String getNombreCarpeta();

    ArchivoEstructuraCarpetaProjection getArchivoEstructuraCarpeta();

}
