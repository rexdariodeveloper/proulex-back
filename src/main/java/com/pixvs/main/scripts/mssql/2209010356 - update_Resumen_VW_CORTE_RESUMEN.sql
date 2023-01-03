SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_CORTE_RESUMEN]
AS
(
	SELECT
		vw.corteId,
		vw.fechaInicio,
		vw.fechaFin,
		(CASE WHEN claveCurso <> 'CURSO GUARDADO' THEN CONCAT(PROG_Codigo,' ',idioma.CMM_Valor) ELSE claveCurso END) nombreCurso,
		SUM(vw.curso) curso,
		SUM(vw.libro) libro,
		SUM(vw.examen) examen,
		SUM(vw.otros) otros,
		SUM(vw.total) importe,
		COUNT(*) AS totalCursos
	FROM
		[dbo].[VW_CORTES] vw
		LEFT JOIN ProgramasGrupos on vw.grupoId = PROGRU_GrupoId
		LEFT JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		LEFT JOIN Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
		LEFT JOIN ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
	WHERE
	    esCurso = 1
	GROUP BY
		vw.corteId, vw.fechaInicio, vw.fechaFin, (CASE WHEN claveCurso <> 'CURSO GUARDADO' THEN CONCAT(PROG_Codigo,' ',idioma.CMM_Valor) ELSE claveCurso END)
)
GO
