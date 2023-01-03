SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_CORTES]
AS
(
	SELECT
		*
	FROM
	(
	SELECT
		SCC_SucursalCorteCajaId AS corteId,
		SUC_Nombre AS sede,
		SCC_Codigo AS codigo,
		CONCAT(USU_Nombre,' ' + USU_PrimerApellido,' ' + USU_SegundoApellido) AS usuario,
		SCC_FechaInicio AS fechaInicio,
		COALESCE(SCC_FechaFin, GETDATE()) AS fechaFin,
		folioFactura AS factura,
		codigo AS codigoOV,
		detallePadreMontoCurso AS curso,
		acumuladoLibro AS libro,
		acumuladoExamen AS examen,
		detallePadreMontoOtros AS otros,
		montoDescuento AS descuento,
		montoTotal AS total,
		'' totalFiscal,
		(
            CASE WHEN esCurso = 0 OR (esCurso = 1 AND OVCD_OVD_OrdenVentaDetalleId IS NOT NULL) THEN ''
            WHEN (INS_CMM_EstatusId = 2000512 AND esCurso = 1) OR ( esCurso = 1 AND PROGRU_Codigo IS NULL) THEN 'CURSO GUARDADO'
            ELSE COALESCE(PROGRU_Codigo, 'CURSO GUARDADO') END
        ) AS claveCurso,
		ART_CodigoArticulo AS codigoArticulo,
		ART_NombreArticulo AS descripcionArticulo,
		COALESCE(BAC_Codigo,'') AS cuentaDeposito,
		MPPV_Nombre AS metodoPago,
		referenciaPago,
		FORMAT(fecha,'dd/MM/yyyy HH:mm:ss') AS fecha,
		FORMAT(fechaPago,'dd/MM/yyyy') AS fechaPago,
		CMM_Valor AS estatus,
		CASE WHEN estatusId IN (2000507, 2000508) THEN detallePadreMontoCurso ELSE 0 END AS pagadoCurso,
		CASE WHEN estatusId IN (2000507, 2000508) THEN acumuladoLibro ELSE 0 END AS pagadoLibro,
		CASE WHEN estatusId IN (2000507, 2000508) THEN acumuladoExamen ELSE 0 END AS pagadoExamen,
		CASE WHEN estatusId IN (2000507, 2000508) THEN detallePadreMontoOtros ELSE 0 END AS pagadoOtros,
		CASE WHEN estatusId IN (2000507, 2000508) THEN montoTotal ELSE 0 END AS pagadoTotal,
		PROGRU_GrupoId AS grupoId,
		esCurso,
		fechaCreacion,
		0 AS esCancelacion,
		(CASE WHEN OVCD_OVD_OrdenVentaDetalleId IS NOT NULL THEN 1 ELSE 0 END) AS ovCancelada
		, descuentoDetalle, totalDetalle, grupoOriginalId
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
			detallePadreMontoOtros,
			folioFactura,
			esCurso,
			fechaCreacion,
			0 AS esCancelacion
			, descuentoDetalle, totalDetalle, grupoOriginalId
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
				CASE WHEN ArticuloPrimario.ART_PROGI_ProgramaIdiomaId IS NULL THEN (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](DetallePadre.OVD_Cantidad,DetallePadre.OVD_Precio,DetallePadre.OVD_Descuento,CASE WHEN DetallePadre.OVD_IVAExento = 0 THEN DetallePadre.OVD_IVA ELSE 0 END,DetallePadre.OVD_IEPS,DetallePadre.OVD_IEPSCuotaFija)) ELSE 0 END AS detallePadreMontoOtros,
				CXCF.CXCF_Folio AS folioFactura,
				CASE WHEN ArticuloPrimario.ART_PROGI_ProgramaIdiomaId IS NOT NULL THEN 1 ELSE 0 END AS esCurso,
				FORMAT(fechaCreacion,'yyyy/MM/dd HH:mm:ss') AS fechaCreacion,
				(SELECT Descuento FROM [dbo].[fn_getImpuestosArticulo](DetallePadre.OVD_Cantidad,DetallePadre.OVD_Precio,DetallePadre.OVD_Descuento,CASE WHEN DetallePadre.OVD_IVAExento = 1 THEN 0 ELSE DetallePadre.OVD_IVA END,DetallePadre.OVD_IEPS,DetallePadre.OVD_IEPSCuotaFija)) AS descuentoDetalle,
                (SELECT Total FROM [dbo].[fn_getImpuestosArticulo](DetallePadre.OVD_Cantidad,DetallePadre.OVD_Precio,DetallePadre.OVD_Descuento,CASE WHEN DetallePadre.OVD_IVAExento = 1 THEN 0 ELSE DetallePadre.OVD_IVA END,DetallePadre.OVD_IEPS,DetallePadre.OVD_IEPSCuotaFija)) AS totalDetalle,
                INS_PROGRU_GrupoId AS grupoOriginalId
			FROM VW_OrdenesVenta
			INNER JOIN OrdenesVentaDetalles AS DetallePadre ON DetallePadre.OVD_OV_OrdenVentaId = id AND DetallePadre.OVD_OVD_DetallePadreId IS NULL
			INNER JOIN Articulos AS ArticuloPrimario on DetallePadre.OVD_ART_ArticuloId = ArticuloPrimario.ART_ArticuloId
			LEFT JOIN (
				SELECT INS_InscripcionId, INS_PROGRU_GrupoId, INS_OVD_OrdenVentaDetalleId,
				ROW_NUMBER() over(partition by INS_OVD_OrdenVentaDetalleId order by INS_InscripcionId DESC) as NEWINDEX
				FROM Inscripciones
			) inscripciones ON DetallePadre.OVD_OrdenVentaDetalleId = inscripciones.INS_OVD_OrdenVentaDetalleId AND inscripciones.NEWINDEX = 1
			LEFT JOIN OrdenesVentaDetalles AS DetalleHijo ON DetalleHijo.OVD_OVD_DetallePadreId = DetallePadre.OVD_OrdenVentaDetalleId
			LEFT JOIN Articulos AS ArticuloSecundario on DetalleHijo.OVD_ART_ArticuloId = ArticuloSecundario.ART_ArticuloId
			LEFT JOIN CXCFacturas CXCF ON facturaId = CXCF.CXCF_FacturaId
		) OVDDesglosado
		GROUP BY id, codigo, fecha, fechaPago, estatusId, sucursalCorteCajaId, medioPagoPVId, referenciaPago, montoDescuento, montoTotal, detallePadreId, detallePadreArticuloId, detallePadreMontoCurso, detallePadreMontoOtros, folioFactura, esCurso, fechaCreacion, descuentoDetalle, totalDetalle, grupoOriginalId
	) OVD ON sucursalCorteCajaId = SCC_SucursalCorteCajaId
	LEFT JOIN Inscripciones ON detallePadreId = INS_OVD_OrdenVentaDetalleId AND (INS_InscripcionId IS NULL OR INS_CMM_EstatusId NOT IN(2000512, 2000513))
	LEFT JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
	LEFT JOIN OrdenesVentaCancelacionesDetalles ON OVCD_OVD_OrdenVentaDetalleId = detallePadreId
	INNER JOIN Articulos ON detallePadreArticuloId = ART_ArticuloId
	LEFT JOIN BancosCuentas ON BAC_CuentaId = SUC_BAC_CuentaId
	INNER JOIN MediosPagoPV ON medioPagoPVId = MPPV_MedioPagoPVId
	INNER JOIN ControlesMaestrosMultiples ON estatusId = CMM_ControlId

	) OVS
	UNION ALL
	(
	select
		SCC_SucursalCorteCajaId AS corteId,
		SUC_Nombre AS sede,
		SCC_Codigo AS codigo,
		CONCAT(USU_Nombre,' ' + USU_PrimerApellido,' ' + USU_SegundoApellido) AS usuario,
		SCC_FechaInicio AS fechaInicio,
		COALESCE(SCC_FechaFin, GETDATE()) AS fechaFin,
		'' AS factura,
		OVC_Codigo AS codigoOV,
		OVC_ImporteReembolsar * -1 AS curso,
		0.0 AS libro,
		0.0 AS examen,
		0.0 AS otros,
		0.0 AS descuento,
		OVC_ImporteReembolsar * -1 AS total,
		'' totalFiscal,
		OV_Codigo AS claveCurso,
		ART_CodigoArticulo AS codigoArticulo,
		CONCAT_WS(' ','Cancelación',tipo.CMM_Valor) AS descripcionArticulo,
		NULL AS cuentaDeposito,
		NULL AS metodoPago,
		NULL AS referenciaPago,
		FORMAT(COALESCE(OV_FechaModificacion,OV_FechaCreacion),'dd/MM/yyyy HH:mm:ss') AS fecha,
		FORMAT(OV_FechaPago,'dd/MM/yyyy') AS fechaPago,
		estatus.CMM_Valor AS estatus,
		CASE WHEN estatus.CMM_ControlId = 2000508 THEN 0 ELSE 0 END AS pagadoCurso,
		CASE WHEN estatus.CMM_ControlId = 2000508 THEN 0 ELSE 0 END AS pagadoLibro,
		CASE WHEN estatus.CMM_ControlId = 2000508 THEN 0 ELSE 0 END AS pagadoExamen,
		CASE WHEN estatus.CMM_ControlId = 2000508 THEN 0 ELSE 0 END AS pagadoOtros,
		CASE WHEN estatus.CMM_ControlId = 2000508 THEN 0 ELSE 0 END AS pagadoTotal,
		PROGRU_GrupoId AS grupoId,
		0 AS esCurso,
        FORMAT(OV_FechaCreacion,'yyyy/MM/dd HH:mm:ss') AS fechaCreacion,
        1 AS esCancelacion,
        0 AS ovCancelada
        , 0 AS descuentoDetalle, (OVC_ImporteReembolsar * -1) AS totalDetalle,
        NULL AS grupoOriginalId
	from
		OrdenesVentaCancelaciones
		inner join OrdenesVentaCancelacionesDetalles on OVC_OrdenVentaCancelacionId = OVCD_OVC_OrdenVentaCancelacionId
		inner join OrdenesVentaDetalles on OVCD_OVD_OrdenVentaDetalleId = OVD_OrdenVentaDetalleId
		inner join OrdenesVenta on OVD_OV_OrdenVentaId = OV_OrdenVentaId
		inner join Sucursales on OV_SUC_SucursalId = SUC_SucursalId
		inner join SucursalesCortesCajas on SCC_SucursalCorteCajaId = OV_SCC_SucursalCorteCajaId
		INNER JOIN Usuarios ON USU_UsuarioId = SCC_USU_UsuarioAbreId
		inner join Articulos on OVD_ART_ArticuloId = ART_ArticuloId
		inner join ControlesMaestrosMultiples estatus on OVC_CMM_EstatusId = estatus.CMM_ControlId
		inner join ControlesMaestrosMultiples tipo on OVC_CMM_TipoCancelacionId = tipo.CMM_ControlId
		LEFT JOIN Inscripciones ON OVD_OrdenVentaDetalleId = INS_OVD_OrdenVentaDetalleId
		LEFT JOIN ProgramasGrupos ON INS_PROGRU_GrupoId = PROGRU_GrupoId
	)
)
GO