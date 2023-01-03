package com.pixvs.main.dao;

import com.pixvs.main.models.EmpleadoContacto;
import com.pixvs.main.models.Proveedor;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.EmpleadoContacto.EmpleadoContactoEditarProjection;
import com.pixvs.main.models.projections.EmpleadoContacto.EmpleadoContactoListadoProjection;
import com.pixvs.main.models.projections.Proveedor.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface EmpleadoContactoDao extends CrudRepository<EmpleadoContacto, String> {

    List<EmpleadoContactoListadoProjection> findProjectedListadoAllBy();

    List<EmpleadoContactoEditarProjection> findAllByEmpleadoId(Integer id);

    EmpleadoContacto findById(Integer id);

}