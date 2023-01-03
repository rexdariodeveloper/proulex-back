-- [[PROGRU_GrupoReferenciaId]]
ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_GrupoReferenciaId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_GrupoReferenciaId] FOREIGN KEY([PROGRU_GrupoReferenciaId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_GrupoReferenciaId]
GO