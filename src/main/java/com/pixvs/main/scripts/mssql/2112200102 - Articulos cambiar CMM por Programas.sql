--- ALTER Articulos
ALTER TABLE [Articulos]
ADD [ART_PROG_ProgramaId] [int] null
GO

---Programas
ALTER TABLE [dbo].[Articulos]  WITH CHECK ADD  CONSTRAINT [FK_ART_PROG_ProgramaId] FOREIGN KEY([ART_PROG_ProgramaId])
REFERENCES [dbo].[Programas] ([PROG_ProgramaId])
GO

ALTER TABLE [dbo].[Articulos] CHECK CONSTRAINT [FK_ART_PROG_ProgramaId]
GO

---UPDATE Articulos
UPDATE Articulos
SET ART_PROG_ProgramaId = 7
WHERE ART_CMM_ProgramaId = 37
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 1
WHERE ART_CMM_ProgramaId = 35
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 5
WHERE ART_CMM_ProgramaId = 36
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 11
WHERE ART_CMM_ProgramaId = 38
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 12
WHERE ART_CMM_ProgramaId = 39
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 17
WHERE ART_CMM_ProgramaId = 40
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 13
WHERE ART_CMM_ProgramaId = 42
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 2
WHERE ART_CMM_ProgramaId = 44
GO

UPDATE Articulos
SET ART_PROG_ProgramaId = 3
WHERE ART_CMM_ProgramaId = 45
GO