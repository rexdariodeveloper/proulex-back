/**
* Created by Angel Daniel Hernández Silva on 06/01/2021.
* Object:  ALTER VIEW [dbo].[VW_CardProjection_ProgramasGrupos]
*/

CREATE OR ALTER VIEW [dbo].[VW_CardProjection_ProgramasGrupos] AS

	SELECT
		PROGRU_GrupoId AS id,
		CONCAT(PROG_Codigo,' ') + CMM_Valor + ' ' + PAMOD_Nombre + ' ' + CAST(PROGRU_Nivel AS varchar(10)) AS nombre,
		PROGRU_Grupo AS numeroGrupo,
		PROGRU_FechaInicio AS fechaInicio,
		PROGRU_FechaFin AS fechaFin,
		PAMODH_Horario AS horario,
		PAMOD_Color AS color,
		ART_ArticuloId AS articuloId,
		PROGRU_Nivel AS nivel,
		PROGI_CMM_Idioma AS idiomaId,
		PROG_ProgramaId AS programaId,
		PAMOD_ModalidadId AS modalidadId,
		CASE WHEN (PROGRU_Cupo - COUNT(INS_InscripcionId)) > 0 THEN CAST(1 AS bit) ELSE CAST(0 AS bit) END AS permiteInscripcion,
		PROGRU_Multisede AS esMultisede,
		SUC_SucursalId AS sucursalId,
		SUC_Nombre AS sucursalNombre,
		(PROGRU_Cupo - COUNT(INS_InscripcionId)) AS cupoDisponible,
		PROGRU_CMM_TipoGrupoId AS tipoGrupoId,
		PROGRU_FechaFinInscripciones  AS fechaFinInscripciones,
		PROGRU_FechaFinInscripcionesBecas  AS fechaFinInscripcionesBecas
	FROM ProgramasGrupos
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
	INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	INNER JOIN Articulos ON ART_PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = PAMOD_ModalidadId
	LEFT JOIN Inscripciones ON INS_PROGRU_GrupoId = PROGRU_GrupoId AND INS_CMM_EstatusId IN (2000510,2000511)
	INNER JOIN Sucursales ON SUC_SucursalId = PROGRU_SUC_SucursalId
	WHERE
		PROGRU_FechaFin > GETDATE()
		AND PROGRU_Activo = 1
	GROUP BY
		PROGRU_GrupoId, PROG_Codigo, CMM_Valor, PAMOD_Nombre, PROGRU_Nivel, PROGRU_Grupo, PROGRU_FechaInicio,
		PROGRU_FechaFin, PAMODH_Horario, PAMOD_Color, ART_ArticuloId, PROGRU_Nivel, PROGI_CMM_Idioma, PROG_ProgramaId,
		PAMOD_ModalidadId, PROGRU_Cupo, PROGRU_Multisede, SUC_SucursalId, SUC_Nombre, PROGRU_CMM_TipoGrupoId,
		PROGRU_FechaFinInscripciones, PROGRU_FechaFinInscripcionesBecas

GO