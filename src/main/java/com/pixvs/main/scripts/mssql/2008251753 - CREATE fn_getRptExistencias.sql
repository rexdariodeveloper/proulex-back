SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

DROP FUNCTION IF EXISTS fn_getRptExistencias
GO

CREATE FUNCTION [dbo].[fn_getRptExistencias] ( @fechaFin DATETIME, @articulos NVARCHAR(MAX), @almacenId INT, @localidades NVARCHAR(MAX) )
RETURNS TABLE 
AS
RETURN 
(
	SELECT ART_CodigoArticulo + ' - ' + ART_NombreArticulo AS Articulo,
		UM_Nombre AS UM,
		ALM_CodigoAlmacen+' - '+ALM_Nombre AS Almacen,
		CASE WHEN LOC_LocalidadGeneral = 0 THEN LOC_CodigoLocalidad+' - '+LOC_Nombre ELSE '' END AS Localidad,
		SUM(IM_Cantidad) AS Cantidad,
		ROW_NUMBER() OVER(ORDER BY ART_CodigoArticulo, ALM_CodigoAlmacen, LOC_CodigoLocalidad) AS Orden
	FROM Articulos
		 INNER JOIN UnidadesMedidas ON ART_UM_UnidadMedidaInventarioId = UM_UnidadMedidaId
		 INNER JOIN InventariosMovimientos ON ART_ArticuloId = IM_ART_ArticuloId
		 INNER JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
		 INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
	WHERE IM_FechaCreacion < ISNULL(@fechaFin, GETDATE())
		  AND LOC_ALM_AlmacenId = ISNULL(@almacenId, LOC_ALM_AlmacenId)
		  AND ISNULL(@localidades, '|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|') LIKE '|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|'
		  AND ISNULL(@articulos, '|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|') LIKE '|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|'
	GROUP BY ALM_CodigoAlmacen,
			 ALM_Nombre,
			 LOC_LocalidadGeneral,
			 LOC_CodigoLocalidad,
			 LOC_Nombre,
			 ART_CodigoArticulo,
			 ART_NombreArticulo,
			 UM_Nombre
)