package com.pixvs.spring.dao;

import com.pixvs.spring.models.RolMenuPermiso;
import com.pixvs.spring.models.projections.RolMenuPermiso.RolMenuPermisoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolMenuPermisoDao extends CrudRepository<RolMenuPermiso, String> {



    @Query(value = "SELECT " +
                "ROLMPP_RolMenuPermisoId AS id, " +
                "MPP_Nombre AS nombrePermiso, " +
                "COALESCE(ROLMPP_ROL_RolId, :idRol) AS rolId, " +
                "MPP_MenuPrincipalPermisoId AS permisoId " +
            "FROM MenuPrincipalPermisos " +
            "LEFT JOIN RolesMenusPermisos ON MPP_MenuPrincipalPermisoId = ROLMPP_MPP_MenuPrincipalPermisoId AND ROLMPP_ROL_RolId = :idRol " +
            "WHERE " +
                "MPP_Activo = 1 " +
                "AND MPP_MP_NodoId = :idNodo",nativeQuery = true)
    List<RolMenuPermisoProjection> findAllProjectedByRolIdAndNodoId(@Param("idRol") Integer idRol, @Param("idNodo") Integer idNodo);

    void deleteAllByRolId(Integer idRol);

    @Modifying
    @Query(value = "DELETE FROM RolesMenusPermisos WHERE ROLMPP_RolMenuPermisoId = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

    RolMenuPermiso findByRolIdAndPermisoId(Integer rolId, Integer permisoId);

    @Query( value = "select rmp from RolMenuPermiso rmp JOIN rmp.permiso p where p.nodoId = :nodoId and rmp.rolId = :rolId")
    List<RolMenuPermisoProjection> getPermisosByRolIdAndNodoId(@Param("rolId") Integer rolId, @Param("nodoId") Integer nodoId);

    @Query(value =
            "SELECT CONVERT(BIT, CASE WHEN ROLMPP_RolMenuPermisoId IS NOT NULL THEN 1 ELSE 0 END) AS tienePermiso\n" +
            "FROM Usuarios\n" +
            "     INNER JOIN Roles ON USU_ROL_RolId = ROL_RolId\n" +
            "     LEFT JOIN RolesMenusPermisos ON ROL_RolId = ROLMPP_ROL_RolId AND ROLMPP_MPP_MenuPrincipalPermisoId = :permisoId\n" +
            "WHERE USU_UsuarioId = :usuarioId", nativeQuery = true)
    boolean isPermisoByUsuarioIdAndPermisoId(@Param("usuarioId") Integer usuarioId, @Param("permisoId") Integer permisoId);
}
