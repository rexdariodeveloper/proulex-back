SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getDiasClaseGrupo](@grupoId INT)
RETURNS @fechasValidas TABLE(
	id INT,
	fecha DATE
)
AS BEGIN
	DECLARE @fechaInicio DATE;
	DECLARE @fechaFin DATE;
	DECLARE @domingo BIT;
	DECLARE @lunes BIT;
	DECLARE @martes BIT;
	DECLARE @miercoles BIT;
	DECLARE @jueves BIT;
	DECLARE @viernes BIT;
	DECLARE @sabado BIT;
	
	select
		 @fechaInicio = MAX(fechaInicio),
		 @fechaFin = MAX(fechaFin),
		 @domingo = CAST(MAX(d) AS bit),
		 @lunes = CAST(MAX(l) AS bit),
		 @martes = CAST(MAX(m) AS bit),
		 @miercoles = CAST(MAX(mi) AS bit),
		 @jueves = CAST(MAX(j) AS bit),
		 @viernes = CAST(MAX(v) AS bit),
		 @sabado = CAST(MAX(s) AS bit) 
	from
	(
	select
			grupoId,
			PROGRU_FechaInicio fechaInicio,
			PROGRU_FechaFin fechaFin,
			[1] d,
			[2] l,
			[3] m,
			[4] mi,
			[5] j,
			[6] v,
			[7] s
		from
		(
			SELECT * FROM [dbo].[VW_GRUPOS_HORARIOS] INNER JOIN ProgramasGrupos ON grupoId = PROGRU_GrupoId
		) t
		PIVOT(
			COUNT(dia) FOR dia IN ([1],[2],[3],[4],[5],[6],[7])
		) AS pv
		WHERE
			grupoId = @grupoId
	) r group by
		grupoId
	
	INSERT INTO @fechasValidas SELECT * FROM fn_getDiaHabiles(@fechaInicio, @fechaFin, @domingo, @lunes, @martes, @miercoles, @jueves, @viernes, @sabado);
	RETURN;
	END
GO