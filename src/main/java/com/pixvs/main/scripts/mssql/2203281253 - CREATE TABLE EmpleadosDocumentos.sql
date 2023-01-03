SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[EmpleadosDocumentos](
	[EMPD_EmpleadoDocumentoId] [int] IDENTITY(1,1) NOT NULL,
	[EMPD_EMP_EmpleadoId] [int] NOT NULL,
	[EMPD_CMM_TipoDocumentoId] [int] NOT NULL,
	[EMPD_ARC_ArchivoId] [int] NOT NULL,
	[EMPD_FechaVencimiento] [date] NULL,
	[EMPD_Activo] [bit] NOT NULL,
	[EMPD_FechaCreacion] [datetime2](7) NOT NULL,
	[EMPD_USU_CreadoPorId] [int] NOT NULL,
	[EMPD_FechaUltimaModificacion] [datetime2](7) NULL,
	[EMPD_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_EmpleadosDocumentos] PRIMARY KEY CLUSTERED
(
	[EMPD_EmpleadoDocumentoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[EmpleadosDocumentos] ADD  CONSTRAINT [DF_EmpleadosDocumentos_EMPD_FechaCreacion]  DEFAULT (getdate()) FOR [EMPD_FechaCreacion]
GO

ALTER TABLE [dbo].[EmpleadosDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_EMPD_EMP_EmpleadoId] FOREIGN KEY([EMPD_EMP_EmpleadoId])
REFERENCES [dbo].[Empleados] ([EMP_EmpleadoId])
GO

ALTER TABLE [dbo].[EmpleadosDocumentos] CHECK CONSTRAINT [FK_EMPD_EMP_EmpleadoId]
GO

ALTER TABLE [dbo].[EmpleadosDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_EMPD_CMM_TipoDocumentoId] FOREIGN KEY([EMPD_CMM_TipoDocumentoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[EmpleadosDocumentos] CHECK CONSTRAINT [FK_EMPD_CMM_TipoDocumentoId]
GO

ALTER TABLE [dbo].[EmpleadosDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_EMPD_ARC_ArchivoId] FOREIGN KEY([EMPD_ARC_ArchivoId])
REFERENCES [dbo].[Archivos] ([ARC_ArchivoId])
GO

ALTER TABLE [dbo].[EmpleadosDocumentos] CHECK CONSTRAINT [FK_EMPD_ARC_ArchivoId]
GO

ALTER TABLE [dbo].[EmpleadosDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_EMPD_USU_CreadoPorId] FOREIGN KEY([EMPD_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosDocumentos] CHECK CONSTRAINT [FK_EMPD_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[EmpleadosDocumentos]  WITH CHECK ADD  CONSTRAINT [FK_EMPD_USU_ModificadoPorId] FOREIGN KEY([EMPD_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[EmpleadosDocumentos] CHECK CONSTRAINT [FK_EMPD_USU_ModificadoPorId]
GO
