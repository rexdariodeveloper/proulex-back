CREATE OR ALTER VIEW [dbo].[VW_REPORTE_PAGO_PROVEEDORES] AS
	SELECT 
		  CXPP_FechaPago fechaPago
		, CXPP_IdentificacionPago referencia
		, CONCAT(BAC_Codigo,' - ',BAC_Descripcion) cuentaBancacaria
		, PRO_Nombre proveedor
		, CXPF_CodigoRegistro numeroFactura
		, CXPF_FechaRegistro fechaFactura
		, CXPF_MontoRegistro montoFactura
		, CXPP_MontoPago montoPagado
		, CXPS_CodigoSolicitud folioProgramacionPago
		, PRO_ProveedorId proveedorId
		, BAC_CuentaId cuentaId
	FROM 
		CXPPagos 
		INNER JOIN BancosCuentas ON CXPP_BAC_CuentaId = BAC_CuentaId
		INNER JOIN CXPPagosDetalles ON CXPP_CXPPagoId = CXPPD_CXPP_CXPPagoId
		INNER JOIN Proveedores ON CXPP_PRO_ProveedorId = PRO_ProveedorId
		INNER JOIN CXPFacturas ON CXPPD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN CXPSolicitudesPagosDetalles ON CXPF_CXPFacturaId = CXPSD_CXPF_CXPFacturaId
		INNER JOIN CXPSolicitudesPagos ON CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId
	WHERE
		CXPP_CMM_EstatusId != 2000173;
GO