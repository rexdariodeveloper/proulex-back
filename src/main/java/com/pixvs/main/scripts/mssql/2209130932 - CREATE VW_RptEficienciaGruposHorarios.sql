SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER VIEW [dbo].[VW_RptEficienciaGruposHorarios]
AS
     SELECT ROW_NUMBER() OVER (ORDER BY horas, horario) AS Id, Horario, Columna
	 FROM 
	 (
		SELECT DISTINCT
			   CONCAT_WS(' - ', CAST(PAMODh_HoraInicio AS NVARCHAR(5)), CAST(PAMODh_HoraFin AS NVARCHAR(5))) AS horario,
			   DATEDIFF (HOUR , PAMODh_HoraInicio, PAMODh_HoraFin) AS horas,
			   REPLACE('h_' + CONCAT_WS('_a_', CAST(PAMODh_HoraInicio AS NVARCHAR(5)), CAST(PAMODh_HoraFin AS NVARCHAR(5))), ':', '_') AS columna
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
	 ) AS todo
GO