ALTER TABLE [dbo].[AlumnosGrupos] ADD [ALUG_INS_InscripcionId] INT
GO

ALTER TABLE [dbo].[AlumnosGrupos]  WITH CHECK ADD  CONSTRAINT [FK_ALUG_INS_InscripcionId] FOREIGN KEY([ALUG_INS_InscripcionId])
REFERENCES [dbo].[Inscripciones] ([INS_InscripcionId])
GO

ALTER TABLE [dbo].[AlumnosGrupos] CHECK CONSTRAINT [FK_ALUG_INS_InscripcionId]
GO

