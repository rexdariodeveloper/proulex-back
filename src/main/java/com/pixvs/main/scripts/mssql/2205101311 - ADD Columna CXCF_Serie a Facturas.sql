ALTER TABLE CXCFacturas ADD CXCF_Serie VARCHAR(10) NULL
GO

UPDATE factura
  SET
      CXCF_Serie = sucursal.SUC_Serie
FROM CXCFacturas AS factura
     INNER JOIN Sucursales AS sucursal ON factura.CXCF_SUC_SucursalId = sucursal.SUC_SucursalId
GO