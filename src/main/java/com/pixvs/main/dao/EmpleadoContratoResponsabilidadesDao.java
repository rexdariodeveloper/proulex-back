package com.pixvs.main.dao;


import com.pixvs.main.models.EmpleadoContratoResponsabilidades;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmpleadoContratoResponsabilidadesDao extends CrudRepository<EmpleadoContratoResponsabilidades, String> {
    List<EmpleadoContratoResponsabilidades> findByEmpleadoContratoId(Integer id);
}
