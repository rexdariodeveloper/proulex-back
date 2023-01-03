SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_GRUPOS]
AS
	Select 
	PROGRU_GrupoId as id,
	CONCAT(PROGI_CLAVE,CMM_Referencia,'-',PAMOD_Codigo,PROGRU_Nivel,PROGRU_Grupo,PAMODH_Horario) as codigo,
	YEAR(PROGRU_FechaInicio) as programacion,
	SUC_Nombre as sucursal,
	CONCAT(PROGI_CLAVE,CMM_Valor,'-',PAMOD_Nombre,PROGRU_Nivel,PROGRU_Grupo,PAMODH_Horario) as nombreGrupo,
	CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as nombreProfesor,
	PAMODH_Horario as horario,
	PROGRU_FechaFin as fechaFin,
	PROGRU_Activo as activo,
	CMM_Valor as idioma,
	PAMOD_Nombre as modalidad,
	CONCAT(PROG_Codigo,'-',PROG_Nombre) as programa
	from ProgramasGrupos
	inner join ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	inner join ControlesMaestrosMultiples on CMM_ControlId = PROGI_CMM_Idioma
	inner join PAModalidadesHorarios on PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	inner join PAModalidades on PAMOD_ModalidadId = PAMODH_PAMOD_ModalidadId
	inner join Sucursales suc on SUC_SucursalId = PROGRU_SUC_SucursalId
	left join Empleados on EMP_EmpleadoId = PROGRU_EMP_EmpleadoId
	inner join Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
GO
