CREATE UNIQUE INDEX [UNQ_FechaCancelacion]
ON [dbo].[ProgramasGruposIncompanyClasesCanceladas](PGINCCL_FechaCancelar,PGINCCL_PROGRU_GrupoId)
GO

CREATE OR ALTER FUNCTION existeAlumnoGrupoDuplicado ( @id int, @alumnoId int, @grupoId int )
RETURNS bit
AS
BEGIN
	
	IF EXISTS (
		SELECT *
		FROM AlumnosGrupos
		INNER JOIN Inscripciones on INS_InscripcionId = ALUG_INS_InscripcionId
		WHERE
			ALUG_AlumnoGrupoId != @id
			AND ALUG_ALU_AlumnoId = @alumnoId
			AND ALUG_PROGRU_GrupoId = @grupoId
			AND ALUG_CMM_EstatusId != 2000677 -- Omitir bajas
			AND INS_CMM_EstatusId != 2000512 -- Omitir Inscripciones Canceladas
	) return CAST(1 as bit)

	return CAST(0 AS bit)
END
GO

ALTER TABLE [dbo].[AlumnosGrupos]
DROP CONSTRAINT [CHK_ALUG_Unique]
GO

ALTER TABLE [dbo].[AlumnosGrupos] WITH CHECK ADD CONSTRAINT [CHK_ALUG_Unique] CHECK ([dbo].[existeAlumnoGrupoDuplicado](ALUG_AlumnoGrupoId,ALUG_ALU_AlumnoId,ALUG_PROGRU_GrupoId) = CAST(0 AS bit))
GO