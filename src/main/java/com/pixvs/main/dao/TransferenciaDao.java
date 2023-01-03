package com.pixvs.main.dao;

import com.pixvs.main.models.Transferencia;
import com.pixvs.main.models.projections.Transferencia.TransferenciaListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransferenciaDao extends CrudRepository<Transferencia, String> {

    Transferencia findById(Integer id);

    Transferencia save(Transferencia transferencia);

    Transferencia findTransferenciaById(Integer id);

    TransferenciaListadoProjection findTransferenciaProjectedById(Integer id);

    TransferenciaListadoProjection findTransferenciaByLocalidadOrigenIdAndLocalidadDestinoIdAndEstatusIdNotIn(Integer origenId, Integer destinoId, Integer[] estatusId);

    List<TransferenciaListadoProjection> findFirst200ByEstatusIdInOrderByFechaDesc(Integer[] estatusId);

    List<TransferenciaListadoProjection> findFirst200ByIdIsNotNullOrderByFechaDesc();

    @Query(value = "SELECT t FROM " +
            "Transferencia t " +
            "INNER JOIN t.localidadOrigen lo " +
            "INNER JOIN lo.almacen ao " +
            "INNER JOIN t.localidadDestino ld " +
            "INNER JOIN ld.almacen ad " +
            "LEFT JOIN ao.usuariosPermisos uo " +
            "LEFT JOIN ad.usuariosPermisos ud " +
            "WHERE " +
            ":usuarioId IN (coalesce(uo.id,0),coalesce(ud.id,0) ) "+
            "ORDER BY t.fechaCreacion DESC")
    List<TransferenciaListadoProjection> findAllTransferenciasByPermiso(@Param("usuarioId") Integer usuarioId);

    @Query(value = "SELECT t FROM " +
            "Transferencia t " +
            "INNER JOIN t.localidadDestino ld " +
            "INNER JOIN ld.almacen ad " +
            "LEFT JOIN ad.usuariosPermisos ud " +
            "WHERE " +
            ":usuarioId = coalesce(ud.id,0) AND "+
            "t.estatusId IN (:estatus) "+
            "ORDER BY t.fechaCreacion DESC")
    List<TransferenciaListadoProjection> findAllTransferenciasByPermisoDestinoAndEstatus(@Param("usuarioId") Integer usuarioId, @Param("estatus") Integer[] estatus);

    @Query(value = "SELECT t FROM " +
            "Transferencia t " +
            "INNER JOIN t.localidadOrigen lo " +
            "INNER JOIN lo.almacen ao " +
            "INNER JOIN t.localidadDestino ld " +
            "INNER JOIN ld.almacen ad " +
            "LEFT JOIN ao.usuariosPermisos uo " +
            "LEFT JOIN ad.usuariosPermisos ud " +
            "WHERE " +
            ":usuarioId IN (coalesce(uo.id,0),coalesce(ud.id,0) ) AND "+
            "t.estatusId IN (:estatus) "+
            "ORDER BY t.fechaCreacion DESC")
    List<TransferenciaListadoProjection> findAllTransferenciasByPermisoAndEstatus(@Param("usuarioId") Integer usuarioId, @Param("estatus") Integer[] estatus);

    @Query("SELECT p FROM " +
            "Transferencia p " +
            "INNER JOIN p.localidadOrigen lo " +
            "INNER JOIN lo.almacen ao " +
            "INNER JOIN p.localidadDestino ld " +
            "INNER JOIN ld.almacen ad " +
            "LEFT JOIN ao.usuariosPermisos uo " +
            "LEFT JOIN ad.usuariosPermisos ud " +
            "WHERE " +
            ":usuarioId IN (coalesce(uo.id,0),coalesce(ud.id,0) ) "+
            "AND (:fechaCreacionDesde IS NULL OR CAST(p.fecha AS date) >= :fechaCreacionDesde) " +
            "AND (:fechaCreacionHasta IS NULL OR CAST(p.fecha AS date) <= :fechaCreacionHasta) " +
            "AND (:allEstatus = 1 OR p.estatusId IN (:estatus))" +
            "ORDER BY p.fecha DESC")
    List<TransferenciaListadoProjection> findAllQueryProjectedBy(@Param("fechaCreacionDesde") Date fechaCreacion,
                                                                 @Param("fechaCreacionHasta") Date fechaCreacionHasta,
                                                                 @Param("allEstatus") int allEstatus,
                                                                 @Param("estatus") List<Integer> estatus,
                                                                 @Param("usuarioId") Integer usuarioId);
}