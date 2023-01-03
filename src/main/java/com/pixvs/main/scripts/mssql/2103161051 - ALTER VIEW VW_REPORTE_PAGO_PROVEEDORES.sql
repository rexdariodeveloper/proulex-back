SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_PAGO_PROVEEDORES] AS
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
			SELECT CXPF_CXPFacturaId facturaId, SUC_Nombre sucursal, SUC_SucursalId sucursalId 
			FROM CXPFacturas
			INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
			INNER JOIN Sucursales on SUC_SucursalId = CPXSPRH_SUC_SucursalId
			GROUP BY CXPF_CXPFacturaId, SUC_Nombre, SUC_SucursalId
			UNION ALL
			-- Solicitudes pago
			SELECT CXPF_CXPFacturaId facturaId, SUC_Nombre sucursal, SUC_SucursalId sucursalId
			FROM CXPFacturas
			INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
			INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
			INNER JOIN OrdenesCompra ON OCR_OC_OrdenCompraId = OC_OrdenCompraId
			INNER JOIN Almacenes ON OC_ALM_RecepcionArticulosAlmacenId = ALM_AlmacenId	
			INNER JOIN Sucursales ON ALM_SUC_SucursalId = SUC_SucursalId
			GROUP BY CXPF_CXPFacturaId, SUC_Nombre, SUC_SucursalId
			UNION ALL
			-- Solicitudes pago servicios
			SELECT CXPF_CXPFacturaId facturaId, SUC_Nombre sucursal, SUC_SucursalId sucursalId
			FROM CXPFacturas
			INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
			INNER JOIN CXPSolicitudesPagosServicios ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
			INNER JOIN Sucursales ON CXPSPS_SUC_SucursalId = SUC_SucursalId
			GROUP BY CXPF_CXPFacturaId, SUC_Nombre, SUC_SucursalId
	) Solicitudes ON facturaId = id
	WHERE
		CXPP_CMM_EstatusId != 2000173;
GO