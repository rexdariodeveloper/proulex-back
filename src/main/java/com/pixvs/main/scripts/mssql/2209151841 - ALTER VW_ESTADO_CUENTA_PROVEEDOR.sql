SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_ESTADO_CUENTA_PROVEEDOR]
AS
SELECT id AS facturaId,
       folio AS factura,
       fechaFactura AS fecha,
       diasCredito AS termino,
       fechaVencimiento AS vencimiento,
       monto,
       montoPagado AS pagado,
       (monto - montoPagado) AS restante,
       idProveedor AS proveedorId,
       nombreProveedor AS proveedorNombre,
       DATEDIFF(DAY, fechaVencimiento, GETDATE()) AS dias,
	   ISNULL(PRO_DiasPlazoCredito, 0) AS diasCredito
FROM VW_CXPFacturas
     INNER JOIN Proveedores ON idProveedor = PRO_ProveedorId
WHERE idEstatus NOT IN(2000111, 2000114)
GO