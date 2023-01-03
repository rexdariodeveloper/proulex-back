SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/* Programas Idiomas Certificaciones Vales */

CREATE TABLE [dbo].[ProgramasIdiomasCertificacionesVales](
	[PICV_ProgramaIdiomaCertificacionValeId] [int] IDENTITY(1,1) NOT NULL,
	[PICV_ALUG_AlumnoGrupoId] [int] NOT NULL,
	[PICV_PICD_ProgramaIdiomaCertificacionDescuentoId] [int] NOT NULL,
	[PICV_PorcentajeDescuento] [decimal](10,2) NOT NULL,
	[PICV_FechaVigenciaInicio] [date] NOT NULL,
	[PICV_FechaVigenciaFin] [date] NOT NULL,
	[PICV_Costo] [decimal](18,2) NOT NULL,
	[PICV_CostoFinal] [decimal](18,2) NOT NULL,
	[PICV_CMM_EstatusId] [int] NOT NULL,
	[PICV_FechaCreacion] [datetime2](7) NOT NULL,
	[PICV_USU_CreadoPorId] [int] NOT NULL,
	[PICV_FechaUltimaModificacion] [datetime2](7) NULL,
	[PICV_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_ProgramasIdiomasCertificacionesVales] PRIMARY KEY CLUSTERED
(
	[PICV_ProgramaIdiomaCertificacionValeId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales] ADD  CONSTRAINT [DF_ProgramasIdiomasCertificacionesVales_PICV_FechaCreacion]  DEFAULT (getdate()) FOR [PICV_FechaCreacion]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales]  WITH CHECK ADD  CONSTRAINT [FK_PICV_ALUG_AlumnoGrupoId] FOREIGN KEY([PICV_ALUG_AlumnoGrupoId])
REFERENCES [dbo].[AlumnosGrupos] ([ALUG_AlumnoGrupoId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales] CHECK CONSTRAINT [FK_PICV_ALUG_AlumnoGrupoId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales]  WITH CHECK ADD  CONSTRAINT [FK_PICV_PICD_ProgramaIdiomaCertificacionDescuentoId] FOREIGN KEY([PICV_PICD_ProgramaIdiomaCertificacionDescuentoId])
REFERENCES [dbo].[ProgramasIdiomasCertificacionesDescuentos] ([PICD_ProgramaIdiomaCertificacionDescuentoId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales] CHECK CONSTRAINT [FK_PICV_PICD_ProgramaIdiomaCertificacionDescuentoId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales]  WITH CHECK ADD  CONSTRAINT [FK_PICV_CMM_EstatusId] FOREIGN KEY([PICV_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales] CHECK CONSTRAINT [FK_PICV_CMM_EstatusId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales]  WITH CHECK ADD  CONSTRAINT [FK_PICV_USU_CreadoPorId] FOREIGN KEY([PICV_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales] CHECK CONSTRAINT [FK_PICV_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales]  WITH CHECK ADD  CONSTRAINT [FK_PICV_USU_ModificadoPorId] FOREIGN KEY([PICV_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVales] CHECK CONSTRAINT [FK_PICV_USU_ModificadoPorId]
GO


/* Programas Idiomas Certificaciones Vigencias */

CREATE TABLE [dbo].[ProgramasIdiomasCertificacionesVigencias](
	[PICVI_ProgramaIdiomaCertificacionVigenciaId] [int] IDENTITY(1,1) NOT NULL,
	[PICVI_Vigencia] [int] NOT NULL,
	[PICVI_Activo] [bit] NOT NULL,
	[PICVI_FechaCreacion] [datetime2](7) NOT NULL,
	[PICVI_USU_CreadoPorId] [int] NOT NULL,
	[PICVI_FechaUltimaModificacion] [datetime2](7) NULL,
	[PICVI_USU_ModificadoPorId] [int] NULL,
 CONSTRAINT [PK_ProgramasIdiomasCertificacionesVigencias] PRIMARY KEY CLUSTERED
(
	[PICVI_ProgramaIdiomaCertificacionVigenciaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVigencias]  WITH CHECK ADD  CONSTRAINT [FK_PICVI_USU_CreadoPorId] FOREIGN KEY([PICVI_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVigencias] CHECK CONSTRAINT [FK_PICVI_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVigencias]  WITH CHECK ADD  CONSTRAINT [FK_PICVI_USU_ModificadoPorId] FOREIGN KEY([PICVI_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasCertificacionesVigencias] CHECK CONSTRAINT [FK_PICVI_USU_ModificadoPorId]
GO

/* PRETERMINADO (DEFAULT) */
INSERT INTO ProgramasIdiomasCertificacionesVigencias(PICVI_Vigencia, PICVI_Activo, PICVI_FechaCreacion, PICVI_USU_CreadoPorId)
VALUES (40, 1, GETDATE(), 1)
GO
