SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE OR ALTER FUNCTION [dbo].[fn_getPedidoMovimientos] ( @pedidoId INT )
RETURNS TABLE 
AS
RETURN 
(
	SELECT PRD_PedidoReciboDetalleId AS Id,
		   PR_FechaCreacion AS Fecha,
		   ART_CodigoArticulo AS CodigoArticulo,
		   ART_NombreArticulo AS NombreArticulo,
		   UM_Nombre AS UM,
		   PRD_CantidadPedida AS CantidadPedida,
		   COALESCE(SUM(PRDL_Cantidad),0) AS CantidadRecibida,
		   PRD_CantidadSpill AS CantidadAjuste,
		   MAX(ALM_Nombre) AS Almacen,
		   USU_Nombre+ISNULL(' '+USU_PrimerApellido, '')+ISNULL(' '+USU_SegundoApellido, '') AS Usuario
	FROM PedidosRecibos
		 INNER JOIN PedidosRecibosDetalles ON PR_PedidoReciboId = PRD_PR_PedidoReciboId
		 LEFT JOIN PedidosRecibosDetallesLocalidades ON PRDL_PRD_PedidoReciboDetalleId = PRD_PedidoReciboDetalleId
		 INNER JOIN Articulos ON PRD_ART_ArticuloId = ART_ArticuloId
		 INNER JOIN UnidadesMedidas ON PRD_UM_UnidadMedidaId = UM_UnidadMedidaId
		 INNER JOIN Localidades ON LOC_LocalidadId = PRDL_LOC_LocalidadId
		 INNER JOIN Almacenes ON LOC_ALM_AlmacenId = ALM_AlmacenId
		 INNER JOIN Usuarios ON PR_USU_CreadoPorId = USU_UsuarioId
	WHERE PR_PED_PedidoId = @pedidoId
		  AND PR_CMM_EstatusId = 1000001
		  AND PRD_CMM_EstatusId = 1000001
	GROUP BY PRD_PedidoReciboDetalleId, PR_FechaCreacion, ART_CodigoArticulo, ART_NombreArticulo, UM_Nombre, PRD_CantidadPedida, PRD_CantidadSpill, USU_Nombre, USU_PrimerApellido, USU_SegundoApellido
)
