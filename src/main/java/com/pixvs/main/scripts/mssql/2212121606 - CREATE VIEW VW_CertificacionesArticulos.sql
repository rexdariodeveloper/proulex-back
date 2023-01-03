SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Rene Carrillo
-- Create date: 06/12/2022
-- Modify date:
-- Description:	La vista de Certificaciones con los Articulos
-- Version 1.0.0
-- =============================================

CREATE OR ALTER VIEW [dbo].[VW_CertificacionesArticulos]
AS
	SELECT pic.PROGIC_ProgramaIdiomaCertificacionId AS Id,
		pic.PROGIC_PROGI_ProgramaIdiomaId AS ProgramaIdiomaId,
		art.ART_NombreArticulo AS NombreArticulo,
		art.ART_CostoUltimo AS CostoUltimo
	FROM ProgramasIdiomasCertificacion pic
		INNER JOIN Articulos art ON pic.PROGIC_ART_ArticuloId = art.ART_ArticuloId AND art.ART_Activo = 1
	WHERE pic.PROGIC_Borrado = 0
		AND art.ART_ARTT_TipoArticuloId = (SELECT at.ARTT_ArticuloTipoId FROM ArticulosTipos at WHERE at.ARTT_Descripcion = 'Misceláneo')
		AND art.ART_ARTST_ArticuloSubtipoId = (SELECT ast.ARTST_ArticuloSubtipoId FROM ArticulosSubtipos ast WHERE ast.ARTST_Descripcion = 'Certificación')
GO