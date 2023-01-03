package com.pixvs.main.dao;

import com.pixvs.main.models.AlumnoGrupo;
import com.pixvs.main.models.projections.AlumnoGrupo.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlumnoGrupoDao extends CrudRepository<AlumnoGrupo, String> {

    AlumnoGrupo findById(Integer id);

    @Query(value = "SELECT id, codigo, nombre, grupo_ as grupo, faltas, asistencias, estatus " +
            "FROM [dbo].[VW_RPT_INASISTENCIAS] " +
            "WHERE sedeId IN (:sedesId) " +
            "AND (:cicloId IS NULL OR cicloId = :cicloId) " +
            "AND (:paId IS NULL OR paId = :paId) " +
            "AND programaId IN (:programasId) " +
            "AND FORMAT(fechaInicio,'dd/MM/yyyy') = :fecha " +
            "AND (COALESCE(:estatusId, NULL) IS NULL OR estatusId IN (:estatusId))", nativeQuery = true)
    List<AlumnoGrupoListadoProjection> getReporteInasistenciasResumen(@Param("sedesId") List<Integer> sedesId,
                                                                      @Param("cicloId") Integer cicloId,
                                                                      @Param("paId") Integer paId,
                                                                      @Param("programasId") List<Integer> programasId,
                                                                      @Param("fecha") String fecha,
                                                                      @Param("estatusId") List<Integer> estatusId);

    @Query(value = "SELECT [dbo].[fn_getEstatusAlumno] (:alumnoId, :grupoId)", nativeQuery = true)
    Integer getEstatusAlumnoGrupo(@Param("alumnoId") Integer alumnoId, @Param("grupoId") Integer grupoId);

    AlumnoGrupo findByAlumnoIdAndGrupoIdAndEstatusIdIsNotIn(Integer alumnoId, Integer grupoId, List<Integer> ids);

    AlumnoGrupo findByAlumnoIdAndGrupoId(Integer alumnoId, Integer grupoId);

    AlumnoGrupo findByAlumnoIdAndGrupoIdAndEstatusIdNot(Integer alumnoId, Integer grupoId, Integer estatusId);

    List<AlumnoGrupo> findAllByAlumnoIdAndGrupoIdAndEstatusIdNotIn(Integer alumnoId, Integer grupoId, List<Integer> estatus);

    List<AlumnoGrupo> findAllByGrupoIdAndEstatusIdNotIn(Integer grupoId, List<Integer> estatus);

    List<AlumnoGrupoCalificacionesProjection> findAllByGrupoIdAndEstatusIdIn(Integer grupoId, List<Integer> estatus);

    @Query(value = "select  " +
            "   ALUG_ALU_AlumnoId alumnoId, " +
            "   ALU_Codigo codigo, " +
            "   ALU_CodigoAlumnoUDG codigoUDG, " +
            "   ALU_PrimerApellido primerApellido, " +
            "   ALU_SegundoApellido segundoApellido, " +
            "   ALU_Nombre nombre, " +
            "   ALUG_CalificacionFinal calificacion, " +
            "   ALUG_CMM_EstatusId estatusId, " +
            "   CMM_Valor estatus " +
            "from AlumnosGrupos " +
            "   inner join Alumnos on ALUG_ALU_AlumnoId = ALU_AlumnoId " +
            "   inner join Inscripciones on ALUG_INS_InscripcionId = INS_InscripcionId " +
            "   inner join ControlesMaestrosMultiples on ALUG_CMM_EstatusId = CMM_ControlId " +
            "where " +
            "   INS_CMM_EstatusId NOT IN (2000512, 2000513) " +
            "   and ALUG_PROGRU_GrupoId = :grupoId " +
            "order by " +
            "   ALU_PrimerApellido, ALU_SegundoApellido, ALU_Nombre", nativeQuery = true)
    List<AlumnoListadoCapturaProjection> findAllListadoCapturaByGrupoId(@Param("grupoId") Integer grupoId);

    AlumnoGrupo findAllByInscripcionId(@Param("inscripcionId") Integer inscripcionId);

    @Query(value = "SELECT * FROM fn_getAlumnoGrupoBoleta (:alumnoGrupoId)", nativeQuery = true)
    AlumnoGrupoBoletaProjection getAlumnoGrupoBoleta(@Param("alumnoGrupoId") Integer alumnoGrupoId);


    @Query(value = "select * from dbo.VW_CLE_ALUMNOSGRUPOS_SYNC WHERE usuarioCleId is null or fechaUltimaActualizacionCle is null or alumnoGrupoEstatusId in (2000674,2000676,2000677)", nativeQuery = true)
    List<AlumnoGrupoCleProjection> getAlumnoGrupoSync();

    @Modifying
    @Query(value = "UPDATE AlumnosGrupos\n" +
            "set ALUG_CLE_FechaUltimaActualizacion = getDate() \n" +
            "WHERE ALUG_AlumnoGrupoId = :alumnoGrupoId ",
            nativeQuery = true)
    int actualizarAlumnoGrupoCle(@Param("alumnoGrupoId") Integer alumnoGrupoId);

    @Modifying
    @Query(value = "UPDATE AlumnosGrupos\n" +
            "set ALUG_CLE_FechaUltimaActualizacion = null \n" +
            "WHERE ALUG_AlumnoGrupoId = :alumnoGrupoId ",
            nativeQuery = true)
    int borrarAlumnoGrupoCle(@Param("alumnoGrupoId") Integer alumnoGrupoId);

    @Modifying
    @Query(value = "UPDATE Alumnos\n" +
            "SET ALU_CLE_UsuarioId = :alumnoCleId \n" +
            "WHERE ALU_AlumnoId = :alumnoId ",
            nativeQuery = true)
    int actualizarAlumnoCle(@Param("alumnoId") Integer alumnoId, @Param("alumnoCleId") Integer alumnoCleId);

}
