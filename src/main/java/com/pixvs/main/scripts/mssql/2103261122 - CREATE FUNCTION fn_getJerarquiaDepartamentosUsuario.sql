SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 
	Author: Angel Daniel Hernández Silva
	Name: fn_getJerarquiaDepartamentosUsuario
	Description: Busca el departamento del usuario y obtiene los ids del departamento mas sus hijos
**/
CREATE OR ALTER FUNCTION [dbo].[fn_getJerarquiaDepartamentosUsuario](@usuarioId int)
RETURNS @tbl TABLE(departamentoId int)
AS
BEGIN
	WITH DepartamentosJerarquia AS (
		SELECT
			USUD_DEP_DepartamentoId AS departamentoId
		FROM Usuarios
		INNER JOIN UsuariosDepartamentos ON USUD_USU_UsuarioId = USU_UsuarioId
		WHERE USU_UsuarioId = 1

		UNION ALL

		SELECT
			DEP_DepartamentoId AS departamentoId
		FROM Departamentos
		INNER JOIN DepartamentosJerarquia ON DEP_DEP_DepartamentoPadreId = departamentoId
	)
	INSERT INTO @tbl
	SELECT DISTINCT departamentoId FROM DepartamentosJerarquia

	RETURN
END