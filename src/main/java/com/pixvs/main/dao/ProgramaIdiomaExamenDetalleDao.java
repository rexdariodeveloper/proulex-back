package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaIdiomaExamenDetalle;
import com.pixvs.main.models.ProgramaIdiomaModalidad;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleEditarProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaExamenDetalle.ProgramaIdiomaExamenDetalleListadoProjection;
import com.pixvs.main.models.projections.ProgramaIdiomaModalidad.ProgramaIdiomaModalidadEditarProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaIdiomaExamenDetalleDao extends CrudRepository<ProgramaIdiomaExamenDetalle, String> {
    ProgramaIdiomaExamenDetalle findProjectionById(Integer id);
    @Query(nativeQuery = true, value = "Select DISTINCT\n" +
            "PROGIED_ProgramaIdiomaExamenDetalleId as id,\n" +
            "PAAE_Actividad as actividad,\n" +
            "CMM_Valor as testFormat,\n" +
            "PROGIED_Puntaje as score,\n" +
            "GETDATE() as fechaInicio,\n" +
            "CASE WHEN ISNUMERIC(PROGIEM_Dias) = 1 THEN PROGIEM_Dias ELSE 0 END as dias,\n" +
            "PROGIED_Time as time\n" +
            "from ProgramasIdiomasExamenesDetalles\n" +
            "INNER JOIN ProgramasIdiomasExamenes ON PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId AND PROGIE_Activo = 1\n" +
            "INNER JOIN ProgramasIdiomasNiveles ON PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId AND PROGIN_Activo = 1\n" +
            "INNER JOIN ProgramasIdiomasExamenesModalidades ON PROGIEM_PROGIED_ProgramaIdiomaExamenDetalleId = PROGIED_ProgramaIdiomaExamenDetalleId\n" +
            "INNER JOIN PAActividadesEvaluacion ON PAAE_ActividadEvaluacionId = PROGIED_PAAE_ActividadEvaluacionId\n" +
            "INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGIED_CMM_TestId\n" +
            "INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGIEM_PAMOD_ModalidadId\n"+
            "WHERE PROGIN_PROGI_ProgramaIdiomaId=:idProgramaIdioma AND (:idModalidad IS NULL OR PAMOD_ModalidadId=:idModalidad) ORDER BY id,dias")
    List<ProgramaIdiomaExamenDetalleListadoProjection> getTestByProgramaId(@Param("idProgramaIdioma") Integer idProgramaIdioma, @Param("idModalidad") Integer idModalidad);

}