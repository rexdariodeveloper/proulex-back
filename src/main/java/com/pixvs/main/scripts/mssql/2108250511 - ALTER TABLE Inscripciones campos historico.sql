ALTER TABLE [dbo].[Inscripciones] ADD [INS_CMM_InstitucionAcademicaId] INT NULL
GO

ALTER TABLE [dbo].[Inscripciones] ADD [INS_CMM_CarreraId] INT NULL
GO

ALTER TABLE [dbo].[Inscripciones] ADD [INS_Carrera] NVARCHAR(255) NULL
GO

ALTER TABLE [dbo].[Inscripciones] ADD [INS_CMM_TurnoId] INT NULL
GO

ALTER TABLE [dbo].[Inscripciones] ADD [INS_CMM_GradoId] INT NULL
GO

ALTER TABLE [dbo].[Inscripciones] ADD [INS_Grupo] NVARCHAR(5) NULL
GO

-- Constraints

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_CMM_InstitucionAcademicaId] FOREIGN KEY([INS_CMM_InstitucionAcademicaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_CMM_InstitucionAcademicaId]
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_CMM_CarreraId] FOREIGN KEY([INS_CMM_CarreraId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_CMM_CarreraId]
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_CMM_TurnoId] FOREIGN KEY([INS_CMM_TurnoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_CMM_TurnoId]
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_CMM_GradoId] FOREIGN KEY([INS_CMM_GradoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO
ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_CMM_GradoId]
GO