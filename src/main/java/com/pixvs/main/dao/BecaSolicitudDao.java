package com.pixvs.main.dao;

import com.pixvs.main.models.Alumno;
import com.pixvs.main.models.BecaSolicitud;
import com.pixvs.main.models.projections.Alumno.*;
import com.pixvs.main.models.projections.BecaSolicitud.BecaSolicitudEditarProjection;
import com.pixvs.main.models.projections.BecaSolicitud.BecaSolicitudListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 31/05/2021.
 */
public interface BecaSolicitudDao extends CrudRepository<BecaSolicitud, String> {

    // Modelo
    BecaSolicitud findById(Integer id);
    BecaSolicitudEditarProjection findProjectionById(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_SOLICITUDES_BECAS] ORDER BY solicitudBeca")
    List<BecaSolicitudListadoProjection> findAllBy();
}
