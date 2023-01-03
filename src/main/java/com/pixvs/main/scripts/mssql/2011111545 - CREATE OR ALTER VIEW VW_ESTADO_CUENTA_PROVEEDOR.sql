CREATE OR ALTER  VIEW [dbo].[VW_ESTADO_CUENTA_PROVEEDOR] AS
select
	facturaId, 
	factura, 
	fecha, 
	termino, 
	vencimiento,
	sum(monto) monto,
	sum(pagado) pagado,
	(SUM(monto) - SUM(pagado)) restante,
	proveedorId
	--datediff(day,vencimiento, GETDATE()) dias
from
(
select
	facturaId, 
	factura, 
	fecha, 
	termino, 
	vencimiento,
	monto,
	coalesce(CXPPD_MontoAplicado,0) pagado,
	proveedorId
from
(
	select 
		facturaId, 
		factura, 
		fecha, 
		termino, 
		vencimiento,
		SUM(total) monto,
		proveedorId
	from
	(
		select 
			CXPF_CXPFacturaId facturaId,
			CXPF_CodigoRegistro factura,
			cast(CXPF_FechaRegistro as date) fecha,
			cmm_tp.CMM_Valor termino,
			cast(CXPF_FechaPago as date) vencimiento,
			(select Total from  fn_getImpuestosArticulo(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija)) total,
			CXPF_PRO_ProveedorId proveedorId
		from 
			CXPFacturas 
			inner join CXPFacturasDetalles on CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId
			inner join ControlesMaestrosMultiples cmm_tp on CXPF_CMM_TerminoPagoId = cmm_tp.CMM_ControlId
	) as t1
	group by
		facturaId, factura, fecha, termino, vencimiento, proveedorId
) as t2
left join CXPPagosDetalles on facturaId = CXPPD_CXPF_CXPFacturaId
) as t3
group by
		facturaId, factura, fecha, termino, vencimiento, proveedorId;