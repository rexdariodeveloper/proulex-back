DELETE FROM [dbo].[RequisicionesPartidas]
GO

DELETE FROM [dbo].[Requisiciones]
GO

ALTER TABLE [dbo].[Requisiciones] DROP CONSTRAINT [FK_REQ_SUC_SucursalId]
GO

ALTER TABLE [dbo].[Requisiciones] DROP COLUMN [REQ_SUC_SucursalId]
GO

ALTER TABLE [dbo].[Requisiciones] ADD [REQ_ALM_AlmacenId] [int] NOT NULL
GO

ALTER TABLE [dbo].[Requisiciones]  WITH CHECK ADD  CONSTRAINT [FK_REQ_ALM_AlmacenId] FOREIGN KEY([REQ_ALM_AlmacenId])
REFERENCES [dbo].[Almacenes] ([ALM_AlmacenId])
GO

ALTER TABLE [dbo].[Requisiciones] CHECK CONSTRAINT [FK_REQ_ALM_AlmacenId]
GO