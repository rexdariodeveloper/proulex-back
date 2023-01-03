SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/** 
	Name: 
		VW_LISTADO_ARTICULOS_EXISTENCIAS
	Description:
		Returns a list of tuples (articuloId, localidadId, existencia). 
		If the system hasn't any relation on LocalidadesArticulos table, returns all rows of Articulos, 
		else returns only the rows that appears into LocalidadesArticulos.
**/

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_ARTICULOS_EXISTENCIAS]
AS
	SELECT 
		ART_ArticuloId articuloId,
		LOC_LocalidadId localidadId,
		COALESCE(LOCAA_Cantidad,0) existencia
	FROM 
		Articulos 
		INNER JOIN Localidades ON 1 = 1
		LEFT JOIN LocalidadesArticulos ON ART_ArticuloId = LOCA_ART_ArticuloId AND LOC_LocalidadId = LOCA_LOC_LocalidadId
		LEFT JOIN LocalidadesArticulosAcumulados ON ART_ArticuloId = LOCAA_ART_ArticuloId AND LOC_LocalidadId = LOCAA_LOC_LocalidadId
	WHERE
		CASE WHEN (SELECT COUNT(*) FROM LocalidadesArticulos) > 0 THEN LOCA_ART_ArticuloId ELSE 1 END IS NOT NULL;
GO