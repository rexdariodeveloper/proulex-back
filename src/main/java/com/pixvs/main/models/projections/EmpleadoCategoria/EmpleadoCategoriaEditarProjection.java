package com.pixvs.main.models.projections.EmpleadoCategoria;

import com.pixvs.main.models.EmpleadoCategoria;
import com.pixvs.main.models.EmpleadoCurso;
import com.pixvs.main.models.projections.PAProfesorCategoria.PAProfesorComboProjection;
import com.pixvs.main.models.projections.Programa.ProgramaComboProjection;
import com.pixvs.spring.models.projections.ControlMaestroMultiple.ControlMaestroMultipleComboProjection;
import org.springframework.data.rest.core.config.Projection;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
@Projection(types = {EmpleadoCategoria.class})
public interface EmpleadoCategoriaEditarProjection {


    Integer getId();

    Integer getEmpleadoId();

    ControlMaestroMultipleComboProjection getIdioma();

    PAProfesorComboProjection getCategoria();

    Boolean getActivo();
}