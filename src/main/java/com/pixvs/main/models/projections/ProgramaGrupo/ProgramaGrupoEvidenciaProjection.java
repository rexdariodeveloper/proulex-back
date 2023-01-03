package com.pixvs.main.models.projections.ProgramaGrupo;

import com.pixvs.main.models.ProgramaGrupoEvidencia;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ProgramaGrupoEvidencia.class})
public interface ProgramaGrupoEvidenciaProjection {

    Integer getId();

    int getGrupoId();

    int getArchivoId();

    ArchivoProjection getArchivo();

    String getNombre();

    boolean getVigente();
}
