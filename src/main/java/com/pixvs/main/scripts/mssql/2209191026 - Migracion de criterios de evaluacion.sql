/* Para todos los grupos finalizados, obtener su configuración de las calificaciones tomadas */
INSERT INTO [dbo].[ProgramasGruposExamenes]([PROGRUE_PROGRU_GrupoId],[PROGRUE_Nombre],[PROGRUE_Porcentaje])
SELECT 
	PROGRU_GrupoId, MAX(PROGIE_Nombre), MAX(PROGIE_Porcentaje)
FROM 
	ProgramasGrupos 
	INNER JOIN (SELECT AEC_PROGRU_GrupoId grupoId, AEC_PROGIED_ProgramaIdiomaExamenDetalleId detalleId FROM AlumnosExamenesCalificaciones GROUP BY AEC_PROGRU_GrupoId, AEC_PROGIED_ProgramaIdiomaExamenDetalleId) t ON PROGRU_GrupoId = grupoId
	INNER JOIN ProgramasIdiomasExamenesDetalles ON t.detalleId = PROGIED_ProgramaIdiomaExamenDetalleId
	INNER JOIN ProgramasIdiomasExamenes ON PROGIED_PROGIE_ProgramaIdiomaExamenId = PROGIE_ProgramaIdiomaExamenId
	INNER JOIN PAActividadesEvaluacion ON PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
WHERE 
	PROGRU_CMM_EstatusId = 2000621 and PROGRU_GrupoId = 2253
GROUP BY PROGRU_GrupoId, PROGIE_ProgramaIdiomaExamenId
ORDER BY PROGRU_GrupoId, PROGIE_ProgramaIdiomaExamenId
GO-- 21,244

INSERT INTO [dbo].[ProgramasGruposExamenesDetalles](PROGRUED_PROGRUE_ProgramaGrupoExamenId, PROGRUED_PAAE_ActividadEvaluacionId, PROGRUED_CMM_TestId, PROGRUED_Puntaje, PROGRUED_Time, PROGRUED_Continuos)
SELECT 
	PROGRUE_ProgramaGrupoExamenId, PAAE_ActividadEvaluacionId, PROGIED_CMM_TestId, PROGIED_Puntaje, PROGIED_Puntaje, PROGIED_Continuos
	--PROGRU_GrupoId, PROGIE_Nombre, PROGIE_Porcentaje, PAAE_Actividad, PROGIED_Puntaje, PROGRUE_Nombre, PROGRUE_Porcentaje, PROGRUE_ProgramaGrupoExamenId
FROM 
	ProgramasGrupos 
	INNER JOIN (SELECT AEC_PROGRU_GrupoId grupoId, AEC_PROGIED_ProgramaIdiomaExamenDetalleId detalleId FROM AlumnosExamenesCalificaciones GROUP BY AEC_PROGRU_GrupoId, AEC_PROGIED_ProgramaIdiomaExamenDetalleId) t ON PROGRU_GrupoId = grupoId
	INNER JOIN ProgramasIdiomasExamenesDetalles ON t.detalleId = PROGIED_ProgramaIdiomaExamenDetalleId
	INNER JOIN ProgramasIdiomasExamenes ON PROGIED_PROGIE_ProgramaIdiomaExamenId = PROGIE_ProgramaIdiomaExamenId
	INNER JOIN PAActividadesEvaluacion ON PROGIED_PAAE_ActividadEvaluacionId = PAAE_ActividadEvaluacionId
	INNER JOIN ProgramasGruposExamenes ON PROGRU_GrupoId = PROGRUE_PROGRU_GrupoId AND PROGIE_Nombre = PROGRUE_Nombre
WHERE 
	PROGRU_CMM_EstatusId = 2000621
ORDER BY PROGRU_GrupoId, PROGIE_ProgramaIdiomaExamenId
GO

/* Para los grupos activos, tomar los criterios de evaluación según corresponda al vigente */ -- 2,407
INSERT INTO [dbo].[ProgramasGruposExamenes]([PROGRUE_PROGRU_GrupoId],[PROGRUE_Nombre],[PROGRUE_Porcentaje])
SELECT PROGRU_GrupoId, PROGIE_Nombre, PROGIE_Porcentaje FROM 
	ProgramasGrupos 
	INNER JOIN ProgramasIdiomasNiveles ON PROGRU_PROGI_ProgramaIdiomaId = PROGIN_PROGI_ProgramaIdiomaId AND PROGRU_Nivel BETWEEN PROGIN_NivelInicial AND PROGIN_NivelFinal
	INNER JOIN ProgramasIdiomasExamenes ON PROGIN_ProgramaIdiomaNivelId = PROGIE_PROGIN_ProgramaIdiomaNivelId
	--INNER JOIN ProgramasIdiomasExamenesDetalles ON PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId
WHERE 
	PROGRU_CMM_EstatusId = 2000620
	AND PROGIN_Activo = 1
	AND PROGIE_Activo = 1
ORDER BY PROGRU_GrupoId, PROGIE_ProgramaIdiomaExamenId
GO

INSERT INTO [dbo].[ProgramasGruposExamenesDetalles](PROGRUED_PROGRUE_ProgramaGrupoExamenId, PROGRUED_PAAE_ActividadEvaluacionId, PROGRUED_CMM_TestId, PROGRUED_Puntaje, PROGRUED_Time, PROGRUED_Continuos)
SELECT 
	PROGRUE_ProgramaGrupoExamenId, PROGIED_PAAE_ActividadEvaluacionId, PROGIED_CMM_TestId, PROGIED_Puntaje, PROGIED_Time, PROGIED_Continuos
	--PROGRUE_ProgramaGrupoExamenId, MAX(PROGRUE_Nombre), COUNT(*), STRING_AGG(PAAE_Actividad, ',')
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
GO