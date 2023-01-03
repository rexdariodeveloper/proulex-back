SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [PedidosRecibos]
GO

CREATE TABLE [dbo].[PedidosRecibos](
	[PR_PedidoReciboId] 		[int] IDENTITY(1,1) NOT NULL,
	[PR_Codigo] 				[varchar](150) NOT NULL,
	[PR_Fecha] 					[datetime2] NOT NULL,
	[PR_PED_PedidoId]			[int] NOT NULL,
	[PR_LOC_LocalidadId] 		[int] NOT NULL,
	[PR_Comentario] 			[varchar](150) NULL,
	[PR_CMM_EstatusId] 			[int] NOT NULL,
	[PR_USU_CreadoPorId] 		[int] NOT NULL,
	[PR_USU_ModificadoPorId] 	[int] NULL,
	[PR_FechaCreacion] 			[datetime2](7) NOT NULL, 
	[PR_FechaModificacion] 		[datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[PR_PedidoReciboId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[PedidosRecibos]  WITH CHECK ADD  CONSTRAINT [FK_PR_PED_PedidoId] FOREIGN KEY([PR_PED_PedidoId])
REFERENCES [dbo].[Pedidos] ([PED_PedidoId])
GO

ALTER TABLE [dbo].[PedidosRecibos] CHECK CONSTRAINT [FK_PR_PED_PedidoId]
GO

ALTER TABLE [dbo].[PedidosRecibos]  WITH CHECK ADD  CONSTRAINT [FK_PR_LOC_LocalidadId] FOREIGN KEY([PR_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[PedidosRecibos] CHECK CONSTRAINT [FK_PR_LOC_LocalidadId]
GO

ALTER TABLE [dbo].[PedidosRecibos]  WITH CHECK ADD  CONSTRAINT [FK_PR_CMM_EstatusId] FOREIGN KEY([PR_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[PedidosRecibos] CHECK CONSTRAINT [FK_PR_CMM_EstatusId]
GO

ALTER TABLE [dbo].[PedidosRecibos]  WITH CHECK ADD  CONSTRAINT [FK_PR_USU_CreadoPorId] FOREIGN KEY([PR_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PedidosRecibos] CHECK CONSTRAINT [FK_PR_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[PedidosRecibos]  WITH CHECK ADD  CONSTRAINT [FK_PR_USU_ModificadoPorIdPR_USU_ModificadoPorId] FOREIGN KEY([PR_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[PedidosRecibos] CHECK CONSTRAINT [FK_PR_USU_ModificadoPorIdPR_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[PedidosRecibos] ADD  CONSTRAINT [DF_PR_FechaCreacion]  DEFAULT (GETDATE()) FOR [PR_FechaCreacion]
GO