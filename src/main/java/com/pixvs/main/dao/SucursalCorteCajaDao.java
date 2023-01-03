package com.pixvs.main.dao;

import com.pixvs.main.models.Sucursal;
import com.pixvs.main.models.SucursalCorteCaja;
import com.pixvs.main.models.projections.SucursalCorteCaja.SucursalCorteCajaListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 03/09/2021.
 */
public interface SucursalCorteCajaDao extends CrudRepository<SucursalCorteCaja, String> {

    // Modelo
    SucursalCorteCaja findById(Integer id);
    SucursalCorteCaja findFirstByUsuarioAbreIdOrderByIdDesc(Integer usuarioId);
    SucursalCorteCaja findByUsuarioAbreIdAndFechaFinIsNull(Integer usuarioAbreId);
    SucursalCorteCaja findBySucursalIdAndFechaFinIsNull(Integer sucursalId);
    SucursalCorteCaja findBySucursalIdAndSucursalPlantelIdAndFechaFinIsNull(Integer sucursalId, Integer plantelId);

    // BigDecimal
    @Query(nativeQuery = true, value = "SELECT total FROM [dbo].[VW_TotalOVs_SucursalesCortesCajas] WHERE corteId = :corteId")
    BigDecimal getTotalOVs(@Param("corteId") Integer corteId);

    @Query(nativeQuery = true, value = "SELECT top 10 * FROM [VW_LISTADO_CORTES] WHERE sedeId IN (:sedes) ORDER BY fechaInicio DESC ")
    List<SucursalCorteCajaListadoProjection> findProjectedListadoAllBySedeIdIn(@Param("sedes") List<Integer> sedes);

    @Query(nativeQuery = true, value = "SELECT * FROM [VW_LISTADO_CORTES] WHERE (sedeId IN (:sedes)) " +
            "AND (:fechaInicio IS NULL OR CAST(fechaInicio AS date) >= CAST(:fechaInicio AS date)) "+
            "AND (:fechaFin IS NULL OR (:fechaFin IS NOT NULL AND CAST(fechaInicio AS date) <= CAST(:fechaFin AS date))) "+
            "AND (:usuarioId IS NULL OR usuarioId = :usuarioId) " +
            "AND (:id IS NULL  OR id = :id) " +
            "ORDER BY fechaInicio DESC " +
            "OPTION(RECOMPILE)")
    List<SucursalCorteCajaListadoProjection> findProjectedListadoAllByFiltros(@Param("sedes") List<Integer> sedes, @Param("fechaInicio") Object fechaInicio, @Param("fechaFin") Object fechaFin, @Param("usuarioId") Integer usuarioId, @Param("id") Integer id);

}
