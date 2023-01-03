CREATE OR ALTER VIEW VW_REPORTE_ORDENES_COMPRA AS
	select 
		OC_Codigo codigo,
		ALM_Nombre almacen,
		OC_FechaOC fechaOC,
		OC_FechaRequerida fechaReq,
		CMM_Valor estatus,
		PRO_Nombre proveedor,
		ART_CodigoArticulo art_cod,
		ART_NombreArticulo articulo,
		UM_Nombre um,
		OCD_Cantidad cantidad,
		OCD_Precio precio,
		(select Subtotal from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) subtotal,
		(select IVA from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) iva,
		(select IEPS from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) ieps,
		(select Descuento from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) descuento,
		(select Total from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) total,

		PRO_ProveedorId,
		OC_Codigo,
		ART_ArticuloId,
		ALM_AlmacenId,
		OC_FechaOC,
		CMM_ControlId,
		OC_MON_MonedaId
	from 
		OrdenesCompra 
		inner join OrdenesCompraDetalles on OC_OrdenCompraId = OCD_OC_OrdenCompraId
		inner join Almacenes on OC_ALM_RecepcionArticulosAlmacenId = ALM_AlmacenId
		inner join ControlesMaestrosMultiples on OC_CMM_EstatusId = CMM_ControlId
		left join Proveedores on OC_PRO_ProveedorId = PRO_ProveedorId
		inner join Articulos on OCD_ART_ArticuloId = ART_ArticuloId
		inner join UnidadesMedidas on OCD_UM_UnidadMedidaId = UM_UnidadMedidaId