package com.pixvs.spring.dao;

import com.pixvs.spring.models.Rol;
import com.pixvs.spring.models.projections.Rol.RolComboProjection;
import com.pixvs.spring.models.projections.Rol.RolEditarProjection;
import com.pixvs.spring.models.projections.Rol.RolListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolDao extends CrudRepository<Rol, String> {

    Rol findById(Integer id);

    List<RolListadoProjection> findAllProjectedBy();

    List<RolComboProjection> findAllProjectedByActivoTrue();

    RolEditarProjection findProjectedById(Integer rolId);

    @Query("" +
            "SELECT \n" +
            "   (CASE WHEN COUNT(rmp) > 0 THEN true ELSE false END) \n" +
            "FROM RolMenuPermiso rmp \n" +
            "WHERE rmp.rolId = :rolId AND rmp.permisoId = :menuPrincipalPermisoId")
    Boolean tienePermiso(@Param("rolId") Integer rolId, @Param("menuPrincipalPermisoId") Integer menuPrincipalPermisoId);

}
