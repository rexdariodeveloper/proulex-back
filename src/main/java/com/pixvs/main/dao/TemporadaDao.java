package com.pixvs.main.dao;

import com.pixvs.main.models.Temporada;
import com.pixvs.main.models.projections.Temporada.TemporadaEditarProjection;
import com.pixvs.main.models.projections.Temporada.TemporadaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TemporadaDao extends CrudRepository<Temporada, Integer> {

    Temporada save(Temporada transferencia);

    TemporadaEditarProjection findTemporadaById(Integer id);

    List<TemporadaListadoProjection> findAllTemporadaBy();

    @Modifying
    @Query(value = "UPDATE Temporadas SET TEM_Activo = :activo WHERE TEM_TemporadaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);
}