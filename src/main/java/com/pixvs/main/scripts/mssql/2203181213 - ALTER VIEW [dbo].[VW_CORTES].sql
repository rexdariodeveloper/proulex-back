SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER VIEW [dbo].[VW_CORTES]
AS
(
	SELECT
		SCC_SucursalCorteCajaId AS corteId,
		SUC_Nombre AS sede,
		SCC_Codigo AS codigo,
		CONCAT(USU_Nombre,' ' + USU_PrimerApellido,' ' + USU_SegundoApellido) AS usuario,
		SCC_FechaInicio AS fechaInicio,
		COALESCE(SCC_FechaFin, GETDATE()) AS fechaFin,
		'' AS factura,
		codigo AS codigoOV, 
		detallePadreMontoCurso AS curso,
		acumuladoLibro AS libro,
		acumuladoExamen AS examen,
		detallePadreMontoOtros AS otros,
		montoDescuento AS descuento,
		montoTotal AS total,
		'' totalFiscal,
		COALESCE(PROGRU_Codigo, '') AS claveCurso,
		ART_CodigoArticulo AS codigoArticulo,
		ART_NombreArticulo AS descripcionArticulo,
		COALESCE(BAC_Codigo,'') AS cuentaDeposito,
		MPPV_Nombre AS metodoPago,
		referenciaPago,
		FORMAT(fecha,'dd/MM/yyyy HH:mm:ss') AS fecha,
		FORMAT(fechaPago,'dd/MM/yyyy') AS fechaPago,
		CMM_Valor AS estatus,
		CASE WHEN estatusId = 2000508 THEN detallePadreMontoCurso ELSE 0 END AS pagadoCurso,
		CASE WHEN estatusId = 2000508 THEN acumuladoLibro ELSE 0 END AS pagadoLibro,
		CASE WHEN estatusId = 2000508 THEN acumuladoExamen ELSE 0 END AS pagadoExamen,
		CASE WHEN estatusId = 2000508 THEN detallePadreMontoOtros ELSE 0 END AS pagadoOtros,
		CASE WHEN estatusId = 2000508 THEN montoTotal ELSE 0 END AS pagadoTotal,
		PROGRU_GrupoId AS grupoId
	FROM SucursalesCortesCajas
	INNER JOIN Sucursales ON SUC_SucursalId = SCC_SUC_SucursalId
	INNER JOIN Usuarios ON USU_UsuarioId = SCC_USU_UsuarioAbreId
	INNER JOIN (
		SELECT
			id,
			codigo,
			fecha,
			fechaPago,
			estatusId,
			sucursalCorteCajaId,
			medioPagoPVId,
			referenciaPago,
			montoDescuento,
			montoTotal,
			detallePadreId,
			detallePadreArticuloId,
			detallePadreMontoCurso,
			SUM(montoLibro) AS acumuladoLibro,
			SUM(montoExamen) AS acumuladoExamen,
			detallePadreMontoOtros
		FROM (
			SELECT
				id,
				codigo,
				COALESCE(fechaModificacion,fechaCreacion) AS fecha,
				fechaPago,
				estatusId,
				sucursalCorteCajaId,
				medioPagoPVId,
				referenciaPago,
				montoDescuento, 
				montoTotal,
				DetallePadre.OVD_OrdenVentaDetalleId AS detallePadreId,
				DetallePadre.OVD_ART_ArticuloId AS detallePadreArticuloId,
				CASE WHEN ArticuloPrimario.ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](DetallePadre.OVD_Cantidad,DetallePadre.OVD_Precio,DetallePadre.OVD_Descuento,CASE WHEN DetallePadre.OVD_IVAExento = 0 THEN DetallePadre.OVD_IVA ELSE 0 END,DetallePadre.OVD_IEPS,DetallePadre.OVD_IEPSCuotaFija)) ELSE 0 END AS detallePadreMontoCurso,
				CASE WHEN ArticuloSecundario.ART_ARTST_ArticuloSubtipoId = 2 THEN (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](DetalleHijo.OVD_Cantidad,DetalleHijo.OVD_Precio,DetalleHijo.OVD_Descuento,CASE WHEN DetalleHijo.OVD_IVAExento = 0 THEN DetalleHijo.OVD_IVA ELSE 0 END,DetalleHijo.OVD_IEPS,DetalleHijo.OVD_IEPSCuotaFija)) ELSE 0 END AS montoLibro,
				CASE WHEN ArticuloSecundario.ART_ARTST_ArticuloSubtipoId = 5 THEN (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](DetalleHijo.OVD_Cantidad,DetalleHijo.OVD_Precio,DetalleHijo.OVD_Descuento,CASE WHEN DetalleHijo.OVD_IVAExento = 0 THEN DetalleHijo.OVD_IVA ELSE 0 END,DetalleHijo.OVD_IEPS,DetalleHijo.OVD_IEPSCuotaFija)) ELSE 0 END AS montoExamen,
				CASE WHEN ArticuloPrimario.ART_PROGI_ProgramaIdiomaId IS NULL THEN (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](DetallePadre.OVD_Cantidad,DetallePadre.OVD_Precio,DetallePadre.OVD_Descuento,CASE WHEN DetallePadre.OVD_IVAExento = 0 THEN DetallePadre.OVD_IVA ELSE 0 END,DetallePadre.OVD_IEPS,DetallePadre.OVD_IEPSCuotaFija)) ELSE 0 END AS detallePadreMontoOtros
			FROM VW_OrdenesVenta
			INNER JOIN OrdenesVentaDetalles AS DetallePadre ON DetallePadre.OVD_OV_OrdenVentaId = id AND DetallePadre.OVD_OVD_DetallePadreId IS NULL
			INNER JOIN Articulos AS ArticuloPrimario on DetallePadre.OVD_ART_ArticuloId = ArticuloPrimario.ART_ArticuloId
			LEFT JOIN OrdenesVentaDetalles AS DetalleHijo ON DetalleHijo.OVD_OVD_DetallePadreId = DetallePadre.OVD_OrdenVentaDetalleId
			LEFT JOIN Articulos AS ArticuloSecundario on DetalleHijo.OVD_ART_ArticuloId = ArticuloSecundario.ART_ArticuloId
		) OVDDesglosado
		GROUP BY id, codigo, fecha, fechaPago, estatusId, sucursalCorteCajaId, medioPagoPVId, referenciaPago, montoDescuento, montoTotal, detallePadreId, detallePadreArticuloId, detallePadreMontoCurso, detallePadreMontoOtros
	) OVD ON sucursalCorteCajaId = SCC_SucursalCorteCajaId
	LEFT JOIN Inscripciones ON detallePadreId = INS_OVD_OrdenVentaDetalleId
	LEFT JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
	INNER JOIN Articulos ON detallePadreArticuloId = ART_ArticuloId
	LEFT JOIN BancosCuentas ON BAC_CuentaId = SUC_BAC_CuentaId
	INNER JOIN MediosPagoPV ON medioPagoPVId = MPPV_MedioPagoPVId
	INNER JOIN ControlesMaestrosMultiples ON estatusId = CMM_ControlId
)
GO
