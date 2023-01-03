package com.pixvs.spring.dao;

import com.pixvs.spring.models.Estado;
import com.pixvs.spring.models.projections.Estado.EstadoComboProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EstadoDao extends CrudRepository<Estado, String> {
    Estado findById(Integer estadoId);
    List<EstadoComboProjection> findProjectedComboAllByPaisId(Integer paisId);
    List<EstadoComboProjection> findProjectedComboAllBy();

    EstadoComboProjection findProjectedComboById(Integer id);
}
