package com.pixvs.main.dao;

import com.pixvs.main.models.MedioPagoPV;
import com.pixvs.main.models.projections.MedioPagoPV.MedioPagoPVComboProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 15/07/2021.
 */
public interface MedioPagoPVDao extends CrudRepository<MedioPagoPV, String> {

    // ComboProjection
    List<MedioPagoPVComboProjection> findProjectedComboAllByActivoTrue();
    @Query("" +
            "SELECT \n" +
            "   mppv.id AS id, \n" +
            "   mppv.codigo AS codigo, \n" +
            "   mppv.nombre AS nombre \n" +
            "FROM Sucursal s \n" +
            "INNER JOIN s.mediosPagoPV mppv \n" +
            "WHERE s.id = :sucursalId AND mppv.activo = true" +
            "")
    List<MedioPagoPVComboProjection> findProjectedComboAllBySucursalId(@Param("sucursalId") Integer sucursalId);

    MedioPagoPV findByCodigo(String codigo);

}
