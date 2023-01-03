SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getImpuestosArticulo](
-- CREATE OR REPLACE FUNCTION [dbo].[fn_getCostosArticulo](
	@cantidad DECIMAL(28,6),
	@precioUnitario DECIMAL(28, 6),
	@descuento DECIMAL(28, 6),
	@iva DECIMAL(28, 6),
	@ieps DECIMAL(28,6),
	@cuotaFija DECIMAL(28,6)
)RETURNS @tbl TABLE(
	Subtotal DECIMAL(28,6),
	IVA DECIMAL(28,6),
	IEPS DECIMAL(28,6),
	Descuento DECIMAL(28,6),
	Total DECIMAL(28,6)
) AS BEGIN
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

	-- Variables a usar
	DECLARE @subtotal DECIMAL(28, 6)
	DECLARE @base DECIMAL(28,6)
	DECLARE @impuestoIEPS DECIMAL(28,6)
	DECLARE @baseImpuestoIVA DECIMAL(28,6)
	DECLARE @impuestoIVA DECIMAL(28,6)

	-- corregimos valores null
	SET @cantidad = ISNULL(@cantidad, 0)
	SET @precioUnitario = ISNULL(@precioUnitario, 0)
	SET @descuento = ISNULL(@descuento, 0)
	SET @ieps = ISNULL(@ieps, 0)
	SET @iva = ISNULL(@iva, 0)

	-- Obtenemos el subtotal base para los calculos de impuestos
	SET @subtotal = ROUND(@cantidad * @precioUnitario, 6)
	SET @base = ROUND(@subtotal - @descuento, 6)
	-- SET @cuotaFR = ROUND((@cantidad * @cuotaFija), 6)

	IF @cuotaFija IS NULL -- Calculamos el IEPS
		SET @impuestoIEPS = ROUND(@base * @ieps, 6)
	ELSE
		SET @impuestoIEPS = ROUND((@cantidad * @cuotaFija), 6);

	SET @baseImpuestoIVA = ROUND( @base + @impuestoIEPS, 6) 

	SET @impuestoIVA = ROUND(@baseImpuestoIVA * @iva, 6)

	INSERT INTO @tbl
	SELECT @subtotal, @impuestoIVA,@impuestoIEPS,@descuento,ROUND(@base + @impuestoIVA + @impuestoIEPS, 6);
RETURN
END
GO


