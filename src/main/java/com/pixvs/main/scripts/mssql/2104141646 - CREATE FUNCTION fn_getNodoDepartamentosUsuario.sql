SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 
	Author: Angel Daniel Hernández Silva
	Name: fn_getNodoDepartamentosUsuario
	Description: Busca el id del departamento de nivel mas bajo del usuario
**/
CREATE OR ALTER FUNCTION [dbo].[fn_getNodoDepartamentosUsuario](@usuarioId int)
RETURNS int
AS
BEGIN
	DECLARE @departamentoId int;

	WITH DepartamentosNodos AS (
		SELECT
			USUD_DEP_DepartamentoId AS departamentoId,
			1 AS nivel
		FROM UsuariosDepartamentos
		INNER JOIN Departamentos ON DEP_DepartamentoId = USUD_DEP_DepartamentoId AND DEP_DEP_DepartamentoPadreId IS NULL
		WHERE USUD_USU_UsuarioId = @usuarioId

		UNION ALL

		SELECT
			DEP_DepartamentoId AS departamentoId,
			nivel + 1 AS nivel
		FROM Departamentos
		INNER JOIN UsuariosDepartamentos ON USUD_DEP_DepartamentoId = DEP_DepartamentoId AND USUD_USU_UsuarioId = @usuarioId
		INNER JOIN DepartamentosNodos ON DEP_DEP_DepartamentoPadreId = departamentoId
	)
	SELECT TOP 1 @departamentoId = departamentoId
	FROM DepartamentosNodos
	ORDER BY nivel DESC

	RETURN @departamentoId;
END