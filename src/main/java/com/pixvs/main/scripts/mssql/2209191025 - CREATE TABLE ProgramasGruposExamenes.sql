SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ProgramasGruposExamenes](
	[PROGRUE_ProgramaGrupoExamenId] [int] IDENTITY(1,1) NOT NULL,
	[PROGRUE_PROGRU_GrupoId] [int] NOT NULL,
	[PROGRUE_Nombre] [varchar](100) NOT NULL,
	[PROGRUE_Porcentaje] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[PROGRUE_ProgramaGrupoExamenId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasGruposExamenes]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUE_PROGRU_GrupoId] FOREIGN KEY([PROGRUE_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposExamenes] CHECK CONSTRAINT [FK_PROGRUE_PROGRU_GrupoId]
GO

CREATE TABLE [dbo].[ProgramasGruposExamenesDetalles](
	[PROGRUED_ProgramaGrupoExamenDetalleId] [int] IDENTITY(1,1) NOT NULL,
	[PROGRUED_PROGRUE_ProgramaGrupoExamenId] [int] NOT NULL,
	[PROGRUED_PAAE_ActividadEvaluacionId] [int] NOT NULL,
	[PROGRUED_CMM_TestId] [int] NOT NULL,
	[PROGRUED_Puntaje] [decimal](10, 2) NOT NULL,
	[PROGRUED_Time] [int] NULL,
	[PROGRUED_Continuos] [bit] NULL,
PRIMARY KEY CLUSTERED 
(
	[PROGRUED_ProgramaGrupoExamenDetalleId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[ProgramasGruposExamenesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUED_CMM_TestId] FOREIGN KEY([PROGRUED_CMM_TestId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGruposExamenesDetalles] CHECK CONSTRAINT [FK_PROGRUED_CMM_TestId]
GO

ALTER TABLE [dbo].[ProgramasGruposExamenesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUED_PAAE_ActividadEvaluacionId] FOREIGN KEY([PROGRUED_PAAE_ActividadEvaluacionId])
REFERENCES [dbo].[PAActividadesEvaluacion] ([PAAE_ActividadEvaluacionId])
GO

ALTER TABLE [dbo].[ProgramasGruposExamenesDetalles] CHECK CONSTRAINT [FK_PROGRUED_PAAE_ActividadEvaluacionId]
GO

ALTER TABLE [dbo].[ProgramasGruposExamenesDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PROGRUED_PROGRUE_ProgramaGrupoExamenId] FOREIGN KEY([PROGRUED_PROGRUE_ProgramaGrupoExamenId])
REFERENCES [dbo].[ProgramasGruposExamenes] ([PROGRUE_ProgramaGrupoExamenId])
GO

ALTER TABLE [dbo].[ProgramasGruposExamenesDetalles] CHECK CONSTRAINT [FK_PROGRUED_PROGRUE_ProgramaGrupoExamenId]
GO

ALTER TABLE [dbo].[AlumnosExamenesCalificaciones] ADD [AEC_PROGRUED_ProgramaGrupoExamenDetalleId] INT
GO

ALTER TABLE [dbo].[AlumnosExamenesCalificaciones]  WITH CHECK ADD CONSTRAINT [FK_AEC_PROGRUED_ProgramaGrupoExamenDetalleId] FOREIGN KEY([AEC_PROGRUED_ProgramaGrupoExamenDetalleId])
REFERENCES [dbo].[ProgramasGruposExamenesDetalles] ([PROGRUED_ProgramaGrupoExamenDetalleId])
GO

ALTER TABLE [dbo].[AlumnosExamenesCalificaciones] CHECK CONSTRAINT [FK_AEC_PROGRUED_ProgramaGrupoExamenDetalleId]
GO