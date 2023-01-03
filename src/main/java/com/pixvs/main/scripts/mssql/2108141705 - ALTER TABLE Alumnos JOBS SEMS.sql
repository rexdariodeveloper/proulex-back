ALTER TABLE [dbo].[Alumnos] ADD [ALU_CodigoAlumnoUDG] NVARCHAR(15) NULL
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_Grupo] NVARCHAR(5) NULL
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_GradoId] INT NULL
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_TurnoId] INT NULL
GO

ALTER TABLE [dbo].[Alumnos] ADD [ALU_CMM_TipoAlumnoId] INT NULL
GO

-- Constraints FK
ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_GradoId] FOREIGN KEY([ALU_CMM_GradoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_CMM_GradoId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_TurnoId] FOREIGN KEY([ALU_CMM_TurnoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_CMM_TurnoId]
GO

ALTER TABLE [dbo].[Alumnos]  WITH CHECK ADD  CONSTRAINT [FK_ALU_CMM_TipoAlumnoId] FOREIGN KEY([ALU_CMM_TipoAlumnoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Alumnos] CHECK CONSTRAINT [FK_ALU_CMM_TipoAlumnoId]
GO