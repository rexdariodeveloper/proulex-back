/**
* Created by Angel Daniel Hernández Silva on 03/01/2022.
* Object: ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos]
*/

CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos](@sucursalId int, @textoBuscar varchar(255), @aprobados bit) RETURNS TABLE WITH SCHEMABINDING AS RETURN(

	SELECT
		ALU_AlumnoId AS id,
		ALU_Codigo AS codigo,
		BECU_BecaId AS becaId,
		COALESCE(BECU_CodigoBeca,'') AS becaCodigo,
		ALU_CodigoUDG AS codigoUDG,
		ALU_Nombre AS nombre,
		ALU_PrimerApellido AS primerApellido,
		ALU_SegundoApellido AS segundoApellido,
		CONCAT(PROG_Codigo,' ',CMM_Valor) AS curso,
		PAMOD_Nombre AS modalidad,
		PAMODH_Horario AS horario,
		nivelActual + 1 AS nivelReinscripcion,
		calificacion,
		PROGRU_CalificacionMinima AS calificacionMinima,
		CAST(COALESCE(limiteFaltasExcedido,0) AS bit) AS limiteFaltasExcedido,
		idiomaId,
		programaId,
		modalidadId,
		PAMODH_PAModalidadHorarioId AS horarioId,
		GrupoAnterior.PROGRU_SUC_SucursalId AS sucursalId,
		ART_ArticuloId AS articuloId,
		GrupoAnterior.PROGRU_Grupo AS numeroGrupo
	FROM (
		-- vvvvvvvvvvvvvvvv --
		-- START SUBQUERY_3 -- (AlumnosListosReinscripcion)
		-- vvvvvvvvvvvvvvvv --
	
		-- Filtramos los grupos que no han finalizado, los que tengan
		-- un plazo mayor al permitido para reinsccripciones y los que
		-- no tengan un siguiente nivel
	
		SELECT
			alumnoId,
			idiomaId,
			programaId,
			nivelActual,
			paModalidadId AS modalidadId,
			paModalidadHorarioId AS horarioId,
			GrupoReinscripcion.programaIdiomaId AS programaIdiomaId,
			calificacion,
			limiteFaltasExcedido,
			textoBuscarAlumno
		FROM (
			-- vvvvvvvvvvvvvvvv --
			-- START SUBQUERY_2 -- (AlumnosNivelActualFiltrados)
			-- vvvvvvvvvvvvvvvv --

			-- Filtramos alumnos que ya tengan inscripción

			SELECT
				alumnoId,
				idiomaId,
				programaId,
				nivelActual,
				programaIdiomaId,
				numeroNiveles,
				calificacion,
				limiteFaltasExcedido,
				textoBuscarAlumno
			FROM(

				-- vvvvvvvvvvvvvvvv --
				-- START SUBQUERY_1 -- (AlumnosNivelActual)
				-- vvvvvvvvvvvvvvvv --

				-- Obtenemos el último nivel cursado por idioma y programa
		
				SELECT
					ALU_AlumnoId AS alumnoId,
					PROGI_CMM_Idioma AS idiomaId,
					PROG_ProgramaId AS programaId,
					MAX(PROGRU_Nivel) AS nivelActual,
					PROGI_ProgramaIdiomaId as programaIdiomaId,
					PROGI_NumeroNiveles as numeroNiveles,
					ALUG_CalificacionFinal AS calificacion,
					CAST(CASE WHEN ALUG_CMM_EstatusId NOT IN (2000671,2000672,2000673,2000678) THEN 1 ELSE 0 END AS bit) AS limiteFaltasExcedido,
					CONCAT(ALU_Codigo,'|',ALU_CodigoUDG,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre) AS textoBuscarAlumno
				FROM [dbo].[Alumnos]
				INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
				INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
				INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
				INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
				LEFT JOIN [dbo].[InscripcionesSinGrupo]
					ON INSSG_ALU_AlumnoId = ALU_AlumnoId
					AND INSSG_CMM_IdiomaId = PROGI_CMM_Idioma
					AND INSSG_CMM_EstatusId IN (2000540,2000541)
				INNER JOIN [dbo].[AlumnosGrupos] ON ALUG_ALU_AlumnoId = ALU_AlumnoId AND ALUG_PROGRU_GrupoId = PROGRU_GrupoId
				WHERE
					ALU_Activo = 1
					AND INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
					AND PROGRU_CMM_EstatusId = 2000621 -- Finalizado
					AND CONCAT(ALU_Codigo,'|',ALU_CodigoUDG,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre) LIKE @textoBuscar
					AND PROGRU_SUC_SucursalId = @sucursalId
					AND (
						(@aprobados = 1 AND (ALUG_CMM_EstatusId IN (2000676)))
						OR (@aprobados = 0 AND (ALUG_CMM_EstatusId IN (2000671,2000672,2000673,2000677,2000678)))
					)
				GROUP BY
					ALU_AlumnoId, ALU_Codigo, ALU_CodigoUDG, ALU_Nombre, ALU_PrimerApellido, ALU_SegundoApellido,
					PROGI_CMM_Idioma, PROG_ProgramaId, PROGI_ProgramaIdiomaId, PROGI_NumeroNiveles, ALUG_CalificacionFinal, ALUG_CMM_EstatusId
				HAVING MAX(INSSG_InscripcionId) IS NULL
		
				-- ^^^^^^^^^^^^^^ --
				-- END SUBQUERY_1 -- (AlumnosNivelActual)
				-- ^^^^^^^^^^^^^^ --

			) AS AlumnosNivelActual
			LEFT JOIN(
				SELECT
					INS_InscripcionId,
					INS_ALU_AlumnoId,
					PROGI_CMM_Idioma
				FROM [dbo].[Inscripciones]
				INNER JOIN [dbo].[ProgramasGrupos]
					ON PROGRU_GrupoId = INS_PROGRU_GrupoId
					AND PROGRU_Activo = 1
					AND PROGRU_CMM_EstatusId = 2000620 -- Activo
				INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
			) AS InscripcionesActivas ON INS_ALU_AlumnoId = alumnoId AND PROGI_CMM_Idioma = idiomaId
			LEFT JOIN [dbo].[InscripcionesSinGrupo]
				ON INSSG_ALU_AlumnoId = alumnoId
				AND INSSG_CMM_IdiomaId = idiomaId
				AND INSSG_CMM_EstatusId IN (2000540,2000541)
			WHERE
				INSSG_InscripcionId IS NULL
				AND INS_InscripcionId IS NULL

			-- vvvvvvvvvvvvvv --
			-- END SUBQUERY_3 -- (AlumnosNivelActualFiltrados)
			-- vvvvvvvvvvvvvv --
		) AS AlumnosNivelActual 
		INNER JOIN [dbo].[VW_ProgramasGrupos] GrupoReinscripcion
			ON GrupoReinscripcion.programaIdiomaId = AlumnosNivelActual.programaIdiomaId
			AND nivel = nivelActual
		INNER JOIN [dbo].[Inscripciones]
			ON INS_PROGRU_GrupoId = id
			AND INS_ALU_AlumnoId = alumnoId
			AND INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
		INNER JOIN [dbo].[ControlesMaestros] ON CMA_Nombre = 'CMA_Inscripciones_PlazoDiasReinscripcion'
		INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = programaId
		WHERE
			numeroNiveles >= nivelActual+1
			AND fechaLimiteReinscripcion >= GETDATE()

		-- ^^^^^^^^^^^^^^ --
		-- END SUBQUERY_2 -- (AlumnosListosReinscripcion)
		-- ^^^^^^^^^^^^^^ --
	) AS AlumnosListosReinscripcion
	INNER JOIN [dbo].[ProgramasGrupos] AS GrupoAnterior
		ON GrupoAnterior.PROGRU_Nivel = nivelActual
		AND GrupoAnterior.PROGRU_PAMOD_ModalidadId = modalidadId
		AND GrupoAnterior.PROGRU_PAMODH_PAModalidadHorarioId = horarioId
		AND GrupoAnterior.PROGRU_PROGI_ProgramaIdiomaId = programaIdiomaId
		AND GrupoAnterior.PROGRU_CMM_EstatusId = 2000621 -- Finalizado
	INNER JOIN [dbo].[ProgramasIdiomas]
		ON PROGI_ProgramaIdiomaId = programaIdiomaId
		AND PROGI_CMM_Idioma = idiomaId
	INNER JOIN [dbo].[Inscripciones] ON INS_PROGRU_GrupoId = GrupoAnterior.PROGRU_GrupoId AND INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN [dbo].[PAModalidades] ON PAMOD_ModalidadId = modalidadId
	INNER JOIN [dbo].[Alumnos] ON ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = programaId
	INNER JOIN [dbo].[ControlesMaestrosMultiples] ON CMM_ControlId = idiomaId
	INNER JOIN [dbo].[PAModalidadesHorarios] ON PAMODH_PAModalidadHorarioId = horarioId
	INNER JOIN [dbo].[Articulos] ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = modalidadId
	LEFT JOIN [dbo].[BecasUDG]
		ON BECU_CodigoEmpleado = ALU_CodigoUDG
		AND BECU_Nombre = ALU_Nombre
		AND BECU_PrimerApellido = BECU_PrimerApellido
		AND(
			(BECU_SegundoApellido IS NULL AND ALU_SegundoApellido IS NULL)
			OR BECU_SegundoApellido = ALU_SegundoApellido
		)
		AND BECU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		AND BECU_PAMOD_ModalidadId = PAMOD_ModalidadId
		AND BECU_Nivel = nivelActual + 1
		AND calificacion >= PROGRU_CalificacionMinima
		AND limiteFaltasExcedido = 0
	WHERE
		INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
		AND GrupoAnterior.PROGRU_SUC_SucursalId = @sucursalId
		AND CONCAT(textoBuscarAlumno,'|',COALESCE(BECU_CodigoBeca,''),'|',CONCAT(PROG_Codigo,' ',CMM_Valor),'|',PAMOD_Nombre,'|',PAMODH_Horario) LIKE @textoBuscar
)
GO