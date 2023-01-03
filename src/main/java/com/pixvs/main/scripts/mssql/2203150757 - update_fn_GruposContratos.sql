CREATE OR ALTER FUNCTION dbo.fn_GruposContratos(@sucursalId int,@programaId int, @cicloId int, @idiomaId int,
					@modalidadId int, @profesorId int, @fechaInicio date, @plantelId int)
RETURNS INT
AS
BEGIN
DECLARE @MedianScore as INT;
SELECT @MedianScore=COUNT(*)
FROM ProgramasGrupos
INNER JOIN ProgramasIdiomas on PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
WHERE PROGRU_SUC_SucursalId = @sucursalId AND
PROGRU_PROGI_ProgramaIdiomaId = @programaId AND
PROGRU_PACIC_CicloId = @cicloId AND
PROGRU_PAMOD_ModalidadId = @modalidadId AND
PROGRU_EMP_EmpleadoId = @profesorId AND
PROGRU_FechaInicio >= @fechaInicio AND
PROGI_CMM_Idioma = @idiomaId AND
(PROGRU_SP_SucursalPlantelId IS NULL OR PROGRU_SP_SucursalPlantelId = @plantelId)
AND PROGRU_Activo = 1
-- AND PROGRU_CMM_EstatusId = 2000620
RETURN @MedianScore;
END;
GO