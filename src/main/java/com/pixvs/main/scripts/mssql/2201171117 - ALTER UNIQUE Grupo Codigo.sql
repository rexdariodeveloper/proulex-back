DROP INDEX PROGRUCOD_Unique ON ProgramasGrupos
GO

ALTER TABLE ProgramasGrupos
DROP CONSTRAINT UQ__Programa__B2AE62559E33A064
GO

ALTER TABLE ProgramasGrupos
ADD CONSTRAINT PROGRUCOD_Unique UNIQUE (PROGRU_Codigo,PROGRU_FechaInicio,PROGRU_Activo)
GO