CREATE OR ALTER FUNCTION [dbo].[fn_getCupoDisponible](@grupoId INT)
RETURNS INT
AS
BEGIN
	DECLARE @cupoDisponible INT = 0;
	SELECT
		@cupoDisponible = ([PROGRU_Cupo] - COALESCE(COUNT([INS_InscripcionId]), 0)) 
	FROM
		[dbo].[ProgramasGrupos] 
		LEFT JOIN [dbo].[Inscripciones] ON [INS_PROGRU_GrupoId] = [PROGRU_GrupoId] AND [INS_CMM_EstatusId] IN ( 2000510, 2000511 )
	WHERE
		[PROGRU_GrupoId] = @grupoId 
	GROUP BY
		[PROGRU_GrupoId], [PROGRU_Cupo];
	RETURN @cupoDisponible;
END