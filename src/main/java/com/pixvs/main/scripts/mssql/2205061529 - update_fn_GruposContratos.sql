CREATE OR ALTER FUNCTION dbo.fn_GruposContratos(@sucursalId int,@programaId int, @cicloId int, @idiomaId int,
					@modalidadId int, @profesorId int, @fechaInicio date, @plantelId int, @fechaInicioContrato date, @fechaFinContrato date)
RETURNS INT
AS
BEGIN
DECLARE @MedianScore as INT;
SELECT @MedianScore=COUNT(*)
FROM ProgramasGrupos
INNER JOIN VW_ProgramasGruposProfesores ON PROGRU_GrupoId = grupoId
INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
WHERE PROGRU_SUC_SucursalId = @sucursalId AND
PROGRU_PROGI_ProgramaIdiomaId = @programaId AND
(@cicloId IS NULL OR PROGRU_PACIC_CicloId = @cicloId) AND
PROGRU_PAMOD_ModalidadId = @modalidadId AND
PROGRU_EMP_EmpleadoId = @profesorId AND
PROGRU_FechaInicio = @fechaInicio AND
PROGI_CMM_Idioma = @idiomaId AND
(@plantelId IS NULL OR PROGRU_SP_SucursalPlantelId = @plantelId)
AND PROGRU_Activo = 1
AND fechaInicio = @fechaInicioContrato
AND fechaFin = @fechaFinContrato
RETURN @MedianScore;
END;
GO