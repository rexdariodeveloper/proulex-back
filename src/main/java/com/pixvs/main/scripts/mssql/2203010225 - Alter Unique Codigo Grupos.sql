DROP INDEX IX_PROGRUCOD_Unique ON ProgramasGrupos
GO

CREATE UNIQUE INDEX IX_PROGRUCOD_Unique
ON ProgramasGrupos(PROGRU_Codigo,PROGRU_FechaInicio,PROGRU_Activo,PROGRU_CMM_EstatusId)
WHERE PROGRU_Activo = 1 AND PROGRU_PGINCG_ProgramaIncompanyId IS NULL
GO