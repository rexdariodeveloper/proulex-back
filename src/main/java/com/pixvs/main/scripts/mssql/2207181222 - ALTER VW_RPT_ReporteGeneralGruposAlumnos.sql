SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RPT_ReporteGeneralGruposAlumnos]
AS
     SELECT DISTINCT
		   PROGRU_GrupoId AS grupoId,
		   PROGRU_SUC_SucursalId AS sedeGrupoId,
		   plantelGrupo.SP_SucursalPlantelId AS plantelGrupoId,
		   UPPER(ISNULL(plantelGrupo.SP_Nombre, '')) AS plantelGrupo,
		   ISNULL(plantelAlumnoPreparatoria.SP_SUC_SucursalId, plantelAlumnoUniversidad.SP_SUC_SucursalId) AS sedeAlumnoId,
		   ISNULL(plantelAlumnoPreparatoria.SP_SucursalPlantelId, plantelAlumnoUniversidad.SP_SucursalPlantelId) AS plantelAlumnoId,
		   UPPER(ISNULL(ISNULL(plantelAlumnoPreparatoria.SP_Nombre, plantelAlumnoUniversidad.SP_Nombre), '')) AS plantelAlumno,
		   PROGRU_FechaInicio AS fechaInicio,
		   FORMAT(PROGRU_Nivel, '00') AS nivel,
		   PAMODH_Horario AS horario,
		   PAMOD_ModalidadId AS modalidadId,
		   UPPER(PAMOD_Nombre) AS modalidad,
		   ALU_AlumnoId AS alumnoId
	FROM ProgramasGrupos
		 INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		 INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		 LEFT JOIN SucursalesPlanteles AS plantelGrupo ON PROGRU_SP_SucursalPlantelId = plantelGrupo.SP_SucursalPlantelId AND plantelGrupo.SP_Activo = 1
		 LEFT JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId AND INS_CMM_EstatusId NOT IN (2000513, 2000512) -- Ni Baja ni Cancelada
		 LEFT JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
		 LEFT JOIN ControlesMaestrosMultiples AS cmmPreparatoria ON ALU_CMM_PreparatoriaJOBSId = cmmPreparatoria.CMM_ControlId
		 LEFT JOIN SucursalesPlanteles AS plantelAlumnoPreparatoria ON cmmPreparatoria.CMM_Referencia = plantelAlumnoPreparatoria.SP_Codigo AND plantelAlumnoPreparatoria.SP_Activo = 1
		 LEFT JOIN ControlesMaestrosMultiples AS cmmUniversidad ON ALU_CMM_CentroUniversitarioJOBSId = cmmUniversidad.CMM_ControlId
		 LEFT JOIN SucursalesPlanteles AS plantelAlumnoUniversidad ON cmmUniversidad.CMM_Referencia = plantelAlumnoUniversidad.SP_Nombre AND plantelAlumnoUniversidad.SP_Activo = 1
	WHERE PROGRU_CMM_EstatusId != 2000622 -- No Cancelado
GO