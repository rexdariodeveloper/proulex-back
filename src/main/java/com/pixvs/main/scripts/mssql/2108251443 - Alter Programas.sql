UPDATE Programas
SET PROG_JOBS=1
WHERE PROG_Codigo='JBP'
GO

UPDATE Programas
SET PROG_JOBSSEMS=1
WHERE PROG_Codigo='JSM'
GO

ALTER TABLE [dbo].[ProgramasIdiomas]
ALTER COLUMN [PROGI_Descripcion] varchar(3000) not null
GO