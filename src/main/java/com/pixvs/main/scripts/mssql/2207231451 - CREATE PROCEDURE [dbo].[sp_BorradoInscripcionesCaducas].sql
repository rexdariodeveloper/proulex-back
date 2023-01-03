CREATE OR ALTER PROCEDURE [dbo].[sp_BorradoInscripcionesCaducas]
AS
BEGIN
	DROP TABLE IF EXISTS #TMP_ProyeccionesCaducas;

	CREATE TABLE #TMP_ProyeccionesCaducas(
		inscripcionId INT,
		ovdId INT,
		ovId INT
	);

	INSERT INTO #TMP_ProyeccionesCaducas
	SELECT INS_InscripcionId, OVD_OrdenVentaDetalleId, OV_OrdenVentaId FROM
	Inscripciones
	INNER JOIN ProgramasGrupos ON PROGRU_GrupoId = INS_PROGRU_GrupoId
	INNER JOIN OrdenesVentaDetalles ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
	INNER JOIN OrdenesVenta ON OV_OrdenVentaId = OVD_OV_OrdenVentaId
	WHERE
		PROGRU_FechaFinInscripcionesBecas < GETDATE()
		AND PROGRU_GrupoReferenciaId IS NOT NULL
		AND INS_CMM_EstatusId = 2000511
		AND OV_MPPV_MedioPagoPVId IS NULL
		AND PROGRU_SUC_SucursalId NOT IN (38, 39);

	/* Borrado de Asistencias */
	DELETE AlumnosAsistencias WHERE AA_AlumnoAsistenciaId IN (
		SELECT AA_AlumnoAsistenciaId FROM AlumnosAsistencias 
		INNER JOIN Inscripciones ON AA_ALU_AlumnoId = INS_ALU_AlumnoId AND AA_PROGRU_GrupoId = INS_PROGRU_GrupoId
		INNER JOIN #TMP_ProyeccionesCaducas ON INS_InscripcionId = inscripcionId
	);
	/* Borrado de Calificaciones */
	DELETE AlumnosExamenesCalificaciones WHERE AEC_AlumnoExamenCalificacionId IN (
		SELECT AEC_AlumnoExamenCalificacionId FROM AlumnosExamenesCalificaciones 
		INNER JOIN Inscripciones ON AEC_ALU_AlumnoId = INS_ALU_AlumnoId AND AEC_PROGRU_GrupoId = INS_PROGRU_GrupoId
		INNER JOIN #TMP_ProyeccionesCaducas ON INS_InscripcionId = inscripcionId
	);
	/* Borrado de relaciones alumno-grupo */
	DELETE AlumnosGrupos WHERE ALUG_INS_InscripcionId IN (SELECT inscripcionId FROM #TMP_ProyeccionesCaducas);
	/* Borrado de historial de grupo */
	DELETE ProgramasGruposHistorial WHERE PROGRUH_INS_InscripcionId IN (SELECT inscripcionId FROM #TMP_ProyeccionesCaducas);
	/* Borrado de inscripciones */
	DELETE Inscripciones WHERE INS_InscripcionId IN (SELECT inscripcionId FROM #TMP_ProyeccionesCaducas);
	/* Borrado de detalles dependiente */
	DELETE OrdenesVentaDetalles WHERE OVD_OVD_DetallePadreId IN (SELECT ovdId FROM #TMP_ProyeccionesCaducas);
	/* Borrado de detalles independientes */
	DELETE OrdenesVentaDetalles WHERE OVD_OrdenVentaDetalleId IN (SELECT ovdId FROM #TMP_ProyeccionesCaducas);
	/* Borrado de ordenes de venta*/
	DELETE OrdenesVenta WHERE OV_OrdenVentaId IN (SELECT ovId FROM #TMP_ProyeccionesCaducas);
END