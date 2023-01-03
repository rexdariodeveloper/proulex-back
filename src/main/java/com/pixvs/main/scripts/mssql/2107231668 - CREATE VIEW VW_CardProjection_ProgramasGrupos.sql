/**
* Created by Angel Daniel Hernández Silva on 08/07/2021.
* Object:  CREATE VIEW [dbo].[VW_CardProjection_ProgramasGrupos]
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
		CASE WHEN (PROGRU_Cupo - COUNT(INS_InscripcionId)) > 0 THEN CAST(1 AS bit) ELSE CAST(0 AS bit) END AS permiteInscripcion
	FROM ProgramasGrupos
	INNER JOIN ProgramasIdiomas ON PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PROGI_CMM_Idioma
	INNER JOIN PAModalidades ON PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
	INNER JOIN PAModalidadesHorarios ON PAMODH_PAModalidadHorarioId = PROGRU_PAMODH_PAModalidadHorarioId
	INNER JOIN Articulos ON ART_PROGI_ProgramaIdiomaId = PROGRU_PROGI_ProgramaIdiomaId AND ART_PAMOD_ModalidadId = PAMOD_ModalidadId
	LEFT JOIN Inscripciones ON INS_PROGRU_GrupoId = PROGRU_GrupoId AND INS_CMM_EstatusId IN (2000510,2000511)
	WHERE DATEADD(DAY,PAMOD_DiasFinPeriodoInscripcion,PROGRU_FechaInicio) >= GETDATE()
	GROUP BY PROGRU_GrupoId, PROG_Codigo, CMM_Valor, PAMOD_Nombre, PROGRU_Nivel, PROGRU_Grupo, PROGRU_FechaInicio, PROGRU_FechaFin, PAMODH_Horario, PAMOD_Color, ART_ArticuloId, PROGRU_Nivel, PROGI_CMM_Idioma, PROG_ProgramaId, PAMOD_ModalidadId, PROGRU_Cupo

GO