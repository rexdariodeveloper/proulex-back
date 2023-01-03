package com.pixvs.spring.dao;

import com.pixvs.spring.models.SATRegimenFiscal;
import com.pixvs.spring.models.projections.SATRegimenFiscal.SATRegimenFiscalComboProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SATRegimenFiscalDao extends CrudRepository<SATRegimenFiscal, String> {

    SATRegimenFiscal findById(Integer id);

    SATRegimenFiscalComboProjection findComboProjectedByCodigoAndActivoTrue(String codigo);

    List<SATRegimenFiscalComboProjection> findAllComboProjectedByActivoTrue();
}
