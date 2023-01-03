package com.pixvs.main.dao;

import com.pixvs.main.models.Prenomina;
import com.pixvs.main.models.projections.Prenomina.PrenominaListadoPagarProjection;
import com.pixvs.main.models.projections.Prenomina.PrenominaQuincenaFechasProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
public interface PrenominaDao extends CrudRepository<Prenomina, String> {
    @Query(value = "" +
            "Select * from dbo.fn_getPrenomina(:fechaInicio,:fechaFin)", nativeQuery = true)
    List<PrenominaListadoPagarProjection> findAllPrenomina(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

    @Query(value = "" +
            "Select COUNT(*) from dbo.fn_getPrenomina(:fechaInicio,:fechaFin)", nativeQuery = true)
    Integer findContarPrenomina(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

    @Query(value = "" +
            "Select * from dbo.fn_getPrenomina(:fechaInicio,:fechaFin) WHERE idSucursal=:sucursal ORDER BY empleado", nativeQuery = true)
    List<PrenominaListadoPagarProjection> findAllPrenominaSucursal(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin, @Param("sucursal") Integer sucursal);

    @Query(value = "" +
            "Select * from dbo.fn_getPrenomina(:fechaInicio,:fechaFin) WHERE idSucursal=:sucursal ORDER BY COALESCE(grupoFechaInicio,'9999-12-31'),codigoGrupo,empleado", nativeQuery = true)
    List<PrenominaListadoPagarProjection> findAllPrenominaSucursalOrderByGrupoFechaInicioAndCodigoGrupoAndEmpleado(@Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin, @Param("sucursal") Integer sucursal);

    @Query(value = "Select * from [dbo].[fn_getFechaInicioFinQuincena](:fecha)", nativeQuery = true)
    List<PrenominaQuincenaFechasProjection> getFechaQuincena(@Param("fecha") String fecha);

    @Query(value = "SELECT fechaInicio,fechaFin\n" +
            "FROM(\n" +
            " Select \n" +
            "    (Select fechaInicio from dbo.fn_getFechaInicioFinQuincena(fecha)) as fechaInicio,\n" +
            "    (Select fechaFin from dbo.fn_getFechaInicioFinQuincena(fecha)) as fechaFin\n" +
            " from dbo.fn_getPrenominaPagosNoPagados(:fecha)\n" +
            ") T1\n" +
            "GROUP BY fechaInicio,fechaFin", nativeQuery = true)
    List<PrenominaQuincenaFechasProjection> getFechasNoPagadas(@Param("fecha") String fecha);

    @Query(value = "Select fechaInicio from dbo.fn_getFechaInicioFinQuincena(:fecha)", nativeQuery = true)
    Date getFechaInicioQuincena(@Param("fecha") String fecha);

    @Query(value = "Select fechaFin from dbo.fn_getFechaInicioFinQuincena(:fecha)", nativeQuery = true)
    Date getFechaFinQuincena(@Param("fecha") String fecha);

    @Query(value = "SELECT [dbo].[getFechaInicialPrenomina]()", nativeQuery = true)
    Date getFechaInicialPrenomina();

    @Query(value = "SELECT * FROM [dbo].[getFechasQuincena](:fechaInicio,:fechaFin)", nativeQuery = true)
    List<PrenominaQuincenaFechasProjection> getFechasQuincena(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    @Query(value = "SELECT * FROM [dbo].[getFechasQuincena](:fechaInicio,:fechaFin) WHERE totalPrenomina > 0", nativeQuery = true)
    List<PrenominaQuincenaFechasProjection> getFechasQuincenaConPrenomina(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);

    @Query(value = "SELECT * FROM [dbo].[getFechasQuincenaSucursal](:fechaInicio,:fechaFin,:sucursalId) WHERE totalPrenomina > 0", nativeQuery = true)
    List<PrenominaQuincenaFechasProjection> getFechasQuincenaConPrenominaPorSucursal(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin, @Param("sucursalId") Integer sucursalId);
}
