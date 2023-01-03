SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		Rene Carrillo
-- Create date: 06/12/2022
-- Modify date: 29/12/2022
-- Description:	La vista de Certificaciones con los Articulos
-- Version 1.0.2
-- =============================================

ALTER   VIEW [dbo].[VW_CertificacionesArticulos]
AS
	SELECT DISTINCT pic.PROGIC_ProgramaIdiomaCertificacionId AS Id,
		pic.PROGIC_PROGI_ProgramaIdiomaId AS ProgramaIdiomaId,
		art.ART_NombreArticulo AS NombreArticulo,
		lp.Precio AS CostoUltimo
	FROM ProgramasIdiomasCertificacion pic
		INNER JOIN Articulos art ON pic.PROGIC_ART_ArticuloId = art.ART_ArticuloId AND art.ART_Activo = 1
		LEFT JOIN (
			SELECT lp.LIPRE_FechaInicio AS FechaInicio,
				lp.LIPRE_FechaFin AS FechaFin,
				lpd.LIPRED_ART_ArticuloId AS ArticuloId,
				lpd.LIPRED_Precio AS Precio
			FROM ListadosPrecios lp
				INNER JOIN ListadosPreciosDetalles lpd ON lp.LIPRE_ListadoPrecioId = lpd.LIPRED_LIPRE_ListadoPrecioId
			WHERE lp.LIPRE_Activo = 1
		) AS lp ON art.ART_ArticuloId = lp.ArticuloId
	WHERE pic.PROGIC_Borrado = 0
		AND art.ART_ARTT_TipoArticuloId = (SELECT at.ARTT_ArticuloTipoId FROM ArticulosTipos at WHERE at.ARTT_Descripcion = 'Misceláneo')
		AND art.ART_ARTST_ArticuloSubtipoId = (SELECT ast.ARTST_ArticuloSubtipoId FROM ArticulosSubtipos ast WHERE ast.ARTST_Descripcion = 'Certificación')
GO