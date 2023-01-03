package com.pixvs.spring.dao;

import com.pixvs.spring.models.MenuListadoGeneral;
import com.pixvs.spring.models.projections.MenuListadoGeneral.MenuListadoGeneralComboProjection;
import com.pixvs.spring.models.projections.MenuListadoGeneral.MenuListadoGeneralEditarProjection;
import com.pixvs.spring.models.projections.MenuListadoGeneral.MenuListadoGeneralListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuListadoGeneralDao extends CrudRepository<MenuListadoGeneral, String> {

    List<MenuListadoGeneralListadoProjection> findProjectedListadoAllBy();

    List<MenuListadoGeneralComboProjection> findProjectedComboAllByActivoTrue();

    MenuListadoGeneralEditarProjection findProjectedEditarById(Integer id);

    MenuListadoGeneral findById(Integer id);


    @Modifying
    @Query(value = "UPDATE ListadoGenerals SET MLG_Activo = :activo WHERE MLG_ListadoGeneralId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query("" +
            "    SELECT mlg \n" +
            "    FROM MenuListadoGeneral mlg \n" +
            "    WHERE mlg.activo = true \n" +
            "    AND COALESCE( mlg.nodoPadreId , -1) =  :nodoPadreId \n" +
            "    ORDER BY mlg.orden \n" +
            "")
    List<MenuListadoGeneral> findAllModelsByNodoPadreIdOrderByOrdenAsc(@Param("nodoPadreId") int nodoPadreId);

}
