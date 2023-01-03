package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupoIncompany;
import com.pixvs.main.models.ProgramaGrupoIncompanyCriterioEvaluacion;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompany.ProgramaGrupoIncompanyListadoProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion.ProgramaGrupoIncompanyCriterioEvaluacionEditarProjection;
import com.pixvs.main.models.projections.ProgramaGrupoIncompanyCriterioEvaluacion.ProgramaGrupoIncompanyCriterioEvaluacionListadoEditarProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaGrupoIncompanyCriterioEvaluacionDao extends CrudRepository<ProgramaGrupoIncompanyCriterioEvaluacion, String> {

    @Query(value = "" +
            "SELECT DISTINCT 0 as id, \n" +
            "0 as incompanyGrupoId, \n" +
            "PAAE as actividadEvaluacion, \n" +
            "PAMOD as modalidad, \n" +
            "CMM as testFormat, \n" +
            "PROGIE.fechaModificacion as fechaAplica, \n" +
            "PROGIED.puntaje as score, \n" +
            "PROGIED.time as time,\n" +
            "CASE WHEN length(PROGIEM.dias) = 1 THEN PROGIEM.dias ELSE 0 END as dias, \n" +
            "true as activo, \n" +
            "PROGIE.orden AS orden \n" +
            "FROM ProgramaIdiomaExamenDetalle PROGIED \n" +
            "INNER JOIN ProgramaIdiomaExamen PROGIE on PROGIE.id = PROGIED.programaIdiomaExamenId \n" +
            "INNER JOIN ProgramaIdiomaNivel PROGIN on PROGIN.id = PROGIE.programaIdiomaNivelId \n" +
            "INNER JOIN ProgramaIdioma PROGI on PROGI.id = PROGIN.programaIdiomaId \n" +
            "INNER JOIN PAActividadEvaluacion PAAE on PAAE.id = PROGIED.actividadEvaluacionId \n" +
            "INNER JOIN ProgramaIdiomaExamenModalidad PROGIEM on PROGIEM.examenDetalleId = PROGIED.id \n" +
            "INNER JOIN PAModalidad PAMOD on PAMOD.id = PROGIEM.modalidadlId \n" +
            "INNER JOIN ControlMaestroMultiple CMM on CMM.id = PROGIED.testId \n" +
            "WHERE PROGI.id = :idProgramaIdioma AND (:idModalidad IS NULL OR PAMOD.id = :idModalidad) AND (:nivel BETWEEN PROGIN.nivelInicial AND PROGIN.nivelFinal) AND PROGIED.activo = true AND PROGIE.activo=true \n" +
            "ORDER BY PROGIE.orden ASC")
    List<ProgramaGrupoIncompanyCriterioEvaluacionListadoEditarProjection> getTestByProgramaId(@Param("idProgramaIdioma") Integer idProgramaIdioma,@Param("idModalidad") Integer idModalidad,@Param("nivel") Integer nivel);


}