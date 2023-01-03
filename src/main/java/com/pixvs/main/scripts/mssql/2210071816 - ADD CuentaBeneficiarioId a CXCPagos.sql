ALTER TABLE CXCPagos ADD CXCP_BAC_CuentaBeneficiarioId INT NULL
GO

ALTER TABLE CXCPagos  WITH CHECK ADD  CONSTRAINT FK_CXCPagos_CuentaBeneficiarioId FOREIGN KEY(CXCP_BAC_CuentaBeneficiarioId)
REFERENCES BancosCuentas (BAC_CuentaId)
GO
ALTER TABLE CXCPagos CHECK CONSTRAINT FK_CXCPagos_CuentaBeneficiarioId
GO

UPDATE CXCPagos
  SET
      CXCP_BAC_CuentaBeneficiarioId = BAC_CuentaId
FROM CXCPagos
     INNER JOIN BancosCuentas ON CXCP_CuentaBeneficiario = BAC_CLABE
GO