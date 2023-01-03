ALTER TABLE ProgramasGrupos
ADD [PROGRU_SP_SucursalPlantelId] [int] null
GO

ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_SP_SucursalPlantelId] FOREIGN KEY([PROGRU_SP_SucursalPlantelId])
REFERENCES [dbo].[SucursalesPlanteles] ([SP_SucursalPlantelId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_SP_SucursalPlantelId]
GO