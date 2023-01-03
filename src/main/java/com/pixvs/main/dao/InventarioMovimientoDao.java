package com.pixvs.main.dao;

import com.pixvs.main.models.InventarioMovimiento;
import com.pixvs.main.models.projections.InventarioMovimiento.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface InventarioMovimientoDao extends CrudRepository<InventarioMovimiento, Integer> {

    InventarioMovimientoListadoProjection findInventarioMovimientoById(Integer id);

    List<InventarioMovimientoListadoProjection> findAllProjectedByTipoMovimientoId(Integer tipo);

    List<InventarioMovimientoListadoProjection> findFirst200ByTipoMovimientoIdOrderByFechaCreacionDesc(Integer tipo);
    List<InventarioMovimientoListadoProjection> findFirst200ByTipoMovimientoIdAndLocalidadIdInOrderByFechaCreacionDesc(Integer tipo, List<Integer> localidadIds);

    List<InventarioMovimientoComboProjection> findAllByIdIsNotNullOrderByFechaCreacionDesc();

    @Query("SELECT p FROM InventarioMovimiento p " +
            "WHERE (:fechaCreacionDesde IS NULL OR CAST(p.fechaCreacion AS date) >= :fechaCreacionDesde) " +
            "AND (:fechaCreacionHasta IS NULL OR CAST(p.fechaCreacion AS date) <= :fechaCreacionHasta) " +
            "AND (:isReferencia = 1 OR p.referencia = :referencia)" +
            "ORDER BY p.fechaCreacion DESC")
    List<InventarioMovimientoListadoProjection> findAllQueryProjectedBy(@Param("fechaCreacionDesde") Date fechaCreacion, @Param("fechaCreacionHasta") Date fechaCreacionHasta, @Param("isReferencia") int isReferencia, @Param("referencia") String referencia);

    @Query(value =
            "SELECT * FROM fn_getKardexArticulo(" +
                    ":articulos, " +
                    ":fechaCreacionDesde, " +
                    ":fechaCreacionHasta, " +
                    ":localidades, " +
                    ":referencia" +
                    ") ORDER BY Fecha ASC",
            nativeQuery = true)
    List<KardexArticuloListadoProjection> getKardexArticulo(@Param("articulos") Object articulos,
                                                            @Param("fechaCreacionDesde") Object fechaCreacionDesde,
                                                            @Param("fechaCreacionHasta") Object fechaCreacionHasta,
                                                            @Param("localidades") Object localidades,
                                                            @Param("referencia") Object referencia);

    @Query(value = "SELECT * FROM fn_reporteExistencias(:fechaFin, :articulos, :localidades, :ceros, :usuario) " +
            "ORDER BY \n" +
            "CASE WHEN PATINDEX('%[0-9]',nombre) > 1 THEN LEFT(nombre,PATINDEX('%[0-9]',nombre)-1) ELSE nombre END ,\n" +
            "    CASE WHEN PATINDEX('%[0-9]',nombre) > 1 THEN\n" +
            "        CAST(SUBSTRING(nombre,PATINDEX('%[0-9]',nombre),LEN(nombre)) as float) ELSE NULL END" +
            "", nativeQuery = true)
    List<ReporteExistenciasListadoProjection> fn_reporteExistencias(@Param("fechaFin") Object fechaFin,
                                                                    @Param("articulos") Object articulos,
                                                                    @Param("localidades") Object localidades,
                                                                    @Param("ceros") Object ceros,
                                                                    @Param("usuario") Integer usuario);

    @Query(value = "SELECT * FROM fn_reporteExistencias(:fechaFin, :articulos, :localidades, :ceros, :usuario) " +
            "ORDER BY \n" +
            "CASE WHEN PATINDEX('%[0-9]',nombre) > 1 THEN LEFT(nombre,PATINDEX('%[0-9]',nombre)-1) ELSE nombre END ,\n" +
            "    CASE WHEN PATINDEX('%[0-9]',nombre) > 1 THEN\n" +
            "        CAST(SUBSTRING(nombre,PATINDEX('%[0-9]',nombre),LEN(nombre)) as float) ELSE NULL END" +
            "", nativeQuery = true)
    List<ReporteExistenciasComboProjection> fn_reporteExistenciasSinPermisos(@Param("fechaFin") Object fechaFin,
                                                                  @Param("articulos") Object articulos,
                                                                  @Param("localidades") Object localidades,
                                                                  @Param("ceros") Object ceros,
                                                                  @Param("usuario") Integer usuario);

    @Query(value = "SELECT * FROM fn_reporteValuacion(:fechaInicio, :fechaFin, :articulos, :localidades, :usuario) ORDER BY almacenLocalidad, nombre", nativeQuery = true)
    List<ReporteValuacionListadoProjection> fn_reporteValuacion(@Param("fechaInicio") Object fechaInicio,
                                                                @Param("fechaFin") Object fechaFin,
                                                                @Param("articulos") Object articulos,
                                                                @Param("localidades") Object localidades,
                                                                @Param("usuario") Integer usuario);

    @Query(value = "SELECT * FROM fn_reporteValuacionSerie(:fechaInicio, :fechaFin, :series, :localidades) ORDER BY almacenLocalidad, serie", nativeQuery = true)
    List<ReporteValuacionSerieListadoProjection> fn_reporteValuacionSerie(@Param("fechaInicio") Object fechaInicio,
                                                                @Param("fechaFin") Object fechaFin,
                                                                @Param("series") Object series,
                                                                @Param("localidades") Object localidades);

    @Query(value = "SELECT * FROM [dbo].[VW_RPT_ARTICULOS_TRANSITO] WHERE " +
            "COALESCE(:localidades, CONCAT(\'|\',CAST(localidadId AS NVARCHAR(10)),'|')) LIKE CONCAT('%|',CAST(localidadId AS NVARCHAR(10)),'|%') " +
            "ORDER BY recibe, codigoArticulo", nativeQuery = true)
    List<ReporteArticulosTransitoProjection> reporteArticulosTransito(@Param("localidades") String localidades);
}