package com.pixvs.main.models.projections.EmpleadoCurso;

import com.pixvs.main.models.EmpleadoContacto;
import com.pixvs.main.models.EmpleadoCurso;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpleadoCurso.class})
public interface EmpleadoCursoEditarProjection {


    Integer getId();

    Integer getEmpleadoId();

    ControlMaestroMultipleComboProjection getIdioma();

    ProgramaComboProjection getPrograma();

    String getComentarios();

    Boolean getActivo();
}