SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE OR ALTER VIEW [dbo].[VW_ARTICULOS_TRANSITO]
AS
SELECT 
	IM_LOC_LocalidadId localidadId,
	IM_ART_ArticuloId articuloId,
	IM_UM_UnidadMedidaId umId, 
	IM_Referencia referencia,
	SUM(IM_Cantidad) cantidad,
	MAX(IM_FechaCreacion) fecha,
	IM_CMM_TipoMovimientoId tipoId
FROM 
	InventariosMovimientos 
WHERE 
	IM_CMM_TipoMovimientoId IN(2000012, 2000016, 2000017)
	AND IM_LOC_LocalidadId = 0
GROUP BY
	IM_LOC_LocalidadId, IM_ART_ArticuloId, IM_Referencia, IM_UM_UnidadMedidaId, IM_CMM_TipoMovimientoId
HAVING SUM(IM_Cantidad) > 0
GO