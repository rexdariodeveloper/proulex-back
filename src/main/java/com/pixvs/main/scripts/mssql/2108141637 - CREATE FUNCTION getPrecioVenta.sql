/**
 * Created by Angel Daniel Hernández Silva on 08/07/2021.
 * Object:  CREATE FUNCTION [dbo].[getPrecioVenta]
 */

CREATE OR ALTER FUNCTION [dbo].[getPrecioVenta] ( @articuloId int, @articuloRaizId int, @listaPreciosId int )
RETURNS decimal(28,6)
AS
BEGIN

	DECLARE @precioVenta decimal(28,6);
	
	WITH PreciosLigados AS (
		SELECT
			LIPRED_ART_ArticuloId AS articuloRaizId,
			LIPRED_ListadoPrecioDetalleId AS detalleRaizId,
			LIPRED_ListadoPrecioDetalleId AS detalleId,
			LIPRED_ART_ArticuloId AS articuloId,
			CASE WHEN CMA_Valor = '0' THEN LIPRED_Precio ELSE (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](1,LIPRED_Precio,0,CASE WHEN ART_IVAExento = 1 THEN 0 ELSE ART_IVA END,ART_IEPS,ART_IEPSCuotaFija)) END AS precioVenta
		FROM ListadosPrecios
		INNER JOIN ListadosPreciosDetalles ON LIPRED_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
		INNER JOIN Articulos ON ART_ArticuloId = LIPRED_ART_ArticuloId
		INNER JOIN ControlesMaestros ON CMA_Nombre = 'CM_CALCULO_PRECIO_UNITARIO'
		WHERE LIPRE_ListadoPrecioId = @listaPreciosId AND LIPRED_ART_ArticuloId = @articuloRaizId

		UNION ALL

		SELECT
			articuloRaizId,
			detalleRaizId,
			LIPRED_ListadoPrecioDetalleId AS detalleId,
			LIPRED_ART_ArticuloId AS articuloId,
			CASE WHEN CMA_Valor = '0' THEN LIPRED_Precio ELSE (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](1,LIPRED_Precio,0,CASE WHEN ART_IVAExento = 1 THEN 0 ELSE ART_IVA END,ART_IEPS,ART_IEPSCuotaFija)) END AS precioVenta
		FROM ListadosPreciosDetalles
		INNER JOIN PreciosLigados ON LIPRED_ListadoPrecioPadreId = detalleId
		INNER JOIN Articulos ON ART_ArticuloId = LIPRED_ART_ArticuloId
		INNER JOIN ControlesMaestros ON CMA_Nombre = 'CM_CALCULO_PRECIO_UNITARIO'
	)

	SELECT @precioVenta = SUM(precioVenta)
	FROM PreciosLigados
	WHERE CASE WHEN @articuloRaizId = @articuloId THEN articuloRaizId ELSE articuloId END = @articuloId
	GROUP BY CASE WHEN @articuloRaizId = @articuloId THEN articuloRaizId ELSE articuloId END
    
	return @precioVenta
END
GO