/**
 * Created by Angel Daniel Hernández Silva on 08/07/2021.
 * Object:  CREATE FUNCTION [dbo].[getPrecioVentaArticulo]
 */

CREATE OR ALTER FUNCTION [dbo].[getPrecioVentaArticulo] ( @listaPreciosId int, @articuloId int )
RETURNS decimal(28,6)
AS
BEGIN

	DECLARE @precioVenta decimal(28,6);
	
	SELECT
        @precioVenta = CASE WHEN CMA_Valor = '0' THEN LIPRED_Precio ELSE (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](1,LIPRED_Precio,0,CASE WHEN ART_IVAExento = 1 THEN 0 ELSE ART_IVA END,ART_IEPS,ART_IEPSCuotaFija)) END
    FROM ListadosPrecios
    INNER JOIN ListadosPreciosDetalles ON LIPRED_LIPRE_ListadoPrecioId = LIPRE_ListadoPrecioId
    INNER JOIN Articulos ON ART_ArticuloId = LIPRED_ART_ArticuloId
    INNER JOIN ControlesMaestros ON CMA_Nombre = 'CM_CALCULO_PRECIO_UNITARIO'
    WHERE LIPRE_ListadoPrecioId = @listaPreciosId AND ART_ArticuloId = @articuloId
    
	return @precioVenta
END
GO