package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 18/08/2021.
 */
@Projection(types = {Alumno.class})
public interface AlumnoEntregarLibrosProjection {

    Integer getId();
    Integer getFotoId();
    String getCodigo();
    String getCodigoUDG();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    Integer getGrupoId();
    String getGrupo();
    String getLibros();
    Integer getInscripcionEstatusId();
    String getInscripcion();
    Integer getInscripcionId();
    Integer getInscripcionSinGrupoId();
    Integer getOrdenVentaId();
    String getOrdenVentaCodigo();

}
