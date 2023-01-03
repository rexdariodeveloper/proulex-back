SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==============================================
-- Author:		Javier Elías
-- Create date: 27/01/2022
-- Modified date: 26/07/2022
-- Description:	Función para obtener los datos de 
--						las OV para la Factura Global
-- ==============================================
CREATE OR ALTER FUNCTION [dbo].[fn_getDatosFacturacionGlobalOV](@sucursalId INT, @fechaInicio DATETIME, @fechaFin DATETIME)
RETURNS
@tbl TABLE 
(	
	Id INT,
	Cantidad DECIMAL(28, 6),
	Unidad VARCHAR(4000),
	ClaveProdServ VARCHAR(4000),
	NoIdentificacion VARCHAR(4000),
	Descripcion VARCHAR(4000),
	ValorUnitario DECIMAL(28, 6),
	Subtotal DECIMAL(28, 6),
	Descuento DECIMAL(28, 6),
	Impuestos DECIMAL(28, 6),
	Total DECIMAL(28, 6),
	SucursalId INT,
	EstatusId INT,
	FacturaId INT
)
AS
BEGIN

	DECLARE @fechaInicioP DATETIME = ISNULL(@fechaInicio, '19000101')
	DECLARE @fechaFinP DATETIME = DATEADD(MINUTE, 1439, ISNULL(@fechaFin, '21000101'))

	INSERT INTO @tbl
	SELECT Id,
		   1 AS Cantidad,
		   'ACT' AS Unidad,
		   '01010101' AS ClaveProdServ,
		   Codigo AS NoIdentificacion,
		   'Venta' AS Descripcion,
		   Subtotal AS ValorUnitario,
		   Subtotal,
		   Descuento,
		   Impuestos,
		   Total,
		   SucursalId,
		   EstatusId,
		   FacturaId
	FROM OrdenesVenta AS ov
		 CROSS APPLY fn_getDatosFacturacionOV(ov.OV_SUC_SucursalId, ov.OV_Codigo) AS datosOV
	WHERE OV_FechaCreacion BETWEEN @fechaInicioP AND @fechaFinP
		  AND OV_SUC_SucursalId = ISNULL(@sucursalId, OV_SUC_SucursalId)
		  AND Total > 0

	RETURN
END