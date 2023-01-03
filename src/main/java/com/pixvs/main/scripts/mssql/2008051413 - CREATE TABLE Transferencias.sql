SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

DROP TABLE IF EXISTS [Transferencias]
GO

CREATE TABLE [dbo].[Transferencias](
	[TRA_TransferenciaId] [int] IDENTITY(1,1) NOT NULL,
	[TRA_Codigo] [nvarchar](150) NOT NULL,
	[TRA_Fecha] [date] NOT NULL,
	[TRA_LOC_LocalidadOrigenId] [int] NOT NULL,
	[TRA_LOC_LocalidadDestinoId] [int] NOT NULL,
	[TRA_Comentario] [nvarchar](150) NULL,
	[TRA_CMM_EstatusId] [int] NOT NULL,
	[TRA_USU_CreadoPorId] [int] NOT NULL,
	[TRA_USU_ModificadoPorId] [int] NULL,
	[TRA_FechaCreacion] [datetime2](7) NOT NULL,
	[TRA_FechaModificacion] [datetime2](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[TRA_TransferenciaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Transferencias]  WITH CHECK ADD  CONSTRAINT [FK_TRA_LOC_LocalidadOrigenId] FOREIGN KEY([TRA_LOC_LocalidadOrigenId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[Transferencias] CHECK CONSTRAINT [FK_TRA_LOC_LocalidadOrigenId]
GO

ALTER TABLE [dbo].[Transferencias]  WITH CHECK ADD  CONSTRAINT [FK_TRA_LOC_LocalidadDestinoId] FOREIGN KEY([TRA_LOC_LocalidadDestinoId])
REFERENCES [dbo].[Localidades] ([LOC_LocalidadId])
GO

ALTER TABLE [dbo].[Transferencias] CHECK CONSTRAINT [FK_TRA_LOC_LocalidadDestinoId]
GO

ALTER TABLE [dbo].[Transferencias]  WITH CHECK ADD  CONSTRAINT [FK_TRA_CMM_EstatusId] FOREIGN KEY([TRA_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Transferencias] CHECK CONSTRAINT [FK_TRA_CMM_EstatusId]
GO

ALTER TABLE [dbo].[Transferencias]  WITH CHECK ADD  CONSTRAINT [FK_TRA_USU_CreadoPorId] FOREIGN KEY([TRA_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Transferencias] CHECK CONSTRAINT [FK_TRA_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[Transferencias]  WITH CHECK ADD  CONSTRAINT [FK_TRA_USU_ModificadoPorIdTRA_USU_ModificadoPorId] FOREIGN KEY([TRA_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[Transferencias] CHECK CONSTRAINT [FK_TRA_USU_ModificadoPorIdTRA_USU_ModificadoPorId]
GO

ALTER TABLE [dbo].[Transferencias] ADD  CONSTRAINT [DF_TRA_FechaCreacion]  DEFAULT (GETDATE()) FOR [TRA_FechaCreacion]
GO