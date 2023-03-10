
CREATE FUNCTION [dbo].[fn_getSubtotal](
	@cantidad DECIMAL(28,6),
	@precioUnitario DECIMAL(28, 6)
)
RETURNS DECIMAL(28,6) WITH SCHEMABINDING 
AS
BEGIN
	return ROUND(ISNULL(@cantidad, 0) * ISNULL(@precioUnitario, 0), 6)
END
GO


CREATE FUNCTION [dbo].[fn_getBase](
	@cantidad DECIMAL(28,6),
	@precioUnitario DECIMAL(28, 6),
	@descuento DECIMAL(28, 6)
)
RETURNS DECIMAL(28,6) WITH SCHEMABINDING 
AS
BEGIN
	return ROUND([dbo].[fn_getSubtotal](@cantidad, @precioUnitario) - ISNULL(@descuento, 0), 6)
END
GO

CREATE FUNCTION [dbo].[fn_getImpuestoIEPS](
	@cantidad DECIMAL(28,6),
	@precioUnitario DECIMAL(28, 6),
	@descuento DECIMAL(28, 6),
	@cuotaFija DECIMAL(28, 6),
	@ieps DECIMAL(28, 6)
)
RETURNS DECIMAL(28,6) WITH SCHEMABINDING 
AS
BEGIN	
	DECLARE @impuestoIEPS DECIMAL(28,6)
	IF @cuotaFija IS NULL -- Calculamos el IEPS
		SET @impuestoIEPS = ROUND([dbo].[fn_getBase](@cantidad, @precioUnitario, @descuento) * ISNULL(@ieps, 0) , 6)
	ELSE
		SET @impuestoIEPS = ROUND((ISNULL(@cantidad, 0) * @cuotaFija), 6);

	return @impuestoIEPS
END
GO


CREATE FUNCTION [dbo].[fn_getImpuestoIVA](
	@cantidad DECIMAL(28,6),
	@precioUnitario DECIMAL(28, 6),
	@descuento DECIMAL(28, 6),
	@cuotaFija DECIMAL(28, 6),
	@ieps DECIMAL(28, 6),
	@iva DECIMAL(28, 6)
)
RETURNS DECIMAL(28,6) WITH SCHEMABINDING 
AS
BEGIN
	DECLARE @baseImpuestoIVA DECIMAL(28,6) = 
		ROUND( [dbo].[fn_getBase](@cantidad, @precioUnitario, @descuento) + [dbo].[fn_getImpuestoIEPS](@cantidad, @precioUnitario, @descuento, @cuotaFija, @ieps), 6) 

	return ROUND(@baseImpuestoIVA * ISNULL(@iva, 0), 6)
END
GO


-- =============================================
-- Author:		David Arroyo
-- Create date: 05/02/2021
-- Description:	Totales - fn_getTotales
-- =============================================
CREATE FUNCTION [dbo].[fn_getTotal](
	@cantidad DECIMAL(28,6),
	@precioUnitario DECIMAL(28, 6),
	@descuento DECIMAL(28, 6),
	@iva DECIMAL(28, 6),
	@ieps DECIMAL(28,6),
	@cuotaFija DECIMAL(28,6),
	@tipoCambio DECIMAL(28,6)
)
RETURNS TABLE WITH SCHEMABINDING 
AS

	/** Proceso para obtener los datos
	*	DATOS REQUERIDOS
	*		1.- Cantidad
	*		2.- PrecioUnitario
	*		3.- Descuento
	*		4.- IVA
	*		5.- IEPS
	*		6.- Impuesto por cuota Fija, este tipo de impuesto es independiente de la UM, pero es por unidad de articulo, es decir, la cuota es la multiplicacion de la cuota fija por la cantidad
	*	---------------------------------------------------------------------------------------------------------------
	* PASOS PARA CALCULOS
	*	1.- Precio unitario por cantidad para obtener el subtotal
	*	2.- Base de calculo de impuestos es: subtotal - descuento
	*	3.- IEPS(Se suma a la base calculada anteriormente para formar la base del IVA)
	*		* Si se tiene CuotaFija, este será el impuesto
	*		* Si no existe una cuota fija, se calculara apartir del % del parametro obtenido
	*	4.- IVA
	*		Si es null o el articulo es excento, se calculara como IVA 0
	*/



	return SELECT [dbo].[fn_getSubtotal](@cantidad, @precioUnitario) * ISNULL(@tipoCambio, 1) as Subtotal, 
					[dbo].[fn_getImpuestoIVA](@cantidad, @precioUnitario, @descuento, @cuotaFija, @ieps, @iva) * ISNULL(@tipoCambio, 1) as IVA, 
					[dbo].[fn_getImpuestoIEPS](@cantidad, @precioUnitario, @descuento, @cuotaFija, @ieps) * ISNULL(@tipoCambio, 1) as IEPS, 
					ISNULL(@descuento, 0) * ISNULL(@tipoCambio, 1) as Descuento, 
					ROUND(([dbo].[fn_getBase](@cantidad, @precioUnitario, @descuento) + [dbo].[fn_getImpuestoIVA](@cantidad, @precioUnitario, @descuento, @cuotaFija, @ieps, @iva) + [dbo].[fn_getImpuestoIEPS](@cantidad, @precioUnitario, @descuento, @cuotaFija, @ieps)) * ISNULL(@tipoCambio, 1), 6) as Total;


GO