package com.pixvs.main.models.projections.ProgramaGrupo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupo;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;


/**
 * Created by David Arroyo Sánchez on 04/07/2022.
 */
@Projection(types = {ProgramaGrupo.class})
public interface ProgramaGrupoProyeccionCLEProjection {
    Integer getId();
    String getCodigoGrupo();
    String getCurso();
    String getModalidad();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaFin();
    String getNivel();
    String getHorario();
    Integer getCupo();
    Integer getTotalInscritos();
    String getProfesor();

    Integer getDuracionSemanas();

    Integer getGrupoProfesorId();
    Integer getGrupoEstudiantesId();
    String getEstudianteResourceId();
    String getEstudianteResourceFechaFin();
    String getProfesorResourceId();
    String getProfesorResourceFechaFin();
    String getProfesorCorreoElectronico();

    Integer getProfesorId();
    Integer getProfesorCleId();
    String getProfesorCodigo();
    String getProfesorNombre();
    String getProfesorApellidos();
    Date getProfesorFechaNacimiento();

    Integer getProgramaGrupoEstatusId();
    Integer getProgramGrupoProfesorCleId();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaCreacion();

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaUltimaActualizacion();
}