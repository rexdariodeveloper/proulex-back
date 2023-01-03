package com.pixvs.spring.dao;

import com.pixvs.spring.models.ControlMaestro;
import com.pixvs.spring.models.projections.ControlMaestro.ControlMaestroProjectionDatosEmpresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ControlMaestroDao extends CrudRepository<ControlMaestro, String> {

    ControlMaestro findCMByNombre(String nombre);

    @Query(nativeQuery = true, value = " \n" +
            "    SELECT \n" +
            "        ORDEN AS orden, \n" +
            "        VALOR AS valor \n" +
            "    FROM getDatosEmpresa() \n" +
            "")
    List<ControlMaestroProjectionDatosEmpresa> fnGetDatosEmpresa();

}
