SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [TransferenciasDetalles]
GO

CREATE TABLE [dbo].[TransferenciasDetalles](
	[TRAD_TransferenciaDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[TRAD_TRA_TransferenciaId] [int] NOT NULL,
	[TRAD_ART_ArticuloId] [int] NOT NULL,
	[TRAD_UM_UnidadMedidaId] [smallint] NOT NULL,
	[TRAD_Cantidad] [decimal](28, 6) NOT NULL,
	[TRAD_CantidadTransferida] [decimal](28, 6) NOT NULL,
	[TRAD_CantidadDevuelta] [decimal](28, 6) NOT NULL,
	[TRAD_Spill] [decimal](28, 6) NOT NULL,
	[TRAD_CMM_EstatusId] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[TRAD_TransferenciaDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[TransferenciasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TRAD_TRA_TransferenciaId] FOREIGN KEY([TRAD_TRA_TransferenciaId])
REFERENCES [dbo].[Transferencias] ([TRA_TransferenciaId])
GO

ALTER TABLE [dbo].[TransferenciasDetalles] CHECK CONSTRAINT [FK_TRAD_TRA_TransferenciaId]
GO

ALTER TABLE [dbo].[TransferenciasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TRAD_ART_ArticuloId] FOREIGN KEY([TRAD_ART_ArticuloId])
REFERENCES [dbo].[Articulos] ([ART_ArticuloId])
GO

ALTER TABLE [dbo].[TransferenciasDetalles] CHECK CONSTRAINT [FK_TRAD_ART_ArticuloId]
GO

ALTER TABLE [dbo].[TransferenciasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TRAD_UM_UnidadMedidaId] FOREIGN KEY([TRAD_UM_UnidadMedidaId])
REFERENCES [dbo].[UnidadesMedidas] ([UM_UnidadMedidaId])
GO

ALTER TABLE [dbo].[TransferenciasDetalles] CHECK CONSTRAINT [FK_TRAD_UM_UnidadMedidaId]
GO

ALTER TABLE [dbo].[TransferenciasDetalles] ADD  CONSTRAINT [DF_TRAD_CantidadTransferida]  DEFAULT (0) FOR [TRAD_CantidadTransferida]
GO

ALTER TABLE [dbo].[TransferenciasDetalles] ADD  CONSTRAINT [DF_TRAD_CantidadDevuelta]  DEFAULT (0) FOR [TRAD_CantidadDevuelta]
GO

ALTER TABLE [dbo].[TransferenciasDetalles] ADD  CONSTRAINT [DF_TRAD_Spill]  DEFAULT (0) FOR [TRAD_Spill]
GO

ALTER TABLE [dbo].[TransferenciasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_TRAD_CMM_EstatusId] FOREIGN KEY([TRAD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[TransferenciasDetalles] CHECK CONSTRAINT [FK_TRAD_CMM_EstatusId]
GO