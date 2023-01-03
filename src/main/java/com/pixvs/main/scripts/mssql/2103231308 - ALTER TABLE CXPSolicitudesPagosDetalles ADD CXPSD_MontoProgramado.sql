ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles] ADD [CXPSD_MontoProgramado] decimal(28,2) NULL
GO

UPDATE CXPSolicitudesPagosDetalles SET CXPSD_MontoProgramado = CXPF_MontoRegistro
FROM CXPSolicitudesPagosDetalles
INNER JOIN CXPFacturas ON CXPF_CXPFacturaId = CXPSD_CXPF_CXPFacturaId
GO

ALTER TABLE [dbo].[CXPSolicitudesPagosDetalles] ALTER COLUMN [CXPSD_MontoProgramado] decimal(28,2) NOT NULL
GO