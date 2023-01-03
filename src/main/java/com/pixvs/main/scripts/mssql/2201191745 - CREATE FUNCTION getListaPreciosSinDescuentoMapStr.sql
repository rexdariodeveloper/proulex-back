/**
 * Created by Angel Daniel Hernández Silva on 14/01/2022.
 * Object:  CREATE FUNCTION [dbo].[getListaPreciosSinDescuentoMapStr]
 */

CREATE OR ALTER FUNCTION [dbo].[getListaPreciosSinDescuentoMapStr] ( @listaPreciosId int )
RETURNS varchar(max)
AS
BEGIN

	DECLARE @listaPreciosMapStr varchar(max) = '{}';
	
	SELECT
		@listaPreciosMapStr = CONCAT('{',STRING_AGG(CONCAT('"',articuloId,'": ',precioVenta),','),'}')
	FROM (
		SELECT
			LIPRED_ListadoPrecioDetalleId AS detalleId,
			LIPRED_ART_ArticuloId AS articuloId,
			CASE WHEN CMA_Valor = '0' THEN LIPRED_Precio ELSE (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](1,LIPRED_Precio,0,CASE WHEN ART_IVAExento = 1 THEN 0 ELSE ART_IVA END,ART_IEPS,ART_IEPSCuotaFija)) END AS precioVenta
		FROM ListadosPrecios
		INNER JOIN ListadosPreciosDetalles ON LIPRED_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
		INNER JOIN Articulos ON ART_ArticuloId = LIPRED_ART_ArticuloId
		INNER JOIN ControlesMaestros ON CMA_Nombre = 'CM_CALCULO_PRECIO_UNITARIO'
		WHERE LIPRE_ListadoPrecioId = @listaPreciosId
	)Precios
    
	return @listaPreciosMapStr
END
GO