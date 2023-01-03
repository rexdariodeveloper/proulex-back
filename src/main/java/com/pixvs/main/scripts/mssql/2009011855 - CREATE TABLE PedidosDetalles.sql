SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [PedidosDetalles]
GO

CREATE TABLE [dbo].[PedidosDetalles](
	[PEDD_PedidoDetalleId] 		[int] IDENTITY(1,1) NOT NULL,
	[PEDD_PED_PedidoId] 		[int] NOT NULL,
	[PEDD_ART_ArticuloId] 		[int] NOT NULL,
	[PEDD_UM_UnidadMedidaId] 	[smallint] NOT NULL,
	[PEDD_CantidadPedida] 		[decimal](28, 6) NOT NULL,
	[PEDD_CantidadSurtida] 		[decimal](28, 6) NOT NULL,
	[PEDD_Existencia]			[decimal](28, 6) NOT NULL,
	[PEDD_CMM_EstatusId] 		[int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[PEDD_PedidoDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PedidosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PEDD_PED_PedidoId] FOREIGN KEY([PEDD_PED_PedidoId])
REFERENCES [dbo].[Pedidos] ([PED_PedidoId])
GO

ALTER TABLE [dbo].[PedidosDetalles] CHECK CONSTRAINT [FK_PEDD_PED_PedidoId]
GO

ALTER TABLE [dbo].[PedidosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PEDD_ART_ArticuloId] FOREIGN KEY([PEDD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[PedidosDetalles] CHECK CONSTRAINT [FK_PEDD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[PedidosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PEDD_UM_UnidadMedidaId] FOREIGN KEY([PEDD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[PedidosDetalles] CHECK CONSTRAINT [FK_PEDD_UM_UnidadMedidaId]
GO

ALTER TABLE [dbo].[PedidosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PEDD_CMM_EstatusId] FOREIGN KEY([PEDD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[PedidosDetalles] CHECK CONSTRAINT [FK_PEDD_CMM_EstatusId]
GO