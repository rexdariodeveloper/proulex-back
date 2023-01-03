SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_REPORTE_PAGO_PROVEEDORES] AS
	SELECT
		CAST(CXPP_FechaCreacion AS DATE) fechaRegistro,
		sucursal sede,
		PRO_Nombre proveedor,
		CXPP_IdentificacionPago numeroDocumento,
		CXPP_MontoPago monto,
		CONCAT(MON_Nombre,' (',MON_Codigo,')') moneda,
		fechaVencimiento,
		CAST(CXPP_FechaPago AS DATE) fechaPago,
		FP_Nombre formaPago,
		BAC_Codigo cuenta,
		codigo,
		ordenCompraId,
		CXPS_CodigoSolicitud solicitud,
		CXPS_CXPSolicitudPagoId solicitudId,
		CXPP_IdentificacionPago identificacionPago,
		CXPP_ARC_ComprobanteId comprobanteId,
		pdfId,
		xmlId,

		sucursalId sedeId,
		PRO_ProveedorId proveedorId,
		MON_MonedaId monedaId,
		BAC_CuentaId cuentaId,
		FP_FormaPagoId formaPagoId
	FROM
		CXPPagos
		INNER JOIN Monedas ON CXPP_MON_MonedaId = MON_MonedaId
		INNER JOIN BancosCuentas ON CXPP_BAC_CuentaId = BAC_CuentaId
		INNER JOIN CXPPagosDetalles ON CXPP_CXPPagoId = CXPPD_CXPP_CXPPagoId
		INNER JOIN Proveedores ON CXPP_PRO_ProveedorId = PRO_ProveedorId
		INNER JOIN VW_CXPFacturas ON CXPPD_CXPF_CXPFacturaId = id
		INNER JOIN FormasPago ON CXPP_FP_FormaPagoId = FP_FormaPagoId
		INNER JOIN 
		(
			-- Solicitudes RH
			SELECT 
				CXPF_CXPFacturaId facturaId, 
				SUC_Nombre sucursal, 
				SUC_SucursalId sucursalId,
				CPXSPRH_Codigo codigo,
				NULL ordenCompraId,
				CXPF_ARC_FacturaPDFId pdfId, 
				CXPF_ARC_FacturaXMLId xmlId
			FROM CXPFacturas
			INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
			INNER JOIN Sucursales on SUC_SucursalId = CPXSPRH_SUC_SucursalId
			WHERE CPXSPRH_CMM_EstatusId NOT IN (2000352, 2000353, 2000355)
			GROUP BY CXPF_CXPFacturaId, SUC_Nombre, SUC_SucursalId, CPXSPRH_Codigo, CXPF_ARC_FacturaPDFId, CXPF_ARC_FacturaXMLId
			UNION ALL
			-- Solicitudes pago
			SELECT 
				CXPF_CXPFacturaId facturaId, 
				SUC_Nombre sucursal, 
				SUC_SucursalId sucursalId,
				OC_Codigo codigo,
				OC_OrdenCompraId ordenCompraId,
				CXPF_ARC_FacturaPDFId pdfId, 
				CXPF_ARC_FacturaXMLId xmlId
			FROM CXPFacturas
			INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
			INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
			INNER JOIN OrdenesCompra ON OCR_OC_OrdenCompraId = OC_OrdenCompraId
			INNER JOIN Almacenes ON OC_ALM_RecepcionArticulosAlmacenId = ALM_AlmacenId	
			INNER JOIN Sucursales ON ALM_SUC_SucursalId = SUC_SucursalId
			WHERE OC_CMM_EstatusId NOT IN(2000062, 2000065)
			GROUP BY CXPF_CXPFacturaId, SUC_Nombre, SUC_SucursalId, OC_Codigo, OC_OrdenCompraId, CXPF_ARC_FacturaPDFId, CXPF_ARC_FacturaXMLId
			UNION ALL
			-- Solicitudes pago servicios
			SELECT 
				CXPF_CXPFacturaId facturaId, 
				SUC_Nombre sucursal, 
				SUC_SucursalId sucursalId,
				CXPSPS_CodigoSolicitudPagoServicio codigo,
				NULL ordenCompraId,
				CXPF_ARC_FacturaPDFId pdfId, 
				CXPF_ARC_FacturaXMLId xmlId
			FROM CXPFacturas
			INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
			INNER JOIN CXPSolicitudesPagosServicios ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
			INNER JOIN Sucursales ON CXPSPS_SUC_SucursalId = SUC_SucursalId
			WHERE CXPSPS_CMM_EstatusId NOT IN (2000283, 2000284, 2000286)
			GROUP BY CXPF_CXPFacturaId, SUC_Nombre, SUC_SucursalId, CXPSPS_CodigoSolicitudPagoServicio, CXPF_ARC_FacturaPDFId, CXPF_ARC_FacturaXMLId
	) Solicitudes ON facturaId = id
	LEFT JOIN CXPSolicitudesPagosDetalles on CXPSD_CXPF_CXPFacturaId = facturaId
	LEFT JOIN CXPSolicitudesPagos on CXPS_CXPSolicitudPagoId = CXPSD_CXPS_CXPSolicitudPagoId
	WHERE 
		CXPP_CMM_EstatusId NOT IN (2000163, 2000164, 2000166)
		AND CXPSD_CMM_EstatusId NOT IN (2000163, 2000164, 2000166)
GO