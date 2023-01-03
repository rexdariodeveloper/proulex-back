package com.pixvs.main.dao;

import com.pixvs.main.models.EmpleadoBeneficiario;
import com.pixvs.main.models.projections.EmpleadoBeneficiario.EmpleadoBeneficiarioContratoProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by David Rene Carrillo on 11/08/2022.
 */

public interface EmpleadoBeneficiarioDao extends CrudRepository<EmpleadoBeneficiario, String> {
    List<EmpleadoBeneficiarioContratoProjection> findAllByEmpleadoIdOrderByPorcentaje(Integer empleadoId);

    List<EmpleadoBeneficiarioContratoProjection> findProjectedBeneficiarioContratoByEmpleadoId(Integer idEmpleado);
}
