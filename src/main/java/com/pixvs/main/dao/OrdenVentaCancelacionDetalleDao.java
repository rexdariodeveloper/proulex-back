package com.pixvs.main.dao;

import com.pixvs.main.models.OrdenVentaCancelacionDetalle;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.OrdenVentaCancelacionDetalle.OrdenVentaCancelacionDetalleAfectarInventarioProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 02/09/2022.
 */
public interface OrdenVentaCancelacionDetalleDao extends CrudRepository<OrdenVentaCancelacionDetalle, String> {

    // OrdenVentaCancelacionDetalleAfectarInventarioProjection
    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM [dbo].[VW_AfectarInventario_OrdenesVentaCancelacionesDetalles] \n" +
            "")
    List<OrdenVentaCancelacionDetalleAfectarInventarioProjection> findProjecctedAfectarInventarioAllByPendientes();

    // OrdenVentaCancelacionDetalleAfectarInventarioProjection
    @Query(value = "SELECT * FROM [dbo].[VW_AfectarInventario_OrdenesVentaCancelacionesDetalles] WHERE cancelacionId = :id \n", nativeQuery = true)
    List<OrdenVentaCancelacionDetalleAfectarInventarioProjection> findProjecctedAfectarInventarioByCancelacionId(@Param("id") Integer id);
}