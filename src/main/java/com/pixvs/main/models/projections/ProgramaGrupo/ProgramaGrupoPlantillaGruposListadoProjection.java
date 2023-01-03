package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface ProgramaGrupoPlantillaGruposListadoProjection {
    Integer getId();

    String getCodigoGrupo();

    String getNombreGrupo();

    String getPlantel();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();

    String getNivel();

    String getHorario();

    Integer getCupo();

    Integer getInscrito();

    String getNombreProfesor();

    Integer getCursoId();

    Integer getPlantelId();

    Integer getSucursalId();

    Integer getModalidadId();
}
