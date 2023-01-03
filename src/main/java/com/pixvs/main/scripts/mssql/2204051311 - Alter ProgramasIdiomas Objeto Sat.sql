-- PROGI_CMM_ObjetoImpuestoId
ALTER TABLE [dbo].[ProgramasIdiomas]
ADD [PROGI_CMM_ObjetoImpuestoId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT [FK_PROGI_CMM_ObjetoImpuestoId] FOREIGN KEY([PROGI_CMM_ObjetoImpuestoId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT [FK_PROGI_CMM_ObjetoImpuestoId]
GO