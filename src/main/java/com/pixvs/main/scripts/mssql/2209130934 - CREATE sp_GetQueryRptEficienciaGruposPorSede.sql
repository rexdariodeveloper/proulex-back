SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ========================================================
-- Author:		Javier Elías
-- Create date: 06/08/2022
-- Modified date: 
-- Description:	Procesador obtener el Query que genera los datos
--						del Reporte de Eficiencia de Grupos por Sede
-- ========================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_GetQueryRptEficienciaGruposPorSede]
	@sedesId VARCHAR(4000),
	@fecha VARCHAR(100),
	@modalidadId VARCHAR(5)
AS
BEGIN
		SET NOCOUNT ON;
		
		DECLARE @consultaBase VARCHAR(4000) = 
		'(
			SELECT ROW_NUMBER() OVER (ORDER BY Sede, Inscritos) AS Id,
				   sedeId,
				   sede,
				   inscritos,
				   COUNT(grupo) AS grupos,
				   SUM(COUNT(grupo)) OVER (PARTITION BY sedeId) AS totalSede,
				   MAX(inscritos) OVER(PARTITION BY NULL) AS maximoInscritos
			FROM
			(
				SELECT sedeId,
					   sede,
					   grupo,
					   SUM(CASE WHEN inscripcionId IS NOT NULL THEN 1 ELSE 0 END) AS inscritos
				FROM VW_RptEficienciaGrupos
				WHERE sedeId IN(' + @sedesId + ')
					  AND FORMAT(fecha, ''dd/MM/yyyy'') = ''' + @fecha + '''
					  AND modalidadId = ' + @modalidadId + '
				GROUP BY sedeId,
						 sede,
						 grupo
			) AS todo
			GROUP BY sedeId,
					 sede,
					 inscritos
		) AS tabla
		'

		DECLARE @query VARCHAR(4000) =

		'DECLARE @contador INT = 0
		DECLARE @maximoInscritos INT = ISNULL(( SELECT TOP 1 MaximoInscritos FROM' + @consultaBase + ' ), 0)
		DECLARE @consultaInscritos VARCHAR(4000) = ''''
		DECLARE @consultaSuma VARCHAR(4000) = ''''

		WHILE ( @contador <= @maximoInscritos )
		BEGIN
			SET @consultaInscritos = @consultaInscritos + ''CASE WHEN Inscritos = '' + CONVERT(VARCHAR(10), @contador) + '' THEN Grupos ELSE 0 END AS _'' + CONVERT(VARCHAR(10), @contador) + '', ''
			SET @consultaSuma = @consultaSuma + ''SUM(_'' + CONVERT(VARCHAR(10), @contador) + '') AS _'' + CONVERT(VARCHAR(10), @contador) + '', ''
	
			SET @contador = @contador + 1
		END

		DECLARE @consultaTotales VARCHAR(4000) =
		''SELECT Sede,'' +
			   @consultaSuma + 
			   ''TotalSede
		FROM
		(
			SELECT Sede,'' +
				   @consultaInscritos + 
				   ''TotalSede
			FROM ' + REPLACE(@consultaBase, '''', '''''') + 
		') AS todo
		GROUP BY Sede, TotalSede
		ORDER BY Sede''

		SELECT @consultaTotales'

		EXECUTE (@query)
END