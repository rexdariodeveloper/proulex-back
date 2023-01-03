package com.pixvs.main.dao;

import com.pixvs.main.models.PedidoRecibo;
import com.pixvs.main.models.projections.PedidoRecibo.PedidoReciboListadoProjection;
import com.pixvs.main.models.projections.PedidoReciboDetalle.PedidoMovimientoListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoReciboDao extends CrudRepository<PedidoRecibo, Integer> {

    PedidoRecibo save(PedidoRecibo recibo);

    List<PedidoReciboListadoProjection> findAllProjectedByPedidoId(Integer pedidoId);

    @Query(value = "SELECT * FROM fn_getPedidoMovimientos(:id) ORDER BY Id", nativeQuery = true)
    List<PedidoMovimientoListadoProjection> findMoviminetosById(@Param("id") Integer id);
}