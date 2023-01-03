package com.pixvs.spring.dao;

import com.pixvs.spring.models.SATPeriodicidad;
import com.pixvs.spring.models.projections.SATPeriodicidad.SATPeriodicidadComboProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SATPeriodicidadDao extends CrudRepository<SATPeriodicidad, String> {

    SATPeriodicidad findById(Integer id);

    List<SATPeriodicidadComboProjection> findAllComboProjectedByActivoTrueOrderByCodigo();
}