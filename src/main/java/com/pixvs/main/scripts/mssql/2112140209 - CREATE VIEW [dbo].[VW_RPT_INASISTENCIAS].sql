CREATE OR ALTER VIEW VW_RPT_INASISTENCIAS
AS
select 
	ALU_CodigoAlumnoUDG id,
	CONCAT(ALU_PrimerApellido,' ', ALU_SegundoApellido,' ',ALU_Nombre) nombre,
	grados.CMM_Valor grados,
	ALU_Grupo grupo,
	turnos.CMM_Valor turnos,
	PROGRU_Nivel nivel,
	SP_Nombre plantel,
	CONCAT(PROG_Codigo,' ',idiomas.CMM_Valor,' ',PAMOD_Nombre,' ','Nivel',' ',FORMAT(PROGRU_Nivel, '00'),' ','Grupo',' ',FORMAT(PROGRU_Grupo, '00')) grupo_,
	CONCAT(EMP_PrimerApellido,' ',EMP_SegundoApellido,' ',EMP_Nombre) maestro,
	PAMODH_Horario horario,
	COALESCE(prepas.CMM_Valor,centros.CMM_Valor) escuela,
	COALESCE(ALU_BachilleratoTecnologico,carreras.CMM_Valor) carrera,
	COALESCE(PAC_Codigo, PACIC_Codigo) cohorte,
	'Sí' regular,
	'Sí' cartaCompromiso,
	ALU_Codigo codigo,
	estatus.CMM_Valor estatus,
	CAST(ALUG_Faltas + (ALUG_MinutosRetardo - (ALUG_MinutosRetardo % (PAMOD_HorasPorDia * 60))) / (PAMOD_HorasPorDia * 60) AS INT) faltas,
	ALUG_Asistencias asistencias,

	PROGRU_SUC_SucursalId sedeId,
	PROGRU_PACIC_CicloId cicloId,
	PROGRU_PAC_ProgramacionAcademicaComercialId paId,
	PROGI_PROG_ProgramaId programaId,
	PROGRU_FechaInicio fechaInicio,
	estatus.CMM_ControlId estatusId
from 
	AlumnosGrupos inner join Alumnos on ALUG_ALU_AlumnoId = ALU_AlumnoId
	inner join ControlesMaestrosMultiples grados on ALU_CMM_GradoId = grados.CMM_ControlId
	left join ControlesMaestrosMultiples turnos on ALU_CMM_TurnoId = turnos.CMM_ControlId
	inner join ProgramasGrupos on ALUG_PROGRU_GrupoId = PROGRU_GrupoId
	left join SucursalesPlanteles on PROGRU_SP_SucursalPlantelId = SP_SucursalPlantelId
	inner join ControlesMaestrosMultiples estatus on ALUG_CMM_EstatusId = estatus.CMM_ControlId
	inner join ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
	inner join Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
	inner join ControlesMaestrosMultiples idiomas on PROGI_CMM_Idioma = idiomas.CMM_ControlId
	inner join PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
	left join Empleados on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
	inner join PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
	left join ControlesMaestrosMultiples prepas on ALU_CMM_PreparatoriaJOBSId = prepas.CMM_ControlId
	left join ControlesMaestrosMultiples centros on ALU_CMM_CentroUniversitarioJOBSId = centros.CMM_ControlId
	left join ControlesMaestrosMultiples carreras on ALU_CMM_CarreraJOBSId = carreras.CMM_ControlId
	left join ProgramacionAcademicaComercial on PROGRU_PAC_ProgramacionAcademicaComercialId = PAC_ProgramacionAcademicaComercialId
	left join PACiclos on PROGRU_PACIC_CicloId = PACIC_CicloId
GO