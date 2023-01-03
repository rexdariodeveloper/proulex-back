SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_RPT_Confirming] AS
SELECT
	suc_nombre nombreSucursal, 	
	nombreProveedor,
	OC_Codigo codigo,
	CAST(CXPF_FechaRegistro AS DATE) fechaRegistro,
	folio,
	CAST(fechaFactura AS DATE) fechaFactura,
	diasCredito,
	CAST(ultimaFechaPago AS DATE) ultimaFechaPago,
	subtotal,
	descuento,
	IVA,
	IEPS,
	NULL retencion,
	total,

	SUC_SucursalId sucursalId,
	idProveedor proveedorId,
	OC_USU_CreadoPorId creadoPorId
FROM
	(
		SELECT 
			OCR_OC_OrdenCompraId ordenCompraId, 
			CXPFD_CXPF_CXPFacturaId facturaId
		FROM
			OrdenesCompraRecibos INNER JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
		GROUP BY
			OCR_OC_OrdenCompraId, CXPFD_CXPF_CXPFacturaId
	) ocs 
	INNER JOIN OrdenesCompra ON ordenCompraId = OC_OrdenCompraId
	INNER JOIN VW_CXPFacturas ON facturaId = id
	INNER JOIN CXPFacturas ON id = CXPF_CXPFacturaId
	INNER JOIN Almacenes ON OC_ALM_RecepcionArticulosAlmacenId = ALM_AlmacenId
	INNER JOIN Sucursales ON ALM_SUC_SucursalId = SUC_SucursalId
UNION ALL
	SELECT 
		SUC_Nombre nombreSucursal,
		nombreProveedor,
		codigo,
		CAST(CXPF_FechaRegistro AS DATE) fechaRegistro,
		folio,
		CAST(fechaFactura AS DATE) fechaFactura,
		diasCredito,
		CAST(ultimaFechaPago AS DATE) ultimaFechaPago,
		subtotal,
		descuento,
		IVA,
		IEPS,
		NULL retencion,
		total,

		sucursalId,
		idProveedor proveedorId,
		creadoPorId
	FROM 
	(
		SELECT 
			CXPSPS_CodigoSolicitudPagoServicio codigo, 
			CXPSPS_SUC_SucursalId sucursalId, 
			CXPSPSD_CXPF_CXPFacturaId facturaId,
			CXPSPS_USU_CreadoPorId creadoPorId
		FROM
			CXPSolicitudesPagosServicios 
			INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
		GROUP BY
			CXPSPS_CodigoSolicitudPagoServicio, CXPSPS_SUC_SucursalId, CXPSPSD_CXPF_CXPFacturaId, CXPSPS_USU_CreadoPorId
	) solicitudes
	INNER JOIN VW_CXPFacturas ON facturaId = id
	INNER JOIN CXPFacturas ON id = CXPF_CXPFacturaId
	INNER JOIN Sucursales ON SUC_SucursalId = sucursalId
GO