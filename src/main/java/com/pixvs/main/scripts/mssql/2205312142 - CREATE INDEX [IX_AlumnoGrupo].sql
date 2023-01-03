DELETE AlumnosGrupos WHERE ALUG_AlumnoGrupoId IN
(
	select ALUG_AlumnoGrupoId from AlumnosGrupos
	inner join (
	select ALUG_ALU_AlumnoId alumnoId, ALUG_PROGRU_GrupoId grupoId from AlumnosGrupos group by ALUG_ALU_AlumnoId, ALUG_PROGRU_GrupoId having COUNT(*) > 1
	) repetidos on ALUG_ALU_AlumnoId = alumnoId AND ALUG_PROGRU_GrupoId = grupoId
	inner join Inscripciones on ALUG_INS_InscripcionId = INS_InscripcionId
	where INS_CMM_EstatusId IN (2000512, 2000513)
)
GO

CREATE UNIQUE NONCLUSTERED INDEX [IX_AlumnoGrupo] ON [dbo].[AlumnosGrupos]
(
	[ALUG_ALU_AlumnoId] ASC,
	[ALUG_PROGRU_GrupoId] ASC
)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO