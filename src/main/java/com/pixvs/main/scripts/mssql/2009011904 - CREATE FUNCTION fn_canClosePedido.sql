SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/** 
	Name: 
		fn_canClosePedido
**/
 
CREATE OR ALTER FUNCTION fn_canClosePedido(@codigoPedido varchar(150))
RETURNS BIT
AS
BEGIN
	DECLARE @pendiente AS decimal(28,6);
	SET @pendiente = (select SUM(IM_Cantidad) pendiente from InventariosMovimientos where IM_LOC_LocalidadId = 0 and IM_Referencia = @codigoPedido);

	IF(@pendiente > 0)
		RETURN 0;
	RETURN 1;
END