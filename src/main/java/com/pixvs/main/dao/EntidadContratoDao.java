package com.pixvs.main.dao;

import com.pixvs.main.models.Empleado;
import com.pixvs.main.models.EntidadContrato;
import com.pixvs.main.models.projections.EntidadContrato.EntidadContratoWordProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EntidadContratoDao  extends CrudRepository<EntidadContrato, String> {

    EntidadContratoWordProjection findProjectedByTipoContratoId(Integer tipoContratoId);

    List<EntidadContratoWordProjection> findAllProjectedByEntidadId(Integer entidadId);


}
