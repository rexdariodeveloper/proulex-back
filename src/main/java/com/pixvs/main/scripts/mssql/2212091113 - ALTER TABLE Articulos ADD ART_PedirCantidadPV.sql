ALTER TABLE [dbo].[Articulos] ADD [ART_PedirCantidadPV] bit NULL
GO

UPDATE [dbo].[Articulos] SET [ART_PedirCantidadPV] = 0
GO

ALTER TABLE [dbo].[Articulos] ALTER COLUMN [ART_PedirCantidadPV] bit NOT NULL
GO

ALTER TABLE [dbo].[Articulos] WITH CHECK ADD CONSTRAINT [DF_Articulos_ART_PedirCantidadPV]  DEFAULT (0) FOR [ART_PedirCantidadPV]
GO