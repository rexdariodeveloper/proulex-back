package com.pixvs.main.dao;

import com.pixvs.main.models.Almacen;
import com.pixvs.main.models.projections.Almacen.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 17/07/2020.
 */
public interface AlmacenDao extends CrudRepository<Almacen, String> {

    List<AlmacenListadoProjection> findProjectedListadoAllByTipoAlmacenIdNotIn(List<Integer> tipoAlmacenIds);
    //List<AlmacenComboProjection> findAllByActivoIsTrueAndSucursalIdIsNullOrSucursalId();
    List<AlmacenComboProjection> findProjectedComboAllByActivoTrue();
    @Query("" +
            "SELECT a " +
            "FROM Almacen a " +
            "INNER JOIN a.usuariosPermisos u " +
            "WHERE " +
            "a.activo = true and " +
            "u.id = :usuarioPermisoId " +
            "ORDER BY a.codigoAlmacen")
    List<AlmacenComboProjection> findProjectedComboAllByActivoTrueAndPermiso(@Param("usuarioPermisoId") Integer usuarioPermisoId);

    @Query("" +
            "SELECT a " +
            "FROM Almacen a " +
            "WHERE " +
            "a.activo = true and (a.sucursalId is null or a.sucursalId=:idSucursal)" +
            "ORDER BY a.codigoAlmacen")
    List<AlmacenComboProjection> findProjectedComboAllByActivoTrueAndSucursalId(@Param("idSucursal") Integer idSucursal);

    AlmacenComboProjection findProjectedComboByActivoTrueAndTipoAlmacenId(Integer tipoId);

    List<AlmacenComboResponsableProjection> findAllProjectedComboResponsableByActivoTrueAndTipoAlmacenIdOrderByCodigoAlmacen(Integer tipoId);

    List<AlmacenComboProjection> findAllProjectedComboByActivoTrueAndTipoAlmacenIdOrderByCodigoAlmacen(Integer tipoId);
    @Query("" +
            "SELECT a " +
            "FROM Almacen a " +
            "INNER JOIN a.usuariosPermisos u " +
            "WHERE " +
            "a.activo = true and " +
            "a.tipoAlmacenId = :tipoId and " +
            "u.id = :usuarioPermisoId " +
            "ORDER BY a.codigoAlmacen")
    List<AlmacenComboProjection> findAllProjectedComboByActivoTrueAndTipoAlmacenIdAndPermisoOrderByCodigoAlmacen(@Param("tipoId") Integer tipoId, @Param("usuarioPermisoId") Integer usuarioPermisoId);

    List<AlmacenComboProjection> findProjectedComboAllBySucursalIdAndActivoTrue(Integer sucursalId);
    List<AlmacenComboProjection> findProjectedComboAllByClienteIdAndActivoTrue(Integer clienteId);

    @Query("" +
            "SELECT DISTINCT" +
            "   a.id as id," +
            "   a.codigoAlmacen as codigoAlmacen," +
            "   a.nombre as nombre," +
            "   a.esCedi as esCedi," +
            "   a.sucursal as sucursal," +
            "   a.mismaDireccionSucursal as mismaDireccionSucursal," +
            "   a.mismaDireccionCliente as mismaDireccionCliente," +
            "   a.cp as cp," +
            "   a.domicilio as domicilio," +
            "   a.colonia as colonia," +
            "   a.ciudad as ciudad," +
            "   e as estado," +
            "   p as pais \n" +
            "FROM Almacen a \n" +
            "INNER JOIN a.usuariosPermisos u \n" +
            "LEFT JOIN a.estado e \n" +
            "LEFT JOIN a.pais p \n" +
            "WHERE a.activo = true AND u.id = :usuarioId \n" +
            "ORDER BY a.nombre \n" +
            "")
    List<AlmacenComboProjection> findProjectedComboAllByUsuarioPermisosId(@Param("usuarioId") Integer usuarioId);

    @Query("" +
            "SELECT DISTINCT" +
            "   a.id as id," +
            "   a.codigoAlmacen as codigoAlmacen," +
            "   a.nombre as nombre," +
            "   a.esCedi as esCedi," +
            "   a.sucursal as sucursal," +
            "   a.mismaDireccionSucursal as mismaDireccionSucursal," +
            "   a.mismaDireccionCliente as mismaDireccionCliente," +
            "   a.cp as cp," +
            "   a.domicilio as domicilio," +
            "   a.colonia as colonia," +
            "   a.ciudad as ciudad," +
            "   e as estado," +
            "   p as pais \n" +
            "FROM Almacen a " +
            "INNER JOIN a.usuariosPermisos u " +
            "LEFT JOIN a.estado e \n" +
            "LEFT JOIN a.pais p \n" +
            "WHERE " +
            "a.activo = true AND " +
            "u.id = :usuarioId AND " +
            "a.tipoAlmacenId = :tipoAlmacenId " +
            "ORDER BY a.nombre" +
            "")
    List<AlmacenComboProjection> findProjectedComboAllByUsuarioPermisosIdAndTipoAlmacen(@Param("usuarioId") Integer usuarioId, @Param("tipoAlmacenId") Integer tipoAlmacenId);

    AlmacenEditarProjection findProjectedEditarById(Integer id);

    Almacen findById(Integer id);

    Almacen findBySucursalIdAndPredeterminadoTrue(Integer sucursalId);
    List<Almacen> findBySucursalIdAndActivoTrue(Integer sucursalId);

    Almacen findByIdNotAndSucursalIdAndPredeterminadoTrue(Integer id, Integer sucursalId);

    Almacen findByCodigoAlmacen(String codigoAlmacen);
    Almacen findByCodigoAlmacenAndIdNot(String codigoAlmacen, Integer id);

    @Query(value = "SELECT * FROM fn_getDatosAdicionalesUsuarioAlmacenes(:usuarioId) ORDER BY Orden", nativeQuery = true)
    List<AlmacenDatosAdicionalesEmpleadoProjection> findProjectedDatosAdicionalesByUsuarioId(@Param("usuarioId") Integer usuarioId);
}
