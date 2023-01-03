/* Inserta todos los registros de la tabla de asistencias sumando sus faltas, asistencias y retardos, los registros se cargan con el estatus 'Activo' */
INSERT INTO [dbo].[AlumnosGrupos](
	[ALUG_ALU_AlumnoId],
	[ALUG_PROGRU_GrupoId],
	[ALUG_Asistencias],
	[ALUG_Faltas],
	[ALUG_MinutosRetardo],
	[ALUG_CMM_EstatusId],
	[ALUG_FechaCreacion],
	[ALUG_USU_CreadoPorId]
) ( SELECT 
		AA_ALU_AlumnoId,
		AA_PROGRU_GrupoId,
		SUM(case when AA_CMM_TipoAsistenciaId = 2000550 then 1 else 0 end) asistencias,
		SUM(case when AA_CMM_TipoAsistenciaId = 2000551 then 1 else 0 end) faltas,
		SUM(coalesce(AA_MinutosRetardo,0)) retardos,
		2000670 estatus,
		GETDATE() fecha,
		1 creador
	FROM 
		[dbo].[AlumnosAsistencias] 
		INNER JOIN [dbo].[ProgramasGrupos] ON [AA_PROGRU_GrupoId] = [PROGRU_GrupoId]
		INNER JOIN [dbo].[ProgramasIdiomas] ON [PROGRU_PROGI_ProgramaIdiomaId] = [PROGI_ProgramaIdiomaId]
		INNER JOIN [dbo].[Programas] ON [PROGI_PROG_ProgramaId] = [PROG_ProgramaId]
	WHERE
		[PROG_JOBS] = 1 OR [PROG_JOBSSEMS] = 1
	GROUP BY [AA_ALU_AlumnoId], [AA_PROGRU_GrupoId] )
GO

UPDATE [dbo].[AlumnosGrupos] SET [ALUG_CMM_EstatusId] = [dbo].[fn_getEstatusAlumno]([ALUG_ALU_AlumnoId], [ALUG_PROGRU_GrupoId])
GO