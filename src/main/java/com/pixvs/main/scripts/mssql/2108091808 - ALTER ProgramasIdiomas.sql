ALTER TABLE [ProgramasIdiomas] 
DROP CONSTRAINT DF__Programas__PROGI__450A2E92
GO

ALTER TABLE [dbo].[ProgramasIdiomas]
ALTER COLUMN [PROGI_FaltasPermitidas] [decimal](10,2) NOT NULL
GO