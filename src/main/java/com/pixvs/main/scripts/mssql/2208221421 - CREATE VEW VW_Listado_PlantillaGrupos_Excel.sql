SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 22/08/2022
-- Description:	la vista para obtener el listado de plantilla grupos (EXCEL)
-- Version 1.0.0
-- =============================================

CREATE OR ALTER VIEW [dbo].[VW_Listado_PlantillaGrupos_Excel]
AS
	SELECT
		pg.PROGRU_Codigo AS codigo,
		YEAR(pg.PROGRU_FechaInicio) AS programacion,
		suc.SUC_Nombre AS sucursal,
		--SP_Nombre as plantel,
		CONCAT(p.PROG_Codigo,' ', idioma.CMM_Valor,' ', pam.PAMOD_Nombre, ' Nivel ', FORMAT(pg.PROGRU_Nivel,'00'), ' Grupo ', FORMAT(pg.PROGRU_Grupo,'00')) AS nombreGrupo,
		CONCAT(e.EMP_PrimerApellido,' ',e.EMP_SegundoApellido, ' ', e.EMP_Nombre) AS nombreProfesor,
		pamh.PAMODH_Horario AS horario,
		pg.PROGRU_FechaInicio AS fechaInicio,
		pg.PROGRU_FechaFin AS fechaFin,
		idioma.CMM_Valor AS idioma,
		cmm.valor AS tipoGrupo,
		pam.PAMOD_Nombre AS modalidad,
		pg.PROGRU_Nivel AS nivel,
		CONCAT(p.PROG_Codigo,'-', p.PROG_Nombre) AS programa,
		CASE
			WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor AS INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(pg.PROGRU_FechaFin AS DATE)) < cast (GETDATE() AS DATE) and pg.PROGRU_CMM_EstatusId = 2000620 THEN 'Finalizado'
			WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor AS INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(pg.PROGRU_FechaFin AS DATE)) >= cast (GETDATE() AS DATE) and pg.PROGRU_CMM_EstatusId = 2000622 THEN 'Activo'
			WHEN pg.PROGRU_CMM_EstatusId = 2000622 THEN 'Cancelado'
		END AS estatus
	FROM ProgramasGrupos pg
		INNER JOIN ProgramasIdiomas pi ON pg.PROGRU_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		INNER JOIN (
			SELECT CMM_Valor AS valor, CMM_ControlId AS id FROM ControlesMaestrosMultiples
		) cmm ON pg.PROGRU_CMM_TipoGrupoId = cmm.id
		INNER JOIN PAModalidadesHorarios pamh ON pg.PROGRU_PAMODH_PAModalidadHorarioId = pamh.PAMODH_PAModalidadHorarioId
		INNER JOIN PAModalidades pam ON pamh.PAMODH_PAMOD_ModalidadId = pam.PAMOD_ModalidadId
		INNER JOIN Sucursales suc ON pg.PROGRU_SUC_SucursalId = suc.SUC_SucursalId
		LEFT JOIN Empleados e ON pg.PROGRU_EMP_EmpleadoId = e.EMP_EmpleadoId
		LEFT JOIN SucursalesPlanteles sp ON pg.PROGRU_SP_SucursalPlantelId = sp.SP_SucursalPlantelId
		INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
	WHERE PROGRU_PGINCG_ProgramaIncompanyId IS NULL
GO


