CREATE TRIGGER [dbo].[trg_migrarCriteriosVigentes] ON [dbo].[ProgramasGrupos]
AFTER INSERT
AS
	DECLARE @grupoId INT = (SELECT PROGRU_GrupoId FROM INSERTED);

	INSERT INTO [dbo].[ProgramasGruposExamenes]([PROGRUE_PROGRU_GrupoId],[PROGRUE_Nombre],[PROGRUE_Porcentaje])
	SELECT PROGRU_GrupoId, PROGIE_Nombre, PROGIE_Porcentaje FROM 
		ProgramasGrupos 
		INNER JOIN ProgramasIdiomasNiveles ON PROGRU_PROGI_ProgramaIdiomaId = PROGIN_PROGI_ProgramaIdiomaId AND PROGRU_Nivel BETWEEN PROGIN_NivelInicial AND PROGIN_NivelFinal
		INNER JOIN ProgramasIdiomasExamenes ON PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId
	WHERE 
		PROGRU_CMM_EstatusId = 2000620
		AND PROGIN_Activo = 1
		AND PROGIE_Activo = 1
		AND PROGRU_GrupoId = @grupoId
	ORDER BY PROGRU_GrupoId, PROGIE_ProgramaIdiomaExamenId

	INSERT INTO [dbo].[ProgramasGruposExamenesDetalles](PROGRUED_PROGRUE_ProgramaGrupoExamenId, PROGRUED_PAAE_ActividadEvaluacionId, PROGRUED_CMM_TestId, PROGRUED_Puntaje, PROGRUED_Time, PROGRUED_Continuos)
	SELECT 
		PROGRUE_ProgramaGrupoExamenId, PROGIED_PAAE_ActividadEvaluacionId, PROGIED_CMM_TestId, PROGIED_Puntaje, PROGIED_Time, PROGIED_Continuos
	FROM 
		ProgramasGrupos
		INNER JOIN ProgramasGruposExamenes ON PROGRU_GrupoId = PROGRUE_PROGRU_GrupoId
		LEFT JOIN ProgramasGruposExamenesDetalles ON PROGRUE_ProgramaGrupoExamenId = PROGRUED_PROGRUE_ProgramaGrupoExamenId
		INNER JOIN ProgramasIdiomasNiveles ON PROGRU_PROGI_ProgramaIdiomaId = PROGIN_PROGI_ProgramaIdiomaId AND PROGRU_Nivel BETWEEN PROGIN_NivelInicial AND PROGIN_NivelFinal
		INNER JOIN ProgramasIdiomasExamenes ON PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId AND PROGRUE_Nombre = PROGIE_Nombre
		INNER JOIN ProgramasIdiomasExamenesDetalles ON PROGIED_PROGIE_ProgramaIdiomaExamenId = PROGIE_ProgramaIdiomaExamenId
		--INNER JOIN PAActividadesEvaluacion ON PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
	WHERE 
		PROGRUED_ProgramaGrupoExamenDetalleId IS NULL
		AND PROGIN_Activo = 1
		AND PROGIE_Activo = 1
		AND PROGIED_Activo = 1
		AND PROGRU_GrupoId = @grupoId
GO