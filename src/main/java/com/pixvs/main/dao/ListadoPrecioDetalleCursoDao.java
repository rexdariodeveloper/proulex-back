package com.pixvs.main.dao;

import com.pixvs.main.models.ListadoPrecioDetalleCurso;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Ángel Daniel Hernández Silva on 24/09/2021.
 */
public interface ListadoPrecioDetalleCursoDao extends CrudRepository<ListadoPrecioDetalleCurso, String> {

    // Modelo
    ListadoPrecioDetalleCurso findByListadoPrecioDetalleIdAndArticuloId(Integer listadoPrecioDetalleId, Integer articuloId);
    List<ListadoPrecioDetalleCurso> findAllByListadoPrecioDetalleIdAndArticuloIdNotIn(Integer listadoPrecioDetalleId, List<Integer> articuloIds);

    void deleteAllByListadoPrecioDetalleIdIn(List<Integer> listadoPrecioDetalleIds);

}
