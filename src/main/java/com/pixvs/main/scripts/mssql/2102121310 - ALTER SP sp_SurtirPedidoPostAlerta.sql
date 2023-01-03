SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE [dbo].[sp_SurtirPedidoPostAlerta]  (@pedidoId int, @estatusId int, @estatusProceso int OUT, @logTipo int OUT)
AS
BEGIN

	DECLARE @surtirPedidoEstatusAutorizado int = 2000263;
	DECLARE @surtirPedidoEstatusRechazado int = 2000269;
	DECLARE @restante decimal(28,6);
	DECLARE @surtirPedidoEstatusSurtidoParcial int = 2000262;
	DECLARE @surtirPedidoEstatusSurtido int = 2000263;
	DECLARE @logTipoSurtidoParcial int = 25;
	DECLARE @logTipoSurtido int = 26;
	DECLARE @logTipoRechazado int = 5;

	IF @estatusId = @surtirPedidoEstatusAutorizado BEGIN
		SELECT @restante = SUM(PEDD_CantidadPedida - PEDD_CantidadSurtida)
		FROM Pedidos
		INNER JOIN PedidosDetalles ON PEDD_PED_PedidoId = PED_PedidoId AND PEDD_CMM_EstatusId != 1000003
		WHERE PED_PedidoId = @pedidoId;

		SELECT
			@estatusProceso = CASE
				WHEN @restante = 0 THEN @surtirPedidoEstatusSurtido
				ELSE @surtirPedidoEstatusSurtidoParcial
			END,
			@logTipo = CASE
				WHEN @restante = 0 THEN @logTipoSurtido
				ELSE @logTipoSurtidoParcial
			END
	END ELSE IF @estatusId = @surtirPedidoEstatusRechazado BEGIN
		SELECT @estatusProceso = @surtirPedidoEstatusRechazado, @logTipo = @logTipoRechazado
	END

END
GO