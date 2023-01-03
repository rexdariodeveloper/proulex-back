

DELETE FROM [dbo].[Inscripciones]
GO

DELETE FROM [dbo].[InscripcionesSinGrupo]
GO

DELETE FROM [dbo].[OrdenesVentaDetalles]
GO

DELETE FROM [dbo].[OrdenesVenta]
GO

ALTER TABLE [dbo].[Inscripciones] DROP CONSTRAINT [FK_INS_OV_OrdenVentaId]
GO

ALTER TABLE [dbo].[Inscripciones] DROP COLUMN [INS_OV_OrdenVentaId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] DROP CONSTRAINT [FK_INSSG_OV_OrdenVentaId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] DROP COLUMN [INSSG_OV_OrdenVentaId]
GO

ALTER TABLE [dbo].[Inscripciones] ADD [INS_OVD_OrdenVentaId] int NOT NULL
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_OVD_OrdenVentaId] FOREIGN KEY([INS_OVD_OrdenVentaId])
REFERENCES [dbo].[OrdenesVentaDetalles] ([OVD_OrdenVentaDetalleId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_OVD_OrdenVentaId]
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] ADD [INSSG_OVD_OrdenVentaId] int NOT NULL
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo]  WITH CHECK ADD  CONSTRAINT [FK_INSSG_OVD_OrdenVentaId] FOREIGN KEY([INSSG_OVD_OrdenVentaId])
REFERENCES [dbo].[OrdenesVentaDetalles] ([OVD_OrdenVentaDetalleId])
GO

ALTER TABLE [dbo].[InscripcionesSinGrupo] CHECK CONSTRAINT [FK_INSSG_OVD_OrdenVentaId]
GO