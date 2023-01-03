package com.pixvs.main.dao;

import com.pixvs.main.models.SucursalImpresoraFamilia;
import com.pixvs.main.models.projections.SucursalImpresoraFamilia.SucursalImpresoraFamiliaEditarProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Benjamain Osorio
 */
public interface SucursalImpresoraFamiliaDao extends CrudRepository<SucursalImpresoraFamilia, Integer> {

    SucursalImpresoraFamilia save(SucursalImpresoraFamilia impresora);

    SucursalImpresoraFamilia findInSucursalImpresoraFamiliaById(Integer impresora);

    List<SucursalImpresoraFamilia> findAllBySucursalId(Integer sucursalId);

    List<SucursalImpresoraFamiliaEditarProjection> findAllProjectedBySucursalId(Integer id);

}
