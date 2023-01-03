UPDATE 
	[ProgramasGrupos]
	SET [PROGRU_CalificacionMinima] = 60	
FROM 
	[ProgramasGrupos]
	INNER JOIN [ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
	INNER JOIN [Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
WHERE
	[PROG_JOBSSEMS] = 1
	AND [PROGRU_FechaInicio] <= GETDATE()
	AND [PROGRU_FechaFin] >= GETDATE()
GO -- 407