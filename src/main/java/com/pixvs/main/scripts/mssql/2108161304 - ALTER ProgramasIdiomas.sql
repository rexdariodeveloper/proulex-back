ALTER TABLE Programas
ADD PROG_JOBS bit null
GO

ALTER TABLE Programas
ADD PROG_JOBSSEMS bit null
GO

UPDATE Pro
SET Pro.PROG_JOBS = 1
FROM Programas Pro
WHERE Pro.PROG_Codigo='JBP'
GO

UPDATE Pro
SET Pro.PROG_JOBSSEMS = 1
FROM Programas Pro
WHERE Pro.PROG_Codigo='JSM'
GO

ALTER TABLE ProgramasGrupos
ADD PROGRU_PACIC_CicloId int null
GO

ALTER TABLE [dbo].[ProgramasGrupos]  WITH CHECK ADD  CONSTRAINT [FK_PROGRU_PACIC_CicloId] FOREIGN KEY([PROGRU_PACIC_CicloId])
REFERENCES [dbo].[PACiclos] ([PACIC_CicloId])
GO

ALTER TABLE [dbo].[ProgramasGrupos] CHECK CONSTRAINT [FK_PROGRU_PACIC_CicloId]
GO

ALTER TABLE [dbo].[ProgramasGrupos]
ALTER COLUMN [PROGRU_PAC_ProgramacionAcademicaComercialId] [INT] null
GO