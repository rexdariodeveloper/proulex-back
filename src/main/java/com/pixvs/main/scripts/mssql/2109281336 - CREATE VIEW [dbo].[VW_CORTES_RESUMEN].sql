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
		CONCAT(PROG_Codigo,' ',idioma.CMM_Valor) nombreCurso, 
		SUM(vw.curso) curso, 
		SUM(vw.libro) libro, 
		SUM(vw.examen) examen, 
		SUM(vw.otros) otros, 
		SUM(vw.total) importe
	FROM 
		[dbo].[VW_CORTES] vw
		INNER JOIN ProgramasGrupos on vw.grupoId = PROGRU_GrupoId
		INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		INNER JOIN Programas on PROGI_PROG_ProgramaId = PROG_ProgramaId
		INNER JOIN ControlesMaestrosMultiples idioma on PROGI_CMM_Idioma = idioma.CMM_ControlId
	GROUP BY
		vw.corteId, vw.fechaInicio, vw.fechaFin, CONCAT(PROG_Codigo,' ',idioma.CMM_Valor)
)
GO