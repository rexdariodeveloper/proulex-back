SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE VIEW [dbo].[VW_LISTADO_WORKSHOPS]
WITH SCHEMABINDING
AS
	SELECT DISTINCT
		[PROGI_ProgramaIdiomaId] AS id,
		[CMM_Valor] AS tipo,
		[PROGI_Nombre] AS nombre,
		[PROGI_HorasTotales] AS horas,
		(SELECT COUNT(*) FROM [dbo].[ProgramasIdiomasModalidades] WHERE PROGIM_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId) AS modalidadesSize,
		(SELECT COUNT(*) FROM [dbo].[ProgramasIdiomasSucursales] WHERE PROGIS_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId) AS sucursalesSize,
		[PROGI_Activo] AS activo
	FROM [dbo].[ProgramasIdiomas]
	INNER JOIN [dbo].[Programas] ON [PROG_ProgramaId] = [PROGI_PROG_ProgramaId]
	INNER JOIN [dbo].[ControlesMaestrosMultiples] ON [PROGI_CMM_TipoWorkshopId] = [CMM_ControlId]
	WHERE PROG_Academy = 1
GO