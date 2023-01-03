package com.pixvs.main.dao;

import com.pixvs.main.models.Puesto;
import com.pixvs.main.models.projections.Puesto.PuestoComboContratosProjection;
import com.pixvs.main.models.projections.Puesto.PuestoComboProjection;
import com.pixvs.main.models.projections.Puesto.PuestoEditarProjection;
import com.pixvs.main.models.projections.Puesto.PuestoListadoProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PuestoDao extends CrudRepository<Puesto, String> {

    List<PuestoListadoProjection> findProjectedListadoAllBy();//EstadoPuestoId(Integer estadoPuestoId);

    List<PuestoComboContratosProjection> findProjectedListadoComboContratoAllBy();//EstadoPuestoId(Integer estadoPuestoId);

    List<PuestoComboProjection> findProjectedComboAllByEstadoPuestoId(Integer estadoPuestoId);

    PuestoEditarProjection findProjectedEditarAllById(Integer idPuesto);
}
