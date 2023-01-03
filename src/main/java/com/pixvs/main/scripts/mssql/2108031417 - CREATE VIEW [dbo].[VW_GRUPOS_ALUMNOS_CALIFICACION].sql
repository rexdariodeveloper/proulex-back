CREATE OR ALTER VIEW [dbo].[VW_GRUPOS_ALUMNOS_CALIFICACION]
AS
	SELECT
		AEC_ALU_AlumnoId alumnoId,
		AEC_PROGRU_GrupoId grupoId,
		ROUND(SUM(AEC_Puntaje * puntos), 2) calificacion
	FROM
		AlumnosExamenesCalificaciones
		INNER JOIN ProgramasIdiomasExamenesDetalles ON AEC_PROGIED_ProgramaIdiomaExamenDetalleId = PROGIED_ProgramaIdiomaExamenDetalleId
		INNER JOIN
		(
			SELECT
				PROGIE_ProgramaIdiomaExamenId id, 
				MAX(PROGIE_Porcentaje) porcentaje,
				SUM(PROGIED_Puntaje) puntaje,
				ROUND(CAST(MAX(PROGIE_Porcentaje) AS decimal(28,10)) / CAST(SUM(PROGIED_Puntaje) AS decimal(28,10)),4) puntos
			FROM
				ProgramasIdiomasExamenes 
				INNER JOIN ProgramasIdiomasExamenesDetalles ON PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId
			GROUP BY
				PROGIE_ProgramaIdiomaExamenId
		) t ON t.id = PROGIED_PROGIE_ProgramaIdiomaExamenId
		GROUP BY AEC_ALU_AlumnoId, AEC_PROGRU_GrupoId
GO