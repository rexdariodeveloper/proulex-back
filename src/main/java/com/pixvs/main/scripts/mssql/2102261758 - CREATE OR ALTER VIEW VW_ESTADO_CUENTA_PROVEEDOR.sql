SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_ESTADO_CUENTA_PROVEEDOR] AS
	SELECT
		id facturaId, 
		folio factura, 
		fechaFactura fecha, 
		diasCredito termino, 
		fechaVencimiento vencimiento,
		monto,
		montoPagado pagado,
		(monto - montoPagado) restante,
		idProveedor proveedorId,
		nombreProveedor proveedorNombre,
		DATEDIFF(DAY,fechaVencimiento, GETDATE()) dias
	FROM
		VW_CXPFacturas
	WHERE
		idEstatus NOT IN(2000111, 2000114)
GO