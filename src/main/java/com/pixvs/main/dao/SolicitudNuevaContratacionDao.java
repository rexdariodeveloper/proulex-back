package com.pixvs.main.dao;

import com.pixvs.main.models.SolicitudNuevaContratacion;
import com.pixvs.main.models.projections.SolicitudNuevaContratacion.SolicitudNuevaContratacionEditarProjection;
import com.pixvs.main.models.projections.SolicitudNuevaContratacion.SolicitudNuevaContratacionListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Rene Carrillo on 22/04/2022.
 */
public interface SolicitudNuevaContratacionDao extends CrudRepository<SolicitudNuevaContratacion, String> {
    SolicitudNuevaContratacionEditarProjection findProjectedEditarById(Integer solicitudNuevaContratacionId);
    SolicitudNuevaContratacion findById(Integer solicitudNuevaContratacionId);

    // ListadoProjection
    @Query(nativeQuery = true,value = "SELECT * FROM [dbo].[VW_Listado_NuevaContratacion]")
    List<SolicitudNuevaContratacionListadoProjection> findProjectedListadoAllBy();
}
