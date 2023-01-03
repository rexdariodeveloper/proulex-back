SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* Programas Idiomas Certificaciones Descuentos */

CREATE TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos](
	[PICD_ProgramaIdiomaCertificacionDescuentoId] [int] IDENTITY(1,1) NOT NULL,
	[PICD_PROGIC_ProgramaIdiomaCertificacionId] [int] NOT NULL,
	[PICD_CMM_EstatusId] [int] NOT NULL,
	[PICD_FechaCreacion] [datetime2](7) NOT NULL,
	[PICD_USU_CreadoPorId] [int] NOT NULL,
	[PICD_FechaUltimaModificacion] [datetime2](7) NULL,
	[PICD_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_ProgramasIdiomasCertificacionesDescuentos] PRIMARY KEY CLUSTERED
(
	[PICD_ProgramaIdiomaCertificacionDescuentoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos] ADD  CONSTRAINT [DF_ProgramasIdiomasCertificacionesDescuentos_PICD_FechaCreacion]  DEFAULT (getdate()) FOR [PICD_FechaCreacion]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PICD_PROGIC_ProgramaIdiomaCertificacionId] FOREIGN KEY([PICD_PROGIC_ProgramaIdiomaCertificacionId])
REFERENCES [dbo].[ProgramasIdiomasCertificacion] ([PROGIC_ProgramaIdiomaCertificacionId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos] CHECK CONSTRAINT [FK_PICD_PROGIC_ProgramaIdiomaCertificacionId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PICD_CMM_EstatusId] FOREIGN KEY([PICD_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos] CHECK CONSTRAINT [FK_PICD_CMM_EstatusId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PICD_USU_CreadoPorId] FOREIGN KEY([PICD_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos] CHECK CONSTRAINT [FK_PICD_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos]  WITH CHECK ADD  CONSTRAINT [FK_PICD_USU_ModificadoPorId] FOREIGN KEY([PICD_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentos] CHECK CONSTRAINT [FK_PICD_USU_ModificadoPorId]
GO

/* Programas Idiomas Certificaciones Descuentos */

CREATE TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentosDetalles](
	[PICDD_ProgramaIdiomaCertificacionDescuentoDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[PICDD_PICD_ProgramaIdiomaCertificacionDescuentoId] [int] NOT NULL,
	[PICDD_NumeroNivel] [int] NOT NULL,
	[PICDD_PorcentajeDescuento] [decimal](10,2) NOT NULL,
	[PICDD_Activo] [bit] NOT NULL
 CONSTRAINT [PK_ProgramasIdiomasCertificacionesDescuentosDetalles] PRIMARY KEY CLUSTERED
(
	[PICDD_ProgramaIdiomaCertificacionDescuentoDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentosDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PICDD_PICD_ProgramaIdiomaCertificacionDescuentoId] FOREIGN KEY([PICDD_PICD_ProgramaIdiomaCertificacionDescuentoId])
REFERENCES [dbo].[ProgramasIdiomasCertificacionesDescuentos] ([PICD_ProgramaIdiomaCertificacionDescuentoId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesDescuentosDetalles] CHECK CONSTRAINT [FK_PICDD_PICD_ProgramaIdiomaCertificacionDescuentoId]
GO