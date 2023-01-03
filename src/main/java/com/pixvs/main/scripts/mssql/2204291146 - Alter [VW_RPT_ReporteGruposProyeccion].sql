CREATE OR ALTER VIEW [dbo].[VW_RPT_ReporteGruposProyeccion]
AS
	SELECT  
		PROGRU_GrupoId as id,
		PROGRU_Codigo as codigoGrupo, 
		--CONCAT_WS(' ',PROG_Codigo,CMM_Valor,PAMOD_Nombre,FORMAT(PROGRU_Nivel,'Nivel 00'),FORMAT(PROGRU_Grupo,'Grupo 00')) as grupoNombre, 
		CONCAT_WS(' ',PROG_Codigo,CMM_Valor) as curso,
		PAMOD_Nombre as modalidad, 
		PROGRU_FechaInicio as fechaInicio, 
		PROGRU_FechaFin as fechaFin, 
		FORMAT(PROGRU_Nivel,'00') as nivel, 
		PAMODH_Horario as horario, 
		PROGRU_Cupo as cupo, 
		COALESCE(t.inscritos, 0) as totalInscritos, 
		CONCAT_WS(' ',EMP_Nombre,EMP_PrimerApellido,EMP_SegundoApellido) as profesor,
		PROGRU_SUC_SucursalId sedeId,
		PROGRU_PAC_ProgramacionAcademicaComercialId paId,
		PROGRU_PAMOD_ModalidadId modalidadId
	FROM 
		ProgramasGrupos 
		INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId 
		INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId 
		INNER JOIN ProgramasIdiomas on PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId 
		INNER JOIN Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId 
		INNER JOIN ControlesMaestrosMultiples CMM on CMM_ControlId = PROGI_CMM_Idioma 
		LEFT JOIN SucursalesPlanteles on SP_SucursalPlantelId = PROGRU_SP_SucursalPlantelId 
		LEFT JOIN Empleados on EMP_EmpleadoId = PROGRU_EMP_EmpleadoId 
		LEFT JOIN (
			SELECT INS_PROGRU_GrupoId grupoId, COUNT(INS_InscripcionId) inscritos FROM Inscripciones WHERE INS_CMM_EstatusId <> 2000512 GROUP BY INS_PROGRU_GrupoId
		) t ON t.grupoId = PROGRU_GrupoId
		WHERE PROGRU_Activo=1 AND PROGRU_CMM_EstatusId=2000620
GO
