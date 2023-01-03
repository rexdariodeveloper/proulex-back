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
	PROGRU_Activo activo,
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
	) suplentes on PROGRU_GrupoId = suplentes.suplenteId
	left join Empleados s on suplentes.suplenteId = s.EMP_EmpleadoId
GO