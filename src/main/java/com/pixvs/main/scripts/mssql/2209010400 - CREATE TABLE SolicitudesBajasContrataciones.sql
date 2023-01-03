DROP TABLE IF EXISTS [dbo].[SolicitudesBajasContrataciones]

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SolicitudesBajasContrataciones](
	[SBC_SolicitudBajaContratacionId] [int] IDENTITY(1,1) NOT NULL,
	[SBC_Codigo] [nvarchar](50) NOT NULL,
	[SBC_EMPCO_EmpleadoContratoId] [int] NOT NULL,
	[SBC_CMM_TipoMotivoId] [int] NOT NULL,
	[SBC_FechaSeparacion] [date] NOT NULL,
	[SBC_Comentario] [nvarchar](500) NULL,
	[SBC_CMM_EstatusId] [int] NOT NULL,
	[SBC_FechaCreacion] [datetime2](7) NOT NULL,
	[SBC_USU_CreadoPorId] [int] NOT NULL,
	[SBC_FechaUltimaModificacion] [datetime2](7) NULL,
	[SBC_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_SolicitudesBajasContrataciones] PRIMARY KEY CLUSTERED
(
	[SBC_SolicitudBajaContratacionId] ASC,
	[SBC_Codigo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones] ADD  CONSTRAINT [DF_SolicitudesBajasContrataciones_SBC_FechaCreacion]  DEFAULT (getdate()) FOR [SBC_FechaCreacion]
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SBC_CMM_TipoMotivoId] FOREIGN KEY([SBC_CMM_TipoMotivoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones] CHECK CONSTRAINT [FK_SBC_CMM_TipoMotivoId]
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SBC_CMM_EstatusId] FOREIGN KEY([SBC_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones] CHECK CONSTRAINT [FK_SBC_CMM_EstatusId]
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SBC_USU_CreadoPorId] FOREIGN KEY([SBC_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones] CHECK CONSTRAINT [FK_SBC_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones]  WITH CHECK ADD  CONSTRAINT [FK_SBC_USU_ModificadoPorId] FOREIGN KEY([SBC_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[SolicitudesBajasContrataciones] CHECK CONSTRAINT [FK_SBC_USU_ModificadoPorId]
GO


