package com.pixvs.spring.dao;

import com.pixvs.spring.models.Municipio;
import com.pixvs.spring.models.projections.Municipio.MunicipioComboProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MunicipioDao extends CrudRepository<Municipio, String> {
    Municipio findById(Integer municipioId);
    List<MunicipioComboProjection> findProjectedComboAllByEstadoId(Integer estadoId);
}
