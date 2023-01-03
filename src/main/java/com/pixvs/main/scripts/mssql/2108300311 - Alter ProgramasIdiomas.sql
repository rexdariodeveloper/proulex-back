ALTER TABLE [ProgramasIdiomas]
ADD [PROGI_TAB_TabuladorId] [int] null
GO

ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT [FK_PROGI_TAB_TabuladorId] FOREIGN KEY([PROGI_TAB_TabuladorId])
REFERENCES [dbo].[Tabuladores] ([TAB_TabuladorId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT [FK_PROGI_TAB_TabuladorId]
GO