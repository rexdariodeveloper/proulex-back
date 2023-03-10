SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

/** 
	Name: 
		fn_getTipoSurtimiento
**/
CREATE OR ALTER FUNCTION [dbo].[fn_getTipoSurtimiento](@codigoPedido varchar(150))
RETURNS BIT
AS
BEGIN
	DECLARE @restantes AS decimal(28,6);
	WITH X AS(
		SELECT
			PED_PedidoId AS pedidoId,
			PED_Codigo AS pedidoCodigo,
			PEDD_CantidadPedida AS pedidoCantidad,
			PEDD_PedidoDetalleId AS pedidoDetalleId,
			PED_LOC_LocalidadCEDISId AS localidadCEDISId,
			PEDD_ART_ArticuloId AS articuloPadreId,
			PEDD_ART_ArticuloId AS articuloId,
			CAST(1 AS decimal(28,6)) AS componenteCantidad
		FROM Pedidos
		INNER JOIN PedidosDetalles ON PEDD_PED_PedidoId = PED_PedidoId
		WHERE PED_Codigo = @codigoPedido

		UNION ALL

		SELECT
			pedidoId,
			pedidoCodigo,
			CAST((pedidoCantidad * ARTC_Cantidad) AS decimal(28,6)) AS pedidoCantidad,
			pedidoDetalleId,
			localidadCEDISId,
			articuloPadreId,
			ARTC_ART_ComponenteId AS articuloId,
			ARTC_Cantidad AS componenteCantidad
		FROM ArticulosComponentes
		INNER JOIN X ON articuloId = ARTC_ART_ArticuloId
	)

	SELECT
		@restantes = SUM(cantidad)
	FROM(
		SELECT
			pedidoId,
			pedidoCodigo,
			pedidoDetalleId,
			MAX(pedidoCantidad) + MIN(cantidad) AS cantidad
		FROM (
			SELECT
				pedidoId,
				pedidoCodigo,
				pedidoDetalleId,
				pedidoCantidad / componenteCantidad AS pedidoCantidad,
				SUM(COALESCE(IM_Cantidad, 0.00) / componenteCantidad) AS cantidad
			FROM X
			LEFT JOIN InventariosMovimientos ON pedidoCodigo = IM_Referencia AND localidadCEDISId = IM_LOC_LocalidadId AND articuloId = IM_ART_ArticuloId
			GROUP BY pedidoId, pedidoCodigo, pedidoDetalleId, pedidoCantidad, componenteCantidad, articuloId
		) Y
		GROUP BY pedidoId, pedidoCodigo, pedidoCantidad, pedidoDetalleId
	) Z;

	IF(@restantes > 0)
		RETURN 1;
	RETURN 0;
END