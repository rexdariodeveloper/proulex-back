SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RptAvanceInscripciones]
AS
     SELECT sedeId,
		   sede,
		   CONVERT(INT, inscripciones) AS inscripciones,
		   ingresos,
		   CONVERT(INT, meta) AS meta,
		   CONVERT(DECIMAL, (inscripciones / meta * 100)) AS avance,
		   modalidadId,
		   fechaInicio
	FROM
	(
		SELECT DISTINCT
			   PROGRU_SUC_SucursalId AS sedeId,
			   SUC_Nombre AS sede,
			   CONVERT(DECIMAL(28, 10), COUNT(INS_InscripcionId) OVER(PARTITION BY PROGRU_PAC_ProgramacionAcademicaComercialId, PROGRU_SUC_SucursalId, PROGRU_PAMOD_ModalidadId, PROGRU_FechaInicio)) AS inscripciones,
			   SUM(Total) OVER(PARTITION BY PROGRU_PAC_ProgramacionAcademicaComercialId, PROGRU_SUC_SucursalId, PROGRU_PAMOD_ModalidadId, PROGRU_FechaInicio) AS ingresos,
			   CONVERT(DECIMAL(28, 10), PMD_Meta) AS meta,
			   PROGRU_PAMOD_ModalidadId AS modalidadId,
			   PROGRU_FechaInicio AS fechaInicio
		FROM ProgramasGrupos
			 INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
			 INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
									 AND PROG_JOBS IS NULL
									 AND PROG_JOBSSEMS IS NULL
									 AND PROG_PCP IS NULL
			 INNER JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
										 AND INS_CMM_EstatusId = 2000510 -- Pagada
			 INNER JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
			 INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
			 LEFT JOIN OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
			 CROSS APPLY dbo.fn_getMontosDetalleOV(OVD_OrdenVentaDetalleId)
			 INNER JOIN Sucursales ON PROGRU_SUC_SucursalId = SUC_SucursalId
			 INNER JOIN
			 (
				SELECT PM_PAC_ProgramacionAcademicaComercialId,
					   PMD_SUC_SucursalId,
					   PMD_PAMOD_ModalidadId,
					   PMD_FechaInicio,
					   PMD_Meta
				FROM ProgramasMetas
					 INNER JOIN ProgramasMetasDetalles ON PM_ProgramaMetaId = PMD_PM_ProgramaMetaId
				WHERE PM_Activo = 1
			 ) AS meta ON PROGRU_PAC_ProgramacionAcademicaComercialId = PM_PAC_ProgramacionAcademicaComercialId
						 AND PROGRU_SUC_SucursalId = PMD_SUC_SucursalId
						 AND PROGRU_PAMOD_ModalidadId = PMD_PAMOD_ModalidadId
						 AND PROGRU_FechaInicio = PMD_FechaInicio
		WHERE PROGRU_CMM_EstatusId = 2000620 -- Activo
			  AND (PROGRU_GrupoReferenciaId IS NULL OR OV_MPPV_MedioPagoPVId IS NOT NULL) -- No incluir las Proyecciones
			  AND OVCD_OrdenVentaCancelacionDetalleId IS NULL
	) AS todo
GO