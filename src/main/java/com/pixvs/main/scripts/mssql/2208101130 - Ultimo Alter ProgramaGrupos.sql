ALTER TABLE [dbo].[ProgramasGrupos]
ADD PROGRU_PREINC_PrecioIncompanyId INT NULL
GO

ALTER TABLE [dbo].[ProgramasGrupos]
DROP CONSTRAINT FK_PROGRU_CMM_ZonaId
GO

ALTER TABLE [dbo].[ProgramasGrupos]
DROP COLUMN [PROGRU_CMM_ZonaId]
GO