package com.pixvs.main.dao;

import com.pixvs.main.models.ArticuloCategoria;
import com.pixvs.main.models.mapeos.ArticulosTipos;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaCardProjection;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloCategoriaDao extends CrudRepository<ArticuloCategoria, String> {

    ArticuloCategoria findById(Integer id);

    List<ArticuloCategoriaListadoProjection> findProjectedListadoAllByActivoTrue();
    List<ArticuloCategoriaComboProjection> findProjectedComboAllByActivoTrue();
    List<ArticuloCategoriaComboProjection> findProjectedComboAllByFamiliaIdAndActivoTrue(Integer familiaId);

    @Query("" +
            "SELECT DISTINCT \n" +
            "   ac.id AS id, \n" +
            "   ac.nombre AS nombre, \n" +
            "   ac.archivoId AS imagenId \n" +
            "FROM ArticuloCategoria ac \n" +
            "INNER JOIN Articulo a ON a.categoriaId = ac.id \n" +
            "WHERE ac.activo = true AND ac.familiaId = :familiaId AND a.activo = true AND a.articuloParaVenta = true AND a.programaIdiomaId IS NULL \n" +
            "")
    List<ArticuloCategoriaCardProjection> findProjectedCardAllByFamiliaIdAndVentaTrue(@Param("familiaId") Integer familiaId);

    @Modifying
    @Query(value = "UPDATE ArticulosCategorias SET ACAT_Activo = :activo WHERE ACAT_CategoriaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
