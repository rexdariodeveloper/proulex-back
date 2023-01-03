SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER FUNCTION [dbo].[fn_getKardexArticulo] ( @articuloId INT,
											  @fechaCreacionDesde DATETIME, 
											  @fechaCreacionHasta DATETIME, 
											  @almacenId INT, 
											  @localidades NVARCHAR(300), 																				  
											  @referencia NVARCHAR(300), 
											  @tiposMovimiento NVARCHAR(300), 
											  @usuarioId INT )
RETURNS @tbl TABLE
(
	Articulo NVARCHAR(500),
	AlmacenLocalidad NVARCHAR(1000),
	ExistenciaAnterior DECIMAL(28, 6),
	Cantidad DECIMAL(28, 6),
	Entrada DECIMAL(28, 6),
	Salida DECIMAL(28, 6),
	Total DECIMAL(28, 6),
	Fecha DATETIME,
	Referencia NVARCHAR(100),
	TipoMovimiento NVARCHAR(300),
	Razon NVARCHAR(300),
	Usuario NVARCHAR(200),
	Costo DECIMAL(28,6),
	Orden INT
)
AS
BEGIN

	SET @fechaCreacionDesde = ISNULL(@fechaCreacionDesde, '19000101')
	SET @fechaCreacionHasta = ISNULL(@fechaCreacionHasta, GETDATE())
	DECLARE @existencias TABLE (IM_ART_ArticuloId INT, IM_LOC_LocalidadId INT, IM_Cantidad DECIMAL(28, 10), IM_FechaCreacion DATETIME, IM_Referencia NVARCHAR(1), IM_Razon NVARCHAR(1), CMM_Valor NVARCHAR(1), IM_USU_CreadoPorId INT, IM_CostoUnitario DECIMAL(28,6))

	INSERT INTO @existencias
		   SELECT IM_ART_ArticuloId,
				  IM_LOC_LocalidadId,
				  ISNULL(SUM(IM_Cantidad), 0.00),
				  DATEADD(DAY, -1, @fechaCreacionDesde),
				  '',
				  '',
				  '',
				  0,
				  IM_CostoUnitario
		   FROM InventariosMovimientos
				INNER JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
		   WHERE IM_ART_ArticuloId = ISNULL(@articuloId, IM_ART_ArticuloId)
				 AND IM_FechaCreacion < @fechaCreacionDesde
				 AND LOC_ALM_AlmacenId = ISNULL(@almacenId, LOC_ALM_AlmacenId)
				 AND ISNULL(@localidades, '|' + CAST(IM_LOC_LocalidadId AS NVARCHAR(10)) + '|') LIKE '|' + CAST(IM_LOC_LocalidadId AS NVARCHAR(10)) + '|'
		   GROUP BY IM_ART_ArticuloId,
					IM_LOC_LocalidadId,
					IM_CostoUnitario
	
	INSERT INTO @tbl
	SELECT *, ROW_NUMBER() OVER (ORDER BY articulo, almacenLocalidad, fecha)
	FROM
	(
		SELECT ART_CodigoArticulo + ' - ' + ART_NombreArticulo AS articulo,
			   ALM_CodigoAlmacen + ' - ' + ALM_Nombre + CASE WHEN LOC_LocalidadGeneral = 0 THEN ' / ' + LOC_CodigoLocalidad + ' - ' + LOC_Nombre ELSE '' END AS almacenLocalidad,
			   COALESCE(SUM(IM_Cantidad) OVER(PARTITION BY IM_ART_ArticuloId, IM_LOC_LocalidadId ORDER BY IM_FechaCreacion ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING), 0) AS existencia,
			   IM_Cantidad AS cantidad,
			   CASE WHEN IM_Cantidad >= 0 THEN IM_Cantidad ELSE NULL END AS entrada,
			   CASE WHEN IM_Cantidad <  0 THEN (IM_Cantidad * -1) ELSE NULL END AS salida,
			   COALESCE(SUM(IM_Cantidad) OVER(PARTITION BY IM_ART_ArticuloId, IM_LOC_LocalidadId ORDER BY IM_FechaCreacion ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING), 0) + IM_Cantidad AS total,
			   IM_FechaCreacion AS fecha,
			   IM_Referencia,
			   CMM_Valor,
			   IM_Razon,
			   dbo.getNombreCompletoUsuario(IM_USU_CreadoPorId) AS usuario,
			   CASE WHEN IM_Cantidad >= 0 THEN 1 ELSE -1 END * IM_Cantidad * IM_CostoUnitario costo
		FROM
		(
			SELECT * FROM @existencias
		
			UNION ALL
		
			SELECT IM_ART_ArticuloId,
					IM_LOC_LocalidadId,				
					IM_Cantidad,
					IM_FechaCreacion,
					IM_Referencia,
					IM_Razon,
					CMM_Valor,
					IM_USU_CreadoPorId,
					IM_CostoUnitario
			FROM InventariosMovimientos
				INNER JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
				INNER JOIN ControlesMaestrosMultiples ON IM_CMM_TipoMovimientoId = CMM_ControlId
			WHERE IM_ART_ArticuloId = ISNULL(@articuloId, IM_ART_ArticuloId)
					AND CAST(IM_FechaCreacion AS DATE) BETWEEN CAST(@fechaCreacionDesde AS DATE) AND CAST(@fechaCreacionHasta AS DATE)
					AND LOC_ALM_AlmacenId = ISNULL(@almacenId, LOC_ALM_AlmacenId)
					AND ISNULL(@localidades, '|' + CAST(IM_LOC_LocalidadId AS NVARCHAR(10)) + '|') LIKE '%|' + CAST(IM_LOC_LocalidadId AS NVARCHAR(10)) + '|%'
					AND IM_Referencia = ISNULL(@referencia, IM_Referencia)
					AND ISNULL(@tiposMovimiento, '|' + CAST(IM_CMM_TipoMovimientoId AS NVARCHAR(10)) + '|') LIKE '%|' + CAST(IM_CMM_TipoMovimientoId AS NVARCHAR(10)) + '|%'
					AND IM_USU_CreadoPorId = ISNULL(@usuarioId, IM_USU_CreadoPorId)
		) AS todo
		INNER JOIN Articulos ON IM_ART_ArticuloId = ART_ArticuloId
		INNER JOIN Localidades ON IM_LOC_LocalidadId = LOC_LocalidadId
		INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
	) AS consulta
	WHERE fecha >= @fechaCreacionDesde

	RETURN
END
GO