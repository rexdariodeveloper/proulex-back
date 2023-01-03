CREATE OR ALTER VIEW [dbo].[VW_REPORTE_OC_RECIBOS_PENDIENTES]
AS
SELECT 
	OC_Codigo codigoOC, 
	OC_FechaOC fechaOC, 
	PRO_Nombre proveedor,
	ART_NombreArticulo articulo,
	UM_Nombre um,
	OCD_Cantidad cantidadRequerida, 
	COALESCE(OCR_CantidadRecibo,0) cantidadRecibida, 
	(OCD_Cantidad - COALESCE(OCR_CantidadRecibo, 0)) cantidadPendiente,

	OC_PRO_ProveedorId proveedorId, 
	OCD_ART_ArticuloId articuloId, 
	OCD_UM_UnidadMedidaId umId,
	OC_ALM_RecepcionArticulosAlmacenId almacenId
FROM
	OrdenesCompra 
	INNER JOIN OrdenesCompraDetalles ON OCD_OC_OrdenCompraId = OC_OrdenCompraId 
	LEFT JOIN OrdenesCompraRecibos ON OCR_OCD_OrdenCompraDetalleId = OCD_OrdenCompraDetalleId
	INNER JOIN Proveedores ON OC_PRO_ProveedorId = PRO_ProveedorId
	INNER JOIN Articulos ON OCD_ART_ArticuloId = ART_ArticuloId
	INNER JOIN UnidadesMedidas ON OCD_UM_UnidadMedidaId = UM_UnidadMedidaId
WHERE 
	OC_CMM_EstatusId IN (2000063, 2000066)
	AND (OCD_Cantidad - COALESCE(OCR_CantidadRecibo, 0)) > 0
GO