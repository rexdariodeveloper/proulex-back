CREATE OR ALTER FUNCTION [dbo].[fn_getDatosAdicionalesUsuarioDepartamentos] ( @usuarioId INT )
RETURNS TABLE 
AS
RETURN 
(
	WITH cte
	AS 
	(
		SELECT tbl.*,
			CAST(RIGHT('00000' + Ltrim(Rtrim(ROW_NUMBER() OVER(ORDER BY tbl.DEP_DepartamentoId))),5) AS NVARCHAR(255)) AS Orden
		FROM Departamentos AS tbl
		WHERE DEP_DEP_DepartamentoPadreId IS NULL
			AND DEP_Activo = 1

		UNION ALL
	
		SELECT tbl.*,
			CAST(cte.Orden + '.' + RIGHT('00000' + Ltrim(Rtrim(ROW_NUMBER() OVER(ORDER BY tbl.DEP_DepartamentoId))),5) AS NVARCHAR(255)) AS Orden
		FROM Departamentos AS tbl
			INNER JOIN cte ON tbl.DEP_DEP_DepartamentoPadreId = cte.DEP_DepartamentoId
		WHERE tbl.DEP_Activo = 1
	)

	SELECT DEP_DepartamentoId AS Id,
		   DEP_DEP_DepartamentoPadreId AS DepartamentoPadre,
		   DEP_Prefijo AS Prefijo,
		   DEP_Nombre AS Nombre,
		   CONVERT(BIT, CASE WHEN USUD_USU_UsuarioId IS NOT NULL THEN 1 ELSE 0 END) AS Selected,
		   Orden
	FROM cte
		 LEFT JOIN UsuariosDepartamentos ON cte.DEP_DepartamentoId = USUD_DEP_DepartamentoId AND USUD_USU_UsuarioId = @usuarioId
)