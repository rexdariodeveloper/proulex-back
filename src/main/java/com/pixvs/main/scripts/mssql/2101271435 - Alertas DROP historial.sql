ALTER TABLE [dbo].[AlertasDetalles] SET ( SYSTEM_VERSIONING = OFF)
DROP TABLE [dbo].[AlertasDetalles_History]
GO
ALTER TABLE [dbo].[Alertas] SET ( SYSTEM_VERSIONING = OFF)
DROP TABLE [dbo].[Alertas_History]
GO
