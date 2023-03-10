SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/**
* Created by Angel Daniel Hernández Silva on 20/04/2022.
* Object: ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos_Reprobados]
*/

CREATE OR ALTER FUNCTION [dbo].[fn_ReinscripcionProjection_Alumnos_Reprobados](@sucursalId int, @textoBuscar varchar(255)) RETURNS TABLE WITH SCHEMABINDING AS RETURN(
	SELECT fechaFinInscripciones,
		id,
		codigo,
		NULL AS becaId,
		'' AS becaCodigo,
		codigoUDG,
		nombre,
		primerApellido,
		segundoApellido,
		CONCAT(PROG_Codigo,' ',CMM_Valor) AS curso,
		PAMOD_Nombre AS modalidad,
		PAMODH_Horario AS horario,
		nivel AS nivelReinscripcion,
		ALUG_CalificacionFinal AS calificacion,
		calificacionMinima,
		CAST(COALESCE(CAST(CASE WHEN ALUG_CMM_EstatusId NOT IN (2000673,2000674) THEN 1 ELSE 0 END AS bit),0) AS bit) AS limiteFaltasExcedido,
		PROGI_CMM_Idioma AS idiomaId,
		PROG_ProgramaId AS programaId,
		modalidadId,
		horarioId,
		sucursalId,
		ART_ArticuloId AS articuloId,
		numeroGrupo
	FROM(
		SELECT
			ALU_AlumnoId AS id,
			ALU_Codigo AS codigo,
			ALU_CodigoUDG AS codigoUDG,
			ALU_Nombre AS nombre,
			ALU_PrimerApellido AS primerApellido,
			ALU_SegundoApellido AS segundoApellido,
			PROGRU_GrupoId AS grupoId,
			PROGRU_PROGI_ProgramaIdiomaId AS cursoId,
			PROGRU_Nivel AS nivel,
			INS_FechaCreacion AS fechaCreacionInscripcion,
			MAX(INS_FechaCreacion) OVER(PARTITION BY ALU_AlumnoId,PROGRU_PROGI_ProgramaIdiomaId) AS fechaCreacionUltimaInscripcion,
			PROGRU_PAMOD_ModalidadId AS modalidadId,
			PROGRU_PAMODH_PAModalidadHorarioId AS horarioId,
			PROGRU_SUC_SucursalId AS sucursalId,
			PROGRU_Grupo AS numeroGrupo,
			PROGRU_FechaFinInscripciones AS fechaFinInscripciones,
			PROGRU_CalificacionMinima AS calificacionMinima
		FROM [dbo].[Alumnos]
		INNER JOIN [dbo].[Inscripciones] ON INS_ALU_AlumnoId = ALU_AlumnoId
		INNER JOIN [dbo].[ProgramasGrupos] ON PROGRU_GrupoId = INS_PROGRU_GrupoId
		WHERE
			ALU_Activo = 1
			AND INS_CMM_EstatusId IN (2000510,2000511)
	) AS AlumnosCursos
	INNER JOIN [dbo].[AlumnosGrupos] ON ALUG_ALU_AlumnoId = id AND ALUG_PROGRU_GrupoId = grupoId
	INNER JOIN [dbo].[ProgramasIdiomas] ON PROGI_ProgramaIdiomaId = cursoId
	LEFT JOIN [dbo].[InscripcionesSinGrupo]
		ON INSSG_ALU_AlumnoId = id
		AND INSSG_CMM_IdiomaId = INSSG_CMM_IdiomaId
		AND INSSG_CMM_EstatusId IN (2000540,2000541)
	INNER JOIN [dbo].[Programas] ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN [dbo].[ControlesMaestrosMultiples] ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN [dbo].[PAModalidades] ON PAMOD_ModalidadId = modalidadId
	INNER JOIN [dbo].[PAModalidadesHorarios] ON PAMODH_PAModalidadHorarioId = horarioId
	INNER JOIN [dbo].[Articulos] ON ART_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = modalidadId
	WHERE
		fechaCreacionInscripcion = fechaCreacionUltimaInscripcion
		AND ALUG_CMM_EstatusId IN (2000673,2000674,2000676) -- (Sin derecho, Desertor o Reprobado)
		AND INSSG_InscripcionId IS NULL
		AND fechaFinInscripciones >= CAST(GETDATE() AS date)
		AND sucursalId = @sucursalId
		AND CONCAT(codigo,'|',codigoUDG,'|',nombre,' ',primerApellido,' ' + segundoApellido,' ',nombre,'|',CONCAT(PROG_Codigo,' ',CMM_Valor),'|',PAMOD_Nombre,'|',PAMODH_Horario) LIKE @textoBuscar
)
