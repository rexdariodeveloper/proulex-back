CREATE OR ALTER FUNCTION [dbo].[getListadoPrecioDetalleHijosTotal] ( @detalleId int )
RETURNS decimal(28,6)
AS
BEGIN

	DECLARE @total decimal(28,6);
	
	SELECT
		@total = SUM(CRD.cantidad)
	FROM(
		SELECT LIPRED_Precio as cantidad
		FROM ListadosPreciosDetalles
		WHERE LIPRED_ListadoPrecioPadreId=@detalleId
	) CRD
    
	return @total
END
GO