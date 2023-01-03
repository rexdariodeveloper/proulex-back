SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_LISTADO_GRUPOS_CAPTURA]
AS
select
	PROGRU_GrupoId id,
	PROGRU_Codigo codigo,
	COALESCE(PAC_Codigo, PACIC_Codigo) programacion,
	SUC_Nombre sucursal,
	SP_Nombre plantel,
	CONCAT_WS(' ', t.EMP_Nombre, t.EMP_PrimerApellido, t.EMP_SegundoApellido) nombreProfesor,
	CONCAT_WS(' ', s.EMP_Nombre, s.EMP_PrimerApellido, s.EMP_SegundoApellido) nombreSuplente,
	PAMODH_Horario horario,
	PROGRU_FechaInicio fechaInicio,
	PROGRU_FechaFin fechaFin,
	PROG_Codigo programaCodigo,
	idioma.CMM_Valor idioma,
	PAMOD_Nombre modalidad,
	FORMAT(PROGRU_Nivel, '00') nivel,
	FORMAT(PROGRU_Grupo, '00') grupo,
	PROGRU_Cupo cupo,
	COALESCE(inscripciones.inscritos, 0) inscritos,
	PAMOD_Color colorModalidad,
	CASE WHEN idioma.CMM_Color IS NULL OR SUBSTRING(idioma.CMM_Color,1,7) = '' THEN '#4D6EB5' ELSE SUBSTRING(idioma.CMM_Color,1,7) END colorPrimario,
	CASE WHEN idioma.CMM_Color IS NULL OR SUBSTRING(idioma.CMM_Color,9,16) = '' THEN '#99CEB0' ELSE SUBSTRING(idioma.CMM_Color,9,16) END colorSecundario,
	-- PROGRU_Activo activo,
	CASE WHEN PROGRU_CMM_EstatusId = 2000620 THEN 1 ELSE 0 END activo,
	SUC_SucursalId sucursalId,
	t.EMP_EmpleadoId profesorId,
	s.EMP_EmpleadoId suplenteId
from 
	ProgramasGrupos
	left join ProgramacionAcademicaComercial on PROGRU_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
	left join PACiclos on PROGRU_PACIC_CicloId = PACIC_CicloId
	inner join Sucursales on PROGRU_SUC_SucursalId = SUC_SucursalId
	left join SucursalesPlanteles on PROGRU_SP_SucursalPlantelId = SP_SucursalPlantelId
	left join Empleados t on PROGRU_EMP_EmpleadoId = t.EMP_EmpleadoId
	inner join PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
	inner join ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
	inner join Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
	inner join ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
	inner join PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
	left join (
		select INS_PROGRU_GrupoId grupoId, COUNT(INS_InscripcionId) inscritos 
		from Inscripciones 
		where INS_CMM_EstatusId in (2000510,2000511) 
		group by INS_PROGRU_GrupoId
	) inscripciones on PROGRU_GrupoId = inscripciones.grupoId
	left join (
		select PROGRULC_PROGRU_GrupoId grupoId, PROGRULC_EMP_EmpleadoId suplenteId
		from ProgramasGruposListadoClases
		where CAST(PROGRULC_Fecha as date) = CAST(GETDATE() as date)
	) suplentes on PROGRU_GrupoId = suplentes.grupoId
	left join Empleados s on suplentes.suplenteId = s.EMP_EmpleadoId
	WHERE PROGRU_PGINCG_ProgramaIncompanyId IS NULL
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_LISTADO_GRUPOS]
AS

	Select 
		PROGRU_GrupoId as id,
		--CONCAT(PROG_Codigo,CMM_Referencia,'-',PAMOD_Codigo,PROGRU_Nivel,PROGRU_Grupo,PAMODH_Horario) as codigo,
		PROGRU_Codigo as codigo,
		YEAR(PROGRU_FechaInicio) as programacion,
		SUC_Nombre as sucursal,
		--SP_Nombre as plantel,
		CONCAT(PROG_Codigo,' ',CMM_Valor,' ',PAMOD_Nombre,' Nivel ',FORMAT(PROGRU_Nivel,'00'),' Grupo ',FORMAT(PROGRU_Grupo,'00')) as nombreGrupo,
		CONCAT(EMP_Nombre,' ',EMP_PrimerApellido,' ',EMP_SegundoApellido) as nombreProfesor,
		PAMODH_Horario as horario,
		PROGRU_FechaInicio as fechaInicio,
		PROGRU_FechaFin as fechaFin,
		PROGRU_Activo as activo,
		CMM_Valor as idioma,
		cmm.valor as tipoGrupo,
		PAMOD_Nombre as modalidad,
		PROGRU_Multisede as multisede,
		PROGRU_Nivel as nivel,
		CONCAT(PROG_Codigo,'-',PROG_Nombre) as programa,
		CASE
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor as INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin as DATE)) < cast (GETDATE() as DATE) and PROGRU_Activo = 1 THEN 'Finalizado'
		WHEN DATEADD(DAY,COALESCE((SELECT CAST(CMA_Valor as INT) FROM ControlesMaestros WHERE CMA_Nombre='CM_SUMA_DIAS_FECHA_FIN'),0),cast(PROGRU_FechaFin as DATE)) >= cast (GETDATE() as DATE) and PROGRU_Activo = 1 THEN 'Activo'
		WHEN PROGRU_Activo = 0 THEN 'Cancelado'
		END as estatus,
		PROG_JOBS jobs,
		PROG_JOBSSEMS jobsSems,
		PROG_PCP pcp,
		
		SUC_SucursalId AS sucursalId,
		PROGRU_SP_SucursalPlantelId AS plantelId,
		PROGRU_PROGI_ProgramaIdiomaId AS cursoId,
		PAMOD_ModalidadId AS modalidadId,
		PAMODH_PAModalidadHorarioId AS horarioId,
		cmm.id AS tipoGrupoId
		
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
