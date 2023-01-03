CREATE OR ALTER VIEW [dbo].[VW_FOLIO_PROJECTION_CXPFactura] AS

	SELECT CXPF_CXPFacturaId AS id, OC_Codigo AS codigo
	FROM CXPFacturas
	INNER JOIN CXPFacturasDetalles ON CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
	INNER JOIN OrdenesCompraRecibos ON OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
	INNER JOIN OrdenesCompra ON OCR_OC_OrdenCompraId = OC_OrdenCompraId
	GROUP BY CXPF_CXPFacturaId, OC_Codigo

	UNION ALL

	SELECT CXPF_CXPFacturaId AS id, CXPSPS_CodigoSolicitudPagoServicio AS codigo
	FROM CXPFacturas
	INNER JOIN CXPSolicitudesPagosServiciosDetalles ON CXPSPSD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
	INNER JOIN CXPSolicitudesPagosServicios ON CXPSPS_CXPSolicitudPagoServicioId = CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId
	GROUP BY CXPF_CXPFacturaId, CXPSPS_CodigoSolicitudPagoServicio

	UNION ALL

	SELECT CXPF_CXPFacturaId AS id, CPXSPRH_Codigo AS codigo
	FROM CXPFacturas
	INNER JOIN CXPSolicitudesPagosRH ON CPXSPRH_CXPF_CXPFacturaId = CXPF_CXPFacturaId
	GROUP BY CXPF_CXPFacturaId, CPXSPRH_Codigo

GO