SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_REPORTE_PEDIDOS] AS
	SELECT 
		PED_Codigo as pedido,
		CAST(PED_Fecha AS DATE) as fechaPedido,
		CONCAT(almacen.ALM_Nombre,' - ',origen.LOC_Nombre) as origen,
		destino.LOC_Nombre as destino,
		ART_CodigoArticulo as codigoArticulo,
		ART_NombreArticulo as articulo,
		UM_Nombre as um,
		PEDD_CantidadPedida as cantidadPedido,
		CAST(CASE WHEN PEDD_CantidadSurtida > 0 THEN PED_FechaModificacion ELSE NULL END AS DATE) as fechaSurtido,
		coalesce(PEDD_CantidadSurtida,0.00) as cantidadSurtida,
		CAST(recibo.fecha AS DATE) as fechaRecibo,
		coalesce(recibo.cantidad, 0.00) as cantidadRecibida,
		PEDD_CantidadPedida - PEDD_CantidadSurtida as cantidadPendiente,
		ART_CostoPromedio as costo,
		CAST(COALESCE(recibo.cantidad,0.00) * ART_CostoPromedio AS DECIMAL(10,2)) as total,
		origen.LOC_LocalidadId as origenId,
		PED_CMM_EstatusId as estatusId,
		almacen.ALM_AlmacenId as almacenId
	FROM 
		Pedidos 
		INNER JOIN PedidosDetalles ON PED_PedidoId = PEDD_PED_PedidoId
		INNER JOIN Localidades origen ON PED_LOC_LocalidadOrigenId = origen.LOC_LocalidadId
		INNER JOIN Almacenes almacen ON ALM_AlmacenId = origen.LOC_ALM_AlmacenId
		INNER JOIN Localidades destino ON PED_LOC_LocalidadCEDISId = destino.LOC_LocalidadId
		INNER JOIN Articulos ON PEDD_ART_ArticuloId = ART_ArticuloId
		INNER JOIN UnidadesMedidas on PEDD_UM_UnidadMedidaId = UM_UnidadMedidaId
		LEFT JOIN (
			SELECT 
				MAX(PR_Fecha) fecha, 
				PR_PED_PedidoId, 
				PRD_ART_ArticuloId, 
				SUM(PRDL_Cantidad) cantidad 
			FROM PedidosRecibos 
			INNER JOIN PedidosRecibosDetalles ON PR_PedidoReciboId = PRD_PR_PedidoReciboId 
			LEFT JOIN PedidosRecibosDetallesLocalidades ON PRDL_PRD_PedidoReciboDetalleId = PRD_PedidoReciboDetalleId
			GROUP BY PR_PED_PedidoId, PRD_ART_ArticuloId
		) AS recibo ON recibo.PR_PED_PedidoId = PED_PedidoId AND recibo.PRD_ART_ArticuloId = ART_ArticuloId
	WHERE
		PED_CMM_EstatusId IN (2000261,2000262,2000263,2000264,2000265);
GO