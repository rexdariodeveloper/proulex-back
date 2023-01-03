package com.pixvs.main.dao;

import com.pixvs.main.models.RequisicionPartida;
import com.pixvs.main.models.projections.RequisicionPartida.RequisicionPartidaConvertirListadoProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 28/10/2020.
 */
public interface RequisicionPartidaDao extends CrudRepository<RequisicionPartida, String> {

    RequisicionPartida findById(Integer id);

    List<RequisicionPartidaConvertirListadoProjection> findProjectedConvertirListadoAllByRequisicionIdAndEstadoPartidaIdIn(Integer requisicionId, List<Integer> estadoPartidaIds);

}
