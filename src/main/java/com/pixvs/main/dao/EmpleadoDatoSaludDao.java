package com.pixvs.main.dao;

import com.pixvs.main.models.EmpleadoDatoSalud;
import com.pixvs.main.models.projections.EmpleadoDatoSalud.EmpleadoDatoSaludEditarProjection;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Rene Carrillo on 22/03/2022.
 */
public interface EmpleadoDatoSaludDao extends CrudRepository<EmpleadoDatoSalud, String> {
    //EmpleadoDatoSaludEditarProjection findProjectedEditarEmpleadoId(Integer empleadoId);

    EmpleadoDatoSaludEditarProjection findProjectedEditarAllByEmpleadoId(Integer empleadoId);
}
