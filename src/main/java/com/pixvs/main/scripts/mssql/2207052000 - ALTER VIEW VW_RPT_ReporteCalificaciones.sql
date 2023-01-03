SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 29/06/2022
-- Modify date: 05/07/2022
-- Description:	la vista para obtener el reporte de calificaciones
-- Version 1.0.1
-- =============================================

ALTER VIEW [dbo].[VW_RPT_ReporteCalificaciones]
AS
	SELECT a.ALU_Codigo AS codigo,
		a.ALU_CodigoAlumnoUDG AS codigoAlumno,
		a.ALU_PrimerApellido AS primerApellido,
		a.ALU_SegundoApellido AS segundoApellido,
		a.ALU_Nombre AS nombre,
		cmm.CMM_Valor AS estatus,
		pg.PROGRU_Codigo AS codigoGrupo,
		pg.PROGRU_SUC_SucursalId AS sedeId,
		pg.PROGRU_SP_SucursalPlantelId AS plantelId,
		pg.PROGRU_PAC_ProgramacionAcademicaComercialId AS paId,
		pg.PROGRU_PACIC_CicloId AS cicloId,
		pg.PROGRU_PAMOD_ModalidadId AS modalidadId,
		CAST(pg.PROGRU_FechaInicio AS DATE) AS fechaInicio,
		a.ALU_CMM_PreparatoriaJOBSId AS prepaId,
		a.ALU_CMM_CentroUniversitarioJOBSId AS cuId,
		ag.ALUG_CalificacionFinal AS calificacionFinal
	FROM AlumnosGrupos ag
		INNER JOIN ProgramasGrupos pg ON ag.ALUG_PROGRU_GrupoId = pg.PROGRU_GrupoId
		INNER JOIN Alumnos a ON ag.ALUG_ALU_AlumnoId = a.ALU_AlumnoId
		INNER JOIN ControlesMaestrosMultiples cmm ON ag.ALUG_CMM_EstatusId = cmm.CMM_ControlId
		INNER JOIN Inscripciones i ON ag.ALUG_ALU_AlumnoId = i.INS_ALU_AlumnoId AND ag.ALUG_PROGRU_GrupoId = i.INS_PROGRU_GrupoId
	WHERE (i.INS_CMM_EstatusId NOT IN (2000512, 2000513))
GO