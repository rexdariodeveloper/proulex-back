ALTER TABLE [dbo].[ProgramasIdiomas] ADD [PROGI_Codigo] NVARCHAR(10) NULL
GO

ALTER TABLE [dbo].[ProgramasIdiomas] ADD [PROGI_Nombre] NVARCHAR(100) NULL
GO

ALTER TABLE [dbo].[ProgramasIdiomas] ADD [PROGI_CMM_TipoWorkshopId] [INT] NULL
GO

ALTER TABLE [dbo].[ProgramasIdiomas]  WITH CHECK ADD  CONSTRAINT FK_PROGI_CMM_TipoWorkshopId FOREIGN KEY([PROGI_CMM_TipoWorkshopId])
REFERENCES [dbo].[ControlesMaestrosMultiples] ([CMM_ControlId])
GO

ALTER TABLE [dbo].[ProgramasIdiomas] CHECK CONSTRAINT FK_PROGI_CMM_TipoWorkshopId
GO