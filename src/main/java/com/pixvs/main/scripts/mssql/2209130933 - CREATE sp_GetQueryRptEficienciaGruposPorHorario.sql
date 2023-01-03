SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ========================================================
-- Author:		Javier Elías
-- Create date: 06/08/2022
-- Modified date: 
-- Description:	Procesador obtener el Query que genera los datos
--						del Reporte de Eficiencia de Grupos por Horario
-- ========================================================
CREATE OR ALTER PROCEDURE [dbo].[sp_GetQueryRptEficienciaGruposPorHorario]
	@sedesId VARCHAR(4000),
	@fecha VARCHAR(100),
	@modalidadId VARCHAR(5)
AS
BEGIN
		SET NOCOUNT ON;
		
		DECLARE @consultaInsertHorarios VARCHAR(4000) = ''
		DECLARE @consultaSumHorarios VARCHAR(4000) = ''

		DECLARE @contador INT = 1
		DECLARE @contadorHorarios INT = ( SELECT COUNT(Horario) FROM VW_RptEficienciaGruposHorarios  )

		DECLARE @horario VARCHAR(4000)
		DECLARE @columna VARCHAR(4000)

		DECLARE @columnasTabla VARCHAR(4000) = 'SedeId INT, Sede VARCHAR(4000), '

		WHILE ( @contador <= @contadorHorarios )
		BEGIN
			SELECT @horario = Horario, @columna = Columna FROM VW_RptEficienciaGruposHorarios WHERE Id = @contador 
	
			SET @columnasTabla = @columnasTabla + @columna + ' INT' + CASE WHEN @contador < @contadorHorarios THEN ', ' ELSE '' END
			SET @consultaInsertHorarios = @consultaInsertHorarios + 'CASE WHEN horario = ''' + @horario + ''' AND inscripcionId IS NOT NULL THEN 1 ELSE 0 END AS ' + @columna + ', '
			SET @consultaSumHorarios = @consultaSumHorarios + 'SUM(' + @columna + ') AS ' + @columna + CASE WHEN @contador < @contadorHorarios THEN ', ' ELSE '' END

			SET @contador = @contador + 1
		END

		DECLARE @consultaInsert2 VARCHAR(4000) = 
		'SELECT grupoId,
				sedeId,
				sede,
				grupo,
				fecha,' + 
				@consultaInsertHorarios + 
				'nivel,
				curso,
				modalidadId
		FROM
		(
			SELECT *
			FROM VW_RptEficienciaGrupos
			WHERE sedeId IN(' + @sedesId + ')
					AND FORMAT(fecha, ''dd/MM/yyyy'') = ''' + @fecha + '''
					AND modalidadId = ' + @modalidadId + '
		) AS todo'

		SELECT 
		'SELECT /*ROW_NUMBER() OVER (ORDER BY sede, curso, nivel, grupo, fecha) AS id,*/
				   sede,
				   curso,
				   nivel,
				   grupo,
				   FORMAT(fecha, ''dd/MM/yyyy'') AS fecha,' +
				   @consultaSumHorarios + 
			' FROM
			(' +
				@consultaInsert2 +
			') AS horarios
			GROUP BY sedeId,
					 sede,
					 curso,
					 nivel,
					 grupo,
					 fecha,
					 modalidadId
			ORDER BY sede, curso, nivel, grupo, fecha'
END