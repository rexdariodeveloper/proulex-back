package com.pixvs.main.dao;

import com.pixvs.main.models.ProgramaGrupo;
import com.pixvs.main.models.mapeos.ControlesMaestrosMultiples.*;
import com.pixvs.main.models.projections.ProgramaGrupo.*;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * Created by David Arroyo Sánchez on 04/11/2020.
 */
public interface ProgramaGrupoDao extends CrudRepository<ProgramaGrupo, String> {

    //List<ProgramaGrupoListadoProjection> findAllBy();
    List<ProgramaGrupoFechaProjection> findDistinctBySucursalId(Integer id);
    List<ProgramaGrupoFechaProjection> findAllByPaCicloId(Integer id);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS] WHERE estatus=:estatus AND (:allSucursales = 1 OR sucursal IN(:sucursales)) ORDER BY codigo")
    List<ProgramaGrupoListadoProjection> findAllByView(@Param("estatus") String estatus,@Param("sucursales") List<String> sucursales,@Param("allSucursales") Integer allSucursales);
    List<ProgramaGrupo> findAllByProgramaIdiomaIdAndEstatusId(Integer programaIdiomaId, Integer estatusId);
    List<ProgramaGrupo> findAllByProgramaIdiomaIdAndPaModalidadIdAndModalidadHorarioIdAndCategoriaProfesorAndEstatusId(Integer programaIdiomaId, Integer modalidadId, Integer horarioId, String categoriaProfesor, Integer estatusId);
    List<ProgramaGrupo> findAllByEmpleadoIdAndEstatusId(Integer empleadoId, Integer estatusId);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_GRUPOS] WHERE id IN :ids AND fechaFinInscripcionesBecas >= CAST(GETDATE() AS date) ORDER BY fechaFinInscripcionesBecas")
    List<ProgramaGrupoComboProjection> findCombosByViewAndIdIn(@Param("ids") List<Integer> ids);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_COMBO_GRUPOS] WHERE id IN :ids AND fechaFinInscripcionesBecas >= CAST(GETDATE() AS date) AND cupoDisponible > 0 ORDER BY fechaFinInscripcionesBecas")
    List<ProgramaGrupoComboProjection> findCombosByViewAndIdInAndCupoDisponible(@Param("ids") List<Integer> ids);
    @Query(nativeQuery = true, value = "SELECT estatus FROM [dbo].[VW_LISTADO_GRUPOS] WHERE id= :id")
    String getEstatusGrupoById(@Param("id") Integer id);
    @Query(value =
            "SELECT * from [dbo].[VW_LISTADO_GRUPOS]\n" +
            "WHERE (COALESCE(:sedesId, 0) = 0 OR sucursalId IN(:sedesId))\n" +
            "       AND (COALESCE(:fechas, '') = '' OR FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas))\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "       AND (COALESCE(:tiposGrupoIds, 0) = 0 OR tipoGrupoId IN (:tiposGrupoIds))\n" +
            "       AND (:estatus IS NULL OR estatus=:estatus)\n" +
            "       AND (COALESCE(:ids, 0) = 0 OR id IN (:ids))\n" +
            "ORDER BY codigo\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findAllByViewFiltros(@Param("sedesId") List<Integer> sedesId,
                                                              @Param("fechas") List<String> fechas,
                                                              @Param("modalidadesId") List<Integer> modalidadesId,
                                                              @Param("tiposGrupoIds") List<Integer> tiposGrupoIds,
                                                              @Param("estatus") String estatus,
                                                              @Param("ids") List<Integer> ids);

    @Query(value =
            "SELECT " +
            "id, codigoGrupo, grupoNombre, plantel, fechaInicio, fechaFin, nivel, horario, cupo, totalInscritos, profesor " +
            "FROM [dbo].[VW_RPT_ReporteGrupos] " +
            "WHERE sedeId IN(:sedesId)\n" +
            "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "ORDER BY codigoGrupo, fechaInicio\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ProgramaGrupoReporteProjection> getReporteGrupos(@Param("sedesId") List<Integer> sedesId,
                                                          @Param("fechas") List<String> fechas,
                                                          @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT SUM(totalAlumnos) AS totalAlumnos,\n" +
            "       SUM(totalGrupos) AS totalGrupos,\n" +
            "       plantelGrupo,\n" +
            "       modalidad,\n" +
            "       horario,\n" +
            "       nivel\n" +
            "FROM\n" +
            "(\n" +
            "    SELECT DISTINCT\n" +
            "           COUNT(alumnoId) OVER(PARTITION BY grupoId, plantelGrupo, modalidad, horario, nivel) AS totalAlumnos,\n" +
            "           COUNT(grupoId) OVER(PARTITION BY alumnoId, plantelGrupo, modalidad, horario, nivel) AS totalGrupos,\n" +
            "           plantelGrupo,\n" +
            "           modalidad,\n" +
            "           horario,\n" +
            "           nivel,\n" +
            "           grupoId\n" +
            "    FROM VW_RPT_ReporteGeneralGruposAlumnos\n" +
            "    WHERE sedeGrupoId = :sedesId\n" +
            "       AND (COALESCE(:plantelesId, 0) = 0 OR plantelGrupoId IN (:plantelesId)) \n" +
            "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas) \n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId)) \n" +
            ") AS todo\n" +
            "GROUP BY nivel,\n" +
            "         horario,\n" +
            "         modalidad,\n" +
            "         plantelGrupo\n" +
            "ORDER BY nivel,\n" +
            "         horario,\n" +
            "         modalidad,\n" +
            "         plantelGrupo\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ReporteGeneralGrupoProjection> getReporteGeneralGrupos(@Param("sedesId") Integer sedesId,
                                                                 @Param("plantelesId") List<Integer> plantelesId,
                                                                 @Param("fechas") List<String> fechas,
                                                                 @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT COUNT(alumnoId) AS totalAlumnos,\n" +
            "       plantelAlumno,\n" +
            "       nivel\n" +
            "FROM VW_RPT_ReporteGeneralGruposAlumnos\n" +
            "WHERE sedeAlumnoId = :sedesId\n" +
            "    AND (COALESCE(:plantelesId, 0) = 0 OR plantelAlumnoId IN (:plantelesId)) \n" +
            "    AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas) \n" +
            "    AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "GROUP BY plantelAlumno,\n" +
            "    nivel\n" +
            "ORDER BY nivel,\n" +
            "    plantelAlumno\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ReporteGeneralAlumnoProjection> getReporteGeneralAlumnos(@Param("sedesId") Integer sedesId,
                                                                @Param("plantelesId") List<Integer> plantelesId,
                                                                @Param("fechas") List<String> fechas,
                                                                @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT * FROM (\n" +
            "SELECT *, 'LOCAL' AS tipoInscripcion\n" +
            "FROM VW_RPT_ALUMNOS\n" +
            "WHERE sedeInscripcionId IN(:sedesId)\n" +
            "    AND (COALESCE(:plantelesId, 0) = 0 OR plantelInscripcionId IN(:plantelesId))\n" +
            "    AND 1 IN (:tiposInscripciones)\n" +
            "    AND sedeInscripcionId = sedeGrupoId\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT *, 'MULTISEDE EVÍA' AS tipoInscripcion\n" +
            "FROM VW_RPT_ALUMNOS\n" +
            "WHERE sedeInscripcionId IN(:sedesId)\n" +
            "    AND (COALESCE(:plantelesId, 0) = 0 OR plantelInscripcionId IN(:plantelesId))\n" +
            "    AND 2 IN (:tiposInscripciones)\n" +
            "    AND sedeInscripcionId != sedeGrupoId\n" +
            "\n" +
            "UNION ALL\n" +
            "\n" +
            "SELECT *, 'MULTISEDE RECIBE' AS tipoInscripcion\n" +
            "FROM VW_RPT_ALUMNOS\n" +
            "WHERE sedeGrupoId IN(:sedesId)\n" +
            "    AND (COALESCE(:plantelesId, 0) = 0 OR plantelGrupoId IN(:sedesId))\n" +
            "    AND 3 IN (:tiposInscripciones)\n" +
            "    AND sedeInscripcionId != sedeGrupoId\n" +
            ") AS todo\n" +
            "WHERE FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
            "    AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "    AND CASE WHEN :alumno IS NULL THEN 1 ELSE CASE WHEN dbo.fn_comparaCadenas(alumno, :alumno) = 1 THEN 1 ELSE 0 END END = 1\n" +
            "ORDER BY alumnoCodigo,\n" +
            "    grupo\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ReporteAlumnoProjection> getReporteAlumnos(@Param("sedesId") List<Integer> sedesId,
                                                    @Param("plantelesId") List<Integer> plantelesId,
                                                    @Param("fechas") List<String> fechas,
                                                    @Param("modalidadesId") List<Integer> modalidadesId,
                                                    @Param("alumno") String alumno,
                                                    @Param("tiposInscripciones") List<Integer> tiposInscripciones);

    @Query(value =
            "SELECT " +
            "id, codigoGrupo, grupoNombre, plantel, fechaInicio, fechaFin, nivel, horario, cupo, totalInscritos, profesor " +
            "FROM [dbo].[VW_RPT_ReporteGruposPCP] " +
            "WHERE sedeId IN(:sedesId)\n" +
            "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "       AND (COALESCE(:programasId, 0) = 0 OR programaId IN (:programasId))\n" +
            "       AND pcp = 1\n" +
            "ORDER BY codigoGrupo, fechaInicio\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ProgramaGrupoReportesPCPProjection> getReporteGrupos(@Param("sedesId") List<Integer> sedesId,
                                                              @Param("fechas") List<String> fechas,
                                                              @Param("modalidadesId") List<Integer> modalidadesId,
                                                              @Param("programasId") List<Integer> programasId);

    @Query(nativeQuery = true, value = "Select distinct \n" +
            "EMP_EmpleadoId as empleadoId,\n" +
            "CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as nombreEmpleado,\n" +
            "PROGRU_Codigo as codigoGrupo,\n" +
            "PAMODH_Horario as horario,\n" +
            "PROGRU_Nivel as nivel,\n" +
            "COALESCE(PROGRU_Aula,'No asignada') as aula\n" +
            "from ProgramasGrupos\n" +
            "INNER JOIN PAModalidadesHorarios on PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId\n" +
            "INNER JOIN Empleados on EMP_EmpleadoId = PROGRU_EMP_EmpleadoId\n" +
            "WHERE (:allEmpleados = 1 OR EMP_EmpleadoId IN(:idsEmpleados) )\n" +
            "AND (:sucursalId IS NULL OR PROGRU_SUC_SucursalId = :sucursalId)\n" +
            "AND (:modalidadId IS NULL OR PROGRU_PAMOD_ModalidadId=:modalidadId)\n" +
            "AND (:cicloId IS NULL OR PROGRU_PACIC_CicloId= :cicloId)\n" +
            "AND (:programacionId IS NULL OR PROGRU_PAC_ProgramacionAcademicaComercialId = :programacionId)\n" +
            "AND (:fechaInicio = '' OR PROGRU_FechaInicio >= :fechaInicio)\n" +
            "AND (:fechaFin = '' OR PROGRU_FechaFin <= :fechaFin)\n" +
            "AND PROGRU_CMM_EstatusId= 2000620")
    List<ProgramaGrupoReporteProfesorProjection> getReporteGruposCriterios(@Param("sucursalId") Integer sucursalId,
                                                          @Param("modalidadId") Integer modalidadId,
                                                          @Param("cicloId") Integer cicloId,
                                                          @Param("programacionId") Integer programacionId,
                                                          @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin,
                                                          @Param("allEmpleados") Integer allEmpleados,
                                                          @Param("idsEmpleados") List<Integer> idsEmpleados);

    @Query(nativeQuery = true, value = "" +
            "Select DISTINCT \n" +
            "	CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as nombreProfesor,\n" +
            "	EMP_CodigoEmpleado as codigoProfesor,\n" +
            "	PAPC_Categoria as categoria,\n" +
            "	CONCAT('$',MIN(PROGRU_SueldoProfesor)) as sueldoProfesor,\n" +
            "	CMM_Valor as idioma,\n" +
            "	SP_Nombre as plantel,\n" +
            "	fechaInicio as fechaInicio,\n" +
            "	fechaFin as fechaFin,\n" +
            "	PAMOD_Nombre as modalidad,\n" +
            "   (CASE WHEN PROGRU_CMM_TipoGrupoId = 2000392 THEN 2000391 ELSE PROGRU_CMM_TipoGrupoId END) as tipoGrupo,\n" +
            "	(dbo.fn_GruposContratos(PROGRU_SUC_SucursalId,PROGRU_PROGI_ProgramaIdiomaId,PROGRU_PACIC_CicloId,CMM_ControlId,PROGRU_PAMOD_ModalidadId,PROGRU_EMP_EmpleadoId,PROGRU_FechaInicio,PROGRU_SP_SucursalPlantelId, fechaInicio, fechaFin, (CASE WHEN PROGRU_CMM_TipoGrupoId = 2000392 THEN 2000391 ELSE PROGRU_CMM_TipoGrupoId END))) as grupos,\n" +
            "	PROGRU_PROGI_ProgramaIdiomaId as programaId,\n" +
            "	empleadoId as empleadoId,\n" +
            "	PROGRU_SUC_SucursalId as sucursalId,\n" +
            "	PROGRU_PAMOD_ModalidadId as modalidadId,\n" +
            "	PROGRU_PACIC_CicloId as cicloId,\n" +
            "	CMM_ControlId as idiomaCmm,\n" +
            "	PROGRU_SP_SucursalPlantelId as plantelId\n" +
            "FROM ProgramasGrupos\n" +
            "    INNER JOIN VW_ProgramasGruposProfesores ON grupoId = PROGRU_GrupoId\n" +
            "    INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId\n" +
            "    INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId\n" +
            "    INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId\n" +
            "    INNER JOIN Empleados ON EMP_EmpleadoId = empleadoId\n" +
            "    INNER JOIN ControlesMaestrosMultiples on CMM_ControlId = PROGI_CMM_Idioma\n" +
            "    LEFT JOIN EmpleadosCategorias on EMPCA_EMP_EmpleadoId = EMP_EmpleadoId AND EMPCA_Activo = 1\n" +
            "    LEFT JOIN SucursalesPlanteles ON SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId\n" +
            "    LEFT JOIN PACiclos ON PROGRU_PACIC_CicloId = PACIC_CicloId\n" +
            "    LEFT JOIN ProgramacionAcademicaComercial ON PROGRU_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId\n" +
            "    LEFT JOIN TabuladoresDetalles on TABD_PAPC_ProfesorCategoriaId = EMPCA_PAPC_ProfesorCategoriaId AND TABD_Activo = 1\n" +
            "    LEFT JOIN PAProfesoresCategorias on PAPC_ProfesorCategoriaId = TABD_PAPC_ProfesorCategoriaId\n" +
            "WHERE\n" +
            "	PROGRU_CMM_EstatusId IN (2000620, 2000621)\n" +
            "   AND (:allEmpleados = 1 OR EMP_EmpleadoId IN(:idsEmpleados) )\n" +
            "	AND (:allProgramasIdiomas = 1 OR PROGRU_PROGI_ProgramaIdiomaId IN(:programaIdiomaId) )--Varios\n" +
            "	AND PROGRU_FechaInicio = COALESCE(CAST(:fechaInicio AS DATE), fechaInicio) -- Uno\n" +
            "	AND (:sucursalId IS NULL OR PROGRU_SUC_SucursalId = :sucursalId )--Uno\n" +
            //"	AND (:cicloId IS NULL OR PROGRU_PACIC_CicloId = :cicloId) --Uno\n" +
            "	AND (:idiomaId IS NULL OR CMM_ControlId = :idiomaId) -- Uno\n" +
            "	AND (:modalidadId IS NULL OR PAMOD_ModalidadId = :modalidadId) --Uno\n" +
            "	AND YEAR(ProgramasGrupos.PROGRU_FechaInicio) = :anio \n" +
            "GROUP BY" +
            "   PROGRU_PROGI_ProgramaIdiomaId,PROGRU_SP_SucursalPlantelId,PROGRU_FechaInicio,EMP_EmpleadoId, PROGRU_CMM_TipoGrupoId, \n" +
            "   PROGRU_FechaFin,PROGRU_PAMOD_ModalidadId,EMP_Nombre,EMP_PrimerApellido,EMP_SegundoApellido,\n" +
            "   EMP_CodigoEmpleado,SP_Nombre,SP_Direccion,SP_Colonia,PAMOD_Nombre,\n" +
            "   CMM_Valor,PROGRU_SUC_SucursalId,CMM_ControlId,PAMOD_ModalidadId,PAPC_Categoria,PROGRU_EMP_EmpleadoId,\n" +
            "   PROGRU_PACIC_CicloId, PROGRU_SP_SucursalPlantelId, fechaInicio, fechaFin, empleadoId\n" +
            "\n")
    List<ProgramaGrupoContratosProjection> getContratosGrupos(@Param("sucursalId") Integer sucursalId,
                                                              @Param("allProgramasIdiomas") Integer allProgramasIdiomas,
                                                              @Param("modalidadId") Integer modalidadId,
                                                              @Param("programaIdiomaId") List<Integer> programaIdiomaId,
                                                              //@Param("cicloId") Integer cicloId,
                                                              @Param("idiomaId") Integer idiomaId,
                                                              @Param("fechaInicio") String fechaInicio,
                                                              @Param("allEmpleados") Integer allEmpleados,
                                                              @Param("idsEmpleados") List<Integer> idsEmpleados,
                                                              @Param("anio") Integer anio);




    ProgramaGrupoEditarProjection findProjectedEditarById(Integer id);

    //List<ProveedorEditarProjection> findProjectedEditarByRfc(String rfc);

    ProgramaGrupo findById(Integer id);
    List<ProgramaGrupo> findAllByIdIn(List<Integer> id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_CardProjection_ProgramasGrupos] WHERE programaIncompanyId IS NULL AND sucursalId = :sucursalId AND idiomaId = :idiomaId AND programaId = :programaId AND modalidadId = :modalidadId AND nivel = :nivel AND fechaFinInscripcionesBecas >= CAST(GETDATE() AS date) ORDER BY nombre, horario, numeroGrupo")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndNivelAndFechaFinBeca(@Param("sucursalId") Integer sucursalId, @Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId, @Param("modalidadId") Integer modalidadId, @Param("nivel") Integer nivel);
    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_CardProjection_ProgramasGrupos] WHERE programaIncompanyId IS NULL AND sucursalId <> :sucursalId AND idiomaId = :idiomaId AND programaId = :programaId AND modalidadId = :modalidadId AND esMultisede = 1 AND nivel = :nivel AND fechaFinInscripcionesBecas >= CAST(GETDATE() AS date) ORDER BY nombre, horario, numeroGrupo")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndMultisedeAndNivelAndFechaFinBeca(@Param("sucursalId") Integer sucursalId, @Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId, @Param("modalidadId") Integer modalidadId, @Param("nivel") Integer nivel);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CardProjection_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   programaIncompanyId IS NULL \n" +
            "   AND sucursalId = :sucursalId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "   AND programaId = :programaId \n" +
            "   AND modalidadId = :modalidadId \n" +
            "   AND tipoGrupoId = :tipoGrupoId \n" +
            "   AND nivel = :nivel \n" +
            "   AND fechaFinInscripciones >= CAST(GETDATE() AS date) \n" +
            "ORDER BY nombre, horario, numeroGrupo")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndTipoGrupoIdAndNivelAndFechaFin(
            @Param("sucursalId") Integer sucursalId,
            @Param("idiomaId") Integer idiomaId,
            @Param("programaId") Integer programaId,
            @Param("modalidadId") Integer modalidadId,
            @Param("tipoGrupoId") Integer tipoGrupoId,
            @Param("nivel") Integer nivel
    );
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CardProjection_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   programaIncompanyId IS NULL \n" +
            "   AND sucursalId = :sucursalId \n" +
            "   AND modalidadId = :modalidadId \n" +
            "   AND tipoId = :tipoId \n" +
            "   AND fechaFinInscripciones >= CAST(GETDATE() AS date) \n" +
            "ORDER BY nombre, horario, numeroGrupo")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndPaModalidadIdAndTipoIdAndFechaFin(
            @Param("sucursalId") Integer sucursalId,
            @Param("modalidadId") Integer modalidadId,
            @Param("tipoId") Integer tipoId
    );
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CardProjection_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   programaIncompanyId IS NULL \n" +
            "   AND sucursalId <> :sucursalId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "   AND programaId = :programaId \n" +
            "   AND modalidadId = :modalidadId \n" +
            "   AND tipoGrupoId = :tipoGrupoId \n" +
            "   AND nivel = :nivel\n" +
            "   AND esMultisede = 1 \n" +
            "   AND fechaFinInscripciones >= CAST(GETDATE() AS date) \n" +
            "ORDER BY nombre, horario, numeroGrupo")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndTipoGrupoIdAndNivelAndMultisedeAndFechaFin(
            @Param("sucursalId") Integer sucursalId,
            @Param("idiomaId") Integer idiomaId,
            @Param("programaId") Integer programaId,
            @Param("modalidadId") Integer modalidadId,
            @Param("tipoGrupoId") Integer tipoGrupoId,
            @Param("nivel") Integer nivel
    );
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CardProjection_ProgramasGrupos] \n" +
            "WHERE id = :id \n")
    ProgramaGrupoCardProjection findProjectedCardById(@Param("id") Integer id);
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CardProjection_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   programaIncompanyId IS NULL \n" +
            "   AND sucursalId = :sucursalId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "   AND programaId = :programaId \n" +
            "   AND modalidadId = :modalidadId \n" +
            "   AND horarioId = :horarioId \n" +
            "   AND nivel = :nivel \n" +
            "   AND fechaFinInscripciones >= CAST(GETDATE() AS date) \n" +
            "ORDER BY nombre, horario, numeroGrupo")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndHorarioIdAndNivelAndFechaFin(
            @Param("sucursalId") Integer sucursalId,
            @Param("idiomaId") Integer idiomaId,
            @Param("programaId") Integer programaId,
            @Param("modalidadId") Integer modalidadId,
            @Param("horarioId") Integer horarioId,
            @Param("nivel") Integer nivel
    );
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CardProjection_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   programaIncompanyId IS NULL \n" +
            "   AND sucursalId <> :sucursalId \n" +
            "   AND idiomaId = :idiomaId \n" +
            "   AND programaId = :programaId \n" +
            "   AND modalidadId = :modalidadId \n" +
            "   AND horarioId = :horarioId \n" +
            "   AND nivel = :nivel\n" +
            "   AND esMultisede = 1 \n" +
            "   AND fechaFinInscripciones >= CAST(GETDATE() AS date) \n" +
            "ORDER BY nombre, horario, numeroGrupo")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndIdiomaIdAndProgramaIdAndModalidadIdAndHorarioIdAndNivelAndMultisedeAndFechaFin(
            @Param("sucursalId") Integer sucursalId,
            @Param("idiomaId") Integer idiomaId,
            @Param("programaId") Integer programaId,
            @Param("modalidadId") Integer modalidadId,
            @Param("horarioId") Integer horarioId,
            @Param("nivel") Integer nivel
    );
    @Query(nativeQuery = true, value = "" +
            "SELECT * \n" +
            "FROM [dbo].[VW_CardProjection_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   sucursalId = :sucursalId \n" +
            "   AND programaIncompanyId IN :programaInCompanyIds \n" +
            "   AND fechaFinInscripciones >= CAST(GETDATE() AS date) \n" +
            "ORDER BY nombre, horario, numeroGrupo \n" +
            "OPTION(RECOMPILE) \n" +
            "")
    List<ProgramaGrupoCardProjection> findProjectedCardAllByActivoTrueAndSucursalIdAndProgramaInCompanyIdInAndFechaFin(
            @Param("sucursalId") Integer sucursalId,
            @Param("programaInCompanyIds") List<Integer> programaInCompanyIds
    );


    /*@Modifying
    @Query(value = "UPDATE ProgramasGrupos SET PROGRU_Activo = :activo WHERE PROGRU_GrupoId = :id",
            nativeQuery = true)
    int actualizarActivo(@Param("id") Integer id, @Param("activo") Boolean activo);*/

    /** Listados para captura de calificaciones y captura de asistencias **/
    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS_CAPTURA] WHERE profesorId = :profesorId", nativeQuery = true)
    List<ProgramaGrupoCapturaProjection> findProjectedCapturaByProfesorId(@Param("profesorId") Integer profesorId);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS_CAPTURA] WHERE suplenteId = :suplenteId", nativeQuery = true)
    List<ProgramaGrupoCapturaProjection> findProjectedCapturaBySuplenteId(@Param("suplenteId") Integer suplenteId);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS_CAPTURA] WHERE sucursalId IN (:sedes)", nativeQuery = true)
    List<ProgramaGrupoCapturaProjection> findProjectedCapturaBySedeIn(@Param("sedes") List<Integer> sedes);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS_CAPTURA] " +
            "WHERE " +
            "(sucursalId IN (:sedes) OR id IN (:grupoIds)) " +
            "AND estatusId <> 2000622 " +
            "AND (fechaFinTolerancia >= GETDATE() or estatusId = 2000620) " +
            "order by fechaInicio, codigo", nativeQuery = true)
    List<ProgramaGrupoCapturaProjection> findProjectedCapturaBySedeInOrIdIn(@Param("sedes") List<Integer> sedes, @Param("grupoIds") List<Integer> grupoIds);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS_CAPTURA] WHERE id = :grupoId", nativeQuery = true)
    ProgramaGrupoCapturaProjection findProjectedCapturaByGrupoId(@Param("grupoId") Integer grupoId);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS_CAPTURA] " +
            "WHERE " +
            "(sucursalId IN (:sedes) OR id IN (:grupoIds) OR profesorId = :profesorId OR suplenteId = :profesorId) " +
            "AND estatusId <> 2000622 " +
            "AND (fechaFinTolerancia >= GETDATE() or estatusId = 2000620)" +
            "ORDER BY fechaInicio, codigo", nativeQuery = true)
    List<ProgramaGrupoCapturaProjection> findProjectedCapturaByFiltros(@Param("profesorId") Integer profesorId, @Param("sedes") List<Integer> sedes, @Param("grupoIds") List<Integer> grupoIds);

    @Query(value =
            "SELECT * FROM VW_LISTADO_GRUPOS_CAPTURA\n" +
            "WHERE sucursalId IN(:sedesId)\n" +
            "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
            "       AND estatusId != " + CMM_PROGRU_Estatus.CANCELADO + "\n" +
            "ORDER BY fechaInicio, codigo\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<ProgramaGrupoCapturaProjection> findProjectedCapturaByFiltros(@Param("sedesId") List<Integer> sedesId,
                                                                       @Param("fechas") List<String> fechas,
                                                                       @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT v.* FROM VW_LISTADO_GRUPOS_CAPTURA v \n" +
                    " INNER JOIN Empleados e on e.EMP_EmpleadoId = profesorId \n" +
                    "WHERE sucursalId IN(:sedesId)\n" +
                    "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                    "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
                    "       AND estatusId != " + CMM_PROGRU_Estatus.CANCELADO + "\n" +
                    "       AND e.EMP_USU_UsuarioId = :usuarioId \n\n" +
                    "UNION\n\n" +
            "SELECT v.* FROM VW_LISTADO_GRUPOS_CAPTURA v\n" +
                    " INNER JOIN Empleados e on e.EMP_EmpleadoId = profesorId \n" +
                    "WHERE sucursalId IN(:sedesId)\n" +
                    "       AND FORMAT(fechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                    "       AND (COALESCE(:modalidadesId, 0) = 0 OR modalidadId IN (:modalidadesId))\n" +
                    "       AND estatusId != " + CMM_PROGRU_Estatus.CANCELADO + "\n" +
                    "       and :permiso = 1 \n" +
                    "       AND sucursalId in  ( select distinct ALM_SUC_SucursalId \n" +
                    "               from Usuarios \n" +
                    "               inner join UsuariosAlmacenes on USUA_USU_UsuarioId = USU_UsuarioId\n" +
                    "               inner join Almacenes on USUA_ALM_AlmacenId = ALM_AlmacenId\n" +
                    "               where USU_UsuarioId = e.EMP_USU_UsuarioId\n" +
                    "               )\n" +
                    "ORDER BY fechaInicio, codigo\n" +
                    "OPTION(RECOMPILE)" +
                    "", nativeQuery = true)
    List<ProgramaGrupoCapturaProjection> findProjectedCapturaByFiltrosConPermisos(@Param("sedesId") List<Integer> sedesId,
                                                                                  @Param("fechas") List<String> fechas,
                                                                                  @Param("modalidadesId") List<Integer> modalidadesId,
                                                                                  @Param("usuarioId") Integer usuarioId,
                                                                                  @Param("permiso") boolean permiso);

    @Query(value = "SELECT * FROM [dbo].[VW_GRUPO_CAPTURA] WHERE id = :grupoId ORDER BY fechaInicio", nativeQuery = true)
    ProgramaGrupoCapturaEditarProjection findProjectedCapturaEditarById(@Param("grupoId") Integer grupoId);

    @Query(value = "SELECT sucursalNombre, programacionCodigo, codigoGrupo, nivel, grupo, fechaInicio, fechaFin, " +
            "modalidadNombre, profesor, horario, evaluacionNombre, evaluacionPorcentaje, actividadNombre, actividadPuntaje, " +
            "alumno, alumnoId, grupoId, evaluacionId, evaluacionDetalleId FROM [dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES] " +
            "WHERE programaId = :programaId AND grupoId = :grupoId AND modalidadId = :modalidadId AND idiomaId = :idiomaId", nativeQuery = true)
    List<ProgramaGrupoActividadesProjection> getActividadesGrupo(@Param("programaId") Integer programaId,
                                                                @Param("grupoId") Integer grupoId,
                                                                @Param("modalidadId") Integer modalidadId,
                                                                @Param("idiomaId") Integer idiomaId);

    @Query(value = "SELECT TOP 1 sucursalNombre, programacionCodigo, programaNombre, codigoGrupo, nivel, grupo, fechaInicio, fechaFin, " +
            "modalidadNombre, profesor, horario FROM [dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES] " +
            "WHERE programaId = :programaId AND grupoId = :grupoId AND modalidadId = :modalidadId AND idiomaId = :idiomaId", nativeQuery = true)
    ProgramaGrupoDatosProjection getDatosGrupo(@Param("programaId") Integer programaId,
                                               @Param("grupoId") Integer grupoId,
                                               @Param("modalidadId") Integer modalidadId,
                                               @Param("idiomaId") Integer idiomaId);

    @Query(value = "SELECT evaluacionId id, evaluacionNombre nombre, MAX(evaluacionPorcentaje) porcentaje " +
            "FROM [dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES] " +
            "WHERE programaId = :programaId AND grupoId = :grupoId AND modalidadId = :modalidadId AND idiomaId = :idiomaId " +
            "GROUP BY evaluacionId, evaluacionNombre", nativeQuery = true)
    List<ProgramaGrupoMetricaProjection> getGrupoMetricas(@Param("programaId") Integer programaId,
                                                                 @Param("grupoId") Integer grupoId,
                                                                 @Param("modalidadId") Integer modalidadId,
                                                                 @Param("idiomaId") Integer idiomaId);

    @Query(value = "SELECT COALESCE (evaluacionDetalleId,evaluacionId) id, evaluacionId, COALESCE(actividadNombre,evaluacionNombre) nombre, MAX(COALESCE (actividadPuntaje,evaluacionPorcentaje)) puntaje " +
            "FROM [dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES] " +
            "WHERE programaId = :programaId AND grupoId = :grupoId AND modalidadId = :modalidadId AND idiomaId = :idiomaId " +
            "GROUP BY evaluacionId, evaluacionDetalleId, actividadNombre,evaluacionNombre", nativeQuery = true)
    List<ProgramaGrupoActividadProjection> getGrupoActividades(@Param("programaId") Integer programaId,
                                                              @Param("grupoId") Integer grupoId,
                                                              @Param("modalidadId") Integer modalidadId,
                                                              @Param("idiomaId") Integer idiomaId);

    @Query(value = "SELECT alumnoId id, alumno nombre " +
            "FROM [dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES] " +
            "WHERE programaId = :programaId AND grupoId = :grupoId AND modalidadId = :modalidadId AND idiomaId = :idiomaId " +
            "GROUP BY alumnoId, alumno", nativeQuery = true)
    List<ProgramaGrupoAlumnoProjection> getGrupoAlumnos(@Param("programaId") Integer programaId,
                                                                 @Param("grupoId") Integer grupoId,
                                                                 @Param("modalidadId") Integer modalidadId,
                                                                 @Param("idiomaId") Integer idiomaId);

    @Query("\n" +
            "SELECT pg \n" +
            "FROM ProgramaGrupo pg \n" +
            "WHERE \n" +
            "   pg.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND pg.programaIdioma.programaId = :programaId \n" +
            "   AND pg.paModalidadId = :modalidadId \n" +
            "   AND pg.modalidadHorarioId = :horarioId \n" +
            "   AND pg.nivel = :nivel \n" +
            "   AND pg.estatusId = 2000620 \n" + // Estatus activo
            "")
    List<ProgramaGrupo> findAllByActivoTrueAndIdiomaIdAndProgramaIdAndModalidadIdAndHorarioIdAndNivel(@Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId, @Param("modalidadId") Integer modalidadId, @Param("horarioId") Integer horarioId, @Param("nivel") Integer nivel);
    @Query("\n" +
            "SELECT pg \n" +
            "FROM ProgramaGrupo pg \n" +
            "WHERE \n" +
            "   pg.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND pg.programaIdioma.programaId = :programaId \n" +
            "   AND pg.paModalidadId = :modalidadId \n" +
            "   AND pg.tipoGrupoId = :tipoGrupoId \n" +
            "   AND pg.modalidadHorarioId = :horarioId \n" +
            "   AND pg.nivel = :nivel \n" +
            "   AND pg.estatusId = 2000620 \n" + // Estatus activo
            "")
    List<ProgramaGrupo> findAllByActivoTrueAndIdiomaIdAndProgramaIdAndModalidadIdAndTipoGrupoIdAndHorarioIdAndNivel(@Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId, @Param("modalidadId") Integer modalidadId, @Param("tipoGrupoId") Integer tipoGrupoId, @Param("horarioId") Integer horarioId, @Param("nivel") Integer nivel);

    @Query("\n" +
            "SELECT pg.cupo - COALESCE(count(ins),0) \n" +
            "FROM ProgramaGrupo pg \n" +
            "LEFT JOIN Inscripcion ins ON ins.grupoId = pg.id AND ins.estatusId IN (2000510,2000511) \n" +
            "WHERE pg.id = :grupoId \n" +
            "GROUP BY pg.id, pg.cupo \n" +
            "")
    Integer getCupoDisponible(@Param("grupoId") Integer grupoId);

    @Query("" +
            "SELECT DISTINCT pg.id \n" +
            "FROM ProgramaGrupo pg \n" +
            "INNER JOIN InscripcionSinGrupo inssg \n" +
            "   ON inssg.programaId = pg.programaIdioma.programaId \n" +
            "   AND inssg.idiomaId = pg.programaIdioma.idiomaId \n" +
            "   AND inssg.paModalidadId = pg.paModalidadId \n" +
            "   AND inssg.nivel = pg.nivel \n" +
            "WHERE \n" +
            "   pg.estatusId = 2000620 \n" +
            "   AND inssg.id = :inscripcionSinGrupoId \n" +
            "")
    List<Integer> findIdsByInscripcionSinGrupoId(@Param("inscripcionSinGrupoId") Integer inscripcionSinGrupoId);

    @Query("" +
            "SELECT DISTINCT pg.id \n" +
            "FROM ProgramaGrupo pg \n" +
            "INNER JOIN InscripcionSinGrupo inssg \n" +
            "   ON inssg.programaId = pg.programaIdioma.programaId \n" +
            "   AND inssg.idiomaId = pg.programaIdioma.idiomaId \n" +
            "   AND inssg.paModalidadId = pg.paModalidadId \n" +
            "   AND inssg.nivel = pg.nivel \n" +
            "   AND pg.tipoGrupoId = :tipoGrupoId \n" +
            "WHERE \n" +
            "   pg.estatusId = 2000620 \n" +
            "   AND inssg.id = :inscripcionSinGrupoId \n" +
            "")
    List<Integer> findIdsByInscripcionSinGrupoIdAndTipoGrupoId(@Param("inscripcionSinGrupoId") Integer inscripcionSinGrupoId, @Param("tipoGrupoId") Integer tipoGrupoId);

    @Query("" +
            "SELECT \n" +
            "   progru.id AS id, \n" +
            "   progru.codigoGrupo AS codigo, \n" +
            "   CONCAT(progru.programaIdioma.programa.codigo,' ',progru.programaIdioma.idioma.valor,' ',progru.paModalidad.nombre,' Nivel ',SUBSTRING(CONCAT('00',CAST(progru.nivel AS text)),LENGTH(CONCAT('00',CAST(progru.nivel AS text)))-1,LENGTH(CONCAT('00',CAST(progru.nivel AS text)))),' Grupo ',SUBSTRING(CONCAT('00',CAST(progru.grupo AS text)),LENGTH(CONCAT('00',CAST(progru.grupo AS text)))-1,LENGTH(CONCAT('00',CAST(progru.grupo AS text))))) AS nombreGrupo \n" +
            "FROM ProgramaGrupo progru \n" +
            "WHERE \n" +
            "   progru.sucursalId = :sucursalId \n" +
            "   AND progru.programaIdioma.programaId = :programaId \n" +
            "   AND progru.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND progru.paModalidadId = :modalidadId \n" +
            "   AND progru.nivel = :nivel \n" +
            "   AND progru.tipoGrupoId = :tipoGrupoId \n" +
            "   AND progru.estatusId = 2000620 \n" + // Activo
            "   AND progru.id <> :idActual \n" +
            "")
    List<ProgramaGrupoComboProjection> getPosiblesGruposParaCambio(@Param("sucursalId") Integer sucursalId,
                                                                   @Param("programaId") Integer programaId,
                                                                   @Param("idiomaId") Integer idiomaId,
                                                                   @Param("modalidadId") Integer modalidadId,
                                                                   @Param("nivel") Integer nivel,
                                                                   @Param("tipoGrupoId") Integer tipoGrupoId,
                                                                   @Param("idActual") Integer idActual);

    @Query(nativeQuery = true, value = "SELECT id,codigo,nombreGrupo,null as nombreSucursal from [dbo].[VW_LISTADO_GRUPOS]\n" +
            "where sucursal=:sucursal and programa=:programa and idioma=:idioma \n" +
            "and modalidad=:modalidad and nivel <=:nivel and estatus='Activo' and id!=:idActual ORDER BY codigo")
    List<ProgramaGrupoComboProjection> getPosiblesGruposParaCambioExamenUbicacion(@Param("sucursal") String sucursal,
                                                                   @Param("programa") String programa,
                                                                   @Param("idioma") String idioma,
                                                                   @Param("modalidad") String modalidad,
                                                                   @Param("nivel") Integer nivel,
                                                                   @Param("idActual") Integer idActual);

    @Query("" +
            "SELECT \n" +
            "   progru.id AS id, \n" +
            "   progru.codigoGrupo AS codigo, \n" +
            "   CONCAT(progru.programaIdioma.programa.codigo,' ',progru.programaIdioma.idioma.valor,' ',progru.paModalidad.nombre,' Nivel ',SUBSTRING(CONCAT('00',CAST(progru.nivel AS text)),LENGTH(CONCAT('00',CAST(progru.nivel AS text)))-1,LENGTH(CONCAT('00',CAST(progru.nivel AS text)))),' Grupo ',SUBSTRING(CONCAT('00',CAST(progru.grupo AS text)),LENGTH(CONCAT('00',CAST(progru.grupo AS text)))-1,LENGTH(CONCAT('00',CAST(progru.grupo AS text))))) AS nombreGrupo, \n" +
            "   progru.sucursalId AS sucursalId, \n" +
            "   progru.sucursal.nombre AS nombreSucursal \n" +
            "FROM ProgramaGrupo progru \n" +
            "WHERE \n" +
            "   progru.sucursalId <> :sucursalId \n" +
            "   AND progru.programaIdioma.programaId = :programaId \n" +
            "   AND progru.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND progru.paModalidadId = :modalidadId \n" +
            "   AND progru.nivel = :nivel \n" +
            "   AND progru.tipoGrupoId = :tipoGrupoId \n" +
            "   AND progru.estatusId = 2000620 \n" + // Activo
            "   AND progru.id <> :idActual \n" +
            "   AND progru.multisede = true \n" +
            "")
    List<ProgramaGrupoComboProjection> getPosiblesGruposMultisede(@Param("sucursalId") Integer sucursalId,
                                                                  @Param("programaId") Integer programaId,
                                                                  @Param("idiomaId") Integer idiomaId,
                                                                  @Param("modalidadId") Integer modalidadId,
                                                                  @Param("nivel") Integer nivel,
                                                                  @Param("tipoGrupoId") Integer tipoGrupoId,
                                                                  @Param("idActual") Integer idActual);

    @Query(nativeQuery = true, value = "SELECT id,codigo,nombreGrupo,sucursal as nombreSucursal from [dbo].[VW_LISTADO_GRUPOS]\n" +
            "where programa=:programa and idioma=:idioma \n" +
            "and modalidad=:modalidad and nivel <=:nivel and estatus='Activo' and id!=:idActual and multisede=1 ORDER BY codigo")
    List<ProgramaGrupoComboProjection> getPosiblesGruposMultisedeExamenUbicacion( @Param("programa") String programa,
                                                                                  @Param("idioma") String idioma,
                                                                                  @Param("modalidad") String modalidad,
                                                                                  @Param("nivel") Integer nivel,
                                                                                  @Param("idActual") Integer idActual);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS] WHERE sucursalId in (:sucursales) and estatus = 'Activo' and jobs = 1 ORDER BY codigo", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findProjectedAllBySucursalInAndEsJobs(@Param("sucursales") List<Integer> sucursales);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS] WHERE sucursalId in (:sucursales) and estatus = 'Activo' and jobssems = 1 ORDER BY codigo", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findProjectedAllBySucursalInAndEsJobsSems(@Param("sucursales") List<Integer> sucursales);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS] WHERE sucursalId in (:sucursales) and estatus = 'Activo' and pcp = 1 ORDER BY codigo", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findProjectedAllBySucursalInAndEsPCP(@Param("sucursales") List<Integer> sucursales);

    @Query(value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS] WHERE id in (:ids) and estatus = 'Activo' ORDER BY codigo", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findProjectedAllByIdIn(@Param("ids") List<Integer> ids);

    @Query(nativeQuery = true, value = "Select TOP 1 PROGRU_Grupo from ProgramasGrupos\n" +
            "WHERE \n" +
            "PROGRU_SUC_SucursalId=:sucursalId \n" +
            "AND PROGRU_PROGI_ProgramaIdiomaId=:cursoId \n" +
            "AND PROGRU_PAMOD_ModalidadId=:modalidadId \n" +
            "AND PROGRU_Nivel=:nivel\n" +
            "AND PROGRU_PAMODH_PAModalidadHorarioId=:horarioId\n" +
            "AND (:fechaInicio = '' OR PROGRU_FechaInicio >= :fechaInicio)\n" +
            "AND PROGRU_CMM_EstatusId=2000620\n" +
            "AND (:plantelId IS NULL OR PROGRU_SP_SucursalPlantelId=:plantelId) --Puede ser null\n" +
            "ORDER BY PROGRU_Grupo DESC")
    Integer getConsecutivo(@Param("sucursalId") Integer sucursalId,
                           @Param("cursoId") Integer cursoId,
                           @Param("modalidadId") Integer modalidadId,
                           @Param("nivel") Integer nivel,
                           @Param("plantelId") Integer plantelId,
                           @Param("horarioId") Integer horarioId,
                           @Param("fechaInicio") String fechaInicio);

    @Query(nativeQuery = true, value = "\n" +
            "SELECT id \n" +
            "FROM [dbo].[VW_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   estatusId = 2000620 \n" +
            "   AND fechaFinTolerancia < GETDATE() \n" +
            "")
    List<Integer> findIdAllByActivoAndFechaFinToleranciaExcedida();

    @Query(nativeQuery = true, value = "\n" +
            "SELECT id \n" +
            "FROM [dbo].[VW_ProgramasGrupos] \n" +
            "WHERE \n" +
            "   estatusId = 2000621 \n" +
            "")
    List<Integer> findIdAllByFinalizado();
    
    @Query("\n" +
            "SELECT DISTINCT progru.id \n" +
            "FROM ProgramaGrupo progru \n" +
            "INNER JOIN Inscripcion ins ON ins.grupoId = progru.id AND ins.estatusId NOT IN (2000512,2000604) \n" +
            "INNER JOIN Alumno alu ON alu.id = ins.alumnoId AND alu.activo = true \n" +
            "INNER JOIN ControlMaestroMultipleDatosAdicionales cmm ON cmm.id = alu.centroUniversitarioJOBSId AND cmm.control = 'CMM_ALU_CentrosUniversitarios' \n" +
            "INNER JOIN cmm.responsables usu \n" +
            "WHERE usu.id = :usuarioResponsableId" +
            "")
    List<Integer> findIdAllByCentroUniversitarioResponsableId(@Param("usuarioResponsableId") Integer usuarioResponsableId);
    @Query("\n" +
            "SELECT DISTINCT progru.id \n" +
            "FROM ProgramaGrupo progru \n" +
            "INNER JOIN Inscripcion ins ON ins.grupoId = progru.id AND ins.estatusId NOT IN (2000512,2000604) \n" +
            "INNER JOIN Alumno alu ON alu.id = ins.alumnoId AND alu.activo = true \n" +
            "INNER JOIN ControlMaestroMultipleDatosAdicionales cmm ON cmm.id = alu.centroUniversitarioJOBSId AND cmm.control = 'CMM_ALU_CentrosUniversitarios' \n" +
            "INNER JOIN cmm.responsables usu \n" +
            "WHERE \n" +
            "   usu.id = :usuarioResponsableId \n" +
            "   AND progru.id NOT IN :gruposIdsNot \n" +
            "")
    List<Integer> findIdAllByCentroUniversitarioResponsableIdAndIdNotIn(@Param("usuarioResponsableId") Integer usuarioResponsableId, @Param("gruposIdsNot") List<Integer> gruposIdsNot);
    @Query("\n" +
            "SELECT DISTINCT progru.id \n" +
            "FROM ProgramaGrupo progru \n" +
            "INNER JOIN Inscripcion ins ON ins.grupoId = progru.id AND ins.estatusId NOT IN (2000512,2000604) \n" +
            "INNER JOIN Alumno alu ON alu.id = ins.alumnoId AND alu.activo = true \n" +
            "INNER JOIN ControlMaestroMultipleDatosAdicionales cmm ON cmm.id = alu.preparatoriaJOBSId AND cmm.control = 'CMM_ALU_Preparatorias' \n" +
            "INNER JOIN cmm.responsables usu \n" +
            "WHERE usu.id = :usuarioResponsableId" +
            "")
    List<Integer> findIdAllByPreparatoriaResponsableId(@Param("usuarioResponsableId") Integer usuarioResponsableId);
    @Query("\n" +
            "SELECT DISTINCT progru.id \n" +
            "FROM ProgramaGrupo progru \n" +
            "INNER JOIN Inscripcion ins ON ins.grupoId = progru.id AND ins.estatusId NOT IN (2000512,2000604) \n" +
            "INNER JOIN Alumno alu ON alu.id = ins.alumnoId AND alu.activo = true \n" +
            "INNER JOIN ControlMaestroMultipleDatosAdicionales cmm ON cmm.id = alu.preparatoriaJOBSId AND cmm.control = 'CMM_ALU_Preparatorias' \n" +
            "INNER JOIN cmm.responsables usu \n" +
            "WHERE \n" +
            "   usu.id = :usuarioResponsableId \n" +
            "   AND progru.id NOT IN :gruposIdsNot \n" +
            "")
    List<Integer> findIdAllByPreparatoriaResponsableIdAndIdNotIn(@Param("usuarioResponsableId") Integer usuarioResponsableId, @Param("gruposIdsNot") List<Integer> gruposIdsNot);

    @Query("\n" +
            "SELECT CASE WHEN COUNT(progru) >= 1 THEN true ELSE false END \n" +
            "FROM ProgramaGrupo progru \n" +
            "INNER JOIN Inscripcion ins ON ins.grupoId = progru.id AND ins.estatusId NOT IN (2000512,2000604) \n" +
            "INNER JOIN Alumno alu ON alu.id = ins.alumnoId AND alu.activo = true \n" +
            "INNER JOIN ControlMaestroMultipleDatosAdicionales cmm ON cmm.id = alu.preparatoriaJOBSId AND cmm.control = 'CMM_ALU_Preparatorias' \n" +
            "INNER JOIN cmm.responsables usu \n" +
            "WHERE usu.id = :usuarioResponsableId AND progru.id = :grupoId" +
            "")
    Boolean getUsuarioEsResponsable(@Param("usuarioResponsableId") Integer usuarioResponsableId, @Param("grupoId") Integer grupoId);

    @Query("\n" +
            "SELECT CASE WHEN COUNT(progru) >= 1 THEN true ELSE false END \n" +
            "FROM ProgramaGrupo progru \n" +
            "INNER JOIN Prenomina preno ON preno.programaGrupoId = progru.id AND preno.activo = true\n" +
            "WHERE progru.id = :grupoId" +
            "")
    Boolean getGrupoTienePrenomina(@Param("grupoId") Integer grupoId);

    @Query("" +
            "SELECT DISTINCT pg.fechaInicio " +
            "FROM ProgramaGrupo pg " +
            "WHERE " +
            "   (pg.programacionAcademicaComercialId = :pacId or pg.paCicloId = :cicloId)" +
            "")
    List<String> findDistinctFechaInicioByProgramacionAcademicaComercialIdOrPaCicloId(@Param("pacId") Integer pacId, @Param("cicloId") Integer cicloId);

    @Query("" +
            "SELECT DISTINCT pg.fechaInicio " +
            "FROM ProgramaGrupo pg " +
            "WHERE FORMAT(pg.fechaInicio,'yyyy') = :anio" +
            "")
    List<String> findDistinctFechaInicioByAnio(@Param("anio") String anio);

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT YEAR(PROGRU_FechaInicio)\n" +
            "FROM ProgramasGrupos\n" +
            "WHERE PROGRU_CMM_EstatusId=2000620\n" +
            "ORDER BY YEAR(PROGRU_FechaInicio)")
    List<Integer> findAniosFechaInicio();

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT YEAR(PROGRU_FechaInicio)\n" +
                    "FROM ProgramasGrupos\n" +
                    "WHERE PROGRU_SUC_SucursalId IN(:sucursales) AND PROGRU_CMM_EstatusId IN (:estatus)\n" +
                    "ORDER BY YEAR(PROGRU_FechaInicio)")
    List<Integer> findAniosBySedeInAndEstatusIn(@Param("sucursales") List<Integer> sucursales, @Param("estatus") List<Integer> estatus);

    @Query(value =
            "SELECT DISTINCT PROGRU_FechaInicio\n" +
            "FROM ProgramasGrupos\n" +
            "WHERE YEAR(PROGRU_FechaInicio) = :anio\n" +
            "      AND (COALESCE(:modalidadesId, 0) = 0 OR PROGRU_PAMOD_ModalidadId IN (:modalidadesId))\n" +
            "      AND PROGRU_CMM_EstatusId != " + CMM_PROGRU_Estatus.CANCELADO + "\n" +
            "ORDER BY PROGRU_FechaInicio\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<String> findFechasInicioByAnioAndModalidadId(@Param("anio") int anio, @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT DISTINCT PROGRU_FechaInicio\n" +
            "FROM ProgramasGrupos\n" +
            "WHERE YEAR(PROGRU_FechaInicio) = :anio\n" +
            "      AND (COALESCE(:sedesId, 0) = 0 OR PROGRU_SUC_SucursalId IN (:sedesId))\n" +
            "      AND (COALESCE(:modalidadesId, 0) = 0 OR PROGRU_PAMOD_ModalidadId IN (:modalidadesId))\n" +
            "      AND PROGRU_CMM_EstatusId != " + CMM_PROGRU_Estatus.CANCELADO + "\n" +
            "ORDER BY PROGRU_FechaInicio\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<String> findFechasInicioBySedeAnioModalidadId(@Param("sedesId") List<Integer> sedesId,
                                                       @Param("anio") int anio,
                                                       @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT DISTINCT PROGRU_FechaInicio\n" +
                    "FROM ProgramasGrupos\n" +
                    "WHERE YEAR(PROGRU_FechaInicio) = :anio\n" +
                    "      AND (COALESCE(:sedesId, 0) = 0 OR PROGRU_SUC_SucursalId IN (:sedesId))\n" +
                    "      AND (COALESCE(:listaCursoId, 0) = 0 OR PROGRU_PROGI_ProgramaIdiomaId IN (:listaCursoId))\n" +
                    "      AND (COALESCE(:modalidadesId, 0) = 0 OR PROGRU_PAMOD_ModalidadId IN (:modalidadesId))\n" +
                    "      AND PROGRU_CMM_EstatusId != " + CMM_PROGRU_Estatus.CANCELADO + "\n" +
                    "ORDER BY PROGRU_FechaInicio\n" +
                    "OPTION(RECOMPILE)", nativeQuery = true)
    List<String> findFechasInicioBySedeCursoIdAnioModalidadId(@Param("sedesId") List<Integer> sedesId,
                                                       @Param("listaCursoId") List<Integer> listaCursoId,
                                                       @Param("anio") int anio,
                                                       @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT DISTINCT PROGRU_FechaInicio\n" +
            "FROM ProgramasGrupos\n" +
            "     INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId\n" +
            "     INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId AND PROG_PCP = 1\n" +
            "ORDER BY PROGRU_FechaInicio\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<String> findFechasInicioPCPByAnioAndModalidadId(@Param("anio") int anio, @Param("modalidadesId") List<Integer> modalidadesId);

    @Query(value =
            "SELECT DISTINCT GrupoId\n" +
            "FROM VW_RptEvidenciaFotografica\n" +
            "WHERE SedeId IN(:sedesId)\n" +
            "       AND FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
            "       AND (COALESCE(:modalidadesId, 0) = 0 OR ModalidadId IN (:modalidadesId))\n" +
            "       AND (COALESCE(:programasId, 0) = 0 OR ProgramaId IN (:programasId))\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<Integer> getIdsGruposPCP(@Param("sedesId") List<Integer> sedesId,
                                  @Param("fechas") List<String> fechas,
                                  @Param("modalidadesId") List<Integer> modalidadesId,
                                  @Param("programasId") List<Integer> programasId);

    @Query(value =
            "SELECT DISTINCT GrupoId\n" +
                    "FROM VW_RPT_ReporteAsistencias\n" +
                    "WHERE SedeId IN(:sedesId)\n" +
                    "       AND FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:fechas)\n" +
                    "       AND (COALESCE(:modalidadesId, 0) = 0 OR ModalidadId IN (:modalidadesId))\n" +
                    "       AND (COALESCE(:programasId, 0) = 0 OR ProgramaId IN (:programasId))\n" +
                    "OPTION(RECOMPILE)", nativeQuery = true)
    List<Integer> getIdsGruposPCPAsistencias(@Param("sedesId") List<Integer> sedesId,
                                  @Param("fechas") List<String> fechas,
                                  @Param("modalidadesId") List<Integer> modalidadesId,
                                  @Param("programasId") List<Integer> programasId);

    @Query(value = "" +
            "SELECT DISTINCT progru.tipoGrupoId \n" +
            "FROM ProgramaGrupo progru \n" +
            "WHERE \n" +
            "   progru.programaIdioma.idiomaId = :idiomaId \n" +
            "   AND progru.programaIdioma.programaId = :programaId \n" +
            "   AND progru.paModalidadId = :modalidadId \n" +
            "")
    List<Integer> findTiposGruposIds(@Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId, @Param("modalidadId") Integer modalidadId);

    @Query("" +
            "SELECT DISTINCT pg.fechaInicio " +
            "FROM ProgramaGrupo pg " +
            "WHERE " +
            "   pg.estatusId = 2000620 " +
            "   and (pg.sucursalId = :sedeId or pg.sucursalPlantelId = :plantelId) " +
            "   and (pg.programacionAcademicaComercialId = :pacId or pg.paCicloId = :cicloId) " +
            "   and pg.paModalidadId = :modalidadId" +
            "")
    List<String> findFechaInicioBySedeAndPAOrCicloAndModalidad(@Param("sedeId") Integer sedeId, @Param("plantelId") Integer plantelId, @Param("pacId") Integer pacId, @Param("cicloId") Integer cicloId, @Param("modalidadId") Integer modalidadId);

    @Query("" +
            "SELECT DISTINCT pg.fechaFin " +
            "FROM ProgramaGrupo pg " +
            "WHERE " +
            "   pg.estatusId = 2000620 or pg.estatusId = 2000621" +
            "   and (pg.sucursalId = :sedeId or pg.sucursalPlantelId = :plantelId) " +
            "   and (pg.programacionAcademicaComercialId = :pacId or pg.paCicloId = :cicloId) " +
            "   and pg.paModalidadId = :modalidadId " +
            "ORDER BY pg.fechaFin" +
            "")
    List<String> findFechaFinBySedeAndPAOrCicloAndModalidad(@Param("sedeId") Integer sedeId, @Param("plantelId") Integer plantelId, @Param("pacId") Integer pacId, @Param("cicloId") Integer cicloId, @Param("modalidadId") Integer modalidadId);

    @Query("" +
            "SELECT DISTINCT pg.fechaInicio " +
            "FROM ProgramaGrupo pg " +
            "WHERE " +
            "   pg.estatusId = 2000620 " +
            "   and (pg.sucursalId = :sedeId or pg.sucursalPlantelId in (:planteles)) " +
            "   and (pg.programacionAcademicaComercialId = :pacId or pg.paCicloId = :cicloId) " +
            "   and pg.paModalidadId = :modalidadId" +
            "")
    List<String> findFechaInicioByFiltros(@Param("sedeId") Integer sedeId, @Param("planteles") List<Integer> planteles, @Param("pacId") Integer pacId, @Param("cicloId") Integer cicloId, @Param("modalidadId") Integer modalidadId);
    
    @Query(nativeQuery = true, value = "" +
            "SELECT DISTINCT nivel \n" +
            "FROM VW_ProgramasGrupos \n" +
            "INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = programaIdiomaId \n" +
            "WHERE \n" +
            "   fechaLimiteReinscripcion >= GETDATE() \n" +
            "   AND PROGI_CMM_Idioma = :idiomaId \n" +
            "   AND PROGI_PROG_ProgramaId = :programaId \n" +
            "   AND paModalidadId = :modalidadId \n" +
            "   AND tipoGrupoId = :tipoGrupoId \n" +
            "ORDER BY nivel \n" +
            "")
    List<Integer> findNiveles(@Param("idiomaId") Integer idiomaId, @Param("programaId") Integer programaId, @Param("modalidadId") Integer modalidadId, @Param("tipoGrupoId") Integer tipoGrupoId);

    ProgramaGrupo findByCodigoGrupoAndFechaInicio(String codigo, Date fecha);

    @Query(nativeQuery = true, value = "Select TOP 1 fecha FROM ProgramasGrupos\n" +
            "INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId\n" +
            "OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaInicio,PAMOD_DiasFinPeriodoInscripcion,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)\n" +
            "WHERE fecha IS NOT NULL AND PROGRU_GrupoId=:idGrupo\n" +
            "ORDER BY fecha DESC")
    Date getfechaFinInscripciones(@Param("idGrupo") Integer idGrupo);

    @Query(nativeQuery = true, value = "Select TOP 1 fecha FROM ProgramasGrupos\n" +
            "INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId\n" +
            "OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaInicio,PAMOD_DiasFinPeriodoInscripcionBeca,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)\n" +
            "WHERE fecha IS NOT NULL AND PROGRU_GrupoId=:idGrupo\n" +
            "ORDER BY fecha DESC")
    Date getfechaFinInscripcionesBecas(@Param("idGrupo") Integer idGrupo);

    @Query(nativeQuery = true, value = "Select TOP 1 fecha FROM PAModalidades\n" +
            "OUTER APPLY dbo.fn_getFechaPorModalidad(:fechaInicio,PAMOD_DiasFinPeriodoInscripcion,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)\n" +
            "WHERE fecha IS NOT NULL AND PAMOD_ModalidadId= :idModalidad \n" +
            "ORDER BY fecha DESC")
    Date getfechaFinInscripcionesNuevo(@Param("idModalidad") Integer idModalidad, @Param("fechaInicio") String fechaInicio);

    @Query(nativeQuery = true, value = "Select TOP 1 fecha FROM PAModalidades\n" +
            "OUTER APPLY dbo.fn_getFechaPorModalidad(:fechaInicio,PAMOD_DiasFinPeriodoInscripcionBeca,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)\n" +
            "WHERE fecha IS NOT NULL AND PAMOD_ModalidadId= :idModalidad \n" +
            "ORDER BY fecha DESC")
    Date getfechaFinInscripcionesBecasNuevo(@Param("idModalidad") Integer idModalidad, @Param("fechaInicio") String fechaInicio);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PA\n" +
            "SET PROGRU_FechaFinInscripciones=(Select top 1 fecha order by fecha desc)\n" +
            "FROM ProgramasGrupos PA\n" +
            "INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId\n" +
            "OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaInicio,PAMOD_DiasFinPeriodoInscripcion,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) wh\n" +
            "WHERE PROGRU_CMM_EstatusId=2000620",
            nativeQuery = true)
    int actualizarFechaFinInscripciones();

    @Modifying
    @Transactional
    @Query(value = "UPDATE PA\n" +
            "SET PROGRU_FechaFinInscripcionesBecas=(Select top 1 fecha order by fecha desc)\n" +
            "FROM ProgramasGrupos PA\n" +
            "INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId\n" +
            "OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaInicio,PAMOD_DiasFinPeriodoInscripcionBeca,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) wh\n" +
            "WHERE PROGRU_CMM_EstatusId=2000620",
            nativeQuery = true)
    int actualizarFechaFinInscripcionesBecas();

    @Query(nativeQuery = true, value = "select CONCAT_WS('/',fechaInicio,fechaFin)\n" +
            "from (\n" +
            "    select PACD_FechaInicio,PACD_FechaFin, PACD_PAC_ProgramacionAcademicaComercialId,PACD_PAMOD_ModalidadId,\n" +
            "\t\t   PACD_CMM_IdiomaId,\n" +
            "\t\t   lead(PACD_FechaInicio) over (order by PACD_ProgramacionAcademicaComercialDetalleId) as fechaInicio,\n" +
            "           lead(PACD_FechaFin) over (order by PACD_ProgramacionAcademicaComercialDetalleId) as fechaFin\n" +
            "    from ProgramacionAcademicaComercialDetalles\n" +
            ") as tab\n" +
            "WHERE PACD_PAC_ProgramacionAcademicaComercialId=:idProgramacionAcademica AND PACD_PAMOD_ModalidadId=:idModalidad AND PACD_CMM_IdiomaId=:idIdiomaCMM\n" +
            "AND PACD_FechaFin=:fechaFin")
    String getfechaInicioFinSiguiente(@Param("idProgramacionAcademica") Integer idProgramacionAcademica,
                                      @Param("idModalidad") Integer idModalidad,
                                      @Param("idIdiomaCMM") Integer idIdiomaCMM,
                                      @Param("fechaFin") String fechaFin);

    ProgramaGrupoEditarProjection findProjectionById(Integer id);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[VW_LISTADO_GRUPOS] WHERE grupoReferenciaId = :idGrupo ORDER BY id desc")
    List<ProgramaGrupoComboProjection> findAllByGrupoReferenciaId(@Param("idGrupo") Integer idGrupo);

    @Query(nativeQuery = true, value = "" +
            "SELECT\n" +
            "id,codigoGrupo, curso, modalidad, fechaInicio, fechaFin, nivel, horario, cupo, totalInscritos, profesor\n" +
            "FROM [dbo].[VW_RPT_ReporteGruposProyeccion]\n" +
            "LEFT JOIN ProgramasGrupos on PROGRU_GrupoReferenciaId = id AND PROGRU_CMM_EstatusId != 2000622" +
            "WHERE " +
            "(:sedeId IS NULL OR sedeId = :sedeId) " +
            "AND (:modalidadId IS NULL OR modalidadId=:modalidadId) " +
            "AND (:programacionId IS NULL OR paId = :programacionId) " +
            "AND (:fechaFin IS NULL OR FORMAT(fechaFin, 'dd/MM/yyyy') = :fechaFin) " +
            "AND PROGRU_GrupoReferenciaId IS NULL \n" +
            "ORDER BY codigoGrupo, fechaInicio" )
    List<ProgramaGrupoProyeccionProjection> getReporteGruposFechaFin(@Param("sedeId") Integer sedeId,
                                                                     @Param("modalidadId") Integer modalidadId,
                                                                     @Param("programacionId") Integer programacionId,
                                                                     @Param("fechaFin") String fechaFin);

    @Query(value = "SELECT * FROM fn_getGruposProyectar(:grupos) OPTION(RECOMPILE)", nativeQuery = true)
    List<ProgramaGrupoProyeccionDetalleProjection> getGruposProyectar(@Param("grupos") String grupos);

    @Query(nativeQuery = true, value = "SELECT * FROM [dbo].[fn_getDiasClaseGrupo](:grupoId) WHERE fecha IS NOT NULL")
    List<ProgramaGrupoFechasClaseProjection> getFechasClaseByGrupoId(@Param("grupoId") Integer grupoId);

    List<ProgramaGrupo> findAllByGrupoReferenciaIdAndEstatusId(Integer referenciaId, Integer estatusId);

    List<ProgramaGrupo> findAllByFechaFinInscripcionesBecasIsBeforeAndGrupoReferenciaIdIsNotNull(Date fecha);

    @Query("\n" +
            "SELECT DISTINCT CAST(progru.fechaInicio AS string) \n" +
            "FROM ProgramaGrupo progru \n" +
            "WHERE \n" +
            " YEAR(progru.fechaInicio) = :anio \n" +
            " AND progru.sucursalId = :sucursalId \n" +
            " AND progru.paModalidadId = :idModalidad \n" +
            " ORDER BY CAST(progru.fechaInicio AS string) \n" +
            "")
    List<String> findProjectedFechasByAnioAndSucursalIdAndModalidadId(@Param("anio") Integer anio, @Param("sucursalId") Integer sucursalId, @Param("idModalidad") Integer idModalidad);

    @Query(nativeQuery = true, value = "SELECT * FROM _TMP_ALUMNOSGRUPOS WHERE codigoGrupo = :codigoGrupo")
    List<ProgramaGrupoCustomProjection> findAllProjectedCustomByCodigoGrupo(@Param("codigoGrupo") String codigoGrupo);

    @Modifying
    @Query(value = "UPDATE ProgramasGruposEvidencias SET PROGRUE_Vigente = 0 WHERE PROGRUE_PROGRU_GrupoId = :grupoId", nativeQuery = true)
    int actualizarEvidencias(@Param("grupoId") Integer grupoId);


    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM dbo.VW_CLE_PROGRAMASGRUPOS_SYNC\n" +
            "\n" +
            "WHERE fechaInicio >= :fechaInicio and (grupoProfesorId is null or grupoEstudiantesId is NULL)\n" +
            //"AND PROGRU_CMM_EstatusId not in (2000621 /*Finalizado */,2000622 /*Cancelado*/)" +
            "ORDER BY codigoGrupo, fechaInicio" )
    List<ProgramaGrupoProyeccionCLEProjection> getCleGruposSync(@Param("fechaInicio") String fechaInicio);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM dbo.VW_CLE_PROGRAMASGRUPOS_SYNC\n" +
            "    WHERE  programaGrupoEstatusId in (2000622, 2000621)\n" +
            "    AND (grupoProfesorId is not NULL or grupoEstudiantesId is not NULL )\n" +
            //"AND PROGRU_CMM_EstatusId not in (2000621 /*Finalizado */,2000622 /*Cancelado*/)" +
            "ORDER BY codigoGrupo, fechaInicio" )
    List<ProgramaGrupoProyeccionCLEProjection> getCleGruposFinalizadosCanceladosSync();


    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM dbo.VW_CLE_PROGRAMASGRUPOS_SYNC\n" +
            "\n" +
            "WHERE fechaInicio >= :fechaInicio and (grupoProfesorId is not null)\n" +
            //"AND PROGRU_CMM_EstatusId not in (2000621 /*Finalizado */,2000622 /*Cancelado*/)" +
            "ORDER BY codigoGrupo, fechaInicio" )
    List<ProgramaGrupoProyeccionCLEProjection> getCleGruposCreadosSync(@Param("fechaInicio") String fechaInicio);

    @Modifying
    @Query(value = "UPDATE ProgramasGrupos \n" +
            "SET PROGRU_CLE_FechaUltimaActualizacion = GETDATE(), PROGRU_CLE_GrupoProfesorId= :grupoProfesorId, PROGRU_CLE_GrupoEstudiantesId = :grupoEstudiantesId \n" +
            "WHERE PROGRU_GrupoId =  :grupoId ",
            nativeQuery = true)
    int actualizarGrupoCle(@Param("grupoId") Integer grupoId, @Param("grupoProfesorId") Integer grupoProfesorId, @Param("grupoEstudiantesId") Integer grupoEstudiantesId);

    @Modifying
    @Query(value = "UPDATE ProgramasGrupos \n" +
            "SET PROGRU_CLE_ProfesorId = :programGrupoProfesorCleId \n" +
            "WHERE PROGRU_GrupoId =  :grupoId ",
            nativeQuery = true)
    int actualizarGrupoProfesorCle(@Param("grupoId") Integer grupoId, @Param("programGrupoProfesorCleId") Integer programGrupoProfesorCleId);

    @Modifying
    @Query(value = "UPDATE Empleados\n" +
            "set EMP_CLE_UsuarioId = :empleadoCleId \n" +
            "WHERE EMP_EmpleadoId = :empleadoId ",
            nativeQuery = true)
    int actualizarProfesorCle(@Param("empleadoId") Integer empleadoId, @Param("empleadoCleId") Integer empleadoCleId);

    @Query(nativeQuery = true, value = "SELECT PROGRU_PAMOD_ModalidadId FROM ProgramasGrupos WHERE PROGRU_PROGI_ProgramaIdiomaId IN (:listaCursoId) GROUP BY PROGRU_PAMOD_ModalidadId")
    List<Integer> buscaModalidadConCurso(@Param("listaCursoId") List<Integer> listaCursoId);

    @Query(value = "SELECT * " +
            "FROM VW_Listado_PlantillaGrupos " +
            "WHERE SucursalId = :sedeId " +
            "   AND (COALESCE(:listaPlantelId, 0) = 0 OR PlantelId IN (:listaPlantelId)) " +
            "   AND (COALESCE(:listaCursoId, 0) = 0 OR CursoId IN (:listaCursoId)) " +
            "   AND (COALESCE(:listaModalidadId, 0) = 0 OR ModalidadId IN (:listaModalidadId)) " +
            "   AND (COALESCE(:listaFecha, '') = '' OR FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:listaFecha)) ORDER BY NombreProfesor ASC", nativeQuery = true)
    List<ProgramaGrupoPlantillaGruposListadoProjection> vw_listado_plantillaGrupos(@Param("sedeId") Integer sedeId,
                                                                                          @Param("listaPlantelId") List<Integer> listaPlantelId,
                                                                                          @Param("listaCursoId") List<Integer> listaCursoId,
                                                                                          @Param("listaModalidadId") List<Integer> listaModalidadId,
                                                                                           @Param("listaFecha") List<String> listaFecha);

    @Query(value = "SELECT * " +
            "FROM [dbo].[VW_PlantillaGrupos] " +
            "WHERE SucursalId IN (:listaSede) " +
            "   AND (COALESCE(:listaPlantelId, 0) = 0 OR PlantelId IN (:listaPlantelId)) " +
            "   AND (COALESCE(:listaCursoId, 0) = 0 OR CursoId IN (:listaCursoId)) " +
            "   AND (COALESCE(:listaModalidadId, 0) = 0 OR ModalidadId IN (:listaModalidadId)) " +
            "   AND (COALESCE(:listaFecha, '') = '' OR FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:listaFecha)) " +
            "   AND EstatusId = 2000620 AND Jobs = 1 ORDER BY codigo", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findProgramaGrupoListadoProjectionAllBySucursalInAndEsJobs(@Param("listaSede") List<Integer> listaSede,
                                                                                                    @Param("listaPlantelId") List<Integer> listaPlantelId,
                                                                                                    @Param("listaCursoId") List<Integer> listaCursoId,
                                                                                                    @Param("listaModalidadId") List<Integer> listaModalidadId,
                                                                                                    @Param("listaFecha") List<String> listaFecha);

    @Query(value = "SELECT * " +
            "FROM [dbo].[VW_PlantillaGrupos] " +
            "WHERE SucursalId IN (:listaSede) " +
            "   AND (COALESCE(:listaPlantelId, 0) = 0 OR PlantelId IN (:listaPlantelId)) " +
            "   AND (COALESCE(:listaCursoId, 0) = 0 OR CursoId IN (:listaCursoId)) " +
            "   AND (COALESCE(:listaModalidadId, 0) = 0 OR ModalidadId IN (:listaModalidadId)) " +
            "   AND (COALESCE(:listaFecha, '') = '' OR FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:listaFecha)) " +
            "   AND EstatusId = 2000620 AND JobsSems = 1 ORDER BY codigo", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findProgramaGrupoListadoProjectionAllBySucursalInAndEsJobsSems(@Param("listaSede") List<Integer> listaSede,
                                                                                                        @Param("listaPlantelId") List<Integer> listaPlantelId,
                                                                                                        @Param("listaCursoId") List<Integer> listaCursoId,
                                                                                                        @Param("listaModalidadId") List<Integer> listaModalidadId,
                                                                                                        @Param("listaFecha") List<String> listaFecha);

    @Query(value = "SELECT * " +
            "FROM [dbo].[VW_PlantillaGrupos] " +
            "WHERE SucursalId IN (:listaSede) " +
            "   AND (COALESCE(:listaPlantelId, 0) = 0 OR PlantelId IN (:listaPlantelId)) " +
            "   AND (COALESCE(:listaCursoId, 0) = 0 OR CursoId IN (:listaCursoId)) " +
            "   AND (COALESCE(:listaModalidadId, 0) = 0 OR ModalidadId IN (:listaModalidadId)) " +
            "   AND (COALESCE(:listaFecha, '') = '' OR FORMAT(FechaInicio, 'dd/MM/yyyy') IN (:listaFecha)) " +
            "   AND EstatusId = 2000620 AND Pcp = 1 ORDER BY codigo", nativeQuery = true)
    List<ProgramaGrupoListadoProjection> findProgramaGrupoListadoProjectionAllBySucursalInAndEsPCP(@Param("listaSede") List<Integer> listaSede,
                                                                                                   @Param("listaPlantelId") List<Integer> listaPlantelId,
                                                                                                   @Param("listaCursoId") List<Integer> listaCursoId,
                                                                                                   @Param("listaModalidadId") List<Integer> listaModalidadId,
                                                                                                   @Param("listaFecha") List<String> listaFecha);

    @Query(nativeQuery = true, value =
            "EXECUTE sp_GetQueryRptEficienciaGruposPorHorario :sedesId, :fecha, :modalidadId")
    String getQueryRptEficienciaGruposPorHorario(@Param("sedesId") String sedesId,
                                                 @Param("fecha") String fecha,
                                                 @Param("modalidadId") String modalidadId);

    @Query(nativeQuery = true, value =
            "EXECUTE sp_GetQueryRptEficienciaGruposPorSede :sedesId, :fecha, :modalidadId")
    String getQueryRptEficienciaGruposPorSede(@Param("sedesId") String sedesId,
                                              @Param("fecha") String fecha,
                                              @Param("modalidadId") String modalidadId);

    @Query(value =
            "SELECT Horario FROM VW_RptEficienciaGruposHorarios ORDER BY Id\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    List<String> getHorariosRptEficienciaGrupos();

    @Query(value =
            "SELECT ISNULL(MAX(inscritos), 0) AS maximoInscritos\n" +
            "FROM\n" +
            "(\n" +
            "    SELECT sedeId,\n" +
            "           sede,\n" +
            "           grupo,\n" +
            "           SUM(CASE WHEN inscripcionId IS NOT NULL THEN 1 ELSE 0 END) AS inscritos\n" +
            "    FROM VW_RptEficienciaGrupos\n" +
            "    WHERE (COALESCE(:sedesId, 0) = 0 OR sedeId IN (:sedesId))\n" +
            "          AND FORMAT(fecha, 'dd/MM/yyyy') = :fecha\n" +
            "          AND modalidadId = :modalidadId\n" +
            "    GROUP BY sedeId,\n" +
            "             sede,\n" +
            "             grupo\n" +
            ") AS todo\n" +
            "OPTION(RECOMPILE)", nativeQuery = true)
    Integer getMaxInscritosRptEficienciaGrupos(@Param("sedesId") List<Integer> sedesId,
                                               @Param("fecha") String fecha,
                                               @Param("modalidadId") int modalidadId);

    @Query(nativeQuery = true, value = "SELECT * FROM fn_getReaperturaGrupo(:codigo) ORDER BY FechaInicio DESC")
    List<ProgramaGrupoReaperturaGrupoProjection> findAllProgramaGrupoReaperturaGrupoProjectionBy(@Param("codigo") String codigo);
}
