CREATE OR ALTER VIEW [dbo].[VW_REPORTE_OC_RECIBOS]
AS
SELECT
	OC_Codigo codigoOC,
	OC_FechaOC fechaOC,
	PRO_Nombre proveedor,
	CMM_Valor tipoMovimiento,
	ART_NombreArticulo articulo,
	UM_Nombre um,

	OCD_Cantidad cantidadRequerida,
	IM_FechaCreacion fechaMovimiento,
	IM_Cantidad cantidadMovimiento,
	OCD_Precio costoPrevio,
	IM_CostoUnitario costoRecibir,
	ALM_Nombre almacen,
	LOC_Nombre localidad,

	IM_ART_ArticuloId articuloId,
	OC_PRO_ProveedorId proveedorId,
	OC_ALM_RecepcionArticulosAlmacenId almacenId
FROM
	InventariosMovimientos 
	INNER JOIN OrdenesCompraDetalles ON IM_ReferenciaMovtoId = OCD_OrdenCompraDetalleId
	INNER JOIN OrdenesCompra ON OCD_OC_OrdenCompraId = OC_OrdenCompraId
	INNER JOIN Proveedores ON PRO_ProveedorId = OC_PRO_ProveedorId
	INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = IM_CMM_TipoMovimientoId
	INNER JOIN Articulos ON ART_ArticuloId = IM_ART_ArticuloId
	INNER JOIN UnidadesMedidas ON UM_UnidadMedidaId = IM_UM_UnidadMedidaId
	INNER JOIN Almacenes ON ALM_AlmacenId = OC_ALM_RecepcionArticulosAlmacenId
	INNER JOIN Localidades ON LOC_LocalidadId = IM_LOC_LocalidadId
WHERE 
	IM_CMM_TipoMovimientoId IN (2000013, 2000014)
GO