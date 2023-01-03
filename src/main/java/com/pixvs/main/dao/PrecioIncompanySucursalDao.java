package com.pixvs.main.dao;

import com.pixvs.main.models.PrecioIncompany;
import com.pixvs.main.models.PrecioIncompanySucursal;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyEditarProjection;
import com.pixvs.main.models.projections.PrecioIncompany.PrecioIncompanyListadoProjection;
import com.pixvs.main.models.projections.PrecioIncompanySucursal.PrecioIncompanySucursalEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrecioIncompanySucursalDao extends CrudRepository<PrecioIncompanySucursal, String> {

    void deleteByPrecioIncompanyId(Integer id);

    List<PrecioIncompanySucursalEditarProjection> findAllBySucursalId(Integer sucursal);
}
