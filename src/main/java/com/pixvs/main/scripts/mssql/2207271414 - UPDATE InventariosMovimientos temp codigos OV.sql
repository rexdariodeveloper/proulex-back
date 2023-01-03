UPDATE im SET im.IM_Referencia = OV_Codigo
FROM InventariosMovimientos AS im
    INNER JOIN OrdenesVentaDetalles ON im.IM_ReferenciaMovtoId = OVD_OrdenVentaDetalleId
    INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
WHERE LEN(im.IM_Referencia) = 36
GO

UPDATE im SET im.IM_ReferenciaMovtoId = OVD_OrdenVentaDetalleId
FROM InventariosMovimientos AS im
	INNER JOIN OrdenesVentaDetalles ON im.IM_ReferenciaMovtoId = OVD_OVD_DetallePadreId AND IM_ART_ArticuloId = OVD_ART_ArticuloId
	INNER JOIN OrdenesVenta ON OVD_OV_OrdenVentaId = OV_OrdenVentaId
WHERE OV_Codigo = IM_Referencia AND IM_CMM_TipoMovimientoId = 2000433
GO