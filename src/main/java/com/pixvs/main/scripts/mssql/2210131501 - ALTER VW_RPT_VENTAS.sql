SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ==============================================
-- Author:		Javier Elías
-- Create date: 19/07/2022
-- Modified date: 13/10/2022
-- Description:	Vista para obtener el reporte de Ventas
-- ==============================================
CREATE OR ALTER VIEW [dbo].[VW_RPT_VENTAS]
AS
	SELECT OVD_OrdenVentaDetalleId AS detalleId,
		   OV_OrdenVentaId AS ordenVentaId,
		   OV_Codigo AS notaVenta,
		   CAST(OV_FechaCreacion AS DATE) AS fechaOV,
		   sedeOV.SUC_SucursalId AS sucursalId,
		   sedeOV.SUC_Nombre AS sede,
		   INS_InscripcionId AS inscripcionId,
		   INS_Codigo AS inscripcion,
		   CAST(INS_FechaCreacion AS DATE) AS fechaInscripcion,
		   ART_NombreArticulo AS nombreArticulo,
		   ALU_AlumnoId AS alumnoId,
		   ALU_Codigo AS codigoAlumno,
		   ALU_Nombre + ' ' + ALU_PrimerApellido + ISNULL(' ' + ALU_SegundoApellido, '') AS nombre,
		   PROGRU_GrupoId AS grupoId,
		   PROGRU_Codigo AS codigoGrupo,
		   sedeGrupo.SUC_Nombre AS grupoSucursal,
		   PROG_Codigo + ' - ' + PROG_Nombre AS curso,
		   PAMOD_Codigo + ' - ' + PAMOD_Nombre AS modalidad,
		   FORMAT(PROGRU_Nivel, '00') AS nivel,
		   idioma.CMM_Valor AS idioma,
		   CONCAT_WS(' - ', CAST(PAMODH_HoraInicio AS NVARCHAR(5)), CAST(PAMODH_HoraFin AS NVARCHAR(5))) AS horario,
		   CAST(PROGRU_FechaInicio AS DATE) AS fechaInicio,
		   CAST(PROGRU_FechaFin AS DATE) AS fechaFin,
		   CONVERT(MONEY, montos.Subtotal) AS subTotal,
		   CONVERT(MONEY, montos.Descuento) AS descuento,
		   CONVERT(MONEY, ROUND(montos.Impuestos, 2)) AS impuestos,
		   CONVERT(MONEY, montos.Total) AS total,
		   BECU_Descuento * 100 AS porcentajeBeca,
		   CASE WHEN BECU_CMM_TipoId = 2000580 OR BECU_CMM_TipoId = 2000581 THEN 'Beca Sindicato' 
				WHEN BECU_CMM_TipoId = 2000582 THEN 'Beca Proulex' 
				WHEN BECU_CMM_TipoId IS NULL AND montos.Descuento IS NOT NULL AND montos.Descuento > 0 THEN 'Descuento Comercial' END AS razonDescuento,
		   OV_LigaCentroPagos AS ligaCentrosPagos,
		   estatusOV.CMM_Valor AS estatusOV,
		   NULL AS cancelacionId,
		   CASE WHEN INS_InscripcionId IS NOT NULL AND INSSG_InscripcionId IS NOT NULL THEN 'CURSO APLICADO' ELSE CASE WHEN INSSG_InscripcionId IS NOT NULL OR INSSG_InscripcionId IS NOT NULL THEN 'CURSO GUARDADO' END END AS estatusInscripcion,
		   CASE WHEN INS_EntregaLibrosPendiente IS NULL AND INSSG_EntregaLibrosPendiente IS NULL THEN NULL ELSE CASE WHEN ISNULL(INS_EntregaLibrosPendiente, INSSG_EntregaLibrosPendiente) = 0 THEN 'SI' ELSE 'NO' END END AS entregaLibro
	FROM OrdenesVenta
		 INNER JOIN OrdenesVentaDetalles ON OV_OrdenVentaId = OVD_OV_OrdenVentaId AND OVD_OVD_DetallePadreId IS NULL
		 CROSS APPLY fn_getMontosDetalleOV(OVD_OrdenVentaDetalleId) AS montos
		 INNER JOIN ControlesMaestrosMultiples AS estatusOV ON OV_CMM_EstatusId = estatusOV.CMM_ControlId
		 INNER JOIN Sucursales AS sedeOV ON OV_SUC_SucursalId = sedeOV.SUC_SucursalId
		 INNER JOIN Articulos ON ART_ArticuloId = OVD_ART_ArticuloId
		 OUTER APPLY
		 (
				SELECT TOP 1 * FROM Inscripciones WHERE INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId ORDER BY ISNULL(INS_FechaModificacion, INS_FechaCreacion) DESC
		 ) AS inscripcion
		 OUTER APPLY
		 (
				SELECT TOP 1 * FROM InscripcionesSinGrupo WHERE INSSG_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId ORDER BY ISNULL(INSSG_FechaModificacion, INSSG_FechaCreacion) DESC
		 ) AS inscripcionSinGrupo
		 LEFT JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
		 LEFT JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
		 LEFT JOIN ControlesMaestrosMultiples AS idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
		 LEFT JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
		 LEFT JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
		 LEFT JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
		 LEFT JOIN BecasUDG ON INS_BECU_BecaId = BECU_BecaId
		 LEFT JOIN Sucursales AS sedeGrupo ON PROGRU_SUC_SucursalId = sedeGrupo.SUC_SucursalId
		 LEFT JOIN AlumnosExamenesCertificaciones ON OVD_OrdenVentaDetalleId = ALUEC_OVD_OrdenVentaDetalleId AND ALUEC_ART_ArticuloId = ART_ArticuloId
		 LEFT JOIN AlumnosConstanciasTutorias ON OVD_OrdenVentaDetalleId = ALUCT_OVD_OrdenVentaDetalleId AND ALUCT_ART_ArticuloId = ART_ArticuloId
		 LEFT JOIN Alumnos ON ISNULL(ISNULL(INS_ALU_AlumnoId, ISNULL(INSSG_ALU_AlumnoId, ALUEC_ALU_AlumnoId)), ALUCT_ALU_AlumnoId) = ALU_AlumnoId
		 LEFT JOIN OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
	WHERE LEN(OV_Codigo) != 36
		AND OV_MPPV_MedioPagoPVId IS NOT NULL
		AND OVCD_OrdenVentaCancelacionDetalleId IS NULL
		AND (INS_InscripcionId IS NULL OR INS_CMM_EstatusId != 2000512)	-- CMM_INS_Estatus Cancelada
		AND (INSSG_InscripcionId IS NULL OR INSSG_CMM_EstatusId != 2000543)	-- CMM_INSSG_Estatus Cancelada

	UNION ALL

	SELECT OVD_OrdenVentaDetalleId AS detalleId,
			OV_OrdenVentaId AS ordenVentaId,
			OVC_Codigo AS notaVenta,
			CAST(OVC_FechaCreacion AS DATE) AS fechaOV,
			sedeOV.SUC_SucursalId AS sucursalId,
			sedeOV.SUC_Nombre AS sede,
			INS_InscripcionId AS inscripcionId,
			INS_Codigo AS inscripcion,
			CAST(INS_FechaCreacion AS DATE) AS fechaInscripcion,
			ART_NombreArticulo AS nombreArticulo,
			ALU_AlumnoId AS alumnoId,
			ALU_Codigo AS codigoAlumno,
			ALU_Nombre + ' ' + ALU_PrimerApellido + ISNULL(' ' + ALU_SegundoApellido, '') AS nombre,
			PROGRU_GrupoId AS grupoId,
			PROGRU_Codigo AS codigoGrupo,
			sedeGrupo.SUC_Nombre AS grupoSucursal,
			PROG_Codigo + ' - ' + PROG_Nombre AS curso,
			PAMOD_Codigo + ' - ' + PAMOD_Nombre AS modalidad,
			FORMAT(PROGRU_Nivel, '00') AS nivel,
			idioma.CMM_Valor AS idioma,
			CONCAT_WS(' - ', CAST(PAMODH_HoraInicio AS NVARCHAR(5)), CAST(PAMODH_HoraFin AS NVARCHAR(5))) AS horario,
			CAST(PROGRU_FechaInicio AS DATE) AS fechaInicio,
			CAST(PROGRU_FechaFin AS DATE) AS fechaFin,
			CONVERT(MONEY, -1 * montos.Subtotal) AS subTotal,
			CONVERT(MONEY, -1 * montos.Descuento) AS descuento,
			CONVERT(MONEY, -1 * montos.Total) AS total,
			CONVERT(MONEY, (-1 * ROUND(montos.Impuestos, 2))) AS impuestos,
			NULL AS porcentajeBeca,
			'' AS razonDescuento,
			NULL AS ligaCentrosPagos,
			estatusOV.CMM_Valor AS estatusOV,
			OVC_OrdenVentaCancelacionId AS cancelacionId,
			'' AS estatusInscripcion,
			'' AS entregaLibro
	FROM OrdenesVenta
			INNER JOIN OrdenesVentaDetalles ON OV_OrdenVentaId = OVD_OV_OrdenVentaId AND OVD_OVD_DetallePadreId IS NULL
			CROSS APPLY fn_getMontosDetalleOV(OVD_OrdenVentaDetalleId) AS montos
			INNER JOIN ControlesMaestrosMultiples AS estatusOV ON OV_CMM_EstatusId = estatusOV.CMM_ControlId
			INNER JOIN Sucursales AS sedeOV ON OV_SUC_SucursalId = sedeOV.SUC_SucursalId
			INNER JOIN Articulos ON ART_ArticuloId = OVD_ART_ArticuloId
			OUTER APPLY
			(
					SELECT TOP 1 * FROM Inscripciones WHERE INS_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId ORDER BY ISNULL(INS_FechaModificacion, INS_FechaCreacion) DESC
			) AS inscripcion
			OUTER APPLY
			(
					SELECT TOP 1 * FROM InscripcionesSinGrupo WHERE INSSG_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId ORDER BY ISNULL(INSSG_FechaModificacion, INSSG_FechaCreacion) DESC
			) AS inscripcionSinGrupo
			LEFT JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
			LEFT JOIN ProgramasIdiomas ON PROGRU_PROGI_ProgramaIdiomaId = PROGI_ProgramaIdiomaId
			LEFT JOIN ControlesMaestrosMultiples AS idioma ON PROGI_CMM_Idioma = idioma.CMM_ControlId
			LEFT JOIN Programas ON PROGI_PROG_ProgramaId = PROG_ProgramaId
			LEFT JOIN PAModalidades ON PROGRU_PAMOD_ModalidadId = PAMOD_ModalidadId
			LEFT JOIN PAModalidadesHorarios ON PROGRU_PAMODH_PAModalidadHorarioId = PAMODH_PAModalidadHorarioId
			LEFT JOIN BecasUDG ON INS_BECU_BecaId = BECU_BecaId
			LEFT JOIN Sucursales AS sedeGrupo ON PROGRU_SUC_SucursalId = sedeGrupo.SUC_SucursalId
			LEFT JOIN AlumnosExamenesCertificaciones ON OVD_OrdenVentaDetalleId = ALUEC_OVD_OrdenVentaDetalleId AND ALUEC_ART_ArticuloId = ART_ArticuloId
			LEFT JOIN AlumnosConstanciasTutorias ON OVD_OrdenVentaDetalleId = ALUCT_OVD_OrdenVentaDetalleId AND ALUCT_ART_ArticuloId = ART_ArticuloId
			LEFT JOIN Alumnos ON ISNULL(ISNULL(INS_ALU_AlumnoId, ISNULL(INSSG_ALU_AlumnoId, ALUEC_ALU_AlumnoId)), ALUCT_ALU_AlumnoId) = ALU_AlumnoId
			INNER JOIN OrdenesVentaCancelacionesDetalles ON OVD_OrdenVentaDetalleId = OVCD_OVD_OrdenVentaDetalleId
			INNER JOIN OrdenesVentaCancelaciones ON OVC_OrdenVentaCancelacionId = OVCD_OVC_OrdenVentaCancelacionId
	WHERE LEN(OV_Codigo) != 36
		AND OV_MPPV_MedioPagoPVId IS NOT NULL
		AND (INS_InscripcionId IS NULL OR INS_CMM_EstatusId = 2000512)	-- CMM_INS_Estatus Cancelada
		AND (INSSG_InscripcionId IS NULL OR INSSG_CMM_EstatusId = 2000543)	-- CMM_INSSG_Estatus Cancelada
GO