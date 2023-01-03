SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [InventariosFisicos]
GO

CREATE TABLE [dbo].[InventariosFisicos](
	[IF_InventarioFisicoId] [int] IDENTITY(1,1) NOT NULL,
	[IF_Codigo] [nvarchar](150) NOT NULL,
	[IF_Fecha] [date] NOT NULL,
	[IF_LOC_LocalidadId] [int] NOT NULL,
	[IF_CMM_EstatusId] [int] NOT NULL,
	[IF_USU_AfectadoPorId] [int] NULL,
	[IF_FechaAfectacion] [date] NULL,	
	[IF_USU_CreadoPorId] [int] NOT NULL,
	[IF_USU_ModificadoPorId] [int] NULL,
	[IF_FechaCreacion] [datetime2](7) NOT NULL,
	[IF_FechaModificacion] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[IF_InventarioFisicoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[InventariosFisicos]  WITH CHECK ADD  CONSTRAINT [FK_IF_LOC_LocalidadId] FOREIGN KEY([IF_LOC_LocalidadId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[InventariosFisicos] CHECK CONSTRAINT [FK_IF_LOC_LocalidadId]
GO

ALTER TABLE [dbo].[InventariosFisicos]  WITH CHECK ADD  CONSTRAINT [FK_IF_CMM_EstatusId] FOREIGN KEY([IF_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[InventariosFisicos] CHECK CONSTRAINT [FK_IF_CMM_EstatusId]
GO

ALTER TABLE [dbo].[InventariosFisicos]  WITH CHECK ADD  CONSTRAINT [FK_IF_USU_AfectadoPorId] FOREIGN KEY([IF_USU_AfectadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[InventariosFisicos] CHECK CONSTRAINT [FK_IF_USU_AfectadoPorId]
GO

ALTER TABLE [dbo].[InventariosFisicos]  WITH CHECK ADD  CONSTRAINT [FK_IF_USU_CreadoPorId] FOREIGN KEY([IF_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[InventariosFisicos] CHECK CONSTRAINT [FK_IF_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[InventariosFisicos]  WITH CHECK ADD  CONSTRAINT [FK_IF_USU_ModificadoPorIdIF_USU_ModificadoPorId] FOREIGN KEY([IF_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[InventariosFisicos] CHECK CONSTRAINT [FK_IF_USU_ModificadoPorIdIF_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[InventariosFisicos] ADD  CONSTRAINT [DF_IF_FechaCreacion]  DEFAULT (GETDATE()) FOR [IF_FechaCreacion]
GO

ALTER TABLE [dbo].[InventariosFisicos] ADD  CONSTRAINT [DF_IF_FechaModificacion]  DEFAULT (GETDATE()) FOR [IF_FechaModificacion]
GO