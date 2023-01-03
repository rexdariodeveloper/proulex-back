package com.pixvs.spring.dao;


import com.pixvs.spring.models.Pais;
import com.pixvs.spring.models.projections.Pais.PaisComboProjection;
import com.pixvs.spring.models.projections.Pais.PaisNacionalidadProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaisDao extends CrudRepository<Pais, String> {

    List<PaisComboProjection> findProjectedComboAllBy();

    @Query("SELECT p.id as id, p.nombre as nombre FROM Pais p INNER JOIN Estado e ON e.paisId = p.id GROUP BY p.id, p.nombre")
    List<PaisComboProjection> findProjectedComboAllByTieneEstados();

    Pais findById(Integer id);

    PaisComboProjection findProjectedComboById(Integer id);

    List<PaisNacionalidadProjection> findProjectedComboNacionalidadAllBy();
}
