package com.pixvs.main.dao;

import com.pixvs.main.models.ArticuloFamilia;
import com.pixvs.main.models.mapeos.ArticulosTipos;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaCardProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaComboProjection;
import com.pixvs.main.models.projections.ArticuloFamilia.ArticuloFamiliaListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.OrderBy;
import java.util.List;

public interface ArticuloFamiliaDao extends CrudRepository<ArticuloFamilia, String> {

    ArticuloFamilia findById(Integer id);

    List<ArticuloFamiliaListadoProjection> findProjectedListadoAllByActivoTrue();
    List<ArticuloFamiliaComboProjection> findProjectedComboAllByActivoTrue();

    @Query("" +
            "SELECT DISTINCT \n" +
            "   af.id AS id, \n" +
            "   af.nombre AS nombre, \n" +
            "   af.archivoId AS imagenId \n" +
            "FROM ArticuloFamilia af \n" +
            "INNER JOIN Articulo a ON a.familiaId = af.id \n" +
            "WHERE af.activo = true AND a.activo = true AND a.articuloParaVenta = true AND a.programaIdiomaId IS NULL \n" +
            "")
    List<ArticuloFamiliaCardProjection> findProjectedCardAllByVentaTrue();

    @Modifying
    @Query(value = "UPDATE ArticulosFamilias SET AFAM_Activo = :activo WHERE AFAM_FamiliaId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

}
