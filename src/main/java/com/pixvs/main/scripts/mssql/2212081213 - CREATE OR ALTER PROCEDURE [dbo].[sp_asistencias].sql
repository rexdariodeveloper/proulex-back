SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE [dbo].[sp_asistencias] @asistencias NVARCHAR(MAX), @creadoPorId INT
AS
BEGIN
	DECLARE @feedBack TABLE (estudiante varchar(100), grupo varchar(100), mensaje varchar(2000))
	DECLARE @alumnosGruposIds TABLE (alumnoId INT UNIQUE, grupoId INT);
	/* Nuevos registros */
	INSERT INTO [dbo].[AlumnosAsistencias]
		(AA_ALU_AlumnoId, AA_PROGRU_GrupoId, AA_Fecha, AA_CMM_TipoAsistenciaId, AA_Comentario, AA_MinutosRetardo, AA_MotivoJustificante, AA_FechaCreacion, AA_USU_CreadoPorId)
	OUTPUT INSERTED.AA_ALU_AlumnoId, INSERTED.AA_PROGRU_GrupoId INTO @alumnosGruposIds(alumnoId, grupoId)
	SELECT
		alumnoId, grupoId, CAST(fecha AS DATE), tipoAsistenciaId, comentario, minutosRetardo, motivoJustificante, GETDATE(), @creadoPorId
	FROM OPENJSON(@asistencias)  
		WITH (
			id INT 'strict $.id',
			alumnoId INT 'strict $.alumnoId',
			grupoId INT 'strict $.grupoId',
			fecha NVARCHAR(50) 'strict $.fecha',
			tipoAsistenciaId INT 'strict $.tipoAsistenciaId',
			comentario NVARCHAR(280) '$.comentario',
			minutosRetardo INT '$.minutosRetardo',
			motivoJustificante NVARCHAR(280) '$.motivoJustificante'
		)
	WHERE
		id IS NULL
	/* Registros editados */
	UPDATE [dbo].[AlumnosAsistencias]
		SET AA_CMM_TipoAsistenciaId = tipoAsistenciaId, 
			AA_Comentario = comentario, 
			AA_MinutosRetardo = minutosRetardo, 
			AA_MotivoJustificante = motivoJustificante,
			AA_FechaModificacion = GETDATE(),
			AA_USU_ModificadoPorId = @creadoPorId
	OUTPUT INSERTED.AA_ALU_AlumnoId, INSERTED.AA_PROGRU_GrupoId INTO @alumnosGruposIds(alumnoId, grupoId)
	FROM OPENJSON(@asistencias)
		WITH (
			id INT 'strict $.id',
			alumnoId INT 'strict $.alumnoId',
			grupoId INT 'strict $.grupoId',
			fecha NVARCHAR(50) 'strict $.fecha',
			tipoAsistenciaId INT 'strict $.tipoAsistenciaId',
			comentario NVARCHAR(280) '$.comentario',
			minutosRetardo INT '$.minutosRetardo',
			motivoJustificante NVARCHAR(280) '$.motivoJustificante'
		)
	INNER JOIN [dbo].[AlumnosAsistencias] ON AA_AlumnoAsistenciaId = id
	/* Recuperar el resumen de asistencias, el estatus actual y el nuevo */
	DECLARE @alumnosGrupos TABLE (id INT UNIQUE, faltas INT, asistencias INT, retardos INT, estatusActual INT, estatusNuevo INT);
	INSERT INTO @alumnosGrupos
	SELECT 
		ALUG_AlumnoGrupoId,
		faltas,
		asistencias,
		minRetardos,
		ALUG_CMM_EstatusId,
		[dbo].[fn_getEstatusAlumno](alumnoId, grupoId)
	FROM @alumnosGruposIds
	INNER JOIN (
		SELECT 
			AA_ALU_AlumnoId a_alumnoId, 
			AA_PROGRU_GrupoId a_grupoId,
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000550 OR AA_CMM_TipoAsistenciaId = 2000552 THEN 1 ELSE 0 END) asistencias,
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000551 THEN 1 ELSE 0 END) faltas,
			SUM(CASE WHEN AA_CMM_TipoAsistenciaId = 2000553 THEN AA_MinutosRetardo ELSE 0 END) minRetardos
		FROM [dbo].[AlumnosAsistencias] GROUP BY AA_ALU_AlumnoId, AA_PROGRU_GrupoId
	) asistencias ON alumnoId = a_alumnoId AND grupoId = a_grupoId
	LEFT JOIN [dbo].[AlumnosGrupos] ON alumnoId = ALUG_ALU_AlumnoId AND grupoId = ALUG_PROGRU_GrupoId
	/* TODO: Agregar la validación de proyecciones */
	/* Actualizar los datos del alumno */
	UPDATE [dbo].[AlumnosGrupos]
	SET
		ALUG_Faltas = faltas,
		ALUG_Asistencias = asistencias,
		ALUG_MinutosRetardo = retardos,
		ALUG_CMM_EstatusId = estatusNuevo
	FROM @alumnosGrupos
	INNER JOIN [dbo].[AlumnosGrupos] ON id = ALUG_AlumnoGrupoId
	/* Verificar si hay mensajes de feedback */
	IF (SELECT COUNT(*) FROM @feedBack) = 0
		INSERT INTO @feedBack VALUES (NULL,NULL,N'Ok');
	SELECT * FROM @feedBack;
END
GO