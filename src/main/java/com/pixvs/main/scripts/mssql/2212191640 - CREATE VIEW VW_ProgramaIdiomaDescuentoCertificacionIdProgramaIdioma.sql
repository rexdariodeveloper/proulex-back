SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 19/12/2022
-- Modify date:
-- Description:	La vista de el combo de programa idioma con descuento certificacion con el ID de Programa Idioma
-- Version 1.0.0
-- =============================================

CREATE VIEW [dbo].[VW_ProgramaIdiomaDescuentoCertificacionIdProgramaIdioma]
AS
	SELECT DISTINCT pi.PROGI_ProgramaIdiomaId AS Id,
		CONCAT(p.PROG_Codigo, ' ', idioma.CMM_Valor) AS Idioma,
		pi.PROGI_NumeroNiveles AS NumeroNiveles
	FROM ProgramasIdiomas pi
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		LEFT JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId AND p.PROG_Activo = 1
GO


