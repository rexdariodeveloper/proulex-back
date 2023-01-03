package com.pixvs.main.models.projections.Alumno;

public interface AlumnoInscripcionPendientePCPProjection {

    Integer getId();
    String getCodigo();
    String getCodigoAlumnoUDG();
    String getNombre();
    String getPrimerApellido();
    String getSegundoApellido();
    String getDependencia();
    String getCurso();
    Integer getGrupoId();
    String getGrupo();
    Boolean getEsCandidato();
    Integer getIdiomaId();
    String getFolio();

}
