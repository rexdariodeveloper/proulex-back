package com.pixvs.main.models.projections.Alumno;

import com.pixvs.main.models.Alumno;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by Angel Daniel Hernández Silva on 19/08/2021.
 */
@Projection(types = {Alumno.class})
public interface AlumnoInscripcionesPendientesJOBSSEMSProjection {

    Integer getId();
    String getCodigo();
    String getCodigoAlumnoUDG();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    String getPreparatoria();
    String getBachilleratoTecnologico();
    Integer getGrupoId();
    String getGrupo();
    Boolean getEsCandidato();
    Integer getIdiomaId();

}
