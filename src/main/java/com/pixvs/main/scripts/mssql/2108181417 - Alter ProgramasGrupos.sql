ALTER TABLE ProgramasGrupos
ADD PROGRU_CalificacionMinima [int] not null default(0)
GO

ALTER TABLE ProgramasGrupos
ADD PROGRU_FaltasPermitidas [int] not null default(0)
GO

UPDATE GRU
SET GRU.PROGRU_FaltasPermitidas = IDI.PROGI_FaltasPermitidas, GRU.PROGRU_CalificacionMinima = IDI.PROGI_CalificacionMinima
FROM ProgramasGrupos GRU
INNER JOIN ProgramasIdiomas IDI ON IDI.PROGI_ProgramaIdiomaId = GRU.PROGRU_PROGI_ProgramaIdiomaId
GO