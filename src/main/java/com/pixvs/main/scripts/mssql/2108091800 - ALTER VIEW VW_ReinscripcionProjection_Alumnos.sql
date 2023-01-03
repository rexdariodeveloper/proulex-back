/**
* Created by Angel Daniel Hernández Silva on 02/08/2021.
* Object:  ALTER VIEW [dbo].[VW_ReinscripcionProjection_Alumnos]
*/

CREATE OR ALTER VIEW [dbo].[VW_ReinscripcionProjection_Alumnos] AS

	SELECT
		ALU_AlumnoId AS id,
		ALU_Codigo AS codigo,
		NULL AS becaId,
		'' AS becaCodigo,
		ALU_CodigoUDG AS codigoUDG,
		ALU_Nombre AS nombre,
		ALU_PrimerApellido AS primerApellido,
		ALU_SegundoApellido AS segundoApellido,
		CONCAT(PROG_Codigo,' ',CMM_Valor) AS curso,
		PAMOD_Nombre AS modalidad,
		PAMODH_Horario AS horario,
		nivelActual + 1 AS nivelReinscripcion,
		Calificaciones.calificacion AS calificacion,
		idiomaId,
		programaId,
		modalidadId,
		PAMODH_PAModalidadHorarioId AS horarioId,
		GrupoAnterior.PROGRU_SUC_SucursalId AS sucursalId,
		ART_ArticuloId AS articuloId,
		GrupoAnterior.PROGRU_Grupo AS numeroGrupo
	FROM (
		-- vvvvvvvvvvvvvvvv --
		-- START SUBQUERY_2 -- (AlumnosReinscripciones)
		-- vvvvvvvvvvvvvvvv --

		-- Filtramos los grupos que no han finalizado, los que tengan
		-- un plazo mayor al permitido para reinsccripciones y los que
		-- no tengan un siguiente nivel
				
		SELECT
			alumnoId,
			idiomaId,
			programaId,
			nivelActual,
			PROGRU_PAMOD_ModalidadId AS modalidadId,
			PROGRU_PAMODH_PAModalidadHorarioId AS horarioId
		FROM (
			-- vvvvvvvvvvvvvvvv --
			-- START SUBQUERY_1 -- (AlumnosNivelActual)
			-- vvvvvvvvvvvvvvvv --

			-- Obtenemos el último nivel cursado por idioma y programa
				
			SELECT
				ALU_AlumnoId AS alumnoId,
				PROGI_CMM_Idioma AS idiomaId,
				PROG_ProgramaId AS programaId,
				MAX(PROGRU_Nivel) AS nivelActual
			FROM Alumnos
			INNER JOIN Inscripciones ON INS_ALU_AlumnoId = ALU_AlumnoId
			INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
			INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
			LEFT JOIN InscripcionesSinGrupo
				ON INSSG_ALU_AlumnoId = ALU_AlumnoId
				AND INSSG_CMM_IdiomaId = PROGI_CMM_Idioma
				AND INSSG_CMM_EstatusId IN (2000540,2000541)
			WHERE
				INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
				AND PROGRU_Activo = 1
			GROUP BY ALU_AlumnoId, PROGI_CMM_Idioma, PROG_ProgramaId
			HAVING MAX(INSSG_InscripcionId) IS NULL
				
			-- ^^^^^^^^^^^^^^ --
			-- END SUBQUERY_1 -- (AlumnosNivelActual)
			-- ^^^^^^^^^^^^^^ --
		) AlumnosNivelActual
		INNER JOIN ProgramasGrupos GrupoReinscripcion ON PROGRU_Nivel = nivelActual
		INNER JOIN ProgramasIdiomas
			ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			AND PROGI_CMM_Idioma = idiomaId
			AND PROGI_PROG_ProgramaId = programaId
		INNER JOIN Inscripciones ON INS_PROGRU_GrupoId = PROGRU_GrupoId AND INS_ALU_AlumnoId = alumnoId
		INNER JOIN ControlesMaestros ON CMA_Nombre = 'CMA_Inscripciones_PlazoDiasReinscripcion'
		INNER JOIN Programas ON PROG_ProgramaId = programaId
		WHERE
			INS_ALU_AlumnoId = alumnoId
			AND INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
			AND PROGRU_Activo = 1
			AND CAST(PROGRU_FechaFin AS DATE) < CAST(GETDATE() AS DATE)
			AND CAST(DATEADD(DAY,CAST(CMA_Valor AS int),PROGRU_FechaFin) AS DATE) > CAST(GETDATE() AS DATE)
			AND PROGI_NumeroNiveles > nivelActual+1

		-- ^^^^^^^^^^^^^^ --
		-- END SUBQUERY_2 -- (AlumnosReinscripciones)
		-- ^^^^^^^^^^^^^^ --
	) AS AlumnosListosReinscripcion
	INNER JOIN ProgramasGrupos AS GrupoAnterior
		ON GrupoAnterior.PROGRU_Nivel = nivelActual
		AND GrupoAnterior.PROGRU_PAMOD_ModalidadId = modalidadId
		AND GrupoAnterior.PROGRU_PAMODH_PAModalidadHorarioId = horarioId
		AND GrupoAnterior.PROGRU_Activo = 1
	INNER JOIN ProgramasIdiomas
		ON PROGI_ProgramaIdiomaId = GrupoAnterior.PROGRU_PROGI_ProgramaIdiomaId
		AND PROGI_CMM_Idioma = idiomaId
		AND PROGI_CMM_Idioma = idiomaId
	INNER JOIN Inscripciones ON INS_PROGRU_GrupoId = GrupoAnterior.PROGRU_GrupoId AND INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = modalidadId
	INNER JOIN Alumnos ON ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN Programas ON PROG_ProgramaId = programaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = idiomaId
	INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = horarioId
	INNER JOIN Articulos ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = modalidadId
	INNER JOIN VW_GRUPOS_ALUMNOS_CALIFICACION Calificaciones ON Calificaciones.alumnoId = AlumnosListosReinscripcion.alumnoId AND Calificaciones.grupoId = PROGRU_GrupoId
	WHERE
		INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId

GO