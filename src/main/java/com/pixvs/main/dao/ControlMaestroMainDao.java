package com.pixvs.main.dao;

import com.pixvs.main.models.mapeos.ControlesMaestros;
import com.pixvs.spring.models.ControlMaestro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ControlMaestroMainDao extends CrudRepository<ControlMaestro, String> {

    @Query(value = "SELECT dbo.getPathServiceSAT('" + ControlesMaestros.CMA_ServiceSAT_ListaNegra + "')",  nativeQuery = true)
    String fnGetPathServiceSAT();

}
