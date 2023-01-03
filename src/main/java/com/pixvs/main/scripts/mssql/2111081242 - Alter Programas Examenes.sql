CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos](@sucursalId int, @textoBuscar varchar(255), @aprobados bit) RETURNS TABLE AS RETURN(

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
		Calificaciones.calificacion AS calificacion,
		PROGRU_CalificacionMinima AS calificacionMinima,
		COALESCE(limiteFaltasExcedido,0) AS limiteFaltasExcedido,
		idiomaId,
		programaId,
		modalidadId,
		PAMODH_PAModalidadHorarioId AS horarioId,
		GrupoAnterior.PROGRU_SUC_SucursalId AS sucursalId,
		ART_ArticuloId AS articuloId,
		GrupoAnterior.PROGRU_Grupo AS numeroGrupo
	FROM (
		-- vvvvvvvvvvvvvvvv --
		-- START SUBQUERY_2 -- (AlumnosListosReinscripcion)
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
			paModalidadHorarioId AS horarioId
		FROM (
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
					PROGI_NumeroNiveles as numeroNiveles
				FROM [dbo].[Alumnos]
				INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
				INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
				INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
				INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
				INNER JOIN [dbo].[InscripcionesSinGrupo]
					ON INSSG_ALU_AlumnoId = ALU_AlumnoId
					AND INSSG_CMM_IdiomaId = PROGI_CMM_Idioma
					AND INSSG_CMM_EstatusId IN (2000540,2000541)
				WHERE
					INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
					AND PROGRU_CMM_EstatusId = 2000620 -- Activo
					AND CONCAT(ALU_Codigo,'|',ALU_CodigoUDG,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre) = @textoBuscar
				GROUP BY ALU_AlumnoId, PROGI_CMM_Idioma, PROG_ProgramaId, PROGI_ProgramaIdiomaId, PROGI_NumeroNiveles
				HAVING MAX(INSSG_InscripcionId) IS NULL
		
			-- ^^^^^^^^^^^^^^ --
			-- END SUBQUERY_1 -- (AlumnosNivelActual)
			-- ^^^^^^^^^^^^^^ --
		) AS AlumnosNivelActual 
		INNER JOIN [dbo].[VW_ProgramasGrupos] GrupoReinscripcion ON AlumnosNivelActual.programaIdiomaId = GrupoReinscripcion.programaIdiomaId 
														AND nivel = nivelActual
		INNER JOIN [dbo].[Inscripciones] ON INS_PROGRU_GrupoId = id AND INS_ALU_AlumnoId = alumnoId
		INNER JOIN [dbo].[ControlesMaestros] ON CMA_Nombre = 'CMA_Inscripciones_PlazoDiasReinscripcion'
		INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = programaId
		WHERE
			INS_ALU_AlumnoId = alumnoId
			AND INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
			AND numeroNiveles >= nivelActual+1
			AND fechaLimiteReinscripcion >= GETDATE()

		-- ^^^^^^^^^^^^^^ --
		-- END SUBQUERY_2 -- (AlumnosListosReinscripcion)
		-- ^^^^^^^^^^^^^^ --
	) AS AlumnosListosReinscripcion
	INNER JOIN [dbo].[ProgramasGrupos] AS GrupoAnterior
		ON GrupoAnterior.PROGRU_Nivel = nivelActual
		AND GrupoAnterior.PROGRU_PAMOD_ModalidadId = modalidadId
		AND GrupoAnterior.PROGRU_PAMODH_PAModalidadHorarioId = horarioId
		AND GrupoAnterior.PROGRU_CMM_EstatusId = 2000620 -- Activo
	INNER JOIN [dbo].[ProgramasIdiomas]
		ON PROGI_ProgramaIdiomaId = GrupoAnterior.PROGRU_PROGI_ProgramaIdiomaId
		AND PROGI_CMM_Idioma = idiomaId
	INNER JOIN [dbo].[Inscripciones] ON INS_PROGRU_GrupoId = GrupoAnterior.PROGRU_GrupoId AND INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN [dbo].[PAModalidades] ON PAMOD_ModalidadId = modalidadId
	INNER JOIN [dbo].[Alumnos] ON ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = programaId
	INNER JOIN [dbo].[ControlesMaestrosMultiples] ON CMM_ControlId = idiomaId
	INNER JOIN [dbo].[PAModalidadesHorarios] ON PAMODH_PAModalidadHorarioId = horarioId
	INNER JOIN [dbo].[Articulos] ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = modalidadId
	INNER JOIN [dbo].[VW_GRUPOS_ALUMNOS_CALIFICACION] Calificaciones ON Calificaciones.alumnoId = AlumnosListosReinscripcion.alumnoId AND Calificaciones.grupoId = PROGRU_GrupoId
	LEFT JOIN [dbo].[VW_ALUMNOS_GRUPOS_FALTAS] AS Faltas ON Faltas.alumnoId = AlumnosListosReinscripcion.alumnoId AND Faltas.grupoId =  PROGRU_GrupoId
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
		AND Calificaciones.calificacion >= PROGRU_CalificacionMinima
		AND limiteFaltasExcedido = 0
	WHERE
		INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
		AND GrupoAnterior.PROGRU_SUC_SucursalId = @sucursalId
		AND CONCAT(COALESCE(BECU_CodigoBeca,''),'|',CONCAT(PROG_Codigo,' ',CMM_Valor),'|',PAMOD_Nombre,'|',PAMODH_Horario) = @textoBuscar
		AND (
			(@aprobados = 1 AND (Calificaciones.calificacion >= PROGRU_CalificacionMinima AND limiteFaltasExcedido = 0))
			OR (@aprobados = 0 AND (Calificaciones.calificacion < PROGRU_CalificacionMinima OR limiteFaltasExcedido = 1))
		)
)
GO

CREATE OR ALTER VIEW [dbo].[VW_GRUPOS_ALUMNOS_CALIFICACION]
AS
	SELECT
		AEC_ALU_AlumnoId alumnoId,
		AEC_PROGRU_GrupoId grupoId,
		ROUND(SUM(AEC_Puntaje * puntos), 2) calificacion
	FROM
		[dbo].[AlumnosExamenesCalificaciones]
		INNER JOIN [dbo].[ProgramasIdiomasExamenesDetalles] ON AEC_PROGIED_ProgramaIdiomaExamenDetalleId = PROGIED_ProgramaIdiomaExamenDetalleId
		INNER JOIN
		(
			SELECT
				PROGIE_ProgramaIdiomaExamenId id, 
				MAX(PROGIE_Porcentaje) porcentaje,
				SUM(PROGIED_Puntaje) puntaje,
				ROUND(CAST(MAX(PROGIE_Porcentaje) AS decimal(28,10)) / CAST(SUM(PROGIED_Puntaje) AS decimal(28,10)),4) puntos
			FROM
				[dbo].[ProgramasIdiomasExamenes] 
				INNER JOIN [dbo].[ProgramasIdiomasExamenesDetalles] ON PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId
			GROUP BY
				PROGIE_ProgramaIdiomaExamenId
		) t ON t.id = PROGIED_PROGIE_ProgramaIdiomaExamenId
		GROUP BY AEC_ALU_AlumnoId, AEC_PROGRU_GrupoId
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenes]
ALTER COLUMN [PROGIE_Porcentaje][decimal](10,2) NOT NULL
GO

ALTER TABLE [dbo].[ProgramasIdiomasExamenesDetalles]
ALTER COLUMN [PROGIED_Puntaje][decimal](10,2) NOT NULL
GO

CREATE OR ALTER VIEW [dbo].[VW_GRUPOS_ALUMNOS_CALIFICACION] WITH SCHEMABINDING
AS
	SELECT
		AEC_ALU_AlumnoId alumnoId,
		AEC_PROGRU_GrupoId grupoId,
		ROUND(SUM(AEC_Puntaje * puntos), 2) calificacion
	FROM
		[dbo].[AlumnosExamenesCalificaciones]
		INNER JOIN [dbo].[ProgramasIdiomasExamenesDetalles] ON AEC_PROGIED_ProgramaIdiomaExamenDetalleId = PROGIED_ProgramaIdiomaExamenDetalleId
		INNER JOIN
		(
			SELECT
				PROGIE_ProgramaIdiomaExamenId id, 
				MAX(PROGIE_Porcentaje) porcentaje,
				SUM(PROGIED_Puntaje) puntaje,
				ROUND(CAST(MAX(PROGIE_Porcentaje) AS decimal(28,10)) / CAST(SUM(PROGIED_Puntaje) AS decimal(28,10)),4) puntos
			FROM
				[dbo].[ProgramasIdiomasExamenes] 
				INNER JOIN [dbo].[ProgramasIdiomasExamenesDetalles] ON PROGIE_ProgramaIdiomaExamenId = PROGIED_PROGIE_ProgramaIdiomaExamenId
			GROUP BY
				PROGIE_ProgramaIdiomaExamenId
		) t ON t.id = PROGIED_PROGIE_ProgramaIdiomaExamenId
		GROUP BY AEC_ALU_AlumnoId, AEC_PROGRU_GrupoId
GO

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
		Calificaciones.calificacion AS calificacion,
		PROGRU_CalificacionMinima AS calificacionMinima,
		COALESCE(limiteFaltasExcedido,0) AS limiteFaltasExcedido,
		idiomaId,
		programaId,
		modalidadId,
		PAMODH_PAModalidadHorarioId AS horarioId,
		GrupoAnterior.PROGRU_SUC_SucursalId AS sucursalId,
		ART_ArticuloId AS articuloId,
		GrupoAnterior.PROGRU_Grupo AS numeroGrupo
	FROM (
		-- vvvvvvvvvvvvvvvv --
		-- START SUBQUERY_2 -- (AlumnosListosReinscripcion)
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
			paModalidadHorarioId AS horarioId
		FROM (
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
					PROGI_NumeroNiveles as numeroNiveles
				FROM [dbo].[Alumnos]
				INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
				INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
				INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
				INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
				INNER JOIN [dbo].[InscripcionesSinGrupo]
					ON INSSG_ALU_AlumnoId = ALU_AlumnoId
					AND INSSG_CMM_IdiomaId = PROGI_CMM_Idioma
					AND INSSG_CMM_EstatusId IN (2000540,2000541)
				WHERE
					INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
					AND PROGRU_CMM_EstatusId = 2000620 -- Activo
					AND CONCAT(ALU_Codigo,'|',ALU_CodigoUDG,'|',ALU_Nombre,' ',ALU_PrimerApellido,' ' + ALU_SegundoApellido,' ',ALU_Nombre) = @textoBuscar
				GROUP BY ALU_AlumnoId, PROGI_CMM_Idioma, PROG_ProgramaId, PROGI_ProgramaIdiomaId, PROGI_NumeroNiveles
				HAVING MAX(INSSG_InscripcionId) IS NULL
		
			-- ^^^^^^^^^^^^^^ --
			-- END SUBQUERY_1 -- (AlumnosNivelActual)
			-- ^^^^^^^^^^^^^^ --
		) AS AlumnosNivelActual 
		INNER JOIN [dbo].[VW_ProgramasGrupos] GrupoReinscripcion ON AlumnosNivelActual.programaIdiomaId = GrupoReinscripcion.programaIdiomaId 
														AND nivel = nivelActual
		INNER JOIN [dbo].[Inscripciones] ON INS_PROGRU_GrupoId = id AND INS_ALU_AlumnoId = alumnoId
		INNER JOIN [dbo].[ControlesMaestros] ON CMA_Nombre = 'CMA_Inscripciones_PlazoDiasReinscripcion'
		INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = programaId
		WHERE
			INS_ALU_AlumnoId = alumnoId
			AND INS_CMM_EstatusId IN (2000510,2000511) -- Solo inscripciones pagadas y pendientes de pago
			AND numeroNiveles >= nivelActual+1
			AND fechaLimiteReinscripcion >= GETDATE()

		-- ^^^^^^^^^^^^^^ --
		-- END SUBQUERY_2 -- (AlumnosListosReinscripcion)
		-- ^^^^^^^^^^^^^^ --
	) AS AlumnosListosReinscripcion
	INNER JOIN [dbo].[ProgramasGrupos] AS GrupoAnterior
		ON GrupoAnterior.PROGRU_Nivel = nivelActual
		AND GrupoAnterior.PROGRU_PAMOD_ModalidadId = modalidadId
		AND GrupoAnterior.PROGRU_PAMODH_PAModalidadHorarioId = horarioId
		AND GrupoAnterior.PROGRU_CMM_EstatusId = 2000620 -- Activo
	INNER JOIN [dbo].[ProgramasIdiomas]
		ON PROGI_ProgramaIdiomaId = GrupoAnterior.PROGRU_PROGI_ProgramaIdiomaId
		AND PROGI_CMM_Idioma = idiomaId
	INNER JOIN [dbo].[Inscripciones] ON INS_PROGRU_GrupoId = GrupoAnterior.PROGRU_GrupoId AND INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN [dbo].[PAModalidades] ON PAMOD_ModalidadId = modalidadId
	INNER JOIN [dbo].[Alumnos] ON ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
	INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = programaId
	INNER JOIN [dbo].[ControlesMaestrosMultiples] ON CMM_ControlId = idiomaId
	INNER JOIN [dbo].[PAModalidadesHorarios] ON PAMODH_PAModalidadHorarioId = horarioId
	INNER JOIN [dbo].[Articulos] ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = modalidadId
	INNER JOIN [dbo].[VW_GRUPOS_ALUMNOS_CALIFICACION] Calificaciones ON Calificaciones.alumnoId = AlumnosListosReinscripcion.alumnoId AND Calificaciones.grupoId = PROGRU_GrupoId
	LEFT JOIN [dbo].[VW_ALUMNOS_GRUPOS_FALTAS] AS Faltas ON Faltas.alumnoId = AlumnosListosReinscripcion.alumnoId AND Faltas.grupoId =  PROGRU_GrupoId
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
		AND Calificaciones.calificacion >= PROGRU_CalificacionMinima
		AND limiteFaltasExcedido = 0
	WHERE
		INS_ALU_AlumnoId = AlumnosListosReinscripcion.alumnoId
		AND GrupoAnterior.PROGRU_SUC_SucursalId = @sucursalId
		AND CONCAT(COALESCE(BECU_CodigoBeca,''),'|',CONCAT(PROG_Codigo,' ',CMM_Valor),'|',PAMOD_Nombre,'|',PAMODH_Horario) = @textoBuscar
		AND (
			(@aprobados = 1 AND (Calificaciones.calificacion >= PROGRU_CalificacionMinima AND limiteFaltasExcedido = 0))
			OR (@aprobados = 0 AND (Calificaciones.calificacion < PROGRU_CalificacionMinima OR limiteFaltasExcedido = 1))
		)
)
GO