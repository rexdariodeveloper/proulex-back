SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		Rene Carrillo
-- Create date: 12/12/2022
-- Modify date:
-- Description:	La vista de el combo de programa idioma con descuento certificacion
-- Version 1.0.0
-- =============================================

CREATE OR ALTER VIEW [dbo].[VW_CMO_ComboProgramaIdiomaDescuentoCertificacion]
AS
	SELECT DISTINCT pi.PROGI_ProgramaIdiomaId AS Id,
		CONCAT(p.PROG_Codigo, ' ', idioma.CMM_Valor) AS Idioma,
		pi.PROGI_NumeroNiveles AS NumeroNiveles
	FROM ProgramasIdiomas pi
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		LEFT JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId AND p.PROG_Activo = 1
		LEFT JOIN VW_CertificacionesArticulos certificacion ON pi.PROGI_ProgramaIdiomaId = certificacion.ProgramaIdiomaId
		LEFT JOIN ProgramasIdiomasCertificacionesDescuentos picd ON certificacion.Id = picd.PICD_PROGIC_ProgramaIdiomaCertificacionId
	WHERE certificacion.Id IS NOT NULL AND picd.PICD_ProgramaIdiomaCertificacionDescuentoId IS NULL
GO


