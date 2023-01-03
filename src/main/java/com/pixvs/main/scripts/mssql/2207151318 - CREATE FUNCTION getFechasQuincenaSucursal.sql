/**
 * Created by Angel Daniel Hernández Silva on 07/07/2022.
 */
 
CREATE OR ALTER FUNCTION [dbo].[getFechasQuincenaSucursal] (@fechaInicio date, @fechaFin date, @sucursalId int)
RETURNS @tblFechas table(
	fechaInicio Date,
	fechaFin Date,
	totalPrenomina int
)
AS BEGIN
	DECLARE @anioInicio int = DATEPART(YEAR,@fechaInicio);
	DECLARE @mesInicio int = DATEPART(MONTH,@fechaInicio);
	DECLARE @diaInicio int = DATEPART(DAY,@fechaInicio);

	DECLARE @anio int = DATEPART(YEAR,@fechaInicio);
	DECLARE @mes int = DATEPART(MONTH,@fechaInicio);

	DECLARE @anioFin int = DATEPART(YEAR,@fechaFin);
	DECLARE @mesFin int = DATEPART(MONTH,@fechaFin);
	DECLARE @diaFin int = DATEPART(DAY,@fechaFin);

	WHILE (@anio < @anioFin OR (@anio = @anioFin AND @mes <= @mesFin)) BEGIN
	
		IF(@anio > @anioInicio OR @mes > @mesInicio OR @diaInicio < 16) BEGIN
			INSERT INTO @tblFechas
			SELECT
				CONCAT(@anio,'-',@mes,'-01'),
				CONCAT(@anio,'-',@mes,'-15'),
				COUNT(*)
			FROM [dbo].[fn_getPrenomina] (CONCAT(@anio,'-',@mes,'-01'), CONCAT(@anio,'-',@mes,'-15'))
            WHERE idSucursal = @sucursalId
		END

		IF(@anio < @anioFin OR @mes < @mesFin OR @diaFin > 15) BEGIN

			DECLARE @ultimoDiaMes date;

			IF(@mes = 12) BEGIN
				SELECT @ultimoDiaMes = DATEADD(DAY,-1,CONCAT(@anio+1,'-',1,'-01'))
			END ELSE BEGIN
				SELECT @ultimoDiaMes = DATEADD(DAY,-1,CONCAT(@anio,'-',@mes+1,'-01'))
			END

			INSERT INTO @tblFechas
			SELECT
				CONCAT(@anio,'-',@mes,'-16'),
				@ultimoDiaMes,
				COUNT(*)
			FROM [dbo].[fn_getPrenomina] (CONCAT(@anio,'-',@mes,'-16'), @ultimoDiaMes)
            WHERE idSucursal = @sucursalId
		END

		IF(@mes = 12) BEGIN
			SELECT @mes = 1
			SELECT @anio = @anio + 1
		END ELSE BEGIN
			SELECT @mes = @mes+1
		END
	END

    RETURN;
END
GO