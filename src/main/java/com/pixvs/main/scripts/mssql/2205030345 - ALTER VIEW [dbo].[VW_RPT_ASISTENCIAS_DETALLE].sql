SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_RPT_ASISTENCIAS_DETALLE]
AS
	SELECT 
		  ALU_CodigoAlumnoUDG codigo,
		  ALU_PrimerApellido primerApellido,
		  ALU_SegundoApellido segundoApellido,
		  ALU_Nombre nombre,
		  ALU_Correoelectronico correo,
		  coalesce(ALUG_Faltas, 0) as faltas, 
		  coalesce(ALUG_Asistencias, 0) as asistencias,
		  coalesce(ALUG_MinutosRetardo, 0) as retardos,
		  coalesce(estatus.CMM_Valor, 'Activo') as estatus,   
		  COALESCE(grado.CMM_Valor, '') grado,
		  COALESCE(ALU_Grupo,'') grupo,
		  COALESCE(turno.CMM_Valor,'') turno,
		  PROGRU_Nivel nivel,
		  CONCAT_WS(' ',EMP_Nombre,EMP_PrimerApellido,EMP_SegundoApellido) AS profesor,
		  SP_Nombre plantel,
		  CONCAT_WS(' ',PROG_Codigo,idioma.CMM_Valor,PAMOD_Nombre,FORMAT(PROGRU_Nivel,'Nivel 00'),FORMAT(PROGRU_Grupo,'Grupo 00')) codigoGrupo,
		  PROGRU_Codigo codigoGrupo2,
		  PAMODH_Horario horario,
		  COALESCE(prepa.CMM_Valor, cu.CMM_Referencia) escuela,
		  COALESCE(ALU_BachilleratoTecnologico, carrera.CMM_Valor) carrera,
		  primeroCiclos.codigo cohorte,
		  N'Si' regular,
		  N'Si' carta,
		  ALU_Codigo codigoProulex,

		  PROGRU_SUC_SucursalId sedeId,
		  SP_SucursalPlantelId plantelId,
		  PROGRU_PAC_ProgramacionAcademicaComercialId paId,
		  PROGRU_PACIC_CicloId cicloId,
		  PROGRU_PAMOD_ModalidadId modalidadId,
		  PROGRU_FechaInicio fechaInicio,
		  ALU_CMM_PreparatoriaJOBSId prepaId,
		  ALU_CMM_CentroUniversitarioJOBSId cuId

	   FROM ProgramasGrupos
	   INNER JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	   INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
	   INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
	   INNER JOIN ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
	   INNER JOIN Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
	   INNER JOIN PAModalidades on PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
	   INNER JOIN PAModalidadesHorarios on PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
	   LEFT JOIN AlumnosGrupos  on ALUG_PROGRU_GrupoId = PROGRU_GrupoId and ALUG_ALU_AlumnoId = ALU_AlumnoId
	   LEFT JOIN ControlesMaestrosMultiples grado on ALU_CMM_GradoId = grado.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples turno on ALU_CMM_TurnoId = turno.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples prepa on ALU_CMM_PreparatoriaJOBSId = prepa.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples cu on ALU_CMM_CentroUniversitarioJOBSId = cu.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples carrera on ALU_CMM_CarreraJOBSId = carrera.CMM_ControlId
	   LEFT JOIN ControlesMaestrosMultiples estatus on ALUG_CMM_EstatusId = estatus.CMM_ControlId 
	   LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId
	   LEFT JOIN Empleados on PROGRU_EMP_EmpleadoId = EMP_EmpleadoId
	   LEFT JOIN (select ins.INS_ALU_AlumnoId alumnoId, p.PROGRU_GrupoId grupoId, pa.PACIC_Codigo codigo from 
		  (
		  SELECT i.INS_ALU_AlumnoId,  
			 i.INS_PROGRU_GrupoId, 
			 ROW_NUMBER() OVER(PARTITION BY i.INS_ALU_AlumnoId 
							   ORDER BY i.INS_FechaCreacion ASC) AS rank
		  FROM Inscripciones i) ins
		  inner join ProgramasGrupos p on ins.INS_PROGRU_GrupoId = p.PROGRU_GrupoId
		  left join PACiclos pa on p.PROGRU_PACIC_CicloId = pa.PACIC_CicloId
		  where ins.rank = 1) primeroCiclos on alumnoId = ALU_AlumnoId
	   WHERE
		  PROGRU_Activo = 1 
		  AND ALU_Activo = 1
		  AND INS_CMM_EstatusId <> 2000512 
GO