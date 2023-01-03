CREATE OR ALTER VIEW [dbo].[VW_GRUPOS_PROFESORES_LISTADO]
AS
	SELECT
		sucursalCodigo, sucursalNombre, programaCodigo, programaNombre, nivel, codigoGrupo, grupo, 
		cupo, COUNT(alumnoId) ocupacion, fechaInicio, fechaFin, idioma, modalidadCodigo, modalidadNombre, 
		color, programacionCodigo, programacionNombre, horario, profesor, suplente, sucursalId, 
		programaId, grupoId, modalidadId, idiomaId, profesorId
	FROM
	(
	SELECT DISTINCT
		codigoGrupo, sucursalCodigo, sucursalNombre, programaCodigo, programaNombre, nivel, grupo, cupo, fechaInicio, fechaFin, 
		idioma, modalidadCodigo, modalidadNombre, color, programacionCodigo, programacionNombre, horario, profesor, suplente, 
		alumno, sucursalId, programaId, grupoId, modalidadId, idiomaId, profesorId, alumnoId
	FROM
	(
	SELECT
		*
	FROM
		[dbo].[VW_GRUPOS_ALUMNOS_ACTIVIDADES]
	) t1
	GROUP BY
		codigoGrupo, sucursalCodigo, sucursalNombre, programaCodigo, programaNombre, nivel, grupo, cupo, 
		fechaInicio, fechaFin, idioma, modalidadCodigo, modalidadNombre, color, programacionCodigo, 
		programacionNombre, horario, profesor, suplente, alumno, sucursalId, programaId, grupoId, modalidadId, 
		idiomaId, profesorId, alumnoId
	) t2
	GROUP BY
		codigoGrupo, sucursalCodigo, sucursalNombre, programaCodigo, programaNombre, nivel, grupo, cupo, 
		fechaInicio, fechaFin, idioma, modalidadCodigo, modalidadNombre, color, programacionCodigo, 
		programacionNombre, horario, profesor, suplente, sucursalId, programaId, grupoId, modalidadId, 
		idiomaId, profesorId
GO