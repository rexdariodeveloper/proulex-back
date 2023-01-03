/****** Object:  Table [dbo].[AlertasDetalles]    Script Date: 05/01/2021 07:50:49 a. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[AlertasDetalles](
	[ALD_AlertaDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[ALD_ALE_AlertaId] [int] NOT NULL,
	[ALD_ACE_AlertaConfiguracionEtapaId] [int] NOT NULL,
	[ALD_CMM_EstatusId] [int] NULL,
	[ALD_USU_UsuarioId] [int] NOT NULL,
	[ALD_FechaAtendido] [datetime] NULL,
	[ALD_Archivado] [bit] NOT NULL,
	[ALD_Mostrar] [bit] NOT NULL,
	[ALD_Comentario] [nvarchar](2000) NULL,
	[ALD_FechaCreacion] [datetime] NOT NULL,
	[ALD_USU_CreadoPorId] [int] NOT NULL,
	[ALD_FechaUltimaModificacion] [datetime] NULL,
	[ALD_USU_ModificadoPorId] [int] NULL,
	[ALD_Timestamp] [timestamp] NOT NULL,
	[ALD_Visto] [bit] NOT NULL,
	[StartTime] [datetime2](7) GENERATED ALWAYS AS ROW START NOT NULL,
	[EndTime] [datetime2](7) GENERATED ALWAYS AS ROW END NOT NULL,
	[ALD_CMM_TipoAlertaId] [int] NOT NULL,
 CONSTRAINT [PK_AlertasDetalles] PRIMARY KEY NONCLUSTERED 
(
	[ALD_AlertaDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
	PERIOD FOR SYSTEM_TIME ([StartTime], [EndTime])
) ON [PRIMARY]
WITH
(
SYSTEM_VERSIONING = ON ( HISTORY_TABLE = [dbo].[AlertasDetalles_History] )
)
GO

ALTER TABLE [dbo].[AlertasDetalles] ADD  CONSTRAINT [DF_AlertasDetalles_ALD_Archivado]  DEFAULT ((0)) FOR [ALD_Archivado]
GO

ALTER TABLE [dbo].[AlertasDetalles] ADD  CONSTRAINT [DF_AlertasDetalles_ALD_Mostrar]  DEFAULT ((1)) FOR [ALD_Mostrar]
GO

ALTER TABLE [dbo].[AlertasDetalles] ADD  CONSTRAINT [DF_AlertasDetalles_ALD_FechaCreacion]  DEFAULT (getdate()) FOR [ALD_FechaCreacion]
GO

ALTER TABLE [dbo].[AlertasDetalles] ADD  CONSTRAINT [DF_AlertasDetalles_ALD_Visto]  DEFAULT ((0)) FOR [ALD_Visto]
GO

ALTER TABLE [dbo].[AlertasDetalles] ADD  DEFAULT ((1000511)) FOR [ALD_CMM_TipoAlertaId]
GO

ALTER TABLE [dbo].[AlertasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_AlertasDetalles_Alertas1] FOREIGN KEY([ALD_ALE_AlertaId])
REFERENCES [dbo].[Alertas] ([ALE_AlertaId])
GO

ALTER TABLE [dbo].[AlertasDetalles] CHECK CONSTRAINT [FK_AlertasDetalles_Alertas1]
GO

ALTER TABLE [dbo].[AlertasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_AlertasDetalles_AlertasConfigEtapa] FOREIGN KEY([ALD_ACE_AlertaConfiguracionEtapaId])
REFERENCES [dbo].[AlertasConfigEtapa] ([ACE_AlertaConfiguracionEtapaId])
GO

ALTER TABLE [dbo].[AlertasDetalles] CHECK CONSTRAINT [FK_AlertasDetalles_AlertasConfigEtapa]
GO

ALTER TABLE [dbo].[AlertasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_AlertasDetalles_ControlesMaestrosMultiples] FOREIGN KEY([ALD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[AlertasDetalles] CHECK CONSTRAINT [FK_AlertasDetalles_ControlesMaestrosMultiples]
GO

ALTER TABLE [dbo].[AlertasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_AlertasDetalles_Usuarios] FOREIGN KEY([ALD_USU_UsuarioId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[AlertasDetalles] CHECK CONSTRAINT [FK_AlertasDetalles_Usuarios]
GO

ALTER TABLE [dbo].[AlertasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_AlertasDetalles_Usuarios1] FOREIGN KEY([ALD_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[AlertasDetalles] CHECK CONSTRAINT [FK_AlertasDetalles_Usuarios1]
GO

ALTER TABLE [dbo].[AlertasDetalles]  WITH CHECK ADD  CONSTRAINT [FK_AlertasDetalles_Usuarios2] FOREIGN KEY([ALD_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[AlertasDetalles] CHECK CONSTRAINT [FK_AlertasDetalles_Usuarios2]
GO