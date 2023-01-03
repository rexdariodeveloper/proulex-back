
ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles] ADD [PACD_CMM_IdiomaId] [int] NOT NULL
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles]  WITH CHECK ADD  CONSTRAINT [FK_PACD_CMM_IdiomaId] FOREIGN KEY([PACD_CMM_IdiomaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles] CHECK CONSTRAINT [FK_PACD_CMM_IdiomaId]
GO

ALTER TABLE [dbo].[ProgramacionAcademicaComercialDetalles] ADD [PACD_NumeroClases] [int] NOT NULL
GO