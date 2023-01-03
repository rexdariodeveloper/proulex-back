package com.pixvs.spring.dao;

import com.pixvs.spring.models.SATMes;
import com.pixvs.spring.models.projections.SATMes.SATMesComboProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SATMesDao extends CrudRepository<SATMes, String> {

    SATMes findById(Integer id);

    List<SATMesComboProjection> findAllComboProjectedByActivoTrueOrderByCodigo();
}