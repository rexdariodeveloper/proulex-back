SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_REPORTE_HISTORIAL_COMPRA] AS
	select 
		PRO_ProveedorId codigoProveedor,
		PRO_Nombre nombreProveedor,
		'' comercialProveedor,
		OC_Codigo codigoOC,
		OC_FechaOC fechaOC,
		cmm_oc.CMM_Valor estatusOC,
		cmm_cxpf.CMM_Valor estatusPartida ,
		ocd_um.UM_Nombre umOC,
		ARTT_Descripcion tipoArticulo,
		ART_CodigoArticulo codigoArticulo,
		ART_NombreArticulo nombreArticulo,
		OCD_IVA porcentajeIVA,
		OCD_Cantidad cantidadRequerida,
		OCD_FactorConversion factorConversion,
		OCD_Precio precioOC,
		(select Subtotal from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) subtotal,
		(select IVA from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) montoIVA,
		(select IEPS from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) montoIEPS,
		OCD_IEPS porcentajeIEPS,
		(select Descuento from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) descuento,
		(select Total from  fn_getImpuestosArticulo(OCD_Cantidad,OCD_Precio,OCD_Descuento,OCD_IVA,OCD_IEPS,OCD_IEPSCuotaFija)) total,
		OCR_FechaRecibo fechaRecibo,
		ART_CostoPromedio costoPromedio,
		ocd_um.UM_Nombre umOCR,
		OCR_CantidadRecibo cantidadRecibida,
		(OCD_Cantidad - OCR_CantidadRecibo) cantidadPendiente,
		CXPF_CXPFacturaId facturaId,
		CXPF_FechaRegistro fechaFactura,
		cxpf_um.UM_Nombre um2,
		CXPFD_PrecioUnitario precioFactura,
		CXPFD_IVA porcentajeIVA2,
		(select IVA from  fn_getImpuestosArticulo(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija)) montoIVA2,
		CXPFD_IEPS porcentajeIEPS2,
		(select IEPS from  fn_getImpuestosArticulo(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija)) montoIEPS2,
		(select Total from  fn_getImpuestosArticulo(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija)) total2,
		PRO_ProveedorId,
		OC_Codigo,
		ART_ArticuloId,
		ALM_AlmacenId,
		OC_FechaOC,
		OC_MON_MonedaId,
		cmm_oc.CMM_ControlId estatusId,
		CAST(DATEADD(DAY,CXPF_DiasCredito,CXPF_FechaRegistro) AS date) vencimiento
	from OrdenesCompra
		inner join Almacenes on OC_ALM_RecepcionArticulosAlmacenId = ALM_AlmacenId
		inner join Proveedores on OC_PRO_ProveedorId = PRO_ProveedorId
		inner join ControlesMaestrosMultiples cmm_oc on OC_CMM_EstatusId = cmm_oc.CMM_ControlId
		inner join OrdenesCompraDetalles on OC_OrdenCompraId = OCD_OC_OrdenCompraId
		inner join UnidadesMedidas ocd_um on OCD_UM_UnidadMedidaId = ocd_um.UM_UnidadMedidaId
		inner join Articulos on OCD_ART_ArticuloId = ART_ArticuloId
		inner join ArticulosTipos on ART_ARTT_TipoArticuloId = ARTT_ArticuloTipoId
		left join OrdenesCompraRecibos on OCD_OrdenCompraDetalleId = OCR_OCD_OrdenCompraDetalleId
		left join CXPFacturasDetalles on OCR_OrdenCompraReciboId = CXPFD_OCR_OrdenCompraReciboId
		left join UnidadesMedidas cxpf_um on CXPFD_UM_UnidadMedidaId = cxpf_um.UM_UnidadMedidaId
		left join CXPFacturas on CXPFD_CXPF_CXPFacturaId = CXPF_CXPFacturaId
		left join ControlesMaestrosMultiples cmm_cxpf on CXPF_CMM_EstatusId = cmm_cxpf.CMM_ControlId
GO


