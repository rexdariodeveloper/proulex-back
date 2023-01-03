package com.pixvs.main.dao;

import com.pixvs.main.models.BecaAlumnoRH;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BecaAlumnoRHDao extends CrudRepository<BecaAlumnoRH, String> {

    @Query(value =
            "SELECT *\n" +
            "FROM BECAS_ALUMNOS_RH\n" +
            "WHERE SUBSTRING(NOMBREDESCUENTOUDG, 0, CHARINDEX(' ', NOMBREDESCUENTOUDG)) IN (:entidadesId)\n" +
            "      AND FECHAALTABECAUDG BETWEEN :fechaInicio AND :fechaFin\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<BecaAlumnoRH> findListadoBecaAlumnoRH(@Param("entidadesId") List<String> entidadesId,
                                               @Param("fechaInicio") String fechaInicio,
                                               @Param("fechaFin") String fechaFin);
}