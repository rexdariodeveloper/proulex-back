DELETE AlumnosAsistencias WHERE AA_AlumnoAsistenciaId IN 
(
	select 
		MAX(AA_AlumnoAsistenciaId) _max
	from 
		AlumnosAsistencias 
	group by 
		AA_ALU_AlumnoId, AA_PROGRU_GrupoId, AA_Fecha
	having
		COUNT(AA_AlumnoAsistenciaId) > 1
)
GO