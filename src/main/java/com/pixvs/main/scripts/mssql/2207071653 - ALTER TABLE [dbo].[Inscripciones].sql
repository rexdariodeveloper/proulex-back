ALTER TABLE [dbo].[Inscripciones] ADD [INS_CMM_InscripcionOrigenId] INT DEFAULT NULL
GO

ALTER TABLE [dbo].[Inscripciones]  WITH CHECK ADD  CONSTRAINT [FK_INS_CMM_InscripcionOrigenId] FOREIGN KEY([INS_CMM_InscripcionOrigenId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[Inscripciones] CHECK CONSTRAINT [FK_INS_CMM_InscripcionOrigenId]
GO