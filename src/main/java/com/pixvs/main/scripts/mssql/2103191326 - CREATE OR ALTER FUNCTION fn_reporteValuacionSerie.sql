SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_reporteValuacionSerie] (@fechaInicio DATETIME, @fechaFin DATETIME, @series NVARCHAR(MAX), @localidades NVARCHAR(MAX))
RETURNS TABLE 
AS
RETURN
(
	SELECT
		serie,
		almacenLocalidad,
		COALESCE(movimientos.costoUnitario, historico.costoUnitario, todos.costoUnitario, 0) costo,
		COALESCE(movimientos.UM, historico.UM, todos.UM) unidad,
		COALESCE(historico.cantidadInicial, todos.cantidadInicial) cantidadInicial,
		COALESCE(movimientos.cantidadEntrada, todos.cantidadEntrada) cantidadEntrada,
		COALESCE(movimientos.cantidadSalida, todos.cantidadSalida) cantidadSalida,
		COALESCE(historico.cantidadInicial, todos.cantidadInicial) + COALESCE(movimientos.cantidadTotal, todos.cantidadTotal) cantidadFinal,
		COALESCE(historico.costoInicial, todos.costoInicial) costoInicial,
		COALESCE(movimientos.costoEntrada, todos.costoEntrada) costoEntrada,
		COALESCE(movimientos.costoSalida, todos.costoSalida) costoSalida,
		COALESCE(historico.costoInicial, todos.costoInicial) + COALESCE(movimientos.costoTotal, todos.costoTotal) costoFinal,

		todos.serieId,
		todos.localidadId
	FROM
	(
		SELECT 
			CMM_Valor serie,
			CONCAT(ALM_Nombre,' / ',LOC_Nombre) almacenLocalidad,
			NULL costoUnitario,
			NULL UM,
			0 cantidadInicial,
			0 cantidadEntrada,
			0 cantidadSalida,
			0 cantidadTotal,
			0 costoInicial,
			0 costoEntrada,
			0 costoSalida,
			0 costoTotal,

			CMM_ControlId serieId,
			LOC_LocalidadId localidadId
		FROM 
			ControlesMaestrosMultiples 
			JOIN Localidades ON LOC_Activo = 1 AND CMM_Control = 'CMM_ART_Clasificacion1'
			INNER JOIN Almacenes ON ALM_AlmacenId = LOC_ALM_AlmacenId
	) todos
	LEFT JOIN
	(
		SELECT 
			ART_CMM_Clasificacion1Id serieId, 
			idLocalidad localidadId,
			CASE WHEN MIN(costoUnitario) = MAX(costoUnitario) THEN MIN(costoUnitario) ELSE NULL END costoUnitario,
			CASE WHEN MIN(UM) = MAX(UM) THEN MIN(UM) ELSE 'Varios' END UM,
			SUM(CASE WHEN cantidad >= 0 then ABS(cantidad) else 0 end) cantidadEntrada,
			SUM(CASE WHEN cantidad <  0 then ABS(cantidad) else 0 end) cantidadSalida,
			SUM(cantidad) cantidadTotal,
			SUM(CASE WHEN cantidad >= 0 then ABS(costoMovimiento) else 0 end) costoEntrada,
			SUM(CASE WHEN cantidad <  0 then ABS(costoMovimiento) else 0 end) costoSalida,
			SUM(costoMovimiento) costoTotal
		FROM 
			[dbo].[VW_KardexAgrupadoPorLocalidad]
			INNER JOIN Articulos ON ART_ArticuloId = idArticulo
		WHERE
			ART_CMM_Clasificacion1Id IS NOT NULL
			AND CAST(fechaMovimiento AS DATE) BETWEEN CAST(@fechaInicio AS DATE) AND CAST(@fechaFin AS DATE)
		GROUP BY
			ART_CMM_Clasificacion1Id, idLocalidad
	) movimientos ON movimientos.serieId = todos.serieId AND movimientos.localidadId = todos.localidadId
	LEFT JOIN
	(
		SELECT 
			ART_CMM_Clasificacion1Id serieId,
			IM_LOC_LocalidadId localidadId,
			CASE WHEN MIN(IM_CostoUnitario) = MAX(IM_CostoUnitario) THEN MIN(IM_CostoUnitario) ELSE NULL END costoUnitario,
			CASE WHEN MIN(UM_Nombre) = MAX(UM_Nombre) THEN MIN(UM_Nombre) ELSE 'Varios' END UM,
			SUM(IM_Cantidad) cantidadInicial,
			SUM(IM_Cantidad * IM_CostoUnitario) costoInicial
		FROM 
			InventariosMovimientos 
			INNER JOIN Articulos ON IM_ART_ArticuloId = ART_ArticuloId
			INNER JOIN UnidadesMedidas ON IM_UM_UnidadMedidaId = UM_UnidadMedidaId
		WHERE
			ART_CMM_Clasificacion1Id IS NOT NULL
			AND CAST(IM_FechaCreacion AS DATE) < CAST(@fechaInicio AS DATE)
		GROUP BY
			ART_CMM_Clasificacion1Id, IM_LOC_LocalidadId
	) historico ON todos.serieId = historico.serieId AND todos.localidadId = historico.localidadId
	WHERE
		ISNULL(@localidades, '|'+CAST(todos.localidadId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(todos.localidadId AS NVARCHAR(10))+'|%'
		AND ISNULL(@series, '|'+CAST(todos.serieId AS NVARCHAR(10))+'|') LIKE '%|'+CAST(todos.serieId AS NVARCHAR(10))+'|%'
)
GO