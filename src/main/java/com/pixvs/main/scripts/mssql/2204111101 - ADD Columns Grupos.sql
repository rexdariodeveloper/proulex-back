-- [PROGRU_CMM_ZonaId]
ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_CMM_ZonaId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_CMM_ZonaId] FOREIGN KEY([PROGRU_CMM_ZonaId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_CMM_ZonaId]
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_PrecioVentaGrupo][decimal](10,2) NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_PrecioVentaCurso][decimal](10,2) NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_PrecioVentaLibro][decimal](10,2) NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_PrecioVentaCertificacion][decimal](10,2) NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_PorcentajeComision][decimal](10,2) NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_PorcentajeApoyoTransporte][decimal](10,2) NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ADD [PROGRU_KilometrosDistancia][decimal](10,2) NULL
GO