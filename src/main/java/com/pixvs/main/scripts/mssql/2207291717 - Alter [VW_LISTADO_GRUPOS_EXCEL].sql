SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_GRUPOS_EXCEL]
AS
	Select 
		PROGRU_Codigo as codigo,
		YEAR(PROGRU_FechaInicio) as programacion,
		SUC_Nombre as sucursal,
		--SP_Nombre as plantel,
		CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) as nombreGrupo,
		CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as nombreProfesor,
		PAMODH_Horario as horario,
		PROGRU_FechaInicio as fechaInicio,
		PROGRU_FechaFin as fechaFin,
		CMM_Valor as idioma,
		cmm.valor as tipoGrupo,
		PAMOD_Nombre as modalidad,
		PROGRU_Nivel as nivel,
		CONCAT(PROG_Codigo,'-',PROG_Nombre) as programa,
		CASE
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor as INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin as DATE)) < cast (GETDATE() as DATE) and PROGRU_CMM_EstatusId = 2000620 THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor as INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin as DATE)) >= cast (GETDATE() as DATE) and PROGRU_CMM_EstatusId = 2000622 THEN 'Activo'
		WHEN PROGRU_CMM_EstatusId = 2000622 THEN 'Cancelado'
		END as estatus
	from ProgramasGrupos
	inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	inner join ControlesMaestrosMultiples on CMM_ControlId = PROGI_CMM_Idioma
	inner join (
		SELECT CMM_Valor as valor, CMM_ControlId as id from ControlesMaestrosMultiples
	) cmm on cmm.id = PROGRU_CMM_TipoGrupoId
	inner join PAModalidadesHorarios on PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	inner join PAModalidades on PAMOD_ModalidadId = PAMODH_PAMOD_ModalidadId
	inner join Sucursales suc on SUC_SucursalId = PROGRU_SUC_SucursalId
	left join Empleados on EMP_EmpleadoId = PROGRU_EMP_EmpleadoId
	left join SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
	inner join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
	WHERE PROGRU_PGINCG_ProgramaIncompanyId IS NULL
GO