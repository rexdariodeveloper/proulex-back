ALTER TABLE [dbo].[Localidades] DROP CONSTRAINT [DF_LOC_Activo]
GO

ALTER TABLE [dbo].[Localidades] ALTER COLUMN [LOC_Activo] bit NOT NULL
GO

ALTER TABLE [dbo].[Localidades] ADD  CONSTRAINT [DF_LOC_Activo]  DEFAULT ((1)) FOR [LOC_Activo]
GO