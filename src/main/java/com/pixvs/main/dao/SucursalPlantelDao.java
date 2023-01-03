package com.pixvs.main.dao;

import com.pixvs.main.models.SucursalPlantel;
import com.pixvs.main.models.projections.SucursalPlantel.SucursalPlantelComboProjection;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
public interface SucursalPlantelDao extends CrudRepository<SucursalPlantel, String> {

    // Modelo
   SucursalPlantel findById(Integer id);

    // ComboProjection
    List<SucursalPlantelComboProjection> findAllBySucursalId(Integer sucursalId);
    @Query("" +
            "SELECT \n" +
            "   sp.id AS id, \n" +
            "   sp.codigoSucursal AS codigoSucursal, \n" +
            "   sp.nombre AS nombre, \n" +
            "   sp.sucursalId AS sucursalId \n" +
            "FROM SucursalPlantel sp \n" +
            "INNER JOIN sp.almacen alm \n" +
            "INNER JOIN alm.usuariosPermisos usu " +
            "WHERE \n" +
            "   alm.activo = true \n" +
            "   AND usu.id = :usuarioId \n" +
            "   AND sp.sucursalId = :sucursalId \n" +
            "")
    List<SucursalPlantelComboProjection> findAllByPermisosUsuarioAndSucursalId(@Param("usuarioId") Integer usuarioId, @Param("sucursalId") Integer sucursalId);

    @Query("" +
            "SELECT \n" +
            "   sp.id AS id, \n" +
            "   sp.codigoSucursal AS codigoSucursal, \n" +
            "   sp.nombre AS nombre, \n" +
            "   sp.sucursalId AS sucursalId \n" +
            "FROM SucursalPlantel sp \n" +
            "INNER JOIN sp.almacen alm \n" +
            "INNER JOIN alm.usuariosPermisos usu " +
            "WHERE \n" +
            "   alm.activo = true \n" +
            "   AND usu.id = :usuarioId \n" +
            "")
    List<SucursalPlantelComboProjection> findProjectedComboAllByUsuarioPermisosId(@Param("usuarioId") Integer usuarioId);

    List<SucursalPlantelComboProjection> findProjectedComboAllByActivoTrueOrderByNombre();

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT\n" +
            "       USU_UsuarioId AS id,\n" +
            "       USU_Nombre AS nombre,\n" +
            "       USU_PrimerApellido AS primerApellido,\n" +
            "       USU_SegundoApellido AS segundoApellido,\n" +
            "       USU_CMM_EstatusId AS estatusId,\n" +
            "       USU_Nombre+ISNULL(' '+USU_PrimerApellido, '')+ISNULL(' '+USU_SegundoApellido, '') AS nombreCompleto\n" +
            "FROM Usuarios\n" +
            "     INNER JOIN UsuariosAlmacenes ON USU_UsuarioId = USUA_USU_UsuarioId\n" +
            "     INNER JOIN Almacenes ON USUA_ALM_AlmacenId = ALM_AlmacenId\n" +
            "     INNER JOIN SucursalesPlanteles ON ALM_AlmacenId = SP_ALM_AlmacenId AND SP_SucursalPlantelId IN(:plantelesId)\n" +
            "WHERE USU_CMM_EstatusId = 1000001 -- Activo\n" +
            "     AND (COALESCE(:usuarioId, 0) = 0 OR USU_UsuarioId = :usuarioId)\n" +
            "ORDER BY nombreCompleto\n" +
            "OPTION(RECOMPILE)")
    List<UsuarioComboProjection> findUsuariosPorPlantelId(@Param("plantelesId") List<Integer> plantelesId, @Param("usuarioId") Integer usuarioId);
}