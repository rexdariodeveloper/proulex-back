package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupoProfesor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Angel Daniel Hernández Silva on 23/11/2021.
 */
public interface ProgramaGrupoProfesorDao extends CrudRepository<ProgramaGrupoProfesor, String> {

    // Modelo
    ProgramaGrupoProfesor findById(Integer id);
    List<ProgramaGrupoProfesor> findAllByGrupoIdAndActivoTrue(Integer grupoId);
    List<ProgramaGrupoProfesor> findAllByGrupoIdAndFechaInicioGreaterThanEqualAndActivoTrue(Integer grupoId, Date fechaInicio);

    // Integer
    @Query(nativeQuery = true, value = "" +
            "SELECT \n" +
            "   PROGRUP_ProgramaGrupoProfesorId \n" +
            "FROM ProgramasGruposProfesores \n" +
            "WHERE \n" +
            "   PROGRUP_PROGRU_GrupoId = :grupoId \n" +
            "   AND PROGRUP_EMP_EmpleadoId = :empleadoId \n" +
            "   AND PROGRUP_Activo = 1 \n" +
            "ORDER BY PROGRUP_FechaInicio DESC \n" +
            "OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY OPTION(RECOMPILE) \n" +
            "")
    Integer getIdByGrupoIdAndEmpleadoIdAndActivoTrueAndActual(@Param("grupoId") Integer grupoId, @Param("empleadoId") Integer empleadoId);

}
