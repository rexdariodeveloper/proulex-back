SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [InventariosFisicosDetalles]
GO

CREATE TABLE [dbo].[InventariosFisicosDetalles](
	[IFD_InventarioFisicoDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[IFD_IF_InventarioFisicoId] [int] NOT NULL,
	[IFD_ART_ArticuloId] [int] NOT NULL,
	[IFD_UM_UnidadMedidaId] [smallint] NOT NULL,
	[IFD_Conteo] [decimal](28, 6) NOT NULL,
	[IFD_Existencia] [decimal](28, 6) NOT NULL
PRIMARY KEY CLUSTERED 
(
	[IFD_InventarioFisicoDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[InventariosFisicosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_IFD_IF_InventarioFisicoId] FOREIGN KEY([IFD_IF_InventarioFisicoId])
REFERENCES [dbo].[InventariosFisicos] ([IF_InventarioFisicoId])
GO

ALTER TABLE [dbo].[InventariosFisicosDetalles] CHECK CONSTRAINT [FK_IFD_IF_InventarioFisicoId]
GO

ALTER TABLE [dbo].[InventariosFisicosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_IFD_ART_ArticuloId] FOREIGN KEY([IFD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[InventariosFisicosDetalles] CHECK CONSTRAINT [FK_IFD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[InventariosFisicosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_IFD_UM_UnidadMedidaId] FOREIGN KEY([IFD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[InventariosFisicosDetalles] CHECK CONSTRAINT [FK_IFD_UM_UnidadMedidaId]
GO