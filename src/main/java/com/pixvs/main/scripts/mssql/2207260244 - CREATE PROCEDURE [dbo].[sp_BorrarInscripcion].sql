SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER PROCEDURE [dbo].[sp_BorrarInscripcion](@alumnoId INT, @grupoId INT)
AS
BEGIN
	DROP TABLE IF EXISTS #TMP_RegistrosBorrar;
	CREATE TABLE #TMP_RegistrosBorrar(
		inscripcionId INT,
		ovdId INT,
		ovId INT
	);
	INSERT INTO #TMP_RegistrosBorrar
	SELECT INS_InscripcionId, OVD_OrdenVentaDetalleId, OV_OrdenVentaId FROM
	Inscripciones
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
	INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
	WHERE
		INS_CMM_EstatusId = 2000511 -- Pendiente de pago
		AND OV_MPPV_MedioPagoPVId IS NULL
		AND INS_ALU_AlumnoId = @alumnoId
		AND INS_PROGRU_GrupoId = @grupoId;
	/* Borrado de Asistencias */
	DELETE AlumnosAsistencias WHERE AA_AlumnoAsistenciaId IN (
		SELECT AA_AlumnoAsistenciaId FROM AlumnosAsistencias 
		INNER JOIN Inscripciones ON AA_ALU_AlumnoId = INS_ALU_AlumnoId AND AA_PROGRU_GrupoId = INS_PROGRU_GrupoId
		INNER JOIN #TMP_RegistrosBorrar ON INS_InscripcionId = inscripcionId
	);
	/* Borrado de Calificaciones */
	DELETE AlumnosExamenesCalificaciones WHERE AEC_AlumnoExamenCalificacionId IN (
		SELECT AEC_AlumnoExamenCalificacionId FROM AlumnosExamenesCalificaciones 
		INNER JOIN Inscripciones ON AEC_ALU_AlumnoId = INS_ALU_AlumnoId AND AEC_PROGRU_GrupoId = INS_PROGRU_GrupoId
		INNER JOIN #TMP_RegistrosBorrar ON INS_InscripcionId = inscripcionId
	);
	/* Borrado de relaciones alumno-grupo */
	DELETE AlumnosGrupos WHERE ALUG_INS_InscripcionId IN (SELECT inscripcionId FROM #TMP_RegistrosBorrar);
	/* Borrado de historial de grupo */
	DELETE ProgramasGruposHistorial WHERE PROGRUH_INS_InscripcionId IN (SELECT inscripcionId FROM #TMP_RegistrosBorrar);
	/* Borrado de inscripciones */
	DELETE Inscripciones WHERE INS_InscripcionId IN (SELECT inscripcionId FROM #TMP_RegistrosBorrar);
	/* Borrado de detalles dependiente */
	DELETE OrdenesVentaDetalles WHERE OVD_OVD_DetallePadreId IN (SELECT ovdId FROM #TMP_RegistrosBorrar);
	/* Borrado de detalles independientes */
	DELETE OrdenesVentaDetalles WHERE OVD_OrdenVentaDetalleId IN (SELECT ovdId FROM #TMP_RegistrosBorrar);
	/* Borrado de ordenes de venta*/
	DELETE OrdenesVenta WHERE OV_OrdenVentaId IN (SELECT ovId FROM #TMP_RegistrosBorrar);
END