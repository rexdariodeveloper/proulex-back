ALTER TABLE PAModalidades
ADD [PAMOD_DiasFinPeriodoInscripcionBeca][INT] NOT NULL DEFAULT(8)
GO

ALTER TABLE [ProgramasGrupos]
ADD [PROGRU_FechaFinInscripciones][DATE] NULL
GO

ALTER TABLE [ProgramasGrupos]
ADD [PROGRU_FechaFinInscripcionesBecas][DATE] NULL
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER FUNCTION [dbo].[fn_getFechaPorModalidad](@fechaInicio Date,@dias int,@domingo bit, @lunes bit, @martes bit, @miercoles bit, @jueves bit, @viernes bit, @sabado bit)
RETURNS @fechasValidas table(
	id int,
	fecha Date
)

AS BEGIN 
	DECLARE @fecha as Date;
	DECLARE @dow as int;
	DECLARE @ff as Date;
	DECLARE @id as int;
	DECLARE @counter as int;
	SET @fecha = @fechaInicio
	SET @id = 1
	SET @counter = 0;
	--INSERT @fechasValidas
	WHILE @counter <= @dias BEGIN
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
		SET @counter =  CASE
					WHEN @dow = 1 and @domingo = 1 then @counter + 1
					WHEN @dow = 2 and @lunes = 1 then @counter + 1
					WHEN @dow = 3 and @martes = 1 then @counter + 1
					WHEN @dow = 4 and @miercoles = 1 then @counter + 1
					WHEN @dow = 5 and @jueves = 1 then @counter + 1
					WHEN @dow = 6 and @viernes = 1 then @counter + 1
					WHEN @dow = 7 and @sabado = 1 then @counter + 1
					ELSE @counter
					END
	END

RETURN;
END
GO

UPDATE PA
SET PROGRU_FechaFinInscripciones=(Select top 1 fecha order by fecha desc)
FROM ProgramasGrupos PA
INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaFin,PAMOD_DiasFinPeriodoInscripcion,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) wh
WHERE PROGRU_CMM_EstatusId=2000620 AND PROGRU_Activo=1
GO

UPDATE PA
SET PROGRU_FechaFinInscripcionesBecas=(Select top 1 fecha order by fecha desc)
FROM ProgramasGrupos PA
INNER JOIN PAModalidades on PAMOD_ModalidadId = PROGRU_PAMOD_ModalidadId
OUTER APPLY dbo.fn_getFechaPorModalidad(PROGRU_FechaFin,PAMOD_DiasFinPeriodoInscripcionBeca,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado) wh
WHERE PROGRU_CMM_EstatusId=2000620 AND PROGRU_Activo=1
GO
