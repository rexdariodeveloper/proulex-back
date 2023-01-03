ALTER TABLE [dbo].[OrdenesVenta] ADD [OV_LigaCentroPagos] [varchar](500) NULL
GO

ALTER TABLE [dbo].[OrdenesVenta] ALTER COLUMN [OV_ReferenciaPago] [varchar](50) NULL
GO