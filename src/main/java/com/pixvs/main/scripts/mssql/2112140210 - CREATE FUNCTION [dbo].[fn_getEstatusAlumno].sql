CREATE OR ALTER FUNCTION fn_getEstatusAlumno(@alumnoId INT, @grupoId INT)
RETURNS INT
AS
BEGIN
	
	DECLARE @fechaInicio DATE;
	DECLARE @jobs BIT;
	DECLARE @sems BIT;
	DECLARE @pagada BIT;
	DECLARE @estatusId INT;
	DECLARE @dias INT;
	DECLARE @diasClase INT;
	DECLARE @faltasPermitidas INT;
	DECLARE @horasDia DECIMAL(10,2);
	
	/* TODO: Reemplazar por una consulta a cmms */
	IF (@sems = 1) 
		SET @dias = 10;
	IF (@jobs = 1)
		SET @dias = 15;

	SELECT 
		@jobs = PROG_JOBS, 
		@sems = PROG_JOBSSEMS, 
		@fechaInicio = PROGRU_FechaInicio, 
		@pagada = CASE WHEN INS_CMM_EstatusId = 2000510 THEN 1 ELSE 0 END,
		@diasClase = (SELECT COUNT(*) FROM [dbo].[fn_getDiaHabiles](PROGRU_fechaInicio,PROGRU_fechaFin,PAMOD_Domingo,PAMOD_Lunes,PAMOD_Martes,PAMOD_Miercoles,PAMOD_Jueves,PAMOD_Viernes,PAMOD_Sabado)),
		@faltasPermitidas = PROGRU_FaltasPermitidas,
		@horasDia = PAMOD_HorasPorDia
	FROM ProgramasGrupos
	INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
	INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
	INNER JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
	LEFT JOIN Inscripciones ON INS_PROGRU_GrupoId = PROGRU_GrupoId AND INS_ALU_AlumnoId = @alumnoId
	WHERE PROGRU_GrupoId = @grupoId;

	SELECT TOP 1 
		@estatusId = CASE WHEN @jobs = 1 THEN CASE WHEN @pagada = 1 THEN 2000672 ELSE 2000678 END
						  WHEN @sems = 1 THEN CASE WHEN fechaInicio = @fechaInicio THEN 2000671 ELSE 2000672 END END
	FROM (
		SELECT 
			alumnoId, grupoId, tipoAsistenciaId, MIN(fecha) fechaInicio, MAX(fecha) fechaFin, COUNT(tipoAsistenciaId) faltas
		FROM
		(
			SELECT AA_ALU_AlumnoId alumnoId, AA_PROGRU_GrupoId grupoId, CAST(AA_Fecha as DATE) fecha, AA_CMM_TipoAsistenciaId tipoAsistenciaId,
				 row_number() over (partition by AA_ALU_AlumnoId order by AA_Fecha) as seq_a,
				 row_number() over (partition by AA_ALU_AlumnoId, AA_CMM_TipoAsistenciaId order by AA_Fecha) as seq_ata
      		FROM AlumnosAsistencias
			WHERE AA_ALU_AlumnoId = @alumnoId AND AA_PROGRU_GrupoId = @grupoId
		) a
		WHERE tipoAsistenciaId = 2000551
	GROUP BY alumnoId, grupoId, tipoAsistenciaId, (seq_a - seq_ata)
	)g
	WHERE g.faltas >= 10
	ORDER BY fechaInicio;

	IF @estatusId IS NULL
	BEGIN
		SELECT 
			@estatusId = CASE WHEN CAST((faltas + (retardos / 60) / @horasDia) AS INT) > @faltasPermitidas THEN 2000673
							  WHEN CAST((faltas + (retardos / 60) / @horasDia) AS INT) > (@faltasPermitidas * 0.6) THEN 2000674 END
		FROM
		(
		SELECT 
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000551 THEN 1 ELSE 0 END) faltas,
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000553 THEN AA_MinutosRetardo ELSE 0 END) retardos
		FROM AlumnosAsistencias
		WHERE AA_ALU_AlumnoId = @alumnoId AND AA_PROGRU_GrupoId = @grupoId
		GROUP BY AA_ALU_AlumnoId, AA_PROGRU_GrupoId
		) t
	END

	/* TODO: Exclude status Baja, Aprobado y Reprobado */

	IF @estatusId IS NULL
		SET @estatusId = 2000670

	RETURN @estatusId;
END
GO