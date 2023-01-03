package com.pixvs.main.dao;

import com.pixvs.main.models.SolicitudBajaContratacion;
import com.pixvs.main.models.projections.SolicitudBajaContratacion.SolicitudBajaContratacionEditarProjection;
import com.pixvs.main.models.projections.SolicitudBajaContratacion.SolicitudBajaContratacionListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rene Carrillo on 31/08/2022.
 */
public interface SolicitudBajaContratacionDao extends CrudRepository<SolicitudBajaContratacion, String> {
    SolicitudBajaContratacionEditarProjection findById(Integer id);

    // ListadoProjection
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Listado_Baja]")
    List<SolicitudBajaContratacionListadoProjection> findProjectedListadoAllBy();
}
