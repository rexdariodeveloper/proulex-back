package com.pixvs.main.dao;

import com.pixvs.main.models.TransferenciaDetalle;
import com.pixvs.main.models.projections.TransferenciaDetalle.TransferenciaDetalleListadoProjection;
import com.pixvs.main.models.projections.TransferenciaDetalle.TransferenciaMovimientoListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferenciaDetalleDao extends CrudRepository<TransferenciaDetalle, Integer> {

    TransferenciaDetalle save(TransferenciaDetalle detalle);

    TransferenciaDetalle findTransferenciaDetalleById(Integer detalleId);

    List<TransferenciaDetalle> findAllTransferenciaDetalleByTransferenciaId(Integer transferenciaId);

    List<TransferenciaDetalleListadoProjection> findAllByTransferenciaId(Integer transferenciaId);

    List<TransferenciaDetalleListadoProjection> findAllByTransferenciaIdAndEstatusIdIn(Integer transferenciaId, Integer[] estatus);

    @Query(value = "SELECT * FROM fn_getTransferenciaMovimientos(:id) ORDER BY Id", nativeQuery = true)
    List<TransferenciaMovimientoListadoProjection> findMovimientosById(@Param("id") Integer id);
}