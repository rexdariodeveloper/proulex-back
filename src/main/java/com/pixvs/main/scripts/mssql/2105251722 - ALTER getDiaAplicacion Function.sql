CREATE OR ALTER FUNCTION [dbo].[getDiaAplicacion] (@modalidad varchar, @diasAplica Integer, @fechaInicio DATE)

RETURNS DATE

AS BEGIN
	DECLARE @fechaAplicacion DATE;
	--DECLARE @NextDayID INT  = 0 -- 0=Mon, 1=Tue, 2 = Wed, ..., 5=Sat, 6=Sun
	DECLARE @diasPasado INT = 0
	SET @fechaAplicacion = @fechaInicio
	IF @modalidad = 1 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(DAY, @diasPasado,@fechaInicio)
			END

	 END
	 IF @modalidad = 2 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				--SET @fechaAplicacion =
				IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'saturday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'friday' SET @fechaAplicacion = DATEADD(d, 3, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
			END

	 END
	 IF @modalidad = 3 BEGIN
		WHILE @diasPasado <= @diasAplica -1 --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				--SET @fechaAplicacion =
				IF DATENAME(DW, @fechaAplicacion) = 'monday' SET @fechaAplicacion = DATEADD(d, 5, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'tuesday' SET @fechaAplicacion = DATEADD(d, 4, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'wednesday' SET @fechaAplicacion = DATEADD(d, 3, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'thursday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'friday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 6, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(d, 7, @fechaAplicacion)
			END

	 END
	 IF @modalidad = 4 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				--SET @fechaAplicacion =
				IF DATENAME(DW, @fechaAplicacion) = 'tuesday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'saturday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'friday' SET @fechaAplicacion = DATEADD(d, 3, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'thursday' SET @fechaAplicacion = DATEADD(d, 3, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
			END

	 END
	 IF @modalidad = 5 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				--SET @fechaAplicacion =
				IF DATENAME(DW, @fechaAplicacion) = 'monday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'saturday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'friday' SET @fechaAplicacion = DATEADD(d, 3, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'wednesday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
			END

	 END
	 IF @modalidad = 6 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				--SET @fechaAplicacion =
				IF DATENAME(DW, @fechaAplicacion) = 'tuesday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'saturday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'thursday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
			END

	 END
	 IF @modalidad = 7 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				--SET @fechaAplicacion =
				IF DATENAME(DW, @fechaAplicacion) = 'monday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'saturday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'friday' SET @fechaAplicacion = DATEADD(d, 3, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'wednesday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
			END

	 END
	 IF @modalidad = 8 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				IF DATENAME(DW, @fechaAplicacion) = 'sunday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'saturday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(DAY, @diasPasado,@fechaInicio)
			END

	 END
	 IF @modalidad = 9 BEGIN
		WHILE @diasPasado <= @diasAplica --Revisamos cuantos días han pasado
			BEGIN
				SET @diasPasado = @diasPasado+1
				--SET @fechaAplicacion =
				IF DATENAME(DW, @fechaAplicacion) = 'monday' SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
				ELSE IF DATENAME(DW, @fechaAplicacion) = 'wednesday' SET @fechaAplicacion = DATEADD(d, 2, @fechaAplicacion)
				ELSE SET @fechaAplicacion = DATEADD(d, 1, @fechaAplicacion)
			END

	 END
	return @fechaAplicacion
END	
GO

