CREATE OR ALTER FUNCTION dbo.fn_GruposContratos(@sucursalId int,@programaId int, @cicloId int, @idiomaId int,
					@modalidadId int, @profesorId int, @fechaInicio date, @plantelId int, @fechaInicioContrato date, @fechaFinContrato date,
					@tipoContrato int)
RETURNS INT
AS
BEGIN
DECLARE @MedianScore as INT;
SELECT @MedianScore=COUNT(*)
FROM ProgramasGrupos
INNER JOIN VW_ProgramasGruposProfesores ON PROGRU_GrupoId = grupoId
INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
WHERE
activo = 1 AND
PROGRU_SUC_SucursalId = @sucursalId AND
PROGRU_PROGI_ProgramaIdiomaId = @programaId AND
(@cicloId IS NULL OR PROGRU_PACIC_CicloId = @cicloId) AND
PROGRU_PAMOD_ModalidadId = @modalidadId AND
PROGRU_EMP_EmpleadoId = @profesorId AND
PROGRU_FechaInicio = @fechaInicio AND
PROGI_CMM_Idioma = @idiomaId AND
(@plantelId IS NULL OR PROGRU_SP_SucursalPlantelId = @plantelId)
AND fechaInicio = @fechaInicioContrato
AND fechaFin = @fechaFinContrato
AND (CASE WHEN PROGRU_CMM_TipoGrupoId = 2000392 THEN 2000391 ELSE PROGRU_CMM_TipoGrupoId END) = @tipoContrato
RETURN @MedianScore;
END;
GO