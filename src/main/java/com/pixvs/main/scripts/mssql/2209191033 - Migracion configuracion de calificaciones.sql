UPDATE AlumnosExamenesCalificaciones set AEC_PROGRUED_ProgramaGrupoExamenDetalleId = PROGRUED_ProgramaGrupoExamenDetalleId
from AlumnosExamenesCalificaciones
inner join ProgramasIdiomasExamenesDetalles on AEC_PROGIED_ProgramaIdiomaExamenDetalleId = PROGIED_ProgramaIdiomaExamenDetalleId
inner join ProgramasIdiomasExamenes on PROGIED_PROGIE_ProgramaIdiomaExamenId = PROGIE_ProgramaIdiomaExamenId
inner join ProgramasGruposExamenes on AEC_PROGRU_GrupoId = PROGRUE_PROGRU_GrupoId and PROGIE_Nombre = PROGRUE_Nombre
inner join ProgramasGruposExamenesDetalles on PROGRUED_PROGRUE_ProgramaGrupoExamenId = PROGRUE_ProgramaGrupoExamenId and PROGIED_PAAE_ActividadEvaluacionId = PROGRUED_PAAE_ActividadEvaluacionId
GO

ALTER TABLE AlumnosExamenesCalificaciones ALTER COLUMN AEC_PROGIED_ProgramaIdiomaExamenDetalleId INT NULL
GO