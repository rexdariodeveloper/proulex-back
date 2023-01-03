

DELETE FROM [dbo].[MonedasParidad]
GO

ALTER TABLE [dbo].[MonedasParidad] DROP COLUMN [MONP_FechaInicio]
GO
ALTER TABLE [dbo].[MonedasParidad] DROP COLUMN [MONP_FechaFin]
GO

ALTER TABLE [dbo].[MonedasParidad] ADD [MONP_Fecha] datetime NOT NULL
GO