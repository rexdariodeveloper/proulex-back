SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER FUNCTION [dbo].[fn_getRptExistencias] ( @fechaFin DATETIME, @articulos NVARCHAR(MAX), @almacenes NVARCHAR(MAX), @localidades NVARCHAR(MAX), @idUsuario int)
RETURNS TABLE 
AS
RETURN 
(
	/* TODO: Si un artículo no tiene existencia, mostrar 0*/
	SELECT 
		CONCAT(ART_CodigoArticulo,' - ',ART_NombreArticulo) AS Articulo,
		UM_Nombre AS UM,
		CONCAT(ALM_CodigoAlmacen,' - ',ALM_Nombre) AS Almacen,
		CONCAT(LOC_CodigoLocalidad,' - ',LOC_Nombre) AS Localidad,
		SUM(IM_Cantidad) AS Cantidad,
		ART_CostoPromedio AS Costo,
		ROW_NUMBER() OVER(ORDER BY ART_CodigoArticulo, ALM_CodigoAlmacen, LOC_CodigoLocalidad) AS Orden
	FROM Articulos
		 INNER JOIN UnidadesMedidas ON ART_UM_UnidadMedidaInventarioId = UM_UnidadMedidaId
		 INNER JOIN InventariosMovimientos ON ART_ArticuloId = IM_ART_ArticuloId
		 INNER JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
		 INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
		 INNER JOIN UsuariosAlmacenes on ALM_AlmacenId = USUA_ALM_AlmacenId AND USUA_USU_UsuarioId = @idUsuario
	WHERE 
		  IM_FechaCreacion < ISNULL(DATEADD(DAY, 1, @fechaFin), GETDATE()) AND
		  ISNULL(@almacenes, '|'+CAST(LOC_ALM_AlmacenId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(LOC_ALM_AlmacenId AS NVARCHAR(10))+'|%' AND
		  ISNULL(@localidades, '|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|%' AND
		  ISNULL(@articulos, '|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|%' AND
		  1 = 1
	GROUP BY 
		ALM_CodigoAlmacen, ALM_Nombre,LOC_LocalidadGeneral, LOC_CodigoLocalidad, LOC_Nombre, ART_CodigoArticulo, ART_NombreArticulo, 
		ART_CostoPromedio, UM_Nombre
)
GO