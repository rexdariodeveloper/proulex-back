package com.pixvs.main.models.projections.ProgramaGrupoProfesor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pixvs.main.models.ProgramaGrupoProfesor;
import com.pixvs.main.models.projections.Empleado.EmpleadoComboProjection;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;

/**
 * Created by Angel Daniel Hernández Silva on 01/12/2021.
 */
@Projection(types = {ProgramaGrupoProfesor.class})
public interface ProgramaGrupoProfesorListadoGrupoProjection {

    Integer getId();
    Integer getGrupoId();
    EmpleadoComboProjection getEmpleado();
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Mexico_City")
    Date getFechaInicio();
    String getMotivo();
    Boolean getActivo();

}
