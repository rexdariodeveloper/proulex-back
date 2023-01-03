
/****** Object:  UserDefinedFunction [dbo].[fn_getFechaInicioFinQuincena]    Script Date: 20/09/2021 12:32:36 p. m. ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getFechaInicioFinQuincena] (@fecha Date)
RETURNS @tablaTMP table(
	fechaInicio Date,
	fechaFin Date
)
AS BEGIN
	DECLARE @Anio CHAR(4) = YEAR(@fecha);
	DECLARE @fechaActual datetime = convert(date, @fecha)
	DECLARE @fechaQuincenaActual datetime
	DECLARE @dias int
	set @dias = case when day(@fechaActual) < 16 then 1 else 16 end
	set @fechaQuincenaActual = dateadd(day, @dias-day(@fechaActual), @fechaActual)
	INSERT @tablaTMP
	SELECT TOP 1	@fechaQuincenaActual as fechaInicio,fecha_fin as fechaFin
	FROM
	(
	SELECT CASE WHEN Q.n = 1 --Si es quincena
					THEN DATEADD( DD, 14, DATEADD(MM, M.n, @Anio)) --Sumamos 14 días al primero del mes
				ELSE EOMONTH( DATEADD(MM, M.n, @Anio)) --Si no, obtenemos el último día
				END AS fecha_fin,
			ROW_NUMBER() OVER( ORDER BY M.n, Q.n DESC) as numeroQuincena -- Generamos el consecutivo de quincenas
	FROM (VALUES(0),(1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11)) M(n) --Generamos números para los meses
	CROSS JOIN (VALUES(0),(1))Q(n) --Generamos filas para saber si es quincena o fin de mes
	
	) Q1
	WHERE fecha_fin >= @fecha and @fechaQuincenaActual<=@fecha
	ORDER BY Q1.numeroQuincena, Q1.fecha_fin DESC 
RETURN;
END
GO
