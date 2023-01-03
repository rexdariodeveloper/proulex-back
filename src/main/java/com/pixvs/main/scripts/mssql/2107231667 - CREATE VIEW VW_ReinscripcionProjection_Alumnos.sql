/**
* Created by Angel Daniel Hernández Silva on 19/07/2021.
* Object:  CREATE VIEW [dbo].[VW_ReinscripcionProjection_Alumnos]
*/

CREATE OR ALTER VIEW [dbo].[VW_ReinscripcionProjection_Alumnos] AS

	SELECT
		ALU_AlumnoId AS id,
		ALU_Codigo AS codigo,
		NULL AS becaId,
		'' AS becaCodigo,
		'' AS codigoUDG,
		ALU_Nombre AS nombre,
		ALU_PrimerApellido AS primerApellido,
		ALU_SegundoApellido AS segundoApellido,
		CONCAT(PROG_Codigo,' ',CMM_Valor) AS curso,
		PAMOD_Nombre AS modalidad,
		PAMODH_Horario AS horario,
		GrupoReinscripcion.PROGRU_Nivel AS nivelReinscripcion,
		NULL AS calificacion,
		idiomaId,
		programaId,
		modalidadId,
		GrupoReinscripcion.PROGRU_GrupoId AS grupoId,
		GrupoReinscripcion.PROGRU_SUC_SucursalId AS sucursalId,
		ART_ArticuloId AS articuloId,
		GrupoReinscripcion.PROGRU_Grupo AS numeroGrupo
	FROM (
		SELECT
			alumnoId,
			idiomaId,
			programaId,
			modalidadId,
			horarioId,
			MAX(nivelReinscripcion) AS nivelReinscripcion
		FROM (
			SELECT
				alumnoId,
				idiomaId,
				programaId,
				modalidadId,
				horarioId,
				nivelActual+1 AS nivelReinscripcion
			FROM (
				SELECT
					ALU_AlumnoId AS alumnoId,
					PROGI_CMM_Idioma AS idiomaId,
					PROG_ProgramaId AS programaId,
					PAMOD_ModalidadId AS modalidadId,
					PAMODH_PAModalidadHorarioId AS horarioId,
					MAX(PROGRU_Nivel) AS nivelActual
				FROM Alumnos
				INNER JOIN Inscripciones ON INS_ALU_AlumnoId = INS_ALU_AlumnoId
				INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
				INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
				INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
				INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
				INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
				WHERE PROGRU_Activo = 1
				GROUP BY ALU_AlumnoId, PROGI_CMM_Idioma, PROG_ProgramaId, PAMOD_ModalidadId, PAMODH_PAModalidadHorarioId
			) AlumnosNivelActual
			INNER JOIN ProgramasGrupos
				ON PROGRU_Nivel = nivelActual
				AND PROGRU_PAMOD_ModalidadId = modalidadId
				AND PROGRU_PAMODH_PAModalidadHorarioId = horarioId
			INNER JOIN ProgramasIdiomas
				ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
				AND PROGI_CMM_Idioma = idiomaId
				AND PROGI_PROG_ProgramaId = programaId
			INNER JOIN Inscripciones ON INS_PROGRU_GrupoId = PROGRU_GrupoId
			WHERE
				INS_ALU_AlumnoId = alumnoId
				AND CAST(PROGRU_FechaFin AS DATE) < CAST(GETDATE() AS DATE)
		) AS AlumnosReinscripciones
		GROUP BY alumnoId, idiomaId, programaId, modalidadId, horarioId
	) AS AlumnosListosReinscripcion
	INNER JOIN ProgramasGrupos AS GrupoReinscripcion
		ON GrupoReinscripcion.PROGRU_Nivel = nivelReinscripcion
		AND GrupoReinscripcion.PROGRU_PAMOD_ModalidadId = modalidadId
		AND GrupoReinscripcion.PROGRU_PAMODH_PAModalidadHorarioId = horarioId
		AND GrupoReinscripcion.PROGRU_Activo = 1
	INNER JOIN ProgramasGrupos AS GrupoAnterior
		ON GrupoAnterior.PROGRU_Nivel = nivelReinscripcion-1
		AND GrupoAnterior.PROGRU_PAMOD_ModalidadId = modalidadId
		AND GrupoAnterior.PROGRU_PAMODH_PAModalidadHorarioId = horarioId
		AND GrupoAnterior.PROGRU_Activo = 1
	INNER JOIN ProgramasIdiomas
		ON PROGI_ProgramaIdiomaId = GrupoReinscripcion.PROGRU_PROGI_ProgramaIdiomaId
		AND PROGI_CMM_Idioma = idiomaId
		AND PROGI_CMM_Idioma = idiomaId
		AND PROGI_PROG_ProgramaId = programaId
	INNER JOIN Inscripciones ON INS_PROGRU_GrupoId = GrupoAnterior.PROGRU_GrupoId
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = modalidadId
	INNER JOIN Alumnos ON ALU_AlumnoId = alumnoId
	INNER JOIN Programas ON PROG_ProgramaId = programaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = idiomaId
	INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = horarioId
	INNER JOIN Articulos ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = modalidadId
	WHERE
		INS_ALU_AlumnoId = alumnoId
		AND DATEADD(DAY,PAMOD_DiasFinPeriodoInscripcion,GrupoReinscripcion.PROGRU_FechaInicio) >= GETDATE()

GO