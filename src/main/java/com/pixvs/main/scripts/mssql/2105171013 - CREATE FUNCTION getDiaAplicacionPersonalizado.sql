CREATE OR ALTER FUNCTION [dbo].[getDiaAplicacionPersonalizada] (@lunes bit, @martes bit, @miercoles bit, @jueves bit, @viernes bit, @sabado bit, @diasAplica Integer, @fechaInicio DATE)

RETURNS DATE

AS BEGIN
	DECLARE @fechaAplicacion DATE;
	--DECLARE @NextDayID INT  = 0 -- 0=Mon, 1=Tue, 2 = Wed, ..., 5=Sat, 6=Sun
	DECLARE @diasPasado INT = 0
	SET @fechaAplicacion = @fechaInicio
	WHILE @diasPasado < @diasAplica --Revisamos cuantos días han pasado
		BEGIN
			--SET @diasPasado = @diasPasado+1
			--SET @fechaAplicacion =
			IF DATENAME(DW, @fechaAplicacion) = 'monday' and @lunes = 1 
			BEGIN
				SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				SET @diasPasado = @diasPasado+1
			END
			ELSE IF DATENAME(DW, @fechaAplicacion) = 'tuesday' and @martes = 1 
			BEGIN
				SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				SET @diasPasado = @diasPasado+1
			END
			ELSE IF DATENAME(DW, @fechaAplicacion) = 'wednesday' and @miercoles = 1 
			BEGIN
				SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				SET @diasPasado = @diasPasado+1
			END
			ELSE IF DATENAME(DW, @fechaAplicacion) = 'thursday' and @jueves = 1 
			BEGIN
				SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				SET @diasPasado = @diasPasado+1
			END
			ELSE IF DATENAME(DW, @fechaAplicacion) = 'friday' and @viernes = 1 
			BEGIN
				SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				SET @diasPasado = @diasPasado+1
			END
			ELSE IF DATENAME(DW, @fechaAplicacion) = 'saturday' and @sabado = 1 
			BEGIN
				SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				SET @diasPasado = @diasPasado+1
			END
			ELSE IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
			ELSE SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
		END
	return DATEADD(d,-1,@fechaAplicacion)
END	
GO