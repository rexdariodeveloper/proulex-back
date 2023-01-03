CREATE OR ALTER VIEW [dbo].[VW_RPT_CXPSolicitudesPagosDetalles] AS
SELECT 
		CXPS_CXPSolicitudPagoId as id, CXPS_CodigoSolicitud as codigo, 
		CONVERT(datetime, CXPS_FechaCreacion) AS fechaCreacion,
		dbo.getNombreCompletoUsuario(CXPS_USU_CreadoPorId) as creador, CXPS_USU_CreadoPorId as creadorId,
		SUC_SucursalId as sucursalId, COALESCE(SUC_Nombre, '-') as sucursal,
		cmm_es.CMM_Valor as estatus, cmm_es.CMM_ControlId as estatusId

,p.proveedores, p.facturas, p.total, p.totalMN, p.totalPagado, p.totalPagadoMN
,detalles.codigoProveedor, detalles.nombreProveedor, detalles.proveedorRFC
,detalles.detalleEstatus, detalles.detalleEstatusId, detalles.sucursalDetalle, detalles.idFactura, detalles.factura, detalles.fechaFactura, detalles.fechaVencimientoFactura, detalles.idMoneda, detalles.moneda, detalles.totalDetalle, detalles.totalDetalleMN, detalles.totalDetallePagado, detalles.totalDetallePagadoMN, detalles.folio

FROM CXPSolicitudesPagos
LEFT JOIN Sucursales on CXPS_SUC_SucursalId = SUC_SucursalId
LEFT JOIN ControlesMaestrosMultiples cmm_es on CXPS_CMM_EstatusId = cmm_es.CMM_ControlId

LEFT JOIN (

	SELECT CXPSD_CXPS_CXPSolicitudPagoId, COUNT ( DISTINCT cxpf.idProveedor ) as proveedores,
		count(DISTINCT CXPSD_CXPF_CXPFacturaId) as facturas, 
		sum(cxpf.total) as total, sum(cxpf.totalMN) as totalMN, 
		sum(cxpf.montoPagado) as totalPagado, sum(cxpf.montoPagadoMN) as totalPagadoMN
	FROM CXPSolicitudesPagosDetalles
	LEFT JOIN ControlesMaestrosMultiples cmm_es on CXPSD_CMM_EstatusId = cmm_es.CMM_ControlId
	LEFT JOIN dbo.[VW_CXPFacturas] cxpf on CXPSD_CXPF_CXPFacturaId = cxpf.id
	GROUP BY CXPSD_CXPS_CXPSolicitudPagoId

) p on p.CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId

LEFT JOIN (

	SELECT cxpf.codigoProveedor, cxpf.nombreProveedor, cxpf.proveedorRFC, CXPSD_CXPS_CXPSolicitudPagoId, cmm_es.CMM_Valor as detalleEstatus, cmm_es.CMM_ControlId as detalleEstatusId,
		COALESCE(srv.sucursalDetalle, oc.sucursalDetalle, '-') as sucursalDetalle,
		cxpf.id as idFactura, cxpf.folio as factura, cxpf.idMoneda, cxpf.moneda, cxpf.fechaFactura as fechaFactura, cxpf.fechaVencimiento as fechaVencimientoFactura,
		(cxpf.total) as totalDetalle, (cxpf.totalMN) as totalDetalleMN, 
		(cxpf.montoPagado) as totalDetallePagado, (cxpf.montoPagadoMN) as totalDetallePagadoMN,
		COALESCE(oc.folio, srv.folio, '-') as folio
	FROM CXPSolicitudesPagosDetalles
	LEFT JOIN CXPSolicitudesPagos ON CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId
	LEFT JOIN ControlesMaestrosMultiples cmm_es on CXPSD_CMM_EstatusId = cmm_es.CMM_ControlId
	LEFT JOIN dbo.[VW_CXPFacturas] cxpf on CXPSD_CXPF_CXPFacturaId = cxpf.id
	LEFT JOIN (
		SELECT CXPSPSD_CXPF_CXPFacturaId , CXPSPS_CodigoSolicitudPagoServicio as folio, SUC_Nombre as sucursalDetalle
		FROM CXPSolicitudesPagosServiciosDetalles
		LEFT JOIN CXPSolicitudesPagosServicios on CXPSPSD_CXPSPS_CXPSolicitudPagoServicioId = CXPSPS_CXPSolicitudPagoServicioId	
		LEFT JOIN Sucursales ON CXPSPS_SUC_SucursalId = SUC_SucursalId

	)srv on srv.CXPSPSD_CXPF_CXPFacturaId = CXPSD_CXPF_CXPFacturaId
	LEFT JOIN (
		SELECT CXPFD_CXPF_CXPFacturaId, OC_Codigo as folio, SUC_Nombre as sucursalDetalle
		FROM OrdenesCompraRecibos
		INNER JOIN CXPFacturasDetalles ON CXPFD_OCR_OrdenCompraReciboId = OCR_OrdenCompraReciboId
		INNER JOIN OrdenesCompra on OCR_OC_OrdenCompraId = OC_OrdenCompraId
		LEFT JOIN Almacenes on OC_ALM_RecepcionArticulosAlmacenId = ALM_AlmacenId
		LEFT JOIN Sucursales ON ALM_SUC_SucursalId = SUC_SucursalId
	)oc on oc.CXPFD_CXPF_CXPFacturaId = CXPSD_CXPF_CXPFacturaId

) detalles on detalles.CXPSD_CXPS_CXPSolicitudPagoId = CXPS_CXPSolicitudPagoId

 -- WHERE CXPS_CXPSolicitudPagoId = 1038--1045