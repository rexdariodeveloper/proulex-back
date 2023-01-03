package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.main.models.ProgramaGrupoListadoClase;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupo.ProgramaGrupoListadoProjection;
import com.pixvs.main.models.projections.ProgramaGrupoListadoClase.ProgramaGrupoListadoClaseEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaGrupoListadoClaseDao extends CrudRepository<ProgramaGrupoListadoClase, String> {

    // Modelo
    ProgramaGrupoListadoClase findById(Integer id);
    List<ProgramaGrupoListadoClase> findAllByIdIn(List<Integer> ids);

    // Integer
    @Query(nativeQuery = true, value = "" +
            "SELECT DISTINCT PROGRULC_ProgramaGrupoListadoClaseId \n" +
            "FROM ProgramasGruposListadoClases \n" +
            "INNER JOIN ProgramasGrupos ON PROGRULC_PROGRU_GrupoId = PROGRU_GrupoId \n" +
            "INNER JOIN VW_ProgramasGruposProfesores \n" +
            "   ON grupoId = PROGRU_GrupoId \n" +
            "   AND fechaInicio <= PROGRULC_Fecha \n" +
            "   AND fechaFin > = PROGRULC_Fecha \n" +
            "WHERE \n" +
            "   PROGRULC_FechaDeduccion IS NULL \n" +
            "   AND grupoId = :grupoId \n" +
            "   AND PROGRULC_CMM_FormaPagoId IN (2000520,2000523) \n" +
            "   AND empleadoId = :profesorTitularId \n" +
            "   AND PROGRULC_Fecha >= :fechaInicioPeriodo \n" +
            "   AND PROGRULC_Fecha <= :fechaFinPeriodo \n" +
            "")
    List<Integer> findAllByProfesorTitularIdAndGrupoIdAndPeriodoAndFechaDeduccionIsNull(@Param("profesorTitularId") Integer profesorTitularId, @Param("grupoId") Integer grupoId, @Param("fechaInicioPeriodo") Date fechaInicioPeriodo, @Param("fechaFinPeriodo") Date fechaFinPeriodo);

    void deleteById(Integer id);

}