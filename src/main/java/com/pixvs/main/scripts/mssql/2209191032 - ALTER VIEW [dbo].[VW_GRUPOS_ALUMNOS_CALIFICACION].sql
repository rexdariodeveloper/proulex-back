SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_GRUPOS_ALUMNOS_CALIFICACION] WITH SCHEMABINDING
AS
	SELECT
		AEC_ALU_AlumnoId alumnoId,
		AEC_PROGRU_GrupoId grupoId,
		ROUND(SUM(AEC_Puntaje * puntos), 2) calificacion
	FROM
		[dbo].[AlumnosExamenesCalificaciones]
		INNER JOIN [dbo].[ProgramasGruposExamenesDetalles] ON [AEC_PROGRUED_ProgramaGrupoExamenDetalleId] = [PROGRUED_ProgramaGrupoExamenDetalleId]
		INNER JOIN
		(
			SELECT
				[PROGRUE_ProgramaGrupoExamenId] id, 
				MAX([PROGRUE_Porcentaje]) porcentaje,
				SUM([PROGRUED_Puntaje]) puntaje,
				ROUND(CAST(MAX([PROGRUE_Porcentaje]) AS decimal(28,10)) / CAST(SUM([PROGRUED_Puntaje]) AS decimal(28,10)),4) puntos
			FROM
				[dbo].[ProgramasGruposExamenes] 
				INNER JOIN [dbo].[ProgramasGruposExamenesDetalles] ON [PROGRUE_ProgramaGrupoExamenId] = [PROGRUED_PROGRUE_ProgramaGrupoExamenId]
			GROUP BY
				[PROGRUE_ProgramaGrupoExamenId]
		) t ON t.id = [PROGRUED_PROGRUE_ProgramaGrupoExamenId]
		GROUP BY AEC_ALU_AlumnoId, AEC_PROGRU_GrupoId
GO