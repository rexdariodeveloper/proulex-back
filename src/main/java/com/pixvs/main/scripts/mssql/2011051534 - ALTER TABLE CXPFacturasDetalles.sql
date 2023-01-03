ALTER TABLE [dbo].[CXPFacturasDetalles] ADD CXPFD_ART_ArticuloId int
GO

ALTER TABLE [dbo].[CXPFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPFD_ART_ArticuloId] FOREIGN KEY([CXPFD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[CXPFacturasDetalles] ADD CXPFD_UM_UnidadMedidaId smallint
GO

ALTER TABLE [dbo].[CXPFacturasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_CXPFD_UM_UnidadMedidaId] FOREIGN KEY([CXPFD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO