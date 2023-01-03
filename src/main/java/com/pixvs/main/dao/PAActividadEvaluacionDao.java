package com.pixvs.main.dao;

import com.pixvs.main.models.PAActividadEvaluacion;
import com.pixvs.main.models.PACiclo;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionCapturaProjection;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionComboProjection;
import com.pixvs.main.models.projections.PAActividadEvaluacion.PAActividadEvaluacionListadoProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloComboProjection;
import com.pixvs.main.models.projections.PACiclos.PACicloListadoProjection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PAActividadEvaluacionDao extends CrudRepository<PAActividadEvaluacion, String> {

    PAActividadEvaluacion findById(Integer id);

    List<PAActividadEvaluacionListadoProjection> findProjectedListadoAllByActivoTrue();
    List<PAActividadEvaluacionComboProjection> findProjectedComboAllByActivoTrue();

    @Modifying
    @Query(value = "UPDATE PAActividadesEvaluacion SET PAAE_Activo = :activo WHERE PAAE_ActividadEvaluacionId = :id", nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);

    @Query(nativeQuery = true, value = "select " +
            "PROGRU_GrupoId grupoId, " +
            "PROGIED_ProgramaIdiomaExamenDetalleId detalleId, " +
            "PROGRU_Nivel nivel, " +
            "CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) grupo, " +
            "CONCAT(FORMAT(CAST(PAMODH_HoraInicio as datetime),'hh:mm'),' - ', FORMAT(CAST(PAMODH_HoraFin as datetime),'hh:mm')) horario, " +
            "PAAE_Actividad actividad, " +
            "(PROGIE_Porcentaje / CAST(100 as decimal(28,10)) / puntajeTotal) puntos " +
            "from ProgramasGrupos inner join PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId " +
            "inner join PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId " +
            "inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId " +
            "inner join ControlesMaestrosMultiples on PROGI_CMM_Idioma = CMM_ControlId " +
            "inner join Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId " +
            "inner join ProgramasIdiomasNiveles on PROGRU_Nivel between PROGIN_NivelInicial and PROGIN_NivelFinal and PROGIN_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId " +
            "inner join ProgramasIdiomasExamenes on PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId " +
            "inner join ( " +
            "select PROGIED_PROGIE_ProgramaIdiomaExamenId examenId, SUM(PROGIED_Puntaje) puntajeTotal " +
            "from ProgramasIdiomasExamenesDetalles group by PROGIED_PROGIE_ProgramaIdiomaExamenId " +
            ") t on PROGIE_ProgramaIdiomaExamenId = t.examenId " +
            "inner join ProgramasIdiomasExamenesDetalles on PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId " +
            "inner join PAActividadesEvaluacion on PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId " +
            "where PROGRU_GrupoId = :grupoId order by detalleId")
    List<PAActividadEvaluacionCapturaProjection> findAllProjectedCapturaByGrupoId(@Param("grupoId") Integer grupoId);
}
