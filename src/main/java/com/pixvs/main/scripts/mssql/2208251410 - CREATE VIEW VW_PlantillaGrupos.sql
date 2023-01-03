SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 24/08/2022
-- Description:	la vista para obtener la plantilla de grupos
-- Version 1.0.0
-- =============================================

CREATE   VIEW [dbo].[VW_PlantillaGrupos]
AS
	SELECT
		pg.PROGRU_GrupoId AS Id,
		--CONCAT(PROG_Codigo,CMM_Referencia,'-',PAMOD_Codigo,PROGRU_Nivel,PROGRU_Grupo,PAMODH_Horario) AS codigo,
		pg.PROGRU_Codigo AS Codigo,
		YEAR(pg.PROGRU_FechaInicio) AS Programacion,
		suc.SUC_Nombre AS Sucursal,
		--SP_Nombre AS plantel,
		CONCAT(p.PROG_Codigo,' ',idioma.CMM_Valor,' ', pam.PAMOD_Nombre,' Nivel ', FORMAT(pg.PROGRU_Nivel,'00'),' Grupo ', FORMAT(pg.PROGRU_Grupo,'00')) AS NombreGrupo,
		CONCAT(e.EMP_Nombre,' ', e.EMP_PrimerApellido,' ', e.EMP_SegundoApellido) AS NombreProfesor,
		pamh.PAMODH_Horario AS Horario,
		pg.PROGRU_FechaInicio AS FechaInicio,
		pg.PROGRU_FechaFin AS FechaFin,
		--PROGRU_Activo AS activo,
		idioma.CMM_Valor AS Idioma,
		cmm.valor AS TipoGrupo,
		pam.PAMOD_Nombre AS Modalidad,
		pg.PROGRU_Multisede AS Multisede,
		pg.PROGRU_Nivel AS Nivel,
		CONCAT(p.PROG_Codigo,'-', p.PROG_Nombre) AS Programa,
		/*
		CASE
		WHEN PROGRU_CMM_EstatusId = 2000621 THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor AS INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin AS DATE)) = cast (GETDATE() AS DATE) and PROGRU_Activo = 1 AND CONVERT(varchar(20),CONVERT(time, GETDATE()), 114) >= CONVERT(varchar(20),CONVERT(time, PAMODH_HoraFin)) THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor AS INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin AS DATE)) < cast (GETDATE() AS DATE) and PROGRU_Activo = 1 THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor AS INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin AS DATE)) >= cast (GETDATE() AS DATE) and PROGRU_Activo = 1 THEN 'Activo'
		WHEN PROGRU_Activo = 0 THEN 'Cancelado'
		END AS estatus,
		*/
		estatus.CMM_Valor AS Estatus,
		pg.PROGRU_CMM_EstatusId AS EstatusId,
		p.PROG_JOBS AS Jobs,
		p.PROG_JOBSSEMS AS JobsSems,
		p.PROG_PCP AS Pcp,
		suc.SUC_SucursalId AS SucursalId,
		pg.PROGRU_SP_SucursalPlantelId AS PlantelId,
		pg.PROGRU_PROGI_ProgramaIdiomaId AS CursoId,
		pam.PAMOD_ModalidadId AS ModalidadId,
		pamh.PAMODH_PAModalidadHorarioId AS HorarioId,
		cmm.id AS TipoGrupoId,
		pg.PROGRU_GrupoReferenciaId AS GrupoReferenciaId,
		pamh.PAMODH_HoraFin AS HoraFin
	FROM ProgramasGrupos pg
	INNER JOIN ProgramasIdiomas pi ON pg.PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId
	INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
	INNER JOIN (
		SELECT CMM_Valor AS valor, CMM_ControlId AS id from ControlesMaestrosMultiples
	) cmm ON pg.PROGRU_CMM_TipoGrupoId = cmm.id
	INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
	INNER JOIN PAModalidades pam ON pamh.PAMODH_PAMOD_ModalidadId = pam.PAMOD_ModalidadId
	INNER JOIN Sucursales suc ON pg.PROGRU_SUC_SucursalId = suc.SUC_SucursalId
	left join Empleados e ON pg.PROGRU_EMP_EmpleadoId = e.EMP_EmpleadoId
	left join SucursalesPlanteles sp ON pg.PROGRU_SP_SucursalPlantelId = sp.SP_SucursalPlantelId
	INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples estatus ON pg.PROGRU_CMM_EstatusId = estatus.CMM_ControlId
	WHERE pg.PROGRU_PGINCG_ProgramaIncompanyId IS NULL
GO


