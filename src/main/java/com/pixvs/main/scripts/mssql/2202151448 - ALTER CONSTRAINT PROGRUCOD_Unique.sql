ALTER TABLE ProgramasGrupos DROP CONSTRAINT PROGRUCOD_Unique
GO

ALTER TABLE ProgramasGrupos
ADD CONSTRAINT PROGRUCOD_Unique UNIQUE (PROGRU_Codigo,PROGRU_FechaInicio,PROGRU_Activo,PROGRU_CMM_EstatusId,PROGRU_FechaUltimaModificacion)
GO