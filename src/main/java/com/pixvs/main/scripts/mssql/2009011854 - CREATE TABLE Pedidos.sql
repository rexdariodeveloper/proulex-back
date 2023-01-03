SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [Pedidos]
GO

CREATE TABLE [dbo].[Pedidos](
	[PED_PedidoId] 				[int] IDENTITY(1,1) NOT NULL,
	[PED_Codigo] 				[varchar](150) NOT NULL,
	[PED_Fecha] 				[datetime2] NOT NULL,
	[PED_LOC_LocalidadOrigenId] [int] NOT NULL,
	[PED_LOC_LocalidadCEDISId] 	[int] NOT NULL,
	[PED_Comentario] 			[varchar](150) NULL,
	[PED_CMM_EstatusId] 		[int] NOT NULL,
	[PED_USU_CreadoPorId] 		[int] NOT NULL,
	[PED_USU_ModificadoPorId] 	[int] NULL,
	[PED_FechaCreacion] 		[datetime2](7) NOT NULL,
	[PED_FechaModificacion] 	[datetime2](7) NULL,

PRIMARY KEY CLUSTERED 
(
	[PED_PedidoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_PED_LOC_LocalidadOrigenId] FOREIGN KEY([PED_LOC_LocalidadOrigenId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_PED_LOC_LocalidadOrigenId]
GO

ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_PED_LOC_LocalidadCEDISId] FOREIGN KEY([PED_LOC_LocalidadCEDISId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_PED_LOC_LocalidadCEDISId]
GO

ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_PED_CMM_EstatusId] FOREIGN KEY([PED_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_PED_CMM_EstatusId]
GO

ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_PED_USU_CreadoPorId] FOREIGN KEY([PED_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_PED_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Pedidos]  WITH CHECK ADD  CONSTRAINT [FK_PED_USU_ModificadoPorIdPED_USU_ModificadoPorId] FOREIGN KEY([PED_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Pedidos] CHECK CONSTRAINT [FK_PED_USU_ModificadoPorIdPED_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Pedidos] ADD  CONSTRAINT [DF_PED_FechaCreacion]  DEFAULT (GETDATE()) FOR [PED_FechaCreacion]
GO