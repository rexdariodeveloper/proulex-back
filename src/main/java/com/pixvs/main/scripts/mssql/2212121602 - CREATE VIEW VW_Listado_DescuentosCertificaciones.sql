SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* ****************************************************************
 * VW_Listado_DescuentosCertificaciones
 * ****************************************************************
 * Descripción: La vista de el listado de Descuentos Certificaciones (ProgramasIdiomasCertificacionesDescuentos)
 * Autor: Rene Carrillo
 * Fecha: 29.11.2022
 * Fecha de Modificar:
 * Versión: 1.0.0
 *****************************************************************
*/
CREATE OR ALTER VIEW [dbo].[VW_Listado_DescuentosCertificaciones]
AS
	SELECT picd.PICD_ProgramaIdiomaCertificacionDescuentoId AS Id,
		CONCAT(p.PROG_Codigo, ' ', idioma.CMM_Valor) AS Curso,
		a.ART_NombreArticulo AS Certificacion,
		CAST(CASE picd.PICD_CMM_EstatusId WHEN 1000001 THEN 1 ELSE 0 END AS bit) AS Activo
	FROM ProgramasIdiomasCertificacionesDescuentos picd
		INNER JOIN ProgramasIdiomasCertificacion pic ON picd.PICD_PROGIC_ProgramaIdiomaCertificacionId = pic.PROGIC_ProgramaIdiomaCertificacionId
		INNER JOIN ProgramasIdiomas pi ON pic.PROGIC_PROGI_ProgramaIdiomaId = pi.PROGI_ProgramaIdiomaId
		INNER JOIN Programas p ON pi.PROGI_PROG_ProgramaId = p.PROG_ProgramaId
		INNER JOIN ControlesMaestrosMultiples idioma ON pi.PROGI_CMM_Idioma = idioma.CMM_ControlId
		INNER JOIN Articulos a ON pic.PROGIC_ART_ArticuloId = a.ART_ArticuloId
	WHERE picd.PICD_CMM_EstatusId = 1000001
GO