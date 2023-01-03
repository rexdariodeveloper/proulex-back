---[ProgramasGruposIncompanyClasesCanceladas]
ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas]
DROP CONSTRAINT FK_PGINCCL_PGINCG_ProgramaIncompanyGrupoId
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas]
DROP COLUMN [PGINCCL_PGINCG_ProgramaIncompanyGrupoId]
GO

-- [ProgramasGruposIncompanyClasesReprogramadas]
ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas]
DROP CONSTRAINT FK_PGINCCR_PGINCG_ProgramaIncompanyGrupoId
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas]
DROP COLUMN [PGINCCR_PGINCG_ProgramaIncompanyGrupoId]
GO

-- [ProgramasGruposIncompanyCriteriosEvaluacion]
ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]
DROP CONSTRAINT FK_PGINCCE_PGINCG_ProgramaIncompanyGrupoId
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]
DROP COLUMN [PGINCCE_PGINCG_ProgramaIncompanyGrupoId]
GO

-- [ProgramasGruposIncompanyHorarios]
ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios]
DROP CONSTRAINT FK_PGINCH_PGINCG_ProgramaIncompanyGrupoId
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios]
DROP COLUMN [PGINCH_PGINCG_ProgramaIncompanyGrupoId]
GO

-- [ProgramasGruposIncompanyMateriales]
ALTER TABLE [dbo].[ProgramasGruposIncompanyMateriales]
DROP COLUMN [PGINCM_PGINCG_ProgramaIncompanyGrupoId]
GO

------------------------------- ADD COLUMN ProgramasGrupos ------------------------------------------------

---[ProgramasGruposIncompanyClasesCanceladas]
ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas]
ADD [PGINCCL_PROGRU_GrupoId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCL_PROGRU_GrupoId] FOREIGN KEY([PGINCCL_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesCanceladas] CHECK CONSTRAINT [FK_PGINCCL_PROGRU_GrupoId]
GO

-- [ProgramasGruposIncompanyClasesReprogramadas]
ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas]
ADD [PGINCCR_PROGRU_GrupoId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCR_PROGRU_GrupoId] FOREIGN KEY([PGINCCR_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyClasesReprogramadas] CHECK CONSTRAINT [FK_PGINCCR_PROGRU_GrupoId]
GO

-- [ProgramasGruposIncompanyCriteriosEvaluacion]
ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]
ADD [PGINCCE_PROGRU_GrupoId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion]  WITH CHECK ADD  CONSTRAINT [FK_PGINCCE_PROGRU_GrupoId] FOREIGN KEY([PGINCCE_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyCriteriosEvaluacion] CHECK CONSTRAINT [FK_PGINCCE_PROGRU_GrupoId]
GO

-- [ProgramasGruposIncompanyHorarios]
ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios]
ADD [PGINCH_PROGRU_GrupoId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios]  WITH CHECK ADD  CONSTRAINT [FK_PGINCH_PROGRU_GrupoId] FOREIGN KEY([PGINCH_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyHorarios] CHECK CONSTRAINT [FK_PGINCH_PROGRU_GrupoId]
GO

-- [ProgramasGruposIncompanyMateriales]
ALTER TABLE [dbo].[ProgramasGruposIncompanyMateriales]
ADD [PGINCM_PROGRU_GrupoId][INT] NULL
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyMateriales]  WITH CHECK ADD  CONSTRAINT [FK_PGINCM_PROGRU_GrupoId] FOREIGN KEY([PGINCM_PROGRU_GrupoId])
REFERENCES [dbo].[ProgramasGrupos] ([PROGRU_GrupoId])
GO

ALTER TABLE [dbo].[ProgramasGruposIncompanyMateriales] CHECK CONSTRAINT [FK_PGINCM_PROGRU_GrupoId]
GO