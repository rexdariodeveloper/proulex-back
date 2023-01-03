DELETE [dbo].[AlumnosAsistencias] WHERE DATEPART(WEEKDAY, [AA_Fecha]) = 1
GO

UPDATE
	[dbo].[AlumnosGrupos]
	SET [ALUG_Asistencias] = asistencias,
		[ALUG_Faltas] = faltas,
		[ALUG_MinutosRetardo] = retardos
FROM 
	[dbo].[AlumnosGrupos]
	INNER JOIN (
		SELECT 
			[AA_ALU_AlumnoId], 
			[AA_PROGRU_GrupoId],
			SUM(CASE WHEN [AA_CMM_TipoAsistenciaId] IN (2000550, 2000552) THEN 1 ELSE 0 END) asistencias,
			SUM(CASE WHEN [AA_CMM_TipoAsistenciaId] = 2000551 THEN 1 ELSE 0 END) faltas,
			SUM(CASE WHEN [AA_CMM_TipoAsistenciaId] = 2000553 THEN [AA_MinutosRetardo] ELSE 0 END) retardos
		FROM 
			[dbo].[AlumnosAsistencias] 
		GROUP BY
			[AA_ALU_AlumnoId], [AA_PROGRU_GrupoId]
	) aa ON [ALUG_ALU_AlumnoId] = [AA_ALU_AlumnoId] and [ALUG_PROGRU_GrupoId] = [AA_PROGRU_GrupoId]
GO