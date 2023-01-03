
ALTER TABLE [dbo].[RequisicionesPartidas] ADD [REQP_ARC_ImagenArticuloId] int NULL
GO

ALTER TABLE [dbo].[RequisicionesPartidas]  WITH CHECK ADD  CONSTRAINT [FK_REQP_ARC_ImagenArticuloId] FOREIGN KEY([REQP_ARC_ImagenArticuloId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[RequisicionesPartidas] CHECK CONSTRAINT [FK_REQP_ARC_ImagenArticuloId]
GO