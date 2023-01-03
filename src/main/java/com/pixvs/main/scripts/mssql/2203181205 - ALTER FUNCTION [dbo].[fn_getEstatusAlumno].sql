SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER FUNCTION [dbo].[fn_getEstatusAlumno](@alumnoId INT, @grupoId INT)
RETURNS INT
AS
BEGIN
	DECLARE @minutosClase DECIMAL(28,10)
	DECLARE @minutosFaltas DECIMAL(28,10) = 0.0
	DECLARE @faltasPermitidas DECIMAL(28,10) -- Porcentaje de faltas permitidas
	DECLARE @faltasDesertor DECIMAL(28,10) --Porcentaje de faltas para ser desertor
	DECLARE @calificacionMinima DECIMAL(28,10)
	DECLARE @ultimaClase DATE
	DECLARE @estatus INT = 2000670 -- Registrado

	-- Obtener del grupo
	select 
		@faltasPermitidas = PROGRU_FaltasPermitidas, 
		@calificacionMinima = PROGRU_CalificacionMinima 
	from 
		ProgramasGrupos where PROGRU_GrupoId = @grupoId
	SET @faltasDesertor = 26.32

	select 
		@minutosClase = SUM(horas * 60.0),
		@ultimaClase = MAX(fecha)
	from 
		fn_getDiasClaseGrupo(@grupoId)
		inner join VW_GRUPOS_HORARIOS on grupoId = @grupoId and DATEPART(WEEKDAY, fecha) = dia
	where
		fecha IS NOT NULL

	-- Si tiene asistencias registradas, cambia a 'Activo'
	IF( (select COUNT(*) FROM AlumnosAsistencias WHERE AA_PROGRU_GrupoID = @grupoId AND AA_ALU_AlumnoId = @alumnoId) > 0 )
	BEGIN
		SET @estatus = 2000671 -- Activo

		SELECT 
			@minutosFaltas = SUM(CASE AA_CMM_TipoAsistenciaId WHEN 2000551 THEN horas * 60.0 ELSE COALESCE(AA_MinutosRetardo, 0.0) END) 
		FROM 
			AlumnosAsistencias 
			inner join VW_GRUPOS_HORARIOS on AA_PROGRU_GrupoId = grupoId and DATEPART(WEEKDAY, AA_Fecha) = dia
		WHERE 
			AA_ALU_AlumnoId = @alumnoId 
			AND AA_PROGRU_GrupoId = @grupoId 
			AND AA_CMM_TipoAsistenciaId IN (2000551, 2000553)

		-- Si el alumno tiene mas del 66.66...% de faltas permitidas, cambia a 'En riesgo'
		IF @minutosFaltas > ((@minutosClase * (@faltasPermitidas / 100.0)) * 0.666666)
		BEGIN
			SET @estatus = 2000672 -- En riesgo
		END

		-- Si el alumno tiene mas del 100% de faltas permitidas, cambia a 'Sin derecho'
		IF @minutosFaltas > (@minutosClase * (@faltasPermitidas / 100.0))
		BEGIN
			SET @estatus = 2000673 -- Sin derecho
		END

		-- Si ya se ha registrado la ultima clase
		IF (SELECT COUNT(*) FROM AlumnosAsistencias WHERE AA_ALU_AlumnoId = @alumnoId AND AA_PROGRU_GrupoId = @grupoId AND CAST(AA_Fecha AS DATE) = CAST(@ultimaClase AS DATE) ) > 0
		BEGIN
			-- Evaluar para desertor: Cuando tienen falta en las ultimas n horas, redondeando a clases
			DECLARE @minutosDesertor DECIMAL(28,10) = @minutosClase * (@faltasDesertor / 100.0)
			DECLARE @ultimos INT;
			DECLARE @total INT;
			-- Obtenemos la cantidad de clases hasta llegar a los minutos requeridos
			select @ultimos = COUNT(fecha), @total = MAX(total) from
			(
				select 
					fecha, 
					SUM(horas * 60.0) OVER (ORDER BY fecha desc) - (horas * 60.0) total 
				from 
					fn_getDiasClaseGrupo(@grupoId) 
					inner join VW_GRUPOS_HORARIOS on grupoId = @grupoId and DATEPART(WEEKDAY, fecha) = dia
			) t
			where total <= @minutosDesertor

			-- Si nos han sobrado minutos, añadimos una clase mas
			IF @total < @minutosDesertor
				SET @ultimos = @ultimos + 1

			-- Contamos de las ultimas n clases, cuantas han sido faltas
			DECLARE @faltas INT
			select
				@faltas = SUM(CASE AA_CMM_TipoAsistenciaId WHEN 2000551 THEN 1 ELSE 0 END)
			from (
				select 
					TOP(@ultimos) *
				from 
					AlumnosAsistencias where AA_PROGRU_GrupoId = @grupoId and AA_ALU_AlumnoId = @alumnoId
			) t
			-- Si todas las clases han sido falta, el estatus es 'Desertor'
			IF(@faltas = @ultimos)
				SET @estatus = 2000674 -- Desertor
		END

	END
	RETURN @estatus
END