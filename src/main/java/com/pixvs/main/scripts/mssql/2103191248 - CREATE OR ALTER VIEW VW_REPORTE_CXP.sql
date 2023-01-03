SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_CXP] AS
	SELECT 
		fechaFactura fechaRegistro,
		sucursal sede,
		nombreProveedor,
		folio numeroDocumento,
		monto,
		(monto - montoPagado) saldo,
		CASE WHEN moneda IS NOT NULL THEN CONCAT(moneda,' (',MON_Codigo,')') ELSE NULL END moneda,
		fechaVencimiento,

		id,
		idProveedor,
		idMoneda,
		sucursalId idSede
	FROM 
		VW_CXPFacturas
	LEFT JOIN Monedas ON MON_MonedaId = idMoneda
	INNER JOIN 
	(
		-- Solicitudes RH
		SELECT CXPF_CXPFacturaId facturaId, SUC_Nombre sucursal, SUC_SucursalId sucursalId 
		FROM CXPFacturas
		INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		INNER JOIN Sucursales on SUC_SucursalId = CPXSPRH_SUC_SucursalId
		GROUP BY CXPF_CXPFacturaId, SUC_Nombre, SUC_SucursalId
		UNION ALL
		-- Solicitudes pago (Ordenes de compra)
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
		idEstatus NOT IN (2000114, 2000118)
		AND (monto - montoPagado) > 0
GO