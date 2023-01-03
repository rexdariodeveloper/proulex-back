SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_PROGRAMAS_IDIOMAS]
AS
	SELECT DISTINCT
	PROGI_ProgramaIdiomaId as id,
	CONCAT(PROG_Codigo,'',CMM_Referencia) as codigo,
	CONCAT(PROG_Codigo,' ',CMM_Valor) as nombre,
	PROGI_HorasTotales as horasTotales,
	PROGI_NumeroNiveles as numeroNiveles,
	(SELECT COUNT(*) FROM ProgramasIdiomasSucursales WHERE PROGIS_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId) as sucursalesSize,
	(SELECT COUNT(*) FROM ProgramasIdiomasModalidades WHERE PROGIM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId) as modalidadesSize,
	PROGI_Activo as activo
	FROM ProgramasIdiomas
	INNER JOIN Programas on PROG_ProgramaId = PROGI_PROG_ProgramaId
	INNER JOIN ControlesMaestrosMultiples on CMM_ControlId = PROGI_CMM_Idioma
GO