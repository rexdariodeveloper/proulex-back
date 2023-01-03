SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_LISTADO_RECIBIR_PROJECTION_Pedido]
AS
	select
		PED_PedidoId id,
		PED_Codigo codigo,
		PED_Fecha fecha,
		almacenOrigen.ALM_Nombre localidadOrigenNombre,
		almacenCedis.ALM_Nombre localidadCEDISNombre,
		PED_Comentario comentario,
		estatus.CMM_ControlId estatusId,
		estatus.CMM_Valor estatusValor,
		permiso.USUA_USU_UsuarioId usuarioId
	from
	(
	SELECT
		pedidoId,
		SUM(surtido) surtido,
		SUM(devolucion) devolucion,
		SUM(spill) spill,
		SUM(recibido) recibido
	FROM
	(
		select 
			PEDD_PED_PedidoId pedidoId,
			SUM(PEDD_CantidadSurtida) surtido,
			0 devolucion,
			0 spill,
			0 recibido
		from
			PedidosDetalles
		group by
			PEDD_PED_PedidoId
		having
			SUM(PEDD_CantidadSurtida) > 0
		union
		select 
			PR_PED_PedidoId pedidoId, 
			0 surtido,
			SUM(PRD_CantidadDevuelta) devolucion,
			SUM(PRD_CantidadSpill) spill,
			SUM(PRDL_Cantidad) recibido
		from 
			PedidosRecibos
			inner join PedidosRecibosDetalles on PRD_PR_PedidoReciboId = PR_PedidoReciboId
			inner join PedidosRecibosDetallesLocalidades on PRDL_PRD_PedidoReciboDetalleId = PRD_PedidoReciboDetalleId
		group by
			PR_PED_PedidoId
	) as t1
	group by
		pedidoId
	having
		sum(surtido) - sum(devolucion + spill + recibido) > 0
	) pendientes
	inner join Pedidos on PED_PedidoId = pendientes.pedidoId
	inner join Localidades origen on PED_LOC_LocalidadOrigenId = origen.LOC_LocalidadId
	inner join Localidades cedis on PED_LOC_LocalidadCEDISId = cedis.LOC_LocalidadId
	inner join ControlesMaestrosMultiples estatus on PED_CMM_EstatusId = CMM_ControlId
	inner join Almacenes almacenOrigen on origen.LOC_ALM_AlmacenId = almacenOrigen.ALM_AlmacenId
	inner join Almacenes almacenCedis on cedis.LOC_ALM_AlmacenId = almacenCedis.ALM_AlmacenId
	inner join UsuariosAlmacenes permiso on almacenOrigen.ALM_AlmacenId = USUA_ALM_AlmacenId
GO