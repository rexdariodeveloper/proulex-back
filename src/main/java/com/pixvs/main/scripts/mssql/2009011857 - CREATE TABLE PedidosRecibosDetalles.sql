SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [PedidosRecibosDetalles]
GO

CREATE TABLE [dbo].[PedidosRecibosDetalles](
	[PRD_PedidoReciboDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[PRD_PR_PedidoReciboId] 	[int] NOT NULL,
	[PRD_ART_ArticuloId] 		[int] NOT NULL,
	[PRD_UM_UnidadMedidaId] 	[smallint] NOT NULL,
	[PRD_Cantidad] 				[decimal](28, 6) NOT NULL,
	[PRD_CantidadPedida] 		[decimal](28, 6) NOT NULL,
	[PRD_CantidadDevuelta] 		[decimal](28, 6) NOT NULL,
	[PRD_CantidadSpill] 		[decimal](28, 6) NOT NULL,
	[PRD_Comentario]			[varchar](150) NULL,
	[PRD_CMM_EstatusId] 		[int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[PRD_PedidoReciboDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PRD_PR_PedidoReciboId] FOREIGN KEY([PRD_PR_PedidoReciboId])
REFERENCES [dbo].[PedidosRecibos] ([PR_PedidoReciboId])
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] CHECK CONSTRAINT [FK_PRD_PR_PedidoReciboId]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PRD_ART_ArticuloId] FOREIGN KEY([PRD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] CHECK CONSTRAINT [FK_PRD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PRD_UM_UnidadMedidaId] FOREIGN KEY([PRD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] CHECK CONSTRAINT [FK_PRD_UM_UnidadMedidaId]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PRD_CMM_EstatusId] FOREIGN KEY([PRD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] CHECK CONSTRAINT [FK_PRD_CMM_EstatusId]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] ADD  CONSTRAINT [DF_PRD_Cantidad]  DEFAULT (0) FOR [PRD_Cantidad]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] ADD  CONSTRAINT [DF_PRD_CantidadPedida]  DEFAULT (0) FOR [PRD_CantidadPedida]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] ADD  CONSTRAINT [DF_PRD_CantidadDevuelta]  DEFAULT (0) FOR [PRD_CantidadDevuelta]
GO

ALTER TABLE [dbo].[PedidosRecibosDetalles] ADD  CONSTRAINT [DF_PRD_CantidadSpill]  DEFAULT (0) FOR [PRD_CantidadSpill]
GO