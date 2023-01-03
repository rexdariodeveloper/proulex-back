/****** Object:  StoredProcedure [dbo].[sp_SurtirPedidoPostAlerta]    Script Date: 04/02/2021 01:09:00 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER PROCEDURE [dbo].[sp_SurtirPedidoPostAlerta]  @pedidoId int, @estatusId int  
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
		SELECT @restante = [dbo].[fn_getTipoSurtimiento](PED_Codigo) FROM Pedidos WHERE PED_PedidoId = @pedidoId;

		SELECT
			CASE
				WHEN @restante = 0 THEN @surtirPedidoEstatusSurtido
				ELSE @surtirPedidoEstatusSurtidoParcial
			END AS estatusProceso,
			CASE
				WHEN @restante = 0 THEN @logTipoSurtido
				ELSE @logTipoSurtidoParcial
			END AS logTipo
	END ELSE IF @estatusId = @surtirPedidoEstatusRechazado BEGIN
		SELECT @surtirPedidoEstatusRechazado AS estatusProceso, @logTipoRechazado AS logTipo
	END

END
GO


UPDATE AlertasConfig SET
	ALC_CMM_EstadoAutorizado = 2000263,
	ALC_CMM_EstadoEnProceso = 2000267,
	ALC_CMM_EstadoRechazado = 2000269,
	ALC_SPFinal = 'sp_SurtirPedidoPostAlerta'
WHERE ALC_AlertaCId = 9
GO