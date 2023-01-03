SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getDiaHabiles](@fechaInicio Date,@fechaFin Date,@domingo bit, @lunes bit, @martes bit, @miercoles bit, @jueves bit, @viernes bit, @sabado bit)
RETURNS @fechasValidas table(
	id int,
	fecha Date
)

AS BEGIN 
	DECLARE @fecha as Date;
	DECLARE @dow as int;
	DECLARE @ff as Date;
	DECLARE @id as int;
	SET @fecha = @fechaInicio
	SET @id = 1
	--INSERT @fechasValidas
	WHILE @fecha <= @fechaFin BEGIN
		SET @dow = DATEPART(WEEKDAY, @fecha)
		SET @ff = @fecha

		IF (Select CAST(CONCAT(YEAR(GETDATE()),'-',EMPDNLF_Mes,'-',EMPDNLF_Dia) AS DATE) as fecha from EmpresaDiasNoLaboralesFijos where CAST(CONCAT(YEAR(GETDATE()),'-',EMPDNLF_Mes,'-',EMPDNLF_Dia) AS DATE) = @ff) is null
			AND (SELECT EMPDNL_Fecha from EmpresaDiasNoLaborales WHERE EMPDNL_Fecha= @ff) IS NULL
			BEGIN
			INSERT @fechasValidas
			SELECT  @id as id,CASE
					WHEN @dow = 1 and @domingo = 1 then @fecha
					WHEN @dow = 2 and @lunes = 1 then @fecha
					WHEN @dow = 3 and @martes = 1 then @fecha
					WHEN @dow = 4 and @miercoles = 1 then @fecha
					WHEN @dow = 5 and @jueves = 1 then @fecha
					WHEN @dow = 6 and @viernes = 1 then @fecha
					WHEN @dow = 7 and @sabado = 1 then @fecha
					END as fecha
			END
		ELSE BEGIN SET @fecha = @fecha END
		SET @fecha = DATEADD(day, 1, @fecha)
	END

RETURN;
END
GO

