ALTER TABLE AlumnosAsistencias ADD CONSTRAINT IX_AlumnoAsistencia UNIQUE (AA_ALU_AlumnoId, AA_PROGRU_GrupoId, AA_Fecha)
GO