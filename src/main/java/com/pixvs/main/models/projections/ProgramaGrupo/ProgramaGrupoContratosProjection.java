package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoContratosProjection {

    String getNombreProfesor();
    String getCodigoProfesor();
    String getSueldoProfesor();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();
    String getIdioma();
    String getPlantel();
    String getModalidad();
    Integer getTipoGrupo();
    Integer getGrupos();
    Integer getProgramaId();
    Integer getEmpleadoId();
    Integer getSucursalId();
    Integer getModalidadId();
    Integer getCicloId();
    Integer getIdiomaCmm();
    Integer getPlantelId();
}