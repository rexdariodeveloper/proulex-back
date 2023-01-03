package com.pixvs.main.dao;

import com.pixvs.main.models.EntidadBeca;
import com.pixvs.main.models.projections.EntidadBeca.EntidadBecaComboProjection;
import com.pixvs.main.models.projections.EntidadBeca.EntidadBecaListadoProjection;
import com.pixvs.main.models.projections.EntidadBeca.RHEntidadBecaComboProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntidadBecaDao extends CrudRepository<EntidadBeca, String> {

    List<EntidadBecaComboProjection> findProjectedComboAllByActivoTrueOrderByNombre();

    List<EntidadBecaListadoProjection> findAllByActivoIsTrueOrderByCodigo();

    boolean existsByIdNotAndCodigoAndActivoIsTrue(Integer id, String codigo);

    @Modifying
    @Query(value = "UPDATE EntidadesBecas SET ENBE_Activo = :activo WHERE ENBE_EntidadBecaId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(value =
            "SELECT ROW_NUMBER() OVER(ORDER BY entidad) AS id,\n" +
            "       entidad\n" +
            "FROM\n" +
            "(\n" +
            "    SELECT DISTINCT\n" +
            "           SUBSTRING(NOMBREDESCUENTOUDG, 0, CHARINDEX(' ', NOMBREDESCUENTOUDG)) AS entidad\n" +
            "    FROM BECAS_ALUMNOS_RH\n" +
            ") AS entidades\n" +
            "ORDER BY entidad\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<RHEntidadBecaComboProjection> findListadoRHEntidadBecaProjected();
}