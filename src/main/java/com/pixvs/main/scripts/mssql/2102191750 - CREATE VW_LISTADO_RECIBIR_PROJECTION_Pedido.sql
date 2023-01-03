CREATE OR ALTER VIEW [dbo].[VW_LISTADO_RECIBIR_PROJECTION_Pedido]
AS
	SELECT
		id,
		codigo,
		fecha,
		localidadOrigenNombre,
		localidadCEDISNombre,
		comentario,
		estatusId,
		estatusValor
	FROM(
		SELECT
			PED_PedidoId AS id,
			PED_Codigo AS codigo,
			PED_Fecha AS fecha,
			almacenOrigen.ALM_Nombre AS localidadOrigenNombre,
			almacenCEDIS.ALM_Nombre AS localidadCEDISNombre,
			PED_Comentario AS comentario,
			PED_CMM_EstatusId AS estatusId,
			CMM_Valor AS estatusValor,
			PEDD_PedidoDetalleId AS pedidoDetalleId,
			PR_PedidoReciboId AS pedidoReciboId,
			PRD_PedidoReciboDetalleId AS pedidoReciboDetalleId,
			COALESCE(SUM(PRDL_Cantidad),0) AS cantidadDetalleRecibo,
			PEDD_CantidadSurtida AS cantidadSurtidaDetalle
		FROM Pedidos
		INNER JOIN PedidosDetalles ON PEDD_PED_PedidoId = PED_PedidoId
		INNER JOIN Localidades localidadOrigen ON localidadOrigen.LOC_LocalidadId = PED_LOC_LocalidadOrigenId
		INNER JOIN Almacenes almacenOrigen ON ALM_AlmacenId = localidadOrigen.LOC_ALM_AlmacenId
		INNER JOIN Localidades localidadCEDIS ON localidadCEDIS.LOC_LocalidadId = PED_LOC_LocalidadOrigenId
		INNER JOIN Almacenes almacenCEDIS ON almacenCEDIS.ALM_AlmacenId = localidadCEDIS.LOC_ALM_AlmacenId
		INNER JOIN ControlesMaestrosMultiples ON CMM_ControlId = PED_CMM_EstatusId
		INNER JOIN UsuariosAlmacenes ON USUA_ALM_AlmacenId = almacenOrigen.ALM_AlmacenId
		LEFT JOIN PedidosRecibos ON PR_PED_PedidoId = PED_PedidoId
		LEFT JOIN PedidosRecibosDetalles ON PRD_PR_PedidoReciboId = PR_PedidoReciboId AND PEDD_ART_ArticuloId = PRD_ART_ArticuloId
		LEFT JOIN PedidosRecibosDetallesLocalidades ON PRDL_PRD_PedidoReciboDetalleId = PRD_PedidoReciboDetalleId
		WHERE PED_CMM_EstatusId IN (2000262,2000263) AND USUA_USU_UsuarioId = 1
		GROUP BY
			PED_PedidoId, PED_Codigo, PED_Fecha, almacenOrigen.ALM_AlmacenId, almacenOrigen.ALM_Nombre, almacenCEDIS.ALM_AlmacenId, almacenCEDIS.ALM_Nombre, PED_Comentario, PED_CMM_EstatusId, CMM_Valor
			, PEDD_PedidoDetalleId, PEDD_CantidadSurtida, PR_PedidoReciboId, PRD_PedidoReciboDetalleId
	) PedidosAgrupadosReciboDetalle
	WHERE cantidadSurtidaDetalle - cantidadDetalleRecibo > 0
	GROUP BY id, codigo, fecha, localidadOrigenNombre, localidadCEDISNombre, comentario, estatusId, estatusValor
GO