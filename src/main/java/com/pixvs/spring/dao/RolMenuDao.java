package com.pixvs.spring.dao;

import com.pixvs.spring.models.RolMenu;
import com.pixvs.spring.models.projections.RolMenu.RolMenuEditarProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RolMenuDao extends CrudRepository<RolMenu, String> {

    RolMenuEditarProjection findAllProjectedByRolIdAndAndNodoId(Integer idRol, Integer idNodo);

    void deleteAllByRolId(Integer idRol);
}
