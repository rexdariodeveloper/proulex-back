package com.pixvs.main.dao;

import com.pixvs.main.models.OrdenVentaCancelacion;
import com.pixvs.main.models.projections.OrdenVentaCancelacion.OrdenVentaCancelacionEditarProjection;
import com.pixvs.main.models.projections.OrdenVentaCancelacion.OrdenVentaCancelacionListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 25/03/2022.
 */
public interface OrdenVentaCancelacionDao extends CrudRepository<OrdenVentaCancelacion, String> {

    // Modelo
    OrdenVentaCancelacion findById(Integer id);

    // OrdenVentaCancelacionListadoProjection
    @Query(nativeQuery = true, value = "SELECT * FROM VW_OrdenesVentaCancelaciones")
    List<OrdenVentaCancelacionListadoProjection> findProjectedListadoAllBy();

    // OrdenVentaCancelacionEditarProjection
    OrdenVentaCancelacionEditarProjection findProjectedEditarById(Integer id);

    // Integer
    @Query(nativeQuery = true, value =
            "SELECT ordenVentaId \n" +
            "FROM [dbo].[VW_OrdenesVentaCancelaciones] \n" +
            "WHERE id = :id")
    Integer findOrdenVentaIdById(@Param("id") Integer id);

    @Query(nativeQuery = true, value =
            "SELECT sucursalId \n" +
            "FROM [dbo].[VW_OrdenesVentaCancelaciones] \n" +
            "WHERE id = :id")
    Integer findSucursalIdById(@Param("id") Integer id);
}