SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_GRUPOS]
AS

	Select 
		PROGRU_GrupoId as id,
		--CONCAT(PROG_Codigo,CMM_Referencia,'-',PAMOD_Codigo,PROGRU_Nivel,PROGRU_Grupo,PAMODH_Horario) as codigo,
		PROGRU_Codigo as codigo,
		YEAR(PROGRU_FechaInicio) as programacion,
		SUC_Nombre as sucursal,
		--SP_Nombre as plantel,
		CONCAT(PROG_Codigo,' ',IDIOMAS.CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) as nombreGrupo,
		CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as nombreProfesor,
		PAMODH_Horario as horario,
		PROGRU_FechaInicio as fechaInicio,
		PROGRU_FechaFin as fechaFin,
		PROGRU_Activo as activo,
		IDIOMAS.CMM_Valor as idioma,
		cmm.valor as tipoGrupo,
		PAMOD_Nombre as modalidad,
		PROGRU_Multisede as multisede,
		PROGRU_Nivel as nivel,
		CONCAT(PROG_Codigo,'-',PROG_Nombre) as programa,
		/*
		CASE
		WHEN PROGRU_CMM_EstatusId = 2000621 THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor as INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin as DATE)) = cast (GETDATE() as DATE) and PROGRU_Activo = 1 AND CONVERT(varchar(20),CONVERT(time, GETDATE()), 114) >= CONVERT(varchar(20),CONVERT(time, PAMODH_HoraFin)) THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor as INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin as DATE)) < cast (GETDATE() as DATE) and PROGRU_Activo = 1 THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor as INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin as DATE)) >= cast (GETDATE() as DATE) and PROGRU_Activo = 1 THEN 'Activo'
		WHEN PROGRU_Activo = 0 THEN 'Cancelado'
		END as estatus,
		*/
		ESTATUS.CMM_Valor estatus,
		PROG_JOBS jobs,
		PROG_JOBSSEMS jobsSems,
		PROG_PCP pcp,
		
		SUC_SucursalId AS sucursalId,
		PROGRU_SP_SucursalPlantelId AS plantelId,
		PROGRU_PROGI_ProgramaIdiomaId AS cursoId,
		PAMOD_ModalidadId AS modalidadId,
		PAMODH_PAModalidadHorarioId AS horarioId,
		cmm.id AS tipoGrupoId,
		PROGRU_GrupoReferenciaId as grupoReferenciaId,
		PAMODH_HoraFin as horaFin
		
	from ProgramasGrupos
	inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	inner join ControlesMaestrosMultiples IDIOMAS on IDIOMAS.CMM_ControlId = PROGI_CMM_Idioma
	inner join (
		SELECT CMM_Valor as valor, CMM_ControlId as id from ControlesMaestrosMultiples
	) cmm on cmm.id = PROGRU_CMM_TipoGrupoId
	inner join PAModalidadesHorarios on PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	inner join PAModalidades on PAMOD_ModalidadId = PAMODH_PAMOD_ModalidadId
	inner join Sucursales suc on SUC_SucursalId = PROGRU_SUC_SucursalId
	left join Empleados on EMP_EmpleadoId = PROGRU_EMP_EmpleadoId
	left join SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
	inner join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ESTATUS ON PROGRU_CMM_EstatusId = ESTATUS.CMM_ControlId
	WHERE PROGRU_PGINCG_ProgramaIncompanyId IS NULL
GO