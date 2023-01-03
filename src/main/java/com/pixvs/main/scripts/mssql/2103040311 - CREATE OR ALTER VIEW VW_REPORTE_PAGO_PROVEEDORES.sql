SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_PAGO_PROVEEDORES] AS
	SELECT
		CAST(CXPP_FechaCreacion AS DATE) fechaRegistro,
		SUC_Nombre sede,
		PRO_Nombre proveedor,
		CXPP_IdentificacionPago numeroDocumento,
		CXPP_MontoPago monto,
		CONCAT(MON_Nombre,' (',MON_Codigo,')') moneda,
		fechaVencimiento,
		CAST(CXPP_FechaPago AS DATE) fechaPago,
		FP_Nombre formaPago,
		BAC_Codigo cuenta,

		SUC_SucursalId sedeId,
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
		LEFT JOIN FormasPago ON CXPP_FP_FormaPagoId = FP_FormaPagoId
		INNER JOIN CXPSolicitudesPagosDetalles ON CXPSD_CXPF_CXPFacturaId = CXPPD_CXPF_CXPFacturaId
		INNER JOIN CXPSolicitudesPagos ON CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId
		INNER JOIN Sucursales ON CXPS_SUC_SucursalId = SUC_SucursalId
	WHERE
		CXPP_CMM_EstatusId != 2000173;
GO