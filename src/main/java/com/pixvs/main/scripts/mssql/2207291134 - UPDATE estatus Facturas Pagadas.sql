UPDATE CXCFacturas
  SET
      CXCF_CMM_EstatusId = 2000495 -- Pagada
FROM CXCFacturas
WHERE CXCF_CMM_TipoRegistroId IN(2000473, 2000474) -- Facturas Nota de Venta y Global
      AND CXCF_CMM_EstatusId = 2000491 -- Abierta
GO