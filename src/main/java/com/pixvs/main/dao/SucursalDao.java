package com.pixvs.main.dao;

import com.pixvs.main.models.Sucursal;
import com.pixvs.main.models.projections.Sucursal.*;
import com.pixvs.spring.models.projections.Usuario.UsuarioComboProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
public interface SucursalDao extends CrudRepository<Sucursal, String> {

    // Modelo
    Sucursal findById(Integer id);

    SucursalComboProjection findSucursalComboProjectionById(Integer id);

    Sucursal findByCodigoSucursal(String codigoSucursal);

    Sucursal findByPredeterminadaTrue();

    Sucursal findByIdNotAndPredeterminadaTrue(Integer id);

    @Query("" +
            "SELECT s \n" +
            "FROM Sucursal s \n" +
            "INNER JOIN s.almacenesHijos a \n" +
            "INNER JOIN a.usuariosPermisos u \n" +
            "WHERE s.activo = true AND u.id = :usuarioId AND s.id = :id \n" +
            "")
    Sucursal findByUsuarioPermisosIdAndId(@Param("usuarioId") Integer usuarioId, @Param("id") Integer id);

    List<SucursalListadoProjection> findProjectedListadoAllByOrderByCodigoSucursal();

    List<SucursalComboProjection> findProjectedComboAllByActivoTrue();

    List<SucursalComboIncompanyProjection> findProjectedComboIncompanyAllByActivoTrue();

    List<SucursalComboProjection> findProjectedComboAllByActivoTrueOrderByNombre();

    @Query("" +
            "SELECT DISTINCT s.id as id,s.nombre as nombre, s.codigoSucursal as codigoSucursal, s.tipoFacturaGlobalId as tipoFacturaGlobalId \n" +
            "FROM Sucursal s \n" +
            "INNER JOIN s.almacenesHijos a \n" +
            "INNER JOIN a.usuariosPermisos u \n" +
            "WHERE s.activo = true AND u.id = :usuarioId \n" +
            "ORDER BY s.nombre \n" +
            "")
    List<SucursalComboProjection> findProjectedComboAllByUsuarioPermisosId(@Param("usuarioId") Integer usuarioId);

    @Query("" +
            "SELECT DISTINCT s.id as id, s.nombre as nombre, s.codigoSucursal as codigoSucursal, s.tipoFacturaGlobalId as tipoFacturaGlobalId \n" +
            "FROM Sucursal s \n" +
            "INNER JOIN s.almacenesHijos a \n" +
            "INNER JOIN a.usuariosPermisos u \n" +
            "WHERE s.activo = true AND u.id = :usuarioId AND s.listadoPrecioId IS NOT NULL \n" +
            "ORDER BY s.nombre \n" +
            "")
    List<SucursalComboProjection> findProjectedComboAllByUsuarioPermisosIdAndListadoPrecioIdIsNotNull(@Param("usuarioId") Integer usuarioId);

    SucursalEditarProjection findProjectedEditarById(Integer id);

    @Query("\n" +
            "SELECT DISTINCT \n" +
            "   s.id AS id, \n" +
            "   s.nombre AS nombre, \n" +
            "   CONCAT(s.nombre,CASE WHEN p.id IS NULL THEN '' ELSE CONCAT(' - ',p.nombre) END) AS nombreCombinado, \n" +
            "   p.id AS sucursalPlantelId, \n" +
            "   p.nombre AS sucursalPlantelNombre \n" +
            "FROM Sucursal s \n" +
            "LEFT JOIN s.planteles p \n" +
            "INNER JOIN s.almacenesHijos a \n" +
            "INNER JOIN a.usuariosPermisos u \n" +
            "WHERE u.id = :usuarioId")
    List<SucursalComboPlantelProjection> findProjectedComboPlantelAllByUsuarioPermisosId(@Param("usuarioId") Integer usuarioId);

    @Query(value = "\n" +
            "SELECT DISTINCT\n" +
            "CASE WHEN p.SP_SucursalPlantelId IS NULL THEN CAST(s.SUC_SucursalId AS VARCHAR) ELSE CONCAT(s.SUC_SucursalId,'-',p.SP_SucursalPlantelId) END AS id,\n" +
            "s.SUC_Nombre AS nombre,\n" +
            "CONCAT(s.SUC_Nombre,CASE WHEN p.SP_SucursalPlantelId IS NULL THEN '' ELSE CONCAT(' - ',p.SP_Nombre) END) AS nombreCombinado,\n" +
            "p.SP_SucursalPlantelId AS sucursalPlantelId,\n" +
            "p.SP_Nombre AS sucursalPlantelNombre \n" +
            "from Sucursales s\n" +
            "LEFT JOIN SucursalesPlanteles p on SP_SUC_SucursalId = SUC_SucursalId\n" +
            "INNER JOIN Almacenes a on ALM_SUC_SucursalId = SUC_SucursalId\n" +
            "INNER JOIN UsuariosAlmacenes u on USUA_ALM_AlmacenId = ALM_AlmacenId\n" +
            "WHERE u.USUA_USU_UsuarioId= :usuarioId\n" +
            "ORDER BY s.SUC_Nombre\n", nativeQuery = true)
    List<SucursalComboPlantelFiltrosProjection> findProjectedComboPlantelAllByUsuarioFiltros(@Param("usuarioId") Integer usuarioId);

    @Query("\n" +
            "SELECT DISTINCT s.id AS id \n" +
            "FROM Sucursal s \n" +
            "INNER JOIN s.almacenesHijos a \n" +
            "INNER JOIN a.usuariosPermisos u \n" +
            "WHERE u.id = :usuarioId")
    List<Integer> findIdsByUsuarioPermisosId(@Param("usuarioId") Integer usuarioId);

    @Modifying
    @Query(value = "UPDATE Sucursales SET SUC_Activo = :activo WHERE SUC_SucursalId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(value = "" +
            "Select TOP 1 CAST(COALESCE(PROG_JOBS,PROG_JOBSSEMS,0) as BIT) from Sucursales\n" +
            "INNER JOIN ProgramasIdiomasSucursales on PROGIS_SUC_SucursalId = SUC_SucursalId\n" +
            "INNER JOIN ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGIS_PROGI_ProgramaIdiomaId\n" +
            "INNER JOIN Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId\n" +
            "WHERE SUC_SucursalId=:idSucursal" +
            "", nativeQuery = true)
    Boolean sucursalEsJobs(@Param("idSucursal") Integer idSucursal);

    SucursalComboProjection findProjectedComboById(Integer id);

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
            "     INNER JOIN Almacenes ON USUA_ALM_AlmacenId = ALM_AlmacenId AND ALM_SUC_SucursalId = :sedeId\n" +
            "WHERE USU_CMM_EstatusId = 1000001 -- Activo\n" +
            "     AND (COALESCE(:usuarioId, 0) = 0 OR USU_UsuarioId = :usuarioId)\n" +
            "ORDER BY nombreCompleto\n" +
            "OPTION(RECOMPILE)")
    List<UsuarioComboProjection> findUsuariosPorSedeId(@Param("sedeId") Integer sedeId, @Param("usuarioId") Integer usuarioId);
}