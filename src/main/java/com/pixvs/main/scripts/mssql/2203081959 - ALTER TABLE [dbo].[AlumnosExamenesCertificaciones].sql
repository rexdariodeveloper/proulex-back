ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] ADD [ALUEC_PROGI_ProgramaIdiomaId] INT
GO
ALTER TABLE [dbo].[AlumnosExamenesCertificaciones]  WITH CHECK ADD  CONSTRAINT [FK_ALUEC_PROGI_ProgramaIdiomaId] FOREIGN KEY([ALUEC_PROGI_ProgramaIdiomaId])
REFERENCES [dbo].[ProgramasIdiomas] ([PROGI_ProgramaIdiomaId])
GO
ALTER TABLE [dbo].[AlumnosExamenesCertificaciones] CHECK CONSTRAINT [FK_ALUEC_PROGI_ProgramaIdiomaId]
GO