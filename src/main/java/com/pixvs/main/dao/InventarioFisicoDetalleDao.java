package com.pixvs.main.dao;

import com.pixvs.main.models.InventarioFisicoDetalle;
import com.pixvs.main.models.projections.InventarioFisicoDetalle.InventarioFisicoDetalleListadoProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventarioFisicoDetalleDao extends CrudRepository<InventarioFisicoDetalle, Integer> {

    InventarioFisicoDetalle save(InventarioFisicoDetalle detalle);

    InventarioFisicoDetalle findInventarioFisicoDetalleById(Integer detalleId);

    List<InventarioFisicoDetalle> findAllByInventarioFisicoId(Integer inventarioFisicoId);

    List<InventarioFisicoDetalleListadoProjection> findAllProjectedByInventarioFisicoId(Integer inventarioFisicoId);
}