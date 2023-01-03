package com.pixvs.main.dao;

import com.pixvs.main.models.SucursalFormasPago;
import com.pixvs.main.models.projections.SucursalFormasPago.SucursalFormasPagoEditarProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Benjamin Osorio
 */
public interface SucursalFormasPagoDao extends CrudRepository<SucursalFormasPago, Integer> {

    SucursalFormasPago save(SucursalFormasPago formaPago);

    SucursalFormasPago findInSucursalFormasPagoById(Integer formaPagoId);

    List<SucursalFormasPago> findAllBySucursalId(Integer sucursalId);

    List<SucursalFormasPagoEditarProjection> findAllProjectedBySucursalId(Integer sucursalId);

    // List<SucursalFormasPagoComboProjection> findAllProjectedBySucursalId(Integer sucursalId);
}
