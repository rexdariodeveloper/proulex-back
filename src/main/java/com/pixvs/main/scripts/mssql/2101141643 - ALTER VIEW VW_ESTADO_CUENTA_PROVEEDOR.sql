SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER VIEW [dbo].[VW_ESTADO_CUENTA_PROVEEDOR] AS
select
	facturaId, 
	factura, 
	fecha, 
	termino, 
	vencimiento,
	sum(monto) monto,
	sum(pagado) pagado,
	(SUM(monto) - SUM(pagado)) restante,
	proveedorId,
	proveedorNombre,
	datediff(day,vencimiento, GETDATE()) dias
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
	proveedorId,
	proveedorNombre
from
(
	select 
		facturaId, 
		factura, 
		fecha, 
		termino, 
		vencimiento,
		SUM(total) monto,
		proveedorId,
		proveedorNombre
	from
	(
		select 
			CXPF_CXPFacturaId facturaId,
			CXPF_CodigoRegistro factura,
			cast(CXPF_FechaRegistro as date) fecha,
			CXPF_DiasCredito termino,
			cast(DATEADD(DAY,CXPF_DiasCredito,CXPF_FechaRegistro) as date) vencimiento,
			(select Total from  fn_getImpuestosArticulo(CXPFD_Cantidad,CXPFD_PrecioUnitario,CXPFD_Descuento,CXPFD_IVA,CXPFD_IEPS,CXPFD_IEPSCuotaFija)) total,
			CXPF_PRO_ProveedorId proveedorId,
			PRO_Nombre proveedorNombre
		from 
			CXPFacturas 
			inner join CXPFacturasDetalles on CXPF_CXPFacturaId = CXPFD_CXPF_CXPFacturaId
			inner join Proveedores on CXPF_PRO_ProveedorId = PRO_ProveedorId
		where
			CXPF_CMM_EstatusId NOT IN(2000111, 2000114)
	) as t1
	group by
		facturaId, factura, fecha, termino, vencimiento, proveedorId, proveedorNombre
) as t2
left join CXPPagosDetalles on facturaId = CXPPD_CXPF_CXPFacturaId
) as t3
group by
		facturaId, factura, fecha, termino, vencimiento, proveedorId, proveedorNombre;
GO


