CREATE TABLE [dbo].[AlumnosAsistencias](
	[AA_AlumnoAsistenciaId] [int] IDENTITY(1,1) NOT NULL,
	[AA_ALU_AlumnoId] [int] NOT NULL,
	[AA_PROGRU_GrupoId] [int] NOT NULL,
	[AA_Fecha] [datetime2](7) NOT NULL,
	[AA_CMM_TipoAsistenciaId] [int] NOT NULL,
	[AA_Comentario] [varchar](280) NULL,
	[AA_MinutosRetardo] [int] NULL,
	[AA_MotivoJustificante] [varchar](280) NULL,
	[AA_FechaCreacion] [datetime2](7) NOT NULL,
	[AA_FechaModificacion] [datetime2](7) NULL,
	[AA_USU_CreadoPorId] [int] NULL,
	[AA_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[AA_AlumnoAsistenciaId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK
ALTER TABLE [dbo].[AlumnosAsistencias]  WITH CHECK ADD  CONSTRAINT [FK_AA_ALU_AlumnoId] FOREIGN KEY([AA_ALU_AlumnoId])
REFERENCES [dbo].[Alumnos] ([ALU_AlumnoId])
GO
ALTER TABLE [dbo].[AlumnosAsistencias] CHECK CONSTRAINT [FK_AA_ALU_AlumnoId]
GO

ALTER TABLE [dbo].[AlumnosAsistencias]  WITH CHECK ADD  CONSTRAINT [FK_AA_PROGRU_GrupoId] FOREIGN KEY([AA_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO
ALTER TABLE [dbo].[AlumnosAsistencias] CHECK CONSTRAINT [FK_AA_PROGRU_GrupoId]
GO

ALTER TABLE [dbo].[AlumnosAsistencias]  WITH CHECK ADD  CONSTRAINT [FK_AA_CMM_TipoAsistenciaId] FOREIGN KEY([AA_CMM_TipoAsistenciaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlumnosAsistencias] CHECK CONSTRAINT [FK_AA_CMM_TipoAsistenciaId]
GO

ALTER TABLE [dbo].[AlumnosAsistencias]  WITH CHECK ADD  CONSTRAINT [FK_AA_USU_CreadoPorId] FOREIGN KEY([AA_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlumnosAsistencias] CHECK CONSTRAINT [FK_AA_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[AlumnosAsistencias]  WITH CHECK ADD  CONSTRAINT [FK_AA_USU_ModificadoPorId] FOREIGN KEY([AA_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlumnosAsistencias] CHECK CONSTRAINT [FK_AA_USU_ModificadoPorId]
GO