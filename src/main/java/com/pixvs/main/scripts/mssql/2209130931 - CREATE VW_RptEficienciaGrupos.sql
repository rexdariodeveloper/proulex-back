SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RptEficienciaGrupos]
AS
     SELECT PROGRU_GrupoId AS grupoId,
			INS_InscripcionId AS inscripcionId,
			SUC_SucursalId AS sedeId,
			SUC_Nombre AS sede,
			PROGRU_Codigo AS grupo,
			PROGRU_FechaInicio AS fecha,
			CONCAT_WS(' - ', CAST(PAMODh_HoraInicio AS NVARCHAR(5)), CAST(PAMODh_HoraFin AS NVARCHAR(5))) AS horario,
			FORMAT(PROGRU_Nivel, '00') AS nivel,
			PROG_Codigo AS curso,
			PROGRU_PAMOD_ModalidadId AS modalidadId
	FROM ProgramasGrupos
			INNER JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
			INNER JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
									AND PROG_JOBS IS NULL
									AND PROG_JOBSSEMS IS NULL
									AND PROG_PCP IS NULL
			INNER JOIN Sucursales ON PROGRU_SUC_SucursalId = SUC_SucursalId
			INNER JOIN PAModalidadesHorarios ON PROGRU_PAMODh_PAModalidadHorarioId = PAMODh_PAModalidadHorarioId
			LEFT JOIN Inscripciones ON PROGRU_GrupoId = INS_PROGRU_GrupoId
			LEFT JOIN OrdenesVentaDetalles ON INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
			LEFT JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
	WHERE PROGRU_CMM_EstatusId = 2000620 -- Activo
			AND (INS_InscripcionId IS NULL OR INS_CMM_EstatusId = 2000510 /*Pagada*/ OR (INS_CMM_EstatusId = 2000511 AND OV_MPPV_MedioPagoPVId IS NOT NULL) /*Pendiente de pago*/)
			AND (PROGRU_GrupoReferenciaId IS NULL OR OV_OrdenVentaId IS NULL OR OV_MPPV_MedioPagoPVId IS NOT NULL) -- No incluir las Proyecciones
GO