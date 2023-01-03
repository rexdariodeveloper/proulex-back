package com.pixvs.main.dao;

import com.pixvs.main.models.ArticuloTipo;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoComboProjection;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoEditarProjection;
import com.pixvs.main.models.projections.ArticuloTipo.ArticuloTipoListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Angel Daniel Hernández Silva on 02/07/2020.
 */
public interface ArticuloTipoDao extends CrudRepository<ArticuloTipo, String> {

    List<ArticuloTipoListadoProjection> findProjectedListadoAllBy();

    List<ArticuloTipoComboProjection> findProjectedComboAllByActivoTrue();

    @Query(value = "SELECT at FROM ArticuloTipo at WHERE at.activo = true AND at.tipoId <> :tipoArticuloId")
    List<ArticuloTipoComboProjection> findProjectedComboAllByActivoTrueAndTipoArticuloIdNot(@Param("tipoArticuloId") Integer tipoArticuloId);

    ArticuloTipoEditarProjection findProjectedEditarById(Integer id);

    ArticuloTipo findById(Integer id);


    @Modifying
    @Query(value = "UPDATE ArticuloTipos SET ARTT_Activo = :activo WHERE ARTT_ArticuloTipoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}