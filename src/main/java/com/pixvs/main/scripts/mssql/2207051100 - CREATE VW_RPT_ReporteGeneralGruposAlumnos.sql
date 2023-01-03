SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RPT_ReporteGeneralGruposAlumnos]
AS
     SELECT PROGRU_GrupoId AS grupoId,
		   PROGRU_SUC_SucursalId AS sedeGrupoId,
		   plantelGrupo.SP_SucursalPlantelId AS plantelGrupoId,
		   UPPER(ISNULL(plantelGrupo.SP_Nombre, '')) AS plantelGrupo,
		   plantelAlumno.SP_SUC_SucursalId AS sedeAlumnoId,
		   plantelAlumno.SP_SucursalPlantelId AS plantelAlumnoId,
		   UPPER(ISNULL(plantelAlumno.SP_Nombre, '')) AS plantelAlumno,
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
		 LEFT JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId AND INS_CMM_EstatusId != 2000512
		 LEFT JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
		 LEFT JOIN ControlesMaestrosMultiples ON ALU_CMM_PreparatoriaJOBSId = CMM_ControlId
		 LEFT JOIN SucursalesPlanteles AS plantelAlumno ON CMM_Referencia = plantelAlumno.SP_Codigo AND plantelAlumno.SP_Activo = 1
	WHERE PROGRU_CMM_EstatusId != 2000622 -- No Cancelado
GO