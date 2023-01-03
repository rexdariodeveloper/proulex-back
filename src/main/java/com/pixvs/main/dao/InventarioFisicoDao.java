package com.pixvs.main.dao;

import com.pixvs.main.models.InventarioFisico;
import com.pixvs.main.models.projections.InventarioFisico.InventarioFisicoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InventarioFisicoDao extends CrudRepository<InventarioFisico, String> {

    InventarioFisico findById(Integer id);

    InventarioFisico save(InventarioFisico inventarioFisico);

    InventarioFisico findInventarioFisicoById(Integer id);

    InventarioFisicoListadoProjection findInventarioFisicoProjectedById(Integer id);

    InventarioFisicoListadoProjection findInventarioFisicoByLocalidadIdAndEstatusId(Integer localidadId, Integer estatusId);

    List<InventarioFisicoListadoProjection> findFirst200ByEstatusIdInOrderByFechaDesc(Integer[] estatusId);

    List<InventarioFisicoListadoProjection> findFirst200ByIdIsNotNullOrderByFechaDesc();

    List<InventarioFisicoListadoProjection> findInventariosFisicosByLocalidadIdAndFechaCreacionGreaterThanEqual(Integer localidadId, Date fechaCreacion);

    @Query("SELECT p FROM InventarioFisico p " +
            "WHERE (:fechaCreacionDesde IS NULL OR CAST(p.fecha AS date) >= :fechaCreacionDesde) " +
            "AND (:fechaCreacionHasta IS NULL OR CAST(p.fecha AS date) <= :fechaCreacionHasta) " +
            "AND (:allEstatus = 1 OR p.estatusId IN (:estatus))" +
            "ORDER BY p.fecha DESC")
    List<InventarioFisicoListadoProjection> findAllQueryProjectedBy(@Param("fechaCreacionDesde") Date fechaCreacion, @Param("fechaCreacionHasta") Date fechaCreacionHasta, @Param("allEstatus") int allEstatus, @Param("estatus") List<Integer> estatus);

    @Modifying
    @Query(value = "UPDATE InventariosFisicos set IF_CMM_EstatusId = :estatusId WHERE IF_InventarioFisicoId = :id", nativeQuery = true)
    int actualizarEstatus(@Param("id") Integer id, @Param("estatusId") Integer estatusId);
}