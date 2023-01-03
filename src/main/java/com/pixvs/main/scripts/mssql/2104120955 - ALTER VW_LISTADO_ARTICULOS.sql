SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ARTICULOS]
AS
     SELECT ART_CodigoArticulo AS "Código Artículo",
            ART_NombreArticulo AS "Nombre Artículo",
            ART_CodigoAlterno AS "Código Alterno",
            ART_NombreAlterno AS "Nombre Alterno",
            ART_DescripcionCorta AS "Descripción Corta",
            ART_Activo AS "Activo",
            ART_FechaCreacion AS "Fecha Creación"
     FROM Articulos 
			INNER JOIN ArticulosTipos ON ART_ARTT_TipoArticuloId = ARTT_ArticuloTipoId AND ARTT_CMM_TipoId != 2000033
GO