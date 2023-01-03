/**
 * Created by Angel Daniel Hernández Silva on 26/01/2021.
 * Object: Actualizar registros de profesores titulares
 */

UPDATE ProgramasGruposProfesores SET PROGRUP_Activo = 1
FROM(
	SELECT
		grupoId,
		CASE WHEN (MAX(PROGRUP_FechaCreacion) OVER(PARTITION BY PROGRUP_PROGRU_GrupoId)) = PROGRUP_FechaCreacion THEN PROGRUP_ProgramaGrupoProfesorId ELSE NULL END AS id
	FROM(
		SELECT
			PROGRU_GrupoId AS grupoId
		FROM ProgramasGruposProfesores
		INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = PROGRUP_PROGRU_GrupoId AND PROGRU_FechaInicio = PROGRUP_FechaInicio
		GROUP BY PROGRU_GrupoId
		HAVING SUM(CASE WHEN PROGRUP_Activo = 1 THEN 1 ELSE 0 END) = 0
	) Q1
	INNER JOIN ProgramasGruposProfesores ON PROGRUP_PROGRU_GrupoId = grupoId
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = PROGRUP_PROGRU_GrupoId AND PROGRU_FechaInicio = PROGRUP_FechaInicio
) Q2
WHERE id IS NOT NULL AND id = PROGRUP_ProgramaGrupoProfesorId