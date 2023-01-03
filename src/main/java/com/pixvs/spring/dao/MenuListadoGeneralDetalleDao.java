package com.pixvs.spring.dao;

import com.pixvs.spring.models.MenuListadoGeneralDetalle;
import com.pixvs.spring.models.projections.MenuListadoGeneralDetalle.MenuListadoGeneralDetalleEditarProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuListadoGeneralDetalleDao extends CrudRepository<MenuListadoGeneralDetalle, String> {

    List<MenuListadoGeneralDetalleEditarProjection> findAllProjectedEditarByListadoGeneralMenuId(Integer listadoGeneralMenuId);

}
