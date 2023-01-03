package com.pixvs.main.dao;

import com.pixvs.main.models.LocalidadArticuloAcumulado;
import com.pixvs.main.models.projections.LocalidadArticuloAcumulado.LocalidadArticuloAcumuladoComboProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocalidadArticuloAcumuladoDao extends CrudRepository<LocalidadArticuloAcumulado, Integer> {

    LocalidadArticuloAcumulado findByArticuloIdAndLocalidadId(Integer articuloId, Integer localidadId);

    List<LocalidadArticuloAcumuladoComboProjection> findLocalidadArticuloAcumuladoByIdIsNotNull();

    List<LocalidadArticuloAcumuladoComboProjection> findAllArticulosByLocalidadId(Integer localidadId);
}