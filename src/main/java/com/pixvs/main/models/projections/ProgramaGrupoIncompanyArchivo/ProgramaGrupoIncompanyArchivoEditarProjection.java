package com.pixvs.main.models.projections.ProgramaGrupoIncompanyArchivo;

import com.pixvs.main.models.ProgramaGrupoIncompanyArchivo;
import com.pixvs.main.models.ProgramaGrupoIncompanyMaterial;
import com.pixvs.main.models.projections.Articulo.ArticuloComboProjection;
import com.pixvs.spring.models.projections.Archivo.ArchivoProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by Angel Daniel Hernández Silva on 30/03/2021.
 */
@Projection(types = {ProgramaGrupoIncompanyArchivo.class})
public interface ProgramaGrupoIncompanyArchivoEditarProjection {


    Integer getId();

    Integer getProgramaIncompanyId();

    ArchivoProjection getArchivo();
}