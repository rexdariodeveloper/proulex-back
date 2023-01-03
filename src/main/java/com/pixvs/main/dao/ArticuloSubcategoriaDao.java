package com.pixvs.main.dao;

import com.pixvs.main.models.ArticuloSubcategoria;
import com.pixvs.main.models.mapeos.ArticulosTipos;
import com.pixvs.main.models.projections.ArticuloCategoria.ArticuloCategoriaCardProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaCardProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaComboProjection;
import com.pixvs.main.models.projections.ArticuloSubcategoria.ArticuloSubcategoriaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloSubcategoriaDao extends CrudRepository<ArticuloSubcategoria, String> {

    ArticuloSubcategoria findById(Integer id);

    List<ArticuloSubcategoriaListadoProjection> findProjectedListadoAllByActivoTrue();

    List<ArticuloSubcategoriaComboProjection> findProjectedComboAllByActivoTrue();
    List<ArticuloSubcategoriaComboProjection> findProjectedComboAllByCategoriaIdAndActivoTrue(Integer categoriaId);

    @Query("" +
            "SELECT DISTINCT \n" +
            "   asub.id AS id, \n" +
            "   asub.nombre AS nombre, \n" +
            "   asub.archivoId AS imagenId \n" +
            "FROM ArticuloSubcategoria asub \n" +
            "INNER JOIN Articulo a ON a.categoriaId = asub.id \n" +
            "WHERE asub.activo = true AND asub.categoriaId = :categoriaId AND a.activo = true AND a.articuloParaVenta = true AND a.programaIdiomaId IS NULL \n" +
            "")
    List<ArticuloSubcategoriaCardProjection> findProjectedCardAllByCategoriaIdAndVentaTrue(@Param("categoriaId") Integer categoriaId);

    @Modifying
    @Query(value = "UPDATE ArticulosSubcategorias SET ASC_Activo = :activo WHERE ASC_SubcategoriaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
