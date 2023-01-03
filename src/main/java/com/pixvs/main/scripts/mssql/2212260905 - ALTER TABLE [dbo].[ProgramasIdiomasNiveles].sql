ALTER TABLE [dbo].[ProgramasIdiomasNiveles] ADD [PROGIN_PAMOD_PAModalidadId] [INT] NULL
GO
ALTER TABLE [dbo].[ProgramasIdiomasNiveles]  WITH CHECK ADD  CONSTRAINT FK_PROGIN_PAMOD_PAModalidadId FOREIGN KEY([PROGIN_PAMOD_PAModalidadId])
REFERENCES [dbo].[PAModalidades] ([PAMOD_ModalidadId])
GO

ALTER TABLE [dbo].[ProgramasIdiomasNiveles] CHECK CONSTRAINT FK_PROGIN_PAMOD_PAModalidadId
GO