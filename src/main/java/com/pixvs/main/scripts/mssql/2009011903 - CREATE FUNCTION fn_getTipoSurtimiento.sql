SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/** 
	Name: 
		fn_getTipoSurtimiento
**/
 
CREATE OR ALTER FUNCTION fn_getTipoSurtimiento(@codigoPedido varchar(150))
RETURNS BIT
AS
BEGIN
	DECLARE @restantes AS decimal(28,6);
	SET @restantes = (SELECT SUM(restante.cantidad) FROM
				(
				SELECT 
					PEDD_CantidadPedida + IM_Cantidad cantidad
				FROM 
					Pedidos 
					INNER JOIN PedidosDetalles ON PED_PedidoId = PEDD_PED_PedidoId
					LEFT JOIN InventariosMovimientos ON PED_Codigo = IM_Referencia AND PED_LOC_LocalidadCEDISId = IM_LOC_LocalidadId AND PEDD_ART_ArticuloId = IM_ART_ArticuloId
				WHERE
					PED_Codigo = @codigoPedido
				) restante);

	IF(@restantes > 0)
		RETURN 1;
	RETURN 0;
END