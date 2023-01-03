CREATE TABLE [dbo].[AlumnosGrupos](
	[ALUG_AlumnoGrupoId] [int] IDENTITY(1,1) NOT NULL,
	[ALUG_ALU_AlumnoId] [int] NOT NULL,
	[ALUG_PROGRU_GrupoId] [int] NOT NULL,
	[ALUG_Asistencias] [int] NOT NULL DEFAULT 0,
	[ALUG_Faltas] [int] NOT NULL DEFAULT 0,
	[ALUG_MinutosRetardo] [int] NOT NULL DEFAULT 0,
	[ALUG_CalificacionFinal] [decimal](28,10) NULL,
	[ALUG_CalificacionConvertida] [decimal](28,10) NULL,
	[ALUG_CMM_EstatusId] [int] NOT NULL,
	[ALUG_FechaCreacion] [datetime2](7) NOT NULL,
	[ALUG_FechaModificacion] [datetime2](7) NULL,
	[ALUG_USU_CreadoPorId] [int] NULL,
	[ALUG_USU_ModificadoPorId] [int] NULL
PRIMARY KEY CLUSTERED 
(
	[ALUG_AlumnoGrupoId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

-- Constraints FK
ALTER TABLE [dbo].[AlumnosGrupos]  WITH CHECK ADD  CONSTRAINT [FK_ALUG_ALU_AlumnoId] FOREIGN KEY([ALUG_ALU_AlumnoId])
REFERENCES [dbo].[Alumnos] ([ALU_AlumnoId])
GO
ALTER TABLE [dbo].[AlumnosGrupos] CHECK CONSTRAINT [FK_ALUG_ALU_AlumnoId]
GO

ALTER TABLE [dbo].[AlumnosGrupos]  WITH CHECK ADD  CONSTRAINT [FK_ALUG_PROGRU_GrupoId] FOREIGN KEY([ALUG_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO
ALTER TABLE [dbo].[AlumnosGrupos] CHECK CONSTRAINT [FK_ALUG_PROGRU_GrupoId]
GO

ALTER TABLE [dbo].[AlumnosGrupos]  WITH CHECK ADD  CONSTRAINT [FK_ALUG_CMM_EstatusId] FOREIGN KEY([ALUG_CMM_EstatusId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[AlumnosGrupos] CHECK CONSTRAINT [FK_ALUG_CMM_EstatusId]
GO

ALTER TABLE [dbo].[AlumnosGrupos]  WITH CHECK ADD  CONSTRAINT [FK_ALUG_USU_CreadoPorId] FOREIGN KEY([ALUG_USU_CreadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlumnosGrupos] CHECK CONSTRAINT [FK_ALUG_USU_CreadoPorId]
GO

ALTER TABLE [dbo].[AlumnosGrupos]  WITH CHECK ADD  CONSTRAINT [FK_ALUG_USU_ModificadoPorId] FOREIGN KEY([ALUG_USU_ModificadoPorId])
REFERENCES [dbo].[Usuarios] ([USU_UsuarioId])
GO
ALTER TABLE [dbo].[AlumnosGrupos] CHECK CONSTRAINT [FK_ALUG_USU_ModificadoPorId]
GO