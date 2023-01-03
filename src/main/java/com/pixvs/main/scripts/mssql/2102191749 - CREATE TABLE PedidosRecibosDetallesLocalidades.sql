SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[PedidosRecibosDetallesLocalidades](
	[PRDL_PedidoReciboDetalleLocalidadId] [int] IDENTITY(1,1) NOT NULL,
	[PRDL_PRD_PedidoReciboDetalleId] [int] NOT NULL,
	[PRDL_LOC_LocalidadId] [int] NOT NULL,
	[PRDL_Cantidad] [decimal](28,6) NOT NULL,
 CONSTRAINT [PK_PedidosRecibosDetallesLocalidades] PRIMARY KEY CLUSTERED 
(
	[PRDL_PedidoReciboDetalleLocalidadId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PedidosRecibosDetallesLocalidades]  WITH CHECK ADD  CONSTRAINT [FK_PRDL_PRD_PedidoReciboDetalleId] FOREIGN KEY([PRDL_PRD_PedidoReciboDetalleId])
REFERENCES [dbo].[PedidosRecibosDetalles] ([PRD_PedidoReciboDetalleId])
GO

ALTER TABLE [dbo].[PedidosRecibosDetallesLocalidades] CHECK CONSTRAINT [FK_PRDL_PRD_PedidoReciboDetalleId]
GO

ALTER TABLE [dbo].[PedidosRecibosDetallesLocalidades]  WITH CHECK ADD  CONSTRAINT [FK_PRDL_LOC_LocalidadId] FOREIGN KEY([PRDL_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[PedidosRecibosDetallesLocalidades] CHECK CONSTRAINT [FK_PRDL_LOC_LocalidadId]
GO


INSERT INTO [dbo].[PedidosRecibosDetallesLocalidades](
	[PRDL_PRD_PedidoReciboDetalleId],
	[PRDL_LOC_LocalidadId],
	[PRDL_Cantidad]
)
SELECT
	PRD_PedidoReciboDetalleId,
	PRD_LOC_LocalidadId,
	PRD_Cantidad
FROM PedidosRecibosDetalles





ALTER TABLE [dbo].[PedidosRecibos] DROP CONSTRAINT [FK_PR_LOC_LocalidadId]
GO

ALTER TABLE [dbo].[PedidosRecibos] DROP COLUMN [PR_LOC_LocalidadId]
GO


ALTER TABLE [dbo].[PedidosRecibosDetalles] DROP COLUMN [PRD_LOC_LocalidadId]
GO
ALTER TABLE [dbo].[PedidosRecibosDetalles] DROP  CONSTRAINT [DF_PRD_Cantidad]
GO
ALTER TABLE [dbo].[PedidosRecibosDetalles] DROP COLUMN [PRD_Cantidad]
GO