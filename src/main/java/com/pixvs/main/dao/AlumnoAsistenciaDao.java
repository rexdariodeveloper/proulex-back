package com.pixvs.main.dao;

import com.pixvs.main.models.projections.AlumnoAsistencia.AlumnoAsistenciaEditarProjection;
import com.pixvs.main.models.projections.AlumnoAsistencia.AlumnoAsistenciaResumenProjection;
import com.pixvs.main.models.projections.AlumnoAsistencia.ReporteAsistenciasProjection;
import com.pixvs.main.models.projections.AlumnoAsistencia.ReporteCalificacionesProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.pixvs.main.models.AlumnoAsistencia;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface AlumnoAsistenciaDao extends CrudRepository<AlumnoAsistencia, String> {
    List<AlumnoAsistenciaEditarProjection> findAllEditarProjetedByGrupoId(@Param("grupoId") Integer grupoId);
    List<AlumnoAsistenciaEditarProjection> findAllByGrupoIdAndAlumnoId(Integer grupoId, Integer alumnoId);
    AlumnoAsistencia findById(Integer id);

    AlumnoAsistencia findByAlumnoIdAndGrupoIdAndFecha(@Param("alumnoId") Integer alumnoId,
                                                               @Param("grupoId") Integer grupoId,
                                                               @Param("fecha") Date fecha);

    @Query(nativeQuery = true, value = "select distinct fecha from ( " +
            "select EMPDNL_Fecha fecha, DATEPART(YEAR, EMPDNL_Fecha) anio from EmpresaDiasNoLaborales " +
            "union all " +
            "select CAST(CONCAT(DATEPART(YEAR,GETDATE()),'-',FORMAT(EMPDNLF_Mes,'00'),'-',FORMAT(EMPDNLF_Dia,'00')) as date) fecha, DATEPART(YEAR, GETDATE()) anio from EmpresaDiasNoLaboralesFijos " +
            ") t where t.anio = :anio")
    List<Date> findFechasInhabilesByAnio(@Param("anio") String anio);

    @Query(nativeQuery = true, value =
            "SELECT * FROM [dbo].[VW_RPT_ASISTENCIAS] " +
            "WHERE sedeId IN(:sedesId)\n" +
            "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
            "       AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas((nombre + ' ' + primerApellido + ISNULL(' ' + segundoApellido, '')), :alumno) = 1 THEN 1 ELSE CASE WHEN codigo = :alumno THEN 1 ELSE CASE WHEN codigoAlumno = :alumno THEN 1 ELSE 0 END END END END = 1\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))")
    List<ReporteAsistenciasProjection> getReporteAsistenciasListado(@Param("sedesId") List<Integer> sedesId,
                                                                    @Param("fechas") List<String> fechas,
                                                                    @Param("alumno") String alumno,
                                                                    @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM [dbo].[VW_RPT_ASISTENCIAS] WHERE " +
            "(:sedeId IS NULL OR sedeId = :sedeId) " +
            "AND (:tienePlanteles IS NULL OR plantelId IN (:planteles)) " +
            "AND (:paId IS NULL OR paId = :paId) " +
            "AND (:cicloId IS NULL OR cicloId = :cicloId) " +
            "AND (:modalidadId IS NULL OR modalidadId = :modalidadId) " +
            "AND (:fecha IS NULL OR fechaInicio = CAST(:fecha AS DATE)) " +
            "AND (:codigo IS NULL OR codigoGrupo = :codigo)")
    List<ReporteAsistenciasProjection> getReporteAsistenciasDetalle(@Param("sedeId") Integer sedeId, @Param("tienePlanteles") Boolean tienePlanteles,
                                                                    @Param("planteles") List<Integer> planteles, @Param("paId") Integer paId,
                                                                    @Param("cicloId") Integer cicloId, @Param("modalidadId") Integer modalidadId,
                                                                    @Param("fecha") String fecha, @Param("codigo") String codigo);

    @Query(nativeQuery = true, value = "" +
            "SELECT TOP 1 " +
            "[AA_ALU_AlumnoId] alumnoId,  " +
            "[AA_PROGRU_GrupoId] grupoId, " +
            "SUM(CASE WHEN [AA_CMM_TipoAsistenciaId] IN (2000550, 2000552) THEN 1 ELSE 0 END) asistencias, " +
            "SUM(CASE WHEN [AA_CMM_TipoAsistenciaId] = 2000551 THEN 1 ELSE 0 END) faltas, " +
            "SUM(CASE WHEN [AA_CMM_TipoAsistenciaId] = 2000553 THEN [AA_MinutosRetardo] ELSE 0 END) retardos " +
            "FROM  " +
            "[dbo].[AlumnosAsistencias] " +
            "WHERE " +
            "[AA_ALU_AlumnoId] = :alumnoId " +
            "AND [AA_PROGRU_GrupoId] = :grupoId " +
            "GROUP BY " +
            "[AA_ALU_AlumnoId], [AA_PROGRU_GrupoId]")
    AlumnoAsistenciaResumenProjection getProjectedResumenByAlumnoIdAndGrupoId(@Param("alumnoId") Integer alumnoId, @Param("grupoId") Integer grupoId);

    @Query(nativeQuery = true, value =
            "SELECT * FROM [dbo].[VW_RPT_ReporteCalificaciones] " +
                    "WHERE sedeId IN(:sedesId)\n" +
                    "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                    "       AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas((nombre + ' ' + primerApellido + ISNULL(' ' + segundoApellido, '')), :alumno) = 1 THEN 1 ELSE 0 END END = 1\n" +
                    "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))")
    List<ReporteCalificacionesProjection> getReporteCalificacionesListado(@Param("sedesId") List<Integer> sedesId,
                                                                          @Param("fechas") List<String> fechas,
                                                                          @Param("alumno") String alumno,
                                                                          @Param("modalidadesId") List<Integer> modalidadesId);

    void deleteAllByAlumnoIdAndGrupoId(Integer alumnoId, Integer grupoId);
}
