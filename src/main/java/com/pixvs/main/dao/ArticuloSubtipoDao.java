package com.pixvs.main.dao;

import com.pixvs.main.models.ArticuloSubtipo;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoComboProjection;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoEditarProjection;
import com.pixvs.main.models.projections.ArticuloSubtipo.ArticuloSubtipoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
public interface ArticuloSubtipoDao extends CrudRepository<ArticuloSubtipo, String> {

    List<ArticuloSubtipoListadoProjection> findProjectedListadoAllBy();

    List<ArticuloSubtipoComboProjection> findProjectedComboAllByActivoTrue();
    List<ArticuloSubtipoComboProjection> findProjectedComboAllByArticuloTipoIdAndActivoTrue(Integer articuloTipoId);

    ArticuloSubtipoEditarProjection findProjectedEditarById(Integer id);

    ArticuloSubtipo findById(Integer id);


    @Modifying
    @Query(value = "UPDATE ArticuloSubtipos SET ARTST_Activo = :activo WHERE ARTST_ArticuloSubtipoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}