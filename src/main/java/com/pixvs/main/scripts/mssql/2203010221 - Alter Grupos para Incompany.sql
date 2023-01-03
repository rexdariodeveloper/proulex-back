ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_PGINCG_ProgramaIncompanyId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_PGINCG_ProgramaIncompanyId] FOREIGN KEY([PROGRU_PGINCG_ProgramaIncompanyId])
REFERENCES [dbo].[ProgramasGruposIncompany] ([PGINC_ProgramaIncompanyId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_PGINCG_ProgramaIncompanyId]
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_Alias][VARCHAR](500) NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_Nombre][VARCHAR](50) NULL
GO