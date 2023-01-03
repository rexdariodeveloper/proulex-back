SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RPT_ALUMNOS]
AS
		SELECT INS_InscripcionId AS inscripcionId,
			   ALU_AlumnoId AS alumnoId,
			   ALU_Codigo AS alumnoCodigo,
			   ALU_Nombre+' '+ALU_PrimerApellido+ISNULL(' '+ALU_SegundoApellido, '') AS alumno,
			   sedeInscripcion.SUC_SucursalId AS sedeInscripcionId,
			   sedeInscripcion.SUC_Nombre AS sedeInscripcion,
			   ISNULL(SP_SucursalPlantelId, -1) AS plantelInscripcionId,
			   OV_Codigo AS notaVenta,
			   sedeGrupo.SUC_SucursalId AS sedeGrupoId,
			   sedeGrupo.SUC_Nombre AS sedeGrupo,
			   PROGRU_GrupoId AS grupoId,
			   PROGRU_Codigo AS grupo,
			   ISNULL(PROGRU_SP_SucursalPlantelId, -1) AS plantelGrupoId,
			   CAST(PROGRU_FechaInicio AS DATE) AS fechaInicio,
			   CAST(PROGRU_FechaFin AS DATE) AS fechaFin,
			   estatusAlumno.CMM_Valor AS estatusAlumno,
			   PROGRU_PAMOD_ModalidadId AS modalidadId
		FROM Inscripciones
			 INNER JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId AND PROGRU_CMM_EstatusId != 2000622 -- CMM_PROGRU_Estatus no Cancelado
			 INNER JOIN Sucursales AS sedeGrupo ON PROGRU_SUC_SucursalId = sedeGrupo.SUC_SucursalId
			 INNER JOIN Alumnos ON INS_ALU_AlumnoId = ALU_AlumnoId
			 INNER JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
			 INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
			 INNER JOIN Sucursales AS sedeInscripcion ON OV_SUC_SucursalId = sedeInscripcion.SUC_SucursalId
			 LEFT JOIN AlumnosGrupos AS ag1 ON ALU_AlumnoId = ag1.ALUG_ALU_AlumnoId AND PROGRU_GrupoId = ag1.ALUG_PROGRU_GrupoId
			 LEFT JOIN AlumnosGrupos AS ag2 ON INS_InscripcionId = ag2.ALUG_INS_InscripcionId
			 INNER JOIN ControlesMaestrosMultiples AS estatusAlumno ON ISNULL(ag1.ALUG_CMM_EstatusId, ag2.ALUG_CMM_EstatusId) = estatusAlumno.CMM_ControlId
			 LEFT JOIN ControlesMaestrosMultiples AS cmmPlantel ON INS_CMM_InstitucionAcademicaId = cmmPlantel.CMM_ControlId
			 LEFT JOIN SucursalesPlanteles ON cmmPlantel.CMM_Referencia = SP_Codigo
		WHERE INS_CMM_EstatusId NOT IN (2000513, 2000512) -- CMM_INS_Estatus ni Baja ni Cancelada
			 AND (PROGRU_GrupoReferenciaId IS NULL OR OV_MPPV_MedioPagoPVId IS NOT NULL) -- No incluir las Proyecciones
GO