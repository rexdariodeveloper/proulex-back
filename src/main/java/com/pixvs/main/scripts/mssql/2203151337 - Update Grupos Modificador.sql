UPDATE ProgramasGrupos
SET PROGRU_USU_ModificadoPorId = PROGRU_USU_CreadoPorId
WHERE PROGRU_FechaUltimaModificacion IS NOT NULL AND PROGRU_USU_ModificadoPorId IS NULL
GO