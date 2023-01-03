--------------------- BecasSolicitudes ---------------------------------------------
CREATE TABLE [dbo].[BecasSolicitudes](
	[BECS_BecaSolicitudId] [int] IDENTITY(1,1) NOT NULL,
	[BECS_Codigo] [nvarchar](30) NOT NULL,
	[BECS_CMM_EstatusId] [int] NOT NULL,
	[BECS_FechaCreacion] [datetime2](7) NOT NULL,
	[BECS_USU_CreadoPorId] [int] NOT NULL,
	[BECS_FechaUltimaModificacion] [datetime2](7) NULL,
	[BECS_USU_ModificadoPorId] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[BECS_BecaSolicitudId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[BecasSolicitudes] ADD  CONSTRAINT [DF_BecasSolicitudes_BECS_FechaCreacion]  DEFAULT (getdate()) FOR [BECS_FechaCreacion]
GO

--

ALTER TABLE [dbo].[BecasSolicitudes]  WITH CHECK ADD  CONSTRAINT [FK_BECS_USU_CreadoPorId] FOREIGN KEY([BECS_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[BecasSolicitudes] CHECK CONSTRAINT [FK_BECS_USU_CreadoPorId]
GO

--

ALTER TABLE [dbo].[BecasSolicitudes]  WITH CHECK ADD  CONSTRAINT [FK_BECS_USU_ModificadoPorId] FOREIGN KEY([BECS_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[BecasSolicitudes] CHECK CONSTRAINT [FK_BECS_USU_ModificadoPorId]
GO

--

ALTER TABLE [dbo].[BecasSolicitudes]  WITH CHECK ADD  CONSTRAINT [FK_BECS_CMM_EstatusId] FOREIGN KEY([BECS_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[BecasSolicitudes] CHECK CONSTRAINT [FK_BECS_CMM_EstatusId]
GO


-------------------------------------------------------------------------------------------------------------------------
ALTER TABLE [dbo].[BecasUDG]
ADD [BECU_CodigoAlumno][varchar](20) NULL
GO

ALTER TABLE [dbo].[BecasUDG]
ADD [BECU_BECS_BecaSolicitudId][INT] NULL
GO

ALTER TABLE [dbo].[BecasUDG]  WITH CHECK ADD  CONSTRAINT [FK_BECU_BECS_BecaSolicitudId] FOREIGN KEY([BECU_BECS_BecaSolicitudId])
REFERENCES [dbo].[BecasSolicitudes] ([BECS_BecaSolicitudId])
GO

ALTER TABLE [dbo].[BecasUDG] CHECK CONSTRAINT [FK_BECU_BECS_BecaSolicitudId]
GO

ALTER TABLE [dbo].[BecasUDG]
ADD [BECU_Comentarios][varchar](500) NULL
GO