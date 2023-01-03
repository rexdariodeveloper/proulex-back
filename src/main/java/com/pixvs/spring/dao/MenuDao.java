package com.pixvs.spring.dao;

import com.pixvs.spring.models.MenuPrincipal;
import com.pixvs.spring.models.projections.MenuPrincipal.MenuPrincipalListadoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuDao extends CrudRepository<MenuPrincipal, String> {


    List<MenuPrincipal> findAllByActivoTrue();

    @Query("SELECT mp FROM MenuPrincipal mp" +
            "   LEFT JOIN RolMenu rm on mp.id = rm.nodoId AND rm.rolId= :rolId " +
            "   WHERE mp.activo = true AND mp.sistemaAccesoId = :sistemaAccesoId " +
            "   AND coalesce( mp.nodoPadreId , -1) =  :nodoPadreId  " +
            "   ORDER BY mp.orden")
    List<MenuPrincipal> findAllProjectedAllByUsuarioIdAndSistemaAccesoIdOrderByOrdenAsc(@Param("rolId") Integer rolId, @Param("sistemaAccesoId") Integer sistemaAccesoId, @Param("nodoPadreId") int nodoPadreId);

    @Query("SELECT Menu FROM MenuPrincipal Menu\n" +
            "WHERE MP_Activo = 1 AND MP_CMM_SistemaAccesoId = :sistemaAccesoId AND coalesce( MP_NodoPadreId , -1) =  :nodoPadreId\n" +
            "AND MP_Titulo!='CONFIGURACIÓN'\n" +
            "ORDER BY MP_Orden")
    List<MenuPrincipal> getMenuPublico(@Param("sistemaAccesoId") Integer sistemaAccesoId, @Param("nodoPadreId") int nodoPadreId);

    @Query(value = "SELECT case when count(rm)> 0 then true else false end FROM RolMenu rm WHERE rm.rolId = :rolId and rm.nodoId = :menuId")
    boolean existsRolMenu(@Param("rolId") Integer rolId, @Param("menuId") Integer menuId);

    MenuPrincipal findByTitle(@Param("title") String title);

    MenuPrincipal findByUrl(@Param("url") String url);

}
