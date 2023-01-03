SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ARTICULOS]
AS
     SELECT ART_CodigoArticulo AS "C�digo Art�culo",
            ART_NombreArticulo AS "Nombre Art�culo",
            ART_CodigoAlterno AS "C�digo Alterno",
            ART_NombreAlterno AS "Nombre Alterno",
            ART_DescripcionCorta AS "Descripci�n Corta",
            ART_Activo AS "Activo",
            ART_FechaCreacion AS "Fecha Creaci�n"
     FROM Articulos 
			INNER JOIN ArticulosTipos ON ART_ARTT_TipoArticuloId = ARTT_ArticuloTipoId AND ARTT_CMM_TipoId != 2000033
GO