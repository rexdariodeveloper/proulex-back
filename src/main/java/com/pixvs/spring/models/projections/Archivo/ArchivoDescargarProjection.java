package com.pixvs.spring.models.projections.Archivo;

import com.pixvs.spring.models.Archivo;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Archivo.class})
public interface ArchivoDescargarProjection {

    Integer getId();

    String getNombreFisico();

    String getNombreOriginal();

    String getRutaFisica();

    boolean getActivo();

    boolean getPublico();
}
