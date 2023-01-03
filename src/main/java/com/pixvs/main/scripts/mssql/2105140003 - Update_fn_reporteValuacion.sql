SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


ALTER   FUNCTION [dbo].[fn_reporteValuacion] (@fechaInicio DATETIME, @fechaFin DATETIME, @articulos NVARCHAR(MAX), @localidades NVARCHAR(MAX), @idUsuario INT)
RETURNS TABLE
AS
RETURN
(
	SELECT
		ART_CodigoArticulo codigo,
		ART_NombreArticulo nombre,
		CMM_Valor serie,
		ART_CostoUltimo costo,
		UM_Nombre unidad,
		COALESCE(inicial,0) inicial,
		COALESCE(entradas,0) entradas,
		COALESCE(salidas,0) salidas,
		COALESCE(total,0) existencia,
		COALESCE(inicial,0) * ART_CostoUltimo cInicial,
		COALESCE(entradas,0) * ART_CostoUltimo cEntradas,
		COALESCE(salidas,0) * ART_CostoUltimo cSalidas,
		COALESCE(total,0) * ART_CostoUltimo cTotal,
		CONCAT(ALM_Nombre,' / ',LOC_Nombre) almacenLocalidad
	FROM
		Almacenes
		/* Filter by user-warehouse assignment */
		INNER JOIN UsuariosAlmacenes ON ALM_AlmacenId = USUA_ALM_AlmacenId AND USUA_USU_UsuarioId = @idUsuario
		INNER JOIN Localidades ON ALM_AlmacenId = LOC_ALM_AlmacenId
		INNER JOIN Articulos ON ART_Inventariable = 1
		LEFT JOIN ControlesMaestrosMultiples ON ART_CMM_Clasificacion1Id = CMM_ControlId
		INNER JOIN UnidadesMedidas ON ART_UM_UnidadMedidaInventarioId = UM_UnidadMedidaId
		LEFT JOIN
		(
			SELECT
				IM_ART_ArticuloId,
				IM_LOC_LocalidadId,
				SUM(CASE WHEN CAST(IM_FechaCreacion AS DATE) <  CAST(@fechaInicio AS DATE) THEN IM_Cantidad ELSE 0 END) inicial,
				SUM(CASE WHEN CAST(IM_FechaCreacion AS DATE) >= CAST(@fechaInicio AS DATE) AND CAST(IM_FechaCreacion AS DATE) <= CAST(@fechaFin AS DATE) AND IM_Cantidad >= 0 THEN IM_Cantidad ELSE 0 END) entradas,
				SUM(CASE WHEN CAST(IM_FechaCreacion AS DATE) >= CAST(@fechaInicio AS DATE) AND CAST(IM_FechaCreacion AS DATE) <= CAST(@fechaFin AS DATE) AND IM_Cantidad <  0 THEN (IM_Cantidad * -1) ELSE 0 END) salidas,
				SUM(CASE WHEN CAST(IM_FechaCreacion AS DATE) <= CAST(@fechaFin AS DATE) THEN IM_Cantidad ELSE 0 END) total
			FROM
				InventariosMovimientos
			WHERE
				CAST(IM_FechaCreacion AS DATE) <= CAST(@fechaFin AS DATE)
			GROUP BY
				IM_ART_ArticuloId, IM_LOC_LocalidadId
		) existencias ON IM_ART_ArticuloId = ART_ArticuloId AND IM_LOC_LocalidadId = LOC_LocalidadId
	WHERE
		ART_Activo = 1
		AND ALM_Activo = 1
		AND LOC_Activo = 1
		AND ISNULL(@localidades, '|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(LOC_LocalidadId AS NVARCHAR(10))+'|%'
		AND ISNULL(@articulos, '|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(ART_ArticuloId AS NVARCHAR(10))+'|%'
)
GO


